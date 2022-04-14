package crystalspider.harvestwithease.config;

import java.util.ArrayList;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

/**
 * Harvest with ease Configuration.
 */
public class HarvestWithEaseConfig {
  /**
   * {@link ForgeConfigSpec} {@link ForgeConfigSpec.Builder Builder}.
   */
	private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
  /**
   * Common Configuration as read from the configuration file.
   */
	public static final CommonConfig COMMON = new CommonConfig(BUILDER);
  /**
   * {@link ForgeConfigSpec}.
   */
	public static final ForgeConfigSpec SPEC = BUILDER.build();
	
  /**
   * Returns the value of {@link CommonConfig#crops}.
   *
   * @return {@link CommonConfig#crops} as read from the {@link #COMMON common} configuration file.
   */
	public static ArrayList<String> getCrops() {
		return COMMON.crops.get();
	}

  /**
   * Returns the value of {@link CommonConfig#requireHoe}.
   *
   * @return {@link CommonConfig#requireHoe} as read from the {@link #COMMON common} configuration file.
   */
	public static Boolean getRequireHoe() {
		return COMMON.requireHoe.get();
	}

  /**
   * Returns the value of {@link CommonConfig#damageOnHarvest}.
   *
   * @return {@link CommonConfig#damageOnHarvest} as read from the {@link #COMMON common} configuration file.
   */
  public static Integer getDamageOnHarvest() {
		return COMMON.damageOnHarvest.get();
	}

  /**
   * Common Configuration for Harvest with ease.
   */
  public static class CommonConfig {
    /**
     * List of additional in-game IDs for crops that need to be supported but do not extend {@link CropBlock}.
     */
    private final ConfigValue<ArrayList<String>> crops;
    /**
     * Whether holding a hoe (either hands) is required.
     */
    private final ConfigValue<Boolean> requireHoe;
    /**
     * Amount of damage to deal on a hoe when it is used to right-click harvest.
     * Effective only if greater than 0 and {@link #requireHoe} is true.
     */
    private final ConfigValue<Integer> damageOnHarvest;

    /**
     * Defines the configuration options, their default values and their comments.
     *
     * @param builder
     */
		public CommonConfig(ForgeConfigSpec.Builder builder) {
			crops = builder.comment("List of in-game IDs of additional crops").define("crops", new ArrayList<String>());
			requireHoe = builder.comment("Require holding a hoe (either hands) to right-click harvest").define("require hoe", false);
			damageOnHarvest = builder.comment("If [require hoe] is set to true, damage the hoe of the given amount (0 to disable, must be an integer)").define("damage on harvest", Integer.valueOf(0));
		}
	}
}
