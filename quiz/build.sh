#!/bin/bash

rm lj6review.jar
cd src
javac com/loyinc/*/*.java
if [ $? -eq 0 ]
then
  jar cvmf ../manifest.mf ../lj6review.jar res com/loyinc/*/*.class
else
  echo "Compilation failed. Packaging skipped."
fi
