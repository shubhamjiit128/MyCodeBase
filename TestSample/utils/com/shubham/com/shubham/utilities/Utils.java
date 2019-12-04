package com.shubham.utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.Platform;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.IAssert;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.shubham.extentreport.ExtentManager;
import com.shubham.extentreport.ExtentTestManager;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Utils {

	public static WebDriver driver;

	/**
	 * Method to invoke browser and open application url, returns the current driver
	 * instance
	 * 
	 * @param url
	 * @return driver
	 */
	public CommonFunctions commFunc;
	public static String baseUrl = null;
	public static Properties prop = null;
	public static String env = null;
	public static String brand = null;
	

	public static WebDriver createInstance(String browserName) throws Exception {
		driver = null;

		try {
			/*************** Extent Report Instance creation *****************/
			if (ExtentManager.getInstance() == null){
				if (Platform.getCurrent().toString().contains("WIN")) {
					ExtentManager.createInstance(System.getProperty("user.dir") + "\\extentreports\\"+ "ExecutionReport" + ".html");
				}
				else if (Platform.getCurrent().toString().equalsIgnoreCase("MAC")) {
					ExtentManager.createInstance(System.getProperty("user.dir") + "/extentreports/"+ "ExecutionReport" + ".html");
				}
			}
			/********************** Driver Initialization *******************/

			System.out.printf("Opening %s browser.%n", browserName);

			// Invoking browser based on type of browser
			if (browserName.toLowerCase().contains("chrome")) {
				WebDriverManager.chromedriver().setup();
				ChromeOptions options = new ChromeOptions();    
				options.setExperimentalOption("useAutomationExtension", false);
				options.setExperimentalOption("excludeSwitches", 
						Collections.singletonList("enable-automation"));
				driver = new ChromeDriver(options);
			} 
			if (driver != null) {
				driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
				driver.manage().window().maximize();
			}
		} catch (Exception e) {
			System.out.println(browserName + " driver initialization failed");
			throw e;
		}
		
		return driver;
	}


	public synchronized WebDriver invokeBrowser(String url) throws Exception {
		try {
			driver.get(ExcelUtils.getTestDataRowColumnData("Input", url, "value"));
			ExtentTestManager.getTest().log(Status.INFO, "Application url is launced as:"+ExcelUtils.getTestDataRowColumnData("Input", url, "value"));
			return driver;

		} catch (Exception e) {
			throw e;
		}
	}


	public static class CommonFunctions {
		WebDriver driver;
		ExtentTest test;
		FluentWait<WebDriver> fluentWait = null;
		WebDriverWait wait = null;
		WebElement element;

		public CommonFunctions(WebDriver driver) {
			this.driver = driver;
			this.test = ExtentTestManager.getTest();
		}

		/**
		 * Common method for fluent wait
		 * 
		 * @param timeOutInSeconds
		 * @param pollingEveryInMiliSec
		 * @return fluentWait
		 */
		public FluentWait<WebDriver> getFluentWait() {
			fluentWait = new FluentWait<WebDriver>(driver).withTimeout(Constants.SECONDS)
					.pollingEvery(Constants.MILISECONDS).ignoring(NoSuchElementException.class)
					.ignoring(NoSuchElementException.class).ignoring(ElementNotVisibleException.class)
					.ignoring(StaleElementReferenceException.class).ignoring(NoSuchFrameException.class);
			return fluentWait;
		}


		public void safeClick(WebElement element) {
			if (isElementPresent(element)) {
				FluentWait wait = getFluentWait();
				wait.until(ExpectedConditions.elementToBeClickable(element));
				element.click();
			} else {
				ExtentTestManager.getTest().log(Status.FAIL,
						"Element: " + element.getText() + ", is not available on a page - " + element.getLocation());
			}
		}
		
		public void clickUsingJS(WebElement element) {
			try {
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript("arguments[0].click()", element);
			} catch (Exception e) {
				throw (e);
			}
		}

		public boolean isElementPresent(WebElement element) {
			if (element == null)
				throw new IllegalArgumentException("Locator cannot be Null");
			try {
				fluentWait = getFluentWait();
				fluentWait.until(ExpectedConditions.visibilityOf(element));
				return (element.isDisplayed()) ? true : false;
			} catch (Exception e) {
				return false;
			}
		}

		public synchronized void sendWhenReady(WebElement element, String value, int timeout) {
			try {
				wait = new WebDriverWait(driver, timeout);
				WebElement element1 = wait.until(ExpectedConditions.visibilityOf(element));
				element1.sendKeys(String.valueOf(value));
			} catch (Exception e) {
				System.out.println(e);
				throw e;
			}
		}

	}

	/**
	 * This class contains Soft Assertion validation methods and unimplemented
	 * methods.
	 * 
	 * @author SRajput
	 */

	public static class TestSoftAssert extends SoftAssert {

		public List<String> messages = new ArrayList<>();

		@Override
		public void onAssertFailure(IAssert<?> assertCommand, AssertionError ex) {
			messages.add("onAssertFailure");
		}

		@Override
		public void assertAll() {
			try {
				messages.add("assertAll");
				super.assertAll();
			} catch (AssertionError e) {
				throw (e);
			}
		}
	}
}
