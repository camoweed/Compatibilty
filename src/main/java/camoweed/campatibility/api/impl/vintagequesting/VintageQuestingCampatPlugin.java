package camoweed.campatibility.api.impl.vintagequesting;
import camoweed.campatibility.api.impl.vintagequesting.chapter.EpicQuestChapter;
import camoweed.campatibility.api.impl.vintagequesting.page.EpicQuestPage;
import com.llamalad7.mixinextras.lib.antlr.runtime.misc.Pair;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
import sunsetsatellite.vintagequesting.util.Logic;
import sunsetsatellite.vintagequesting.util.QuestTeam;
import sunsetsatellite.vintagequesting.util.VQPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static sunsetsatellite.catalyst.Catalyst.listOf;
import static sunsetsatellite.catalyst.Catalyst.zip;

public class VintageQuestingCampatPlugin implements VQPlugin {


	public static ChapterData EPIC_CHAPTER = new EpicQuestChapter();

	@Override
	public void initializePlugin() {
		List<QuestData> epicQuests = addEpicQuests();
		for (QuestData quest : epicQuests) {
			EPIC_CHAPTER.addQuest(quest);
		}
		VintageQuesting.LOGGER.info("Loaded quests from: epicness!");
	}

	@Override
	public boolean shouldLoad() {
		return true;
	}
	@Environment(EnvType.CLIENT)
	public void reloadClient() {
		VintageQuestingClient.CHAPTER_PAGES.unregister(EPIC_CHAPTER.getId());

		VintageQuestingClient.CHAPTER_PAGES.register(EPIC_CHAPTER.getId(), new EpicQuestPage(new Chapter(EPIC_CHAPTER, new QuestTeam("null", UUID.randomUUID()))));
	}
	public static List<QuestData> addEpicQuests() {
		QuestData welcome = new QuestData("minecraft:welcome", "quest.minecraft.welcome", Items.STICK, Logic.AND, Logic.AND)
			.setTasks(listOf(new ClickTaskData("minecraft:welcome/click")));

		return listOf(welcome);}
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
			//retrievalTasks.add(new RetrievalTaskData("minecraft:" + id + "/" + task.getLeft(), task.getRight()));
		}
		List<RewardData> itemRewards = new ArrayList<>();
		for (Pair<String, ItemStack> reward : rewards) {
			//itemRewards.add(new ItemRewardData("minecraft:" + id + "/" + reward.getLeft(), reward.getRight()));
		}
		return new QuestData("minecraft:" + id, "quest.minecraft." + id, icon, Logic.AND, Logic.AND)
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
		return new QuestData("minecraft:" + id, "quest.minecraft." + id, icon, Logic.AND, Logic.AND)
			.setPreRequisites(preRequisites)
			.setTasks(listOf(new ClickTaskData("minecraft:" + id + "/click")))
			.setX(offsetQuest, xOffset)
			.setY(offsetQuest, yOffset);
	}
	public static QuestData getQuest(String id) {
		QuestData item = VintageQuesting.QUESTS.getItem("minecraft:" + id);
		if (item == null) {
			throw new NullPointerException("Quest " + id + " not found!");
		}
		return item;
	}

}
