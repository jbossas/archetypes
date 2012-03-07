package com.mycompany.subsystem.extension;


import org.jboss.as.subsystem.test.AbstractSubsystemBaseTest;

import java.io.IOException;


/**
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 */
public class SubsystemParsingTestCase extends AbstractSubsystemBaseTest {

    public SubsystemParsingTestCase() {
        super(SubsystemExtension.SUBSYSTEM_NAME, new SubsystemExtension());
    }

    @Override
    protected String getSubsystemXml() throws IOException {
        return "<subsystem xmlns=\"" + SubsystemExtension.NAMESPACE + "\">" +
                "</subsystem>";
    }


}
