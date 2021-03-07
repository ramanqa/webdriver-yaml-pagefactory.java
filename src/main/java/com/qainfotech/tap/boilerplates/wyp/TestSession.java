package com.qainfotech.tap.boilerplates.wyp;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.Map;
import java.util.HashMap;

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
}
