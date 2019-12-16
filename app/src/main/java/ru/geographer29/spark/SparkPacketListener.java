package ru.geographer29.spark;

import org.apache.log4j.BasicConfigurator;
import org.pcap4j.core.PacketListener;
import org.pcap4j.packet.Packet;
import ru.geographer29.kafka.KafkaHandler;
import ru.geographer29.storage.DBHandler;
import ru.geographer29.storage.Query;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class SparkPacketListener implements PacketListener {
    private final static Logger logger = LogManager.getLogger(SparkPacketListener.class);
    private AtomicInteger trafficAmount = new AtomicInteger(0);
    private final int limitMin;
    private final int limitMax;

    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
    private Runnable checkIfLimitsExceed = new Runnable() {
        @Override
        public void run() {
            if (trafficAmount.get() < limitMin || trafficAmount.get() > limitMax){
                KafkaHandler.notifyKafka();
                logger.warn("Traffic amount exceeds limits");
                trafficAmount.set(0);
            }
        }
    };

    static {
        BasicConfigurator.configure();
    }

    {
        DBHandler dbHandler = new DBHandler();
        dbHandler.connect("localhost", 5432);
        limitMin = Integer.parseInt(dbHandler.selectElement(Query.SELECT_MIN_TRAFFIC_LIMIT.getQuery()));
        limitMax = Integer.parseInt(dbHandler.selectElement(Query.SELECT_MAX_TRAFFIC_LIMIT.getQuery()));
        dbHandler.disconnect();
        executorService.scheduleAtFixedRate(checkIfLimitsExceed, 5, 5, TimeUnit.MINUTES);
        logger.debug("Task was scheduled");
    }

    @Override
    public void gotPacket(Packet packet) {
        trafficAmount.getAndAdd(packet.length());
    }

}
