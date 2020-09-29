# Duke project

_Duke_ is a Java project for managing tasks effectively using the command-line interface. Given below are instructions on how to use it.

## Setting up in IntelliJ

Prerequisites: JDK 11, update IntelliJ to the most recent version.

1. Open IntelliJ (if you are not in the welcome screen, click `File` > `Close Project` to close the existing project dialog first)
1. Set up the correct JDK version, as follows:
   1. Click `Configure` > `Structure for New Projects` and then `Project Settings` > `Project` > `Project SDK`
   1. If JDK 11 is listed in the drop down, select it. If it is not, click `New...` and select the directory where you installed JDK 11
   1. Click `OK`
1. Import the project into IntelliJ as follows:
   1. Click `Open or Import`.
   1. Select the project directory, and click `OK`
   1. If there are any further prompts, accept the defaults.
1. After the importing is complete, locate the `src/main/java/Duke.java` file, right-click it, and choose `Run Duke.main()`. If the setup is correct, you should see something like the below:
   ```
    ____________________________________________________________
    Created data/duke.txt to store future tasks.
    ____________________________________________________________
    
    ____________________________________________________________
    Hello dude! I'm Duke
    How can I help you?
    ____________________________________________________________
   ```
