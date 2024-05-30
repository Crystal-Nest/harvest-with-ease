package it.crystalnest.harvest_with_ease.handler;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

/**
 * Fabric harvest handler.
 */
public final class FabricHarvestHandler extends HarvestHandler {
  private FabricHarvestHandler() {}

  /**
   * Handles the {@link PlayerBlockBreakEvents#AFTER} event.
   *
   * @param level level.
   * @param player player.
   * @param pos position.
   * @param state block state.
   * @param blockEntity optional block entity.
   */
  public static void handle(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity) {
    handle(level, state, pos);
  }

  /**
   * Handles the {@link UseBlockCallback#EVENT} event.
   *
   * @param player player.
   * @param level level.
   * @param hand player's hand.
   * @param hitResult {@link BlockHitResult}.
   * @return interaction result.
   */
  public static InteractionResult handle(Player player, Level level, InteractionHand hand, BlockHitResult hitResult) {
    return !player.isSpectator() && handle(level, level.getBlockState(hitResult.getBlockPos()), hitResult.getDirection(), hitResult.getBlockPos(), hitResult, player, hand) ? InteractionResult.SUCCESS : InteractionResult.PASS;
  }
}
