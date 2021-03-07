package com.qainfotech.tap.boilerplates.wyp;

import org.openqa.selenium.WebElement;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import com.jcabi.aspects.Loggable;

@Loggable(Loggable.DEBUG)
public class ContainerYamlWebElement extends YamlWebElement {

    public ContainerYamlWebElement(WebDriver driver, String pageObjectName, Map<String, Object> specs){
        super(driver, pageObjectName, "container", specs);
    }

    public YamlWebElement childElement(String childName, Map<String, Object> specs){
        return new YamlWebElement(this, childName, specs);
    }

    public YamlWebElements childElements(String childName, Map<String, Object> specs){
        return new YamlWebElements(this, childName, specs);
    }
}
