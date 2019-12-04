package pageObjects;


import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.shubham.extentreport.ExtentManager;
import com.shubham.extentreport.ExtentTestManager;
import com.shubham.utilities.Utils;

public class GooglePage extends Utils {

	WebDriver driver;
	ExtentTest test;
	TestSoftAssert softAssert;

	public GooglePage(WebDriver driver, TestSoftAssert softAssert) {
		this.test = ExtentTestManager.getTest();
		this.driver = driver;
		this.softAssert = softAssert;
		PageFactory.initElements(driver, this);
		commFunc = new CommonFunctions(driver);
	}

	@FindBy(xpath = "//input[@type='text']")
	public WebElement googleSearchBox;

	@FindBy(xpath = "//div[@id='center_col']//h3//span")
	public List<WebElement> articles;





	public GooglePage navigateToGoogle() throws Exception
	{
		try {
			driver.get("https://www.google.com");
			test.log(Status.INFO, "User is navigated to Google homepage.");
		} catch (Exception e) {
			test.log(Status.FAIL, "Failed to navigate to Google homepage.");
			throw e;
		}
		return this;
	}

	public GooglePage searchNewsArticle(String news) throws Exception
	{
		try {
			commFunc.sendWhenReady(googleSearchBox,news,5);
			googleSearchBox.sendKeys(Keys.ENTER);
			test.log(Status.INFO, "User is able to search news in google as: "+news);
		} catch (Exception e) {
			test.log(Status.FAIL, "Failed to search news.");
			throw e;
		}
		return this;
	}

	public GooglePage validateArticle(String news) throws Exception
	{
		try {
			List<WebElement> newsArticles = new ArrayList();
			int count = 0;
			for(WebElement elem: articles){
				String article = elem.getText();
				String []arr = article.split("");
				String []newsArr = news.split("");
				for(String str:arr){
					for(String str1:newsArr){
						if(str1.equalsIgnoreCase(str)){
							count++;
							break;
						}
						break;
					}
				}
			}
			if(count>=6){
				System.out.println("Count is: "+count);
				test.log(Status.PASS, "News is validated successfully on Google search Page.");
			}
			else{
				test.log(Status.FAIL, "News is not authenticated successfully.");
				throw new Exception();
			}
		} catch (Exception e) {
			test.log(Status.FAIL, "Failed to validate news.");
			throw e;
		}
		/*finally{
			ExtentManager.getInstance().flush();
		}*/
		return this;
	}



}
