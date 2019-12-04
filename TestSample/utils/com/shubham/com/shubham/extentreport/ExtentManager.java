package com.shubham.extentreport;

import org.openqa.selenium.Platform;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Protocol;
import com.aventstack.extentreports.reporter.configuration.Theme;

/**
 * This class contains all the methods related to extent report configuration
 * @author SRajput
 *
 */
public class ExtentManager {

	static ExtentReports extent;
	/**
	 * Method to return extent report instance
	 * @return extent
	 */
	public static ExtentReports getInstance() {
		return extent;
	}


	/**
	 * ----------------------------Extent Report configuration---------------------------------
	 * This method will create an instance of extent report and set few properties for extent report
	 * @param fileName
	 * @return extent
	 */
	public static synchronized ExtentReports createInstance(String extentHtmlFileName) {
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(extentHtmlFileName);

		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);

		extent.setSystemInfo("OS", Platform.getCurrent().toString());
		extent.setSystemInfo("Host Name", "shubham");
		extent.setSystemInfo("User Name", System.getProperty("user.name"));

		htmlReporter.config().setDocumentTitle(" Assignment Report");
		htmlReporter.config().setReportName(" Assignment Report");
		htmlReporter.config().setTheme(Theme.DARK);
		htmlReporter.config().setAutoCreateRelativePathMedia(true);
		htmlReporter.config().setCSS("css-string");
		htmlReporter.config().setEncoding("utf-8");
		htmlReporter.config().setJS("js-string");
		htmlReporter.config().setProtocol(Protocol.HTTPS);
		htmlReporter.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");

		return extent;
	}

}
