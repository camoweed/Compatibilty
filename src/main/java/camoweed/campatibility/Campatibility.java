package camoweed.campatibility;

import camoweed.campatibility.api.impl.vintagequesting.VintageQuestingCampatPlugin;
import camoweed.campatibility.api.impl.vintagequesting.VintageQuestingRoseToolsPlugin;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.HalpLibe;
import turniplabs.halplibe.event.defs.CommonEvents;
import turniplabs.halplibe.util.dependency.Key;

public class Campatibility implements ModInitializer {
	public static final String MOD_ID = HalpLibe.registerMod("campatibility", true);
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		CommonEvents.BEFORE_GAME_START.listen(Key.of(MOD_ID), this::beforeGameStart);
		CommonEvents.AFTER_GAME_START.listen(Key.of(MOD_ID), this::afterGameStart);
		//LOGGER.info("Compabitlity initialised.");
		if (FabricLoader.getInstance().isModLoaded("catalyst-core")) {
			LOGGER.info("☑ Catalyst is active!");
		}

		else { LOGGER.info("⚠ No mods are active"); }
	}

	public void beforeGameStart() {

	}

	public void afterGameStart() {
		if (FabricLoader.getInstance().isModLoaded("vintagequesting")) {
			LOGGER.info("☑ Vintage Questing is active!");
			//new VintageQuestingCampatPlugin().reloadClient();
		}
		if (FabricLoader.getInstance().isModLoaded("rosesword")) {
			LOGGER.info("☑ Rose Tools is active!");
			new VintageQuestingRoseToolsPlugin().reloadClient();
		}
	}
}
