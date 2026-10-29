package Hooks;


import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Scenario;



public class Hooks {
	

	    public WebDriver driver = DriverFactory.getDriver();

	    @AfterStep
	    public void addScreenshot(Scenario scenario) {

	    	if(driver !=null) {
	    		try {
	        final byte[] screenshot =
	                ((TakesScreenshot) driver)
	                .getScreenshotAs(OutputType.BYTES);

	        scenario.attach(
	                screenshot,
	                "image/png",
	                scenario.getName()
	        );
	    		}catch(Exception e) {
	    			System.out.print(e);
	    		}
	    }
	    	}
	    
	    
	    @After
	    public void tearDown() {


			// Capture failure details before closing browser


			if (driver != null) {
	            driver.close();
	        }
	    }
	    


}
