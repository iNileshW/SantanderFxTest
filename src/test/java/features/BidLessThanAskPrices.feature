Feature: Validate each price to ensure that bid < ask

  @RegressionTest
  Scenario: Validation of bid price less than
    Given FX price feed of spot prices from the market is connected
    When price is picked for an instrument
    Then bid price is less than ask price