package crystalspider.harvestwithease.api.event;

import java.util.ArrayList;
import java.util.List;

import crystalspider.harvestwithease.ModLoader;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

/**
 * Collection of all events from Harvest With Ease mod.
 */
public final class HarvestWithEaseEvents {
  /**
   * Priority phase for listeners that will be called first.
   */
  public static final Identifier PRIORITY_PHASE = new Identifier(ModLoader.MOD_ID, "priority");
  /**
   * Deferred phase for listeners that will be called last.
   */
  public static final Identifier DEFERRED_PHASE = new Identifier(ModLoader.MOD_ID, "deferred");

  public static final Event<HarvestCheck> HARVEST_CHECK = EventFactory.createWithPhases(
    HarvestCheck.class,
    (listeners) -> (world, crop, pos, player, hand, first, event) -> {
      for (HarvestCheck listener : listeners) {
        event.setCanHarvest(listener.check(world, crop, pos, player, hand, first, event));
        if (event.isCanceled()) {
          return event.canHarvest();
        }
      }
      return event.canHarvest();
    },
    PRIORITY_PHASE,
    Event.DEFAULT_PHASE,
    DEFERRED_PHASE
  );

  public static final Event<BeforeHarvest> BEFORE_HARVEST = EventFactory.createWithPhases(
    BeforeHarvest.class,
    (listeners) -> (world, crop, pos, face, result, player, hand, first) -> {
      for (BeforeHarvest listener : listeners) {
        listener.beforeHarvest(world, crop, pos, face, result, player, hand, first);
      }
    },
    PRIORITY_PHASE,
    Event.DEFAULT_PHASE,
    DEFERRED_PHASE
  );

  public static final Event<HarvestDrops> HARVEST_DROPS = EventFactory.createWithPhases(
    HarvestDrops.class,
    (listeners) -> (world, crop, pos, face, result, player, hand, first, event) -> {
      for (HarvestDrops listener : listeners) {
        event.setDrops(listener.getDrops(world, crop, pos, face, result, player, hand, first, event));
        if (event.isCanceled()) {
          return event.getDrops();
        }
      }
      return event.getDrops();
    },
    PRIORITY_PHASE,
    Event.DEFAULT_PHASE,
    DEFERRED_PHASE
  );

  public static final Event<AfterHarvest> AFTER_HARVEST = EventFactory.createWithPhases(
    AfterHarvest.class,
    (listeners) -> (world, crop, pos, face, result, player, hand, first) -> {
      for (AfterHarvest listener : listeners) {
        listener.afterHarvest(world, crop, pos, face, result, player, hand, first);
      }
    },
    PRIORITY_PHASE,
    Event.DEFAULT_PHASE,
    DEFERRED_PHASE
  );

  private HarvestWithEaseEvents() {}

  @FunctionalInterface
  public interface HarvestCheck {
    /**
     * Called when checking whether the player can right-click harvest.
     * 
     * @param world {@link World} of the interaction.
     * @param crop {@link BlockState} of the crop being harvested.
     * @param pos {@link BlockPos} of the crop being harvested.
     * @param player {@link PlayerEntity} trying to harvest the crop. 
     * @param hand {@link Hand} used to harvest.
     * @param first whether the current crop is the actual right-clicked crop (the one also at the center of the harvest area).
     * @param event {@link HarvestCheckEvent}.
     * @return whether the player can right-click harvest the crop.
     */
    boolean check(World world, BlockState crop, BlockPos pos, PlayerEntity player, Hand hand, boolean first, HarvestCheckEvent event);
  }

  @FunctionalInterface
  public interface BeforeHarvest {
    /**
     * Event fired before right-click harvesting.
     * 
     * @param world {@link World} of the interaction.
     * @param crop {@link BlockState} of the crop being harvested.
     * @param pos {@link BlockPos} of the crop being harvested.
     * @param face {@link Direction face} of the crop block clicked.
     * @param result {@link BlockHitResult}.
     * @param player {@link ServerPlayerEntity} trying to harvest the crop.
     * @param first whether the current crop is the actual right-clicked crop (the one also at the center of the harvest area).
     * @param hand {@link Hand} used to harvest.
     */
    void beforeHarvest(ServerWorld world, BlockState crop, BlockPos pos, Direction face, BlockHitResult result, ServerPlayerEntity player, Hand hand, boolean first);
  }

  @FunctionalInterface
  public interface HarvestDrops {
    /**
     * Event fired when calculating the drops resulting from right-click harvesting.
     * 
     * @param world {@link World} of the interaction.
     * @param crop {@link BlockState} of the crop being harvested.
     * @param pos {@link BlockPos} of the crop being harvested.
     * @param face {@link Direction face} of the crop block clicked.
     * @param result {@link BlockHitResult}.
     * @param player {@link ServerPlayerEntity} trying to harvest the crop. 
     * @param hand {@link Hand} used to harvest.
     * @param first whether the current crop is the actual right-clicked crop (the one also at the center of the harvest area).
     * @param event {@link HarvestDropsEvent}.
     * @return this list of drops to drop.
     */
    List<ItemStack> getDrops(ServerWorld world, BlockState crop, BlockPos pos, Direction face, BlockHitResult result, ServerPlayerEntity player, Hand hand, boolean first, HarvestDropsEvent event);
  }

  @FunctionalInterface
  public interface AfterHarvest {
    /**
     * Event fired after right-click harvesting.
     * 
     * @param world {@link World} of the interaction.
     * @param crop {@link BlockState} of the crop being harvested.
     * @param pos {@link BlockPos} of the crop being harvested.
     * @param face {@link Direction face} of the crop block clicked.
     * @param result {@link BlockHitResult}.
     * @param player {@link ServerPlayerEntity} trying to harvest the crop. 
     * @param hand {@link Hand} used to harvest.
     * @param first whether the current crop is the actual right-clicked crop (the one also at the center of the harvest area).
     */
    void afterHarvest(ServerWorld world, BlockState crop, BlockPos pos, Direction face, BlockHitResult result, ServerPlayerEntity player, Hand hand, boolean first);
  }

  /**
   * Base class for Harvest With Ease mod events.
   */
  private static abstract class HarvestWithEaseEvent {
    /**
     * Whether this event is canceled and further processing should be prevented.
     */
    private boolean isCanceled = false;

    /**
     * Returns this {@link #isCanceled}.
     * 
     * @return this {@link #isCanceled}.
     */
    public boolean isCanceled() {
      return isCanceled;
    }

    /**
     * Sets this {@link #isCanceled} flag.
     * Trying to set this flag may result in an {@link UnsupportedOperationException} if this event is not cancelable.
     * 
     * @param cancel
     */
    public void setCanceled(boolean cancel) {
      if (!isCancelable()) {
        throw new UnsupportedOperationException("Attempted to call setCanceled() on a non-cancelable event of type: " + this.getClass().getCanonicalName());
      }
      isCanceled = cancel;
    }

    /**
     * Returns whether this event can be canceled.
     * 
     * @return whether this event can be canceled.
     */
    public abstract boolean isCancelable();
  }

  /**
   * Event fired when checking whether the player can right-click harvest.
   */
  public static class HarvestCheckEvent extends HarvestWithEaseEvent {
    /**
     * Whether the player can right-click harvest the crop.
     */
    public boolean canHarvest = true;
        
    /**
     * Returns this {@link #canHarvest}.
     * 
     * @return this {@link #canHarvest}.
     */
    public boolean canHarvest() {
      return this.canHarvest;
    }

    @Override
    public boolean isCancelable() {
      return true;
    }

    /**
     * Sets this {@link #canHarvest}.
     * 
     * @param canHarvest
     */
    private void setCanHarvest(boolean canHarvest) {
      this.canHarvest = canHarvest;
    }
  }

  /**
   * Event fired when calculating the drops resulting from right-click harvesting.
   */
  public static class HarvestDropsEvent extends HarvestWithEaseEvent {
    /**
     * List of drops.
     */
    private List<ItemStack> drops;

    /**
     * Reference to the default drops.
     */
    private final List<ItemStack> defaultDrops;

    /**
     * @param world {@link World} of the interaction.
     * @param crop {@link BlockState} of the crop being harvested.
     * @param pos {@link BlockPos} of the crop being harvested.
     * @param player {@link ServerPlayerEntity} trying to harvest the crop. 
     * @param hand {@link Hand} used to harvest.
     */
    public HarvestDropsEvent(ServerWorld world, BlockState crop, BlockPos pos, ServerPlayerEntity player, Hand hand) {
      defaultDrops = initDrops(world, crop, pos, player, hand);
      drops = new ArrayList<>(defaultDrops.stream().map(item -> item.copy()).toList());
    }

    /**
     * Returns whether the list of drops changed from its default value.
     * 
     * @return whether the list of drops changed from its default value.
     */
    public boolean haveDropsChanged() {
      if (defaultDrops.size() == drops.size()) {
        for (int c = 0; c < defaultDrops.size(); c++) {
          if (!ItemStack.areEqual(defaultDrops.get(c), drops.get(c))) {
            return true;
          }
        }
        return false;
      }
      return true;
    }

    /**
     * Returns this {@link #drops}.
     * 
     * @return this {@link #drops}.
     */
    public List<ItemStack> getDrops() {
      return drops;
    }

    @Override
    public boolean isCancelable() {
      return true;
    }
  
    /**
     * Initializes the drops list.
     * 
     * @return the list of drops a player would get by breaking the crop, with one seed removed.
     */
    private List<ItemStack> initDrops(ServerWorld world, BlockState crop, BlockPos pos, ServerPlayerEntity player, Hand hand) {
      List<ItemStack> drops = Block.getDroppedStacks(crop, world, pos, crop.hasBlockEntity() ? world.getBlockEntity(pos) : null, player, player.getStackInHand(hand));
      boolean seedRemoved = false;
      for (ItemStack stack : drops) {
        if (!seedRemoved && stack.isOf(crop.getBlock().getPickStack(world, pos, crop).getItem())) {
          stack.decrement(1);
          seedRemoved = true;
        }
      }
      return drops;
    }

    /**
     * Sets this {@link #drops}.
     * Sets only if the given list is not {@code null}.
     * 
     * @param drops
     */
    private void setDrops(List<ItemStack> drops) {
      if (drops != null) {
        this.drops = drops;
      }
    }
  }
}
