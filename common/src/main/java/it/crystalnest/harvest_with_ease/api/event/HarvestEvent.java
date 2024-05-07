package it.crystalnest.harvest_with_ease.api.event;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Generic harvest event.
 *
 * @param <P> Player.
 * @param <L> Level.
 */
public interface HarvestEvent<P extends Player, L extends Level> {
  /**
   * Returns the player harvesting.
   *
   * @return the player.
   */
  P getEntity();

  /**
   * Returns the level in which the interaction takes place.
   *
   * @return the level.
   */
  L getLevel();

  /**
   * Returns the targeted crop.
   *
   * @return the targeted crop.
   */
  BlockState getCrop();

  /**
   * Returns the position of the targeted crop.
   *
   * @return the position.
   */
  BlockPos getPos();

  /**
   * Returns the right-clicked face of the crop.
   *
   * @return the face.
   */
  Direction getFace();

  /**
   * Returns the interaction {@link BlockHitResult}.<br>
   * Can be {@code null} if the current crop is being harvested via multi-harvest.
   *
   * @return the {@link BlockHitResult}.
   */
  @Nullable
  BlockHitResult getHitResult();

  /**
   * Returns the player's hand used to harvest.
   *
   * @return the player's hand.
   */
  InteractionHand getHand();

  /**
   * Whether the current crop is the actual right-clicked crop (the one also at the center of the harvest area).
   *
   * @return whether the crop is the actual right-clicked crop.
   */
  default boolean isFirst() {
    return getHitResult() != null;
  }

  /**
   * Event triggered when checking whether a crop can be harvested.<br>
   * Fired on both sides.
   */
  interface HarvestCheckEvent extends HarvestEvent<Player, Level> {
    /**
     * Returns whether the crop can be harvested.
     *
     * @return whether the crop can be harvested.
     */
    boolean canHarvest();

    /**
     * Sets whether the crop can be harvested.
     *
     * @param canHarvest whether the crop can be harvested.
     */
    void setCanHarvest(boolean canHarvest);
  }

  /**
   * Event triggered before harvesting.<br>
   * Fired on server side only.
   */
  interface BeforeHarvestEvent extends HarvestEvent<ServerPlayer, ServerLevel> {}

  /**
   * Event triggered when calculating the drops for a harvest.<br>
   * Fired on server side only.
   */
  interface HarvestDropsEvent extends HarvestEvent<ServerPlayer, ServerLevel> {
    /**
     * Returns the list of default drops.
     *
     * @return the default drops.
     */
    List<ItemStack> getDefaultDrops();

    /**
     * Returns the list of drops.
     *
     * @return the drops.
     */
    List<ItemStack> getDrops();

    /**
     * Sets the current list of drops.<br>
     *
     * @param drops list of drops.
     */
    @ApiStatus.Internal
    void setDrops(List<ItemStack> drops);

    /**
     * Cancel this event to prevent further computations.
     */
    void cancel();

    /**
     * Returns whether the list of drops changed from its default value.
     *
     * @return whether the list of drops changed from its default value.
     */
    default boolean didDropsChange() {
      List<ItemStack> defaultDrops = getDefaultDrops(), drops = getDrops();
      if (defaultDrops.size() == drops.size()) {
        for (int c = 0; c < defaultDrops.size(); c++) {
          if (!ItemStack.matches(defaultDrops.get(c), drops.get(c))) {
            return true;
          }
        }
        return false;
      }
      return true;
    }

    /**
     * Initializes the drops list.
     *
     * @param level level.
     * @param crop crop.
     * @param pos position.
     * @param hand player's hand.
     * @return the list of drops a player would get by breaking the crop, with one seed removed.
     */
    @ApiStatus.Internal
    default List<ItemStack> initDefaultDrops(ServerLevel level, BlockState crop, BlockPos pos, InteractionHand hand) {
      List<ItemStack> drops = Block.getDrops(crop, level, pos, crop.hasBlockEntity() ? level.getBlockEntity(pos) : null, getEntity(), getEntity().getItemInHand(hand));
      boolean seedRemoved = false;
      for (ItemStack stack : drops) {
        if (!seedRemoved && stack.is(crop.getBlock().getCloneItemStack(level, pos, crop).getItem())) {
          stack.shrink(1);
          seedRemoved = true;
        }
      }
      return drops;
    }
  }

  /**
   * Event triggered after harvesting.<br>
   * Fired on server side only.
   */
  interface AfterHarvestEvent extends HarvestEvent<ServerPlayer, ServerLevel> {}
}
