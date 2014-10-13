package org.IntelligentSigma.selenium.helpers;

/**
 * Testing Platform Enum
 * Contains the various testing platforms. Currently, they are REMOTE (a local GRID), LOCAL (your local machine) and
 * SAUCE (sauceLabs)
 *
 * @author Eric Pabst
 * @author David Mamanakis
 */
public enum TestingPlatform {

  /**
   * This is the section that contains your environments
   */
  REMOTE_GRID("REMOTE"),
  LOCAL_HOST("LOCAL"),
  SAUCE_LABS("SAUCE");

  /**
   * The string value of your environment.
   */
  private final String value;

  /**
   * Setter for the string value of your environment
   *
   * @param value
   */
  private TestingPlatform(String value) {
    this.value = value;
  }

  /**
   * Converter to set the value as a string.
   *
   * @return
   */
  @Override
  public String toString() {
    return value;
  }
}
