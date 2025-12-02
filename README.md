# Cucumber Playground

This repository is a small sample project used to learn and run Cucumber tests with Maven and Java.

## Prerequisites

- Java JDK (recommended: OpenJDK / Eclipse Temurin) 11 or newer
- Apache Maven 3.6 or newer

Make sure you install a JDK (not only a JRE) and configure the `JAVA_HOME` environment variable so Maven can find it.

## Install on Windows 

Download a JDK (Temurin/AdoptOpenJDK/Oracle) and Maven from their websites and install manually.

JDK:    https://adoptium.net/temurin/releases

Maven:  https://maven.apache.org/download.cgi

Make sure JAVA_HOME points correctly to the installation folder for JDK and that MAVEN_HOME is correct as well, add %MAVEN_HOME%\bin to the PATH and restart the environment if required

To verify the installation, open a terminal (PowerShell on Windows, bash on Linux) and run:

```
java -version
mvn -version
```

Both commands should print version information. If `mvn` fails, Maven is not installed or not on your PATH.

## Install on Linux (Debian/Ubuntu)

On Debian/Ubuntu-based systems you can install OpenJDK and Maven from the package manager:

```
sudo apt update
sudo apt install -y openjdk-11-jdk maven
```

## Run the Cucumber tests

From the repository root (where `pom.xml` is located) run Maven's test goal. This project uses a JUnit-based Cucumber runner:

```
mvn test
```

To build and run:

```
mvn clean install
java -jar .\target\simple-calculator-1.0-SNAPSHOT.jar
```