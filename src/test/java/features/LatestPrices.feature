Feature: Latest price for a given instrument is used
  @SmokeTest
  Scenario: Validation of latest instrument price picked
    Given  FX price feed of spot prices from the market is connected
    When valid price is picked for an instrument
    Then price picked is latest