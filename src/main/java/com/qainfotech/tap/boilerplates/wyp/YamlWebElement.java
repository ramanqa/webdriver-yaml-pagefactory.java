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
//import com.jcabi.aspects.Loggable;

//@Loggable
public class YamlWebElement {

    private String name;
    private WebDriver driver;
    private WebDriverWait wait;
    private Map<String, Object> specs;
    private WebElement element = null;
    private By by = null;
    private long startTime;
    private String pageObjectName;

    public YamlWebElement(WebDriver driver, String pageObjectName, String name, Map<String, Object> specs){
        this.specs = specs;
        this.name = name;
        this.driver = driver;
        this.pageObjectName = pageObjectName;
        this.wait = new WebDriverWait(this.driver, 1);
        if(specs.containsKey("timeout")){
            this.wait = new WebDriverWait(driver, Integer.parseInt(specs.get("timeout").toString()));
        }
    }

    public YamlWebElement(WebDriver driver, String pageObjectName, String name, WebElement element){
        this.element = element;
        this.name = name;
        this.driver = driver;
        this.pageObjectName = pageObjectName;
        this.wait = new WebDriverWait(this.driver, 1);
    }
    
    public YamlWebElement(WebDriver driver, String pageObjectName, String name, By locator, Integer timeout){
        this.by = locator;
        this.name = name;
        this.driver = driver;
        this.pageObjectName = pageObjectName;
        this.wait = new WebDriverWait(this.driver, timeout);
    }

    private By locatedBy(){
        if(this.by == null){
            String findBy = specs.get("findBy").toString();
            String locator = specs.get("locator").toString();
            switch(findBy){
                case "css":
                case "cssSelector":
                    return By.cssSelector(locator);
                case "xpath":
                    return By.xpath(locator);
                case "id":
                    return By.id(locator);
                case "name":
                    return By.name(locator);
                case "linkText":
                case "link":
                    return By.linkText(locator);
                case "partialLinkText":
                case "partialLink":
                    return By.partialLinkText(locator);
                case "tag":
                case "tagName":
                    return By.tagName(locator);
                default:
                  return By.cssSelector(locator);
            }
        }
        return this.by; 
    }


    /**
     * get WebElement for YamlWebElement
     *
     */
    public WebElement webElement(){
        if(this.element == null){
            this.wait.until(ExpectedConditions.presenceOfElementLocated(locatedBy()));
            return this.driver.findElement(locatedBy());
        }
        return this.element;
    }

    private void reportMethodEntry(){
        this.startTime = System.currentTimeMillis();
    }

    private void reportMethodExit(String params){
        long executionTime = System.currentTimeMillis() - this.startTime;
        String methodName = new Exception().getStackTrace()[2].getMethodName();
        Reporter.log("Executed " + this.pageObjectName + "." + this.name + "#" + methodName + "(" + params + ") in " + executionTime + "ms" , true);
    }
    private void reportMethodExit(String params, String response){
        long executionTime = System.currentTimeMillis() - this.startTime;
        String methodName = new Exception().getStackTrace()[2].getMethodName();
        Reporter.log("Executed " + this.pageObjectName + "." + this.name + "#" + methodName + "(" + params + "), returns "+response+" in " + executionTime + "ms" , true);
    }

    private void reportMethodExit(){
        this.reportMethodExit("");
    }

    public void click(){
        this.webElement().click();
    }

    public void submit(){
        this.webElement().submit();
    }

    public void sendKeys(String text){
        this.webElement().sendKeys(text);
    }

    public void clear(){
        this.webElement().clear();
    }

    public String getText(){
        return this.webElement().getText();
    }

    public String getTagName(){
        String response = this.webElement().getTagName();
        return response;
    }

    public String getAttribute(String attributeKey){
        String response = this.webElement().getAttribute(attributeKey);
        return response;
    }
    
    public String getCssValue(String attributeKey){
        String response = this.webElement().getCssValue(attributeKey);
        return response;
    }

    public Point getLocation(){
        Point response = this.webElement().getLocation();
        return response;
    }

    public Dimension getSize(){
        Dimension response = this.webElement().getSize();
        return response;
    }

    public Rectangle getRect(){
        Rectangle response = this.webElement().getRect();
        return response;
    }

    public boolean isDisplayed(){
        Boolean response = this.webElement().isDisplayed();
        return response;
    }

    public boolean isSelected(){
        Boolean response = this.webElement().isSelected();
        return response;
    }

    public boolean isEnabled(){
        Boolean response = this.webElement().isEnabled();
        return response;
    }

    public YamlWebElement findElement(By locator){
        Integer timeout = 1;
        if(this.specs != null){
            if(this.specs.containsKey("timeout")){
                timeout = Integer.parseInt(this.specs.get("timeout").toString());
            }
        }
        return new YamlWebElement(this.driver, this.pageObjectName, this.name+".child("+locator+")", locator, timeout);
    }

}
