package com.teammetallurgy.metallurgy.client.particle;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;

public class EntityOreFX extends EntityFX
{
    private TextureManager textureManager;
    private ResourceLocation particles = new ResourceLocation("metallurgy:textures/particles/m_particles.png");
    private ResourceLocation mc_particles = new ResourceLocation("minecraft:textures/particle/particles.png");
    private float effectScale;
    private int effectType;

    public EntityOreFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12)
    {
        super(par1World, par2, par4, par6, par8, par10, par12);
        this.textureManager = FMLClientHandler.instance().getClient().getTextureManager();

        this.motionX = this.motionX * 0.009999999776482582D + par8;
        this.motionY = this.motionY * 0.009999999776482582D + par10;
        this.motionZ = this.motionZ * 0.009999999776482582D + par12;

        this.effectScale = this.particleScale;
        this.particleRed = this.particleGreen = this.particleBlue = 1.0F;
        this.particleMaxAge = (int) (8.0D / (Math.random() * 0.8D + 0.2D)) + 4;

        this.noClip = true;

        this.effectType = 0;
    }

    /**
     * Gets how bright this entity is.
     */
    @Override
    public float getBrightness(float par1)
    {
        float f1 = (this.particleAge + par1) / this.particleMaxAge;

        if (f1 < 0.0F)
        {
            f1 = 0.0F;
        }

        if (f1 > 1.0F)
        {
            f1 = 1.0F;
        }

        float f2 = super.getBrightness(par1);
        return f2 * f1 + (1.0F - f1);
    }

    @Override
    public int getBrightnessForRender(float par1)
    {
        float f1 = (this.particleAge + par1) / this.particleMaxAge;

        if (f1 < 0.0F)
        {
            f1 = 0.0F;
        }

        if (f1 > 1.0F)
        {
            f1 = 1.0F;
        }

        int i = super.getBrightnessForRender(par1);
        int j = i & 255;
        int k = i >> 16 & 255;
        j += (int) (f1 * 15.0F * 16.0F);

        if (j > 240)
        {
            j = 240;
        }

        return j | k << 16;
    }

    @Override
    public int getFXLayer()
    {
        return 0;
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void onUpdate()
    {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (this.particleAge++ >= this.particleMaxAge)
        {
            this.setDead();
        }
        this.updateTextureIndex();
        this.moveEntity(this.motionX, this.motionY, this.motionZ);

        this.motionX *= 0.9599999785423279D;
        this.motionY *= 0.9599999785423279D;
        this.motionZ *= 0.9599999785423279D;

        if (this.onGround)
        {
            this.motionX *= 0.699999988079071D;
            this.motionZ *= 0.699999988079071D;
        }
    }

    @Override
    public void renderParticle(Tessellator tessellator, float ticker, float par3, float par4, float par5, float par6, float par7)
    {
        float f6 = (this.particleAge + ticker) / this.particleMaxAge;
        f6 *= f6;
        float f7 = 2.0F - f6 * 2.0F;

        if (f7 > 1.0F)
        {
            f7 = 1.0F;
        }

        f7 *= 0.2F;

        float minU = this.particleTextureIndexX / 4.0F;
        float maxU = minU + 0.25F;
        float minV = this.effectType / 4.0F;
        float maxV = minV + 0.25F;
        float scale = 0.1F * this.effectScale;

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        Tessellator tess = new Tessellator();
        this.textureManager.bindTexture(this.particles);

        float newPosX = (float) (this.prevPosX + (this.posX - this.prevPosX) * ticker - EntityFX.interpPosX);
        float newPosY = (float) (this.prevPosY + (this.posY - this.prevPosY) * ticker - EntityFX.interpPosY);
        float newPosZ = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * ticker - EntityFX.interpPosZ);

        tess.startDrawingQuads();
        tess.setBrightness(this.getBrightnessForRender(ticker));
        tess.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha);

        tess.addVertexWithUV(newPosX - par3 * scale - par6 * scale, newPosY - par4 * scale, newPosZ - par5 * scale - par7 * scale, maxU, maxV);
        tess.addVertexWithUV(newPosX - par3 * scale + par6 * scale, newPosY + par4 * scale, newPosZ - par5 * scale + par7 * scale, maxU, minV);
        tess.addVertexWithUV(newPosX + par3 * scale + par6 * scale, newPosY + par4 * scale, newPosZ + par5 * scale + par7 * scale, minU, minV);
        tess.addVertexWithUV(newPosX + par3 * scale - par6 * scale, newPosY - par4 * scale, newPosZ + par5 * scale - par7 * scale, minU, maxV);

        tess.draw();

        this.textureManager.bindTexture(this.mc_particles);

    }

    public void setTypeAndColor(int type, float red, float green, float blue)
    {
        this.effectType = type;
        this.particleRed = red;
        this.particleGreen = green;
        this.particleBlue = blue;

    }

    public void setTypeAndColor(int type, int red, int green, int blue)
    {
        this.effectType = type;
        this.particleRed = red / 255F;
        this.particleGreen = green / 255F;
        this.particleBlue = blue / 255F;

    }

    private void updateTextureIndex()
    {

        switch (this.effectType)
        {
            case 0:
                this.particleTextureIndexX = 3 - this.particleAge * 4 / this.particleMaxAge;
                break;
            case 1:
                this.particleTextureIndexX = 1 - this.particleAge * 2 / this.particleMaxAge;
                break;
            case 2:
                this.particleTextureIndexX = 2 - this.particleAge * 3 / this.particleMaxAge;
                break;

        }
    }
}
