package camoweed.campatibility.item;

import luke.paxels.compat.commandly.PaxelCommandlyRules;
import net.example.superheatedsteel.item.SuperheatedIgnite;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicLog;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntityActivator;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.data.gamerule.GameRules;
import net.minecraft.core.data.gamerule.TreecapitatorHelper;
import net.minecraft.core.data.tag.Tag;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumBlockSoundEffectType;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tool.ItemToolPickaxe;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class PaxelSuperheatedSteel extends ItemToolPickaxe {
	public PaxelSuperheatedSteel(String name, String namespaceId, int id, ToolMaterial toolMaterial) {
		super(name, namespaceId, id, toolMaterial);
		this.setMaxDamage(toolMaterial.getDurability() * 2);
	}

	public float getStrVsBlock(@NotNull ItemStack itemstack, Block<?> block) {
		return !block.hasTag(BlockTags.MINEABLE_BY_PICKAXE) && !block.hasTag(BlockTags.MINEABLE_BY_AXE) && !block.hasTag(BlockTags.MINEABLE_BY_SHOVEL) && !block.hasTag(BlockTags.MINEABLE_BY_HOE) ? 1.0F : this.material.getEfficiency(false);
	}

	public boolean canHarvestBlock(@NotNull ItemStack selfStack, @NotNull Mob mob, @NotNull Block<?> block) {
		int miningLevel = miningLevels.getOrDefault(block, -1);
		if (miningLevel != -1) {
			return this.material.getMiningLevel() >= miningLevel;
		} else {
			return block.hasTag(BlockTags.MINEABLE_BY_PICKAXE) || block.hasTag(BlockTags.MINEABLE_BY_AXE) || block.hasTag(BlockTags.MINEABLE_BY_SHOVEL) || block.hasTag(BlockTags.MINEABLE_BY_HOE);
		}
	}

	public boolean beforeBlockDestroyed(@NotNull ItemStack selfStack, @NotNull World world, @NotNull Player player, @NotNull Block<?> block, @NotNull TilePosc blockPos, @NotNull Side side) {
		if (!world.isClientSide && (Boolean)world.getGameRuleValue(GameRules.TREECAPITATOR) && !player.isSneaking()) {
			Block<?> b = world.getBlockType(blockPos);
			if (Block.hasLogicClass(b, BlockLogicLog.class)) {
				return !(new TreecapitatorHelper(world, blockPos.x(), blockPos.y(), blockPos.z(), player)).chopTree();
			}
		}

//		if (!world.isClientSide && PaxelCommandlyRules.canVeinMine(world) && !player.isSneaking()) {
//			VeinMining veinMining = VeinMining.veinMining(world, selfStack, blockPos, player);
//			return !veinMining.setMiningTags(new Tag[]{BlockTags.MINEABLE_BY_PICKAXE}).mine(block, side);
//		} else {
			return true;
//		}
	}

	public boolean onUseOnBlock(@NotNull ItemStack selfStack, @NotNull World world, @Nullable Player player, @NotNull TilePosc blockPos, @NotNull Side side, double xHit, double yHit) {
		return SuperheatedIgnite.igniteGround(selfStack, world, player, blockPos, side);
	}

	public boolean shovelBlock(@NotNull ItemStack itemstack, @NotNull World world, @Nullable Player entityplayer, @NotNull TilePosc blockPos, @NotNull Side side) {
		TilePosc blockAbovePos = blockPos.add(Direction.UP, new TilePos(blockPos));
		int blockID = world.getBlockType(blockPos).id();
		Block<?> blockAbove = world.getBlockType(blockAbovePos);
		if (side != Side.BOTTOM && blockAbove.id() == Blocks.AIR.id() && (blockID == Blocks.GRASS.id() || blockID == Blocks.DIRT.id() || blockID == Blocks.GRASS_RETRO.id() || blockID == Blocks.FARMLAND_DIRT.id())) {
			world.playBlockSoundEffect(entityplayer, (double)((float)blockPos.x() + 0.5F), (double)((float)blockPos.y() + 0.5F), (double)((float)blockPos.z() + 0.5F), Blocks.getBlock(blockID), EnumBlockSoundEffectType.PLACE);
			if (!world.isClientSide) {
				world.setBlockDataNotify(blockPos, Blocks.PATH_DIRT.id());
				itemstack.damageItem(1, entityplayer);
			}

			return true;
		} else {
			return false;
		}
	}

	public void onUseByActivator(@NotNull ItemStack selfStack, @NotNull World world, @NotNull TileEntityActivator activator, @NotNull Random random, @NotNull TilePosc blockPos, @NotNull Direction direction, double offX, double offY, double offZ) {
		TilePosc tilePosc = blockPos.add(direction, new TilePos(blockPos));
		this.shovelBlock(selfStack, world, (Player)null, tilePosc, direction.side());
	}
	public boolean hitEntity(ItemStack selfStack, Mob target, Mob attacker) {
		boolean result = super.hitEntity(selfStack, target, attacker);
		SuperheatedIgnite.apply(target);
		return result;
	}

}
