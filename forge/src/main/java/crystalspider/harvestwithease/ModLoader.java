package crystalspider.harvestwithease;

import crystalspider.harvestwithease.config.ModConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.SimpleChannel;

/**
 * Harvest with ease mod loader.
 */
@Mod(ModLoader.MOD_ID)
public class ModLoader {
  /**
   * ID of this mod.
   */
  public static final String MOD_ID = "harvestwithease";

  /**
   * Network channel protocol version.
   */
  public static final int PROTOCOL_VERSION = 1_20_2__8_0;
  /**
   * {@link SimpleChannel} instance for compatibility client-server.
   */
  public static final SimpleChannel INSTANCE = ChannelBuilder.named(new ResourceLocation(MOD_ID, "main")).networkProtocolVersion(PROTOCOL_VERSION).optionalClient().simpleChannel();

  public ModLoader() {
    ModLoadingContext.get().registerConfig(Type.COMMON, ModConfig.SPEC);
  }
}
