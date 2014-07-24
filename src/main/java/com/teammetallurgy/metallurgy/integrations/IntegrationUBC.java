package com.teammetallurgy.metallurgy.integrations;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import com.teammetallurgy.metallurgy.BlockList;
import com.teammetallurgy.metallurgy.api.IMetalSet;
import com.teammetallurgy.metallurgycore.handlers.LogHandler;

import exterminatorJeff.undergroundBiomes.api.UBAPIHook;

public class IntegrationUBC
{
    public static void init()
    {
        String[] loadedSets = { "base", "fantasy", "precious", "utility" };

        for (int i = 0; i < loadedSets.length; i++)
        {
            String setName = loadedSets[i];
            String setTextureName = setName.toLowerCase().trim().replace(" ", "_");

            IMetalSet metalSet = BlockList.getSet(setName);

            String[] metalNames = metalSet.getMetalNames();

            for (int j = 0; j < metalNames.length; j++)
            {
                String metalName = metalNames[j];
                ItemStack itemStack = metalSet.getOre(metalName);

                if (itemStack == null)
                {
                    // Metal without ore, skipping...
                    continue;
                }

                Block oreBlock = metalSet.getDefaultOre();
                int metadata = itemStack.getItemDamage();

                String overlayName = "metallurgy:";
                overlayName += setTextureName;
                overlayName += "/" + metalName.toLowerCase().trim().replace(" ", "_");
                overlayName += "_ore_overlay";

                String blockName = metalName + " Ore";

                UBAPIHook.ubAPIHook.ubOreTexturizer.requestUBOreSetup(oreBlock, metadata, overlayName, blockName);
            }
        }
    }
}
