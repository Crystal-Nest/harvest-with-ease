package crystalspider.harvestwithease;

import crystalspider.harvestwithease.config.HarvestWithEaseConfig;
import crystalspider.harvestwithease.handlers.RightClickBlockHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

@Mod("harvestwithease")
public class HarvestWithEaseLoader {
    public HarvestWithEaseLoader() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, HarvestWithEaseConfig.SPEC);        
    }

    private void setup(final FMLCommonSetupEvent event) {
    	MinecraftForge.EVENT_BUS.register(new RightClickBlockHandler(HarvestWithEaseConfig.getCrops()));
    }
}
