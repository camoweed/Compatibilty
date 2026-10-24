package camoweed.campatibility.item;

import imbacrian.btf.items.ItemMaterials;
import imbacrian.btf.items.ItemsList;
import luke.paxels.ItemToolPaxel;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.Items;
import turniplabs.halplibe.helper.ItemBuilder;
import turniplabs.halplibe.helper.creativeInventory.CreativeInventoryPlacement;


public class PaxelBTFItems {
	public static Item TOOL_PAXEL_IRIDESCITE;

	public static String itemKey(String string) {
		return "campatibility" + ":item/" + string;
	}

	public static void initializeItems() {
		TOOL_PAXEL_IRIDESCITE = new ItemBuilder("campatibility")
			.setCreativeInventoryPlacement(new CreativeInventoryPlacement.After(() -> ItemsList.IRIDESCITE_HOE))
			.build(new ItemToolPaxel("tool.paxel.iridescite", itemKey("tool_paxel_iridescite"), 20007, ItemMaterials.iridesciteItem));
	}
}
