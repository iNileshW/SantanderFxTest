Feature: Validate correct commission applied

  @RegressionTest
  Scenario: Validation of commission calculation as per rule
    Given FX price feed of spot prices from the market is connected
    When valid price is picked for an instrument
    And commission calculated is correct
    Then commission is as per rule

  @RegressionTest
  Scenario: Validation of commission calculation as per rule
    Given FX price feed of spot prices from the market is connected
    When valid price is picked for an instrument
    And commission calculated is not correct
    Then commission is rejected as per rule