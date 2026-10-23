package camoweed.campatibility.api.impl.vintagequesting;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.item.IItemConvertible;
import camoweed.campatibility.api.impl.vintagequesting.chapter.RoseToolsQuestChapter;
import camoweed.campatibility.api.impl.vintagequesting.page.RoseToolsQuestPage;
import net.minecraft.core.util.collection.Pair;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.block.Blocks;
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
import sunsetsatellite.vintagequesting.util.Logic;
import sunsetsatellite.vintagequesting.util.QuestTeam;
import sunsetsatellite.vintagequesting.util.VQPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static sunsetsatellite.catalyst.Catalyst.listOf;
import static sunsetsatellite.catalyst.Catalyst.zip;

import camoweed.rosesword.*;

public class RoseToolsQuests implements VQPlugin {

	public static ChapterData ROSE_TOOLS_CHAPTER = new RoseToolsQuestChapter()	;

	@Override
	public void initializePlugin() {
		if (!shouldLoad()) return;
		List<QuestData> roseToolQuests = addRoseToolsQuests();
		for (QuestData quest : roseToolQuests) {
			ROSE_TOOLS_CHAPTER.addQuest(quest);
		}
		VintageQuesting.LOGGER.info("Loaded quests from: Rose Tools!");
	}

	@Override
	public boolean shouldLoad() {return FabricLoader.getInstance().isModLoaded("btf");}

	@Environment(EnvType.CLIENT)
	public void reloadClient() {
		VintageQuestingClient.CHAPTER_PAGES.unregister(ROSE_TOOLS_CHAPTER.getId());

		VintageQuestingClient.CHAPTER_PAGES.register(ROSE_TOOLS_CHAPTER.getId(), new RoseToolsQuestPage(new Chapter(ROSE_TOOLS_CHAPTER, new QuestTeam("null", UUID.randomUUID()))));
	}
	public static List<QuestData> addRoseToolsQuests() {
		QuestData starter = new QuestData("rosesword:starter", "quest.rosesword.starter", Blocks.MOSS_BASALT, Logic.AND, Logic.AND)
			.setTasks(listOf(new ClickTaskData("rosesword:starter/click")));
		QuestData rose = simpleQuest("rose", Blocks.FLOWER_RED,
			zip(listOf("retrieval"),
				listOf(
					new ItemStack(Blocks.FLOWER_RED, 2)
				)),
			zip(listOf("reward"), listOf(new ItemStack(Blocks.FLOWER_RED, 2))),
			listOf(starter), starter,
			0,
			2);
		QuestData algae = simpleQuest("algae", Blocks.ALGAE,
			zip(listOf("retrieval"),
				listOf(
					new ItemStack(Blocks.ALGAE, 4)
				)),
			zip(listOf("reward"), listOf(new ItemStack(Blocks.ALGAE, 4))),
			listOf(rose), rose,
			1,
			1);
		QuestData steel = simpleQuest("steel", Items.INGOT_STEEL,
			zip(listOf("retrieval"),
				listOf(
					new ItemStack(Items.INGOT_STEEL, 2)
				)),
			zip(listOf("reward"), listOf(new ItemStack(Items.INGOT_STEEL, 2))),
			listOf(algae), rose,
			-1,
			1);
		QuestData pickaxe = simpleQuest("pickaxe", ModItems.rosePick,
			zip(listOf("retrieval"),
				listOf(
					new ItemStack(ModItems.rosePick, 1)
				)),
			listOf(),
			listOf(rose), steel,
			1,
			2);
		QuestData axe = simpleQuest("axe", ModItems.roseAxe,
			zip(listOf("retrieval"), listOf(new ItemStack(ModItems.roseAxe, 1))),listOf(),listOf(pickaxe), pickaxe, 2, 0);
		QuestData sword = simpleQuest("sword", ModItems.roseSword,
			zip(listOf("retrieval"), listOf(new ItemStack(ModItems.roseSword, 1))),listOf(),listOf(pickaxe), pickaxe, -2, 0);
		QuestData helmet = simpleQuest("helmet", ModItems.roseHelm,
			zip(listOf("retrieval"), listOf(new ItemStack(ModItems.roseHelm, 1))),listOf(),listOf(pickaxe), pickaxe, 0, 2);
		QuestData chestplate = simpleQuest("chestplate", ModItems.roseChest,
			zip(listOf("retrieval"), listOf(new ItemStack(ModItems.roseChest, 1))),listOf(),listOf(pickaxe), pickaxe, 0, 3);
		QuestData pants = simpleQuest("pants", ModItems.rosePants,
			zip(listOf("retrieval"), listOf(new ItemStack(ModItems.rosePants, 1))),listOf(),listOf(pickaxe), pickaxe, 0, 4);
		QuestData boots = simpleQuest("boots", ModItems.roseBoots,
			zip(listOf("retrieval"), listOf(new ItemStack(ModItems.roseBoots, 1))),listOf(),listOf(pickaxe), pickaxe, 0, 5);

		return listOf(starter, rose, algae, steel, pickaxe, axe, sword, helmet, chestplate, pants, boots);}
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
