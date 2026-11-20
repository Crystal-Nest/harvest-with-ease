package it.crystalnest.harvest_with_ease;

import it.crystalnest.harvest_with_ease.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig.Type;
import net.neoforged.fml.common.Mod;
import org.jetbrains.annotations.ApiStatus;

/**
 * Mod loader.
 */
@ApiStatus.Internal
@Mod(Constants.MOD_ID)
public class ModLoader {
  /**
   * @param container mod container used to register the configuration.
   */
  public ModLoader(ModContainer container) {
    container.registerConfig(Type.LOCAL, ModConfig.SPEC, ModConfig.FILE_NAME);
    CommonModLoader.init();
  }
}
