package it.crystalnest.harvest_with_ease.config;

/**
 * Harvest area size increase step.
 */
public enum AreaStep {
  /**
   * No area increase.
   */
  NONE(0),
  /**
   * Increase area side length by 2.
   */
  SMALL(2),
  /**
   * Increase area side length by 4.
   */
  MEDIUM(4),
  /**
   * Increase area side length by 6.
   */
  LARGE(6);

  /**
   * Increase step.
   */
  public final int step;

  /**
   * @param step {@link #step}.
   */
  AreaStep(int step) {
    this.step = step;
  }

  @Override
  public String toString() {
    return name().toLowerCase();
  }
}
