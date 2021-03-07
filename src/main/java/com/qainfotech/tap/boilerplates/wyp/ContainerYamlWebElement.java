package com.qainfotech.tap.boilerplates.wyp;

import java.util.Map;
import com.jcabi.aspects.Loggable;

@Loggable(Loggable.DEBUG)
public class ContainerYamlWebElement extends YamlWebElement {

    public ContainerYamlWebElement(TestSession session, String pageObjectName, Map<String, Object> specs){
        super(session, pageObjectName, "container", specs);
    }

    public YamlWebElement childElement(String childName, Map<String, Object> specs){
        return new YamlWebElement(this, childName, specs);
    }

    public YamlWebElements childElements(String childName, Map<String, Object> specs){
        return new YamlWebElements(this, childName, specs);
    }
}
