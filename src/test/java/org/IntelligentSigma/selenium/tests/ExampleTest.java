package org.IntelligentSigma.selenium.tests;

import org.IntelligentSigma.selenium.helpers.ApplicationHelper;
import org.IntelligentSigma.selenium.helpers.BrowserHelper;
import org.IntelligentSigma.selenium.helpers.DriverHelper;
import org.IntelligentSigma.selenium.pageObjects.ExampleGoogle;
import org.IntelligentSigma.selenium.pageObjects.ExampleGoogleResults;
import org.openqa.selenium.WebDriver;

/**
 * ExampleTest
 * This class holds the actual tests. You can define the tests here by including the methods included in the Page Objects
 * (in this case the ExampleGoogle page objects)
 * You can define your variables, as seen below with the initialization of the 4 Strings and the Page Object.
 * When you are done, you have a test that reads simply: goto google, verify google's main page title, input search criteria,
 * click the search button, verify the results... etc.
 * It makes the tests very easy to follow, easy to understand and easier to debug.
 * All verifications also happen in the Page Object. You call them here, like "verifySearchResultsPageTitle" and pass in
 * the values you want to check. This keeps everything "dry", "clean" and "simple". Reusable code.
 *
 * @author David Mamanakis
 */
public class ExampleTest extends ApplicationHelper {

  /**
   * ExampleTest
   * Constructor for the class.
   *
   * @param driver instantiation the driver for your requested settings
   * @param browserSession instantiation the browser session for your requested settings
   */
  public ExampleTest(WebDriver driver, BrowserHelper browserSession) {
    super(driver, browserSession);
  }

  /**
   * searchUsingCommonLib
   * This tests runs against Google Search page and it uses the Common Library to do it.
   * You will notice there are a couple different ways that we create a Page Object.
   * They both work equally well.
   * The first is to simply "new up" the page.
   * EXAMPLE: Google google = new Google(driver);
   * The second is to create the page in the page object, after an action is performed, like clicking on a button.
   * EXAMPLE: SearchResults searchResults = google.clickDoSearchButton(); which uses
   * PageFactory.initElements(getDriver(), ExampleGoogleResults.class);
   * The second method is a little "cleaner".
   * BUT the "gotoTestLocation" method would return any page object, updated.
   *
   * @throws Exception
   */
  public void searchUsingCommonLib() throws Exception {
    DriverHelper driverHelper = new DriverHelper();

    String mainTitle = "Google";
    String searchResultsTitle = "1930 Ford Model A - Google Search";
    String searchResults = "Wikipedia";
    String searchString = "1930 Ford Model A";
    ExampleGoogle exampleGoogle = new ExampleGoogle(driver);
    String url = driverHelper.getWorkingUrl();

    //Go to the starting point
    gotoTestLocation(exampleGoogle, url);

    //Verify the user is on the correct page, fail if it is NOT the right page
    exampleGoogle.verifyGoogleMainPageTitle(mainTitle);

    //If it is the right page, we continue to execute actions against the page
    exampleGoogle.inputTextIntoSearchBox(searchString);
    ExampleGoogleResults exampleGoogleResults = exampleGoogle.clickSearchButton();

    //Verify that the right page is achieved and that our target exists on that page
    exampleGoogleResults.verifySearchResultsPageTitle(searchResultsTitle);
    exampleGoogleResults.verifySearchResults(searchResults);
  }
}
