#!/bin/bash
# A simple variable example
login="hlamboro-21"
remoteFolder="/tmp/$login/"
src_dir="src"
fileName="SimpleServerProgram"
fileExtension=".java"
computers=("tp-t310-08" "tp-t310-09" "tp-t310-10")
#computers=("tp-1a226-28")
for c in ${computers[@]}; do
  command0=("ssh" "$login@$c" "lsof -ti | xargs kill -9")
  command1=("ssh" "$login@$c" "rm -rf $remoteFolder;mkdir $remoteFolder")
  command2=("scp" "$src_dir/$fileName$fileExtension" "$login@$c:$remoteFolder")
  command3=("ssh" "$login@$c" "cd $remoteFolder;javac $fileName$fileExtension;java $fileName")
  echo "${command0[*]}"
  "${command0[@]}"
  echo ${command1[*]}
  "${command1[@]}"
  echo ${command2[*]}
  "${command2[@]}"
  echo ${command3[*]}
  "${command3[@]}" &
done