package com.acme.corp.tracker.extension;


import org.jboss.as.subsystem.test.AbstractSubsystemBaseTest;

import java.io.IOException;


/**
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 */
public class SubsystemParsingTestCase extends AbstractSubsystemBaseTest {

    public SubsystemParsingTestCase() {
        super(TrackerExtension.SUBSYSTEM_NAME, new TrackerExtension());
    }

    @Override
    protected String getSubsystemXml() throws IOException {
        return readResource("subsystem.xml");
    }
}
