package ru.geographer29.handler;

import org.apache.log4j.BasicConfigurator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pcap4j.core.BpfProgram;
import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;

import java.net.InetAddress;

public class CustomPacketHandler extends AbstractPacketHandler {
    private final static Logger logger = LogManager.getLogger(CustomPacketHandler.class);
    private String filter;
    private PcapHandle.PcapDirection listeningDirection;

    static {
        BasicConfigurator.configure();
    }

    public CustomPacketHandler(InetAddress networkInterfaceIP, String filter, PcapHandle.PcapDirection listeningDirection) {
        super(networkInterfaceIP);
        this.filter = filter;
        this.listeningDirection = listeningDirection;
    }

    @Override
    public void addSettings(PcapHandle handle) {
        try {
            handle.setFilter(filter, BpfProgram.BpfCompileMode.OPTIMIZE);
            handle.setDirection(listeningDirection);
        } catch (PcapNativeException e) {
            logger.error("Pcap library error", e);
        } catch (NotOpenException e) {
            logger.error("Unable to change settings", e);
        }
    }

}
