package com.qainfotech.tap.boilerplates.wyp;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

import org.testng.Reporter;

public class TestNGReporterAppender extends AppenderBase<ILoggingEvent> {

    private Boolean recordDebugLogs(){
        return Boolean.parseBoolean(System.getProperty("PO_DEBUG_LOGS"));
    }

    @Override
    protected void append(final ILoggingEvent event) {
        if(event.getLevel().toString().equals("INFO")){
            String logLine = event.getLoggerName() + event.getMessage();
            System.out.println(logLine);
            Reporter.log(logLine);
        }else{
            if(recordDebugLogs()){
                String logLine = "";
                logLine = "--" + event.getLoggerName() + event.getMessage();
                if(event.getLevel().toString().equals("WARN")){
                    logLine = "-" + event.getLoggerName() + event.getMessage();
                }
                System.out.println(logLine);
                Reporter.log(logLine);
            }
        }
    }

}
