Feature: Validate each price to ensure that bid < ask
  Scenario: Validation of bid price less than
    Given FX price feed of spot prices from the market is connected
    When price is picked for an instrument
    Then bid price is less than ask price

Feature: Validate correct commission applied
  Scenario: Validation of commision calcualtion as per rule
    Given FX price feed of spot prices from the market is connected
    When price is picked for an instrument
    And commission calculated
    Then commission is as per rule

Feature: End to End test
  Scenario: Validation of prices
    Given FX price feed of spot prices from the market is connected
    When price is picked for an instrument
    Then the instrument latest price is picked
    And price is rejected for any missing price
    And commission is calculated as per rule