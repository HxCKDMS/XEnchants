package HxCKDMS.HxCEnchants.Configurations;

import HxCKDMS.HxCCore.api.Configuration.Config;
import HxCKDMS.HxCCore.api.Configuration.Config.*;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
@Config
@SuppressWarnings("all")
public class Configurations {
    public static boolean ExplosionDestroysTerrain = false, AurasAffectPlayers = true,  enableChargesSystem = true, enableCustomBlocks;
    public static float PiercingPercent = 0.15f, GaiasAuraSpeed = 2.5f, SpeedTweak = 0.0387f;
    public static int updateTime = 10, guiVersion = 1, repairTimer = 120, regenTimer = 45, tableRange = 3;
    public static short StartingID=400;
    public static boolean notice = true, blacklistEnchantsFromEnchantingPlus = false, EnableKeybinds = true, EnableCoordinatesInGUIs = false;

    public static List<String> VoidedItems = Arrays.asList(new String[]{"minecraft:cobblestone", "minecraft:dirt", "minecraft:gravel"});

    @comment("If false, the enchant won't even exist in the game.")
    public static LinkedHashMap<String, Boolean> EnabledEnchants = new LinkedHashMap<>();
    @comment("If deleted will regenerate starting at chosen ID.")
    public static LinkedHashMap<String, Short> EnchantIDs = new LinkedHashMap<>();
    @comment("Max level is 120")
    public static LinkedHashMap<String, Byte> EnchantLevels = new LinkedHashMap<>();
    @comment("The odds of getting the enchant")
    public static LinkedHashMap<String, Byte> EnchantWeight = new LinkedHashMap<>();
    @comment("The cost of the enchant")
    public static LinkedHashMap<String, Byte> EnchantCost = new LinkedHashMap<>();
    @comment("The charge cost of the enchant")
    public static LinkedHashMap<String, Integer> EnchantChargeNeeded = new LinkedHashMap<>();

    //Enabled, ID, Level, Weight, Cost, Charge Cost
    static {
        EnabledEnchants.put("Bound", true);
        EnabledEnchants.put("FlameTouch", true);
        EnabledEnchants.put("Repair", true);
        EnabledEnchants.put("EarthEater", true);
        EnabledEnchants.put("SpeedMine", true);
        EnabledEnchants.put("VoidTouch", true);
        EnabledEnchants.put("AdrenalineBoost", true);
        EnabledEnchants.put("AuraFiery", true);
        EnabledEnchants.put("AuraDeadly", true);
        EnabledEnchants.put("AuraDark", true);
        EnabledEnchants.put("AuraThick", true);
        EnabledEnchants.put("AuraToxic", true);
        EnabledEnchants.put("BattleHealing", true);
        EnabledEnchants.put("DivineIntervention", true);
        EnabledEnchants.put("Fly", true);
        EnabledEnchants.put("JumpBoost", true);
        EnabledEnchants.put("Regen", true);
        EnabledEnchants.put("Shroud", true);
        EnabledEnchants.put("Stealth", true);
        EnabledEnchants.put("Swiftness", true);
        EnabledEnchants.put("Vitality", true);
        EnabledEnchants.put("WitherProtection", true);
        EnabledEnchants.put("MeteorFall", true);
        EnabledEnchants.put("FeatherFall", true);
        EnabledEnchants.put("FlashStep", true);
        EnabledEnchants.put("HealingAura", true);
        EnabledEnchants.put("RepulsiveAura", true);
        EnabledEnchants.put("AuraMagnetic", true);
        EnabledEnchants.put("GaiaAura", true);
        EnabledEnchants.put("IcyAura", true);
        EnabledEnchants.put("Gluttony", true);
        EnabledEnchants.put("ExplosiveDischarge", true);
        EnabledEnchants.put("ChargedAura", true);
        EnabledEnchants.put("Nightvision", true);
        EnabledEnchants.put("LightningArrow", true);
        EnabledEnchants.put("ArrowExplosive", true);
        EnabledEnchants.put("ArrowZeus", true);
        EnabledEnchants.put("ArrowSeeking", true);
        EnabledEnchants.put("LifeSteal", true);
        EnabledEnchants.put("Piercing", true);
        EnabledEnchants.put("Poison", true);
        EnabledEnchants.put("SoulTear", true);
        EnabledEnchants.put("SCurse", true);
        EnabledEnchants.put("Vampirism", true);
        EnabledEnchants.put("Vorpal", true);
        EnabledEnchants.put("OverCharge", true);
        EnabledEnchants.put("EnchLeech", true);
        EnabledEnchants.put("Examine", true);
        EnabledEnchants.put("FlamingArrow", true);
        EnabledEnchants.put("BloodRazor", true);

        EnchantIDs.put("Bound", (short) (StartingID+EnchantIDs.size()));
        EnchantIDs.put("FlameTouch", (short) (StartingID+EnchantIDs.size()));
        EnchantIDs.put("Repair", (short) (StartingID+EnchantIDs.size()));
        EnchantIDs.put("EarthEater", (short) (StartingID+EnchantIDs.size()));
        EnchantIDs.put("SpeedMine", (short) (StartingID+EnchantIDs.size()));
        EnchantIDs.put("VoidTouch", (short) (StartingID+EnchantIDs.size()));
        EnchantIDs.put("AdrenalineBoost", (short) (StartingID+EnchantIDs.size()));
        EnchantIDs.put("AuraFiery", (short) (StartingID+EnchantIDs.size()));
        EnchantIDs.put("AuraDeadly", (short) (StartingID+EnchantIDs.size()));
        EnchantIDs.put("AuraDark", (short) (StartingID+EnchantIDs.size()));
        EnchantIDs.put("AuraThick", (short) (StartingID+EnchantIDs.size()));
        EnchantIDs.put("AuraToxic", (short) (StartingID+EnchantIDs.size()));
        EnchantIDs.put("BattleHealing", (short) (StartingID+EnchantIDs.size()));
        EnchantIDs.put("DivineIntervention", (short) (StartingID+EnchantIDs.size()));
        EnchantIDs.put("Fly", (short) (StartingID+EnchantIDs.size()));
        EnchantIDs.put("JumpBoost", (short) (StartingID+EnchantIDs.size()));
        EnchantIDs.put("Regen", (short) (StartingID+EnchantIDs.size()));
        EnchantIDs.put("Shroud", (short) (StartingID+EnchantIDs.size()));
        EnchantIDs.put("Stealth", (short) (StartingID+EnchantIDs.size()));
        EnchantIDs.put("Swiftness", (short) (StartingID+EnchantIDs.size()));
        EnchantIDs.put("Vitality", (short) (StartingID+EnchantIDs.size()));
        EnchantIDs.put("WitherProtection", (short) (StartingID+EnchantIDs.size()));
        EnchantIDs.put("MeteorFall", (short) (StartingID+EnchantIDs.size()));
        EnchantIDs.put("FeatherFall", (short) (StartingID+EnchantIDs.size()));
        EnchantIDs.put("FlashStep", (short) (StartingID+EnchantIDs.size()));
        EnchantIDs.put("HealingAura", (short) (StartingID+EnchantIDs.size()));
        EnchantIDs.put("RepulsiveAura", (short) (StartingID+EnchantIDs.size()));
        EnchantIDs.put("AuraMagnetic", (short) (StartingID+EnchantIDs.size()));
        EnchantIDs.put("GaiaAura", (short) (StartingID+EnchantIDs.size()));
        EnchantIDs.put("IcyAura", (short) (StartingID+EnchantIDs.size()));
        EnchantIDs.put("Gluttony", (short) (StartingID+EnchantIDs.size()));
        EnchantIDs.put("ExplosiveDischarge", (short) (StartingID+EnchantIDs.size()));
        EnchantIDs.put("ChargedAura", (short) (StartingID+EnchantIDs.size()));
        EnchantIDs.put("Nightvision", (short) (StartingID+EnchantIDs.size()));
        EnchantIDs.put("LightningArrow", (short) (StartingID+EnchantIDs.size()));
        EnchantIDs.put("ArrowExplosive", (short) (StartingID+EnchantIDs.size()));
        EnchantIDs.put("ArrowZeus", (short) (StartingID+EnchantIDs.size()));
        EnchantIDs.put("ArrowSeeking", (short) (StartingID+EnchantIDs.size()));
        EnchantIDs.put("LifeSteal", (short) (StartingID+EnchantIDs.size()));
        EnchantIDs.put("Piercing", (short) (StartingID+EnchantIDs.size()));
        EnchantIDs.put("Poison", (short) (StartingID+EnchantIDs.size()));
        EnchantIDs.put("SoulTear", (short) (StartingID+EnchantIDs.size()));
        EnchantIDs.put("SCurse", (short) (StartingID+EnchantIDs.size()));
        EnchantIDs.put("Vampirism", (short) (StartingID+EnchantIDs.size()));
        EnchantIDs.put("Vorpal", (short) (StartingID+EnchantIDs.size()));
        EnchantIDs.put("OverCharge", (short) (StartingID+EnchantIDs.size()));
        EnchantIDs.put("EnchLeech", (short) (StartingID+EnchantIDs.size()));
        EnchantIDs.put("Examine", (short) (StartingID+EnchantIDs.size()));
        EnchantIDs.put("FlamingArrow", (short) (StartingID+EnchantIDs.size()));
        EnchantIDs.put("BloodRazor", (short) (StartingID+EnchantIDs.size()));

        EnchantLevels.put("Bound", (byte) 3);
        EnchantLevels.put("FlameTouch", (byte) 4);
        EnchantLevels.put("Repair", (byte) 4);
        EnchantLevels.put("EarthEater", (byte) 10);
        EnchantLevels.put("SpeedMine", (byte) 4);
        EnchantLevels.put("VoidTouch", (byte) 1);
        EnchantLevels.put("AdrenalineBoost", (byte) 4);
        EnchantLevels.put("AuraFiery", (byte) 10);
        EnchantLevels.put("AuraDeadly", (byte) 10);
        EnchantLevels.put("AuraDark", (byte) 10);
        EnchantLevels.put("AuraThick", (byte) 10);
        EnchantLevels.put("AuraToxic", (byte) 10);
        EnchantLevels.put("BattleHealing", (byte) 4);
        EnchantLevels.put("DivineIntervention", (byte) 5);
        EnchantLevels.put("Fly", (byte) 1);
        EnchantLevels.put("JumpBoost", (byte) 4);
        EnchantLevels.put("Regen", (byte) 4);
        EnchantLevels.put("Shroud", (byte) 1);
        EnchantLevels.put("Stealth", (byte) 2);
        EnchantLevels.put("Swiftness", (byte) 4);
        EnchantLevels.put("Vitality", (byte) 4);
        EnchantLevels.put("WitherProtection", (byte) 4);
        EnchantLevels.put("MeteorFall", (byte) 10);
        EnchantLevels.put("FeatherFall", (byte) 10);
        EnchantLevels.put("FlashStep", (byte) 10);
        EnchantLevels.put("HealingAura", (byte) 10);
        EnchantLevels.put("RepulsiveAura", (byte) 10);
        EnchantLevels.put("AuraMagnetic", (byte) 10);
        EnchantLevels.put("GaiaAura", (byte) 10);
        EnchantLevels.put("IcyAura", (byte) 10);
        EnchantLevels.put("Gluttony", (byte) 20);
        EnchantLevels.put("ExplosiveDischarge", (byte) 10);
        EnchantLevels.put("ChargedAura", (byte) 10);
        EnchantLevels.put("Nightvision", (byte) 1);
        EnchantLevels.put("LightningArrow", (byte) 5);
        EnchantLevels.put("ArrowExplosive", (byte) 4);
        EnchantLevels.put("ArrowZeus", (byte) 1);
        EnchantLevels.put("ArrowSeeking", (byte) 4);
        EnchantLevels.put("LifeSteal", (byte) 5);
        EnchantLevels.put("Piercing", (byte) 5);
        EnchantLevels.put("Poison", (byte) 4);
        EnchantLevels.put("SoulTear", (byte) 4);
        EnchantLevels.put("SCurse", (byte) 10);
        EnchantLevels.put("Vampirism", (byte) 5);
        EnchantLevels.put("Vorpal", (byte) 10);
        EnchantLevels.put("OverCharge", (byte) 4);
        EnchantLevels.put("EnchLeech", (byte) 4);
        EnchantLevels.put("Examine", (byte) 10);
        EnchantLevels.put("FlamingArrow", (byte) 3);
        EnchantLevels.put("BloodRazor", (byte) 5);

        EnchantWeight.put("Bound", (byte) 5);
        EnchantWeight.put("FlameTouch", (byte) 2);
        EnchantWeight.put("Repair", (byte) 1);
        EnchantWeight.put("EarthEater", (byte) 2);
        EnchantWeight.put("SpeedMine", (byte) 2);
        EnchantWeight.put("VoidTouch", (byte) 2);
        EnchantWeight.put("AdrenalineBoost", (byte) 10);
        EnchantWeight.put("AuraFiery", (byte) 5);
        EnchantWeight.put("AuraDeadly", (byte) 1);
        EnchantWeight.put("AuraDark", (byte) 15);
        EnchantWeight.put("AuraThick", (byte) 10);
        EnchantWeight.put("AuraToxic", (byte) 5);
        EnchantWeight.put("BattleHealing", (byte) 3);
        EnchantWeight.put("DivineIntervention", (byte) 1);
        EnchantWeight.put("Fly", (byte) 1);
        EnchantWeight.put("JumpBoost", (byte) 10);
        EnchantWeight.put("Regen", (byte) 1);
        EnchantWeight.put("Shroud", (byte) 2);
        EnchantWeight.put("Stealth", (byte) 6);
        EnchantWeight.put("Swiftness", (byte) 10);
        EnchantWeight.put("Vitality", (byte) 4);
        EnchantWeight.put("WitherProtection", (byte) 8);
        EnchantWeight.put("MeteorFall", (byte) 1);
        EnchantWeight.put("FeatherFall", (byte) 1);
        EnchantWeight.put("FlashStep", (byte) 1);
        EnchantWeight.put("HealingAura", (byte) 1);
        EnchantWeight.put("RepulsiveAura", (byte) 1);
        EnchantWeight.put("AuraMagnetic", (byte) 1);
        EnchantWeight.put("GaiaAura", (byte) 1);
        EnchantWeight.put("IcyAura", (byte) 1);
        EnchantWeight.put("Gluttony", (byte) 1);
        EnchantWeight.put("ExplosiveDischarge", (byte) 1);
        EnchantWeight.put("ChargedAura", (byte) 1);
        EnchantWeight.put("Nightvision", (byte) 1);
        EnchantWeight.put("LightningArrow", (byte) 1);
        EnchantWeight.put("ArrowExplosive", (byte) 2);
        EnchantWeight.put("ArrowZeus", (byte) 5);
        EnchantWeight.put("ArrowSeeking", (byte) 1);
        EnchantWeight.put("LifeSteal", (byte) 7);
        EnchantWeight.put("Piercing", (byte) 1);
        EnchantWeight.put("Poison", (byte) 10);
        EnchantWeight.put("SoulTear", (byte) 2);
        EnchantWeight.put("SCurse", (byte) 1);
        EnchantWeight.put("Vampirism", (byte) 3);
        EnchantWeight.put("Vorpal", (byte) 1);
        EnchantWeight.put("OverCharge", (byte) 2);
        EnchantWeight.put("EnchLeech", (byte) 2);
        EnchantWeight.put("Examine", (byte) 1);
        EnchantWeight.put("FlamingArrow", (byte) 7);
        EnchantWeight.put("BloodRazor", (byte) 2);

        EnchantCost.put("Bound", (byte) 45);
        EnchantCost.put("FlameTouch", (byte) 30);
        EnchantCost.put("Repair", (byte) 15);
        EnchantCost.put("EarthEater", (byte) 30);
        EnchantCost.put("SpeedMine", (byte) 30);
        EnchantCost.put("VoidTouch", (byte) 30);
        EnchantCost.put("AdrenalineBoost", (byte) 30);
        EnchantCost.put("AuraFiery", (byte) 30);
        EnchantCost.put("AuraDeadly", (byte) 50);
        EnchantCost.put("AuraDark", (byte) 20);
        EnchantCost.put("AuraThick", (byte) 20);
        EnchantCost.put("AuraToxic", (byte) 35);
        EnchantCost.put("BattleHealing", (byte) 40);
        EnchantCost.put("DivineIntervention", (byte) 45);
        EnchantCost.put("Fly", (byte) 55);
        EnchantCost.put("JumpBoost", (byte) 10);
        EnchantCost.put("Regen", (byte) 15);
        EnchantCost.put("Shroud", (byte) 45);
        EnchantCost.put("Stealth", (byte) 40);
        EnchantCost.put("Swiftness", (byte) 20);
        EnchantCost.put("Vitality", (byte) 30);
        EnchantCost.put("WitherProtection", (byte) 40);
        EnchantCost.put("MeteorFall", (byte) 40);
        EnchantCost.put("FeatherFall", (byte) 40);
        EnchantCost.put("FlashStep", (byte) 40);
        EnchantCost.put("HealingAura", (byte) 40);
        EnchantCost.put("RepulsiveAura", (byte) 40);
        EnchantCost.put("AuraMagnetic", (byte) 40);
        EnchantCost.put("GaiaAura", (byte) 40);
        EnchantCost.put("IcyAura", (byte) 40);
        EnchantCost.put("Gluttony", (byte) 40);
        EnchantCost.put("ExplosiveDischarge", (byte) 40);
        EnchantCost.put("ChargedAura", (byte) 40);
        EnchantCost.put("Nightvision", (byte) 30);
        EnchantCost.put("LightningArrow", (byte) 40);
        EnchantCost.put("ArrowExplosive", (byte) 40);
        EnchantCost.put("ArrowZeus", (byte) 30);
        EnchantCost.put("ArrowSeeking", (byte) 45);
        EnchantCost.put("LifeSteal", (byte) 35);
        EnchantCost.put("Piercing", (byte) 40);
        EnchantCost.put("Poison", (byte) 25);
        EnchantCost.put("SoulTear", (byte) 30);
        EnchantCost.put("SCurse", (byte) 55);
        EnchantCost.put("Vampirism", (byte) 45);
        EnchantCost.put("Vorpal", (byte) 55);
        EnchantCost.put("OverCharge", (byte) 30);
        EnchantCost.put("EnchLeech", (byte) 30);
        EnchantCost.put("Examine", (byte) 55);
        EnchantCost.put("FlamingArrow", (byte) 20);
        EnchantCost.put("BloodRazor", (byte) 30);

        EnchantChargeNeeded.put("Bound", 0);
        EnchantChargeNeeded.put("FlameTouch", 30);
        EnchantChargeNeeded.put("Repair", 25);
        EnchantChargeNeeded.put("EarthEater", 30);
        EnchantChargeNeeded.put("SpeedMine", 30);
        EnchantChargeNeeded.put("VoidTouch", 30);
        EnchantChargeNeeded.put("AdrenalineBoost", 30);
        EnchantChargeNeeded.put("AuraFiery", 30);
        EnchantChargeNeeded.put("AuraDeadly", 50);
        EnchantChargeNeeded.put("AuraDark", 20);
        EnchantChargeNeeded.put("AuraThick", 20);
        EnchantChargeNeeded.put("AuraToxic", 35);
        EnchantChargeNeeded.put("BattleHealing", 40);
        EnchantChargeNeeded.put("DivineIntervention", 45);
        EnchantChargeNeeded.put("Fly", 50);
        EnchantChargeNeeded.put("JumpBoost", 10);
        EnchantChargeNeeded.put("Regen", 15);
        EnchantChargeNeeded.put("Shroud", 45);
        EnchantChargeNeeded.put("Stealth", 40);
        EnchantChargeNeeded.put("Swiftness", 20);
        EnchantChargeNeeded.put("Vitality", 30);
        EnchantChargeNeeded.put("WitherProtection", 40);
        EnchantChargeNeeded.put("MeteorFall", 40);
        EnchantChargeNeeded.put("FeatherFall", 40);
        EnchantChargeNeeded.put("FlashStep", 40);
        EnchantChargeNeeded.put("HealingAura", 40);
        EnchantChargeNeeded.put("RepulsiveAura", 40);
        EnchantChargeNeeded.put("AuraMagnetic", 40);
        EnchantChargeNeeded.put("GaiaAura", 40);
        EnchantChargeNeeded.put("IcyAura", 40);
        EnchantChargeNeeded.put("Gluttony", 40);
        EnchantChargeNeeded.put("ExplosiveDischarge", 40);
        EnchantChargeNeeded.put("ChargedAura", 40);
        EnchantChargeNeeded.put("Nightvision", 0);
        EnchantChargeNeeded.put("LightningArrow", 40);
        EnchantChargeNeeded.put("ArrowExplosive", 40);
        EnchantChargeNeeded.put("ArrowZeus", 30);
        EnchantChargeNeeded.put("ArrowSeeking", 45);
        EnchantChargeNeeded.put("LifeSteal", 35);
        EnchantChargeNeeded.put("Piercing", 40);
        EnchantChargeNeeded.put("Poison", 25);
        EnchantChargeNeeded.put("SoulTear", 30);
        EnchantChargeNeeded.put("SCurse", 55);
        EnchantChargeNeeded.put("Vampirism", 45);
        EnchantChargeNeeded.put("Vorpal", 55);
        EnchantChargeNeeded.put("OverCharge", 30);
        EnchantChargeNeeded.put("EnchLeech", 30);
        EnchantChargeNeeded.put("Examine", 55);
        EnchantChargeNeeded.put("FlamingArrow", 20);
        EnchantChargeNeeded.put("BloodRazor", 50);
    }
}
