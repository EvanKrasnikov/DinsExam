package ru.geographer29.spark;

import org.pcap4j.core.PacketListener;
import org.pcap4j.packet.Packet;
import ru.geographer29.kafka.KafkaHandler;
import ru.geographer29.storage.DBHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static ru.geographer29.storage.Queries.*;

public class SparkPacketListener implements PacketListener {
    private final static Logger logger = LogManager.getLogger(SparkPacketListener.class);
    private AtomicInteger trafficAmount = new AtomicInteger(0);
    private AtomicInteger atomicLimitMin = new AtomicInteger(0);
    private AtomicInteger atomicLimitMax = new AtomicInteger(0);

    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
    private Runnable checkIfLimitsExceed = new Runnable() {
        @Override
        public void run() {
            int traffic = trafficAmount.getAndSet(0);
            int limitMin = atomicLimitMin.get();
            int limitMax = atomicLimitMax.get();
            String msg = "";

            if (traffic < limitMin){
                msg = String.format("Traffic amount exceeds the min limit.\n Limit = %s \n Traffic = %s\n", limitMin, traffic);
            } else if (traffic > limitMax){
                msg = String.format("Traffic amount exceeds the max limit.\n Limit = %s \n Traffic = %s\n", limitMax, traffic);
            }

            KafkaHandler.notifyKafka(msg);
            logger.warn(msg);
        }
    };

    {
        try (DBHandler dbHandler = new DBHandler()){
            atomicLimitMin.set(Integer.parseInt(dbHandler.selectElement(SELECT_MIN_TRAFFIC_LIMIT)));
            atomicLimitMax.set(Integer.parseInt(dbHandler.selectElement(SELECT_MAX_TRAFFIC_LIMIT)));
        } catch (Exception e){
            logger.error("Unable to update limits:", e);
        }

        executorService.scheduleAtFixedRate(checkIfLimitsExceed, 5, 5, TimeUnit.MINUTES);
        logger.debug("Task was scheduled");
    }

    @Override
    public void gotPacket(Packet packet) {
        trafficAmount.getAndAdd(packet.length());
    }

}
