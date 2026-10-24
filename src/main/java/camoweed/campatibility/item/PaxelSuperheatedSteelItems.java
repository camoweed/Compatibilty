package camoweed.campatibility.item;

import luke.paxels.ItemToolPaxel;
import net.example.superheatedsteel.item.SuperheatedSteelMaterial;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.Items;
import turniplabs.halplibe.helper.ItemBuilder;
import turniplabs.halplibe.helper.creativeInventory.CreativeInventoryPlacement;

public class PaxelSuperheatedSteelItems {
	public static Item TOOL_PAXEL_SUPERHEATED_STEEL;

	public static String itemKey(String string) {
		return "campatibility" + ":item/" + string;
	}

	public static void initializeItems() {
		TOOL_PAXEL_SUPERHEATED_STEEL = new ItemBuilder("campatibility")
			.setCreativeInventoryPlacement(new CreativeInventoryPlacement.After(() -> Items.TOOL_SWORD_STEEL))
			.build(new PaxelSuperheatedSteel(
				"tool.paxel.superheated_steel",
				itemKey("tool_paxel_superheated_steel"),
				20008,
				SuperheatedSteelMaterial.INSTANCE));
	}

}
