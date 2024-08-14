#!/bin/bash

/iahome/m/mj/mjschnie/software/jdk1.8.0_421/bin/java -jar jnaerator-0.12-shaded.jar config.jnaerator

jar -xvf openmm.jar

cp edu/uiowa/jopenmm/*java ../java/edu/uiowa/jopenmm/.

rm openmm.jar

rm -rf edu


