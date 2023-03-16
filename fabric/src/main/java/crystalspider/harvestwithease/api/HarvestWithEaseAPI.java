package crystalspider.harvestwithease.api;

import java.util.NoSuchElementException;

import crystalspider.harvestwithease.config.HarvestWithEaseConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CocoaBlock;
import net.minecraft.block.CropBlock;
import net.minecraft.block.NetherWartBlock;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.registry.Registry;

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
   * Returns the in-game ID of the block passed as parameter.
   * 
   * @param block
   * @return in-game ID of the given block.
   */
  private static String getKey(Block block) {
    return Registry.BLOCK.getKey(block).get().getValue().toString();
  }
}
