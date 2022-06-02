package crystalspider.harvestwithease.config;

import java.util.ArrayList;

import crystalspider.config.AbstractConfig;
import crystalspider.config.ConfigProperty;
import crystalspider.config.FabricConfig;
import crystalspider.harvestwithease.HarvestWithEaseLoader;

/**
 * Harvest with ease Configuration.
 */
public class HarvestWithEaseConfig {
  /**
   * Configuration.
   */
	private static final Config CONFIG = new Config();
  /**
   * {@link FabricConfig} {@link FabricConfig.Builder Builder}.
   */
  public static final FabricConfig.Builder BUILDER = new FabricConfig.Builder(HarvestWithEaseLoader.MODID, CONFIG);

	/**
   * Returns the value of {@link Config#crops}.
   *
   * @return {@link Config#crops} as read from the {@link #CONFIG configuration} file.
   */
	public static ArrayList<String> getCrops() {
		return CONFIG.crops.getValue();
	}

  /**
   * Returns the value of {@link Config#requireHoe}.
   *
   * @return {@link Config#requireHoe} as read from the {@link #CONFIG configuration} file.
   */
	public static Boolean getRequireHoe() {
		return CONFIG.requireHoe.getValue();
	}

  /**
   * Returns the value of {@link Config#damageOnHarvest}.
   *
   * @return {@link Config#damageOnHarvest} as read from the {@link #CONFIG configuration} file.
   */
  public static Integer getDamageOnHarvest() {
		return CONFIG.damageOnHarvest.getValue();
	}

  /**
   * Returns the value of {@link Config#grantedExp}.
   *
   * @return {@link Config#grantedExp} as read from the {@link #CONFIG configuration} file.
   */
  public static Integer getGrantedExp() {
		return CONFIG.grantedExp.getValue();
	}

  /**
   * Returns the value of {@link Config#playSound}.
   *
   * @return {@link Config#playSound} as read from the {@link #CONFIG configuration} file.
   */
	public static Boolean getPlaySound() {
		return CONFIG.playSound.getValue();
	}

  /**
   * Configuration for Harvest with ease.
   */
	private static class Config implements AbstractConfig {
		/**
     * List of additional in-game IDs for crops that need to be supported but do not extend {@link CropBlock}.
     */
    private ConfigProperty<ArrayList<String>> crops;
    /**
     * Whether holding a hoe (either hands) is required.
     */
    private ConfigProperty<Boolean> requireHoe;
    /**
     * Amount of damage to deal on a hoe when it is used to right-click harvest.
     * Effective only if greater than 0 and {@link #requireHoe} is true.
     */
    private ConfigProperty<Integer> damageOnHarvest;
    /**
     * Amount of experience to grant on harvest.
     * Effective only if greater than 0.
     */
    private ConfigProperty<Integer> grantedExp;
    /**
     * Whether to play a sound when harvesting a crop.
     */
    private ConfigProperty<Boolean> playSound;

    @Override
    public void register(FabricConfig.Builder builder) {
      crops = builder.registerProperty("crops", new ArrayList<String>(), "List of in-game IDs of additional crops", "Changes should not be necessary, edit only if you're sure of what you're doing!");
			requireHoe = builder.registerProperty("require hoe", false, "Require holding a hoe (either hands) to right-click harvest");
			damageOnHarvest = builder.registerProperty("damage on harvest", 0, "If [require hoe] is set to true, damage the hoe of the given amount (0 to disable, must be an integer)");
			grantedExp = builder.registerProperty("exp on harvest", 0, "Amount of experience to grant on harvest (0 to disable, must be an integer).");
			playSound = builder.registerProperty("play sound", true, "Play a sound when harvesting a crop.");
    }
	}
}
