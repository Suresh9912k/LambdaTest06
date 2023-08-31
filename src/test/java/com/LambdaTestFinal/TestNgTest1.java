/*
 * 
 */

package com.LambdaTestFinal;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class TestNgTest1 {

	// public WebDriver driver;
	private static RemoteWebDriver driver = null;
	public WebDriverWait wait;
	public SoftAssert softassert;

	public String username = "suresh9912k";
	public String accesskey = "FbfD6lLX0RxV2gq7iWZjlHELutWCWDpvdNRNhOov0PBiUIS2tX"; // New Access Key
	public String gridURL = "@hub.lambdatest.com/wd/hub";

	@Parameters({ "Browser", "Version", "Platform", "testName" })
	@BeforeClass
	public void setUp(String browser, String version, String platform, String testname) throws Exception {
		DesiredCapabilities capabilities = new DesiredCapabilities();

		capabilities.setCapability("build", "MyBuild 6.0");
		capabilities.setCapability("name", testname);

		capabilities.setCapability("platform", platform); 															
		capabilities.setCapability("browserName", browser);
		capabilities.setCapability("version", version);

		capabilities.setCapability("network", true);
		capabilities.setCapability("visual", true);
		capabilities.setCapability("video", true);
		capabilities.setCapability("console", true);

		System.out.println("Desired Caps: " + capabilities);
		try {
			driver = new RemoteWebDriver(new URL("https://" + username + ":" + accesskey + gridURL), capabilities);
		} catch (MalformedURLException e) {
			System.out.println("Invalid grid URL");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// ----------------------

	// 1st Test Scenario-1
	@Test
	@Parameters({ "URL" })
	public void Test_Scenario_01(String url) {

		// step:1 Load the AUT
		driver.get(url);

		wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		// SoftAssert class---
		softassert = new SoftAssert();

		// get Title---
		String expected_title = "Selenium Grid Online | Run Selenium Test On Cloud--";
		String actual_title = driver.getTitle();

		softassert.assertEquals(actual_title, expected_title, " ****Title Not matched****");

	}

	// 2nd Test Scenario-02
	@Test
	public void Test_Scenario_02() {

		// click on the "Check Box Demo"
		driver.findElement(By.linkText("Checkbox Demo")).click();

		// click on the "Single Check Box"
		WebElement cb1 = driver.findElement(By.id("isAgeSelected")); // read WebElement of 'single check box'

		// validate check box is selected---

		cb1.click(); // click on the check box

		if (cb1.isSelected()) {
			System.out.println("Check box is Selected.");
		} else {
			cb1.click();
			System.out.println("check box is unselected");
		}

		// method-2
		softassert.assertFalse(cb1.isDisplayed(), " **** Check Box already selected  ****");

	}

	// 2nd Test Scenario-03
	@Test
	@Parameters({ "URL" })
	public void Test_Scenario_03(String url) {

		// step:1 Load the AUT
		driver.get(url);

		// click on "Javascript Alert"
		driver.findElement(By.xpath("//a[text()='Javascript Alerts']")).click();

		// click the “Click Me”
		driver.findElement(By.xpath("//button[text()='Click Me']")).click();

		// validate the alert message---
		String actual_alert = driver.switchTo().alert().getText();
		System.out.println("Alert Message:" + actual_alert);

		// validate alert message
		String Expected_alert = "I am an alert box!--";
		softassert.assertEquals(actual_alert, Expected_alert, "**** Alert Box message Not Matched! ******");

//		assertEquals(actual_alert,Expected_alert,"****Alert Box message Not-Matched! ******");  //Hard Assert

		// accept alert
		driver.switchTo().alert().accept();

	}

	@Parameters({ "testName" })
	@AfterClass
	public void tearDown(String testname) {

		System.err.println("Current URl:" + driver.getCurrentUrl());
		System.err.println("Current Title:" + driver.getTitle());

		System.out.println("Completed Successfully");
		System.out.println("TestName :" + testname);
		driver.quit();
		softassert.assertAll(); // print all asserts---

	}

}
