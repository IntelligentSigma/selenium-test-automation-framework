package org.IntelligentSigma.selenium.pageObjects;

import org.IntelligentSigma.selenium.helpers.ApplicationHelper;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

/**
 * ExampleGoogleResults Page Object
 * The following methods are all "part of the page".
 * We define the buttons, links, text locations and anything else we want to interact with on the page in this Page Object.
 * For instance:
 * The Strings defined here are all CSS Locators for the Objects on the Search Results Page.
 * We also "extend" the Application Helper, which does anything that is COMMON to ALL tests.
 *
 * @author David Mamanakis
 */
public class ExampleGoogleResults extends ApplicationHelper {

  /**
   * CSS Locators for the elements on the Google Search Results Page.
   */
  public static final String searchResult = "#gsr #main #rcnt #center_col #search #rso .g .r a";
  private WebDriver driver;

  /**
   * ExampleGoogleResults
   * Constructor for the Page Object which makes the DRIVER available to you if you need it.
   *
   * @param driver the WebDriver
   */
  public ExampleGoogleResults(WebDriver driver) {
    super(driver);
    this.driver = driver;
  }

  /**
   * verifySearchResultsPageTitle
   * This method verifies the Page Title for the Search Results Page
   *
   * @param searchResultsTitle the Title of the Search Results page
   */
  public void verifySearchResultsPageTitle(String searchResultsTitle) {
    String resultsTitle = driver.getTitle();
    if (!resultsTitle.equalsIgnoreCase(searchResultsTitle)) {
      animationWait(3000);
      resultsTitle = driver.getTitle();
    }
    Assert.assertTrue(resultsTitle.equalsIgnoreCase(searchResultsTitle), "EXPECTED: " + searchResultsTitle + " ACTUAL: " + resultsTitle);
  }

  /**
   * verifySearchResults
   * This method verifies the Name you searched for exists in the results on the page
   *
   * @param string the searched for results
   */
  public void verifySearchResults(String string) {
    String results = getText(searchResult);
    Assert.assertTrue(results.contains(string), "EXPECTED: " + string + " ACTUAL: " + results);
  }
}
