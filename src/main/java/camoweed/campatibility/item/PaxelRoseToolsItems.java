package camoweed.campatibility.item;

import camoweed.rosesword.ModItems;
import luke.paxels.ItemToolPaxel;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.Items;
import turniplabs.halplibe.helper.ItemBuilder;
import turniplabs.halplibe.helper.creativeInventory.CreativeInventoryPlacement;


public class PaxelRoseToolsItems {
	public static Item TOOL_PAXEL_ROSE;

	public static String itemKey(String string) {
		return "campatibility" + ":item/" + string;
	}

	public static void initializeItems() {
		TOOL_PAXEL_ROSE = new ItemBuilder("campatibility")
			.setCreativeInventoryPlacement(new CreativeInventoryPlacement.After(() -> Items.TOOL_SWORD_STEEL))
			.build(new ItemToolPaxel("tool.paxel.rose", itemKey("tool_paxel_rose"), 20006, ModItems.roseToolMaterial));
	}
}
