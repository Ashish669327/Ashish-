# vueautomation
This repository contains automated test scripts

## Instructions for making changes to vueautomation
1. Find an issue to work on
   - Issues can be found [here](https://github.com/orgs/GreywallSoftware/projects/1/)
2. Move the issue from **Todo** to **In Progress**
3. Create a new branch
   - Update your local master branch by pulling from the origin master branch
   - Create a new branch off of master
   - Name the branch using this convention: ***issue/{yourName}/{issueNumber}***
      > ex. issue/cam/168
4. Make any changes to fix the issue
5. Add, commit, and push the changes to the branch on the repo
6. Open a pull request (PR) for merging your new branch in master
   - This can be done in easily on the github website. After pushing changes to your new branch, a link will appear on github to **Compare & pull request**
   - ***In the description of the PR, write*** `fixes #{issueNumber}` ***to link the PR to the issue***
     > ex. fixes #168
7. Have someone else review the PR
8. Squash and merge into master

## Below are the basic requirements to execute the code
- Java_Version= 1.8.x
- Maven_Version = 3.6.x
- TestNG_Version = 6.14.x
- Selenium_Version = 3.13.x
- Chromedriver = 114

## Configuring the environment (for now)
- DriverFactory.java needs to know where chromedriver is
  - Either copy chromedriver into the drivers folder or mess with the path

## Using the import script
Instructions can be found [here](readme/IMPORT_TEST_CASES.md)
