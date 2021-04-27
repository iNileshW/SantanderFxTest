Feature: End to End test
  Scenario: Validation of prices
    Given FX price feed of spot prices from the market is connected
    When price is picked for an instrument
    Then the instrument latest price is picked
    And price is rejected for any missing price
    And commission is calculated as per rule