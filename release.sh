#!/bin/bash
# USAGE: ./release.sh 1.1.1

if [ $# -eq 0 ]
  then
    echo "USAGE: ./release.sh 1.1.1"
   exit
fi


DIR=$(dirname $0)
VERSION_NAME=$1
ARR=(${VERSION_NAME//./ })
VERSION_CODE=$((  1000000*${ARR[0]} + 1000*${ARR[1]} + ${ARR[2]}  ))

git stash
git pull --rebase origin/master
git checkout develop -b release/$VERSION_NAME

sed -i \
	-e "s/versionName \".*\"/versionName \"$VERSION_NAME\"/" \
	-e "s/versionCode .*/versionCode $VERSION_CODE/" \
	$DIR/mobile/build.gradle

sed -i \
	-e "s/versionName \".*\"/versionName \"$VERSION_NAME\"/" \
	-e "s/versionCode .*/versionCode $VERSION_CODE/" \
	$DIR/wear/build.gradle

git commit -a -m "Update version in build.gradle to $VERSION_NAME."
git checkout master
git merge --no-ff --no-edit release/$VERSION_NAME
git push
git tag $VERSION_NAME
git push --tags
git checkout develop
git merge --no-ff --no-edit release/$VERSION_NAME
git push
git branch -d release/$VERSION_NAME