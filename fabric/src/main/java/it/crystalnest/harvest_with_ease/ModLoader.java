package it.crystalnest.harvest_with_ease;

import it.crystalnest.harvest_with_ease.handler.HandlerRegistry;
import fuzs.forgeconfigapiport.fabric.api.v5.ConfigRegistry;
import it.crystalnest.harvest_with_ease.config.ModConfig;
import net.fabricmc.api.ModInitializer;
import net.neoforged.fml.config.ModConfig.Type;
import org.jetbrains.annotations.ApiStatus;

/**
 * Mod loader.
 */
@ApiStatus.Internal
public class ModLoader implements ModInitializer {
  @Override
  public void onInitialize() {
    ConfigRegistry.INSTANCE.register(Constants.MOD_ID, Type.COMMON, ModConfig.SPEC, ModConfig.FILE_NAME);
    CommonModLoader.init();
    HandlerRegistry.register();
  }
}
