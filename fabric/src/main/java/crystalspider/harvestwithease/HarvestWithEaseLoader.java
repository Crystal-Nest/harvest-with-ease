package crystalspider.harvestwithease;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import crystalspider.harvestwithease.config.HarvestWithEaseConfig;
import crystalspider.harvestwithease.handler.PlayerBlockBreakHandler;
import crystalspider.harvestwithease.handler.UseBlockHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraftforge.api.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig.Type;

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
  public static final Logger LOGGER = LoggerFactory.getLogger("harvestwithease");

  @Override
  public void onInitialize() {
    ModLoadingContext.registerConfig(MODID, Type.COMMON, HarvestWithEaseConfig.SPEC);
    UseBlockCallback.EVENT.register(UseBlockHandler::handle);
    PlayerBlockBreakEvents.AFTER.register(PlayerBlockBreakHandler::handle);
  }
}
