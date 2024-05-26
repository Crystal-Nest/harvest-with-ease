package it.crystalnest.harvest_with_ease.config;

import it.crystalnest.cobweb.api.config.CommonConfig;
import it.crystalnest.harvest_with_ease.Constants;
import net.neoforged.neoforge.common.ModConfigSpec;

/**
 * Mod common configuration.
 */
public final class ModConfig extends CommonConfig {
  /**
   * Mod common configuration.
   */
  public static final ModConfig CONFIG = register(Constants.MOD_ID, ModConfig::new);

  /**
   * Config example value.
   */
  private ModConfigSpec.BooleanValue example;

  /**
   * @param builder configuration builder.
   */
  private ModConfig(ModConfigSpec.Builder builder) {
    super(builder);
  }

  /**
   * Returns the value of {@link #example} as read from the configuration file.
   *
   * @return the value of {@link #example} as read from the configuration file.
   */
  public static Boolean getExample() {
    return CONFIG.example.get();
  }

  @Override
  protected void define(ModConfigSpec.Builder builder) {
    example = builder.comment(" Config example value").define("example", true);
  }
}
