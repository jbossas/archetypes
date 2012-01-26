Release process for Archetypes
==============================

1. Regenerate the blanks by running

        ./generate-blank.sh -ca

   which will update the blank archetypes from the main sources

2. Commit any changes to the blanks

3. Update versions by running

        ./release-utils.sh -u -o <old snapshot version> -n <release version>

4. Commit the version update
5. Tag
    
        git tag -a <release version> -m "Release <release version"
6. Stage the release
        
        ./release-utils.sh -r
7. Reset version numbers to snapshots
        
        ./release-utils.sh -u -o <release version> -n <new snapshot version>
8. Commit this
9. Promote the staged repo