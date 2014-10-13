package org.IntelligentSigma.selenium.pageObjects;

import org.IntelligentSigma.selenium.helpers.ApplicationHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import static org.testng.Assert.assertEquals;

/**
 * ExampleGoogle Page Object
 * The following methods are all "part of the page".
 * We define the buttons, links, text locations and anything else we want to interact with on the page in this Page Object.
 * For instance:
 * The Strings defined here are all CSS Locators for the Objects on the Google Page.
 * We also "extend" the Application Helper, which does anything that is COMMON to ALL tests.
 *
 * @author David Mamanakis
 */
public class ExampleGoogle extends ApplicationHelper {

  /**
   * CSS Locators for the elements on the Google Search Page.
   */
  private final String inputTextBox = "#gsr #mngb #gbq #gbqfw #gbqff #gbqfqw #sb_ifc0 #gbqfq";
  private final String searchButton = "#gsr #mngb #gbq #gbqfw #gbqfba";
  //private final String searchButton = "#gsr #mngb #gbq #gbqfw #gbqfb";
  private final String searchResultsList = "#gsr #main #appbar #topabar #resultStats";
  private WebDriver driver;

  /**
   * ExampleGoogle
   * Constructor for the Page Object which makes the DRIVER available to you if you need it.
   *
   * @param driver the WebDriver
   */
  public ExampleGoogle(WebDriver driver) {
    super(driver);
    this.driver = driver;
  }

  /**
   * clickSearchButton
   * This clicks on the Search Button on the Main Page to get to the Search Page
   *
   * @return New Page Object "SearchPage"
   */
  public ExampleGoogleResults clickSearchButton() {
    clickAndWaitForElementPresent(searchButton, searchResultsList);
    String stats = "";
    int counter = 0;
    while (stats.isEmpty()) {
      stats = getText(searchResultsList);
      if (stats.isEmpty()) {
        animationWait(1000);
        counter++;
      }
      if (counter > 4) {
        break;
      }
    }
    return PageFactory.initElements(getDriver(), ExampleGoogleResults.class);
  }

  /**
   * verifyGoogleMainPageTitle
   * This will verify the Page Title on google.com
   *
   * @param mainTitle the expected main title of the Google web page
   */
  public void verifyGoogleMainPageTitle(String mainTitle) {
    String title = driver.getTitle();
    assertEquals(title, mainTitle, "EXPECTED: " + mainTitle + " ACTUAL: " + title);
  }

  /**
   * inputTextIntoSearchBox
   * This will enter the requested test string into Google
   */
  public void inputTextIntoSearchBox(String searchText) {
    setInputValue(inputTextBox, searchText);
  }
}
