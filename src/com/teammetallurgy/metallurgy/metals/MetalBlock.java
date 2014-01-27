package com.teammetallurgy.metallurgy.metals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

import com.teammetallurgy.metallurgy.Metallurgy;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MetalBlock extends Block
{
    private HashMap<Integer, String> names = new HashMap<Integer, String>();
    private HashMap<Integer, Integer> blockTypes = new HashMap<Integer, Integer>();
    private HashMap<Integer, Integer> drops = new HashMap<Integer, Integer>();
    private HashMap<Integer, String> textures = new HashMap<Integer, String>();
    private HashMap<Integer, Icon> icons = new HashMap<Integer, Icon>();

    public MetalBlock(int id)
    {
        super(id, Material.rock);

        setTextureName(Metallurgy.MODID + ":metal_block_" + id);
        setUnlocalizedName("metal.block." + id);
        setCreativeTab(Metallurgy.instance.creativeTabBlocks);
        setHardness(3F);
    }

    /**
     * Add Sub Block attributes
     * 
     * @param meta
     *            Meta/Damage ID
     * @param name
     *            Block name
     * @param blockType
     *            0:Ore, 1:Block, 2:Brick
     * @param texture
     *            Texture
     */
    public void addSubBlock(int meta, String name, int blockType, String texture)
    {
        addSubBlock(meta, name, blockType, texture, 0);
    }

    /**
     * Add Sub Block attributes
     * 
     * @param meta
     *            Meta/Damage ID
     * @param name
     *            Block Name
     * @param blockType
     *            0:Ore, 1:Block, 2:Brick
     * @param texture
     *            Texture
     * @param dropId
     *            Dropped Item ID, 0: drop block
     */
    public void addSubBlock(int meta, String name, int blockType, String texture, int dropId)
    {
        names.put(meta, name);

        // invalid type would be set to ore
        if (blockType < 0 || blockType > 2)
        {
            blockType = 0;
        }

        blockTypes.put(meta, blockType);

        textures.put(meta, texture);

        drops.put(meta, dropId);

    }

    @SideOnly(Side.CLIENT)
    @Override
    public Icon getIcon(int side, int meta)
    {
        if (icons.containsKey(meta))
        {
            return icons.get(meta);
        }
        else
        {
            return this.blockIcon;
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister register)
    {
        // Default Texture
        // this.blockIcon = register.registerIcon(this.getTextureName());

        // Sub Block Textures
        for (Map.Entry<Integer, String> texture : textures.entrySet())
        {
            int meta = texture.getKey();
            String textureName = texture.getValue();

            Icon icon = register.registerIcon(textureName);

            icons.put(meta, icon);
        }

    }

    @Override
    public int idDropped (int meta, Random par2Random, int par3)
    {
        int id = this.blockID ;
        
        if (this.drops.get(meta) != null && this.drops.get(meta)!= 0)
        {
            id = this.drops.get(meta) + 256;
        }
        
        return id;
    }
    
    @Override
    public int damageDropped(int meta)
    {
        return meta;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void getSubBlocks(int id, CreativeTabs tab, List list)
    {
        for (Map.Entry<Integer, String> name : names.entrySet())
        {
            list.add(new ItemStack(id, 1, name.getKey()));
        }
    }

    public String getUnlocalizedName(int meta)
    {

        if (names.get(meta) != null)
        {
            String unlocalizedName = names.get(meta);
            unlocalizedName = unlocalizedName.replace(" ", ".").toLowerCase();

            String blockType = "";
            switch (blockTypes.get(meta))
            {
                case 0:
                    blockType = ".ore";
                    break;
                case 1:
                    blockType = ".block";
                    break;
                case 2:
                    blockType = ".brick";
            }

            String prefix = "tile." + Metallurgy.MODID.toLowerCase() + ".";
            return prefix + unlocalizedName + blockType;
        }
        else
        {
            return this.getUnlocalizedName();
        }

    }

}
