#!/bin/bash
echo -----------------------------------------------------
echo MAKE SURE TO PUT PROCESSING_CORE.JAR IN LIB FOLDER
echo Starting jar generation...
echo -----------------------------------------------------

fileName=pacman-jhwang04.jar

#cd dist
cd bin

ls

mkdir -p libs
cp ../lib/processing_core.jar ./libs/processing_core.jar
cd libs
tar xf processing_core.jar
cp -R processing ../processing
#cp -R icon ../icon
#cp -R japplemenubar ../japplemenubar
cd ..
rm -R ./libs
#rm ./libs/processing_core.jar

jar cvfe ../dist/$fileName jhwang04.pacman.Pacman jhwang04/pacman processing

rm -R processing

cd ..
echo -----------------------------------------------------
echo Done! Saved $fileName to ./dist folder
echo -----------------------------------------------------
