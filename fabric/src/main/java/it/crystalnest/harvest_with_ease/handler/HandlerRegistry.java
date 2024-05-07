package it.crystalnest.harvest_with_ease.handler;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;

/**
 * Handler registry.
 */
public final class HandlerRegistry {
  private HandlerRegistry() {}

  /**
   * Registers all event handlers.
   */
  public static void register() {
    UseBlockCallback.EVENT.register(FabricHarvestHandler::handle);
    PlayerBlockBreakEvents.AFTER.register(FabricHarvestHandler::handle);
  }
}
