package crystalspider.harvestwithease.config;

import java.util.Collections;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Stream;

import crystalspider.harvestwithease.api.HarvestWithEaseAPI;
import net.minecraft.block.CropBlock;
import net.minecraft.item.ToolMaterials;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.EnumValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

/**
 * Harvest with ease Configuration.
 */
public class ModConfig {
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
  public static List<? extends String> getCrops() {
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
   * Returns the value of {@link CommonConfig#grantedExp}.
   *
   * @return {@link CommonConfig#grantedExp} as read from the {@link #COMMON common} configuration file.
   */
  public static Integer getGrantedExp() {
    return COMMON.grantedExp.get();
  }

  /**
   * Returns the value of {@link CommonConfig#playSound}.
   *
   * @return {@link CommonConfig#playSound} as read from the {@link #COMMON common} configuration file.
   */
  public static Boolean getPlaySound() {
    return COMMON.playSound.get();
  }
  
  /**
   * Returns the value of {@link CommonConfig#multiHarvestStartingTier}.
   *
   * @return {@link CommonConfig#multiHarvestStartingTier} as read from the {@link #COMMON common} configuration file.
   */
  public static String getMultiHarvestStartingTier() {
    return COMMON.multiHarvestStartingTier.get();
  }

  /**
   * Returns the value of {@link CommonConfig#areaStartingSize}.
   *
   * @return {@link CommonConfig#areaStartingSize} as read from the {@link #COMMON common} configuration file.
   */
  public static AreaSize getAreaStartingSize() {
    return COMMON.areaStartingSize.get();
  }
  
  /**
   * Returns the value of {@link CommonConfig#areaIncrementStep}.
   *
   * @return {@link CommonConfig#areaIncrementStep} as read from the {@link #COMMON common} configuration file.
   */
  public static AreaStep getAreaIncrementStep() {
    return COMMON.areaIncrementStep.get();
  }

  /**
   * Common Configuration for Harvest with ease.
   */
  public static class CommonConfig {
    /**
     * List of additional in-game IDs for crops that need to be supported but do not extend {@link CropBlock}.
     */
    private final ConfigValue<List<? extends String>> crops;
    /**
     * Whether holding a hoe (either hands) is required.
     */
    private final BooleanValue requireHoe;
    /**
     * Amount of damage to deal on a hoe when it is used to right-click harvest.
     * Effective only if greater than 0 and {@link #requireHoe} is true.
     */
    private final IntValue damageOnHarvest;
    /**
     * Amount of experience to grant on harvest.
     * Effective only if greater than 0.
     */
    private final IntValue grantedExp;
    /**
     * Whether to play a sound when harvesting a crop.
     */
    private final BooleanValue playSound;
    /**
     * Tool tier starting from which it is possible to harvest multiple crops at once.
     */
    private final ConfigValue<String> multiHarvestStartingTier;
    /**
     * Starting harvest area size (square side length).
     */
    private final EnumValue<AreaSize> areaStartingSize;
    /**
     * Increment step for the harvest area size with higher tool tiers.
     */
    private final EnumValue<AreaStep> areaIncrementStep;

    /**
     * Defines the configuration options, their default values and their comments.
     *
     * @param builder
     */
    public CommonConfig(ForgeConfigSpec.Builder builder) {
      crops = builder.comment("List of in-game IDs of additional crops").defineListAllowEmpty(List.of("crops"), Collections::emptyList, element -> element instanceof String && !((String) element).isBlank());
      requireHoe = builder.comment("Require holding a hoe (either hands) to right-click harvest").define("require hoe", false);
      damageOnHarvest = builder.comment("If [require hoe] is set to true, damage the hoe of the given amount (0 to disable, must be an integer)").defineInRange("damage on harvest", 0, 0, Integer.MAX_VALUE);
      grantedExp = builder.comment("Amount of experience to grant on harvest (0 to disable, must be an integer).").defineInRange("exp on harvest", 0, 0, Integer.MAX_VALUE);
      playSound = builder.comment("Play a sound when harvesting a crop.").define("play sound", true);
      multiHarvestStartingTier = builder.comment(
        "Tool tier starting from which it is possible to harvest multiple crops at once.",
        "All tiers that cannot multi-harvest will have a 1x1 square area of effect (a single crop).",
        "If [starting harvest area size] is set to \"" + AreaSize.SINGLE + "\" and [area increment step] to \"" + AreaStep.NONE + "\" multi-harvest will be effectively disabled, regardless of this config option value.",
        "From lesser to greater, Vanilla tiers are: " + String.join(", ", Stream.of(ToolMaterials.values()).sorted((t1, t2) -> t1.getMiningLevel() - t2.getMiningLevel()).map(tier -> "\"" + tier.toString().toLowerCase() + "\"").toArray(String[]::new)) + ".",
        "When set to \"none\", the only value not in the tiers list, multi-harvest will be enabled without a tool too. Note that [require hoe] takes precedence."
      ).define("multi-harvest starting tier", ToolMaterials.WOOD.toString().toLowerCase(), value -> value instanceof String string && (string.equalsIgnoreCase("none") || HarvestWithEaseAPI.isTierIn(Arrays.asList(ToolMaterials.values()), string)));
      areaStartingSize = builder.comment(getAreaSizeComments()).defineEnum("starting harvest area size", AreaSize.SINGLE, AreaSize.values());
      areaIncrementStep = builder.comment(getAreaStepComments()).defineEnum("area increment step", AreaStep.NONE, AreaStep.values());
    }

    /**
     * Gets the comments for {@link #areaStartingSize}.
     * 
     * @return the comments for {@link #areaStartingSize}.
     */
    private String[] getAreaSizeComments() {
      AreaSize[] sizes = AreaSize.values();
      String[] comments = new String[3 + sizes.length];
      comments[0] = "Starting multi-harvest area size (square side length).";
      comments[1] = "The area is always a square centered on the right-clicked crop.";
      comments[2] = "Setting this to \"" + AreaSize.SINGLE + "\" and [area increment step] to \"" + AreaStep.NONE + "\" will effectively disable multi-harvest.";
      for (int i = 0; i < sizes.length; i++) {
        comments[i + 3] = "\"" + sizes[i] + "\" - " + sizes[i] + " harvest area size, a " + sizes[i].size + "x" + sizes[i].size + " square.";
      }
      return comments;
    }

    /**
     * Gets the comments for {@link #areaIncrementStep}.
     * 
     * @return the comments for {@link #areaIncrementStep}.
     */
    private String[] getAreaStepComments() {
      AreaStep[] steps = AreaStep.values();
      String[] comments = new String[2 + steps.length];
      comments[0] = "Increment step for the harvest area size with higher tool tiers.";
      comments[1] = "Setting this to \"" + AreaStep.NONE + "\" and [starting harvest area size] to \"" + AreaSize.SINGLE + "\" will effectively disable multi-harvest.";
      comments[2] = "\"" + steps[0] + "\" - no increment, the area stays the same (as defined by [starting harvest area size]) regardless of the tool used, if any.";
      for (int i = 1; i < steps.length; i++) {
        comments[i + 2] = "\"" + steps[i] + "\" - " + steps[i] + " increment, the size of the area, starting from [starting harvest area size], increases by " + steps[i].step + " with each higher tier. E.g. 1x1 -> " + (1 + steps[i].step) + "x" + (1 + steps[i].step) + " -> " + (1 + steps[i].step * 2) + "x" + (1 + steps[i].step * 2) + " -> ...";
      }
      return comments;
    }
  }
}
