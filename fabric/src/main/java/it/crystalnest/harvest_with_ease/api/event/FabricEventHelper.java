package it.crystalnest.harvest_with_ease.api.event;

import it.crystalnest.harvest_with_ease.platform.services.EventHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

/**
 * Fabric event helper.
 */
@ApiStatus.Internal
public class FabricEventHelper implements EventHelper {
  @Override
  public boolean fireHarvestCheckEvent(Level level, BlockState crop, BlockPos pos, Direction face, @Nullable BlockHitResult hitResult, Player player, InteractionHand hand) {
    return HarvestEvents.CHECK.invoker().check(new HarvestEvents.HarvestCheckEvent(level, crop, pos, face, hitResult, player, hand));
  }

  @Override
  public void fireBeforeHarvestEvent(ServerLevel level, BlockState crop, BlockPos pos, Direction face, @Nullable BlockHitResult hitResult, ServerPlayer player, InteractionHand hand) {
    HarvestEvents.BEFORE.invoker().beforeHarvest(new HarvestEvents.BeforeHarvestEvent(level, crop, pos, face, hitResult, player, hand));
  }

  @Override
  public HarvestEvent.HarvestDropsEvent fireHarvestDropsEvent(ServerLevel level, BlockState crop, BlockPos pos, Direction face, @Nullable BlockHitResult hitResult, ServerPlayer player, InteractionHand hand) {
    return HarvestEvents.DROPS.invoker().getResult(new HarvestEvents.HarvestDropsEvent(level, crop, pos, face, hitResult, player, hand));
  }

  @Override
  public void fireAfterHarvestEvent(ServerLevel level, BlockState crop, BlockPos pos, Direction face, @Nullable BlockHitResult hitResult, ServerPlayer player, InteractionHand hand) {
    HarvestEvents.AFTER.invoker().afterHarvest(new HarvestEvents.AfterHarvestEvent(level, crop, pos, face, hitResult, player, hand));
  }
}
