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
import net.minecraftforge.common.MinecraftForge;
import org.jetbrains.annotations.Nullable;

/**
 * Forge event helper.
 */
public class ForgeEventHelper implements EventHelper {
  @Override
  public boolean fireHarvestCheckEvent(Level level, BlockState crop, BlockPos pos, Direction face, @Nullable BlockHitResult hitResult, Player player, InteractionHand hand) {
    HarvestEvents.HarvestCheckEvent event = new HarvestEvents.HarvestCheckEvent(level, crop, pos, face, hitResult, player, hand);
    MinecraftForge.EVENT_BUS.post(event);
    return event.canHarvest();
  }

  @Override
  public void fireBeforeHarvestEvent(ServerLevel level, BlockState crop, BlockPos pos, Direction face, @Nullable BlockHitResult hitResult, ServerPlayer player, InteractionHand hand) {
    MinecraftForge.EVENT_BUS.post(new HarvestEvents.BeforeHarvestEvent(level, crop, pos, face, hitResult, player, hand));
  }

  @Override
  public HarvestEvent.HarvestDropsEvent fireHarvestDropsEvent(ServerLevel level, BlockState crop, BlockPos pos, Direction face, @Nullable BlockHitResult hitResult, ServerPlayer player, InteractionHand hand) {
    HarvestEvents.HarvestDropsEvent event = new HarvestEvents.HarvestDropsEvent(level, crop, pos, face, hitResult, player, hand);
    MinecraftForge.EVENT_BUS.post(event);
    return event;
  }

  @Override
  public void fireAfterHarvestEvent(ServerLevel level, BlockState crop, BlockPos pos, Direction face, @Nullable BlockHitResult hitResult, ServerPlayer player, InteractionHand hand) {
    MinecraftForge.EVENT_BUS.post(new HarvestEvents.AfterHarvestEvent(level, crop, pos, face, hitResult, player, hand));
  }
}
