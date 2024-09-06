
#!/bin/bash

# Check if JAVA_HOME is empty
if [ -z "$JAVA_HOME" ]; then
    echo "Required JAVA_HOME and PATH"
    export JAVA_HOME="/usr/lib/jvm/java-17-openjdk"
fi

# Check if PATH contains JAVA_HOME/bin
if [[ ! "$PATH" == *"$JAVA_HOME/bin"* ]]; then
    echo "Adding JAVA_HOME/bin to PATH"
    export PATH="$JAVA_HOME/bin:$PATH"
fi

echo "Step 1: Set JAVA_HOME and PATH"
#export PATH="$JAVA_HOME/bin:$PATH"
#export JAVA_HOME="/usr/lib/jvm/java-17-openjdk"

echo "Step 2: Execute Spring Boot application"
mvn clean package
mvn dependency:tree
java -jar target/note-demo.jar