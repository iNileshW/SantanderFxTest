package stepdefs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class PricesSteps {

    @Given("FX price feed of spot prices from the market is connected")
    public void fx_price_feed_of_spot_prices_from_the_market_is_connected() {
        System.out.println("FX Feed Connected");
    }

    @When("price is picked for an instrument")
    public void price_is_picked_for_an_instrument() {

    }


    @Then("bid price is less than ask price")
    public void bid_price_is_less_than_ask_price() {

    }

    @When("commission calculated")
    public void commission_calculated() {

    }

    @Then("commission is as per rule")
    public void commission_is_as_per_rule() {

    }

    @Then("price picked is latest")
    public void price_picked_is_latest() {

    }

    @Then("old price is not considered")
    public void old_price_is_not_considered() {

    }

    @When("price is picked")
    public void price_is_picked() {

    }

    @Then("prices are in sequence")
    public void prices_are_in_sequence() {

    }

    @Then("feed is rejected for missing price")
    public void feed_is_rejected_for_missing_price() {

    }


    @Then("feed is accepted for correct price")
    public void feed_is_accepted_for_correct_price() {

    }

}