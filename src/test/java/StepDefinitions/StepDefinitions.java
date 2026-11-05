package StepDefinitions;


import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.IOException;
import java.util.List;

import POM.HomePage;
import BasePackage.BaseClass;

public class StepDefinitions extends BaseClass {
	
	BaseClass bClass=new BaseClass();
	HomePage test1=new HomePage();
	 
	@Given("Launch URL")
	public void launch_url() throws IOException, InterruptedException {
	bClass.launchBrowser();
	   
	}
	@When("Get the title value")
	public void get_the_title_value() {
		test1.getTitle(); 
	}
	@Then("validate the title of the homepage")
	public void validate_the_title_of_the_homepage(DataTable dtable) throws IOException {
		List<List<String>> list = dtable.asLists(String.class);
		   
	    for (List<String> data : list) {
	    boolean titleValue=test1.verifyTitle(data.get(0));
	    if(titleValue == true) {
	    	System.out.println("The title of the home page is verified successfully");
	    	
	    }
	    } 
	}
	
	@When("Click on location pincode")
	public void click_on_location_pincode() {
	   test1.clickPinCode();
	}
	@Then("verify choose your location popup")
	public void verify_choose_your_location_popup() {
	    test1.verifyChooseYourLocation();
	}
	@When("click on the address {string}")
	public void click_on_the_address(String string) {
	    test1.clickLocation(string);
	}
	@Then("Verify the current location and pincode {string}")
	public void verify_the_current_location_and_pincode(String string) {
	   test1.verifyLocation(string);
	}

	@When("click on enter the pincode and enter {string}")
	public void click_on_enter_the_pincode_and_enter(String string) {
		test1.enterPincode(string);
	}
	
	@Then("Click on Apply button")
	public void Click_on_Apply_button() {
		test1.clickApply();
	}

	@When("Search for Amazon product {string}")
	public void searchForAmazonProduct(String product) {
		test1.searchAmazonProduct(product);
	}

}
