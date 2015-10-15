package com.teammetallurgy.metallurgy.handlers;

import org.apache.logging.log4j.Logger;

public class LogHandler
{

    private static Logger log;

    public static void log(String message)
    {
        if (LogHandler.log != null)
        {
            LogHandler.log.warn(message);
        }
    }

    public static void setLog(Logger logger)
    {
        LogHandler.log = logger;
    }

}
