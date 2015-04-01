package org.IntelligentSigma.selenium.helpers;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Iterator;
import org.testng.IAnnotationTransformer;
import org.testng.IRetryAnalyzer;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.ITestAnnotation;

/**
 *
 * @author Brett Hymas
 */
public class RetryListener implements IAnnotationTransformer, ITestListener {
  @Override
  public void transform(ITestAnnotation annotation, Class testClass, Constructor constructor, Method method) {
    IRetryAnalyzer retry = annotation.getRetryAnalyzer();

    if(retry == null) {
      annotation.setRetryAnalyzer(RetryFailed.class);
    }
  }

  public void onTestStart(ITestResult itr) {}

  public void onTestSuccess(ITestResult itr) {}

  @Override
  public void onTestFailure(ITestResult itr) {
    if(itr.getMethod().getRetryAnalyzer() != null) {
      RetryFailed retry = (RetryFailed)itr.getMethod().getRetryAnalyzer();

      if(retry.isRetryAvailable()) {
        itr.setStatus(ITestResult.SKIP);
      } else {
        itr.setStatus(ITestResult.FAILURE);
      }
      Reporter.setCurrentTestResult(itr);
    }
  }

  public void onTestSkipped(ITestResult itr) {}

  public void onTestFailedButWithinSuccessPercentage(ITestResult itr) {}

  public void onStart(ITestContext itc) {}

  @Override
  public void onFinish(ITestContext itc) {
    Iterator<ITestResult> failedTests = itc.getFailedTests().getAllResults().iterator();
    while(failedTests.hasNext()) {
      ITestResult test = failedTests.next();
      ITestNGMethod method = test.getMethod();
      if(itc.getFailedTests().getResults(method).size() > 1) {
        failedTests.remove();
      } else {
        if(itc.getPassedTests().getResults(method).size() > 0) {
          failedTests.remove();
        }
      }
    }
  }
}