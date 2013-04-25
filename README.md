Build project:
=========
For build, inside gist-cli folder type:`ant` for build project by Ant or type:`mvn install` for build by maven, it create runnable jar file. As needed you can automatically install ivy, for it type: `init-ivy`

Сommands:
=========
* `java –jar gist-cli.jar –d` the same behavior as previously command if exist inside working directory user property file (“config.properties”)
* `java –jar gist-cli.jar –h` show command list
* `java –jar gist-cli.jar –show gistId` show gist info by ID
* `java –jar gist-cli.jar –show all` show all user gists info
* `java –jar gist-cli.jar –download gistId` download gist files by gistId into work directory (/localRepository/’gistId’/)
* `java –jar gist-cli.jar –download all` download all gists files into work directory  (/localRepository/’gistId’/)


Documentation:
=========
* Ant `ant makedoc` create documentation to `gist-cli\build\runnable\javadoc\`
* Maven  `mvn javadoc:javadoc` create doucmentation to `gist-cli\target\site\`



Tests:
=========
* Maven  `mvn test` run tests and create report to `gist-cli\target\surefire-reports\index.html`
