package crystalspider.harvestwithease;

import crystalspider.harvestwithease.config.ModConfig;
import crystalspider.harvestwithease.handler.PlayerBlockBreakHandler;
import crystalspider.harvestwithease.handler.UseBlockHandler;
import fuzs.forgeconfigapiport.api.config.v3.ForgeConfigRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.neoforged.fml.config.ModConfig.Type;

/**
 * Mod loader.
 */
public class ModLoader implements ModInitializer {
  /**
   * Mod ID.
   */
  public static final String MOD_ID = "harvestwithease";

  @Override
  public void onInitialize() {
    ForgeConfigRegistry.INSTANCE.register(MOD_ID, Type.COMMON, ModConfig.SPEC);
    UseBlockCallback.EVENT.register(UseBlockHandler::handle);
    PlayerBlockBreakEvents.AFTER.register(PlayerBlockBreakHandler::handle);
  }
}
