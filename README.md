# SparkPackageCapture
SparkPackageCapture is a Java application for capturing packets using Spark, Kafka and Pcap.

## Prerequisites
Install [Docker](https://docs.docker.com/install/), [Docker-compose](https://docs.docker.com/compose/install/) and one of the Pcap implementations ([Libpcap](http://www.tcpdump.org/#latest-releases) for Linux or [Npcap](https://nmap.org/book/install.html) for Windows).

## Deployment
Execute in a shell:
```bash
git clone https://github.com/EvanKrasnikov/DinsExam.git
cd deploy
docker-compose up -d
```

## Usage
```bash
docker-compose exec --privileged app java -jar SparkPackageCapture-1.0.jar [ARGUMENTS]
```
Arguments from built-in help
```bash
usage: SparkPackageCapture
 -d,--direction <arg>   Direction of the listening (IN, OUT, INOUT)
 -h,--help              Print help
 -i,--interface <arg>   IP address of the network interface
 -t,--target <arg>      IP address to listen
```
