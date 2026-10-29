package BasePackage;
import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import org.openqa.selenium.OutputType;

import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;

import Hooks.DriverFactory;
import io.github.bonigarcia.wdm.WebDriverManager;


public class BaseClass {

	public static WebDriver driver;

	public void launchBrowser() throws IOException {
    
		/*The reads the browser from the properties file*/
    String browser=ConfigReader.getProperty("browser");
    if(browser.equalsIgnoreCase("Chrome")) {
    WebDriverManager.chromedriver().setup();
	driver=new ChromeDriver();
	DriverFactory.setDriver(driver);
    }
    
    /*The reads the application URL from the properties file*/
	String url = ConfigReader.getProperty("url");
	driver.get(url);
	
	/*To apply global wait and maximize the browser window*/
	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
	driver.manage().window().maximize();
	
	}
	
	public void getScreenShot() throws IOException {
		
		/*TakesScreenshot class to take a screenshot of the page*/
		TakesScreenshot ts=((TakesScreenshot) driver);
		
		/*File source and destination to store the file*/
		File src=ts.getScreenshotAs(OutputType.FILE);
		File dest=new File("Screenshots/screenshot1.png");
		
		/*FileHandler to copy the source file in to destination location*/
		FileHandler.copy(src, dest);
	}
	
	

}
