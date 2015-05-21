package HxCKDMS.HxCEnchants.Handlers;

import HxCKDMS.HxCCore.Handlers.NBTFileIO;
import HxCKDMS.HxCCore.HxCCore;
import HxCKDMS.HxCCore.Utils.Teleporter;
import HxCKDMS.HxCEnchants.Config;
import HxCKDMS.HxCEnchants.enchantment.Enchants;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ArmorEventHandler
{
    boolean isFlying;
    //UUIDs for Attributes
    public static UUID HealthUUID = UUID.fromString("fe15f490-62d7-11e4-b116-123b93f75cba");
    public static UUID SpeedUUID = UUID.fromString("fe15f828-62d7-11e4-b116-123b93f75cba");
    public static UUID StealthUUID = UUID.fromString("1e4a1a12-ab1e-4987-b527-e0adeefc904a");

    // Integers
    int ShouldRepair = (Config.enchRepairRate * 20);
    int CanRegen = (Config.enchRegenRate * 20);

    int JumpBoostLevel;
    int VitalityLevel;
    int AdrenalineBoostLevel;
    int BattleHealingLevel;
    int WitherProt;
    int FlyLevel;
    int RegenLevel;
    int SpeedLevel;
    int StealthLevel;
    int H;
    int C;
    int L;
    int B;
    //doubles
    double SpeedBoost;
    double Vitality;

    //ItemStacks
    ItemStack ArmourHelm = null;
    ItemStack ArmourChest = null;
    ItemStack ArmourLegs = null;
    ItemStack ArmourBoots = null;

    @SubscribeEvent
    @SuppressWarnings("ConstantConditions")
	public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        ShouldRepair--;
        CanRegen--;
		if(event.entityLiving instanceof EntityPlayerMP) {
            EntityPlayerMP player = (EntityPlayerMP) event.entityLiving;

            String UUID = player.getUniqueID().toString();
            File CustomPlayerData = new File(HxCCore.HxCCoreDir, "HxC-" + UUID + ".dat");

            IAttributeInstance ph = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.maxHealth);
            IAttributeInstance ps = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.movementSpeed);

            AttributeModifier HealthBuff = new AttributeModifier(HealthUUID, "HealthBuffedChestplate", Vitality, 1);
            AttributeModifier SpeedBuff = new AttributeModifier(SpeedUUID, "SpeedBuffedPants", SpeedBoost, 1);

            ArmourHelm = player.inventory.armorItemInSlot(3);
            ArmourChest = player.inventory.armorItemInSlot(2);
            ArmourLegs = player.inventory.armorItemInSlot(1);
            ArmourBoots = player.inventory.armorItemInSlot(0);

            /** Big blob is better than 5 char lines**/
            JumpBoostLevel = 0;
            VitalityLevel = 0;AdrenalineBoostLevel = 0;
            BattleHealingLevel = 0;WitherProt = 0;
            FlyLevel = 0;RegenLevel = 0;SpeedLevel = 0;
            StealthLevel = 0;H = 0;C = 0;L = 0;B = 0;

            //Helmet Enchants
            if (Config.enchAdrenalineBoostEnable)AdrenalineBoostLevel = EnchantmentHelper.getEnchantmentLevel(Enchants.AdrenalineBoost.effectId, ArmourHelm);
            if (Config.enchWitherProtectionEnable)WitherProt = EnchantmentHelper.getEnchantmentLevel(Enchants.WitherProtection.effectId, ArmourHelm);
            if (Config.enchRegenEnable)H = EnchantmentHelper.getEnchantmentLevel(Enchants.ArmorRegen.effectId, ArmourHelm);

            //Chestplate Enchants
            if (Config.enchVitalityEnable)VitalityLevel = EnchantmentHelper.getEnchantmentLevel(Enchants.Vitality.effectId, ArmourChest);
            if (Config.enchBattleHealingEnable)BattleHealingLevel = EnchantmentHelper.getEnchantmentLevel(Enchants.BattleHealing.effectId, ArmourChest);
            if (Config.enchRegenEnable)C = EnchantmentHelper.getEnchantmentLevel(Enchants.ArmorRegen.effectId, ArmourChest);

            //Legging Enchants
            if (Config.enchSwiftnessEnable)SpeedLevel = EnchantmentHelper.getEnchantmentLevel(Enchants.Swiftness.effectId, ArmourLegs);
            if (Config.enchJumpBoostEnable)JumpBoostLevel = EnchantmentHelper.getEnchantmentLevel(Enchants.JumpBoost.effectId, ArmourLegs);
            if (Config.enchRegenEnable)L = EnchantmentHelper.getEnchantmentLevel(Enchants.ArmorRegen.effectId, ArmourLegs);

            //Boot Enchants
            if (Config.enchFlyEnable)FlyLevel = EnchantmentHelper.getEnchantmentLevel(Enchants.Fly.effectId, ArmourBoots);
            if (Config.enchStealthEnable)StealthLevel = EnchantmentHelper.getEnchantmentLevel(Enchants.Stealth.effectId, ArmourBoots);
            if (Config.enchRegenEnable)B = EnchantmentHelper.getEnchantmentLevel(Enchants.ArmorRegen.effectId, ArmourBoots);
            RegenLevel = 0;
            Vitality = 0;
            SpeedBoost = 0;
            RegenLevel = (H + C + L + B);
            Vitality = VitalityLevel * 0.5F;
            SpeedBoost = SpeedLevel * 0.2;

            //Indented for Beyond here stuff is actually done
                if(Config.enchFlyEnable){
                    boolean flyhbt = NBTFileIO.getBoolean(CustomPlayerData, "EFlyHasChanged");
                    if (FlyLevel > 0 && !player.capabilities.allowFlying){
                        player.capabilities.allowFlying = true;
                        player.sendPlayerAbilities();
                        NBTFileIO.setBoolean(CustomPlayerData, "EFlyHasChanged", true);
                    }
                    if (FlyLevel < 1 && flyhbt) {
                        player.capabilities.allowFlying = false;
                        player.capabilities.isFlying = false;
                        player.sendPlayerAbilities();
                        NBTFileIO.setBoolean(CustomPlayerData, "EFlyHasChanged", false);
                    }
                }
                if (isFlying && FlyLevel > 0 && !player.capabilities.isCreativeMode) player.worldObj.spawnParticle("smoke", player.posX + Math.random() - 0.5d, player.posY - 1.62d, player.posZ + Math.random() - 0.5d, 0.0d, 0.0d, 0.0d);

                if(!ph.func_111122_c().contains(HealthBuff) && VitalityLevel > 0) {ph.applyModifier(HealthBuff);}
                if(ph.func_111122_c().contains(HealthBuff) && VitalityLevel < 1) {ph.removeModifier(HealthBuff);}

                if(!ps.func_111122_c().contains(SpeedBuff) && SpeedLevel > 0) {ps.applyModifier(SpeedBuff);}
                if(ps.func_111122_c().contains(SpeedBuff) && SpeedLevel < 1) {ps.removeModifier(SpeedBuff);}


                if (player.getHealth() < player.getMaxHealth() && RegenLevel > 0 && CanRegen <= 0) {
                    player.heal(RegenLevel * 2);
                    CanRegen = Config.enchRegenRate * 20;
                }

                if(Config.enchRepairEnable && ShouldRepair <= 0) {
                    RepairItems(player);
                    ShouldRepair = (Config.enchRepairRate * 20);
                }

                if (!player.worldObj.isRemote){
                    Stealth(player, StealthLevel);
                }
        }
	}

    public void RepairItems(EntityPlayerMP player){
        ItemStack Inv;
        ItemStack Armor;
        for(int j = 0; j < 36; j++){
            Inv = player.inventory.getStackInSlot(j);
            if (Inv != null && Inv.isItemStackDamageable()){
                int a = EnchantmentHelper.getEnchantmentLevel(Enchants.Repair.effectId, Inv);
                int b = Inv.getItemDamage() - a;
                if (Inv.getItemDamage() < Inv.getMaxDamage())
                {
                    Inv.setItemDamage(b);
                }
            }
        }
        for(int j = 0; j < 4; j++){
            Armor = player.getCurrentArmor(j);
            if (Armor != null && Armor.isItemStackDamageable()){
                int c = EnchantmentHelper.getEnchantmentLevel(Enchants.Repair.effectId, Armor);
                int d = Armor.getItemDamage() - c;
                if (Armor.getItemDamage() < Armor.getMaxDamage())
                {
                    Armor.setItemDamage(d);
                }
            }
        }
    }

    @SubscribeEvent
    public void livingHurtEvent(LivingHurtEvent event)
    {
        if (event.entity instanceof EntityPlayerMP)
        {
            EntityPlayerMP player = (EntityPlayerMP)event.entityLiving;
            boolean allowABEffect = true;

            if (event.source.damageType.equalsIgnoreCase("wither") || event.source.damageType.equalsIgnoreCase("starve") ||event.source.damageType.equalsIgnoreCase("fall") ||event.source.damageType.equalsIgnoreCase("explosion.player") ||event.source.damageType.equalsIgnoreCase("explosion") || event.source.damageType.equalsIgnoreCase("inWall")) allowABEffect = false;
            if(WitherProt > 0 && event.source.damageType.equalsIgnoreCase("wither")) event.setCanceled(true);
            if(BattleHealingLevel > 0 && event.source.damageType.equalsIgnoreCase("generic")) player.addPotionEffect(new PotionEffect(Potion.regeneration.getId(), BattleHealingLevel * 60, BattleHealingLevel));

            if(AdrenalineBoostLevel > 0 && allowABEffect) {
                player.addPotionEffect(new PotionEffect(Potion.regeneration.getId(), 60, AdrenalineBoostLevel));
                player.addPotionEffect(new PotionEffect(Potion.damageBoost.getId(), 60, AdrenalineBoostLevel));
                player.addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), 60, AdrenalineBoostLevel));
                player.addPotionEffect(new PotionEffect(Potion.jump.getId(), 60, AdrenalineBoostLevel));
                player.addPotionEffect(new PotionEffect(Potion.resistance.getId(), 60, AdrenalineBoostLevel));
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void Stealth (EntityPlayer player, int StealthLevel) {
        int px = Math.round((float)player.posX); int py = Math.round((float)player.posY); int pz = Math.round((float) player.posZ);
        List list  = player.worldObj.getEntitiesWithinAABB(EntityMob.class, getAreaBoundingBox(px, py, pz));
        for (EntityMob entity : (List<EntityMob>) list) {
            IAttributeInstance fr = entity.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.followRange);
            AttributeModifier StealthBuff = new AttributeModifier(StealthUUID, "StealthDeBuff", (StealthLevel*-15), 1);
            fr.removeModifier(StealthBuff);
            fr.applyModifier(StealthBuff);
        }
    }

    protected AxisAlignedBB getAreaBoundingBox(float x, float y, float z) {
        return AxisAlignedBB.getBoundingBox(x - 24, y - 24, z - 24,
        /** Indented because CDO :P **/    x + 24, y + 24, z + 24);
    }

	@SubscribeEvent
	public void livingJumpEvent(LivingJumpEvent event) {
		if(event.entityLiving instanceof EntityPlayer && JumpBoostLevel > 0) {
			EntityPlayer player = (EntityPlayer) event.entityLiving;
            double JumpBuff = player.motionY + 0.1 * JumpBoostLevel;
            player.motionY += JumpBuff;
		}
	}
    int DivineInterventionLevel;
    @SubscribeEvent
    @SuppressWarnings("unchecked")
    public void LivingHurtEvent(LivingHurtEvent event){
        Entity hurtEntity = event.entity;
        if (hurtEntity instanceof EntityPlayerMP){
            EntityPlayerMP player = (EntityPlayerMP) hurtEntity;
            ArmourChest = player.inventory.armorItemInSlot(2);
            if (Config.enchDivineInterventionEnable) DivineInterventionLevel = EnchantmentHelper.getEnchantmentLevel(Enchants.DivineIntervention.effectId, ArmourChest);
            if (DivineInterventionLevel > 0){
                if (player.prevHealth - event.ammount <= 1) {
                    player.heal(5);
                    int x, y, z;
                        if (player.getBedLocation(0) != null) {
                            x = player.getBedLocation(0).posX;
                            y = player.getBedLocation(0).posY;
                            z = player.getBedLocation(0).posZ;
                        } else {
                            ChunkCoordinates coords = HxCCore.server.worldServerForDimension(0).getSpawnPoint();
                            x = coords.posX;
                            y = coords.posY;
                            z = coords.posZ;
                        }
                        if (player.dimension != 0)Teleporter.transferPlayerToDimension(player, 0, x, y, z);
                        else player.playerNetServerHandler.setPlayerLocation(x, y, z, 90, 0);
                    Map<Integer, Integer> enchs = EnchantmentHelper.getEnchantments(ArmourChest);
                    enchs.remove(Config.enchDivineInterventionID);
                    if (DivineInterventionLevel > 1) enchs.put(Config.enchDivineInterventionID, DivineInterventionLevel - 1);
                    EnchantmentHelper.setEnchantments(enchs, ArmourChest);
                }
            }
        }
    }
}
