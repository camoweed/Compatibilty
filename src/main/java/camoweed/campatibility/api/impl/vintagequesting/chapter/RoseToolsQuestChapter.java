package camoweed.campatibility.api.impl.vintagequesting.chapter;

import camoweed.campatibility.api.impl.vintagequesting.VintageQuestingCampatPlugin;
import camoweed.campatibility.api.impl.vintagequesting.VintageQuestingRoseToolsPlugin;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import sunsetsatellite.vintagequesting.core.data.ChapterData;
import sunsetsatellite.vintagequesting.core.data.QuestData;

public class RoseToolsQuestChapter extends ChapterData {
	public RoseToolsQuestChapter() {
		super("rosesword:tutorial", 0);
	}
	@Override
	public @NotNull String getName() {
		return "Rose Tools";
	}

	@Override
	public @NotNull String getDescription() {
		return "Specialized utility tools";
	}

	@Override
	public @NotNull ItemStack getIcon() {
		return Blocks.FLOWER_RED.getDefaultStack();
	}

	@Override
	public QuestData getStartingQuest() {
		return VintageQuestingRoseToolsPlugin.getQuest("starter");
	}
}
