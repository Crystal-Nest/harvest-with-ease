package crystalspider.harvestwithease.api;

import crystalspider.harvestwithease.config.ModConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.neoforged.neoforge.common.TierSortingRegistry;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Utility class that serves as an API for mods interfacing with Harvest With Ease mod.
 */
public final class HarvestWithEaseAPI {
  private HarvestWithEaseAPI() {}

  /**
   * Checks whether the given block is a crop that can be broken and, optionally, drop xp.
   *
   * @param block
   * @return whether the given block is a valid breakable crop.
   */
  public static boolean isCrop(Block block) {
    return block instanceof CropBlock || block instanceof NetherWartBlock || block instanceof CocoaBlock || block instanceof PitcherCropBlock || ModConfig.getCrops().contains(getKey(block));
  }

  /**
   * Returns the age integer property from the given blockState.
   *
   * @param blockState {@link BlockState state} to take the age property from.
   * @return the age property from the given blockState.
   * @throws NullPointerException if the age property was null.
   * @throws NoSuchElementException if no value for the age property is present.
   * @throws ClassCastException if the age property is not an {@link IntegerProperty}.
   */
  public static IntegerProperty getAge(BlockState blockState) throws NullPointerException, NoSuchElementException, ClassCastException {
    return (IntegerProperty) blockState.getProperties().stream().filter(property -> property.getName().equals("age")).findFirst().orElseThrow();
  }

  /**
   * Checks whether the given blockstate is a mature crop.
   *
   * @param blockState {@link BlockState state} to take the age property from.
   * @param age {@link IntegerProperty integer property} for the crop age.
   * @return whether the given blockstate is a mature crop.
   */
  public static boolean isMature(BlockState blockState, IntegerProperty age) {
    return blockState.getOptionalValue(age).orElse(0) >= Collections.max(age.getPossibleValues());
  }

  /**
   * Checks whether the given blockstate is a mature crop.
   *
   * @param blockState {@link BlockState state} to take the age property from.
   * @return whether the given blockstate is a mature crop.
   * @throws NullPointerException if the age property was null.
   * @throws NoSuchElementException if no value for the age property is present.
   * @throws ClassCastException if the age property is not an {@link IntegerProperty}.
   */
  public static boolean isMature(BlockState blockState) throws NullPointerException, NoSuchElementException, ClassCastException {
    return isMature(blockState, getAge(blockState));
  }

  /**
   * Checks whether the given crop is a multi-block crop (a crop made of multiple vertically connected blocks).
   *
   * @param level {@link Level world} in which the crop is placed.
   * @param blockState {@link BlockState} of the crop.
   * @param blockPos {@link BlockPos} of the crop.
   * @return whether the given crop is a multi-block crop.
   */
  public static boolean isTallCrop(Level level, BlockState blockState, BlockPos blockPos) {
    return blockState.is(BlockTags.CROPS) && level.getBlockState(blockPos.below()).is(blockState.getBlock()) || level.getBlockState(blockPos.above()).is(blockState.getBlock());
  }

  /**
   * Checks whether the given {@link TieredItem tool} has a high enough tier for multi-harvest.
   *
   * @param tool
   * @return whether the given {@link TieredItem tool} is allowed to multi-harvest.
   */
  public static boolean isTierForMultiHarvest(TieredItem tool) {
    Tier toolTier = tool.getTier();
    ResourceLocation id = TierSortingRegistry.getName(toolTier);
    String configTier = ModConfig.getMultiHarvestStartingTier();
    return configTier.equalsIgnoreCase("none") || (
      toolTier.toString().equalsIgnoreCase(configTier) || id != null && (
        id.toString().equalsIgnoreCase(configTier) || isTierIn(TierSortingRegistry.getTiersLowerThan(toolTier), configTier)
      )
    );
  }

  /**
   * Checks if the given tier string reference is in the given list of tiers.
   *
   * @param tiers
   * @param tierRef
   * @return whether {@code tierRef} represents a {@link Tier} in {@code tiers}.
   */
  public static boolean isTierIn(List<Tier> tiers, String tierRef) {
    return tiers.stream().anyMatch(tier -> matchesTier(tierRef, tier));
  }

  /**
   * Checks if the given tier id is in the given list of tiers.
   *
   * @param tiers
   * @param tierRef
   * @return whether {@code tierRef} represents a {@link Tier} in {@code tiers}.
   */
  public static boolean isTierIn(List<Tier> tiers, ResourceLocation tierRef) {
    return isTierIn(tiers, tierRef.toString());
  }

  /**
   * Checks if the given {@link Tier} is in the given list of tiers.
   *
   * @param tiers
   * @param tier
   * @return whether {@code tier} is a {@link Tier} in {@code tiers}.
   */
  public static boolean isTierIn(List<Tier> tiers, Tier tier) {
    return isTierIn(tiers, tier.toString());
  }

  /**
   * Gets the proper tier level for the given {@link Tier}.
   * <p>
   * If the tier is not in the {@link TierSortingRegistry} then the level is {@code -1}.
   *
   * @param tier
   * @return tier level.
   */
  public static int getTierLevel(Tier tier) {
    return TierSortingRegistry.getTiersLowerThan(tier).size() + 1;
  }

  /**
   * Gets the proper tier level for the given {@link Tier} reference.
   * <p>
   * If the tier is not in the {@link TierSortingRegistry} then the level is {@code -1}.
   *
   * @param tierRef
   * @return tier level.
   */
  public static int getTierLevel(ResourceLocation tierRef) {
    return getTierLevel(TierSortingRegistry.byName(tierRef));
  }

  /**
   * Gets the proper tier level for the given {@link Tier} reference.
   * <p>
   * If {@code tierRef} is {@code "none"} then the level is {@code -1}.
   * <p>
   * If the tier is not in the {@link TierSortingRegistry} then the level is {@code 0} (same as Vanilla wood tier).
   *
   * @param tierRef
   * @return tier level.
   */
  public static int getTierLevel(String tierRef) {
    try {
      return tierRef.equalsIgnoreCase("none") ? -1 : getTierLevel(TierSortingRegistry.getSortedTiers().stream().filter(tier -> matchesTier(tierRef, tier)).findFirst().orElseThrow());
    } catch (NoSuchElementException e) {
      return 0;
    }
  }

  /**
   * Checks whether the given {@link Tier} matches the given tier reference.
   *
   * @param tier
   * @param tierRef
   * @return whether {@code tierRef} represents {@code tier}.
   */
  @SuppressWarnings("null")
  public static boolean matchesTier(String tierRef, Tier tier) {
    return tier.toString().equalsIgnoreCase(tierRef) || TierSortingRegistry.getName(tier).toString().equalsIgnoreCase(tierRef);
  }

  /**
   * Returns the in-game ID of the block passed as parameter.
   *
   * @param block
   * @return in-game ID of the given block.
   */
  private static String getKey(Block block) {
    return BuiltInRegistries.BLOCK.getKey(block).toString();
  }
}
