#!/bin/bash

#not sure which folder level the user has extracted the standalone zip
#find the standalone openmrs modules folder
STANDALONE_MOD_PATH=`find ./openmrs-standalone/ -type d -name  modules | grep appdata\/modules`
STANDALONE_TOMCAT_PATH=$STANDALONE_MOD_PATH/../../tomcat

if [ -z "$STANDALONE_MOD_PATH" ]; then
echo "Cant find openrms-standalone modules folder. Have you extractd the openrms-standalone modules??"
exit
fi


#Replaced the existing omod in the standalone module folder with the
#newly built omod from the target folder
rm -rf $STANDALONE_MOD_PATH/cpm*.omod
rm -rf $STANDALONE_TOMCAT_PATH/webapps/openmrs-standalone
cp ./omod/target/*.omod $STANDALONE_MOD_PATH
echo Published omod to $STANDALONE_MOD_PATH

