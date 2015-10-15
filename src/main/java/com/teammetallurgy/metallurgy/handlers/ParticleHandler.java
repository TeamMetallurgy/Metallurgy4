package com.teammetallurgy.metallurgy.handlers;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.world.World;

import com.teammetallurgy.metallurgy.client.particle.EntityOreFX;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ParticleHandler
{

    @SideOnly(Side.CLIENT)
    public static void renderOreParticle(World world, int blockX, int blockY, int blockZ, Random rand, int[] particle)
    {

        if (particle == null || particle.length != 4)
        {
            String message = "Invaild ore particle configuration while trying to generate at";
            message += " x: " + blockX;
            message += " y: " + blockY;
            message += " z: " + blockZ;
            LogHandler.log(message);
            return;
        }

        Minecraft mc = FMLClientHandler.instance().getClient();

        if (mc == null || mc.renderViewEntity == null || mc.effectRenderer == null || mc.gameSettings == null) { return; }

        int particleSetting = mc.gameSettings.particleSetting;

        if (particleSetting == 2 || (particleSetting == 1 && rand.nextInt(3) > 0)) { return; }

        double distanceX = mc.renderViewEntity.posX - blockX;
        double distanceY = mc.renderViewEntity.posY - blockY;
        double distanceZ = mc.renderViewEntity.posZ - blockZ;

        double viewableDistanceSqr = 256.0D;
        double distanceFromBlockSqr = distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ;

        if (distanceFromBlockSqr > viewableDistanceSqr) { return; }

        EffectRenderer effectRenderer = mc.effectRenderer;

        double constant = 0.0625D;

        for (int l = 0; l < 6; ++l)
        {
            double particleX = blockX + rand.nextFloat();
            double particleY = blockY + rand.nextFloat();
            double particleZ = blockZ + rand.nextFloat();

            if (l == 0 && !world.getBlock(blockX, blockY + 1, blockZ).isOpaqueCube())
            {
                particleY = blockY + 1 + constant;
            }

            if (l == 1 && !world.getBlock(blockX, blockY - 1, blockZ).isOpaqueCube())
            {
                particleY = blockY + 0 - constant;
            }

            if (l == 2 && !world.getBlock(blockX, blockY, blockZ + 1).isOpaqueCube())
            {
                particleZ = blockZ + 1 + constant;
            }

            if (l == 3 && !world.getBlock(blockX, blockY, blockZ - 1).isOpaqueCube())
            {
                particleZ = blockZ + 0 - constant;
            }

            if (l == 4 && !world.getBlock(blockX + 1, blockY, blockZ).isOpaqueCube())
            {
                particleX = blockX + 1 + constant;
            }

            if (l == 5 && !world.getBlock(blockX - 1, blockY, blockZ).isOpaqueCube())
            {
                particleX = blockX + 0 - constant;
            }

            if (particleX < blockX || particleX > blockX + 1 || particleY < 0.0D || particleY > blockY + 1 || particleZ < blockZ || particleZ > blockZ + 1)
            {

                EntityOreFX particleFX = new EntityOreFX(world, particleX, particleY, particleZ, 0.0D, 0.0D, 0.0D);
                particleFX.setTypeAndColor(particle[0], particle[1], particle[2], particle[3]);

                effectRenderer.addEffect(particleFX);
            }
        }
    }
}
