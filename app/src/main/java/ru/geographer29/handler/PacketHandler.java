package ru.geographer29.handler;

import org.pcap4j.core.PcapHandle;

public interface PacketHandler {

    void addSettings(PcapHandle handle);

}
