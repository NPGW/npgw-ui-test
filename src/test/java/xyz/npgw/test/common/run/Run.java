package xyz.npgw.test.common.run;

import lombok.extern.slf4j.Slf4j;
import org.testng.TestNG;
import org.testng.xml.XmlPackage;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.util.List;

@Slf4j
public class Run {

    public static void main(String[] args) {
        String suiteXml = System.getProperty("testng.suiteXml", "config/testng.xml");

        TestNG testng = new TestNG();
        testng.setTestSuites(List.of(suiteXml));

        testng.run();
        System.exit(testng.getStatus());

    }
}
