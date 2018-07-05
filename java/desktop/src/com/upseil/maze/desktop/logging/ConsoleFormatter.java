package com.upseil.maze.desktop.logging;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class ConsoleFormatter extends Formatter {
    
    private static final String lineSeparator = System.lineSeparator();
    
    private final String timeFormat;
    private final Date date = new Date();
    
    public ConsoleFormatter(String timeFormat) {
        this.timeFormat = timeFormat;
    }

    @Override
    public synchronized String format(LogRecord record) {
        StringBuilder messageBuilder = new StringBuilder();
        
        date.setTime(record.getMillis());
        messageBuilder.append(String.format(timeFormat, date));
        
        int levelValue = record.getLevel().intValue();
        if (levelValue >= Level.WARNING.intValue()) {
            messageBuilder.append(record.getLevel().getLocalizedName());
            messageBuilder.append(": ");
        }
        
        messageBuilder.append(formatMessage(record));
        messageBuilder.append(lineSeparator);
        if (record.getThrown() != null) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            record.getThrown().printStackTrace(pw);
            pw.close();
            messageBuilder.append(sw.toString());
        }
        return messageBuilder.toString();
    }

    public String getTimeFormat() {
        return timeFormat;
    }

    public String getIntendToStartOfMessage() {
        return createIntendationString(String.format(timeFormat, date).length());
    }

    private String createIntendationString(int length) {
        StringBuilder builder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            builder.append(" ");
        }
        return builder.toString();
    }
    
}
