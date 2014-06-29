package com.teammetallurgy.metallurgy.api;

import java.lang.reflect.Method;

public class MetallurgyApi
{
    private static Class<?> blockList;
    private static Method getSet;
    private static Method getDefaultSets;

    public static IMetalSet getMetalSet(String setName)
    {
        IMetalSet metalSet = null;

        if (blockList == null)
        {
            try
            {
                String blockListClass = "com.teammetallurgy.metallurgy.BlockList";
                blockList = Class.forName(blockListClass);

            }
            catch (Exception e)
            {
                MetallurgyApiLogger.warn("Error while retriving BlockList: " + e.getLocalizedMessage());
            }
        }

        if (getSet == null)
        {
            try
            {
                getSet = blockList.getDeclaredMethod("getSet", String.class);
            }
            catch (Exception e)
            {
                MetallurgyApiLogger.warn("Error while retriving getSet: " + e.getLocalizedMessage());
            }
        }

        try
        {
            metalSet = (IMetalSet) getSet.invoke(null, setName);
        }
        catch (Exception e)
        {
            MetallurgyApiLogger.warn("Error while invoking getSet: " + e.getLocalizedMessage());
        }

        return metalSet;
    }

    public static String[] getSetNames()
    {
        String[] setNames = null;

        if (blockList == null)
        {
            try
            {
                String blockListClass = "com.teammetallurgy.metallurgy.BlockList";
                blockList = Class.forName(blockListClass);

            }
            catch (Exception e)
            {
                MetallurgyApiLogger.warn("Error while retriving BlockList: " + e.getLocalizedMessage());
            }
        }

        if (getSet == null)
        {
            try
            {
                getDefaultSets = blockList.getDeclaredMethod("getDefaultSetNames");
            }
            catch (Exception e)
            {
                MetallurgyApiLogger.warn("Error while retriving getDefaultSetNames: " + e.getLocalizedMessage());
            }
        }

        try
        {
            setNames = (String[]) getDefaultSets.invoke(null);
        }
        catch (Exception e)
        {
            MetallurgyApiLogger.warn("Error while invoking getDefaultSetNames: " + e.getLocalizedMessage());
        }

        return setNames;
    }
}
