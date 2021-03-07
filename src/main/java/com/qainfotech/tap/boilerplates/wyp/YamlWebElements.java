package com.qainfotech.tap.boilerplates.wyp;

import org.openqa.selenium.WebElement;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import java.util.List;
import java.util.ArrayList;
import com.jcabi.aspects.Loggable;

@Loggable(Loggable.DEBUG)
public class YamlWebElements {

    private String name;
    private WebDriver driver;
    private WebDriverWait wait;
    private Map<String, Object> specs;
    private long startTime;
    private String pageObjectName;
    private List<YamlWebElement> elements;
    private YamlWebElement container = null;

    public YamlWebElements(WebDriver driver, String pageObjectName, String name, Map<String, Object> specs){
        this.specs = specs;
        this.name = name;
        this.driver = driver;
        this.pageObjectName = pageObjectName;
        this.wait = new WebDriverWait(this.driver, 1);
        if(specs.containsKey("timeout")){
            this.wait = new WebDriverWait(driver, Integer.parseInt(specs.get("timeout").toString()));
        }

        this.initElements();
    }

    public YamlWebElements(YamlWebElement container, String name, Map<String, Object> specs){
        this.specs = specs;
        this.name = name;
        this.container = container;
        this.driver = container.driver;
        this.pageObjectName = container.pageObjectName;
        this.wait = new WebDriverWait(this.driver, 1);
        if(specs.containsKey("timeout")){
            this.wait = new WebDriverWait(driver, Integer.parseInt(specs.get("timeout").toString()));
        }

        this.initElements();
    }

    private By locatedBy(){
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
            this.elements.add(new YamlWebElement(this.driver, this.pageObjectName, this.name+"["+elementIndex+"]", elementList.get(elementIndex)));
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
