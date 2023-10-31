package crystalspider.harvestwithease.api;

import java.util.Collections;
import java.util.NoSuchElementException;

import crystalspider.harvestwithease.config.HarvestWithEaseConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CocoaBlock;
import net.minecraft.block.CropBlock;
import net.minecraft.block.NetherWartBlock;
import net.minecraft.state.property.IntProperty;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

/**
 * Utility class that serves as an API for mods interfacing with Harvest With Ease mod.
 */
public final class HarvestWithEaseAPI {
  private HarvestWithEaseAPI() {}

  /**
   * Checks whether or not the block passed as parameter is a crop that can be harvested using this mod.
   * 
   * @param block
   * @return whether the given block it's a valid crop.
   */
  public static boolean isCrop(Block block) {
    // Hardcoded block id, might consider importing the mod and get the id from its list of registered blocks.
    return !getKey(block).equals("farmersdelight:tomatoes") && isBreakableCrop(block);
  }

  /**
   * Checks whether the given block is a crop that can be broken and, optionally, drop xp.
   * 
   * @param block
   * @return whether the given block is a valid breakable crop.
   */
  public static boolean isBreakableCrop(Block block) {
    return block instanceof CropBlock || block instanceof NetherWartBlock || block instanceof CocoaBlock || HarvestWithEaseConfig.getCrops().contains(getKey(block));
  }

  /**
   * Returns the age integer property from the given blockState.
   * 
   * @param blockState - {@link BlockState state} to take the age property from.
   * @return the age property from the given blockState.
   * @throws NullPointerException - if the age property was null.
   * @throws NoSuchElementException - if no value for the age property is present.
   * @throws ClassCastException - if the age property is not an {@link IntProperty}.
   */
  public static IntProperty getAge(BlockState blockState) throws NullPointerException, NoSuchElementException, ClassCastException {
    return (IntProperty) blockState.getProperties().stream().filter(property -> property.getName().equals("age")).findFirst().orElseThrow();
  }

  /**
   * Checks whether the given blockstate is a mature crop.
   * 
   * @param blockState - {@link BlockState state} to take the age property from.
   * @param age - {@link IntProperty integer property} for the crop age.
   * @return whether the given blockstate is a mature crop.
   */
  public static boolean isMature(BlockState blockState, IntProperty age) {
    return blockState.getOrEmpty(age).orElse(0) >= Collections.max(age.getValues());
  }

  /**
   * Checks whether the given blockstate is a mature crop.
   * 
   * @param blockState - {@link BlockState state} to take the age property from.
   * @return whether the given blockstate is a mature crop.
   * @throws NullPointerException if the age property was null.
   * @throws NoSuchElementException if no value for the age property is present.
   * @throws ClassCastException if the age property is not an {@link IntProperty}.
   */
  public static boolean isMature(BlockState blockState) throws NullPointerException, NoSuchElementException, ClassCastException {
    return HarvestWithEaseAPI.isMature(blockState, HarvestWithEaseAPI.getAge(blockState));
  }


  /**
   * Checks whether the given crop is a multi-block crop (a crop made of multiple vertically connected blocks).
   * 
   * @param level - {@link World} in which the crop is placed.
   * @param blockState - {@link BlockState} of the crop.
   * @param blockPos - {@link BlockPos} of the crop.
   * @return whether the given crop is a multi-block crop.
   */
  public static boolean isTallCrop(World level, BlockState blockState, BlockPos blockPos) {
    return blockState.isIn(BlockTags.CROPS) && level.getBlockState(blockPos.down()).isOf(blockState.getBlock()) || level.getBlockState(blockPos.up()).isOf(blockState.getBlock());
  }

  /**
   * Returns the in-game ID of the block passed as parameter.
   * 
   * @param block
   * @return in-game ID of the given block.
   */
  private static String getKey(Block block) {
    return Registry.BLOCK.getKey(block).get().getValue().toString();
  }
}
