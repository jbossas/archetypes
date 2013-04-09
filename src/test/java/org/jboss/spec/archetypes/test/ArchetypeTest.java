/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
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
package org.jboss.spec.archetypes.test;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.maven.it.VerificationException;
import org.apache.maven.it.Verifier;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.PlexusContainerException;
import org.codehaus.plexus.component.repository.exception.ComponentLookupException;
import org.junit.Test;

/**
 * @author Rafael Benevides
 * 
 */
public class ArchetypeTest {

    private static Log log = LogFactory.getLog(ArchetypeTest.class);

    private String outputDir = System.getProperty("outPutDirectory");

    private String testOutputDirectory = System.getProperty("testOutputDirectory");

    private String baseDir = System.getProperty("basedir");

    @Test
    public void testArchetypes() throws Exception {
        File baseDirFile = new File(baseDir);
        for (File file : baseDirFile.listFiles()) {
            if (file.isDirectory() && isMavenProject(file)) {
                Reader reader = new FileReader(new File(file, "pom.xml"));
                try {
                    MavenXpp3Reader xpp3Reader = new MavenXpp3Reader();
                    Model model = xpp3Reader.read(reader);

                    installArchetype(file, model);

                    executeCreateArchetype(model, false);
                    executeCreateArchetype(model, true);
                } finally {
                    reader.close();
                }
            }
        }
    }

    /**
     * @param baseDir
     * @param model
     * @throws VerificationException
     */
    private void installArchetype(File baseDir, Model model) throws VerificationException {
        log.info("Installing Archetype " + model);
        Verifier installer = new Verifier(baseDir.getAbsolutePath());
        installer.setLogFileName("install.log");
        installer.executeGoal("install");

        // Remove install.log from inside archetype
        new File(baseDir, "install.log").delete();
    }

    /**
     * @param file
     * @return
     */
    private boolean isMavenProject(File dir) {
        return new File(dir, "pom.xml").exists();
    }

    /**
     * @param archetypeVersion
     * @throws ComponentLookupException
     * @throws PlexusContainerException
     */
    private void executeCreateArchetype(Model model, boolean eap) throws Exception {
        String archetypeWithEnterprise = model + (eap ? " - Enterprise" : "");
        log.info("Creating project from Archetype: " + archetypeWithEnterprise);
        String goal = "org.apache.maven.plugins:maven-archetype-plugin:2.2:generate";
        Properties properties = new Properties();
        if (eap) {
            properties.put("enterprise", "true");
        }
        properties.put("archetypeGroupId", model.getGroupId());
        properties.put("archetypeArtifactId", model.getArtifactId());
        properties.put("archetypeVersion", model.getVersion());
        properties.put("groupId", "org.jboss.as.quickstarts");
        String artifactId = System.currentTimeMillis() + "-" + model.toString().replaceAll("[^a-zA-Z_0-9]", "") + (eap ? "-eap" : "");
        properties.put("artifactId", artifactId);
        properties.put("version", "0.0.1-SNAPSHOT");
        Verifier verifier = new org.apache.maven.it.Verifier(outputDir);
        verifier.setAutoclean(false);
        verifier.setSystemProperties(properties);
        verifier.setLogFileName(artifactId + "-generate.txt");
        verifier.executeGoal(goal);

        log.info("Building project from Archetype: " + archetypeWithEnterprise);
        Verifier buildVerifier = new Verifier(outputDir + File.separator + artifactId);
        buildVerifier.addCliOption("-s " + testOutputDirectory + File.separator + "settings-all.xml");
        buildVerifier.executeGoal("compile"); // buildVerifier log is inside each project
    }
}
