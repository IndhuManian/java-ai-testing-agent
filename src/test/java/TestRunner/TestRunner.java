package TestRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

import BasePackage.RetryAnalyzer;


import io.cucumber.testng.PickleWrapper;
import io.cucumber.testng.FeatureWrapper;

import AI.Listener.AIAfterTestListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(AIAfterTestListener.class)
@CucumberOptions(
        features = "src/test/resources/Features",
        glue = {"StepDefinitions","Hooks"},
        tags = "@homepage",
        plugin = {"pretty",
                "html:target/cucumber-reports.html",
                "rerun:target/failed_scenarios.txt",
                "AI.Collector.FailureCollector"},
        dryRun = false
)


public class TestRunner extends AbstractTestNGCucumberTests {

    @Override
    @Test(
            groups = "cucumber",
            description = "Runs Cucumber Scenarios",
            dataProvider = "scenarios",
            retryAnalyzer = RetryAnalyzer.class
    )
    public void runScenario(
            PickleWrapper pickleWrapper,
            FeatureWrapper featureWrapper) {

        super.runScenario(pickleWrapper, featureWrapper);
    }
    
    
  
}