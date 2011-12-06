#!/bin/sh

# This script converts the readme.md to readme.html.
#

if [ ! -z `which markdown` ]; then
    markdown readme.md -f readme.html
else
    echo markdown cannot be found, skipping generation of readme.html
fi
