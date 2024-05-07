package it.crystalnest.harvest_with_ease.api.event;

import it.crystalnest.harvest_with_ease.Constants;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Harvest events.
 */
public final class HarvestEvents {
  /**
   * Priority phase for listeners that will be called first.
   */
  public static final ResourceLocation PRIORITY_PHASE = new ResourceLocation(Constants.MOD_ID, "priority");

  /**
   * Deferred phase for listeners that will be called last.
   */
  public static final ResourceLocation DEFERRED_PHASE = new ResourceLocation(Constants.MOD_ID, "deferred");

  /**
   * Event triggered when checking whether a crop can be harvested.
   */
  public static final Event<HarvestCheck> CHECK = EventFactory.createArrayBacked(
    HarvestCheck.class,
    listeners -> event -> {
      for (HarvestCheck listener : listeners) {
        if (!listener.check(event)) {
          return false;
        }
      }
      return true;
    }
  );

  /**
   * Event triggered before harvesting.
   */
  public static final Event<BeforeHarvest> BEFORE = EventFactory.createWithPhases(
    BeforeHarvest.class,
    listeners -> event -> {
      for (BeforeHarvest listener : listeners) {
        listener.beforeHarvest(event);
      }
    },
    PRIORITY_PHASE,
    Event.DEFAULT_PHASE,
    DEFERRED_PHASE
  );

  /**
   * Event triggered when calculating the drops for a harvest.
   */
  public static final Event<HarvestDrops> DROPS = EventFactory.createWithPhases(
    HarvestDrops.class,
    listeners -> event -> {
      for (HarvestDrops listener : listeners) {
        event.setDrops(listener.getResult(event).getDrops());
        if (event.canceled) {
          return event;
        }
      }
      return event;
    },
    PRIORITY_PHASE,
    Event.DEFAULT_PHASE,
    DEFERRED_PHASE
  );

  /**
   * Event triggered after harvesting.
   */
  public static final Event<AfterHarvest> AFTER = EventFactory.createWithPhases(
    AfterHarvest.class,
    listeners -> event -> {
      for (AfterHarvest listener : listeners) {
        listener.afterHarvest(event);
      }
    },
    PRIORITY_PHASE,
    Event.DEFAULT_PHASE,
    DEFERRED_PHASE
  );

  private HarvestEvents() {}

  /**
   * Handle harvest check event.
   */
  @FunctionalInterface
  public interface HarvestCheck {
    /**
     * Handle harvest check event and return whether the crop can be harvested.
     *
     * @param event {@link HarvestCheckEvent}.
     * @return whether the crop can be harvested.
     */
    boolean check(HarvestCheckEvent event);
  }

  /**
   * Handle before harvest event.
   */
  @FunctionalInterface
  public interface BeforeHarvest {
    /**
     * Handle before harvest event.
     *
     * @param event {@link BeforeHarvestEvent}.
     */
    void beforeHarvest(BeforeHarvestEvent event);
  }

  /**
   * Handle harvest drops event.
   */
  @FunctionalInterface
  public interface HarvestDrops {
    /**
     * Handle harvest drops event and return the list of drops.
     *
     * @param event {@link HarvestDropsEvent}.
     * @return the list of drops to drop.
     */
    HarvestDropsEvent getResult(HarvestDropsEvent event);
  }

  /**
   * Handle after harvest event.
   */
  @FunctionalInterface
  public interface AfterHarvest {
    /**
     * Handle after harvest event.
     *
     * @param event {@link AfterHarvestEvent}.
     */
    void afterHarvest(AfterHarvestEvent event);
  }

  /**
   * Generic Fabric harvest event.
   */
  public static abstract class FabricHarvestEvent<P extends Player, L extends Level> implements HarvestEvent<P, L> {
    /**
     * Level in which the interaction takes place.
     */
    protected final L level;

    /**
     * Targeted crop.
     */
    protected final BlockState crop;

    /**
     * Position of the targeted crop.
     */
    protected final BlockPos pos;

    /**
     * {@link Direction face} of the crop block clicked.
     */
    protected final Direction face;

    /**
     * {@link BlockHitResult}.<br>
     * Can be {@code null} if the current crop is being harvested via multi-harvest.
     */
    protected final @Nullable BlockHitResult hitResult;

    /**
     * Player harvesting.
     */
    protected final P entity;

    /**
     * Player's hand used to harvest.
     */
    protected final InteractionHand hand;

    /**
     * @param level {@link #level}.
     * @param crop {@link #crop}.
     * @param pos {@link #pos}.
     * @param face {@link #face}.
     * @param hitResult {@link #hitResult}.
     * @param entity {@link #entity}.
     * @param hand {@link #hand}.
     */
    protected FabricHarvestEvent(L level, BlockState crop, BlockPos pos, Direction face, @Nullable BlockHitResult hitResult, P entity, InteractionHand hand) {
      this.level = level;
      this.crop = crop;
      this.pos = pos;
      this.face = face;
      this.hitResult = hitResult;
      this.entity = entity;
      this.hand = hand;
    }

    @Override
    public P getEntity() {
      return entity;
    }

    @Override
    public L getLevel() {
      return level;
    }

    @Override
    public BlockState getCrop() {
      return crop;
    }

    @Override
    public BlockPos getPos() {
      return pos;
    }

    @Override
    public Direction getFace() {
      return face;
    }

    @Override
    @Nullable
    public BlockHitResult getHitResult() {
      return hitResult;
    }

    @Override
    public InteractionHand getHand() {
      return hand;
    }
  }

  /**
   * Event triggered when checking whether a crop can be harvested.<br>
   * Fired on both sides.
   */
  public static class HarvestCheckEvent extends FabricHarvestEvent<Player, Level> implements HarvestEvent.HarvestCheckEvent {
    /**
     * Whether the crop can be harvested.
     */
    private boolean canHarvest = true;

    /**
     * @param level {@link #level}.
     * @param crop {@link #crop}.
     * @param pos {@link #pos}.
     * @param face {@link #face}.
     * @param hitResult {@link #hitResult}.
     * @param entity {@link #entity}.
     * @param hand {@link #hand}.
     */
    HarvestCheckEvent(Level level, BlockState crop, BlockPos pos, Direction face, @Nullable BlockHitResult hitResult, Player entity, InteractionHand hand) {
      super(level, crop, pos, face, hitResult, entity, hand);
    }

    @Override
    public boolean canHarvest() {
      return canHarvest;
    }

    @Override
    public void setCanHarvest(boolean canHarvest) {
      this.canHarvest = canHarvest;
    }
  }

  /**
   * Event triggered before harvesting.<br>
   * Fired on server side only.
   */
  public static class BeforeHarvestEvent extends FabricHarvestEvent<ServerPlayer, ServerLevel> implements HarvestEvent.BeforeHarvestEvent {
    /**
     * @param level {@link #level}.
     * @param crop {@link #crop}.
     * @param pos {@link #pos}.
     * @param face {@link #face}.
     * @param hitResult {@link #hitResult}.
     * @param entity {@link #entity}.
     * @param hand {@link #hand}.
     */
    BeforeHarvestEvent(ServerLevel level, BlockState crop, BlockPos pos, Direction face, @Nullable BlockHitResult hitResult, ServerPlayer entity, InteractionHand hand) {
      super(level, crop, pos, face, hitResult, entity, hand);
    }
  }

  /**
   * Event triggered when calculating the drops for a harvest.<br>
   * Fired on server side only.
   */
  public static class HarvestDropsEvent extends FabricHarvestEvent<ServerPlayer, ServerLevel> implements HarvestEvent.HarvestDropsEvent {
    /**
     * Reference to the default drops.
     */
    private final List<ItemStack> defaultDrops;

    /**
     * List of drops.
     */
    private List<ItemStack> drops;

    private boolean canceled;

    /**
     * @param level {@link #level}.
     * @param crop {@link #crop}.
     * @param pos {@link #pos}.
     * @param face {@link #face}.
     * @param hitResult {@link #hitResult}.
     * @param entity {@link #entity}.
     * @param hand {@link #hand}.
     */
    HarvestDropsEvent(ServerLevel level, BlockState crop, BlockPos pos, Direction face, @Nullable BlockHitResult hitResult, ServerPlayer entity, InteractionHand hand) {
      super(level, crop, pos, face, hitResult, entity, hand);
      defaultDrops = initDefaultDrops(level, crop, pos, hand);
      drops = new ArrayList<>(defaultDrops.stream().map(ItemStack::copy).toList());
    }

    @Override
    public List<ItemStack> getDefaultDrops() {
      return defaultDrops;
    }

    @Override
    public List<ItemStack> getDrops() {
      return drops;
    }

    @Override
    @ApiStatus.Internal
    public void setDrops(List<ItemStack> drops) {
      this.drops = drops;
    }

    @Override
    public void cancel() {
      canceled = false;
    }
  }

  /**
   * Event triggered after harvesting.<br>
   * Fired on server side only.
   */
  public static class AfterHarvestEvent extends FabricHarvestEvent<ServerPlayer, ServerLevel> implements HarvestEvent.AfterHarvestEvent {
    /**
     * @param level {@link #level}.
     * @param crop {@link #crop}.
     * @param pos {@link #pos}.
     * @param face {@link #face}.
     * @param hitResult {@link #hitResult}.
     * @param entity {@link #entity}.
     * @param hand {@link #hand}.
     */
    AfterHarvestEvent(ServerLevel level, BlockState crop, BlockPos pos, Direction face, @Nullable BlockHitResult hitResult, ServerPlayer entity, InteractionHand hand) {
      super(level, crop, pos, face, hitResult, entity, hand);
    }
  }
}
