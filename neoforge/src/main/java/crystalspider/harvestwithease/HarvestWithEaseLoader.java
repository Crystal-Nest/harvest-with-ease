package crystalspider.harvestwithease;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import crystalspider.harvestwithease.config.HarvestWithEaseConfig;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.network.NetworkRegistry.ChannelBuilder;
import net.neoforged.neoforge.network.simple.SimpleChannel;
import net.neoforged.fml.config.ModConfig;

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
  public static final String PROTOCOL_VERSION = "1.20.2-7.1";
  /**
   * {@link SimpleChannel} instance for compatibility client-server.
   */
  public static final SimpleChannel INSTANCE = ChannelBuilder.named(new ResourceLocation(MODID, "main")).networkProtocolVersion(() -> PROTOCOL_VERSION).clientAcceptedVersions(PROTOCOL_VERSION::equals).serverAcceptedVersions(PROTOCOL_VERSION::equals).simpleChannel();

  public HarvestWithEaseLoader() {
    ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, HarvestWithEaseConfig.SPEC);
  }
}
