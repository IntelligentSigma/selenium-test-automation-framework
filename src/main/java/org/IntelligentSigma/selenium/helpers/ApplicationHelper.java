package org.IntelligentSigma.selenium.helpers;

import org.IntelligentSigma.selenium.BaseSeleniumPage;
import org.IntelligentSigma.selenium.pageObjects.ExampleGoogle;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * ApplicationHelper
 * This class is a bit of "common to everything" methods.
 *
 * @author Eric Pabst
 * @author David Mamanakis
 */
public class ApplicationHelper extends BaseSeleniumPage {

  /**
   * Variables, Statics, Commons, etc
   * etc
   *
   */
  protected static final Logger LOG = Logger.getLogger(ApplicationHelper.class);
  protected WebDriver driver;
  private BrowserHelper browserHelper;

  /**
   * ApplicationHelper Constructor
   *
   * @param webDriver initializing the WebDriver
   * @param browserHelper initialising the WebBrowser
   */
  public ApplicationHelper(WebDriver webDriver, BrowserHelper browserHelper) {
    super(webDriver);

    this.browserHelper = browserHelper;
    this.driver = webDriver;
  }

  /**
   * ApplicationHelper Constructor
   *
   * @param webDriver the webDriver
   */
  public ApplicationHelper(WebDriver webDriver) {
    super(webDriver);

    this.driver = webDriver;
  }

  /**
   * getBrowserSession
   * Will return the Browser Session
   *
   * @return BrowserHelper
   */
  protected BrowserHelper getBrowserSession() {
    return browserHelper;
  }

  /**
   * setBrowserSession
   * Will set the Browser Session to be retrieved later.
   *
   * @param browserSession this is the actual browser
   */
  protected void setBrowserSession(BrowserHelper browserSession) {
    this.browserHelper = browserSession;
  }

  /**
   * gotoTestLocation
   * This is the GOTO method to take the user to the Google page.
   *
   * @param exampleGoogle the Google Page Object
   * @param url the destination URL for testing
   * @throws Exception
   */
  public void gotoTestLocation(ExampleGoogle exampleGoogle, String url) throws Exception {
    launchURL(driver, url);
    PageFactory.initElements(driver, exampleGoogle);
  }

  /**
   * animationWait
   * This is a general generic WAIT for when your animation needs a second, if "waitForElementPresent" and or
   * "waitForJQueryToBeInactive" or other "page loading wait" doesn't work
   *
   * @param waitTime the specific amount of time to wait: 1000 = 1 second
   */
  protected void animationWait(int waitTime) {
    try{Thread.sleep(waitTime);}catch(Exception ex){}
  }
}
