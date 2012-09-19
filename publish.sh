#!/bin/bash

MODULE_PATH=../openmrs-core/webapp/src/main/webapp/WEB-INF/bundledModules

rm -rf $MODULE_PATH/cpm*.omod
cp ./omod/target/*.omod $MODULE_PATH
echo Published omod to $MODULE_PATH
