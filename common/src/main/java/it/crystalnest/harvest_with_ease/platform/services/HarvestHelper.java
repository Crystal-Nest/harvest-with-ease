package it.crystalnest.harvest_with_ease.platform.services;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Platform specific harvest helper.
 */
public interface HarvestHelper {
  /**
   * Retrieves the correct sound type for the crop.
   *
   * @param level level.
   * @param player player.
   * @param crop crop.
   * @param pos position.
   * @return correct sound type.
   */
  SoundType getSoundType(ServerLevel level, ServerPlayer player, BlockState crop, BlockPos pos);

  /**
   * Returns whether the given item is a hoe.
   *
   * @param item item.
   * @return whether the item is a hoe.
   */
  boolean isHoe(ItemStack item);
}
