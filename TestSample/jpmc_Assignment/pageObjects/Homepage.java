package pageObjects;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.shubham.extentreport.ExtentTestManager;
import com.shubham.utilities.Utils;

public class Homepage extends Utils {

	WebDriver driver;
	ExtentTest test = null;
	TestSoftAssert softAssert;
	public Homepage(WebDriver driver, TestSoftAssert softAssert) {
		this.test = ExtentTestManager.getTest();
		this.driver = driver;
		this.softAssert = softAssert;
		PageFactory.initElements(driver, this);
		commFunc = new CommonFunctions(driver);
	}

	@FindBy(xpath = "(//div[@class='fc-item__content ']//div[@class='fc-item__header']//span[contains(@class,'headline-text')])[1]")
	public WebElement newArticle;

	public String getNewArticle()
	{
		String text = "";
		try {
			text = newArticle.getText();
			test.log(Status.INFO, "Fetched first news article as: "+text);
		} catch (Exception e) {
			test.log(Status.FAIL, "Failed to fetch new article.");
		}
		return text;
	}
}
