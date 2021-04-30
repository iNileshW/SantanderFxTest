package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions
        (
                features = {"src/test/java/features"}
                ,glue = {"stepdefs"}
                ,plugin = {"pretty","html:target/test-output",
                "json:target/json_output/cucumber.json",
                "junit:target/junit_xml/cucumber.xml"
        }
                ,dryRun = false
                ,tags = "@SmokeTest"
                ,monochrome = true
        )


public class TestRunner {
}
