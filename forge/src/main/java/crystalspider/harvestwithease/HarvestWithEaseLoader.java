package crystalspider.harvestwithease;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import crystalspider.harvestwithease.config.HarvestWithEaseConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

/**
 * Harvest with ease mod loader.
 */
@Mod(HarvestWithEaseLoader.MODID)
public class HarvestWithEaseLoader {
  /**
   * ID of this mod.
   */
  public static final String MODID = "harvestwithease";

  /**
   * Logger.
   */
  public static final Logger LOGGER = LogUtils.getLogger();

  /**
   * Network channel protocol version.
   */
  public static final String PROTOCOL_VERSION = "1.19-6.1";
  /**
   * {@link SimpleChannel} instance for compatibility client-server.
   */
  public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(MODID, "main"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

  public HarvestWithEaseLoader() {
    ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, HarvestWithEaseConfig.SPEC);
  }
}
