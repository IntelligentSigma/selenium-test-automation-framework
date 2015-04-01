package org.IntelligentSigma.selenium.helpers;

import java.util.concurrent.atomic.AtomicInteger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 *
 * @author Brett Hymas
 */
public class RetryFailed implements IRetryAnalyzer {
  private int iMaxCount = 2;
  AtomicInteger count = new AtomicInteger(iMaxCount);

  public Boolean isRetryAvailable() {
    return(count.intValue() > 0);
  }

  @Override
  public boolean retry(ITestResult testResult) {
    Boolean bRetry = false;
    if(isRetryAvailable()) {
      bRetry = true;
      count.decrementAndGet();
    }
    return bRetry;
  }
}