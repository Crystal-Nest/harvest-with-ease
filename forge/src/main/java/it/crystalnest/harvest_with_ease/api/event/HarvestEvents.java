package it.crystalnest.harvest_with_ease.api.event;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Cancelable;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Harvest events.
 */
public final class HarvestEvents {
  private HarvestEvents() {}

  /**
   * Generic Forge harvest event.
   */
  public static class ForgeHarvestEvent<P extends Player, L extends Level> extends PlayerInteractEvent implements HarvestEvent<P, L> {
    /**
     * Level in which the interaction takes place.
     */
    protected final L level;

    /**
     * Targeted crop.
     */
    protected final BlockState crop;

    /**
     * {@link BlockHitResult}.<br>
     * Can be {@code null} if the current crop is being harvested via multi-harvest.
     */
    protected final @Nullable BlockHitResult hitResult;

    /**
     * @param level {@link #level}.
     * @param crop {@link #crop}.
     * @param pos {@link #pos}.
     * @param face {@link #face}.
     * @param hitResult {@link #hitResult}.
     * @param player {@link #player}.
     * @param hand {@link #hand}.
     */
    protected ForgeHarvestEvent(L level, BlockState crop, BlockPos pos, Direction face, @Nullable BlockHitResult hitResult, P player, InteractionHand hand) {
      super(player, hand, pos, face);
      this.level = level;
      this.crop = crop;
      this.hitResult = hitResult;
    }

    @Override
    @SuppressWarnings("unchecked")
    public P getEntity() {
      return (P) super.getEntity();
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
    public @Nullable BlockHitResult getHitResult() {
      return hitResult;
    }
  }

  /**
   * Event triggered when checking whether a crop can be harvested.<br>
   * Fired on both sides.
   */
  public static class HarvestCheckEvent extends ForgeHarvestEvent<Player, Level> implements HarvestEvent.HarvestCheckEvent {
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
  public static class BeforeHarvestEvent extends ForgeHarvestEvent<ServerPlayer, ServerLevel> implements HarvestEvent.BeforeHarvestEvent {
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
  @Cancelable
  public static class HarvestDropsEvent extends ForgeHarvestEvent<ServerPlayer, ServerLevel> implements HarvestEvent.HarvestDropsEvent {
    /**
     * Reference to the default drops.
     */
    private final List<ItemStack> defaultDrops;

    /**
     * List of drops.
     */
    private List<ItemStack> drops;

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
      setCanceled(true);
    }
  }

  /**
   * Event triggered after harvesting.<br>
   * Fired on server side only.
   */
  public static class AfterHarvestEvent extends ForgeHarvestEvent<ServerPlayer, ServerLevel> implements HarvestEvent.AfterHarvestEvent {
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
