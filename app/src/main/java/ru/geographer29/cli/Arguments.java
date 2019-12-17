package ru.geographer29.cli;

public enum Arguments {

    INTERFACE("interface"),
    TARGET("target"),
    DIRECTION("direction"),
    HELP("help");

    Arguments(String value){
        this.value = value;
    }

    private String value;

    public String getValue(){
        return value;
    }

}
