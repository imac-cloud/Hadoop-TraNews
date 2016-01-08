#!/bin/bash

#download sqoop
cd /opt

if ls /opt | grep -Fxq "sqoop-1.4.6.bin__hadoop-2.0.4-alpha.tar.gz"
then
    echo "Found"
    tar xvzf sqoop-1.4.6.bin__hadoop-2.0.4-alpha.tar.gz
else
    echo "Not Found... "
    wget ftp://ftp.stu.edu.tw/Unix/Web/apache/sqoop/1.4.6/sqoop-1.4.6.bin__hadoop-2.0.4-alpha.tar.gz
    tar xvzf sqoop-1.4.6.bin__hadoop-2.0.4-alpha.tar.gz
fi

#download mysql-connector

if ls /opt/sqoop/lib | grep -Fxq "mysql-connector-java-5.1.36.jar"
then
    echo "Found"
else
    echo "Not Found... "
    wget http://repo1.maven.org/maven2/mysql/mysql-connector-java/5.1.36/mysql-connector-java-5.1.36.jar
fi


#rename sqoop-1.4.6.bin__hadoop-2.0.4-alpha to sqoop
sudo mv sqoop-1.4.6.bin__hadoop-2.0.4-alpha sqoop

# install mysql 
expect -c "
spawn sudo apt-get install mysql-server
expect \"Do you want to continue? [Y/n]\"
send \"y\r\"
"

#move mysql-connector-java-5.1.36.jar to /opt/sqoop/lib/
sudo mv mysql-connector-java-5.1.36.jar /opt/sqoop/lib/

#get hadoop jar
sudo cp /opt/hadoop-2.6.0/share/hadoop/mapreduce/hadoop-mapreduce-client-common-2.6.0.jar /opt/sqoop/lib/
sudo cp /opt/hadoop-2.6.0/share/hadoop/mapreduce/hadoop-mapreduce-client-core-2.6.0.jar /opt/sqoop/lib/


#sqoop-env.sh
echo 'export HADOOP_COMMON_HOME=/opt/hadoop-2.6.0
export HADOOP_MAPRED_HOME=/opt/hadoop-2.6.0' >> /opt/sqoop/conf/sqoop-env.sh


#run sqoop version
/opt/sqoop/bin/sqoop version





