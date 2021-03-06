package com.qainfotech.tap.boilerplates.wyp;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

import org.testng.Reporter;

public class TestNGReporterAppender extends AppenderBase<ILoggingEvent> {

    @Override
    protected void append(final ILoggingEvent event) {
        String logLine = "";
        if(event.getLevel().toString().equals("INFO")){
            logLine += event.getLoggerName() + event.getMessage();
        }else{
            logLine += "|---> " + event.getLoggerName() + event.getMessage();
        }
        System.out.println(logLine);
        Reporter.log(logLine);
    }

}
