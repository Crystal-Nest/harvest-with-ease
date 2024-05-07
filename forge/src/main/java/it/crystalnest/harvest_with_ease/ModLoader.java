package it.crystalnest.harvest_with_ease;

import net.minecraftforge.fml.common.Mod;

/**
 * Mod loader.
 */
@Mod(Constants.MOD_ID)
public class ModLoader {
  /**
   * Mod initialization.
   */
  public ModLoader() {
    CommonModLoader.init();
  }
}
