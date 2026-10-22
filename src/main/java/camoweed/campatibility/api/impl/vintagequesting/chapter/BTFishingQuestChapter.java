package camoweed.campatibility.api.impl.vintagequesting.chapter;

import camoweed.campatibility.api.impl.vintagequesting.BTFishingQuests;
import camoweed.campatibility.api.impl.vintagequesting.RoseToolsQuests;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import org.jetbrains.annotations.NotNull;
import sunsetsatellite.vintagequesting.core.data.ChapterData;
import sunsetsatellite.vintagequesting.core.data.QuestData;

public class BTFishingQuestChapter extends ChapterData {
	public BTFishingQuestChapter() {
		super("btf:tutorial", 0);
	}
	@Override
	public @NotNull String getName() {
		return "Better than Fishing";
	}

	@Override
	public @NotNull String getDescription() {
		return "Expanded Fishing";
	}

	@Override
	public @NotNull ItemStack getIcon() {
		return Items.FOOD_FISH_RAW.getDefaultStack();
	}

	@Override
	public QuestData getStartingQuest() {
		return BTFishingQuests.getQuest("begin");
	}
}
