package it.crystalnest.harvest_with_ease.handler;

import it.crystalnest.harvest_with_ease.Constants;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

/**
 * Forge harvest handler.
 */
@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Bus.FORGE)
public final class ForgeHarvestHandler extends HarvestHandler {
  private ForgeHarvestHandler() {}

  /**
   * Handles the {@link BlockEvent.BreakEvent} event.
   *
   * @param event {@link BlockEvent.BreakEvent}.
   */
  @SubscribeEvent
  public static void handle(BlockEvent.BreakEvent event) {
    handle(event.getLevel(), event.getState(), event.getPos());
  }

  /**
   * Handles the {@link PlayerInteractEvent.RightClickBlock} event.
   *
   * @param event {@link PlayerInteractEvent.RightClickBlock}.
   */
  @SubscribeEvent(priority = EventPriority.HIGH)
  public static void handle(PlayerInteractEvent.RightClickBlock event) {
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
    return !player.isSpectator() && event.getUseBlock() != Event.Result.DENY && event.getUseItem() != Event.Result.DENY && event.getResult() != Event.Result.DENY;
  }
}
