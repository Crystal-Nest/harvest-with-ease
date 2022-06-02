package crystalspider.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Map.Entry;
import java.util.function.Function;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.loader.api.FabricLoader;

/**
 * Handles all that regards mods configurations.
 */
public class FabricConfig {
  /**
   * Logger.
   */
  public static final Logger LOGGER = LoggerFactory.getLogger("FabricConfig");

  /**
   * Uses the given builder to register a configuration.
   * 
   * @param builder - {@link Builder builder} to use.
   */
  public static void register(Builder builder) {
    LOGGER.debug("Building config file for context [" + builder.context + "]");
    File file = FabricLoader.getInstance().getConfigDir().resolve(builder.context + ".config").toFile();
    if (!file.exists()) {
      LOGGER.debug("File [" + file.getName() + ".config] was not found, proceeding to generate it");
      generate(file);
    }
    save(file, builder.build(parse(file)));
  }

  /**
   * Creates the given file.
   * 
   * @param file - {@link File file} to create.
   */
  private static void generate(File file) {
    file.getParentFile().mkdirs();
    try {
      Files.createFile(file.toPath());
    } catch (IOException e) {
      LOGGER.error("Config file [" + file.getName() + "] could not be generated, see stack trace:", e);
    }
  }

  /**
   * Parses the given configuration file and returns a {@link HashMap} with {@link ConfigProperty Configuration Properties} for values
   * and {@link ConfigProperty#id their IDs} for keys.
   * Returns null if the file could not be parsed.
   * 
   * @param file - {@link File file} to parse.
   * @return {@link HashMap} resulting from parsing, or null.
   */
  @Nullable
  private static HashMap<String, ConfigProperty<String>> parse(File file) {
    LOGGER.debug("Parsing config file [" + file.getName() + "]");
    HashMap<String, ConfigProperty<String>> config = new HashMap<String, ConfigProperty<String>>();
    try {
      Scanner scanner = new Scanner(file);
      String nextLine = null;
      while (scanner.hasNextLine()) {
        String id = null;
        String value = null;
        ArrayList<String> comments = new ArrayList<String>();
        nextLine = scanner.nextLine().trim();
        if (!nextLine.isEmpty()) {
          while (nextLine.startsWith("#") && scanner.hasNextLine()) {
            comments.add(nextLine.substring(1).trim());
            nextLine = scanner.nextLine();
          }
          if (!nextLine.startsWith("#")) {
            String[] idWithValue = nextLine.split("=", 2);
            if (idWithValue.length == 2) {
              id = parseString(idWithValue[0].trim());
              value = parseString(idWithValue[1].trim());
            }
          }
          if (id != null && !id.isEmpty() && value != null && !value.isEmpty()) {
            config.put(id, new ConfigProperty<String>(id, value, comments.toArray(new String[1])));
          }
        }
      }
      scanner.close();
      return config;
    } catch (FileNotFoundException e) {
      LOGGER.warn("Config file [" + file.getName() + "] was not found. This is not blocking, but indicates something went wrong while trying to access in read mode the file, see stack trace:", e);
      return null;
    }
  }

  /**
   * Saves the given configuration in the given file.
   * 
   * @param file - {@link File file} to write on.
   * @param config - {@link HashMap} of config values to write.
   */
  private static void save(File file, HashMap<String, ConfigProperty<?>> config) {
    LOGGER.debug("Saving config file [" + file.getName() + "]");
    PrintWriter writer = null;
    try {
      writer = new PrintWriter(file, "UTF-8");
    } catch (FileNotFoundException e) {
      LOGGER.warn("Config file [" + file.getName() + "] was not found. This is not blocking, but indicates something went wrong while trying to access in write mode the file, see stack trace:", e);
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      LOGGER.error("UTF-8 Encoding is unsupported, see stack trace:", e);
      e.printStackTrace();
    }
    if (writer != null) {
      writer.write(stringify(config));
      writer.close();
    }
  }

  /**
   * Parses the given string.
   * 
   * @param string - {@link String string} to parse.
   * @return parsed string.
   */
  private static String parseString(String string) {
    String trimmed = string.trim();
    if (trimmed.startsWith("\"") && trimmed.endsWith("\"")) {
      return trimmed.substring(1, trimmed.length() - 1).trim();
    }
    return trimmed;
  }

  /**
   * Transforms and returns the stringified version of the given configuration.
   * 
   * @param config - {@link HashMap} of config values to stringify.
   * @return stringified version of the given configuration.
   */
  private static String stringify(HashMap<String, ConfigProperty<?>> config) {
    String stringifiedData = "";
    for (Entry<String, ConfigProperty<?>> entry : config.entrySet()) {
      stringifiedData += entry.getValue().toString();
    }
    return stringifiedData;
  }

  /**
   * Configuration Builder.
   */
  public static class Builder {
    /**
     * Builder context (usually the ID of the mod using this builder).
     */
    private final String context;
    /**
     * Configuration specification to build.
     */
    private final AbstractConfig spec;
    /**
     * Parsed configuration.
     */
    private HashMap<String, ConfigProperty<String>> parsedConfig;
    /**
     * Configuration value.
     */
    private final HashMap<String, ConfigProperty<?>> config = new HashMap<String, ConfigProperty<?>>();

    /**
     * @param context
     * @param config
     */
    public Builder(String context, AbstractConfig config) {
      this.spec = config;
      this.context = context;
    }

    /**
     * @param context
     * @param subcontext
     * @param config
     */
    public Builder(String context, String subcontext, AbstractConfig config) {
      this(context + "-" + subcontext, config);
    }

    /**
     * Builds and returns the configuration.
     * Uses the value read from disk ({@link #parsedConfig}) to build a new configuration, registering all the properties with {@link AbstractConfig#register}.
     * 
     * @param parsedConfig - configuration read from disk.
     * @return new built configuration.
     */
    @Nonnull
    public HashMap<String, ConfigProperty<?>> build(@Nullable HashMap<String, ConfigProperty<String>> parsedConfig) {
      if (parsedConfig != null) {
        this.parsedConfig = parsedConfig;
        LOGGER.debug("Registering [" + context + "] configuration.");
        this.spec.register(this);
      }
      return config;
    }

    /**
     * Registers a {@link Boolean} property into the configuration being built.
     * 
     * @param id - configuration property {@link ConfigProperty#id id}.
     * @param defaultValue - configuration property default value.
     * @return a new {@link ConfigProperty}.
     */
    public ConfigProperty<Boolean> registerProperty(String id, Boolean defaultValue) {
      LOGGER.debug("Registering [" + context + "] configuration property with id [" + id + "].");
      ConfigProperty<Boolean> property = new ConfigProperty<Boolean>(id, getValueOrDefault(id, defaultValue, Boolean::parseBoolean));
      config.put(id, property);
      return property;
    }

    /**
     * Registers a {@link Boolean} property into the configuration being built.
     * 
     * @param id - configuration property {@link ConfigProperty#id id}.
     * @param defaultValue - configuration property default value.
     * @param comments - configuration property comments.
     * @return a new {@link ConfigProperty}.
     */
    public ConfigProperty<Boolean> registerProperty(String id, Boolean defaultValue, String ...comments) {
      LOGGER.debug("Registering [" + context + "] configuration property with id [" + id + "].");
      ConfigProperty<Boolean> property = new ConfigProperty<Boolean>(id, getValueOrDefault(id, defaultValue, Boolean::parseBoolean), comments);
      config.put(id, property);
      return property;
    }

    /**
     * Registers a {@link Integer} property into the configuration being built.
     * 
     * @param id - configuration property {@link ConfigProperty#id id}.
     * @param defaultValue - configuration property default value.
     * @return a new {@link ConfigProperty}.
     */
    public ConfigProperty<Integer> registerProperty(String id, Integer defaultValue) {
      LOGGER.debug("Registering [" + context + "] configuration property with id [" + id + "].");
      ConfigProperty<Integer> property = new ConfigProperty<Integer>(id, getValueOrDefault(id, defaultValue, Integer::parseInt));
      config.put(id, property);
      return property;
    }

    /**
     * Registers a {@link Integer} property into the configuration being built.
     * 
     * @param id - configuration property {@link ConfigProperty#id id}.
     * @param defaultValue - configuration property default value.
     * @param comments - configuration property comments.
     * @return a new {@link ConfigProperty}.
     */
    public ConfigProperty<Integer> registerProperty(String id, Integer defaultValue, String ...comments) {
      LOGGER.debug("Registering [" + context + "] configuration property with id [" + id + "].");
      ConfigProperty<Integer> property = new ConfigProperty<Integer>(id, getValueOrDefault(id, defaultValue, Integer::parseInt), comments);
      config.put(id, property);
      return property;
    }

    /**
     * Registers a {@link Double} property into the configuration being built.
     * 
     * @param id - configuration property {@link ConfigProperty#id id}.
     * @param defaultValue - configuration property default value.
     * @return a new {@link ConfigProperty}.
     */
    public ConfigProperty<Double> registerProperty(String id, Double defaultValue) {
      LOGGER.debug("Registering [" + context + "] configuration property with id [" + id + "].");
      ConfigProperty<Double> property = new ConfigProperty<Double>(id, getValueOrDefault(id, defaultValue, Double::parseDouble));
      config.put(id, property);
      return property;
    }

    /**
     * Registers a {@link Double} property into the configuration being built.
     * 
     * @param id - configuration property {@link ConfigProperty#id id}.
     * @param defaultValue - configuration property default value.
     * @param comments - configuration property comments.
     * @return a new {@link ConfigProperty}.
     */
    public ConfigProperty<Double> registerProperty(String id, Double defaultValue, String ...comments) {
      LOGGER.debug("Registering [" + context + "] configuration property with id [" + id + "].");
      ConfigProperty<Double> property = new ConfigProperty<Double>(id, getValueOrDefault(id, defaultValue, Double::parseDouble), comments);
      config.put(id, property);
      return property;
    }

    /**
     * Registers a {@link Integer} property ranging from {@code min} to {@code max} into the configuration being built.
     * 
     * @param id - configuration property {@link ConfigProperty#id id}.
     * @param defaultValue - configuration property default value.
     * @param min - minimum value for this configuration property.
     * @param max - maximum value for this configuration property.
     * @return a new {@link ConfigProperty}.
     */
    public ConfigProperty<Integer> registerProperty(String id, Integer defaultValue, Integer min, Integer max) {
      LOGGER.debug("Registering [" + context + "] configuration property with id [" + id + "].");
      ConfigProperty<Integer> property = new ConfigProperty<Integer>(id, getValueOrDefault(id, defaultValue, parseRangedInt(min, max)), "Range: " + min + " ~ " + max);
      config.put(id, property);
      return property;
    }

    /**
     * Registers a {@link Integer} property ranging from {@code min} to {@code max} into the configuration being built.
     * 
     * @param id - configuration property {@link ConfigProperty#id id}.
     * @param defaultValue - configuration property default value.
     * @param min - minimum value for this configuration property.
     * @param max - maximum value for this configuration property.
     * @param comments - configuration property comments.
     * @return a new {@link ConfigProperty}.
     */
    public ConfigProperty<Integer> registerProperty(String id, Integer defaultValue, Integer min, Integer max, String ...comments) {
      LOGGER.debug("Registering [" + context + "] configuration property with id [" + id + "].");
      String[] rangedComments = Arrays.copyOf(comments, comments.length + 1);
      rangedComments[comments.length] = "Range: " + min + " ~ " + max;
      ConfigProperty<Integer> property = new ConfigProperty<Integer>(id, getValueOrDefault(id, defaultValue, parseRangedInt(min, max)), rangedComments);
      config.put(id, property);
      return property;
    }

    /**
     * Registers a {@link Double} property ranging from {@code min} to {@code max} into the configuration being built.
     * 
     * @param id - configuration property {@link ConfigProperty#id id}.
     * @param defaultValue - configuration property default value.
     * @param min - minimum value for this configuration property.
     * @param max - maximum value for this configuration property.
     * @return a new {@link ConfigProperty}.
     */
    public ConfigProperty<Double> registerProperty(String id, Double defaultValue, Double min, Double max) {
      LOGGER.debug("Registering [" + context + "] configuration property with id [" + id + "].");
      ConfigProperty<Double> property = new ConfigProperty<Double>(id, getValueOrDefault(id, defaultValue, parseRangedDouble(min, max)), "Range: " + min + " ~ " + max);
      config.put(id, property);
      return property;
    }

    /**
     * Registers a {@link Double} property ranging from {@code min} to {@code max} into the configuration being built.
     * 
     * @param id - configuration property {@link ConfigProperty#id id}.
     * @param defaultValue - configuration property default value.
     * @param min - minimum value for this configuration property.
     * @param max - maximum value for this configuration property.
     * @param comments - configuration property comments.
     * @return a new {@link ConfigProperty}.
     */
    public ConfigProperty<Double> registerProperty(String id, Double defaultValue, Double min, Double max, String ...comments) {
      LOGGER.debug("Registering [" + context + "] configuration property with id [" + id + "].");
      String[] rangedComments = Arrays.copyOf(comments, comments.length + 1);
      rangedComments[comments.length] = "Range: " + min + " ~ " + max;
      ConfigProperty<Double> property = new ConfigProperty<Double>(id, getValueOrDefault(id, defaultValue, parseRangedDouble(min, max)), rangedComments);
      config.put(id, property);
      return property;
    }

    /**
     * Registers a {@link String} {@link ArrayList} property into the configuration being built.
     * 
     * @param id - configuration property {@link ConfigProperty#id id}.
     * @param defaultValue - configuration property default value.
     * @return a new {@link ConfigProperty}.
     */
    public ConfigProperty<ArrayList<String>> registerProperty(String id, ArrayList<String> defaultValue) {
      LOGGER.debug("Registering [" + context + "] configuration property with id [" + id + "].");
      ConfigProperty<ArrayList<String>> property = new ConfigProperty<ArrayList<String>>(id, getValueOrDefault(id, defaultValue, this::parseArray));
      config.put(id, property);
      return property;
    }

    /**
     * Registers a {@link String} {@link ArrayList} property into the configuration being built.
     * 
     * @param id - configuration property {@link ConfigProperty#id id}.
     * @param defaultValue - configuration property default value.
     * @param comments - configuration property comments.
     * @return a new {@link ConfigProperty}.
     */
    public ConfigProperty<ArrayList<String>> registerProperty(String id, ArrayList<String> defaultValue, String ...comments) {
      LOGGER.debug("Registering [" + context + "] configuration property with id [" + id + "].");
      ConfigProperty<ArrayList<String>> property = new ConfigProperty<ArrayList<String>>(id, getValueOrDefault(id, defaultValue, this::parseArray), comments);
      config.put(id, property);
      return property;
    }

    /**
     * Returns the parsed value of the configuration property with the given id via the given parser function.
     * If no value could be parsed, returns the given default value.
     * 
     * @param <T> T - type of the configuration property.
     * @param id - configuration property {@link ConfigProperty#id id}.
     * @param defaultValue - default value to return if no value could be parsed.
     * @param parser - parser to use to parse the {@link String} read value.
     * @return parsed value or default value.
     */
    private <T> T getValueOrDefault(String id, T defaultValue, Function<String, T> parser) {
      if (parsedConfig.containsKey(id)) {
        String value = parsedConfig.get(id).getValue();
        LOGGER.debug("Configuration property with key [" + id + "] was found with value [" + value + "], proceeding to parse");
        try {
          return parser.apply(value);
        } catch (NumberFormatException e) {
          LOGGER.warn("Number parsing failed for configuration property with id [" + id + "], see stack trace:", e);
        }
      }
      LOGGER.debug("Configuration property with key [" + id + "] was not found, setting default value [" + defaultValue + "]");
      return defaultValue;
    }

    /**
     * Returns a {@link Function} that can parse a given {@link String} into an {@link Integer} ranging from {@code min} to {@code max}.
     * 
     * @param min - lower bound of the range, inclusive.
     * @param max - upper bound of the range, inclusive.
     * @return ranged {@link Integer} parsed {@link Function}.
     */
    private Function<String, Integer> parseRangedInt(Integer min, Integer max) {
      return (String string) -> this.parseRangedInt(string, min, max);
    }

    /**
     * Returns a {@link Function} that can parse a given {@link String} into an {@link Double} ranging from {@code min} to {@code max}.
     * 
     * @param min - lower bound of the range, inclusive.
     * @param max - upper bound of the range, inclusive.
     * @return ranged {@link Double} parsed {@link Function}.
     */
    private Function<String, Double> parseRangedDouble(Double min, Double max) {
      return (String string) -> this.parseRangedDouble(string, min, max);
    }

    /**
     * Parses the given {@link String} into an {@link Integer} bound by {@code min} and {@code max}.
     * 
     * @param string - string to be parsed.
     * @param min - lower bound of the range, inclusive.
     * @param max - upper bound of the range, inclusive.
     * @return {@link Integer} parsed value.
     * @throws NumberFormatException if {@link Integer#parseInt(String)} throws or if the value is not in range.
     */
    private Integer parseRangedInt(String string, Integer min, Integer max) throws NumberFormatException {
      Integer value = Integer.parseInt(string);
      if (value < min) {
        throw new NumberFormatException(string + "was less than minimum value " + min + ".");
      }
      if (value > max) {
        throw new NumberFormatException(string + "was greater than maximum value " + max + ".");
      }
      return value;
    }

    /**
     * Parses the given {@link String} into an {@link Double} bound by {@code min} and {@code max}.
     * 
     * @param string - string to be parsed.
     * @param min - lower bound of the range, inclusive.
     * @param max - upper bound of the range, inclusive.
     * @return {@link Double} parsed value.
     * @throws NumberFormatException if {@link Double#parseDouble(String)} throws or if the value is not in range.
     */
    private Double parseRangedDouble(String string, Double min, Double max) throws NumberFormatException {
      Double value = Double.parseDouble(string);
      if (value < min) {
        throw new NumberFormatException(string + "was less than minimum value " + min + ".");
      }
      if (value > max) {
        throw new NumberFormatException(string + "was greater than maximum value " + max + ".");
      }
      return value;
    }

    /**
     * Parses a {@link String} into a {@link String} {@link ArrayList}.
     * 
     * @param stringyValues - {@link String} containing the value to be parsed.
     * @return {@link String} {@link ArrayList} resulting from parsing the given {@link String}.
     */
    private ArrayList<String> parseArray(String stringyValues) {
      ArrayList<String> array = new ArrayList<String>();
      String[] values = stringyValues.substring(1, stringyValues.length() - 1).split(",");
      for (String value : values) {
        array.add(parseString(value));
      }
      return array;
    }
  }
}
