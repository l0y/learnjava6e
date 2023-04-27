#!/bin/sh

rm lj6review.jar
cd src
jar cvmf ../manifest.mf ../lj6review.jar res com/loyinc/*/*.class
