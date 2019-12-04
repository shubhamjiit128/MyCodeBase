package com.shubham.utilities;

import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import com.aventstack.extentreports.Status;
import com.shubham.extentreport.ExtentManager;
import com.shubham.extentreport.ExtentTestManager;
import com.shubham.utilities.Utils.TestSoftAssert;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import pageObjects.GooglePage;
import pageObjects.Homepage;

/**
 * Hooks implementation class for Cucumber Steps defined in Feature file
 */

public class Hooks extends Utils {

	static Homepage home;
	static TestSoftAssert softAssert;
	static GooglePage gPage;

	@Before
	public void setUpScenario(Scenario scenario){
		try {
			createInstance("chrome");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@After
	public void afterScenario(Scenario scenario){
		try{
			ExtentManager.getInstance().flush();
			if (driver != null) {
				driver.quit();
				ExtentManager.getInstance().flush();
			}
		}
		catch(Exception e){
		}
	}
}