Feature: Prices processed in sequence and that none are missing
  @SmokeTest
  Scenario: Validation of prices in sequence
    Given  FX price feed of spot prices from the market is connected
    When price is picked
    Then prices are in sequence

  @SmokeTest
  Scenario: Validation of price missing feed rejected
    Given  FX price feed of spot prices from the market is connected
    When price is picked
    Then feed is rejected for missing price

  @SmokeTest
  Scenario: Validation of price feed accepted
    Given  FX price feed of spot prices from the market is connected
    When price is picked
    Then feed is accepted for correct price
