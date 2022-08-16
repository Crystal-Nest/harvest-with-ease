package crystalspider.harvestwithease;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraftforge.api.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig.Type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import crystalspider.harvestwithease.config.HarvestWithEaseConfig;
import crystalspider.harvestwithease.handlers.UseBlockHandler;

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
		UseBlockCallback.EVENT.register(new UseBlockHandler()::handle);
	}
}
