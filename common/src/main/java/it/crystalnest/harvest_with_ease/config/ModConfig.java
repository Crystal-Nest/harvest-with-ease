package it.crystalnest.harvest_with_ease.config;

import it.crystalnest.cobweb.api.config.CommonConfig;
import it.crystalnest.cobweb.api.item.TierUtils;
import it.crystalnest.harvest_with_ease.Constants;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.block.CropBlock;
import net.minecraftforge.common.ForgeConfigSpec;
import org.jetbrains.annotations.ApiStatus;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

/**
 * Mod common configuration.
 */
@ApiStatus.Internal
public final class ModConfig extends CommonConfig {
  /**
   * Mod common configuration.
   */
  public static final ModConfig CONFIG = register(Constants.MOD_ID, ModConfig::new);

  /**
   * List of additional in-game IDs for crops that need to be supported but do not extend {@link CropBlock}.
   */
  private ForgeConfigSpec.ConfigValue<List<? extends String>> crops;

  /**
   * List in-game IDs for crops that under no condition can be right-click harvested.
   */
  private ForgeConfigSpec.ConfigValue<List<? extends String>> blacklist;

  /**
   * Whether holding a hoe (either hands) is required.
   */
  private ForgeConfigSpec.BooleanValue requireHoe;

  /**
   * Amount of damage to deal on a hoe when it is used to right-click harvest.
   * Effective only if greater than 0 and {@link #requireHoe} is true.
   */
  private ForgeConfigSpec.IntValue damageOnHarvest;

  /**
   * Amount of experience to grant on harvest.
   * Effective only if greater than 0.
   */
  private ForgeConfigSpec.IntValue grantedExp;

  /**
   * Whether to use seeds from the player's inventory to replant crops if seeds are not dropped.
   */
  private ForgeConfigSpec.BooleanValue useSeedsFromInventory;

  /**
   * Whether to gather drops near the player when harvesting.
   */
  private ForgeConfigSpec.BooleanValue gatherDrops;

  /**
   * Tool tier starting from which it is possible to harvest multiple crops at once.
   */
  private ForgeConfigSpec.ConfigValue<String> multiHarvestStartingTier;

  /**
   * Starting harvest area size (square side length).
   */
  private ForgeConfigSpec.EnumValue<AreaSize> areaStartingSize;

  /**
   * Increment step for the harvest area size with higher tool tiers.
   */
  private ForgeConfigSpec.EnumValue<AreaStep> areaIncrementStep;

  /**
   * @param builder configuration builder.
   */
  private ModConfig(ForgeConfigSpec.Builder builder) {
    super(builder);
  }

  /**
   * Returns the value of {@link #crops} as read from the configuration file.
   *
   * @return the value of {@link #crops} as read from the configuration file.
   */
  public static List<? extends String> getCrops() {
    return CONFIG.crops.get();
  }

  /**
   * Returns the value of {@link #blacklist} as read from the configuration file.
   *
   * @return the value of {@link #blacklist} as read from the configuration file.
   */
  public static List<? extends String> getBlacklist() {
    return CONFIG.blacklist.get();
  }

  /**
   * Returns the value of {@link #requireHoe} as read from the configuration file.
   *
   * @return the value of {@link #requireHoe} as read from the configuration file.
   */
  public static Boolean getRequireHoe() {
    return CONFIG.requireHoe.get();
  }

  /**
   * Returns the value of {@link #damageOnHarvest} as read from the configuration file.
   *
   * @return the value of {@link #damageOnHarvest} as read from the configuration file.
   */
  public static Integer getDamageOnHarvest() {
    return CONFIG.damageOnHarvest.get();
  }

  /**
   * Returns the value of {@link #grantedExp} as read from the configuration file.
   *
   * @return the value of {@link #grantedExp} as read from the configuration file.
   */
  public static Integer getGrantedExp() {
    return CONFIG.grantedExp.get();
  }

  /**
   * Returns the value of {@link #useSeedsFromInventory} as read from the configuration file.
   *
   * @return the value of {@link #useSeedsFromInventory} as read from the configuration file.
   */
  public static Boolean getUseSeedsFromInventory() {
    return CONFIG.useSeedsFromInventory.get();
  }

  /**
   * Returns the value of {@link #gatherDrops} as read from the configuration file.
   *
   * @return the value of {@link #gatherDrops} as read from the configuration file.
   */
  public static Boolean getGatherDrops() {
    return CONFIG.gatherDrops.get();
  }

  /**
   * Returns the value of {@link #multiHarvestStartingTier} as read from the configuration file.
   *
   * @return the value of {@link #multiHarvestStartingTier} as read from the configuration file.
   */
  public static String getMultiHarvestStartingTier() {
    return CONFIG.multiHarvestStartingTier.get();
  }

  /**
   * Returns the value of {@link #areaStartingSize} as read from the configuration file.
   *
   * @return the value of {@link #areaStartingSize} as read from the configuration file.
   */
  public static AreaSize getAreaStartingSize() {
    return CONFIG.areaStartingSize.get();
  }

  /**
   * Returns the value of {@link #areaIncrementStep} as read from the configuration file.
   *
   * @return the value of {@link #areaIncrementStep} as read from the configuration file.
   */
  public static AreaStep getAreaIncrementStep() {
    return CONFIG.areaIncrementStep.get();
  }

  /**
   * Gets the comments for {@link #areaStartingSize}.
   *
   * @return the comments for {@link #areaStartingSize}.
   */
  private static String[] getAreaSizeComments() {
    AreaSize[] sizes = AreaSize.values();
    String[] comments = new String[3 + sizes.length];
    comments[0] = " Starting multi-harvest area size (square side length).";
    comments[1] = " The area is always a square centered on the right-clicked crop.";
    comments[2] = " Setting this to \"" + AreaSize.SINGLE + "\" and [area increment step] to \"" + AreaStep.NONE + "\" will effectively disable multi-harvest.";
    for (int i = 0; i < sizes.length; i++) {
      comments[i + 3] = " \"" + sizes[i] + "\" - " + sizes[i] + " harvest area size, a " + sizes[i].size + "x" + sizes[i].size + " square.";
    }
    return comments;
  }

  /**
   * Gets the comments for {@link #areaIncrementStep}.
   *
   * @return the comments for {@link #areaIncrementStep}.
   */
  private static String[] getAreaStepComments() {
    AreaStep[] steps = AreaStep.values();
    String[] comments = new String[2 + steps.length];
    comments[0] = " Increment step for the harvest area size with higher tool tiers.";
    comments[1] = " Setting this to \"" + AreaStep.NONE + "\" and [starting harvest area size] to \"" + AreaSize.SINGLE + "\" will effectively disable multi-harvest.";
    comments[2] = " \"" + steps[0] + "\" - no increment, the area stays the same (as defined by [starting harvest area size]) regardless of the tool used, if any.";
    for (int i = 1; i < steps.length; i++) {
      comments[i + 2] = " \"" + steps[i] + "\" - " + steps[i] + " increment, the size of the area, starting from [starting harvest area size], increases by " + steps[i].step + " with each higher tier." +
                        "E.g. 1x1 -> " + (1 + steps[i].step) + "x" + (1 + steps[i].step) + " -> " + (1 + steps[i].step * 2) + "x" + (1 + steps[i].step * 2) + " -> ...";
    }
    return comments;
  }

  @Override
  private void define(ForgeConfigSpec.Builder builder) {
    crops = builder.comment(" List of in-game IDs of additional crops.").defineListAllowEmpty(List.of("crops"), Collections::emptyList, this::stringListValidator);
    blacklist = builder.comment(" List of in-game IDs for crops that under no condition can be right-click harvested.").defineListAllowEmpty(List.of("blacklist"), Collections::emptyList, this::stringListValidator);
    requireHoe = builder.comment(" Require holding a hoe (either hands) to right-click harvest.").define("require hoe", false);
    damageOnHarvest = builder.comment(" If [require hoe] is set to true, damage the hoe of the given amount (0 to disable, must be an integer).").defineInRange("damage on harvest", 0, 0, Integer.MAX_VALUE);
    grantedExp = builder.comment(" Amount of experience to grant on harvest (0 to disable, must be an integer).").defineInRange("exp on harvest", 0, 0, Integer.MAX_VALUE);
    useSeedsFromInventory = builder.comment(" Whether to use seeds from the player's inventory to replant crops if seeds are not dropped.").define("use seeds from inventory", true);
    gatherDrops = builder.comment(" Whether to gather drops near the player when harvesting.").define("gather drops", false);
    multiHarvestStartingTier = builder.comment(
      " Tool tier starting from which it is possible to harvest multiple crops at once.",
      " All tiers that cannot multi-harvest will have a 1x1 square area of effect (a single crop).",
      " If [starting harvest area size] is set to \"" + AreaSize.SINGLE + "\" and [area increment step] to \"" + AreaStep.NONE + "\" multi-harvest will be effectively disabled, regardless of this config option value.",
      " From lesser to greater, Vanilla tiers are: " + String.join(", ", Stream.of(Tiers.values()).sorted(TierUtils::compare).map(tier -> "\"" + tier.toString().toLowerCase() + "\"").toArray(String[]::new)) + ".",
      " When set to \"none\", multi-harvest will be enabled without a tool too. Note that [require hoe] takes precedence.",
      " The tier can be specified with either the name of the tier, e.g. \"iron\", or the id of the tier, e.g. \"minecraft:iron\"."
    ).define(
      "multi-harvest starting tier",
      Tiers.WOOD.toString().toLowerCase(),
      // With Forge/NeoForge tier registry, the list of all tiers is empty when the game starts and configurations are first checked.
      value -> value instanceof String string && ("none".equalsIgnoreCase(string) || TierUtils.getAllTiers().isEmpty() || TierUtils.isIn(TierUtils.getAllTiers(), string))
    );
    areaStartingSize = builder.comment(getAreaSizeComments()).defineEnum("starting harvest area size", AreaSize.SINGLE, AreaSize.values());
    areaIncrementStep = builder.comment(getAreaStepComments()).defineEnum("area increment step", AreaStep.NONE, AreaStep.values());
  }
}
