package camoweed.campatibility;

import camoweed.campatibility.api.impl.vintagequesting.BTFishingQuests;
import camoweed.campatibility.api.impl.vintagequesting.DefaultQuest;
import camoweed.campatibility.api.impl.vintagequesting.RoseToolsQuests;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.HalpLibe;
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

	Key KEY = Key.of(MOD_ID);
	public static final boolean RoseToolQuests;
	public static final boolean BTFishingQuests;
	public static final TomlConfigHandler CFG;
	static {
		Toml cfg = new Toml();
		cfg.addCategory("Quests");
		cfg.addEntry("Quests.RoseTools", "Quests for Rose Tools", true);
		cfg.addEntry("Quests.BTFishing", "Quests for Better than Fishing", true);

		CFG = new TomlConfigHandler(MOD_ID, cfg);

		RoseToolQuests = CFG.getBoolean("Quests.RoseTools");
		BTFishingQuests = CFG.getBoolean("Quests.BTFishing");
	}

	@Override
	public void onInitialize() {
		CommonEvents.BEFORE_GAME_START.listen(KEY, this::beforeGameStart);
		CommonEvents.AFTER_GAME_START.listen(KEY, this::afterGameStart);
	}
	public void beforeGameStart() {}
	public void afterGameStart() {
		if (quests) {LOGGER.info("☑ Vintage Questing is active!");
			boolean btfActive = false;
			boolean roseToolsActive = false;
			if (btfishing && BTFishingQuests) {LOGGER.info("☑ Better than Fishing is active!");BTFishingLoaded.init(); btfActive = true;}
			if (roseTools && RoseToolQuests) {LOGGER.info("☑ Rose Tools is active!");RoseToolsLoaded.init(); roseToolsActive = true;}
			else if(!btfActive && !roseToolsActive) {LOGGER.info("⚠ No modded quests active");new DefaultQuest().reloadClient();}

		}

	}
	private static class BTFishingLoaded {
		static void init() {
			new BTFishingQuests().reloadClient();
		}
	}
	private static class RoseToolsLoaded {
		static void init() {
			new RoseToolsQuests().reloadClient();
		}
	}
}

