package ru.geographer29.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pcap4j.core.*;
import ru.geographer29.spark.SparkPacketListener;

import java.net.InetAddress;

public abstract class AbstractPacketHandler implements PacketHandler {
    private final static Logger logger = LogManager.getLogger(AbstractPacketHandler.class);
    private PcapHandle handle = null;

    public AbstractPacketHandler(InetAddress ipAddress) {
        PcapNetworkInterface device = null;
        try {
            device = Pcaps.getDevByAddress(ipAddress);
        } catch (PcapNativeException e) {
            logger.error("Pcap library error", e);
        }
        if (device == null){
            logger.error("Device has not been selected", new NullPointerException());
        } else {
            logger.info("Device selected: " + device.getName());
        }

        try {
            int snaplen = 65536;
            int timeout = 10;
            handle = device.openLive(snaplen, PcapNetworkInterface.PromiscuousMode.NONPROMISCUOUS, timeout);
            addSettings(handle);
        } catch (PcapNativeException e) {
            logger.error("Pcap library error", e);
        }
    }

    @Override
    public void run() {
        int packetCount = Integer.MAX_VALUE;
        try {
            handle.loop(packetCount, new SparkPacketListener());
        } catch (PcapNativeException e) {
            logger.error("Pcap library error", e);
        } catch (InterruptedException e) {
            logger.error("Pcap loop error", e);
        } catch (NotOpenException e) {
            logger.error("Unable to set settings", e);
        }
    }

    @Override
    public void stop() {
        try {
            handle.breakLoop();
        } catch (NotOpenException e) {
            logger.error("Unable to break the loop", e);
        }
    }
}
