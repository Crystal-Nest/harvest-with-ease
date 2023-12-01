package crystalspider.harvestwithease;

import crystalspider.harvestwithease.config.ModConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

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
  public static final String PROTOCOL_VERSION = "1.19.2-8.0";
  /**
   * {@link SimpleChannel} instance for compatibility client-server.
   */
  public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(MOD_ID, "main"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, version -> true);

  public ModLoader() {
    ModLoadingContext.get().registerConfig(Type.COMMON, ModConfig.SPEC);
  }
}
