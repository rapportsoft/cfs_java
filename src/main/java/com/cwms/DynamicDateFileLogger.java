package com.cwms;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

import com.p6spy.engine.spy.appender.FileLogger;

public class DynamicDateFileLogger extends FileLogger {

	
	//private static final String LOG_FILE_PATH = "C:/Container Freight Station/Daily_Logs/cfs-logs-"; // Base path
	private static final String LOG_FILE_PATH = "E:/CFS/Backup/03-12-2024/New/cfs-logs-"; // Base path

    private static final String FILE_EXTENSION = ".log";

    @Override
    public void logText(String text) {
        String formattedText = formatLogMessage(text);
        String logFileName = LOG_FILE_PATH + getCurrentDate() + FILE_EXTENSION;
        createLogFileIfNotExist(logFileName); // Ensure the file exists before writing
        writeLogToFile(logFileName, formattedText);
    }

    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy"); // Change format to include time
        return sdf.format(new Date());
    }
    
    private String getCurrentDate1() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"); // Change format to include time
        return sdf.format(new Date());
    }

    private String formatLogMessage(String text) {
        // Get the current date and time
        String currentDateTime = getCurrentDate1();
        
        // Find the position of the last '|' character
        int lastPipeIndex = text.lastIndexOf("|");
        
        // If there's a '|' in the text, extract everything after it
        String sqlQueryWithValues = "";
        if (lastPipeIndex != -1 && lastPipeIndex < text.length() - 1) {
            sqlQueryWithValues = text.substring(lastPipeIndex + 1).trim(); // Extract after last '|'
        }
        
        // Return the current date and time with the SQL query after the last '|'
        return currentDateTime + " " + sqlQueryWithValues;
    }


    
    
    private void createLogFileIfNotExist(String logFileName) {
        File logFile = new File(logFileName);
        // Check if file exists, if not, create it
        if (!logFile.exists()) {
            try {
                // Create parent directories if they don't exist
                logFile.getParentFile().mkdirs();
                logFile.createNewFile(); // Create the log file
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void writeLogToFile(String logFileName, String text) {
        try (FileWriter fileWriter = new FileWriter(logFileName, true)) {
            fileWriter.write(text + "\n\n");  // Append newline character after each log entry
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
