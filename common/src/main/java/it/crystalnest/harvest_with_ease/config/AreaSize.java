package it.crystalnest.harvest_with_ease.config;

/**
 * Harvest area size.
 */
public enum AreaSize {
  /**
   * 1x1 area.
   */
  SINGLE(1),
  /**
   * 3x3 area.
   */
  MEDIUM(3),
  /**
   * 5x5 area.
   */
  LARGE(5);

  /**
   * Numeric size (square side length).
   */
  public final int size;

  /**
   * @param size {@link #size}.
   */
  AreaSize(int size) {
    this.size = size;
  }

  @Override
  public String toString() {
    return name().toLowerCase();
  }
}
