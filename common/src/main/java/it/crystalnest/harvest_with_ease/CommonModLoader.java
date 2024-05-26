package it.crystalnest.harvest_with_ease;

import it.crystalnest.harvest_with_ease.config.ModConfig;

/**
 * Common mod loader.
 */
public final class CommonModLoader {
  private CommonModLoader() {}

  /**
   * Initialize common operations across loaders.
   */
  public static void init() {
    ModConfig.CONFIG.register();
  }
}
