<!-- README.md -->
# File Filter Utility

This is a Java-based utility for filtering data from input files into separate output files based on data type (integers, floats, strings) with statistics collection.

## Requirements
- **Java Version**: 17
- **Build System**: Maven 3.8.6 or later
- **Dependencies**:
    - Apache Commons Lang 3.14.0 (`org.apache.commons:commons-lang3:3.14.0`)
        - Link: https://mvnrepository.com/artifact/org.apache.commons/commons-lang3/3.14.0

## Project Structure
- `src/main/java/com/example/FileFilterUtility.java`: Main application logic
- `pom.xml`: Maven configuration file
- `README.md`: This instruction file

## Building the Project
1. Ensure Maven and Java 17 are installed.
2. Clone or download the project.
3. Navigate to the project directory and run:
   ```bash
   mvn clean package