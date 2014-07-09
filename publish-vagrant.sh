#!/bin/bash

MODULE_PATH=/var/lib/tomcat6/webapps/openmrs/WEB-INF/bundledModules

sudo rm -rf $MODULE_PATH/openmrs-module-concept*.omod
sudo cp ./conceptpropose/build/libs/*.omod $MODULE_PATH
sudo cp ./conceptreview/build/libs/*.omod $MODULE_PATH
sudo /etc/init.d/tomcat6 restart
echo Published omod to $MODULE_PATH
