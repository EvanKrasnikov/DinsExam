package ru.geographer29.handler;

import org.pcap4j.core.PcapHandle;

public interface PacketHandler extends Runnable {

    void addSettings(PcapHandle handle);
    void stop();

}
