package ru.geographer29.handler;

import org.pcap4j.core.BpfProgram;
import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;

import java.net.InetAddress;

public class CustomPacketHandler extends AbstractPacketHandler {
    private String filter;
    private PcapHandle.PcapDirection listeningDirection;

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
            System.out.println("Pcap library error");
            e.printStackTrace();
        } catch (NotOpenException e) {
            System.out.println("Unable to change settings");
            e.printStackTrace();
        }
    }

}
