package ru.geographer29.cli;

import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pcap4j.core.PcapHandle;
import ru.geographer29.handler.CustomPacketHandler;
import ru.geographer29.handler.DefaultPacketHandler;
import ru.geographer29.handler.PacketHandler;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Parser {
    private final static Logger logger = LogManager.getLogger(Parser.class);

    public static PacketHandler parse(String[] args){
        InetAddress networkInterfaceIP = InetAddress.getLoopbackAddress();
        String filter = "host google.com";
        PcapHandle.PcapDirection listeningDirection = PcapHandle.PcapDirection.INOUT;

        Options clOptions = new Options();
        clOptions.addOption("i", Arguments.INTERFACE.getValue(), true, "IP address of the network interface");
        clOptions.addOption("t", Arguments.TARGET.getValue(), true, "IP address to listen");
        clOptions.addOption("d", Arguments.DIRECTION.getValue(), true, "Direction of the listening (IN, OUT, INOUT)");
        clOptions.addOption("h", Arguments.HELP.getValue(), false, "Print help");

        HelpFormatter formatter = new HelpFormatter();
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(clOptions, args);
        } catch (ParseException e) {
            logger.error("Unable to parse commandline arguments", e);
        }

        if (cmd.hasOption(Arguments.HELP.getValue())){
            formatter.printHelp("SparkPackageCapture", clOptions);
            System.exit(0);
        }

        if (!cmd.hasOption(Arguments.INTERFACE.getValue())){
            logger.error ("'Interface' is a mandatory option" );
            formatter.printHelp("SparkPackageCapture", clOptions);
            System.exit(1);
        } else {
            String str = cmd.getOptionValue(Arguments.INTERFACE.getValue());
            try {
                networkInterfaceIP = InetAddress.getByName(str);
            } catch (UnknownHostException e) {
                logger.error("Unable to set 'interface' argument", e);
            }
        }

        if (!cmd.hasOption(Arguments.TARGET.getValue()) && !cmd.hasOption(Arguments.DIRECTION.getValue())){
            return new DefaultPacketHandler(networkInterfaceIP);
        }


        if (cmd.hasOption(Arguments.TARGET.getValue())){
            filter = "host " + cmd.getOptionValue(Arguments.TARGET.getValue());
        }
        if (cmd.hasOption(Arguments.DIRECTION.getValue())){
            String str = cmd.getOptionValue(Arguments.DIRECTION.getValue());
            listeningDirection = PcapHandle.PcapDirection.valueOf(str);
        }

        return new CustomPacketHandler(networkInterfaceIP, filter, listeningDirection);
    }

}
