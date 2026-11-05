Feature: Test Scenarios to validate HomePage functionalities

@homepage @title
Scenario: TC01_Validate the Title of Amazon homepage
Given Launch URL
When Get the title value
Then validate the title of the homepage
|Online Shopping site in India: Shop Online for Mobiles, Books, Watches, Shoes and More - Amazon.in|

@homepage @pincode @Update
Scenario: TC02_Verify updating address and pincode
Given Launch URL
When Click on location pincode
Then verify choose your location popup
When click on enter the pincode and enter "600126"
Then Click on Apply button
And Verify the current location and pincode "Chennai 600126"

  @homepage @search @SelfHealing
  Scenario: TC03_Search Amazon using self-healing locator
    Given Launch URL
    When Search for Amazon product "Laptop"



