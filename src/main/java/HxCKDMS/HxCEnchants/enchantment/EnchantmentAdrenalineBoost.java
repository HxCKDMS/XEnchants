package HxCKDMS.HxCEnchants.enchantment;

import HxCKDMS.HxCEnchants.Config;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;


public class EnchantmentAdrenalineBoost extends Enchantment {

    public EnchantmentAdrenalineBoost(int id, int weight) {
        super(id, weight, EnumEnchantmentType.armor_head);
        this.setName("AdrenalineBoost");
    }
    
    @Override
    public int getMinEnchantability(int par1)
    {
        return 20;
    }
    
    @Override
    public int getMaxEnchantability(int par1)
    {
        return super.getMinEnchantability(par1) + 50;
    }

    @Override
    public int getMaxLevel()
    {
        return Config.enchAdrenalineBoostLVL;
    }
}