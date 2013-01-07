JBoss Archetypes Contributing Guide
===================================

Archetype is a Maven project templating toolkit

Basic Steps
-----------

To contribute with Archetypes, clone your own fork instead of cloning the main Archetypes repository, commit your work on topic branches and make pull requests. In detail:

1. [Fork](http://help.github.com/fork-a-repo/) the project.

2. Clone your fork (`git@github.com:<your-username>/jboss-as-archetype.git`).

3. Add an `upstream` remote (`git remote add upstream git@github.com:jboss-jdf/jboss-as-archetype.git`).

4. Get the latest changes from upstream (e.g. `git pull upstream master`).

5. Create a new topic branch to contain your feature, change, or fix (`git checkout -b <topic-branch-name>`).

6. Make sure that your changes follow the General Guidelines below.

7. Commit your changes to your topic branch.

8. Push your topic branch up to your fork (`git push origin  <topic-branch-name>`).

9. [Open a Pull Request](http://help.github.com/send-pull-requests/) with a clear title and description.

If you don't have the Git client (`git`), get it from: <http://git-scm.com/>

General Guidelines
------------------

* We strongly encourage you to discuss your planned archetype on the [dev list](http://www.jboss.org/jdf/forums/jdf-dev/) before starting.

* The archetype should be formatted using the JBoss AS profiles found at <https://github.com/jboss/ide-configs/tree/master/ide-configs>

* More instructions, see the [Guide to Creating Archetypes](http://maven.apache.org/guides/mini/guide-creating-archetypes.html)

License Information and Contributor Agreement
---------------------------------------------

  JBoss Developer Framework is licensed under the Apache License 2.0, as we believe it is one of the most permissive Open Source license. This allows developers to easily make use of the code samples in JBoss Developer Framework. 

  There is no need to sign a contributor agreement to contribute to JBoss Developer Framework. You just need to explicitly license any contribution under the AL 2.0. If you add any new files to JBoss Developer Framework, make sure to add the correct header.

### Java

      /*
       * JBoss, Home of Professional Open Source
       * Copyright <Year>, Red Hat, Inc. and/or its affiliates, and individual
       * contributors by the @authors tag. See the copyright.txt in the 
       * distribution for a full listing of individual contributors.
       *
       * Licensed under the Apache License, Version 2.0 (the "License");
       * you may not use this file except in compliance with the License.
       * You may obtain a copy of the License at
       * http://www.apache.org/licenses/LICENSE-2.0
       * Unless required by applicable law or agreed to in writing, software
       * distributed under the License is distributed on an "AS IS" BASIS,  
       * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
       * See the License for the specific language governing permissions and
       * limitations under the License.
       */

### XML

      <!--
       JBoss, Home of Professional Open Source
       Copyright <Year>, Red Hat, Inc. and/or its affiliates, and individual
       contributors by the @authors tag. See the copyright.txt in the 
       distribution for a full listing of individual contributors.

       Licensed under the Apache License, Version 2.0 (the "License");
       you may not use this file except in compliance with the License.
       You may obtain a copy of the License at
       http://www.apache.org/licenses/LICENSE-2.0
       Unless required by applicable law or agreed to in writing, software
       distributed under the License is distributed on an "AS IS" BASIS,  
       WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
       See the License for the specific language governing permissions and
       limitations under the License.
       -->

### Properties files

       # JBoss, Home of Professional Open Source
       # Copyright 2012, Red Hat, Inc. and/or its affiliates, and individual
       # contributors by the @authors tag. See the copyright.txt in the 
       # distribution for a full listing of individual contributors.
       #
       # Licensed under the Apache License, Version 2.0 (the "License");
       # you may not use this file except in compliance with the License.
       # You may obtain a copy of the License at
       # http://www.apache.org/licenses/LICENSE-2.0
       # Unless required by applicable law or agreed to in writing, software
       # distributed under the License is distributed on an "AS IS" BASIS,  
       # WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
       # See the License for the specific language governing permissions and
       # limitations under the License.


