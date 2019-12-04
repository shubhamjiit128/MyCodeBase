package stepDefinition;

import com.shubham.extentreport.ExtentTestManager;
import com.shubham.utilities.Utils;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import pageObjects.GooglePage;
import pageObjects.Homepage;

public class StepDefinition extends Utils {
	static Homepage home;
	static TestSoftAssert softAssert;
	String article = "";
	static GooglePage gPage;

	static {
		softAssert = new TestSoftAssert();
		ExtentTestManager.createTest("Validate news authenticity", "Validate news authenticity");
		ExtentTestManager.getTest().assignCategory("Assignment JPMC");
		home = new Homepage(driver, softAssert);
		gPage = new GooglePage(driver, softAssert);
	}

	@Given("I navigate to Website (.*)")
	public void i_navigates_to_name(String urlVal) {
		// Write code here that turns the phrase above into concrete actions
		try {
			invokeBrowser(urlVal);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@And("get the first news article")
	public void get_the_first_news_article() {
		// Write code here that turns the phrase above into concrete actions
		try{
			article = home.getNewArticle();
		}
		catch(Exception e){
			throw e;
		}
	}

	@When("I navigate to google")
	public void i_navigate_to_google() throws Exception {
		// Write code here that turns the phrase above into concrete actions
		try {
			gPage.navigateToGoogle();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		}

	}

	@When("search for new article")
	public void search_for_new_article() throws Exception {
		// Write code here that turns the phrase above into concrete actions
		try {
			gPage.searchNewsArticle(article);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		}
	}

	@Then("I validate that the news is true")
	public void i_validate_that_the_news_is_true() throws Exception {
		// Write code here that turns the phrase above into concrete actions
		try {
			gPage.validateArticle(article);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		}
	}

}
