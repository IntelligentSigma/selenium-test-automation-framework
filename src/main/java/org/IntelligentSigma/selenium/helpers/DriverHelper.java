package org.IntelligentSigma.selenium.helpers;

import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.*;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * DriverHelper
 * Used to create the Selenium WebDriver/RemoteWebDriver and obtain properties
 *
 * @author Eric Pabst
 * @author David Mamanakis
 */
@Listeners({SauceOnDemandTestListener.class})
public class DriverHelper extends ApplicationHelper implements SauceOnDemandSessionIdProvider{

  /**
   * Variables, Constants, Statics, Finals, Commons, Globals, etc
   *
   */
  //public SauceOnDemandAuthentication authentication;
  private BrowserHelper browserHelper;
  private static String workingUrl;
  private static String sauceUserName;
  private static String sauceUserKey;
  private static String browser;
  private static int browserVersion;
  private static String server;
  private static String os;

  /**
   *  DriverHelper
   *  Constructor for Driver Helper
   *
   */
  public DriverHelper() {
    super(null, null);
  }

  /**
   * getBrowserSession
   * Getter for the Browser Session
   *
   * @return  BrowserHelper
   */
  @Override
  protected BrowserHelper getBrowserSession() {
    if (super.getBrowserSession() == null) {
      setBrowserSession(browserHelper.createBrowserSession());
    }
    return super.getBrowserSession();
  }

  /**
   * getDriver
   * Getter for the Driver
   *
   * @return  WebDriver
   */
  @Override
  protected WebDriver getDriver() {
    return getBrowserSession().getDriver();
  }

  /**
   * setupClass
   * Method to start-up the WebDriver - calls beforeClass
   *
   * @param context the context of the test being run (OS, Browser, Version, etc)
   * @param browser What browser the tests is using. Pulled from the TestRunner.xml
   * @param seleniumServerUrl The URL of the Selenium Server. Pulled from the TestRunner.xml
   * @param runLocation If the tests is to be run locally, or on the grid, or on SauceLabs.
   * @param workingUrl This is the environment the tests will run against.
   * @throws Exception
   */
  @BeforeClass(alwaysRun = true)
  @Parameters({"selenium.browser", "selenium.browser.version", "selenium.os", "selenium.server", "run.location", "working.url", "sauce.user.name", "sauce.user.key"})
  public final void setupClass(ITestContext context, @Optional String browser, @Optional int browserVersion,
                               @Optional String os, @Optional String seleniumServerUrl, @Optional String runLocation,
                               @Optional String workingUrl, @Optional String sauceUser, @Optional String sauceKey) throws Exception {
    this.browserHelper = BrowserHelper.getInstance(context, browser, seleniumServerUrl, runLocation);
    this.workingUrl = workingUrl;
    this.sauceUserKey = sauceKey;
    this.sauceUserName = sauceUser;
    this.browser = browser;
    this.browserVersion = browserVersion;
    this.server = seleniumServerUrl;
    this.os = os;
    beforeClass();
    // Make sure one is allocated for this tests class.
    BrowserHelper browserSession = getBrowserSession();
    LOG.info("Using Browser on host= " + browserSession.getTestNodeIP());
  }

  /**
   * getWorkingUrl
   * Returns the Location that we want to run the tests against. This is imported above in setupClass.
   *
   * @return String (workingUrl)
   */
  public String getWorkingUrl() {
    return this.workingUrl;
  }

  /**
   * getSauceUserName
   * Returns the saucelabs username as supplied in the POM or TestRunner.xml.
   *
   * @return String (sauceUserName)
   */
  public String getSauceUserName() {
    return this.sauceUserName;
  }

  /**
   * getSauceUserKey
   * Returns the saucelabs user key as supplied in the POM or TestRunner.xml.
   *
   * @return String (sauceUserKey)
   */
  public String getSauceUserKey() {
    return this.sauceUserKey;
  }

  /**
   * tearDownClass
   * The method to tear-down the WebDriver and clean up after the tests run - calls afterClass
   *
   * @throws Exception
   */
  @AfterClass(alwaysRun = true)
  public final void tearDownClass() throws Exception {
    afterClass();
    LOG.info("Done with testClass=" + getClass().getSimpleName());
  }

  /**
   * beforeClass
   * The method override that can be used to run special preps BEFORE a tests or set of tests are run
   *
   */
  protected void beforeClass() throws Exception {
    // Override this method in the tests class if you need to do work before the class executes
  }

  /**
   * beforeMethod
   * Runs before each tests method. Will reload the page to make sure each tests method starts fresh. It prevents the
   * browser from getting into a bad state.
   *
   */
  @BeforeMethod(alwaysRun = true)
  protected void beforeMethod() throws Exception {
    refresh();
  }

  /**
   * afterClass
   * The method override that can be used to run special clean-ups in this space AFTER a tests/set of tests are run
   *
   */
  protected void afterClass() throws Exception {
    if (isBaseInitialized()) {
      getBrowserSession().quit();
    }
  }

  /**
   * isBaseInitialized
   * This method reports if Browser Session and Driver are both initialized (not null).
   *
   * @return boolean
   */
  protected boolean isBaseInitialized() {
    return browserHelper != null && super.getBrowserSession() != null && getDriver() != null;
  }

  /**
   * createWebDriver
   * Creator of the Web Driver  (Local, Remote, Sauce)
   *
   * @param runLocation The environment that we want the Driver to be initialized for.
   * @param browser The type of browser to be used in the tests.
   * @param serverUrl The location of the environment for the tests.
   * @return webDriver (WebDriver or RemoteWebDriver)
   * @throws MalformedURLException
   */
  protected WebDriver createWebDriver(String runLocation, String browser, String serverUrl) throws MalformedURLException {
    WebDriver webDriver = null;

    if (runLocation.equals(TestingPlatform.REMOTE_GRID.toString())) {
      if ("firefox".equalsIgnoreCase(browser)) {
        webDriver = new RemoteWebDriver(new URL(serverUrl), DesiredCapabilities.firefox());
      }
      else if ("ie".equalsIgnoreCase(browser)) {
        webDriver = new RemoteWebDriver(new URL(serverUrl), DesiredCapabilities.internetExplorer());
      }
      else if ("chrome".equalsIgnoreCase(browser)) {
        webDriver = new RemoteWebDriver(new URL(serverUrl), DesiredCapabilities.chrome());
      }
      else if (browser == null || browser.trim().length() == 0) {
        webDriver = new RemoteWebDriver(new URL(serverUrl), DesiredCapabilities.firefox());
      }
      else {
        throw new IllegalArgumentException("Unknown Browser: " + browser);
      }
    }
    else if (runLocation.equals(TestingPlatform.LOCAL_HOST.toString())) {
      if ("firefox".equalsIgnoreCase(browser)) {

        //The following code will need some modification, but it could/should provide a way to run FIREBUG in our TEST
        //Browsers. Currently, it isn't enabling FireBug.
//        String path = System.getProperty("user.home") + File.separator + "firebug-1.8.4.xpi";
//        File file = new File(path);
//        FirefoxProfile firefoxProfile = new FirefoxProfile();
//
//        try {
//          firefoxProfile.addExtension(file);
//        }
//        catch (IOException e) {
//          e.printStackTrace();
//        }
//
//        firefoxProfile.setPreference("extensions.firebug.currentVersion", "1.8.1"); // Avoid startup screen
//        webDriver = new FirefoxDriver(firefoxProfile);

        webDriver = new FirefoxDriver();
      }
      else if ("ie".equalsIgnoreCase(browser)) {
        webDriver = new InternetExplorerDriver();
      }
      else if ("chrome".equalsIgnoreCase(browser)) {
        String executableName;
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
          executableName = "chromedriver.exe";
        }
        else {
          executableName = "chromedriver";
        }
        System.setProperty("webdriver.chrome.driver", new File(System.getProperty("user.home") + File.separator
            + executableName).getPath());
        webDriver = new ChromeDriver();
      }
      else if (browser == null || browser.trim().length() == 0) {
        webDriver = new FirefoxDriver();
      }
      else {
        throw new IllegalArgumentException("Unknown Browser: " + browser);
      }
    }
    else if (runLocation.equals(TestingPlatform.SAUCE_LABS.toString())) {
      if (getSauceUserName().isEmpty() && getSauceUserKey().isEmpty()) {
        LOG.fatal("No user name or key for saucelabs");
      }

      DesiredCapabilities capabilities = new DesiredCapabilities();
      capabilities.setBrowserName(browser);
      capabilities.setCapability("version", this.browserVersion);
      capabilities.setCapability("platform", Platform.valueOf(this.os));

//      capabilities.setCapability("name", testName);
      String urlURL = "http://" + getSauceUserName() + ":" + getSauceUserKey() + serverUrl;
      webDriver = new RemoteWebDriver(new URL(urlURL), capabilities);
    }
    return webDriver;
  }

  //This factory uses the SauceBrowserDataProvider, which parses the SAUCE_ONDEMAND_BROWSERS environment variable
  //@Factory(dataProviderClass=SauceBrowserDataProvider.class, dataProvider = "sauceBrowserDataProvider", parameters="browserJson")
  public DriverHelper(String browser, int version, String os) {
    super(null);
    this.browser = browser;
    this.browserVersion = version;
    this.os = os;
  }

//  @Override
//  public SauceOnDemandAuthentication getAuthentication() {
//    return this.authentication;  //To change body of implemented methods use File | Settings | File Templates.
//  }

  @Override
  public String getSessionId() {
    return getSessionId();  //To change body of implemented methods use File | Settings | File Templates.
  }
}