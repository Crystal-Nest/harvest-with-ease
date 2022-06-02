package crystalspider.config;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Configuration Property.
 */
public class ConfigProperty<T> {
  /**
   * Property ID, used to uniquely identify this property.
   * Must be not null.
   */
  private final @Nonnull String id;
  /**
   * Optional formatted comment (can be multiline).
   */
  private final @Nullable String comment;
  /**
   * Property value.
   */
  private final T value;

  /**
   * @param id
   * @param value
   */
  public ConfigProperty(@Nonnull String id, T value) {
    this.id = id;
    this.value = value;
    this.comment = null;
  }

  /**
   * @param id
   * @param value
   * @param comments
   */
  public ConfigProperty(@Nonnull String id, T value, String ...comments) {
    this.id = id;
    this.value = value;
    String comment = "";
    for (int c = 0; c < comments.length; c++) {
      comment += formatComment(comments[c]);
    }
    this.comment = comment;
  }

  /**
   * Returns this property {@link #id}.
   * 
   * @return this property {@link #id}.
   */
  @Nonnull
  public String getId() {
    return this.id;
  }

  /**
   * Returns this property {@link #value}.
   * 
   * @return this property {@link #value}.
   */
  @Nullable
  public T getValue() {
    return value;
  }

  /**
   * Returns this property {@link #comment}.
   * 
   * @return this property {@link #comment}.
   */
  @Nullable
  public String getComment() {
    return comment;
  }

  /**
   * Formats the given comment in a writable way.
   * 
   * @param comment - comment to format (must be single line).
   * @return single line of formatted comment.
   */
  private String formatComment(String comment) {
    return "# " + comment + System.lineSeparator();
  }

  @Override
  public String toString() {
    return comment + id + " = " + value + System.lineSeparator();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    ConfigProperty<?> other = (ConfigProperty<?>) obj;
    if (!id.equals(other.id)) {
      return false;
    }
    if (value == null) {
      if (other.value != null) {
        return false;
      }
    } else if (!value.equals(other.value)) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + id.hashCode();
    result = prime * result + (value == null ? 0 : value.hashCode());
    return result;
  }
}
