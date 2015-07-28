package HxCKDMS.HxCEnchants.Handlers;

import HxCKDMS.HxCCore.Handlers.NBTFileIO;
import HxCKDMS.HxCCore.HxCCore;
import HxCKDMS.HxCCore.api.Utils.AABBUtils;
import HxCKDMS.HxCCore.api.Utils.Teleporter;
import HxCKDMS.HxCEnchants.Configurations;
import HxCKDMS.HxCEnchants.EnchantConfigHandler;
import HxCKDMS.HxCEnchants.enchantment.Enchants;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
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
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@SuppressWarnings("all")
public class ArmorEventHandler {
    //UUIDs for Attributes
    public static UUID HealthUUID = UUID.fromString("fe15f490-62d7-11e4-b116-123b93f75cba"),
            SpeedUUID = UUID.fromString("fe15f828-62d7-11e4-b116-123b93f75cba"),
            StealthUUID = UUID.fromString("1e4a1a12-ab1e-4987-b527-e0adeefc904a");

    private int ShouldRepair = 60, CanRegen = 60, flyTimer = 1200, swiftTimer = 600, vitTimer = 600, stealthTimer = 600;

    private int tickTimer = Configurations.updateTime;

    @SubscribeEvent
	public void playerTickEvent(TickEvent.PlayerTickEvent event) {
        tickTimer--;
        if (tickTimer <= 0) {
            tickTimer = Configurations.updateTime;
            double SpeedBoost, Vitality;
            int VitalityLevel, FlyLevel, RegenLevel, SpeedLevel, StealthLevel, H = 0, C = 0, L = 0, B = 0;
            EntityPlayer player = event.player;
            ShouldRepair--;
            CanRegen--;

            String UUID = player.getUniqueID().toString();
            File CustomPlayerData = new File(HxCCore.HxCCoreDir, "HxC-" + UUID + ".dat");

            ItemStack ArmourHelm = player.inventory.armorItemInSlot(3),
                    ArmourChest = player.inventory.armorItemInSlot(2),
                    ArmourLegs = player.inventory.armorItemInSlot(1),
                    ArmourBoots = player.inventory.armorItemInSlot(0);

            long HChrg = 0, CChrg = 0, LChrg = 0, BChrg = 0;
            if (Configurations.enableChargesSystem) {
                if (ArmourHelm != null && ArmourHelm.getTagCompound() != null)
                    HChrg = ArmourHelm.getTagCompound().getLong("HxCEnchantCharge");
                if (ArmourChest != null && ArmourChest.getTagCompound() != null)
                    CChrg = ArmourChest.getTagCompound().getLong("HxCEnchantCharge");
                if (ArmourLegs != null && ArmourLegs.getTagCompound() != null)
                    LChrg = ArmourLegs.getTagCompound().getLong("HxCEnchantCharge");
                if (ArmourBoots != null && ArmourBoots.getTagCompound() != null)
                    BChrg = ArmourBoots.getTagCompound().getLong("HxCEnchantCharge");
            }

            //Chestplate Enchants
            if (EnchantConfigHandler.isEnabled("Vitality", "armor") && ArmourChest != null) {
                vitTimer--;
                IAttributeInstance ph = player.getEntityAttribute(SharedMonsterAttributes.maxHealth);
                VitalityLevel = EnchantmentHelper.getEnchantmentLevel(Enchants.Vitality.effectId, ArmourChest);
                Vitality = VitalityLevel * 0.5F;
                AttributeModifier HealthBuff = new AttributeModifier(HealthUUID, "HealthBuffedChestplate", Vitality, 1);
                if (!ph.func_111122_c().contains(HealthBuff) && VitalityLevel != 0 && (CChrg > EnchantConfigHandler.getData("Vitality", "armor")[4] || !Configurations.enableChargesSystem))
                    ph.applyModifier(HealthBuff);
                if (ph.func_111122_c().contains(HealthBuff) && (VitalityLevel == 0 || (CChrg < EnchantConfigHandler.getData("Vitality", "armor")[4]) || !Configurations.enableChargesSystem))
                    ph.removeModifier(HealthBuff);

                if (vitTimer <= 0 && Configurations.enableChargesSystem) {
                    ArmourChest.getTagCompound().setLong("HxCEnchantCharge", CChrg - EnchantConfigHandler.getData("Vitality", "armor")[4]);
                    vitTimer = 600;
                }
            }

            //Legging Enchants
            if (EnchantConfigHandler.isEnabled("Swiftness", "armor") && ArmourLegs != null) {
                swiftTimer--;
                IAttributeInstance ps = player.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
                SpeedLevel = EnchantmentHelper.getEnchantmentLevel(Enchants.Swiftness.effectId, ArmourLegs);
                SpeedBoost = SpeedLevel * 0.2;
                AttributeModifier SpeedBuff = new AttributeModifier(SpeedUUID, "SpeedBuffedPants", SpeedBoost, 1);
                if (!ps.func_111122_c().contains(SpeedBuff) && SpeedLevel != 0 && (LChrg > EnchantConfigHandler.getData("Swiftness", "armor")[4] || !Configurations.enableChargesSystem))
                    ps.applyModifier(SpeedBuff);
                if (ps.func_111122_c().contains(SpeedBuff) && (SpeedLevel == 0 || (LChrg < EnchantConfigHandler.getData("Swiftness", "armor")[4] || !Configurations.enableChargesSystem)))
                    ps.removeModifier(SpeedBuff);

                if (swiftTimer <= 0 && Configurations.enableChargesSystem && LChrg > EnchantConfigHandler.getData("Swiftness", "armor")[4]) {
                    ArmourLegs.getTagCompound().setLong("HxCEnchantCharge", LChrg - EnchantConfigHandler.getData("Swiftness", "armor")[4]);
                    swiftTimer = 600;
                }
            }


            //Boot Enchants
            if (EnchantConfigHandler.isEnabled("Fly", "armor") && ArmourBoots != null) {
                FlyLevel = EnchantmentHelper.getEnchantmentLevel(Enchants.Fly.effectId, ArmourBoots);
                if (FlyLevel > 0 && player.capabilities.isFlying && !player.capabilities.isCreativeMode)
                    flyTimer--;

                if (flyTimer <= 0 && Configurations.enableChargesSystem && BChrg > EnchantConfigHandler.getData("Fly", "armor")[4]) {
                    flyTimer = 1200;
                    ArmourBoots.getTagCompound().setLong("HxCEnchantCharge", BChrg - EnchantConfigHandler.getData("Fly", "armor")[4]);
                }

                boolean flyhbt = NBTFileIO.getBoolean(CustomPlayerData, "EFlyHasChanged");
                if (FlyLevel > 0 && !player.capabilities.allowFlying && (BChrg > EnchantConfigHandler.getData("Fly", "armor")[4] * 2 || !Configurations.enableChargesSystem)) {
                    player.capabilities.allowFlying = true;
                    player.sendPlayerAbilities();
                    NBTFileIO.setBoolean(CustomPlayerData, "EFlyHasChanged", true);
                    if (Configurations.enableChargesSystem)
                        ArmourBoots.getTagCompound().setLong("HxCEnchantCharge", BChrg - EnchantConfigHandler.getData("Fly", "armor")[4]);
                }
                if ((FlyLevel < 1 && flyhbt) || (FlyLevel > 0 && BChrg < EnchantConfigHandler.getData("Fly", "armor")[4])) {
                    player.capabilities.allowFlying = false;
                    player.capabilities.isFlying = false;
                    player.sendPlayerAbilities();
                    NBTFileIO.setBoolean(CustomPlayerData, "EFlyHasChanged", false);
                }
                if (player.capabilities.isFlying && FlyLevel > 0 && !player.capabilities.isCreativeMode)
                    player.worldObj.spawnParticle("smoke", player.posX + Math.random() - 0.5d,
                            player.posY - 1.62d, player.posZ + Math.random() - 0.5d, 0.0d, 0.0d, 0.0d);
            }

            if (EnchantConfigHandler.isEnabled("Stealth", "armor") && ArmourBoots != null) {
                stealthTimer--;
                StealthLevel = EnchantmentHelper.getEnchantmentLevel(Enchants.Stealth.effectId, ArmourBoots);

                Stealth(player, StealthLevel);

                if (stealthTimer <= 0 && Configurations.enableChargesSystem) {
                    ArmourBoots.getTagCompound().setLong("HxCEnchantCharge", BChrg - EnchantConfigHandler.getData("Stealth", "armor")[4]);
                    stealthTimer = 600;
                }
            }


            if (EnchantConfigHandler.isEnabled("Repair", "other") && ShouldRepair <= 0) {
                RepairItems(player);
                ShouldRepair = (EnchantConfigHandler.getData("Repair", "other")[5] * 20);
            }

            if (EnchantConfigHandler.isEnabled("Regen", "armor") && CanRegen <= 0) {
                RegenLevel = 0;
                if (ArmourHelm != null)
                    H = EnchantmentHelper.getEnchantmentLevel(Enchants.ArmorRegen.effectId, ArmourHelm);
                if (ArmourBoots != null)
                    B = EnchantmentHelper.getEnchantmentLevel(Enchants.ArmorRegen.effectId, ArmourBoots);
                if (ArmourChest != null)
                    C = EnchantmentHelper.getEnchantmentLevel(Enchants.ArmorRegen.effectId, ArmourChest);
                if (ArmourLegs != null)
                    L = EnchantmentHelper.getEnchantmentLevel(Enchants.ArmorRegen.effectId, ArmourLegs);

                if (H > 0) RegenLevel += 1;
                if (B > 0) RegenLevel += 1;
                if (C > 0) RegenLevel += 1;
                if (L > 0) RegenLevel += 1;

                if (player.getHealth() < player.getMaxHealth() && RegenLevel > 0) {
                    float hp = player.getMaxHealth() - player.getHealth();
                    CanRegen = EnchantConfigHandler.getData("Regen", "armor")[4] * 20;
                    if (H > 0) {
                        if (Configurations.enableChargesSystem)
                            ArmourChest.getTagCompound().setLong("HxCEnchantCharge", HChrg - H * EnchantConfigHandler.getData("Regen", "armor")[4]);
                        player.heal(H / 2);
                    }
                    if (C > 0 && CChrg > (hp * 2) / RegenLevel) {
                        if (Configurations.enableChargesSystem)
                            ArmourChest.getTagCompound().setLong("HxCEnchantCharge", CChrg - C * EnchantConfigHandler.getData("Regen", "armor")[4]);
                        player.heal(C / 2);
                    }
                    if (L > 0 && LChrg > (hp * 2) / RegenLevel) {
                        if (Configurations.enableChargesSystem)
                            ArmourLegs.getTagCompound().setLong("HxCEnchantCharge", LChrg - L * EnchantConfigHandler.getData("Regen", "armor")[4]);
                        player.heal(L / 2);
                    }
                    if (B > 0 && BChrg > (hp * 2) / RegenLevel) {
                        if (Configurations.enableChargesSystem)
                            ArmourBoots.getTagCompound().setLong("HxCEnchantCharge", BChrg - B * EnchantConfigHandler.getData("Regen", "armor")[4]);
                        player.heal(B / 2);
                    }
                }
            }
        }

        if (event.player.inventory.armorItemInSlot(0) != null && event.player.inventory.armorItemInSlot(0).isItemEnchanted() && event.player.motionY < -0.8 && !event.player.isSneaking()) {
            int tmp = 0, tmp2 = 0;
            if (EnchantConfigHandler.isEnabled("FeatherFall", "armor") && event.player.inventory.armorItemInSlot(0).getTagCompound().getLong("HxCEnchantCharge") > EnchantConfigHandler.getData("FeatherFall", "armor")[4])
                tmp = EnchantmentHelper.getEnchantmentLevel(Enchants.FeatherFall.effectId, event.player.inventory.armorItemInSlot(0));
            if (EnchantConfigHandler.isEnabled("MeteorFall", "armor") && event.player.inventory.armorItemInSlot(0).getTagCompound().getLong("HxCEnchantCharge") > EnchantConfigHandler.getData("MeteorFall", "armor")[4])
                tmp2 = EnchantmentHelper.getEnchantmentLevel(Enchants.MeteorFall.effectId, event.player.inventory.armorItemInSlot(0));

            if (tmp > 0) {
                event.player.motionY += (0.01f * (tmp / 2));
                if (Configurations.enableChargesSystem)
                    event.player.inventory.armorItemInSlot(0).getTagCompound().setLong("HxCEnchantCharge", event.player.inventory.armorItemInSlot(0).getTagCompound().getLong("HxCEnchantCharge") - EnchantConfigHandler.getData("FeatherFall", "armor")[4]);
            }
            if (tmp2 > 0) {
                event.player.motionY -= (0.02f * tmp2);
                if (Configurations.enableChargesSystem)
                    event.player.inventory.armorItemInSlot(0).getTagCompound().setLong("HxCEnchantCharge", event.player.inventory.armorItemInSlot(0).getTagCompound().getLong("HxCEnchantCharge") - EnchantConfigHandler.getData("MeteorFall", "armor")[4]);
            }
        }
	}

    public void RepairItems(EntityPlayer player){
        ItemStack Inv;
        ItemStack Armor;
        long tmp = 0;
        for(int j = 0; j < 36; j++){
            Inv = player.inventory.getStackInSlot(j);
            if (Inv != null && Inv.isItemStackDamageable() && Inv.getTagCompound() != null){
                if (Configurations.enableChargesSystem)
                    tmp = Inv.getTagCompound().getLong("HxCEnchantCharge");
                int a = EnchantmentHelper.getEnchantmentLevel(Enchants.Repair.effectId, Inv);
                int b = Inv.getItemDamage() - a;
                if (Inv.getItemDamage() > 0 && (tmp >= Inv.getItemDamage() || !Configurations.enableChargesSystem)) {
                    Inv.setItemDamage(b);
                    if (Configurations.enableChargesSystem)
                        Inv.getTagCompound().setLong("HxCEnchantCharge", tmp - a * EnchantConfigHandler.getData("Repair", "other")[4]);
                }
            }
        }
        for(int j = 0; j < 4; j++){
            Armor = player.getCurrentArmor(j);
            if (Armor != null && Armor.isItemStackDamageable() && Armor.getTagCompound() != null){
                if (Configurations.enableChargesSystem)
                    tmp = Armor.getTagCompound().getLong("HxCEnchantCharge");
                int c = EnchantmentHelper.getEnchantmentLevel(Enchants.Repair.effectId, Armor);
                int d = Armor.getItemDamage() - c;
                if (Armor.getItemDamage() > 0 && (tmp >= Armor.getItemDamage() || !Configurations.enableChargesSystem)) {
                    Armor.setItemDamage(d);
                    if (Configurations.enableChargesSystem)
                        Armor.getTagCompound().setLong("HxCEnchantCharge", tmp - c * EnchantConfigHandler.getData("Repair", "other")[4]);
                }
            }
        }
    }

    @SubscribeEvent
    public void livingHurtEvent(LivingHurtEvent event) {
        if (event.entity instanceof EntityPlayerMP) {
            EntityPlayerMP player = (EntityPlayerMP)event.entityLiving;
            boolean allowABEffect = true;
            ItemStack ArmourHelm = player.inventory.armorItemInSlot(3),
                    ArmourChest = player.inventory.armorItemInSlot(2);

            int AdrenalineBoostLevel = 0, BattleHealingLevel = 0, WitherProt = 0;

            if (EnchantConfigHandler.isEnabled("BattleHealing", "armor") && ArmourChest != null)
                BattleHealingLevel = EnchantmentHelper.getEnchantmentLevel(Enchants.BattleHealing.effectId, ArmourChest);
            if (EnchantConfigHandler.isEnabled("AdrenalineBoost", "armor") && ArmourHelm != null)
                AdrenalineBoostLevel = EnchantmentHelper.getEnchantmentLevel(Enchants.AdrenalineBoost.effectId, ArmourHelm);
            if (EnchantConfigHandler.isEnabled("WitherProtection", "armor") && ArmourHelm != null)
                WitherProt = EnchantmentHelper.getEnchantmentLevel(Enchants.WitherProtection.effectId, ArmourHelm);

            if (event.source.damageType.equalsIgnoreCase("wither") || event.source.damageType.equalsIgnoreCase("starve") ||event.source.damageType.equalsIgnoreCase("fall") ||event.source.damageType.equalsIgnoreCase("explosion.player") ||event.source.damageType.equalsIgnoreCase("explosion") || event.source.damageType.equalsIgnoreCase("inWall"))
                allowABEffect = false;
            if (WitherProt > 0 && event.source.damageType.equalsIgnoreCase("wither") && (ArmourHelm.getTagCompound().getLong("HxCEnchantCharge") > EnchantConfigHandler.getData("WitherProtection", "armor")[4] || !Configurations.enableChargesSystem)) {
                player.removePotionEffect(Potion.wither.getId());
                event.setCanceled(true);
                if (Configurations.enableChargesSystem)
                    ArmourHelm.getTagCompound().setLong("HxCEnchantCharge", ArmourHelm.getTagCompound().getLong("HxCEnchantCharge") - EnchantConfigHandler.getData("WitherProtection", "armor")[4]);
            }
            if (BattleHealingLevel > 0 && event.source.damageType.equalsIgnoreCase("generic")) {
                player.addPotionEffect(new PotionEffect(Potion.regeneration.getId(), BattleHealingLevel * 60, BattleHealingLevel));
                if (Configurations.enableChargesSystem) {
                    assert ArmourHelm != null;
                    ArmourHelm.getTagCompound().setLong("HxCEnchantCharge", ArmourHelm.getTagCompound().getLong("HxCEnchantCharge") - EnchantConfigHandler.getData("BattleHealing", "armor")[4]);
                }
            }

            if(AdrenalineBoostLevel > 0 && allowABEffect && (ArmourHelm.getTagCompound().getLong("HxCEnchantCharge") > EnchantConfigHandler.getData("AdrenalineBoost", "armor")[4] || !Configurations.enableChargesSystem)) {
                player.addPotionEffect(new PotionEffect(Potion.regeneration.getId(), 60, AdrenalineBoostLevel));
                player.addPotionEffect(new PotionEffect(Potion.damageBoost.getId(), 60, AdrenalineBoostLevel));
                player.addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), 60, AdrenalineBoostLevel));
                player.addPotionEffect(new PotionEffect(Potion.jump.getId(), 60, AdrenalineBoostLevel));
                player.addPotionEffect(new PotionEffect(Potion.resistance.getId(), 60, AdrenalineBoostLevel));
                if (Configurations.enableChargesSystem)
                    ArmourHelm.getTagCompound().setLong("HxCEnchantCharge", ArmourHelm.getTagCompound().getLong("HxCEnchantCharge") - EnchantConfigHandler.getData("AdrenalineBoost", "armor")[4]);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void Stealth (EntityPlayer player, int StealthLevel) {
        int px = Math.round((float)player.posX); int py = Math.round((float)player.posY); int pz = Math.round((float) player.posZ);
        List list  = player.worldObj.getEntitiesWithinAABB(EntityMob.class, AABBUtils.getAreaBoundingBox(px, py, pz, 24));
        for (EntityMob entity : (List<EntityMob>) list) {
            IAttributeInstance fr = entity.getEntityAttribute(SharedMonsterAttributes.followRange);
            AttributeModifier StealthBuff = new AttributeModifier(StealthUUID, "StealthDeBuff", (fr.getBaseValue()-StealthLevel), 1);
            fr.removeModifier(StealthBuff);
            fr.applyModifier(StealthBuff);
        }
    }

	@SubscribeEvent
	public void livingJumpEvent(LivingEvent.LivingJumpEvent event) {
		if(event.entityLiving instanceof EntityPlayer && ((EntityPlayer) event.entityLiving).inventory.armorItemInSlot(1) != null) {
            ItemStack boots = ((EntityPlayer) event.entityLiving).inventory.armorItemInSlot(1);
            int JumpBoostLevel = EnchantmentHelper.getEnchantmentLevel(Enchants.JumpBoost.effectId, boots);
            if (JumpBoostLevel > 0 && (boots.getTagCompound().getLong("HxCEnchantCharge") > EnchantConfigHandler.getData("JumpBoost", "armor")[4] || !Configurations.enableChargesSystem)) {
                EntityPlayer player = (EntityPlayer) event.entityLiving;
                double JumpBuff = player.motionY + 0.1 * JumpBoostLevel;
                player.motionY += JumpBuff;
                if (Configurations.enableChargesSystem)
                    boots.getTagCompound().setLong("HxCEnchantCharge", boots.getTagCompound().getLong("HxCEnchantCharge") - EnchantConfigHandler.getData("JumpBoost", "armor")[4]);
            }
		}
	}

    @SubscribeEvent
    public void LivingHurtEvent(LivingHurtEvent event){
        Entity hurtEntity = event.entity;
        if (hurtEntity instanceof EntityPlayerMP){
            EntityPlayerMP player = (EntityPlayerMP) hurtEntity;
            ItemStack ArmourChest = player.inventory.armorItemInSlot(2);
            int DivineInterventionLevel = 0;
            assert ArmourChest != null;
            if (EnchantConfigHandler.isEnabled("DivineIntervention", "armor"));
                DivineInterventionLevel = EnchantmentHelper.getEnchantmentLevel(Enchants.DivineIntervention.effectId, ArmourChest);
            if (DivineInterventionLevel > 0) {
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
                        if (player.dimension != 0) Teleporter.transferPlayerToDimension(player, 0, x, y, z);
                        else player.playerNetServerHandler.setPlayerLocation(x, y, z, 90, 0);
                    Map<Integer, Integer> enchs = EnchantmentHelper.getEnchantments(ArmourChest);
                    enchs.remove(EnchantConfigHandler.getData("DivineIntervention", "armor")[0]);
                    if (DivineInterventionLevel > 1) enchs.put(EnchantConfigHandler.getData("DivineIntevention", "armor")[0], DivineInterventionLevel - 1);
                    EnchantmentHelper.setEnchantments(enchs, ArmourChest);
                }
            }
        }
    }

    @SubscribeEvent
    public void livingFallEvent(LivingFallEvent event) {
        if (event.entityLiving instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)event.entityLiving;
            if (player.inventory.armorItemInSlot(0) != null && player.inventory.armorItemInSlot(0).isItemEnchanted()) {
                ItemStack boots = player.inventory.armorItemInSlot(0);
                int meh = 0, meh2 = 0;
                if (EnchantConfigHandler.isEnabled("FeatherFall", "armor"))
                    meh = EnchantmentHelper.getEnchantmentLevel(Enchants.FeatherFall.effectId, boots);
                if (EnchantConfigHandler.isEnabled("MeteorFall", "armor"))
                    meh2 = EnchantmentHelper.getEnchantmentLevel(Enchants.MeteorFall.effectId, boots);

                if (meh < 4 && meh > 0)event.distance /= meh;
                else if (meh > 4) event.distance = 0;

                if (meh2 > 0 && event.distance > 10) {
                    player.worldObj.createExplosion(player, player.posX, player.posY, player.posZ, event.distance/2 * meh2, false);
                    event.distance = 0;
                }
            }
        }
    }
}
