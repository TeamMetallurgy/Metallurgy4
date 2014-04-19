package com.teammetallurgy.metallurgy.recipes;

import java.util.HashMap;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CrusherRecipes {
	private static CrusherRecipes instance = new CrusherRecipes();

	public static CrusherRecipes getInstance() {
		return CrusherRecipes.instance;
	}

	private final HashMap<String, ItemStack> metaList = new HashMap<String, ItemStack>();

	@Deprecated
	public void addRecipe(int itemID, int itemDamage, ItemStack itemStack) {
		this.addRecipe(new ItemStack(Item.getItemById(itemID), 1, itemDamage),
				itemStack);
	}

	public ItemStack getCrushingResult(ItemStack itemStack) {
		if (itemStack == null) {
			return null;
		}
		return this.metaList.get(itemStack.getUnlocalizedName());

	}

	public void addRecipe(ItemStack oreItem, ItemStack ret) {
		this.metaList.put(oreItem.getUnlocalizedName(), ret);
	}

}
