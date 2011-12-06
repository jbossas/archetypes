#!/bin/sh

SOURCE="${BASH_SOURCE[0]}"
while [ -h "$SOURCE" ] ; do SOURCE="$(readlink "$SOURCE")"; done
DIR="$( cd -P "$( dirname "$SOURCE" )" && pwd )"

# DEFINE

ARCHETYPES="jboss-javaee6-webapp-archetype jboss-javaee6-webapp-ear-archetype"

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

This script generates a "blanked" version of an archetype.

The name of archetype directory must match the artifactId of the archetype. Arcehtypes are placed in $DIR with the suffix -blank

OPTIONS:
   -a      Generate a blank archetype for all archetypes, currently ${HUMAN_READABLE_ARCHETYPES}
   -n      The name of the archetype to blank.
   -c      Removes the blank archetypes
   -h      Shows this message

EOF
}

clean() 
{
    echo "**** Cleaning $DIR/*-blank"
    rm -rf $DIR/*-blank
}

blank()
{
   ARCHETYPE_NAME=$1
   ARCHETYPE_DIR=${DIR}/${ARCHETYPE_NAME}
   ARCHETYPE_TMP_DIR="${DIR}/${ARCHETYPE_NAME}-blank"
   ARCHETYPE_TMP_NAME="${ARCHETYPE_NAME}-blank"

cat << EOF

**** Generating blank version of ${ARCHETYPE_NAME} into ${ARCHETYPE_TMP_DIR}
**** Blank artifactId is ${ARCHETYPE_TMP_NAME}

EOF

   cd $TMPDIR

   cp -r ${ARCHETYPE_DIR} ${ARCHETYPE_TMP_DIR}

   cd $ARCHETYPE_TMP_DIR

   mv src/main/resources/META-INF/maven/archetype-metadata-blank.xml src/main/resources/META-INF/maven/archetype-metadata.xml

   perl -pi -e "s/${ARCHETYPE_NAME}/${ARCHETYPE_TMP_NAME}/g" `find . -name pom.xml`

}

ALL=0
CLEAN=0

while getopts “ahcn:” OPTION
do
     case $OPTION in
         a)
             ALL=1
             ;;
         h)
             usage
             exit
             ;;
         c)
             clean
             CLEAN=1
             ;;
         n) 
             NAME=$OPTARG
             ;;
         [?])
             usage
             exit
             ;;
     esac
done

if [ $ALL -eq 1 ]
then
   for archetype in $ARCHETYPES
   do
      blank $archetype
   done
else
   if [ -z "$NAME"] && [ $CLEAN -ne 1 ]
   then
      echo "No archetype name defined"
      usage
      exit
   elif [ -n "$NAME" ]
   then
      blank $NAME
   fi
fi

