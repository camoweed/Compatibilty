package camoweed.campatibility.api.impl.vintagequesting;

import net.minecraft.core.item.IItemConvertible;
import camoweed.campatibility.api.impl.vintagequesting.chapter.EpicQuestChapter;
import camoweed.campatibility.api.impl.vintagequesting.chapter.RoseToolsQuestChapter;
import camoweed.campatibility.api.impl.vintagequesting.page.EpicQuestPage;
import camoweed.campatibility.api.impl.vintagequesting.page.RoseToolsQuestPage;
import net.minecraft.core.util.collection.Pair;
//import com.llamalad7.mixinextras.lib.antlr.runtime.misc.Pair;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.item.IItemConvertible;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import sunsetsatellite.vintagequesting.VintageQuesting;
import sunsetsatellite.vintagequesting.VintageQuestingClient;
import sunsetsatellite.vintagequesting.core.Chapter;
import sunsetsatellite.vintagequesting.core.data.ChapterData;
import sunsetsatellite.vintagequesting.core.data.QuestData;
import sunsetsatellite.vintagequesting.core.data.RewardData;
import sunsetsatellite.vintagequesting.core.data.TaskData;
import sunsetsatellite.vintagequesting.core.data.reward.ItemRewardData;
import sunsetsatellite.vintagequesting.core.data.task.ClickTaskData;
import sunsetsatellite.vintagequesting.core.data.task.RetrievalTaskData;
import sunsetsatellite.vintagequesting.core.data.task.VisitDimensionTaskData;
import sunsetsatellite.vintagequesting.util.Logic;
import sunsetsatellite.vintagequesting.util.QuestTeam;
import sunsetsatellite.vintagequesting.util.VQPlugin;
//import com.llamalad7.mixinextras.lib.antlr.runtime.misc.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static sunsetsatellite.catalyst.Catalyst.listOf;
import static sunsetsatellite.catalyst.Catalyst.zip;

public class VintageQuestingRoseToolsPlugin implements VQPlugin {

	public static ChapterData ROSE_TOOLS_CHAPTER = new RoseToolsQuestChapter()	;

	@Override
	public void initializePlugin() {
		List<QuestData> roseToolQuests = addRoseToolsQuests();
		for (QuestData quest : roseToolQuests) {
			ROSE_TOOLS_CHAPTER.addQuest(quest);
		}
		VintageQuesting.LOGGER.info("Loaded quests from: Rose Tools!");
	}

	@Override
	public boolean shouldLoad() {
		return true;
	}
	@Environment(EnvType.CLIENT)
	public void reloadClient() {
		VintageQuestingClient.CHAPTER_PAGES.unregister(ROSE_TOOLS_CHAPTER.getId());

		VintageQuestingClient.CHAPTER_PAGES.register(ROSE_TOOLS_CHAPTER.getId(), new RoseToolsQuestPage(new Chapter(ROSE_TOOLS_CHAPTER, new QuestTeam("null", UUID.randomUUID()))));
	}
	public static List<QuestData> addRoseToolsQuests() {
		QuestData starter = new QuestData("rosesword:starter", "quest.rosesword.starter", Blocks.FLOWER_RED, Logic.AND, Logic.AND)
			.setTasks(listOf(new ClickTaskData("rosesword:starter/click")));
		QuestData algae = simpleQuest("algae", Blocks.ALGAE,
			zip(listOf("retrieval"),
				listOf(
					new ItemStack(Blocks.ALGAE, 4)
				)),
			listOf(),
			listOf(starter), starter,
			0,
			1);
		/*QuestData algae = simpleQuest("algae", Blocks.ALGAE,
			zip(listOf("retrieval"),
				listOf(
					new ItemStack(Blocks.ALGAE, 4)
				)),
			,
			listOf(starter), starter,
			0,
			1);
		 */
		return listOf(starter, algae);}
	public static QuestData simpleQuest(
		String id,
		IItemConvertible icon,
		List<Pair<String, ItemStack>> tasks,
		List<Pair<String, ItemStack>> rewards,
		List<QuestData> preRequisites,
		QuestData offsetQuest,
		int xOffset,
		int yOffset
	) {
		List<TaskData> retrievalTasks = new ArrayList<>();
		for (Pair<String, ItemStack> task : tasks) {
			retrievalTasks.add(new RetrievalTaskData("rosesword:" + id + "/" + task.getLeft(), task.getRight()));
		}
		List<RewardData> itemRewards = new ArrayList<>();
		for (Pair<String, ItemStack> reward : rewards) {
			itemRewards.add(new ItemRewardData("rosesword:" + id + "/" + reward.getLeft(), reward.getRight()));
		}
		return new QuestData("rosesword:" + id, "quest.rosesword." + id, icon, Logic.AND, Logic.AND)
			.setPreRequisites(preRequisites)
			.setTasks(retrievalTasks)
			.setRewards(itemRewards)
			.setX(offsetQuest, xOffset)
			.setY(offsetQuest, yOffset);
	}
	public static QuestData simpleClickQuest(
		String id,
		IItemConvertible icon,
		List<QuestData> preRequisites,
		QuestData offsetQuest,
		int xOffset,
		int yOffset
	) {
		return new QuestData("rosesword:" + id, "quest.rosesword." + id, icon, Logic.AND, Logic.AND)
			.setPreRequisites(preRequisites)
			.setTasks(listOf(new ClickTaskData("rosesword:" + id + "/click")))
			.setX(offsetQuest, xOffset)
			.setY(offsetQuest, yOffset);
	}
	public static QuestData getQuest(String id) {
		QuestData item = VintageQuesting.QUESTS.getItem("rosesword:" + id);
		if (item == null) {
			throw new NullPointerException("Quest " + id + " not found!");
		}
		return item;
	}
	}
