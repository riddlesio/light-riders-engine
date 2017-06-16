# Starapple Light Riders
This repository contains the engine for the StarApple Light Riders game for the Riddles.io platform.

## Setting up

This guide assumes the following software to be installed and globally
accessible:

- Gradle 2.14
- JVM 1.8.0_91

## Opening this project in IntelliJ IDEA

- Select 'Import Project'
- Browse to project directory root
- Select build.gradle
- Check settings:
- * Use local gradle distribution
- * Gradle home: /usr/share/gradle-2.14
- * Gradle JVM: 1.8
- * Project format: .idea (directory based)

*Note: for other IDEs, look at online documentation*

## Building the engine

Use Gradle to build a .jar of the engine. Go to Tasks -> build -> jar.  
The .jar file can be found at `build/libs/`.

*Note: You can also download the latest engine .jar file from the 'releases'
tab on the GitHub repo.*

## Running 

Running is handled by the GameWrapper. This application handles all communication between
the engine and bots and stores the results of the match. To run, firstly edit the 
`wrapper-commands.json` file. This should be pretty self-explanatory. Just change the command
fields to the right values to run the engine and the bots. In the example, the starterbot
is run twice, plus the command for the engine built in the previous step.
 
To run the GameWrapper, use the following command (Linux):
````
java -jar match-wrapper-*.jar "$(cat wrapper-commands.json)"
````
Or run `run_wrapper.sh`, which contains the same command.

*Note: if running on other systems, find how to put the content of wrapper-commands.json as
argument when running the match-wrapper.jar*