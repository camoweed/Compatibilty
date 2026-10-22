package camoweed.campatibility.api.impl.vintagequesting;

import camoweed.campatibility.api.impl.vintagequesting.chapter.BTFishingQuestChapter;
import camoweed.campatibility.api.impl.vintagequesting.page.BTFishingQuestPage;
import camoweed.rosesword.ModItems;
import imbacrian.btf.BetterThanFishing;
import imbacrian.btf.items.FishingRodItem;
import imbacrian.btf.items.ItemsList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.block.Blocks;
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



public class BTFishingQuests implements VQPlugin {

	public static ChapterData BETTER_THAN_FISHING_CHAPTER = new BTFishingQuestChapter()	;

	@Override
	public void initializePlugin() {
		List<QuestData> btfquests = addBTFQuests();
		for (QuestData quest : btfquests) {
			BETTER_THAN_FISHING_CHAPTER.addQuest(quest);
		}
		VintageQuesting.LOGGER.info("Loaded quests from: Better than Fishing!");
	}

	@Override
	public boolean shouldLoad() {
		return true;
	}

	@Environment(EnvType.CLIENT)
	public void reloadClient() {
		VintageQuestingClient.CHAPTER_PAGES.unregister(BETTER_THAN_FISHING_CHAPTER.getId());

		VintageQuestingClient.CHAPTER_PAGES.register(BETTER_THAN_FISHING_CHAPTER.getId(), new BTFishingQuestPage(new Chapter(BETTER_THAN_FISHING_CHAPTER, new QuestTeam("null", UUID.randomUUID()))));
	}
	public static List<QuestData> addBTFQuests() {
		QuestData begin = new QuestData("btf:begin", "quest.btf.begin", Items.FOOD_FISH_RAW, Logic.AND, Logic.AND)
			.setTasks(listOf(new ClickTaskData("btf:begin/click")));
		QuestData rodUpgradeIron = consumeQuest("rodUpgradeIron", ItemsList.IRON_FISHING_ROD,
			zip(listOf("retrieval"),
				listOf(
					new ItemStack(Items.TOOL_FISHINGROD, 1)
				)),
			zip(listOf("reward"), listOf(new ItemStack(ItemsList.IRON_FISHING_ROD, 1))),
			listOf(begin), begin,
			0,
			-1);
		QuestData rodUpgradeGold = simpleQuest("rodUpgradeGold", ItemsList.GOLD_FISHING_ROD,
			zip(listOf("retrieval"),listOf(new ItemStack(ItemsList.GOLD_FISHING_ROD, 1))),
			zip(listOf("reward"), listOf(new ItemStack(ItemsList.GOLD_FISHING_ROD, 1))),
			listOf(rodUpgradeIron), rodUpgradeIron,0,-2);
		return listOf(begin, rodUpgradeIron, rodUpgradeGold);}
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
			retrievalTasks.add(new RetrievalTaskData("btf:" + id + "/" + task.getLeft(), task.getRight()));
		}
		List<RewardData> itemRewards = new ArrayList<>();
		for (Pair<String, ItemStack> reward : rewards) {
			itemRewards.add(new ItemRewardData("btf:" + id + "/" + reward.getLeft(), reward.getRight()));
		}
		return new QuestData("btf:" + id, "quest.btf." + id, icon, Logic.AND, Logic.AND)
			.setPreRequisites(preRequisites)
			.setTasks(retrievalTasks)
			.setRewards(itemRewards)
			.setX(offsetQuest, xOffset)
			.setY(offsetQuest, yOffset);
	}
	public static QuestData consumeQuest(
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
			retrievalTasks.add(new RetrievalTaskData("btf:" + id + "/" + task.getLeft(), task.getRight()).setConsume().setIgnoreMeta());
		}
		List<RewardData> itemRewards = new ArrayList<>();
		for (Pair<String, ItemStack> reward : rewards) {
			itemRewards.add(new ItemRewardData("btf:" + id + "/" + reward.getLeft(), reward.getRight()));
		}
		return new QuestData("btf:" + id, "quest.btf." + id, icon, Logic.AND, Logic.AND)
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
		return new QuestData("btf:" + id, "quest.btf." + id, icon, Logic.AND, Logic.AND)
			.setPreRequisites(preRequisites)
			.setTasks(listOf(new ClickTaskData("btf:" + id + "/click")))
			.setX(offsetQuest, xOffset)
			.setY(offsetQuest, yOffset);
	}
	public static QuestData getQuest(String id) {
		QuestData item = VintageQuesting.QUESTS.getItem("btf:" + id);
		if (item == null) {
			throw new NullPointerException("Quest " + id + " not found!");
		}
		return item;
	}
	}
