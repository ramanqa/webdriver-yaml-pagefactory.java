package com.qainfotech.tap.boilerplates.wyp;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.OutputType;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

import java.util.Map;
import java.util.HashMap;

import org.testng.Reporter;

public class TestSession {

    Map<String, Object> config;
    WebDriver driver;

    public TestSession(WebDriver driver, Map<String, Object> config){
        this.driver = driver;
        this.config = config;
    }

    public TestSession(){
        this.driver = new ChromeDriver();
        this.config = new HashMap<>();
        this.config.put("timeout", 60);
    }

    public WebDriver driver(){
        return this.driver;
    }

    public Map<String, Object> config() {
        return this.config;
    }

    public String captureScreenshot(String name) throws IOException {
        File ssFile = ((TakesScreenshot)this.driver).getScreenshotAs(OutputType.FILE);
        File DestFile=new File("target/screenshots/" + name);
        FileUtils.copyFile(ssFile, DestFile);
        Reporter.log(DestFile.getAbsolutePath());
        return DestFile.getAbsolutePath();
    }

    public String captureScreenshot() throws IOException {
        return this.captureScreenshot(System.currentTimeMillis() + ".png");
    }
}
