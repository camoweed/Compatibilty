package camoweed.campatibility.api.impl.vintagequesting.chapter;

import camoweed.campatibility.api.impl.vintagequesting.DefaultQuest;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import sunsetsatellite.vintagequesting.core.data.ChapterData;
import sunsetsatellite.vintagequesting.core.data.QuestData;

public class DefaultQuestChapter extends ChapterData {
	public DefaultQuestChapter() {
		super("minecraft:epic", 99);
	}
	@Override
	public @NotNull String getName() {
		return "No quest mods!";
	}

	@Override
	public @NotNull String getDescription() {
		return "☹";
	}

	@Override
	public @NotNull ItemStack getIcon() {
		return Blocks.STONE.getDefaultStack();
	}

	@Override
	public QuestData getStartingQuest() {
		return DefaultQuest.getQuest("welcome");
	}
}
