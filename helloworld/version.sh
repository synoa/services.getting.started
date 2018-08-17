#!/bin/sh

file="/git.properties"

# read the values of the Properties-File
# https://stackoverflow.com/a/28831442/6504528
if [ -f "$file" ]
then

  while IFS='=' read -r key value
  do
    key=$(echo $key | tr '.' '_')
    eval "${key}='${value}'"
  done < "$file"

  echo "Version   : " $git_build_version
  echo "Git Commit: " $git_commit_id

else
  echo "Can't find '$file'"
fi