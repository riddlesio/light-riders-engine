#!/bin/bash

BASEDIR=`pwd`
java -jar $BASEDIR/match-wrapper-*.jar "$(cat wrapper-commands.json)"
