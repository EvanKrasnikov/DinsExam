package ru.geographer29.spark;

import org.pcap4j.core.PacketListener;
import org.pcap4j.packet.Packet;
import ru.geographer29.kafka.KafkaHandler;
import ru.geographer29.storage.DBHandler;
import ru.geographer29.storage.Query;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class SparkPacketListener implements PacketListener {
    private AtomicInteger trafficAmount = new AtomicInteger(0);
    private final int limitMin;
    private final int limitMax;

    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
    private Runnable checkIfLimitsExceed = new Runnable() {
        @Override
        public void run() {
            if (trafficAmount.get() < limitMin || trafficAmount.get() > limitMax){
                KafkaHandler.notifyKafka();
                trafficAmount.set(0);
            }
        }
    };

    {
        DBHandler dbHandler = new DBHandler();
        dbHandler.connect("localhost", 5432);
        limitMin = Integer.parseInt(dbHandler.selectElement(Query.SELECT_MIN_TRAFFIC_LIMIT.getQuery()));
        limitMax = Integer.parseInt(dbHandler.selectElement(Query.SELECT_MAX_TRAFFIC_LIMIT.getQuery()));
        dbHandler.disconnect();
        executorService.scheduleAtFixedRate(checkIfLimitsExceed, 5, 5, TimeUnit.MINUTES);
    }

    @Override
    public void gotPacket(Packet packet) {
        trafficAmount.getAndAdd(packet.length());
    }

}
