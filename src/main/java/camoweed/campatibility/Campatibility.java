package camoweed.campatibility;

import camoweed.campatibility.api.impl.vintagequesting.BTFishingQuests;
import camoweed.campatibility.api.impl.vintagequesting.DefaultQuest;
import camoweed.campatibility.api.impl.vintagequesting.RoseToolsQuests;
import camoweed.campatibility.api.impl.vintagequesting.StardewQuests;
import camoweed.campatibility.item.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.HalpLibe;
import turniplabs.halplibe.event.defs.ClientEvents;
import turniplabs.halplibe.event.defs.CommonEvents;
import turniplabs.halplibe.util.TomlConfigHandler;
import turniplabs.halplibe.util.dependency.Key;
import turniplabs.halplibe.util.toml.Toml;

public class Campatibility implements ModInitializer {
	public static final String MOD_ID = HalpLibe.registerMod("campatibility", true);
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
// check if mod exist
	//boolean catalyst = FabricLoader.getInstance().isModLoaded("catalyst-core");
	boolean quests = FabricLoader.getInstance().isModLoaded("vintagequesting");
	boolean roseTools = FabricLoader.getInstance().isModLoaded("rosesword");
	boolean btfishing = FabricLoader.getInstance().isModLoaded("btf");
	boolean stardew = FabricLoader.getInstance().isModLoaded("stardew");
	boolean paxels = FabricLoader.getInstance().isModLoaded("paxels");
	boolean heatedSteel = FabricLoader.getInstance().isModLoaded("superheatedsteel");

	Key KEY = Key.of(MOD_ID);
	public static final boolean RoseToolQuests;
	public static final boolean BTFishingQuests;
	public static final boolean StarwdewFarmingQuests;

	public static final boolean PaxelsandRoseTools;
	public static final boolean PaxelsandBTF;
	public static final boolean PaxelsandSuperheatedSteel;

	public static final TomlConfigHandler CFG;
	static {
		Toml cfg = new Toml();
		cfg.addCategory("Quests");
		cfg.addEntry("Quests.RoseTools", "Quests for Rose Tools", true);
		cfg.addEntry("Quests.BTFishing", "Quests for Better than Fishing", true);
		cfg.addEntry("Quests.StardewFarming", "Quests for Stardew Farming", true);

		cfg.addCategory("Items");
		cfg.addEntry("Items.PaxelsxRoseTools", "Rose Paxel", true);
		cfg.addEntry("Items.PaxelsxBetterThanFishing", "Iridescite Paxel", true);
		cfg.addEntry("Items.PaxelsxSuperheatedSteel", "Superheated Steel Paxel", true);

		CFG = new TomlConfigHandler(MOD_ID, cfg);

		RoseToolQuests = CFG.getBoolean("Quests.RoseTools");
		BTFishingQuests = CFG.getBoolean("Quests.BTFishing");
		StarwdewFarmingQuests = CFG.getBoolean("Quests.StardewFarming");

		PaxelsandRoseTools = CFG.getBoolean("Items.PaxelsxRoseTools");
		PaxelsandBTF = CFG.getBoolean("Items.PaxelsxBetterThanFishing");
		PaxelsandSuperheatedSteel = CFG.getBoolean("Items.PaxelsxSuperheatedSteel");
	}

	@Override
	public void onInitialize() {
		CommonEvents.BEFORE_GAME_START.listen(KEY, this::beforeGameStart);
		CommonEvents.AFTER_GAME_START.listen(KEY, this::afterGameStart);
		if (roseTools && paxels && PaxelsandRoseTools) {LOGGER.info("☑ Paxels + Rose Tools is active!");PaxelandRoseToolsLoaded.init();}
		if (btfishing && paxels && PaxelsandBTF) {LOGGER.info("☑ Paxels + Better than Fishing is active!");PaxelandBTFLoaded.init();}
		if (heatedSteel && paxels && PaxelsandSuperheatedSteel) {LOGGER.info("☑ Paxels + Superheated Steel is active!");PaxelandSuperheatedSteelLoaded.init();}
	}
	public void beforeGameStart() {}
	public void afterGameStart() {
		if (quests) {LOGGER.info("☑ Vintage Questing is active!");
			boolean roseToolsActive = false;
			boolean btfActive = false;
			boolean stardewActive = false;
			if (roseTools && RoseToolQuests) {LOGGER.info("☑ Rose Tools is active!");RoseToolsLoaded.init(); roseToolsActive = true;}
			if (btfishing && BTFishingQuests) {LOGGER.info("☑ Better than Fishing is active!");BTFishingLoaded.init(); btfActive = true;}
			if (stardew && StarwdewFarmingQuests) {LOGGER.info("☑ Better than Fishing is active!");StardewLoaded.init(); stardewActive = true;}
			if(!btfActive && !roseToolsActive && !stardewActive) {LOGGER.info("⚠ No modded quests active");new DefaultQuest().reloadClient();}

		}
	}
	private static class RoseToolsLoaded {
		static void init() {
			new RoseToolsQuests().reloadClient();
		}
	}
	private static class BTFishingLoaded {
		static void init() {
			new BTFishingQuests().reloadClient();
		}
	}
	private static class StardewLoaded {
		static void init() {
			new StardewQuests().reloadClient();
		}
	}
	private static class PaxelandRoseToolsLoaded {
		static void init() {
			CommonEvents.AFTER_ITEM_INIT.listen(Key.of(MOD_ID), PaxelRoseToolsItems::initializeItems);
			ClientEvents.BEFORE_CLIENT_START.listen(Key.of(MOD_ID), PaxelRoseToolsModels::beforeClientStart);
			ClientEvents.ITEM_MODEL_RELOAD.listen(Key.of(MOD_ID), PaxelRoseToolsModels::initItemModels);
		}
	}
	private static class PaxelandBTFLoaded {
		static void init() {
			CommonEvents.AFTER_ITEM_INIT.listen(Key.of(MOD_ID), PaxelBTFItems::initializeItems);
			ClientEvents.BEFORE_CLIENT_START.listen(Key.of(MOD_ID), PaxelBTFModels::beforeClientStart);
			ClientEvents.ITEM_MODEL_RELOAD.listen(Key.of(MOD_ID), PaxelBTFModels::initItemModels);
		}
	}
	private static class PaxelandSuperheatedSteelLoaded {
		static void init() {
			CommonEvents.AFTER_ITEM_INIT.listen(Key.of(MOD_ID), PaxelSuperheatedSteelItems::initializeItems);
			ClientEvents.BEFORE_CLIENT_START.listen(Key.of(MOD_ID), PaxelSuperheatedSteelModels::beforeClientStart);
			ClientEvents.ITEM_MODEL_RELOAD.listen(Key.of(MOD_ID), PaxelSuperheatedSteelModels::initItemModels);
		}
	}
}

