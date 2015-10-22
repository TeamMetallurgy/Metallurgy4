package com.teammetallurgy.metallurgy.metals;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.teammetallurgy.metallurgy.BlockList;
import com.teammetallurgy.metallurgy.Metallurgy;
import com.teammetallurgy.metallurgy.handlers.ParticleHandler;
import com.teammetallurgy.metallurgy.lib.Configs;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MetalBlock extends Block
{
    private HashMap<Integer, String> names = new HashMap<Integer, String>();
    private HashMap<Integer, Integer> blockTypes = new HashMap<Integer, Integer>();
    private HashMap<Integer, Item> drops = new HashMap<Integer, Item>();
    private HashMap<Integer, String> textures = new HashMap<Integer, String>();
    private HashMap<Integer, IIcon> icons = new HashMap<Integer, IIcon>();
    private HashMap<Integer, int[]> particles = new HashMap<Integer, int[]>();

    public MetalBlock(String postfix)
    {
        super(Material.rock);
        this.init(postfix);
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
        this.addSubBlock(meta, name, blockType, texture, null);
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
     * @param drop
     *            Dropped Item, null: drop block
     */
    public void addSubBlock(int meta, String name, int blockType, String texture, Item drop)
    {
        this.names.put(meta, name);

        // invalid type would be set to ore
        if (blockType < 0 || blockType > 2)
        {
            blockType = 0;
        }

        this.blockTypes.put(meta, blockType);

        this.textures.put(meta, texture);

        this.drops.put(meta, drop);

    }

    @Override
    public int damageDropped(int meta)
    {
        return meta;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta)
    {
        if (this.icons.containsKey(meta))
        {
            return this.icons.get(meta);
        }
        else
        {
            return this.blockIcon;
        }
    }

    @Override
    public Item getItemDropped(int meta, Random par2Random, int par3)
    {
        Item item = Item.getItemFromBlock(this);

        if (this.drops.get(meta) != null)
        {
            item = this.drops.get(meta);
        }

        return item;
    }

    @Override
    public int quantityDropped(int meta, int fortune, Random random)
    {
        boolean haveDrops = (this.drops.get(meta) != null);

        if (haveDrops)
        {
            int range = random.nextInt(3 - 2 + 1) + 2;
            int droppedAmount = random.nextInt(fortune + range + 1) - 1;

            if (droppedAmount < 0)
            {
                droppedAmount = 0;
            }

            return (droppedAmount + 1);

        }

        // default
        return 1;
    }

    @Override
    public int getExpDrop(IBlockAccess world, int metadata, int fortune)
    {
        boolean haveDrops = (this.drops.get(metadata) != null);
        if (haveDrops)
        {
            Random random = new Random();
            int exp = random.nextInt(7 - 2 + 1) + 2;
            return exp;
        }
        // default
        return 0;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List list)
    {
        for (Map.Entry<Integer, String> name : this.names.entrySet())
        {
            list.add(new ItemStack(item, 1, name.getKey()));
        }
    }

    public String getUnlocalizedName(int meta)
    {

        if (this.names.get(meta) != null)
        {
            String unlocalizedName = this.names.get(meta);
            unlocalizedName = unlocalizedName.replace(" ", ".").toLowerCase(Locale.US);

            String blockType = "";
            switch (this.blockTypes.get(meta))
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

            String prefix = "tile." + Metallurgy.MODID.toLowerCase(Locale.US) + ".";
            return prefix + unlocalizedName + blockType;
        }
        else
        {
            return this.getUnlocalizedName();
        }

    }

    private void init(String posfix)
    {
        this.setBlockTextureName(Metallurgy.MODID + ":metal_block_default");
        this.setBlockName("metal.block." + posfix);
        this.setCreativeTab(Metallurgy.instance.creativeTabBlocks);
        this.setHardness(3.0F);
        this.setResistance(5.0F);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(World world, int blockX, int blockY, int blockZ, Random rand)
    {

        int meta = world.getBlockMetadata(blockX, blockY, blockZ);

        if (!this.particles.containsKey(meta) || !Configs.enabledOreParticles) { return; }

        int[] particle = this.particles.get(meta);

        ParticleHandler.renderOreParticle(world, blockX, blockY, blockZ, rand, particle);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister register)
    {
        // Default Texture
        this.blockIcon = register.registerIcon(this.getTextureName());

        // Sub-Blocks Textures
        for (Map.Entry<Integer, String> texture : this.textures.entrySet())
        {
            int meta = texture.getKey();
            String textureName = texture.getValue();

            IIcon icon = register.registerIcon(textureName);

            this.icons.put(meta, icon);
        }

    }

    public void setSubBlockParticles(int meta, int type, int red, int green, int blue)
    {
        int[] settings = { type, red, green, blue };
        this.particles.put(meta, settings);
    }

    @Override
    public boolean isBeaconBase(IBlockAccess worldObj, int x, int y, int z, int beaconX, int beaconY, int beaconZ)
    {
        int meta = worldObj.getBlockMetadata(x, y, z);

        Integer type = blockTypes.get(meta);

        if (type == null || type.intValue() == 0 || this == BlockList.getExtraStorageBlock()) 
        {
            return false;
        }

        return true;
    }

}
