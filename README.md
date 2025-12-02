# Cucumber Playground

This repository is a small sample project used to learn and run Cucumber tests with Maven and Java.

## Prerequisites

- Java JDK (recommended: OpenJDK / Eclipse Temurin) 11 or newer
- Apache Maven 3.6 or newer

Make sure you install a JDK (not only a JRE) and configure the `JAVA_HOME` environment variable so Maven can find it.

## Verify current environment

Open a terminal (PowerShell on Windows, bash on Linux) and run:

```
java -version
mvn -version
```

Both commands should print version information. If `mvn` fails, Maven is not installed or not on your PATH.

## Install on Windows (PowerShell)

Preferred quick option: use Chocolatey (if you have it installed):

```
choco install temurin11 -y    # installs OpenJDK 11 (Eclipse Temurin)
choco install maven -y       # installs Apache Maven
```

If you don't use Chocolatey, download a JDK (Temurin/AdoptOpenJDK/Oracle) and Maven from their websites and install manually.

After installing a JDK, set `JAVA_HOME` for the current PowerShell session (replace the path with your JDK install path):

```
$env:JAVA_HOME = 'C:\Program Files\Eclipse Adoptium\jdk-11.0.16.1-hotspot'  # example path
$env:Path = "$env:JAVA_HOME\bin;$env:Path"
```

To persist `JAVA_HOME` for new sessions, use `setx` (replace the path):

```
setx JAVA_HOME "C:\Program Files\Eclipse Adoptium\jdk-11.0.16.1-hotspot"
# You may also add Maven's bin to PATH permanently via system settings or setx for PATH (careful when editing PATH).
```

## Install on Linux (Debian/Ubuntu)

On Debian/Ubuntu-based systems you can install OpenJDK and Maven from the package manager:

```
sudo apt update
sudo apt install -y openjdk-11-jdk maven
```

Set `JAVA_HOME` for the current session:

```
export JAVA_HOME=$(dirname $(dirname $(readlink -f $(which java))))
export PATH="$JAVA_HOME/bin:$PATH"
```

To persist these changes, append the export lines to `~/.bashrc` or `~/.profile`.

On other Linux distributions use the equivalent package manager (e.g., `yum`, `dnf`, `pacman`) or install the vendor JDK and Maven packages.

## Run the Cucumber tests

From the repository root (where `pom.xml` is located) run Maven's test goal. This project uses a JUnit-based Cucumber runner.

Run all tests:

```
mvn test
```

Run the specific test runner class (the project includes `RunCucumberTest`):

```
mvn -Dtest=RunCucumberTest test
```

If you prefer more verbose Maven output while diagnosing issues, remove `-q` or add `-X` for debug.

## Troubleshooting

- If `java -version` shows a JRE or an unexpected version, ensure `JAVA_HOME` points to a JDK installation.
- If Maven complains about missing plugins or dependencies, ensure your machine has internet access or configure a proxy in Maven settings (`~/.m2/settings.xml`).
- On Windows, when editing the system PATH via `setx`, new values apply only to newly opened terminals.

## Next steps / recommendations

- Consider pinning a JDK version (11 or 17) for reproducible builds.
- Add a CI pipeline (GitHub Actions, GitLab CI, etc.) to run `mvn test` on push.
- If you want reproducible developer environments, consider adding a Dockerfile or a `devcontainer.json`.

---