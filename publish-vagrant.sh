#!/bin/bash

MODULE_PATH=/var/lib/tomcat6/webapps/openmrs/WEB-INF/bundledModules

sudo rm -rf $MODULE_PATH/cpm*.omod
sudo cp ./omod/target/*.omod $MODULE_PATH
sudo /etc/init.d/tomcat6 restart
echo Published omod to $MODULE_PATH
