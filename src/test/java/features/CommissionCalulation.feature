Feature: Validate correct commission applied
  Scenario: Validation of commision calcualtion as per rule
    Given FX price feed of spot prices from the market is connected
    When price is picked for an instrument
    And commission calculated
    Then commission is as per rule