package com.shubham.extentreport;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

/**
 * This class contains methods to create/get instance of extent report
 * 
 * @author SRajput
 *
 */
public class ExtentTestManager {

	private static ExtentTest extentTest =null;
	private static ExtentReports extent = ExtentManager.createInstance(System.getProperty("user.dir") + "\\extentreports\\" + "ExecutionReport" + ".html");

	/**
	 * Method to get extent test instance and return
	 * 
	 * @return extent
	 */
	public synchronized static ExtentTest getTest() {
		return extentTest;
	}

	/**
	 * Method to create extent test instance
	 * 
	 * @param name
	 * @param description
	 * @return extent
	 */
	public synchronized static ExtentTest createTest(String name, String description) {
		extentTest = extent.createTest(name, description);
		return getTest();
	}

}
