package crystalspider.harvestwithease;

import crystalspider.config.FabricConfig;
import crystalspider.harvestwithease.config.HarvestWithEaseConfig;
import crystalspider.harvestwithease.handlers.UseBlockHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
  public static final Logger LOGGER = LoggerFactory.getLogger("Harvestwithease");

  @Override
	public void onInitialize() {
    FabricConfig.register(HarvestWithEaseConfig.BUILDER);
		UseBlockCallback.EVENT.register(new UseBlockHandler()::handle);
	}
}
