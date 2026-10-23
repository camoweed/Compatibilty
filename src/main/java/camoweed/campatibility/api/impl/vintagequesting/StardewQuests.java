package camoweed.campatibility.api.impl.vintagequesting;

import camoweed.campatibility.api.impl.vintagequesting.chapter.BTFishingQuestChapter;
import camoweed.campatibility.api.impl.vintagequesting.chapter.StardewQuestChapter;
import camoweed.campatibility.api.impl.vintagequesting.page.BTFishingQuestPage;
import camoweed.campatibility.api.impl.vintagequesting.page.StardewQuestPage;
import imbacrian.btf.items.ItemsList;
import luke.stardew.items.StardewItems;
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
//fish
		QuestData fishing = new QuestData("stardew:fishing", "quest.stardew.fishing", StardewItems.TOOL_FISHINGROD_STONE, Logic.AND, Logic.AND)
			.setX(-6)
			.setY(2)
			.setPreRequisites(listOf(starter))
			.setTasks(listOf(new ClickTaskData("stardew:fishing/click")));

		QuestData rodUpgradeIron = holdQuestNoMetadata("rodUpgradeIron", StardewItems.TOOL_FISHINGROD_IRON,
			zip(listOf("retrieval"),listOf(new ItemStack(StardewItems.TOOL_FISHINGROD_IRON, 1))),
			zip(listOf("reward"), listOf(new ItemStack(ItemsList.IRIDESCENT_CLUMP, 1))),
			listOf(fishing), fishing,0,-1);
		QuestData rodUpgradeGold = holdQuestNoMetadata("rodUpgradeGold", StardewItems.TOOL_FISHINGROD_GOLD,
			zip(listOf("retrieval"),listOf(new ItemStack(StardewItems.TOOL_FISHINGROD_GOLD, 1))),
			zip(listOf("reward"), listOf(new ItemStack(ItemsList.IRIDESCENT_CLUMP, 1))),
			listOf(fishing), rodUpgradeIron,0,-1);
		QuestData rodUpgradeDiamond = holdQuestNoMetadata("rodUpgradeDiamond", StardewItems.TOOL_FISHINGROD_DIAMOND,
			zip(listOf("retrieval"),listOf(new ItemStack(StardewItems.TOOL_FISHINGROD_DIAMOND, 1))),
			zip(listOf("reward"), listOf(new ItemStack(ItemsList.IRIDESCENT_CLUMP, 1))),
			listOf(fishing), rodUpgradeGold,0,-1);
		QuestData rodUpgradeSteel = holdQuestNoMetadata("rodUpgradeSteel", StardewItems.TOOL_FISHINGROD_STEEL,
			zip(listOf("retrieval"),listOf(new ItemStack(StardewItems.TOOL_FISHINGROD_STEEL, 1))),
			zip(listOf("reward"), listOf(new ItemStack(ItemsList.IRIDESCENT_CLUMP, 1))),
			listOf(fishing), rodUpgradeDiamond,0,-1);

		QuestData salmon = holdQuestNoMetadata("salmon", StardewItems.FOOD_SALMON_RAW,
			zip(listOf("retrieval"),listOf(new ItemStack(StardewItems.FOOD_SALMON_RAW, 8))),
			zip(listOf("reward"), listOf(new ItemStack(ItemsList.IRIDESCENT_CLUMP, 1))),
			listOf(fishing), fishing,2,-1);
		QuestData bass = holdQuestNoMetadata("bass", StardewItems.FOOD_BASS_RAW,
			zip(listOf("retrieval"),listOf(new ItemStack(StardewItems.FOOD_BASS_RAW, 8))),
			zip(listOf("reward"), listOf(new ItemStack(ItemsList.IRIDESCENT_CLUMP, 1))),
			listOf(fishing), fishing,2,-2);
		QuestData snapper = holdQuestNoMetadata("snapper", StardewItems.FOOD_SNAPPER_RAW,
			zip(listOf("retrieval"),listOf(new ItemStack(StardewItems.FOOD_SNAPPER_RAW, 8))),
			zip(listOf("reward"), listOf(new ItemStack(ItemsList.IRIDESCENT_CLUMP, 1))),
			listOf(fishing), fishing,2,-3);
		QuestData fishingMaster = consumeQuest("fishingMaster", StardewItems.FISH_EEL_LAVA,
			zip(listOf("retrieval","retrieval1","retrieval2","retrieval3"),
				listOf(
					new ItemStack(StardewItems.FISH_GHOST, 1),
					new ItemStack(StardewItems.FISH_EEL_LAVA, 1),
					new ItemStack(StardewItems.FISH_SWORD, 1),
					new ItemStack(StardewItems.FISH_STONE, 1)
				)),
			zip(listOf("reward"), listOf(new ItemStack(StardewItems.ARMOR_CAN_OF_WORMS_GOLDEN, 1))),
			listOf(fishing), fishing,0,0)
			.setX(-2)
			.setY(0)
			.setPreRequisites(listOf(salmon,bass,snapper,fishing));

		QuestData farming = new QuestData("stardew:farming", "quest.stardew.farming", StardewItems.WATERING_CAN, Logic.AND, Logic.AND)
			.setX(6)
			.setY(2)
			.setPreRequisites(listOf(starter))
			.setTasks(listOf(new ClickTaskData("stardew:farming/click")));

		QuestData wateringCanUpgrade = holdQuestNoMetadata("wateringCanUpgrade", StardewItems.WATERING_CAN_STEEL,
			zip(listOf("retrieval"),listOf(new ItemStack(StardewItems.WATERING_CAN_STEEL, 1))),
			zip(listOf("reward"), listOf(new ItemStack(ItemsList.IRIDESCENT_CLUMP, 1))),
			listOf(farming), farming,0,-4);

		QuestData carrot = holdQuestNoMetadata("carrot", StardewItems.CARROT,
			zip(listOf("retrieval","retrieval1"),
				listOf(
					new ItemStack(StardewItems.CARROT, 8),
					new ItemStack(StardewItems.SEEDS_CARROT, 16)
				)),
			zip(listOf("reward"),
				listOf(new ItemStack(ItemsList.IRIDESCENT_CLUMP, 1))),
			listOf(farming), farming,0,-1);
		QuestData corn = holdQuestNoMetadata("corn", StardewItems.CORN,
		zip(listOf("retrieval","retrieval1"),
			listOf(
				new ItemStack(StardewItems.CORN, 8),
				new ItemStack(StardewItems.SEEDS_CORN, 16)
			)),
			zip(listOf("reward"),
				listOf(new ItemStack(ItemsList.IRIDESCENT_CLUMP, 1))),
			listOf(farming), farming,0,-2);
		QuestData potato = holdQuestNoMetadata("potato", StardewItems.POTATO,
		zip(listOf("retrieval","retrieval1"),
			listOf(
				new ItemStack(StardewItems.POTATO, 8),
				new ItemStack(StardewItems.SEEDS_POTATO, 16)
			)),
			zip(listOf("reward"),
				listOf(new ItemStack(ItemsList.IRIDESCENT_CLUMP, 1))),
			listOf(farming), farming,0,-3);

		QuestData berry = holdQuestNoMetadata("berry", StardewItems.BLUEBERRY,
			zip(listOf("retrieval","retrieval1"),
				listOf(
					new ItemStack(StardewItems.BLUEBERRY, 8),
					new ItemStack(StardewItems.SEEDS_BLUEBERRY, 16),
					new ItemStack(StardewItems.CRANBERRIES, 8),
					new ItemStack(StardewItems.SEEDS_CRANBERRIES, 16),
					new ItemStack(StardewItems.GRAPES, 8),
					new ItemStack(StardewItems.SEEDS_GRAPES, 16)
				)),
			zip(listOf("reward"),
				listOf(new ItemStack(ItemsList.IRIDESCENT_CLUMP, 1))),
			listOf(farming), farming,-2,-1);
		QuestData tomato = holdQuestNoMetadata("tomato", StardewItems.TOMATO,
			zip(listOf("retrieval","retrieval1"),
				listOf(
					new ItemStack(StardewItems.TOMATO, 8),
					new ItemStack(StardewItems.SEEDS_TOMATO, 16)
				)),
			zip(listOf("reward"),
				listOf(new ItemStack(ItemsList.IRIDESCENT_CLUMP, 1))),
			listOf(farming), farming,-2,-2);
		QuestData strawberry = holdQuestNoMetadata("strawberry", StardewItems.STRAWBERRY,
			zip(listOf("retrieval","retrieval1"),
				listOf(
					new ItemStack(StardewItems.STRAWBERRY, 8),
					new ItemStack(StardewItems.SEEDS_STRAWBERRY, 16)
				)),
			zip(listOf("reward"),
				listOf(new ItemStack(ItemsList.IRIDESCENT_CLUMP, 1))),
			listOf(farming), farming,-2,-3);

		QuestData farmingMaster = consumeQuest("farmingMaster", StardewItems.BEANS_COFFEE,
			zip(listOf("retrieval","retrieval1","retrieval2","retrieval3","retrieval4","retrieval5","retrieval6","retrieval7","retrieval8"),
				listOf(
					new ItemStack(StardewItems.BEANS_COFFEE, 16),
					new ItemStack(StardewItems.CARROT, 8),
					new ItemStack(StardewItems.CORN, 8),
					new ItemStack(StardewItems.POTATO, 8),
					new ItemStack(StardewItems.BLUEBERRY, 16),
					new ItemStack(StardewItems.CRANBERRIES, 16),
					new ItemStack(StardewItems.GRAPES, 16),
					new ItemStack(StardewItems.STRAWBERRY, 8),
					new ItemStack(StardewItems.PINEAPPLE, 8))),
			zip(listOf("reward"), listOf(new ItemStack(ItemsList.IRIDESCENT_CLUMP, 1))),
			listOf(farming), farming,0,0)
			.setX(2)
			.setY(0)
			.setPreRequisites(listOf(carrot,potato,corn,berry,tomato,strawberry));
		QuestData cookingMaster = consumeQuest("cookingMaster", StardewItems.FOOD_PIZZA,
			zip(listOf("retrieval",
					"retrieval1",
					"retrieval2",
					"retrieval3",
					"retrieval4",
					"retrieval5",
					"retrieval6",
					"retrieval7",
					"retrieval8",
					"retrieval9",
					"retrieval10",
					"retrieval11",
					"retrieval12",
					"retrieval13"
					),
				listOf(
					new ItemStack(StardewItems.FOOD_PIZZA, 1),
					new ItemStack(StardewItems.FOOD_APPLE_PIE, 1),
					new ItemStack(StardewItems.FOOD_CAKE_CHOCOLATE, 1),
					new ItemStack(StardewItems.JAR_JAM, 1),
					new ItemStack(StardewItems.EGG_COOKED, 16),
					new ItemStack(StardewItems.HONEY, 16),
					new ItemStack(StardewItems.FOOD_COFFEE, 1),
					new ItemStack(StardewItems.FOOD_COFFEE_LARGE, 1),
					new ItemStack(StardewItems.CHEESE, 4),
					new ItemStack(StardewItems.FOOD_STEW_FRUIT, 1),
					new ItemStack(StardewItems.FOOD_STEW_CHEESE, 1),
					new ItemStack(StardewItems.FOOD_BASS_COOKED, 1),
					new ItemStack(StardewItems.FOOD_SALMON_COOKED, 1),
					new ItemStack(StardewItems.FOOD_SNAPPER_COOKED, 1)
				)),
			zip(listOf("reward"), listOf(new ItemStack(ItemsList.IRIDESCENT_CLUMP, 1))),
			listOf(farming), farming,0,0)
			.setX(0)
			.setY(2)
			.setPreRequisites(listOf(fishing, farming, starter));

		return List.of(starter, fishing, farming,
			rodUpgradeIron, rodUpgradeGold, rodUpgradeDiamond, rodUpgradeSteel,
			salmon, bass, snapper, fishingMaster,
			wateringCanUpgrade,
			carrot,potato,corn,berry,tomato,strawberry, farmingMaster,
			cookingMaster);
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
