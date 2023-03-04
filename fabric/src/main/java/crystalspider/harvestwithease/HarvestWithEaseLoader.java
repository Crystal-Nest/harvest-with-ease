package crystalspider.harvestwithease;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import crystalspider.harvestwithease.config.HarvestWithEaseConfig;
import crystalspider.harvestwithease.handlers.UseBlockHandler;
import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraftforge.fml.config.ModConfig;

/**
 * Harvest with ease mod loader.
 */
public class HarvestWithEaseLoader implements ModInitializer {
  /**
   * Harvest with ease mod ID.
   */
  public static final String MODID = "harvestwithease";

  /**
   * Logger.
   */
  public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

  @Override
  public void onInitialize() {
    ForgeConfigRegistry.INSTANCE.register(MODID, ModConfig.Type.COMMON, HarvestWithEaseConfig.SPEC);
    UseBlockCallback.EVENT.register(UseBlockHandler::handle);
  }
}
