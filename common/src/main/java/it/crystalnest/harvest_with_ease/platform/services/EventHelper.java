package it.crystalnest.harvest_with_ease.platform.services;

import it.crystalnest.harvest_with_ease.api.event.HarvestEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

/**
 * Platform specific event helper.
 */
public interface EventHelper {
  /**
   * Fires a new {@link HarvestEvent.HarvestCheckEvent HarvestCheckEvent}.
   *
   * @param level level.
   * @param crop crop.
   * @param pos position.
   * @param face right-click face.
   * @param hitResult {@link BlockHitResult}.
   * @param player player harvesting.
   * @param hand player's hand.
   * @return whether the crop can be harvested.
   */
  boolean fireHarvestCheckEvent(Level level, BlockState crop, BlockPos pos, Direction face, @Nullable BlockHitResult hitResult, Player player, InteractionHand hand);

  /**
   * Fires a new {@link HarvestEvent.BeforeHarvestEvent BeforeHarvestEvent}.
   *
   * @param level level.
   * @param crop crop.
   * @param pos position.
   * @param face right-click face.
   * @param hitResult {@link BlockHitResult}.
   * @param player player harvesting.
   * @param hand player's hand.
   */
  void fireBeforeHarvestEvent(ServerLevel level, BlockState crop, BlockPos pos, Direction face, @Nullable BlockHitResult hitResult, ServerPlayer player, InteractionHand hand);

  /**
   * Fires a new {@link HarvestEvent.HarvestDropsEvent HarvestDropsEvent}.
   *
   * @param level level.
   * @param crop crop.
   * @param pos position.
   * @param face right-click face.
   * @param hitResult {@link BlockHitResult}.
   * @param player player harvesting.
   * @param hand player's hand.
   * @return the harvest drops.
   */
  HarvestEvent.HarvestDropsEvent fireHarvestDropsEvent(ServerLevel level, BlockState crop, BlockPos pos, Direction face, @Nullable BlockHitResult hitResult, ServerPlayer player, InteractionHand hand);

  /**
   * Fires a new {@link HarvestEvent.AfterHarvestEvent AfterHarvestEvent}.
   *
   * @param level level.
   * @param crop crop.
   * @param pos position.
   * @param face right-click face.
   * @param hitResult {@link BlockHitResult}.
   * @param player player harvesting.
   * @param hand player's hand.
   */
  void fireAfterHarvestEvent(ServerLevel level, BlockState crop, BlockPos pos, Direction face, @Nullable BlockHitResult hitResult, ServerPlayer player, InteractionHand hand);
}
