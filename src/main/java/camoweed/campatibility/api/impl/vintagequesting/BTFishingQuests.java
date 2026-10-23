package camoweed.campatibility.api.impl.vintagequesting;

import camoweed.campatibility.api.impl.vintagequesting.chapter.BTFishingQuestChapter;
import camoweed.campatibility.api.impl.vintagequesting.page.BTFishingQuestPage;
import camoweed.rosesword.ModItems;
import imbacrian.btf.BetterThanFishing;
import imbacrian.btf.items.FishingRodItem;
import imbacrian.btf.items.ItemsList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
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
		if (!shouldLoad()) return;
		List<QuestData> btfquests = addBTFQuests();
		for (QuestData quest : btfquests) {
			BETTER_THAN_FISHING_CHAPTER.addQuest(quest);
		}
		VintageQuesting.LOGGER.info("Loaded quests from: Better than Fishing!");
	}

	@Override
	public boolean shouldLoad() {
		return FabricLoader.getInstance().isModLoaded("btf");
	}

	@Environment(EnvType.CLIENT)
	public void reloadClient() {
		VintageQuestingClient.CHAPTER_PAGES.unregister(BETTER_THAN_FISHING_CHAPTER.getId());

		VintageQuestingClient.CHAPTER_PAGES.register(BETTER_THAN_FISHING_CHAPTER.getId(), new BTFishingQuestPage(new Chapter(BETTER_THAN_FISHING_CHAPTER, new QuestTeam("null", UUID.randomUUID()))));
	}
	public static List<QuestData> addBTFQuests() {
		QuestData begin = new QuestData("btf:begin", "quest.btf.begin", Items.FOOD_FISH_RAW, Logic.AND, Logic.AND)
			.setTasks(listOf(new ClickTaskData("btf:begin/click")));
		// rod progression
		QuestData rodUpgradeIron = consumeQuest("rodUpgradeIron", ItemsList.IRON_FISHING_ROD,
			zip(listOf("retrieval"),
				listOf(
					new ItemStack(Items.TOOL_FISHINGROD, 1)
				)),
			zip(listOf("reward", "reward1"), listOf(new ItemStack(ItemsList.IRON_FISHING_ROD, 1), new ItemStack(ItemsList.IRIDESCENT_CLUMP, 1))),
			listOf(begin), begin,
			0,
			-1);
		QuestData rodUpgradeGold = holdQuestNoMetadata("rodUpgradeGold", ItemsList.GOLD_FISHING_ROD,
			zip(listOf("retrieval"),listOf(new ItemStack(ItemsList.GOLD_FISHING_ROD, 1))),
			zip(listOf("reward"), listOf(new ItemStack(ItemsList.IRIDESCENT_CLUMP, 1))),
			listOf(rodUpgradeIron), rodUpgradeIron,0,-1);
		QuestData rodUpgradeDiamond = holdQuestNoMetadata("rodUpgradeDiamond", ItemsList.DIAMOND_FISHING_ROD,
			zip(listOf("retrieval"),listOf(new ItemStack(ItemsList.DIAMOND_FISHING_ROD, 1))),
			zip(listOf("reward"), listOf(new ItemStack(ItemsList.IRIDESCENT_CLUMP, 1))),
			listOf(rodUpgradeIron), rodUpgradeIron,-1,-3);
		QuestData rodUpgradeSteel = holdQuestNoMetadata("rodUpgradeSteel", ItemsList.STEEL_FISHING_ROD,
			zip(listOf("retrieval"),listOf(new ItemStack(ItemsList.STEEL_FISHING_ROD, 1))),
			zip(listOf("reward"), listOf(new ItemStack(ItemsList.IRIDESCENT_CLUMP, 1))),
			listOf(rodUpgradeIron), rodUpgradeIron,1,-3);
		QuestData rodUpgradeIridescent 	 = holdQuestNoMetadata("rodUpgradeIridescent", ItemsList.IRIDESCITE_FISHING_ROD,
			zip(listOf("retrieval"),listOf(new ItemStack(ItemsList.IRIDESCITE_FISHING_ROD, 1))),
			zip(listOf("reward"), listOf(new ItemStack(ItemsList.INGOT_IRIDESCITE, 2))),
			listOf(rodUpgradeIron), rodUpgradeIron,0,-4);
		// fish progression
		QuestData anchovyMaster = simpleQuest("anchovyMaster", ItemsList.FOOD_ANCHOVY_RAW,
			zip(listOf("retrieval"),listOf(new ItemStack(ItemsList.FOOD_ANCHOVY_RAW, 8))),
			zip(listOf("reward"), listOf(new ItemStack(ItemsList.IRIDESCENT_CLUMP, 1))),
			listOf(begin), begin,-1,-1);
		QuestData goldenCarpMaster = simpleQuest("goldenCarpMaster", ItemsList.FOOD_GOLDEN_CARP_RAW,
			zip(listOf("retrieval"),listOf(new ItemStack(ItemsList.FOOD_GOLDEN_CARP_RAW, 8))),
			zip(listOf("reward"), listOf(new ItemStack(ItemsList.IRIDESCENT_CLUMP, 1))),
			listOf(begin), begin,1,-1);
		QuestData knifefishMaster = simpleQuest("knifefishMaster", ItemsList.FOOD_KNIFEFISH_RAW,
			zip(listOf("retrieval"),listOf(new ItemStack(ItemsList.FOOD_KNIFEFISH_RAW, 8))),
			zip(listOf("reward"), listOf(new ItemStack(ItemsList.IRIDESCENT_CLUMP, 1))),
			listOf(begin), begin,-2,-1);
		QuestData sardineMaster = simpleQuest("sardineMaster", ItemsList.FOOD_SARDINE_RAW,
			zip(listOf("retrieval"),listOf(new ItemStack(ItemsList.FOOD_SARDINE_RAW, 8))),
			zip(listOf("reward"), listOf(new ItemStack(ItemsList.IRIDESCENT_CLUMP, 1))),
			listOf(begin), begin,2,-1);
		QuestData sunfishMaster = simpleQuest("sunfishMaster", ItemsList.FOOD_SUNFISH_RAW,
			zip(listOf("retrieval"),listOf(new ItemStack(ItemsList.FOOD_SUNFISH_RAW, 8))),
			zip(listOf("reward"), listOf(new ItemStack(ItemsList.IRIDESCENT_CLUMP, 1))),
			listOf(begin), begin,-1,-2);
		QuestData tunaMaster = simpleQuest("tunaMaster", ItemsList.FOOD_TUNA_RAW,
			zip(listOf("retrieval"),listOf(new ItemStack(ItemsList.FOOD_TUNA_RAW, 8))),
			zip(listOf("reward"), listOf(new ItemStack(ItemsList.IRIDESCENT_CLUMP, 1))),
			listOf(begin), begin,1,-2);
		QuestData rockfishMaster = simpleQuest("rockfishMaster", ItemsList.FOOD_ROCKFISH,
			zip(listOf("retrieval"),listOf(new ItemStack(ItemsList.FOOD_ROCKFISH, 8))),
			zip(listOf("reward"), listOf(new ItemStack(ItemsList.IRIDESCENT_CLUMP, 1))),
			listOf(begin), begin,-2,-2);
		QuestData catfishMaster = simpleQuest("goldenCarpMaster", ItemsList.FOOD_CATFISH_RAW,
			zip(listOf("retrieval"),listOf(new ItemStack(ItemsList.FOOD_CATFISH_RAW, 8))),
			zip(listOf("reward"), listOf(new ItemStack(ItemsList.IRIDESCENT_CLUMP, 1))),
			listOf(begin), begin,2,-2);
		QuestData bootfishMaster = simpleQuest("bootfishMaster", ItemsList.FOOD_BOOTFISH_RAW,
			zip(listOf("retrieval"),listOf(new ItemStack(ItemsList.FOOD_BOOTFISH_RAW, 8))),
			zip(listOf("reward"), listOf(new ItemStack(ItemsList.IRIDESCENT_CLUMP, 1))),
			listOf(begin), begin,-2,0);
		QuestData redMulletMaster = simpleQuest("redMulletMaster", ItemsList.FOOD_RED_MULLET_RAW,
			zip(listOf("retrieval"),listOf(new ItemStack(ItemsList.FOOD_RED_MULLET_RAW, 8))),
			zip(listOf("reward"), listOf(new ItemStack(ItemsList.IRIDESCENT_CLUMP, 1))),
			listOf(begin), begin,1,0);
		QuestData herringMaster = simpleQuest("herringMaster", ItemsList.FOOD_HERRING_RAW,
			zip(listOf("retrieval"),listOf(new ItemStack(ItemsList.FOOD_HERRING_RAW, 8))),
			zip(listOf("reward"), listOf(new ItemStack(ItemsList.IRIDESCENT_CLUMP, 1))),
			listOf(begin), begin,-1,0);
		QuestData halibutMaster = simpleQuest("halibutMaster", ItemsList.FOOD_HALIBUT_RAW,
			zip(listOf("retrieval"),listOf(new ItemStack(ItemsList.FOOD_HALIBUT_RAW, 8))),
			zip(listOf("reward"), listOf(new ItemStack(ItemsList.IRIDESCENT_CLUMP, 1))),
			listOf(begin), begin,2,0);
		QuestData perchMaster = simpleQuest("perchMaster", ItemsList.FOOD_PERCH_RAW,
			zip(listOf("retrieval"),listOf(new ItemStack(ItemsList.FOOD_PERCH_RAW, 8))),
			zip(listOf("reward"), listOf(new ItemStack(ItemsList.IRIDESCENT_CLUMP, 1))),
			listOf(begin), begin,-1,-3);
		QuestData seaBassMaster = simpleQuest("seaBassMaster", ItemsList.FOOD_SEA_BASS_RAW,
			zip(listOf("retrieval"),listOf(new ItemStack(ItemsList.FOOD_SEA_BASS_RAW, 8))),
			zip(listOf("reward"), listOf(new ItemStack(ItemsList.IRIDESCENT_CLUMP, 1))),
			listOf(begin), begin,1,-3);
		QuestData troutMaster = simpleQuest("troutMaster", ItemsList.FOOD_TROUT_RAW,
			zip(listOf("retrieval"),listOf(new ItemStack(ItemsList.FOOD_TROUT_RAW, 8))),
			zip(listOf("reward"), listOf(new ItemStack(ItemsList.IRIDESCENT_CLUMP, 1))),
			listOf(begin), begin,-2,-3);
		QuestData eelMaster = simpleQuest("eelMaster", ItemsList.FOOD_EEL_RAW,
			zip(listOf("retrieval"),listOf(new ItemStack(ItemsList.FOOD_EEL_RAW, 8))),
			zip(listOf("reward"), listOf(new ItemStack(ItemsList.IRIDESCENT_CLUMP, 1))),
			listOf(begin), begin,2,-3);
		QuestData crabMaster = simpleQuest("crabMaster", ItemsList.FOOD_CRAB,
			zip(listOf("retrieval"),listOf(new ItemStack(ItemsList.FOOD_CRAB, 8))),
			zip(listOf("reward"), listOf(new ItemStack(ItemsList.IRIDESCENT_CLUMP, 1))),
			listOf(begin), begin,0,-3);
		// we tie it back in booya
		QuestData allTheFishium = consumeQuest("allTheFishium", ItemsList.INGOT_IRIDESCITE,
			zip(listOf("retrieval","retrieval1","retrieval2","retrieval3","retrieval4","retrieval5","retrieval6","retrieval7","retrieval8","retrieval9","retrieval10","retrieval11","retrieval12","retrieval13","retrieval14","retrieval15"),listOf(
				new ItemStack(ItemsList.FOOD_CRAB, 1),
				new ItemStack(ItemsList.FOOD_ROCKFISH, 1),
				new ItemStack(ItemsList.FOOD_ANCHOVY_COOKED, 1),
				new ItemStack(ItemsList.FOOD_GOLDEN_CARP_COOKED, 1),
				new ItemStack(ItemsList.FOOD_KNIFEFISH_COOKED, 1),
				new ItemStack(ItemsList.FOOD_SARDINE_COOKED, 1),
				new ItemStack(ItemsList.FOOD_SUNFISH_COOKED, 1),
				new ItemStack(ItemsList.FOOD_TUNA_COOKED, 1),
				new ItemStack(ItemsList.FOOD_CATFISH_COOKED, 1),
				new ItemStack(ItemsList.FOOD_BOOTFISH_COOKED, 1),
				new ItemStack(ItemsList.FOOD_RED_MULLET_COOKED, 1),
				new ItemStack(ItemsList.FOOD_HERRING_COOKED, 1),
				new ItemStack(ItemsList.FOOD_HALIBUT_COOKED, 1),
				new ItemStack(ItemsList.FOOD_PERCH_COOKED, 1),
				new ItemStack(ItemsList.FOOD_TROUT_COOKED, 1),
				new ItemStack(ItemsList.FOOD_EEL_COOKED, 1)
			)),
			zip(listOf("reward", "reward2"), listOf(new ItemStack(ItemsList.IRIDESCITE_FISHING_ROD, 1), new ItemStack(ItemsList.INGOT_IRIDESCITE, 2))),
			listOf(begin), begin,0,-4);
		QuestData equipped = holdQuestNoMetadata("equipped", ItemsList.IRIDESCITE_PICKAXE,
			zip(listOf("retrieval","retrieval1","retrieval2","retrieval3","retrieval4","retrieval5"),
				listOf(
				new ItemStack(ItemsList.IRIDESCITE_PICKAXE, 1),
				new ItemStack(ItemsList.IRIDESCITE_AXE, 1),
				new ItemStack(ItemsList.IRIDESCITE_SWORD, 1),
				new ItemStack(ItemsList.IRIDESCITE_SHOVEL, 1),
				new ItemStack(ItemsList.IRIDESCITE_HOE, 1),
				new ItemStack(ItemsList.IRIDESCITE_FILLET_KNIFE, 1)
			)),
			zip(listOf("reward"), listOf()),
			listOf(begin), begin,-1,-5);
		QuestData fullSet = holdQuestNoMetadata("fullSet", ItemsList.IRIDESCITE_CHESTPLATE,
			zip(listOf("retrieval","retrieval1","retrieval2","retrieval3"),
				listOf(
					new ItemStack(ItemsList.IRIDESCITE_HELMET, 1),
					new ItemStack(ItemsList.IRIDESCITE_CHESTPLATE, 1),
					new ItemStack(ItemsList.IRIDESCITE_LEGGINGS, 1),
					new ItemStack(ItemsList.IRIDESCITE_BOOTS, 1)
			)),
				zip(listOf("reward"), listOf()),
				listOf(begin), begin,1,-5);
		return listOf(begin, rodUpgradeIron, rodUpgradeGold, rodUpgradeDiamond, rodUpgradeSteel, rodUpgradeIridescent,
			anchovyMaster, goldenCarpMaster, knifefishMaster, sardineMaster, sunfishMaster, tunaMaster, rockfishMaster, catfishMaster, bootfishMaster, redMulletMaster,
			herringMaster, halibutMaster, perchMaster, seaBassMaster, troutMaster, eelMaster, crabMaster,
			allTheFishium, equipped, fullSet
			);
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
	public static QuestData consumeQuest(String id,IItemConvertible icon,List<Pair<String, ItemStack>> tasks,List<Pair<String, ItemStack>> rewards,List<QuestData> preRequisites,QuestData offsetQuest,int xOffset,int yOffset) {
		List<TaskData> consumeTasks = new ArrayList<>();
		for (Pair<String, ItemStack> task : tasks) {
			consumeTasks.add(new RetrievalTaskData("btf:" + id + "/" + task.getLeft(), task.getRight()).setConsume());
		}
		List<RewardData> itemRewards = new ArrayList<>();
		for (Pair<String, ItemStack> reward : rewards) {
			itemRewards.add(new ItemRewardData("btf:" + id + "/" + reward.getLeft(), reward.getRight()));
		}
		return new QuestData("btf:" + id, "quest.btf." + id, icon, Logic.AND, Logic.AND)
			.setPreRequisites(preRequisites)
			.setTasks(consumeTasks)
			.setRewards(itemRewards)
			.setX(offsetQuest, xOffset)
			.setY(offsetQuest, yOffset);
	}
	public static QuestData holdQuestNoMetadata(String id,IItemConvertible icon,List<Pair<String, ItemStack>> tasks,List<Pair<String, ItemStack>> rewards,List<QuestData> preRequisites,QuestData offsetQuest,int xOffset,int yOffset) {
		List<TaskData> consumeTasks = new ArrayList<>();
		for (Pair<String, ItemStack> task : tasks) {
			consumeTasks.add(new RetrievalTaskData("btf:" + id + "/" + task.getLeft(), task.getRight()).setIgnoreMeta());
		}
		List<RewardData> itemRewards = new ArrayList<>();
		for (Pair<String, ItemStack> reward : rewards) {
			itemRewards.add(new ItemRewardData("btf:" + id + "/" + reward.getLeft(), reward.getRight()));
		}
		return new QuestData("btf:" + id, "quest.btf." + id, icon, Logic.AND, Logic.AND)
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
