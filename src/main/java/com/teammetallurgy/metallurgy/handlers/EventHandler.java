package com.teammetallurgy.metallurgy.handlers;

import net.minecraftforge.event.entity.player.FillBucketEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EventHandler extends com.teammetallurgy.metallurgycore.handlers.EventHandler
{

    @Override
    public String getModTag()
    {
        return "Metallurgy4";
    }

    @SubscribeEvent
    public void onBucketFill(FillBucketEvent event)
    {
        BucketsHandler.instance.fillBucket(event);
    }

}
