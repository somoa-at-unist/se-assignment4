#!/bin/bash

#rm examples/simple/*.class

# give your own ROOT dir
ROOT=/SE_Assignment4/fl-assignment
FUZZ_TARGET=$ROOT/fuzz/target
# you can change the junit version
javac -cp $FUZZ_TARGET/classes:$FUZZ_TARGET/dependency/junit-4.13.1.jar:$FUZZ_TARGET/dependency/junit-quickcheck-core-0.8.jar:. examples/simple/ExampleClassTest.java
javac -cp $FUZZ_TARGET/classes:$FUZZ_TARGET/dependency/junit-4.13.1.jar:$FUZZ_TARGET/dependency/junit-quickcheck-core-0.8.jar:. examples/simple/ExampleClass.java
