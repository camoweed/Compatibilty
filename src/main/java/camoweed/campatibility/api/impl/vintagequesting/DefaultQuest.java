package camoweed.campatibility.api.impl.vintagequesting;
import camoweed.campatibility.api.impl.vintagequesting.chapter.DefaultQuestChapter;
import camoweed.campatibility.api.impl.vintagequesting.page.DefaultQuestPage;
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
import sunsetsatellite.vintagequesting.core.data.task.ClickTaskData;
import sunsetsatellite.vintagequesting.util.Logic;
import sunsetsatellite.vintagequesting.util.QuestTeam;
import sunsetsatellite.vintagequesting.util.VQPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static sunsetsatellite.catalyst.Catalyst.listOf;

public class DefaultQuest implements VQPlugin {


	public static ChapterData EPIC_CHAPTER = new DefaultQuestChapter()	;

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

		VintageQuestingClient.CHAPTER_PAGES.register(EPIC_CHAPTER.getId(), new DefaultQuestPage(new Chapter(EPIC_CHAPTER, new QuestTeam("null", UUID.randomUUID()))));
	}
	public static List<QuestData> addEpicQuests() {
		QuestData welcome = new QuestData("campatibility:welcome", "quest.campatibility.welcome", Items.STICK, Logic.AND, Logic.AND)
			.setTasks(listOf(new ClickTaskData("campatibility:welcome/click")));

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
			//retrievalTasks.add(new RetrievalTaskData("campatibility:" + id + "/" + task.getLeft(), task.getRight()));
		}
		List<RewardData> itemRewards = new ArrayList<>();
		for (Pair<String, ItemStack> reward : rewards) {
			//itemRewards.add(new ItemRewardData("campatibility:" + id + "/" + reward.getLeft(), reward.getRight()));
		}
		return new QuestData("campatibility:" + id, "quest.campatibility." + id, icon, Logic.AND, Logic.AND)
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
		return new QuestData("campatibility:" + id, "quest.campatibility." + id, icon, Logic.AND, Logic.AND)
			.setPreRequisites(preRequisites)
			.setTasks(listOf(new ClickTaskData("campatibility:" + id + "/click")))
			.setX(offsetQuest, xOffset)
			.setY(offsetQuest, yOffset);
	}
	public static QuestData getQuest(String id) {
		QuestData item = VintageQuesting.QUESTS.getItem("campatibility:" + id);
		if (item == null) {
			throw new NullPointerException("Quest " + id + " not found!");
		}
		return item;
	}

}
