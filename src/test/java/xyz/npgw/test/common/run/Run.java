package xyz.npgw.test.common.run;

import lombok.extern.slf4j.Slf4j;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlPackage;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.util.List;

@Slf4j
public class Run {

    public static void main(String[] args) {
        XmlSuite suite = new XmlSuite();
        suite.setParallel(XmlSuite.ParallelMode.NONE);

        for (XmlClass c : new XmlPackage("xyz.npgw.test.run.*").getXmlClasses()) {
            System.out.println(c);
            XmlTest t = new XmlTest(suite);
            t.setName(c.getName());
            t.setXmlClasses(List.of(c));
        }

        TestNG testng = new TestNG();
        testng.setXmlSuites(List.of(suite));
        testng.run();
    }
}
