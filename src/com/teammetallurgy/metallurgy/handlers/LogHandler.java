package com.teammetallurgy.metallurgy.handlers;

import java.util.logging.Logger;

public class LogHandler
{

    private static Logger log;

    public static void setLog(Logger logger)
    {
        log = logger;
    }

    public static void log(String message)
    {
        if (log != null)
        {
            log.warning(message);
        }
    }

}
