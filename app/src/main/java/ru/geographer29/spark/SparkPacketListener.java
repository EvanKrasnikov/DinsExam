package ru.geographer29.spark;

import org.pcap4j.core.PacketListener;
import org.pcap4j.packet.Packet;

public class SparkPacketListener implements PacketListener {
    static int i = 0;

    @Override
    public void gotPacket(Packet packet) {
        //System.out.println("[" + Thread.currentThread().getName() + "] Package length = " + packet.length() + " bytes. Total number of packets = " + ++i);
    }

}
