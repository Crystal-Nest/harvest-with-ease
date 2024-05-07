package it.crystalnest.harvest_with_ease;


import it.crystalnest.harvest_with_ease.config.ModConfig;

/**
 * Common mod loader.
 */
public final class CommonModLoader {
  private CommonModLoader() {}

  /**
   * Initialize operations common across loaders.
   */
  public static void init() {
    ModConfig.CONFIG.register();
  }
}
