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
        clOptions.addOption("i", "interface", true, "IP address of the network interface");
        clOptions.addOption("t", "target", true, "IP address to listen");
        clOptions.addOption("d", "direction", true, "Direction of the listening (IN, OUT, INOUT)");
        clOptions.addOption("h", "help", false, "Print help");

        HelpFormatter formatter = new HelpFormatter();
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(clOptions, args);
        } catch (ParseException e) {
            logger.error("Unable to parse commandline arguments", e);
        }

        if (cmd.hasOption("help")){
            formatter.printHelp("SparkPackageCapture", clOptions);
            System.exit(0);
        }

        if (!cmd.hasOption("interface")){
            logger.error ("'Interface' is a mandatory option" );
            formatter.printHelp("SparkPackageCapture", clOptions);
            System.exit(1);
        } else {
            String str = cmd.getOptionValue("interface");
            try {
                networkInterfaceIP = InetAddress.getByName(str);
            } catch (UnknownHostException e) {
                logger.error("Unable to set 'interface' argument", e);
            }
        }

        if (!cmd.hasOption("target") && !cmd.hasOption("direction")){
            return new DefaultPacketHandler(networkInterfaceIP);
        }


        if (cmd.hasOption("target")){
            filter = "host " + cmd.getOptionValue("target");
        }
        if (cmd.hasOption("direction")){
            String str = cmd.getOptionValue("direction");
            listeningDirection = PcapHandle.PcapDirection.valueOf(str);
        }

        return new CustomPacketHandler(networkInterfaceIP, filter, listeningDirection);
    }

}
