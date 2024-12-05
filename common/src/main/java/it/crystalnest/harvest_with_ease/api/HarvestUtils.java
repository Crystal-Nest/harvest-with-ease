package it.crystalnest.harvest_with_ease.api;

import it.crystalnest.cobweb.api.block.BlockUtils;
import it.crystalnest.harvest_with_ease.Constants;
import it.crystalnest.harvest_with_ease.config.ModConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CocoaBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.PitcherCropBlock;
import net.minecraft.world.level.block.TorchflowerCropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.ApiStatus;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Utility methods for harvest and crop related stuff.
 */
public final class HarvestUtils {
  /**
   * Block tag for blacklisted crops.
   */
  public static final TagKey<Block> BLACKLIST = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "blacklist"));

  private HarvestUtils() {}

  /**
   * Checks whether the given block is a crop that can be broken and, optionally, drop xp.
   *
   * @param block block.
   * @return whether the given block is a valid breakable crop.
   */
  public static boolean isCrop(Block block) {
    return !(block instanceof TorchflowerCropBlock) && (block instanceof CropBlock || block instanceof NetherWartBlock || block instanceof CocoaBlock || block instanceof PitcherCropBlock || ModConfig.getCrops().contains(BlockUtils.getStringKey(block)));
  }

  /**
   * Checks whether the given block is not blacklisted from being harvested.
   *
   * @param block block.
   * @return whether the given block is allowed to be harvested.
   */
  public static boolean isAllowed(BlockState block) {
    return ModConfig.getBlacklist().stream().noneMatch(id -> id.equalsIgnoreCase(BlockUtils.getStringKey(block.getBlock()))) && !block.is(BLACKLIST);
  }

  /**
   * Checks whether the given player has enough hunger to harvest.
   *
   * @param player player.
   * @return whether the given player has enough hunger to harvest.
   */
  public static boolean hasEnoughHunger(Player player) {
    return player.level().getDifficulty() == Difficulty.PEACEFUL ||
      ModConfig.getExhaustionMultiplier().compareTo(BigDecimal.ZERO) == 0 ||
      (player.getFoodData() instanceof FoodData foodData && ((foodData.getFoodLevel() + foodData.getSaturationLevel()) - Math.floor(foodData.exhaustionLevel / 4)) >= 0);
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
    return (IntegerProperty) blockState.getProperties().stream().filter(property -> "age".equals(property.getName())).findFirst().orElseThrow();
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
   * @param level {@link BlockGetter world} in which the crop is placed.
   * @param blockState {@link BlockState} of the crop.
   * @param blockPos {@link BlockPos} of the crop.
   * @return whether the given crop is a multi-block crop.
   */
  public static boolean isTallCrop(BlockGetter level, BlockState blockState, BlockPos blockPos) {
    return blockState.is(BlockTags.CROPS) && level.getBlockState(blockPos.below()).is(blockState.getBlock()) || level.getBlockState(blockPos.above()).is(blockState.getBlock());
  }

  /**
   * Checks whether the given {@link TieredItem tool} has a high enough tier for multi-harvest.
   *
   * @param tool tool.
   * @return whether the given {@link TieredItem tool} is allowed to multi-harvest.
   */
  public static boolean isTierForMultiHarvest(TieredItem tool) {
    return ModConfig.getTiers().stream().anyMatch(tier -> isSameTier(ResourceLocation.parse(tier), tool.getTier().getIncorrectBlocksForDrops().location()));
  }

  /**
   * Returns the tier level, based on the configuration tier list value.
   *
   * @param tool tiered tool.
   * @return tier level.
   */
  public static int getTierLevel(TieredItem tool) {
    return getTierLevel(tool.getTier().getIncorrectBlocksForDrops().location());
  }

  /**
   * Returns the tier level, based on the configuration tier list value.<br />
   * Always use the other overload {@link #getTierLevel(TieredItem)}!
   *
   * @param tier tier reference.
   * @return tier level.
   */
  @ApiStatus.Internal
  public static int getTierLevel(ResourceLocation tier) {
    List<? extends String> tiers = ModConfig.getTiers();
    for (int i = 0; i < tiers.size(); i++) {
      if (isSameTier(ResourceLocation.parse(tiers.get(i)), tier)) {
        return i;
      }
    }
    return 0;
  }

  /**
   * Checks whether the first tier reference is the same as the second tier reference.
   *
   * @param tier1 first tier reference.
   * @param tier2 second tier reference.
   * @return whether the two tier references are the same.
   */
  private static boolean isSameTier(ResourceLocation tier1, ResourceLocation tier2) {
    return tier1.getNamespace().equalsIgnoreCase(tier2.getNamespace()) && (
      tier1.getPath().equalsIgnoreCase(tier2.getPath()) ||
      ("incorrect_for_" + tier1.getPath() + "_tool").equalsIgnoreCase(tier2.getPath()) ||
      ("incorrect_for_" + tier1.getPath() + "en_tool").equalsIgnoreCase(tier2.getPath())
    );
  }
}
