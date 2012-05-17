Sync repositories 
==============================

## Warning

1. Before to start make sure to have the latest updates from repositories
2. Strongly recomended to create a separated branch for it.

## as-archetypes 

1. Download as-archetypes and as-quickstarts repositories
2. Run diff -ur oldfolder newfolder > patch.diff and patch -p**N** < patch.diff. Where **N** is the number of path prefixes starting from **-p0**. 


For example:

		diff -ur /path/to/as-archetypes/jboss-html5-mobile-archetype/src/main/resources/archetype-resources/src /path/to/as-quickstarts/kitchensink-html5-mobile/src > patch.diff

		cd /path/to/as-archetypes/

		patch -p4 < ../patch.diff

3. Be careful before commit changes, because the archetypes make use of some variables, like this:

		set( $symbol_pound = '#' )
		set( $symbol_dollar = '$' )
		set( $symbol_escape = '\' )


Release process for Archetypes
==============================

Warning: Announce at aerogear-dev that the release process will begin


1. Regenerate the blanks by running

        ./generate-blank.sh -ca

   which will update the blank archetypes from the main sources

2. Commit any changes to the blanks

3. Update versions by running

        ./release-utils.sh -u -o <old snapshot version> -n <release version>

4. Commit the version update
5. Stage the release
        
        ./release-utils.sh -r

6. Communicate with the team

7. Wait for the feedback from tests

8. Tag
    
        git tag -a <release version> -m "Release <release version"


9. Reset version numbers to snapshots
        
        ./release-utils.sh -u -o <release version> -n <new snapshot version>
10. Commit this
11. Promote the staged repo