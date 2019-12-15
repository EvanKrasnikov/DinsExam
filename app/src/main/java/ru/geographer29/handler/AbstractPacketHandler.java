package ru.geographer29.handler;

import org.pcap4j.core.*;
import ru.geographer29.spark.SparkPacketListener;

import java.net.InetAddress;

public abstract class AbstractPacketHandler implements PacketHandler {

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

        PcapHandle handle = null;
        try {
            int snaplen = 65536;
            int timeout = 10;
            int packetCount = 10;
            handle = device.openLive(snaplen, PcapNetworkInterface.PromiscuousMode.NONPROMISCUOUS, timeout);
            addSettings(handle);
            handle.loop(packetCount, new SparkPacketListener());
        } catch (PcapNativeException e) {
            System.out.println("Pcap library error");
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("Pcap loop error");
            e.printStackTrace();
        } catch (NotOpenException e) {
            e.printStackTrace();
        }
    }

}
