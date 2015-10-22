package com.teammetallurgy.metallurgy.tools;

import java.util.List;
import java.util.Locale;
import java.util.Random;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.StatCollector;

import com.teammetallurgy.metallurgy.Metallurgy;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Sword extends ItemSword
{

    private int effectId = 0;
    private int effectDura = 0;
    private int effectAmp = 0;
    private int effectReceiver = 0;

    public Sword(ToolMaterial toolMaterial, String unlocalizedName, String texture)
    {
        super(toolMaterial);
        this.setTextureName(texture);
        this.setUnlocalizedName(Metallurgy.MODID.toLowerCase(Locale.US) + "." + unlocalizedName);
        this.setCreativeTab(Metallurgy.instance.creativeTabTools);
    }

    public void addEffect(int id, int duration, int amplifier, int receiver)
    {
        this.effectId = id;
        this.effectDura = duration;
        this.effectAmp = amplifier;
        this.effectReceiver = receiver;

    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
        if (this.effectId != 0)
        {
            String effectName;

            if (this.effectId == -3)
            {
                effectName = "enchantment.metallurgy.heal";
            }
            else if (this.effectId == -2)
            {
                effectName = "enchantment.fire";
            }
            else if (this.effectId < 1 && this.effectId >= 32)
            {
                effectName = "enchantment.metallurgy.unknown";
            }
            else
            {
                effectName = Potion.potionTypes[this.effectId].getName();
            }

            effectName = StatCollector.translateToLocal(effectName);

            if (this.effectId > 0)
            {
                effectName += " " + StatCollector.translateToLocal("potion.potency." + this.effectAmp);
                effectName = effectName.trim();
            }

            par3List.add(effectName);
        }
    }

    private void applyEffects(EntityLivingBase target, EntityLivingBase player)
    {
        if (this.effectId == -2)
        {
            if (this.effectReceiver != 0)
            {
                player.setFire(this.effectAmp);
            }

            if (this.effectReceiver != 1)
            {
                target.setFire(this.effectAmp);
            }
        }
        else if (this.effectId == -3)
        {
            if (this.effectReceiver != 0)
            {
                player.heal(this.effectAmp);
            }

            if (this.effectReceiver != 1)
            {
                target.heal(this.effectAmp);
            }
        }
        else
        {
            if (this.effectReceiver != 0)
            {
                player.addPotionEffect(new PotionEffect(this.effectId, this.effectDura, this.effectAmp));
            }

            if (this.effectReceiver != 1)
            {
                target.addPotionEffect(new PotionEffect(this.effectId, this.effectDura, this.effectAmp));
            }
        }

    }

    @Override
    public boolean hitEntity(ItemStack currentItemStack, EntityLivingBase target, EntityLivingBase player)
    {
        Item item = currentItemStack.getItem();
        Random rand = new Random();

        if (item instanceof Sword && this.effectId != 0 && this.effectId != -1 && rand.nextInt(100) <= 30)
        {
            this.applyEffects(target, player);
        }

        return super.hitEntity(currentItemStack, target, player);
    }

}
