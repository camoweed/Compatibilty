package camoweed.campatibility.api.impl.vintagequesting.chapter;

import camoweed.campatibility.api.impl.vintagequesting.VintageQuestingCampatPlugin;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import sunsetsatellite.vintagequesting.core.data.ChapterData;
import sunsetsatellite.vintagequesting.core.data.QuestData;

public class EpicQuestChapter extends ChapterData {
	public EpicQuestChapter() {
		super("minecraft:epic", 99);
	}
	@Override
	public @NotNull String getName() {
		return "epic";
	}

	@Override
	public @NotNull String getDescription() {
		return "truely epic";
	}

	@Override
	public @NotNull ItemStack getIcon() {
		return Blocks.STONE.getDefaultStack();
	}

	@Override
	public QuestData getStartingQuest() {
		return null;
		//return VintageQuestingCampatPlugin.getQuest("welcome");
	}
}
