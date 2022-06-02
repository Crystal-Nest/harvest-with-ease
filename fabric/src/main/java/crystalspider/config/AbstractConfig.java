package crystalspider.config;

/**
 * Describes the methods a Configuration class should implement.
 */
public interface AbstractConfig {
  /**
   * Registers all the configuration properties using the given {@link FabricConfig.Builder builder}.
   * 
   * @param builder - {@link FabricConfig.Builder builder} that will be used in registering the properties.
   */
  public void register(FabricConfig.Builder builder);
}
