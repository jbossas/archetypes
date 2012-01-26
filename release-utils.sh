#!/bin/sh

SOURCE="${BASH_SOURCE[0]}"
while [ -h "$SOURCE" ] ; do SOURCE="$(readlink "$SOURCE")"; done
DIR="$( cd -P "$( dirname "$SOURCE" )" && pwd )"

# DEFINE

ARCHETYPES="jboss-html5-mobile-archetype"

SNAPSHOT_REPO_URL="https://repository.jboss.org/nexus/content/repositories/snapshots/"
SNAPSHOT_REPO_ID="jboss-snapshots-repository"
RELEASE_REPO_URL="https://repository.jboss.org/nexus/service/local/staging/deploy/maven2/"
RELEASE_REPO_ID="jboss-releases-repository"

# SCRIPT

usage()
{

HUMAN_READABLE_ARCHETYPES=""
i=0
for archetype in $ARCHETYPES
do
   if [ $i -ne 0 ]
   then
      HUMAN_READABLE_ARCHETYPES="${HUMAN_READABLE_ARCHETYPES}, "
   fi
   echo ""
   HUMAN_READABLE_ARCHETYPES="${HUMAN_READABLE_ARCHETYPES}${archetype}"
   i=$[$i+1]
done


cat << EOF
usage: $0 options

This script aids with releases. Remember to generate new blank archetypes before release

OPTIONS:
   -u      Updates version numbers in all POMs, used with -o and -n
   -l      Install all archetypes locally: $HUMAN_READABLE_ARCHETYPES
   -o      Old version number to update from
   -n      New version number to update to
   -s      Deploy a snapshot of the archetypes
   -r      Deploy a release of the archetypes
   -h      Shows this message

EOF
}

update()
{
cd $DIR
echo "Updating versions from $OLDVERSION TO $NEWVERSION for all Java and XML files under $PWD"
perl -pi -e "s/${OLDVERSION}/${NEWVERSION}/g" `find . -name \*.xml -or -name \*.java`
}

install()
{
   for archetype in $ARCHETYPES
   do
      echo "\n**** Installing $archetype\n"
      mvn clean install -f ${archetype}/pom.xml
   done

}

snapshot()
{
   for archetype in $ARCHETYPES
   do
      echo "\n**** Deploying $archetype to ${SNAPSHOT_REPO_URL} \n"
      mvn clean deploy -f ${archetype}/pom.xml -DaltDeploymentRepository=${SNAPSHOT_REPO_ID}::default::${SNAPSHOT_REPO_URL}
   done

}

release()
{
   for archetype in $ARCHETYPES
   do
      echo "\n**** Deploying $archetype to ${RELEASE_REPO_URL} \n"
      mvn clean deploy -f ${archetype}/pom.xml -DaltDeploymentRepository=${RELEASE_REPO_ID}::default::${RELEASE_REPO_URL}
   done

}

OLDVERSION="1.0.0-SNAPSHOT"
NEWVERSION="1.0.0-SNAPSHOT"
CMD="usage"

while getopts “srluo:n:” OPTION

do
     case $OPTION in
         u)
             CMD="update"
             ;;
         h)
             usage
             exit
             ;;
         o)
             OLDVERSION=$OPTARG
             ;;
         n)
             NEWVERSION=$OPTARG
             ;;
         s)
             CMD="snapshot"
             ;;
         r)  
             CMD="release"
             ;;
         l)
             CMD="install"
             ;;
         [?])
             usage
             exit
             ;;
     esac
done

$CMD

