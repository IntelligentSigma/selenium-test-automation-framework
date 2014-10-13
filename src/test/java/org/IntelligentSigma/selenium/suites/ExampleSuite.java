package org.IntelligentSigma.selenium.suites;

import org.IntelligentSigma.selenium.helpers.DriverHelper;
import org.IntelligentSigma.selenium.tests.ExampleTest;
import org.testng.annotations.Test;

/**
 * ExampleSuite
 * This is an example Test Suite. This would normally have a variety of tests in it that all fit into a specific group or
 * category.
 * groups can be used to signify what group or category (groups or categories) that this suites of tests belongs to:
 * (groups = "") or (groups = {"", ""}) The grouping happens in the TestRunner.xml
 * Groups can also be added to each individual test. If the tests are run in more than one group or category, simply add
 * those groups to each tests.
 * The environment setup happens in the beforeClass() method. Creating Data, Logging In, Navigating to special URLs or
 * other prep goes here.
 * You can also add afterClass() to do any clean-up or other special tasks AFTER all the tests have run and the tests
 * suites comes to an end.
 *
 * @author David Mamanakis
 */
@Test(groups = {"exampleSuite"})
public class ExampleSuite extends DriverHelper {
  private ExampleTest exampleTest;

  /**
   * beforeClass
   * Method Override to set up any necessary bits for the tests suites/tests cases
   * Creating data, creating page objects, etc.
   *
   * @throws Exception
   */
  @Override
  protected void beforeClass() throws Exception {
    exampleTest = new ExampleTest(getDriver(), getBrowserSession());
  }

  /**
   * This is a TEST.
   * The Test Suite calls a series of things called @Test
   * Each one will use one or more items from the "Test" class, in this case, "ExampleTest".
   *
   * @throws Exception
   */
  @Test(groups = {"exampleTest"})
  public void runningAnExampleTestFromAnExampleTestSuite() throws Exception {
    exampleTest.searchUsingCommonLib();
  }

  @Override
  public String getSessionId() {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }
}
