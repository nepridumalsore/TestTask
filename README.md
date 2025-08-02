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

## Installation
1. **Install Java 17**:
    - Download and install Java 17 from [Adoptium](https://adoptium.net/temurin/releases/?version=17).
    - Verify installation:
      ```bash
      java -version

## Install Maven:
- `Download and install Maven 3.8.6 or later from Apache Maven.`
- `Verify installation:`
   ```bash
  mvn --version
## Clone the Repository
- `Clone or download the project from GitHub:`
   ```bash
  git clone https://github.com/nepridumalsore/TestTask.git
  cd TestTask
## Building the Project
1. `Navigate to the project directory and run:`
2. `Run the following command to build the project and create the JAR file:`
   ```bash
   mvn clean package
3. `The executable JAR will be created in target/TestTask-0.0.1-SNAPSHOT.jar.`
## Usage
- `Run the utility with the following command:`
    ```bash
  java -jar target/TestTask-0.0.1-SNAPSHOT.jar [options] input_file1 [input_file2 ...]
## Command-Line Options
- **-a**: Append mode (append data to output files instead of overwriting).
- **-s**: Short statistics (display only the count of integers, floats, and strings). 
- **-f**: Full statistics (display count, min, max, sum, and average for numbers; count, min/max length for strings). 
- **-p <prefix>**: Prefix for output file names (e.g., -p sample-creates sample-integers.txt, sample-floats.txt, sample-strings.txt). 
- **-o <path>**: Output directory for result files (e.g., -o output saves files to output/).

## Example
1. `Create input files (in1.txt, in2.txt) in the project directory:`
    ```bash
    echo -e "Lorem ipsum dolor sit amet\n45\nПример\n3.1415\nconsectetur adipiscing\n-0.001\nтестовое задание\n100500" > in1.txt
    echo -e "Нормальная форма числа с плавающей запятой\n1.528535047E-25\nLong\n1234567890123456789" > in2.txt
2. `Run the utility:`
    ```bash
   java -jar target/TestTask-0.0.1-SNAPSHOT.jar -s -a -p sample- in1.txt in2.txt
3. `Expected output:`
   ```text
    Statistics:
    Integers: 3
    Floats: 3
    Strings: 6   
4. `Check output files:`
   - `sample-integers.txt:`
   ```text
    45
    100500
    1234567890123456789

- `sample-floats.txt:`
    ```text
    3.1415
    -0.001
    1.528535047E-25

- `sample-strings.txt:`
    ```text
    Lorem ipsum dolor sit amet
    Пример
    consectetur adipiscing
    тестовое задание
    Нормальная форма числа с плавающей запятой
    Long