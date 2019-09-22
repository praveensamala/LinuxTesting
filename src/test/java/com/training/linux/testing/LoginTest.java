package com.training.linux.testing;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class LoginTest {
	RemoteWebDriver driver;
	@BeforeTest
	public void setup() {
		DesiredCapabilities dc = new DesiredCapabilities();
		dc.setCapability(CapabilityType.BROWSER_NAME, BrowserType.CHROME);
		dc.setCapability(CapabilityType.PLATFORM_NAME, Platform.LINUX);
		
		try {
			URL url = new URL("http://localhost:4444/wd/hub");
			driver = new RemoteWebDriver(url, dc);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		driver.get("https://www.facebook.com");
		driver.manage().window().maximize();
	}
	
	@Test
	public void loginTest() {
		driver.findElement(By.id("email")).sendKeys("gamecheck280@gmail.com");
		driver.findElement(By.id("pass")).sendKeys("system123");
		driver.findElement(By.id("loginbutton")).click();
		
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type=\"submit\"]")));
		
		System.out.println("title : "+driver.getTitle());
		
		
		List <WebElement> list = new ArrayList<WebElement>();
		list = driver.findElements(By.tagName("span"));
		//System.out.println("list size : "+list.size());
		
		boolean userfound = false;
		for(WebElement e: list) {
			//System.out.println(e.getText());
			if (e.getText().contains("Gamecheck") && !e.getText().contains("Not you") && !e.getText().contains("Log in as")) {
				userfound = true;
				break;
			}
		}
		
		AssertJUnit.assertTrue(userfound);
	}
	
	@AfterMethod
	@AfterTest
	public void tearDown() {
		driver.quit();
	}
}
