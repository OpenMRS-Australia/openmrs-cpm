Welcome to the OpenMRS Concept Proposal Module!
===============================================

Intro
-----

[OpenMRS](http://openmrs.org) is an open source medical records system actively developed by volunteers worldwide to support healthcare delivery in developing nations.

The Concept Proposal Module is intended to be used by local administrators who have created new concepts they wish to propose for inclusion in the central dictionary database.  There are 2 parts to this module: The proposal and the review.

To find out more information, visit our [wiki](https://wiki.openmrs.org/display/projects/Melbourne+Hack+Night+-+Concept+Proposal+Module)

How to Contribute
-----------------

We are looking for volunteers to help!  Please contact us via the [mailing list](http://groups.google.com/d/forum/openmrs-australia) or through the [meetup](http://www.meetup.com/melbourne-hack-nights) based in Melbourne, Australia.

If you're new to web or Java development, or are just curious to see what we're working with, have a look at the [summary of technologies used](https://github.com/OpenMRS-Australia/openmrs-cpm/wiki/Resources).

Contributors submit contributions in the form of [pull requests](https://help.github.com/articles/using-pull-requests) in order for a member of our team to review the code appropriately as any commits to master are picked up by our continuous deployment server.

Quickstart
----------

Follow the instructions in readme.txt on https://github.com/rdoh/cpm-go

As of now, you will need to come to the meetup in order to have access to the USB keys referenced in that document.

Sample workflow
---------------

    git checkout -b 92-github-move

    while not done:
        hack
        run(tests)
        refactor
        run(tests)
        git add .
        git commit -m 'Somthing meaningful'

    git push origin 92-github-move
    open https://github.com/<your-username>/openmrs-cpm
    Click the Pull Request button
    Verify your changes
    Send pull request


When the pull request has been submitted, somebody will review your changes and either approve the pull request or request modifications. The first thing that the reviewer will do is to run the following commands:

    $ ./go
    $ grunt

If either of these commands fail, the PR will be sent back without further review. Point being - you should run these commands yourself and resolve any issues before issuing the PR.

If your changes are approved, the pull request will be merged into the OpenMRS-cpm `master`, which will trigger a build. If changes are requested, discuss the changes with the reviewer, and then make modifications on the feature branch on your fork (which will cause them to appear in the pull request).

Once your pull request has been accepted, be sure to update your fork as described under "Staying updated": https://www.openshift.com/wiki/github-workflow-for-submitting-pull-requests. 

Errata
------
The [notes on setting up a dev environment](https://github.com/OpenMRS-Australia/openmrs-cpm/wiki/HowTo) and the [list of gotchas](https://github.com/OpenMRS-Australia/openmrs-cpm/wiki/Gotchas) may also be useful.

Links
-----

Mailing list: http://groups.google.com/d/forum/openmrs-australia

Meetup: http://www.meetup.com/melbourne-hack-nights

OpenMRS core source: https://github.com/openmrs/openmrs-core

Concept Proposal Module Documentation:
http://wiki.openmrs.org/display/projects/Concept+Proposal+Module+Documentation

CI server: http://ec2-50-112-42-202.us-west-2.compute.amazonaws.com:8153/go/

Card wall (Trello): https://trello.com/b/9OlGMLQm/openmrs-melb

Mingle [deprecated]: https://minglehosting.thoughtworks.com/unicef/profile/login?project_id=openmrs_oz

CI environment (admin, OpenMRS1): http://ec2-54-245-143-28.us-west-2.compute.amazonaws.com:8080/openmrs

QA environment (admin, OpenMRS1): http://ec2-54-245-1-154.us-west-2.compute.amazonaws.com:8080/openmrs
