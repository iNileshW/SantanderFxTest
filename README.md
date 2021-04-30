Santander Corporate & Investment Banking (FX)
QA Java Developer 2021 - Market Price Handler Test Exercise
Functional requirement:
We would like to connect to a FX price feed of spot prices from the market. With each price, we want to modify it by applying a margin adjustment to each price (like commission). 
With the new price, we then want to make this available to clients/components so they can get the latest price for an instrument whenever they ask for it (history is not required).
You can imagine that there could be a UI to show this price to a real user, but in this exercise, you do not need to write a UI, typically a colleague would be working on this part.
The definition of a Price consists of unique id, instrument name, bid, ask and timestamp. You can assume that the Bid means the sell price (which is lower) and the Ask is the buy price (which is higher).
The market price feed will be given to you in CSV format line by line for EUR/USD, GBP/USD and EUR/JPY, e.g. here are some individual messages (each message could have 1 or more lines in it):
106, EUR/USD, 1.1000,1.2000,01-06-2020 12:01:01:001
…
107, EUR/JPY, 119.60,119.90,01-06-2020 12:01:02:002
…
108, GBP/USD, 1.2500,1.2560,01-06-2020 12:01:02:002
…
109, GBP/USD, 1.2499,1.2561,01-06-2020 12:01:02:100
…
110, EUR/JPY, 119.61,119.91,01-06-2020 12:01:02:110

Other information:
Each message is a CSV string (see above format).
With an incoming price, each will be processed with a margin (add commission) function, assume it is simply  -0.1% on bid, +0.1% on ask (subtract from bid, add to ask). 
Adjusted price will be published to REST endpoint (just show where the endpoint will be, do not need to implement a webserver).

Task:
Use test frameworks of your choice to test the following:
1) That the prices are being processed in sequence and that none are missing
2) That only the latest price for a given instrument is stored (client should not be able to see older prices if a newer one has already been received)
3) Validate each price to ensure that bid < ask and that the commission has been applied correctly
4) Please summarise a strategy of how this could be tested end-end?

Please spend up to 2 hours on the task. Provide java code files in a text document, zip or github.  If you make any assumptions, please add comments in the code or into the document.
<End>



Assumptions:
1. The FX Feed service is connected
2. The commission calculation is fed through to test in the project static values are considered

This is a Java 8 Maven project with Cucumber & JUnit4.
The project is loaded in Git Repository at:
https://github.com/iNileshW/SantanderFxTest

Following are the features & Test scenarios to Test:
Feature: Prices processed in sequence and that none are missing
Scenario: Validation of prices in sequence
Given  FX price feed of spot prices from the market is connected
When price is picked 
Then prices are in sequence
Scenario: Validation of price missing feed rejected
Given  FX price feed of spot prices from the market is connected
When price is picked 
Then feed is rejected for missing price

Scenario: Validation of price feed accepted 
Given  FX price feed of spot prices from the market is connected
When price is picked 
Then feed is accepted for correct price

Feature: Latest price for a given instrument is used
Scenario: Validation of latest instrument price picked 
Given  FX price feed of spot prices from the market is connected
When price is picked for an instrument
Then price picked is latest
And old price is not considered

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
