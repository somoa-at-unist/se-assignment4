#!/bin/bash

rm -f examples/simple/*.class

javac -cp target/classes examples/simple/ExampleClass.java

# If you use Linux or mac, use ":" instead of "\;".
javac -cp target/classes\;target/dependency/junit-4.13.1.jar\;target/dependency/junit-quickcheck-core-0.8.jar\;. examples/simple/ExampleClassTest.java
