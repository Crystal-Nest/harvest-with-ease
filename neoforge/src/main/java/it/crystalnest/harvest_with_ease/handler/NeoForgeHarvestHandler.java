package it.crystalnest.harvest_with_ease.handler;

import it.crystalnest.harvest_with_ease.Constants;
import net.minecraft.util.TriState;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

/**
 * NeoForge harvest handler.
 */
@EventBusSubscriber(modid = Constants.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public final class NeoForgeHarvestHandler extends HarvestHandler {
  private NeoForgeHarvestHandler() {}

  /**
   * Handles the {@link BlockEvent.BreakEvent} event.
   *
   * @param event {@link BlockEvent.BreakEvent}.
   */
  @SubscribeEvent
  private static void handle(BlockEvent.BreakEvent event) {
    handle(event.getLevel(), event.getState(), event.getPos());
  }

  /**
   * Handles the {@link PlayerInteractEvent.RightClickBlock} event.
   *
   * @param event {@link PlayerInteractEvent.RightClickBlock}.
   */
  @SubscribeEvent(priority = EventPriority.HIGH)
  private static void handle(PlayerInteractEvent.RightClickBlock event) {
    if (
      canInteract(event.getEntity(), event) &&
      handle(event.getLevel(), event.getLevel().getBlockState(event.getHitVec().getBlockPos()), event.getHitVec().getDirection(), event.getHitVec().getBlockPos(), event.getHitVec(), event.getEntity(), event.getHand())
    ) {
      event.setCancellationResult(InteractionResult.SUCCESS);
      event.setCanceled(true);
    }
  }

  /**
   * Checks whether the player can interact.
   *
   * @param player player.
   * @param event {@link PlayerInteractEvent.RightClickBlock}.
   * @return whether the player can interact.
   */
  private static boolean canInteract(Player player, PlayerInteractEvent.RightClickBlock event) {
    return !player.isSpectator() && event.getUseBlock() != TriState.FALSE && event.getUseItem() != TriState.FALSE;
  }
}
