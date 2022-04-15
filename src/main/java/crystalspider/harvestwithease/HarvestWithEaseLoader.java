package crystalspider.harvestwithease;

import crystalspider.harvestwithease.config.HarvestWithEaseConfig;
import crystalspider.harvestwithease.handlers.RightClickBlockHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

/**
 * Harvest with ease mod loader.
 */
@Mod("harvestwithease")
public class HarvestWithEaseLoader {
  /**
   * Adds {@link #setup(FMLCommonSetupEvent)} to the Mod event bus listeners and registers the {@link HarvestWithEaseConfig configuration}.
   */
  public HarvestWithEaseLoader() {
    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, HarvestWithEaseConfig.SPEC);
  }

  /**
   * Registers a new instance of {@link RightClickBlockHandler} to the event bus.
   * 
   * @param event
   */
  private void setup(final FMLCommonSetupEvent event) {
    MinecraftForge.EVENT_BUS.register(new RightClickBlockHandler());
  }
}
