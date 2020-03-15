# SparkPackageCapture
SparkPackageCapture is a Java application for capturing packets using Spark, Kafka and Pcap.

## Prerequisites
Install [JDK](https://www.oracle.com/technetwork/java/javase/downloads/index.html), [Maven](https://maven.apache.org/download.cgi), [Docker](https://docs.docker.com/install/) and [Docker-compose](https://docs.docker.com/compose/install/).

## Deployment
Execute in a shell:
```bash
git clone https://github.com/EvanKrasnikov/SparkPackageCapture.git
cd app && mvn install
cd ../deploy && docker-compose up -d
```

## Usage
```bash
docker-compose exec --privileged app java -jar SparkPackageCapture-1.0.jar [ARGUMENTS]
```
Arguments from built-in help. Argument "--interface" is mandatory.
```bash
usage: SparkPackageCapture
 -d,--direction <arg>   Direction of the listening (IN, OUT, INOUT)
 -h,--help              Print help
 -i,--interface <arg>   IP address of the network interface 
 -t,--target <arg>      IP address to listen
```
For example:
```bash
docker-compose exec --privileged app java -jar SparkPackageCapture-1.0.jar -i $(docker container inspect -f "{{ .NetworkSettings.Networks.deploy_default.IPAddress }}" app)
```

## Known issues
~~For unknown reasons Postgres failed to initialize database if container was deployed with docker-compose. Works fine with vanilla docker.~~

## TODO
- Count traffic with Spark Streaming
- Write proper settings for log4j
- Write more unit tests
