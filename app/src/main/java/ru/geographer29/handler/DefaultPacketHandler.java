package ru.geographer29.handler;

import org.pcap4j.core.PcapHandle;

import java.net.InetAddress;

public class DefaultPacketHandler extends AbstractPacketHandler {

    public DefaultPacketHandler(InetAddress networkInterfaceIP) {
        super(networkInterfaceIP);
    }

    @Override
    public void addSettings(PcapHandle handle) {

    }

}
