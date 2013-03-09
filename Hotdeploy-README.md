*** steps to hot deployment

1. Install the omodreloader module
1.a download the module from https://github.com/openmrs/openmrs-module-omodreloader
1.b run the standalone server
1.c	login as admin 
1.d open "manage modules"
1.e add omodreloader
1.f stop the standalone server

2. edit the line in openmrs-standalone-runtime.properties starting with vm_arguments and add
-Domodreloader.paths=~/<PATH-TO>/openmrs-cpm/omod/target

3. Start the OpenMRS standalone server
$ start openmrs-standalone.jar

4. build the api module and install it to the local maven repositorz
$ mvn install -pl api

5. Just build the part you've changed (in our case the omod frontend)
$ mvn -pl omod -DskipTests package