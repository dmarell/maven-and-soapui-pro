/*
 * Developed by Folksam.
 * Copyright (c) Folksam. All rights reserved.
 */
package test;

import org.junit.Test;
import com.eviware.soapui.SoapUIProTestCaseRunner;

public class SoapUITest {
    @Test
    public void runTests() throws Exception {
        SoapUIProTestCaseRunner runner = new SoapUIProTestCaseRunner();
        runner.setProjectFile("Demo-soapui-project.xml");
        runner.setPrintReport(true);
        runner.setOutputFolder("target");
        String environment = System.getProperty("soapui-environment");
        if (environment == null) {
            environment = "DEV";
        }
        System.out.println("Setting SoapUI environment to " + environment);
        runner.setEnvironment(environment);
        runner.run();
    }
}
