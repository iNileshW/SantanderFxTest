Feature: End to End test

  @End2End
  Scenario: Validation of prices
    Given FX price feed of spot prices from the market is connected
    When valid price is picked for an instrument
    And commission calculated is correct
    Then commission is as per rule
    And prices are in sequence
    And feed is accepted for correct price
    And bid price is less than ask price
    And price picked is latest