Welcome to the OpenMRS Concept Proposal Module!
===============================================

Build Requirements
------------------

* A Java Development Kit (either Oracle or OpenJDK)
* Maven
* Git


Quickstart
----------

1. [Download](http://openmrs.org/download/) the OpenMRS standalone distribution and login as the administrator
2. Clone the concept proposal module source code onto your local drive:
    * `git clone https://github.com/johnsyweb/openmrs-cpm.git`
3. Build the module:
    * `mvn -DskipTests=true package`
4. Locate the module file:
    * `omod/target/cpm-1.0-SNAPSHOT-<build-id>.omod`
5. Install the module into OpenMRS by uploading the OMOD file within administration section:
    * Administration -> Manage Modules -> Add or upgrade module -> Add module


Contributions
-------------

We are looking for volunteers to help!

We ask that newcomers please submit contributions
in the form of pull requests in order for a member of our team to review the code appropriately
as any commits to master are picked up by our continuous deployment server.

Links
-----

Mailing list: https://groups.google.com/d/forum/openmrs-australia

OpenMRS core source: https://github.com/openmrs/openmrs-core

Concept Proposal Module Documentation:
https://wiki.openmrs.org/display/projects/Concept+Proposal+Module+Documentation

