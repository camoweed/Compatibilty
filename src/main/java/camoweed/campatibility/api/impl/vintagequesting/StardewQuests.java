package camoweed.campatibility.api.impl.vintagequesting;

import camoweed.campatibility.api.impl.vintagequesting.chapter.BTFishingQuestChapter;
import camoweed.campatibility.api.impl.vintagequesting.chapter.StardewQuestChapter;
import camoweed.campatibility.api.impl.vintagequesting.page.BTFishingQuestPage;
import camoweed.campatibility.api.impl.vintagequesting.page.StardewQuestPage;
import imbacrian.btf.items.ItemsList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.item.IItemConvertible;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.util.collection.Pair;
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
import sunsetsatellite.vintagequesting.util.Logic;
import sunsetsatellite.vintagequesting.util.QuestTeam;
import sunsetsatellite.vintagequesting.util.VQPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static sunsetsatellite.catalyst.Catalyst.listOf;
import static sunsetsatellite.catalyst.Catalyst.zip;


public class StardewQuests implements VQPlugin {

	public static ChapterData STARDEW_FARMING_CHAPTER = new StardewQuestChapter()	;

	@Override
	public void initializePlugin() {
		if (!shouldLoad()) return;
		List<QuestData> stardewquests = addStardewQuests();
		for (QuestData quest : stardewquests) {
			STARDEW_FARMING_CHAPTER.addQuest(quest);
		}
		VintageQuesting.LOGGER.info("Loaded quests from: Stardew Farming!");
	}

	@Override
	public boolean shouldLoad() {
		return FabricLoader.getInstance().isModLoaded("stardew");
	}

	@Environment(EnvType.CLIENT)
	public void reloadClient() {
		VintageQuestingClient.CHAPTER_PAGES.unregister(STARDEW_FARMING_CHAPTER.getId());

		VintageQuestingClient.CHAPTER_PAGES.register(STARDEW_FARMING_CHAPTER.getId(), new StardewQuestPage(new Chapter(STARDEW_FARMING_CHAPTER, new QuestTeam("null", UUID.randomUUID()))));
	}
	public static List<QuestData> addStardewQuests() {
		QuestData starter = new QuestData("stardew:starter", "quest.stardew.starter", Items.SEEDS_WHEAT, Logic.AND, Logic.AND)
			.setTasks(listOf(new ClickTaskData("stardew:starter/click")));

		return List.of(starter);
	}
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
			retrievalTasks.add(new RetrievalTaskData("stardew:" + id + "/" + task.getLeft(), task.getRight()));
		}
		List<RewardData> itemRewards = new ArrayList<>();
		for (Pair<String, ItemStack> reward : rewards) {
			itemRewards.add(new ItemRewardData("stardew:" + id + "/" + reward.getLeft(), reward.getRight()));
		}
		return new QuestData("stardew:" + id, "quest.stardew." + id, icon, Logic.AND, Logic.AND)
			.setPreRequisites(preRequisites)
			.setTasks(retrievalTasks)
			.setRewards(itemRewards)
			.setX(offsetQuest, xOffset)
			.setY(offsetQuest, yOffset);
	}
	public static QuestData consumeQuest(String id,IItemConvertible icon,List<Pair<String, ItemStack>> tasks,List<Pair<String, ItemStack>> rewards,List<QuestData> preRequisites,QuestData offsetQuest,int xOffset,int yOffset) {
		List<TaskData> consumeTasks = new ArrayList<>();
		for (Pair<String, ItemStack> task : tasks) {
			consumeTasks.add(new RetrievalTaskData("stardew:" + id + "/" + task.getLeft(), task.getRight()).setConsume());
		}
		List<RewardData> itemRewards = new ArrayList<>();
		for (Pair<String, ItemStack> reward : rewards) {
			itemRewards.add(new ItemRewardData("stardew:" + id + "/" + reward.getLeft(), reward.getRight()));
		}
		return new QuestData("stardew:" + id, "quest.stardew." + id, icon, Logic.AND, Logic.AND)
			.setPreRequisites(preRequisites)
			.setTasks(consumeTasks)
			.setRewards(itemRewards)
			.setX(offsetQuest, xOffset)
			.setY(offsetQuest, yOffset);
	}
	public static QuestData holdQuestNoMetadata(String id,IItemConvertible icon,List<Pair<String, ItemStack>> tasks,List<Pair<String, ItemStack>> rewards,List<QuestData> preRequisites,QuestData offsetQuest,int xOffset,int yOffset) {
		List<TaskData> consumeTasks = new ArrayList<>();
		for (Pair<String, ItemStack> task : tasks) {
			consumeTasks.add(new RetrievalTaskData("stardew:" + id + "/" + task.getLeft(), task.getRight()).setIgnoreMeta());
		}
		List<RewardData> itemRewards = new ArrayList<>();
		for (Pair<String, ItemStack> reward : rewards) {
			itemRewards.add(new ItemRewardData("stardew:" + id + "/" + reward.getLeft(), reward.getRight()));
		}
		return new QuestData("stardew:" + id, "quest.stardew." + id, icon, Logic.AND, Logic.AND)
			.setPreRequisites(preRequisites)
			.setTasks(consumeTasks)
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
		return new QuestData("stardew:" + id, "quest.stardew." + id, icon, Logic.AND, Logic.AND)
			.setPreRequisites(preRequisites)
			.setTasks(listOf(new ClickTaskData("stardew:" + id + "/click")))
			.setX(offsetQuest, xOffset)
			.setY(offsetQuest, yOffset);
	}
	public static QuestData getQuest(String id) {
		QuestData item = VintageQuesting.QUESTS.getItem("stardew:" + id);
		if (item == null) {
			throw new NullPointerException("Quest " + id + " not found!");
		}
		return item;
	}
	}
