package com.qainfotech.tap.boilerplates.wyp;

import org.openqa.selenium.WebElement;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import java.util.ArrayList;
import com.jcabi.aspects.Loggable;

@Loggable(Loggable.DEBUG)
public class YamlWebElements {

    private String name;
    private WebDriver driver;
    private WebDriverWait wait;
    private Map<String, Object> specs;
    private String pageObjectName;
    private List<YamlWebElement> elements;
    private YamlWebElement container = null;
    private TestSession testSession;
    private Map<String, Object> config;

    public YamlWebElements(TestSession session, String pageObjectName, String name, Map<String, Object> specs){
        this.specs = specs;
        this.name = name;
        this.testSession = session;
        this.driver = session.driver();
        this.config = session.config();
        this.pageObjectName = pageObjectName;
        this.setWait();
        this.initElements();
    }

    public YamlWebElements(YamlWebElement container, String name, Map<String, Object> specs){
        this.specs = specs;
        this.name = name;
        this.container = container;
        this.testSession = container.testSession;
        this.driver = container.driver;
        this.config = container.config;
        this.pageObjectName = container.pageObjectName;
        this.setWait();
        this.initElements();
    }

    public YamlWebElements(YamlWebElement container, String name, By locator, Integer timeout){
        this.name = name;
        this.container = container;
        this.testSession = container.testSession;
        this.driver = container.driver;
        this.config = container.config;
        this.pageObjectName = container.pageObjectName;
        this.setWait(timeout);
        this.initElements(locator);
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

    protected WebDriverWait setWait(Integer timeout){
        this.wait = new WebDriverWait(this.driver, timeout);
        return this.wait;
    }

    protected By locatedBy(){
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

    private void initElements(){
        this.elements = new ArrayList<>();
        List<WebElement> elementList;
        if(this.container == null){
            this.wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locatedBy()));
            elementList =  this.driver.findElements(locatedBy());
        }else{
            this.wait.until(ExpectedConditions.presenceOfNestedElementsLocatedBy(this.container.locatedBy(), locatedBy()));
            elementList =  this.container.webElement().findElements(locatedBy());
        }
        for(int elementIndex=0; elementIndex < elementList.size(); elementIndex++){
            this.elements.add(new YamlWebElement(this.testSession, this.pageObjectName, this.name+"["+elementIndex+"]", elementList.get(elementIndex)));
        }
    }

    private void initElements(By locator){
        this.elements = new ArrayList<>();
        List<WebElement> elementList;
        if(this.container == null){
            this.wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
            elementList =  this.driver.findElements(locator);
        }else{
            this.wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(this.container.webElement(), locator));
            elementList =  this.container.webElement().findElements(locator);
        }
        for(int elementIndex=0; elementIndex < elementList.size(); elementIndex++){
            this.elements.add(new YamlWebElement(this.testSession, this.pageObjectName, this.name+"["+elementIndex+"]", elementList.get(elementIndex)));
        }
    }
    public YamlWebElement get(Integer index){
        return this.elements.get(index); 
    }

    public Integer size(){
        return this.elements.size();
    }

    public Integer count(){
        return this.elements.size();
    }

    public List<YamlWebElement> asList(){
        return this.elements;
    }

    public List<YamlWebElement> elements(){
        return this.elements;
    }

}
