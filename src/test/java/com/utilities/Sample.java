package com.utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Sample {

	public static void main(String[] args) throws Throwable {
		// TODO Auto-generated method stub
		
		WebDriver driver=new FirefoxDriver();
		
		driver.get("https://sandbox-carnation-std-virtual-y.openy.org/user/7/edit?destination=/admin/people");
		/*driver.get("https://sandbox-lily-std-virtual-y.openy.org/user/login");
		
		driver.findElement(By.xpath("//*[@id='edit-name']")).sendKeys("admin");
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@id='edit-pass']")).sendKeys("open9622");
		Thread.sleep(3000);
		
		driver.findElement(By.xpath("//*[@id='edit-submit']")).click();
		Thread.sleep(3000);*/
		
		String data=driver.getTitle();
		
		System.out.println(data);

	}

}
