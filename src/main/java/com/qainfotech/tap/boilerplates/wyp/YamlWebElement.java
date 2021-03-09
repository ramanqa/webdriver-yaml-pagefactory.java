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
public class YamlWebElement {

    protected String name;
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Map<String, Object> specs;
    protected WebElement element = null;
    protected By by = null;
    protected String pageObjectName;
    protected YamlWebElement container = null;
    protected TestSession testSession;
    protected Map<String, Object> config;

    public YamlWebElement(TestSession session, String pageObjectName, String name, Map<String, Object> specs){
        this.specs = specs;
        this.name = name;
        this.testSession = session;
        this.config = session.config();
        this.driver = session.driver();
        this.pageObjectName = pageObjectName;
        this.setWait();
    }

    public YamlWebElement(YamlWebElement container, String name, Map<String, Object> specs){
        this.specs = specs;
        this.container = container;
        this.testSession = container.testSession;
        this.name = name;
        this.driver = container.driver;
        this.config = container.config;
        this.pageObjectName = container.pageObjectName;
        this.setWait();
    }
    
    public YamlWebElement(TestSession session, String pageObjectName, String name, WebElement element){
        this.element = element;
        this.name = name;
        this.testSession = session;
        this.driver = session.driver();
        this.config = session.config();
        this.pageObjectName = pageObjectName;
        this.wait = new WebDriverWait(this.driver, 1);
    }
    
    public YamlWebElement(TestSession session, String pageObjectName, String name, By locator, Integer timeout){
        this.by = locator;
        this.name = name;
        this.testSession = session;
        this.driver = session.driver();
        this.config = session.config();
        this.pageObjectName = pageObjectName;
        this.wait = new WebDriverWait(this.driver, timeout);
    }

    protected WebDriverWait setWait(){
        this.wait = new WebDriverWait(this.driver, 1);
        if(this.config.containsKey("timeout")){
            this.wait = new WebDriverWait(driver, Integer.parseInt(this.config.get("timeout").toString()));
        }
        if(this.specs.containsKey("timeout")){
            this.wait = new WebDriverWait(driver, Integer.parseInt(this.specs.get("timeout").toString()));
        }
        return this.wait;
    }

    public By locatedBy(){
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
            if(this.container != null){
                this.wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(this.container.webElement(), locatedBy()));
                return this.container.webElement().findElement(locatedBy());
            }
            this.wait.until(ExpectedConditions.presenceOfElementLocated(locatedBy()));
            return this.driver.findElement(locatedBy());
        }
        return this.element;
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
        return new YamlWebElement(this.testSession, this.pageObjectName, this.name+".child("+locator+")", locator, timeout);
    }

}
