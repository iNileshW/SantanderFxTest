Feature: Validate each price to ensure that bid < ask

  @SmokeTest
  Scenario: Validation of bid less than ask price
    Given FX price feed of spot prices from the market is connected
    When valid price is picked for an instrument
    Then bid price is less than ask price