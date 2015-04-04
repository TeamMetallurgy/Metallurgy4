package com.teammetallurgy.metallurgy.machines.closet;

import org.lwjgl.opengl.GL11;

import com.teammetallurgy.metallurgy.BlockList;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class ItemRendererCloset implements IItemRenderer
{

    private ModelCloset model = new ModelCloset();
    private ResourceLocation texture = new ResourceLocation("metallurgy:textures/models/machines/cabinet_platinum.png");
    
    @Override
    public boolean handleRenderType(ItemStack itemStack, ItemRenderType type)
    {
        if (itemStack == null || itemStack.getItem() == null) return false;

        Block closet = BlockList.getCloset();

        if (closet != Block.getBlockFromItem(itemStack.getItem())) return false;

        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack itemStack, ItemRendererHelper helper)
    {
        if (itemStack == null || itemStack.getItem() == null) return false;

        Block closet = BlockList.getCloset();

        if (closet != Block.getBlockFromItem(itemStack.getItem())) return false;

        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef(0.5F, 1.5F, 0.5F);
        GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
        if (type == ItemRenderType.INVENTORY)
        {
            GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
            GL11.glScalef(0.65F, 0.65F, 0.65F);
            GL11.glTranslatef(0F, 1.2F, 0F);
        }
        if (type == ItemRenderType.EQUIPPED_FIRST_PERSON)
        {
            GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
        }
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(texture);
        this.model.renderAllModels();
        GL11.glPopMatrix();
        
    }

}
