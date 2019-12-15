package ru.geographer29.handler;

import org.pcap4j.core.*;
import ru.geographer29.spark.SparkPacketListener;

import java.net.InetAddress;

public abstract class AbstractPacketHandler implements PacketHandler {
    private PcapHandle handle = null;

    public AbstractPacketHandler(InetAddress ipAddress) {
        PcapNetworkInterface device = null;
        try {
            device = Pcaps.getDevByAddress(ipAddress);
        } catch (PcapNativeException e) {
            System.out.println("Pcap library error");
            e.printStackTrace();
        }
        if (device == null){
            System.out.println("Device has not been selected");
            throw new NullPointerException("Device has not been selected");
        } else {
            System.out.println("Device selected: " + device.getName());
        }

        try {
            int snaplen = 65536;
            int timeout = 10;
            handle = device.openLive(snaplen, PcapNetworkInterface.PromiscuousMode.NONPROMISCUOUS, timeout);
            addSettings(handle);
        } catch (PcapNativeException e) {
            System.out.println("Pcap library error");
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        int packetCount = Integer.MAX_VALUE;
        try {
            handle.loop(packetCount, new SparkPacketListener());
        } catch (PcapNativeException e) {
            System.out.println("Pcap library error");
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("Pcap loop error");
            e.printStackTrace();
        } catch (NotOpenException e) {
            System.out.println("Unable to set settings");
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        try {
            handle.breakLoop();
        } catch (NotOpenException e) {
            System.out.println("Unable to break the loop");
            e.printStackTrace();
        }
    }
}
