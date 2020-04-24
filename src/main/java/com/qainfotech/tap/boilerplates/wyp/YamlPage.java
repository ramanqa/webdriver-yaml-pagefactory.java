package com.qainfotech.tap.boilerplates.wyp;

import org.openqa.selenium.WebDriver;
import java.util.Map;
import java.util.List;
import org.yaml.snakeyaml.Yaml;

public class YamlPage {

    protected WebDriver driver;
    Map<String, Object> pageUI;

    public YamlPage(WebDriver driver){
        this.driver = driver;
        String filePath = this.getClass().getCanonicalName().replaceAll("\\.", "/") + ".yaml";
        Yaml yaml = new Yaml();
        this.pageUI = yaml.load(this.getClass().getClassLoader().getResourceAsStream(filePath));
    }

    private Map<String, Object> elementUI(String elementName){
        return (Map<String, Object>)((Map<String, Object>) pageUI.get("elements")).get(elementName);
    }

    public YamlWebElement element(String elementName){
        String pageObjectName = this.getClass().getCanonicalName();
        return new YamlWebElement(driver, pageObjectName, elementName, elementUI(elementName));
    }

    public List<YamlWebElement> elements(String elementName){
        String pageObjectName = this.getClass().getCanonicalName();
        return new YamlWebElements(driver, pageObjectName, elementName, elementUI(elementName)).elements();
    }
}