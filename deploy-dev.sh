#!/bin/bash
#
#    .   ____          _            __ _ _
#   /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
#  ( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
#   \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
#    '  |____| .__|_| |_|_| |_\__, | / / / /
#   =========|_|==============|___/=/_/_/_/
#   :: Spring Boot Startup Script ::
#

### BEGIN INIT INFO
# Provides:          coupon
# Required-Start:    $remote_fs $syslog $network
# Required-Stop:     $remote_fs $syslog $network
# Default-Start:     2 3 4 5
# Default-Stop:      0 1 6
# Short-Description: coupon
# Description:       happylife coupon platform
# chkconfig:         2345 99 01
### END INIT INFO

git pull
mvn package  -Dmaven.test.skip=true
mkdir -p /data/www/wechat-service
mv impl/target/wechat-service.jar /data/www/wechat-service/wechat-service.jar
chmod -R 700 /data/www/wechat-service/wechat-service.jar
rm /etc/init.d/wechat-service
ln -s  /data/www/wechat-service/wechat-service.jar /etc/init.d/wechat-service
service wechat-service restart -Xms128m -Xmx128m

