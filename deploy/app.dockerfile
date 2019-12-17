FROM centos:7

RUN yum install java-1.8.0-openjdk.x86_64 libpcap -y && yum clean all
RUN /sbin/setcap cap_net_raw,cap_net_admin=eip  /usr/lib/jvm/java-1.8.0-openjdk-1.8.0.232.b09-0.el7_7.x86_64/jre/bin/java

WORKDIR /opt/app/

COPY ./app/SparkPackageCapture-1.0.jar . 
COPY ./app/lib/ ./lib/

