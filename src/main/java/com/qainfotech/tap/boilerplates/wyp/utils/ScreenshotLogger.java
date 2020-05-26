package com.qainfotech.tap.boilerplates.wyp.utils;

import org.testng.Reporter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.TakesScreenshot;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import org.openqa.selenium.OutputType;
import org.apache.commons.io.FileUtils;
import java.io.IOException;

public class ScreenshotLogger {

    WebDriver driver;
    String path = "target/screenshots";

    public ScreenshotLogger(WebDriver driver){
        this.driver = driver;
    }
    
    public void log(String message){
        try{
            File screenshotFile = ((TakesScreenshot) this.driver).getScreenshotAs(OutputType.FILE);
            String fileName = "" + System.currentTimeMillis() + ".png";
            if(!Files.exists(Paths.get(path))){
                new File(this.path).mkdirs(); 
            }

            File destinationFile = new File(this.path + "/" + fileName);

            FileUtils.copyFile(screenshotFile, destinationFile);
            Reporter.log("Screenshot Captured ["+this.path + "/" + fileName +"]: " + message);
        }catch(IOException e){}
    }

    public void log(){
        this.log("");
    }
}
