package com.teammetallurgy.metallurgy.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MetallurgyApiLogger
{
    private static Logger logger = LogManager.getLogger("MetallurgyAPI");
    
    public static void info(String message)
    {
        MetallurgyApiLogger.logger.info(message);
    }
    
    public static void trace(String message)
    {
        MetallurgyApiLogger.logger.trace(message);
    }
    
    public static void warn(String message)
    {
        MetallurgyApiLogger.logger.warn(message);
    }
}
