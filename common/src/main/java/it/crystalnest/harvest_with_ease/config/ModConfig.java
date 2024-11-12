package it.crystalnest.harvest_with_ease.config;

import it.crystalnest.cobweb.api.config.CommonConfig;
import it.crystalnest.harvest_with_ease.Constants;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.block.CropBlock;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;
import net.neoforged.neoforge.common.ModConfigSpec.EnumValue;
import net.neoforged.neoforge.common.ModConfigSpec.IntValue;
import org.jetbrains.annotations.ApiStatus;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mod common configuration.
 */
@ApiStatus.Internal
public final class ModConfig extends CommonConfig {
  /**
   * Default value for the tier list.<br />
   * Needs to be initialized before {@link #CONFIG} to avoid NPE when used inside {@link #define(ModConfigSpec.Builder)}.
   */
  private static final List<String> DEFAULT_TIER_LIST = List.of(
    "none",
    getTierName(Tiers.WOOD),
    getTierName(Tiers.STONE),
    getTierName(Tiers.IRON),
    getTierName(Tiers.GOLD),
    getTierName(Tiers.DIAMOND),
    getTierName(Tiers.NETHERITE)
  );

  /**
   * Mod common configuration.
   */
  public static final ModConfig CONFIG = register(Constants.MOD_ID, ModConfig::new);

  /**
   * List of additional in-game IDs for crops that need to be supported but do not extend {@link CropBlock}.
   */
  private ConfigValue<List<? extends String>> crops;

  /**
   * List in-game IDs for crops that under no condition can be right-click harvested.
   */
  private ConfigValue<List<? extends String>> blacklist;

  /**
   * Whether holding a hoe (either hands) is required.
   */
  private BooleanValue requireHoe;

  /**
   * Amount of damage to deal on a hoe when it is used to right-click harvest.
   * Effective only if greater than 0 and {@link #requireHoe} is true.
   */
  private IntValue damageOnHarvest;

  /**
   * Amount of experience to grant on harvest.
   * Effective only if greater than 0.
   */
  private IntValue grantedExp;

  /**
   * Whether to gather drops near the player when harvesting.
   */
  private BooleanValue gatherDrops;

  /**
   * Ordered list of tiers.
   */
  private ConfigValue<List<? extends String>> tiers;

  /**
   * Tool tier starting from which it is possible to harvest multiple crops at once.
   */
  private ConfigValue<String> multiHarvestStartingTier;

  /**
   * Starting harvest area size (square side length).
   */
  private EnumValue<AreaSize> areaStartingSize;

  /**
   * Increment step for the harvest area size with higher tool tiers.
   */
  private EnumValue<AreaStep> areaIncrementStep;

  /**
   * @param builder configuration builder.
   */
  private ModConfig(ModConfigSpec.Builder builder) {
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
   * Returns the value of {@link #gatherDrops} as read from the configuration file.
   *
   * @return the value of {@link #gatherDrops} as read from the configuration file.
   */
  public static Boolean getGatherDrops() {
    return CONFIG.gatherDrops.get();
  }

  /**
   * Returns the value of {@link #tiers} as read from the configuration file.
   *
   * @return the value of {@link #tiers} as read from the configuration file.
   */
  public static List<? extends String> getTiers() {
    return CONFIG.tiers.get();
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

  /**
   * Returns the tier name.
   *
   * @param tier tier.
   * @return tier name.
   */
  private static String getTierName(Tiers tier) {
    return tier.name().toLowerCase();
  }

  @Override
  protected void define(ModConfigSpec.Builder builder) {
    crops = builder.comment(" List of in-game IDs of additional crops.").defineListAllowEmpty(List.of("crops"), Collections::emptyList, this::stringListValidator);
    blacklist = builder.comment(" List of in-game IDs for crops that under no condition can be right-click harvested.").defineListAllowEmpty(List.of("blacklist"), Collections::emptyList, this::stringListValidator);
    requireHoe = builder.comment(" Require holding a hoe (either hands) to right-click harvest.").define("require hoe", false);
    damageOnHarvest = builder.comment(" If [require hoe] is set to true, damage the hoe of the given amount (0 to disable, must be an integer).").defineInRange("damage on harvest", 0, 0, Integer.MAX_VALUE);
    grantedExp = builder.comment(" Amount of experience to grant on harvest (0 to disable, must be an integer).").defineInRange("exp on harvest", 0, 0, Integer.MAX_VALUE);
    gatherDrops = builder.comment(" Whether to gather drops near the player when harvesting.").define("gather drops", false);
    tiers = builder.comment(
      " Ordered list of tiers.",
      " Used to determine the tier level for the other configuration options below.",
      " \"none\" is a special value that represents not using a tool.",
      " The tier name is made of two parts: a namespace and a name.",
      " The namespace is an optional mod ID and defaults to \"minecraft\" if not specified. The name can be either the tier name, e.g. \"iron\" (this is not granted to work aside from Vanilla tiers) or the tier tag, e.g. \"incorrect_for_iron_tool\".",
      " Examples: \"iron\", \"incorrect_for_iron_tool\", \"minecraft:iron\", \"minecraft:incorrect_for_iron_tool\"."
    ).defineListAllowEmpty(
      List.of("tiers"),
      DEFAULT_TIER_LIST,
      this::stringListValidator
    );
    multiHarvestStartingTier = builder.comment(
      " Tool tier starting from which it is possible to harvest multiple crops at once.",
      " All tiers that cannot multi-harvest will have a 1x1 square area of effect (a single crop).",
      " If [starting harvest area size] is set to \"" + AreaSize.SINGLE + "\" and [area increment step] to \"" + AreaStep.NONE + "\" multi-harvest will be effectively disabled, regardless of this config option value.",
      " From lesser to greater, default Vanilla tiers are: " + DEFAULT_TIER_LIST.stream().map(tier -> "\"" + tier + "\"").collect(Collectors.joining(", ")) + ".",
      " When set to \"none\", multi-harvest will be enabled without a tool too. Note that [require hoe] takes precedence.",
      " The tier name is made of two parts: a namespace and a name.",
      " The namespace is an optional mod ID and defaults to \"minecraft\" if not specified. The name can be either the tier name, e.g. \"iron\" (this is not granted to work aside from Vanilla tiers) or the tier tag, e.g. \"incorrect_for_iron_tool\".",
      " Examples: \"iron\", \"incorrect_for_iron_tool\", \"minecraft:iron\", \"minecraft:incorrect_for_iron_tool\"."
    ).define(
      "multi-harvest starting tier",
      getTierName(Tiers.WOOD),
      this::stringListValidator
    );
    areaStartingSize = builder.comment(getAreaSizeComments()).defineEnum("starting harvest area size", AreaSize.SINGLE, AreaSize.values());
    areaIncrementStep = builder.comment(getAreaStepComments()).defineEnum("area increment step", AreaStep.NONE, AreaStep.values());
  }
}
