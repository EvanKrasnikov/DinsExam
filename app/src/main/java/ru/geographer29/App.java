package ru.geographer29;

import ru.geographer29.cli.Parser;
import ru.geographer29.handler.PacketHandler;

public class App {

    public static void main( String[] args ) {

        PacketHandler packetHandler = Parser.parse(args);

    }

}
