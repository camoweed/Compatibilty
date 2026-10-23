package camoweed.campatibility.api.impl.vintagequesting.chapter;

import camoweed.campatibility.api.impl.vintagequesting.BTFishingQuests;
import camoweed.campatibility.api.impl.vintagequesting.StardewQuests;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import org.jetbrains.annotations.NotNull;
import sunsetsatellite.vintagequesting.core.data.ChapterData;
import sunsetsatellite.vintagequesting.core.data.QuestData;

public class StardewQuestChapter extends ChapterData {
	public StardewQuestChapter() {
		super("stardew:tutorial", 0);
	}
	@Override
	public @NotNull String getName() {
		return "Stardew Farming";
	}

	@Override
	public @NotNull String getDescription() {
		return "Expanded Farming";
	}

	@Override
	public @NotNull ItemStack getIcon() {
		return Items.SEEDS_WHEAT.getDefaultStack();
	}

	@Override
	public QuestData getStartingQuest() {
		return StardewQuests.getQuest("starter");
	}
}
