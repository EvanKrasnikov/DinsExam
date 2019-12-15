package ru.geographer29;

import ru.geographer29.cli.Parser;
import ru.geographer29.handler.PacketHandler;

import java.util.Scanner;

public class App {

    public static void main( String[] args ) {
        PacketHandler packetHandler = Parser.parse(args);
        Thread thread = new Thread(packetHandler);
        thread.start();

        System.out.println("SparkPacket listener started. In order to terminate execution enter '/stop' ");
        Scanner scanner = new Scanner(System.in);

        for (;;){
            String str = scanner.next();

            if (str.equals("/stop")){
                break;
            }
        }

        packetHandler.stop();
        System.out.println("Execution was terminated");
    }

}
