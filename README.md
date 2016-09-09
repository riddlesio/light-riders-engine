# Light Riders
This repository contains Light Riders for the Riddles.io platform and TheAIGames.com.

## Setting up

This guide assumes the following software to be installed and globally
accessible:

- Git
- Gradle 2.14
- Groovy 2.4.7
- JVM 1.8.0_91

Execute the following commands:

```
git clone git@github.com:riddlesio/light-riders-engine.git
cd light-riders-engine
rm -Rf .git
```

## Opening this project in IntelliJ IDEA

- Select 'Import Project'
- Browse to project directory root
- Select build.gradle
- Check settings:
- * Use local gradle distribution
- * Gradle home: /usr/share/gradle-2.14
- * Gradle JVM: 1.8
- * Project format: .idea (directory based)


### Building assets

What can Gradle do?
```
gradle build #Builds your project
gradle test #Runs all testing scripts
```

### Developing an Engine

## Application flow

The main Java code is split up in two parts:
- javainterface: handles everything the game engine needs to do to start, run and finish.
- tictactoe: contains project specific code and extends most of javainterfaces abstract classes.

The Engine's entry point is the `src/java/io/riddles/lightriders/Lightriders.java` file. This file creates a Light Riders Engine and invokes it's run function.

### Testing

Tests are located in:
test/groovy/io/riddles/lightriders

For testing, the Groovy language is used, which is compatible with JDK 8. When you run 'gradle test', all tests located in /test are executed and results are shown.

## Contributions

