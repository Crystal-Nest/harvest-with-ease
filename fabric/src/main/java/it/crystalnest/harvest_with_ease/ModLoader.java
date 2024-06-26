package it.crystalnest.harvest_with_ease;

import it.crystalnest.harvest_with_ease.handler.HandlerRegistry;
import net.fabricmc.api.ModInitializer;
import org.jetbrains.annotations.ApiStatus;

/**
 * Mod loader.
 */
@ApiStatus.Internal
public class ModLoader implements ModInitializer {
  @Override
  public void onInitialize() {
    CommonModLoader.init();
    HandlerRegistry.register();
  }
}
