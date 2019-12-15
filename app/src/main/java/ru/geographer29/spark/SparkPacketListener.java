package ru.geographer29.spark;

import org.pcap4j.core.PacketListener;
import org.pcap4j.packet.Packet;
import ru.geographer29.kafka.KafkaHandler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class SparkPacketListener implements PacketListener {
    private AtomicInteger trafficAmount = new AtomicInteger(0);
    private final int limitMin = 1024;
    private final int limitMax = Integer.MAX_VALUE / 2;

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
        executorService.scheduleAtFixedRate(checkIfLimitsExceed, 5, 5, TimeUnit.MINUTES);
    }

    @Override
    public void gotPacket(Packet packet) {
        trafficAmount.getAndAdd(packet.length());
    }

}
