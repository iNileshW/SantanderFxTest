package stepdefs;

import base.BaseClass;
import base.PriceFeed;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

public class PricesSteps extends BaseClass {
    private static String csvTestData = "src/main/testdata/FxFeed.csv";
    PriceFeed priceFeed = new PriceFeed();
    @Given("FX price feed of spot prices from the market is connected")
    public void fx_price_feed_of_spot_prices_from_the_market_is_connected() {
        System.out.println("FX Feed Connected");
    }
    PriceFeed pickedFeed;
    @When("valid price is picked for an instrument")
    public void valid_price_is_picked_for_an_instrument() throws IOException, CsvValidationException {
        priceFeed = pickUpPrice();
        pickedFeed = pickValidPrice(priceFeed);
    }


    @Then("bid price is less than ask price")
    public void bid_price_is_less_than_ask_price() {
        bidaskPriceCheck(priceFeed);
    }

    double askCommission;
    double bidCommission;
    double expectedBidCommission=1.2101;
    double expectedAskCommission=1.3202;

    @When("commission calculated is correct")
    public void commission_calculated_is_correct() {
        bidCommission = pickedFeed.getBid()*1.1;
        askCommission = pickedFeed.getAsk()*1.1;
    }


    @Then("commission is as per rule")
    public void commission_is_as_per_rule() {
        String bid = convertDoubleToString(bidCommission);
        String ask = convertDoubleToString(askCommission);
        assertTrue(bid.equals(Double.toString(expectedBidCommission)));
        assertTrue(ask.equals(Double.toString(expectedAskCommission)));
    }

    public String convertTimeStamp(String s) {

        String datetime = s.substring(0, 19);
        String ms = s.substring(21, 23);
        String newTimeStamp = (datetime + "." + ms);
        return newTimeStamp;
    }

    @Then("price picked is latest")
    public void price_picked_is_latest() throws IOException, CsvException, ParseException {
        PriceFeed latestPriceFeed = pickLatestPrice(priceFeed);
        PriceFeed pickPenUltimatePrice = pickPenUltimatePrice(priceFeed);
//        latestPriceFeed.getFtime();
//        pickPenUltimatePrice.getFtime();

        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss.SSS");
        String newTimeStamp2 = convertTimeStamp(latestPriceFeed.getFtime());
        Date d2 = sdf.parse(newTimeStamp2);
        String newTimeStamp1 = convertTimeStamp(pickPenUltimatePrice.getFtime());
        Date d1 = sdf.parse(newTimeStamp1);
        Timestamp t1 = new java.sql.Timestamp(d1.getTime());
        int i = d1.compareTo(d2);
        Assert.assertTrue("Test Fail for latest time",i<0);
    }

    @Then("old price is not considered")
    public void old_price_is_not_considered() {

    }

    @When("price is picked")
    public void price_is_picked() {
        priceFeed = pickUpPrice();
    }

    @Then("prices are in sequence")
    public void prices_are_in_sequence() {
        sequenceCheck(priceFeed);
    }

    @Then("feed is rejected for missing price")
    public void feed_is_rejected_for_missing_price() throws IOException, CsvException {
        misingFieldCheck(priceFeed);
    }


    @Then("feed is accepted for correct price")
    public void feed_is_accepted_for_correct_price() {
        sequenceCheck(priceFeed);
    }

//    @And("commission calculated is correct")
//    public void commissionCalculatedIsCorrect() {
//
//    }

    @And("commission calculated is not correct")
    public void commissionCalculatedIsNotCorrect() {
        bidCommission = pickedFeed.getBid()*1.2;
        askCommission = pickedFeed.getAsk()*1.2;
    }

    @Then("commission is rejected as per rule")
    public void commissionIsRejectedAsPerRule() {
        String bid = convertDoubleToString(bidCommission);
        String ask = convertDoubleToString(askCommission);

        assertFalse(bid.equals(Double.toString(expectedBidCommission)));
        assertFalse(ask.equals(Double.toString(expectedAskCommission)));
    }
}