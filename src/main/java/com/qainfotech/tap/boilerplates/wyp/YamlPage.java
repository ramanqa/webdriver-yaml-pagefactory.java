package com.qainfotech.tap.boilerplates.wyp;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.JavascriptExecutor;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import org.yaml.snakeyaml.Yaml;

public class YamlPage {

    protected WebDriver driver;
    Map<String, Object> pageUI;
    protected TestSession testSession;
    protected Map<String, Object> config;

    public YamlPage(TestSession session){
        this.testSession = session;
        this.driver = session.driver();
        this.config = session.config();
        String filePath = this.getClass().getCanonicalName().replaceAll("\\.", "/") + ".yaml";
        Yaml yaml = new Yaml();
        try{
            this.pageUI = yaml.load(this.getClass().getClassLoader().getResourceAsStream(filePath));
        }catch(Exception e){}
    }

    private Map<String, Object> elementUI(String elementName){
        return (Map<String, Object>)((Map<String, Object>) pageUI.get("elements")).get(elementName);
    }

    private Boolean hasContainer() {
        return ((Map<String, Object>) pageUI.get("elements")).containsKey("container");
    }

    public YamlWebElement element(String elementName){
        String pageObjectName = this.getClass().getCanonicalName();
        if(this.hasContainer()){
            return (new ContainerYamlWebElement(testSession, pageObjectName, elementUI("container")))
              .childElement(elementName, elementUI(elementName));
        }

        return new YamlWebElement(this.testSession, pageObjectName, elementName, elementUI(elementName));
    }

    public List<YamlWebElement> elements(String elementName){
        String pageObjectName = this.getClass().getCanonicalName();
        if(this.hasContainer()){
            return (new ContainerYamlWebElement(testSession, pageObjectName, elementUI("container")))
              .childElements(elementName, elementUI(elementName)).elements();
        }
        return new YamlWebElements(this.testSession, pageObjectName, elementName, elementUI(elementName)).elements();
    }

    public Object executeJS(String jsactionName){
        return ((JavascriptExecutor)this.driver)
            .executeScript(((Map<String, Object>)pageUI.get("jsactions")).get(jsactionName).toString());
    }

    public void waitForPageLoad(){
        for(YamlWebElement yamlWebElement:expectedElementList()){
            yamlWebElement.isDisplayed();
        }
    }
    
    private List<YamlWebElement> expectedElementList() {
        List<YamlWebElement> listOfWaitForElements = new ArrayList<>();
        String pageObjectName = this.getClass().getCanonicalName();
        Map<String, Object> elements = (Map<String, Object>)pageUI.get("elements");
        for(String elementName: elements.keySet()){
            if(((Map<String, Object>)elements.get(elementName)).containsKey("expected")){
                if((Boolean)((Map<String, Object>)elements.get(elementName)).get("ex[ected")){
                    YamlWebElement yamlWebElement = new YamlWebElement(this.testSession, pageObjectName, elementName, elementUI(elementName));
                    listOfWaitForElements.add(yamlWebElement);
                }
            }
        }
        return listOfWaitForElements;
    }
}
