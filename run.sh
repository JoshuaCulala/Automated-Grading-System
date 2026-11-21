#!/bin/sh
mkdir -p out
javac -d out src/Main.java || exit 1
java -cp out Main
