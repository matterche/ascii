# Service for providing access to ascii images in text representation

### Commands

#### Publish with docker

  ```
  docker build -t <your_tag> .
  docker run -d -p 4444:4444 <your_tag>
  ```

This will package the application. Port 4444 is exposed.

#### Start sbt

  ```bash
  ./sbt
  ```
 
This will start the interactive sbt shell. You can also directly follow `sbt` with one of the subsequent commands, but
I'd recommend using the shell, as it is a lot faster.


#### Compile the project

  ```bash
  compile
  ```
  
Compiles the project without starting it. 

**Tip:** You can prefix any command with `~` (e.g. `~compile`) so it is executed on every file change.


#### Run application in dev mode

  ```bash
  run 4444
  ```
  
Compiles the project and starts the Play application in an automatic-reload mode.


#### Run all tests

  ```bash
  test
  ```
  
Runs the project test suite.

**Tip:** You can use `~test` to run your test suite continuously on every file change. This is handy when doing TDD/BDD.


#### Run subset of all tests testOnly

  ```bash
  testOnly ascii.AcceptanceSpec
  ```
  
Runs the specified spec only. You can use a wildcard (e.g. `*ascii.AcceptanceSpec`) to execute multiple matching specs.


#### Run failed tests

  ```bash
  testQuick
  ```
  
Runs only the specs which were failing in the last executed test command.


#### Reload project configuration

Use `reload` in your sbt session when you make any changes to `build.sbt` 
or files in the `project` folder. Otherwise the changes will likely not be applied.
