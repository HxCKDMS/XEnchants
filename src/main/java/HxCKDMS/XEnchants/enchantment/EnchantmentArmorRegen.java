package HxCKDMS.XEnchants.enchantment;

import HxCKDMS.XEnchants.Config;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

public class EnchantmentArmorRegen extends Enchantment
{
	public EnchantmentArmorRegen(int id, int rarity)
	{
		super(id, rarity, EnumEnchantmentType.armor);
		this.setName("armorRegen");
	}

	@Override
	public int getMinEnchantability(int i)
	{
		return 20 + (i - 1);
	}

	@Override
	public int getMaxEnchantability(int i)
	{
		return this.getMinEnchantability(i) + 20;
	}

	@Override
	public int getMaxLevel()
	{
		return Config.enchArmorRegenLVL;
	}
}