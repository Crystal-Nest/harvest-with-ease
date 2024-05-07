package it.crystalnest.harvest_with_ease.platform;

import it.crystalnest.harvest_with_ease.platform.services.HarvestHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolActions;

/**
 * Forge harvest helper.
 */
public class ForgeHarvestHelper implements HarvestHelper {
  @Override
  public SoundType getSoundType(ServerLevel level, ServerPlayer player, BlockState state, BlockPos pos) {
    return state.getSoundType(level, pos, player);
  }

  @Override
  public boolean isHoe(ItemStack item) {
    return ToolActions.DEFAULT_HOE_ACTIONS.stream().allMatch(item::canPerformAction);
  }
}
