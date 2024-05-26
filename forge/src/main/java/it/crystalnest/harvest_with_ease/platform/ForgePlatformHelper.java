package it.crystalnest.harvest_with_ease.platform;

import it.crystalnest.cobweb.platform.model.Platform;
import it.crystalnest.harvest_with_ease.platform.services.PlatformHelper;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;

/**
 * Forge platform helper.
 */
public class ForgePlatformHelper implements PlatformHelper {
  @Override
  public Platform getPlatformName() {
    return Platform.FORGE;
  }

  @Override
  public boolean isModLoaded(String modId) {
    return ModList.get().isLoaded(modId);
  }

  @Override
  public boolean isDevEnv() {
    return !FMLLoader.isProduction();
  }
}
