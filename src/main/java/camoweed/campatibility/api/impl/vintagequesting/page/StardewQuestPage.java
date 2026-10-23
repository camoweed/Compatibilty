package camoweed.campatibility.api.impl.vintagequesting.page;


import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import org.jetbrains.annotations.Nullable;
import sunsetsatellite.vintagequesting.client.gui.ChapterPage;
import sunsetsatellite.vintagequesting.client.gui.ScreenQuestbook;
import sunsetsatellite.vintagequesting.core.Chapter;

import java.util.Random;

public class StardewQuestPage extends ChapterPage {
	public StardewQuestPage(Chapter chapter) {
		super(chapter);
	}

	@Override
	public @Nullable IconCoordinate getBackgroundTile(ScreenQuestbook screenQuestbook, int i, Random random, int i1, int i2) {
		return TextureRegistry.getTexture("minecraft:block/mud");
	}

	@Override
	public void postProcessBackground(ScreenQuestbook screenQuestbook, Random random, ScreenQuestbook.BGLayer bgLayer, int i, int i1) {

	}

	@Override
	public int backgroundLayers() {
		return 1;
	}

	@Override
	public int backgroundColor() {
		return 0;
	}

	@Override
	public int lineColorLocked(boolean b) {
		return 0;
	}

	@Override
	public int lineColorUnlocked(boolean b) {
		return 0;
	}

	@Override
	public int lineColorCanUnlock(boolean b) {
		return 0;
	}
}
