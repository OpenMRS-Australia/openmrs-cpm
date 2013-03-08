Welcome to the OpenMRS Concept Proposal Module!
===============================================

Intro
-----

[OpenMRS](http://openmrs.org) is an Java based open source medical records system
actively developed by volunteers worldwide to support healthcare delivery in
developing nations.

The Concept Proposal Module is intended to be used by local administrators 
who have created new concepts they wish to propose for inclusion in the central
dictionary database.  There are 2 parts to this module: The proposal and the review.


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


How to Contribute
-----------------

We are looking for volunteers to help!  Please contact us via the [mailing list](http://groups.google.com/d/forum/openmrs-australia)
or through the [meetup](http://www.meetup.com/melbourne-hack-nights) based in Melbourne, Australia.

If you're new to web or Java development, or are just curious to see what we're working with, have a look at
the [summary of technologies used](https://github.com/johnsyweb/openmrs-cpm/wiki/Resources).

The [notes on setting up a dev environment](https://github.com/johnsyweb/openmrs-cpm/wiki/HowTo) and the [list of gotchas](https://github.com/johnsyweb/openmrs-cpm/wiki/Gotchas) may also be useful.

We ask that newcomers please submit contributions
in the form of pull requests in order for a member of our team to review the code appropriately
as any commits to master are picked up by our continuous deployment server.

Links
-----

Mailing list: http://groups.google.com/d/forum/openmrs-australia

Meetup: http://www.meetup.com/melbourne-hack-nights

OpenMRS core source: https://github.com/openmrs/openmrs-core

Concept Proposal Module Documentation:
http://wiki.openmrs.org/display/projects/Concept+Proposal+Module+Documentation


