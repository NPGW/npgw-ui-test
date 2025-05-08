package xyz.npgw.test.common.base;

import lombok.extern.log4j.Log4j2;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;
import org.testng.internal.annotations.IgnoreListener;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

@Log4j2
public class RetryListener extends IgnoreListener implements IAnnotationTransformer {

    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        super.transform(annotation, testClass, testConstructor, testMethod);
        annotation.setRetryAnalyzer(RetryAnalyzer.class);
    }
}
