package crystalspider.harvestwithease.config;

import java.util.ArrayList;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class HarvestWithEaseConfig {
	private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static final CommonConfig COMMON = new CommonConfig(BUILDER);
	public static final ForgeConfigSpec SPEC = BUILDER.build();
		
	public static ArrayList<String> getCrops() {
		return COMMON.crops.get();
	}

	public static class CommonConfig {
		public final ConfigValue<ArrayList<String>> crops;

		public CommonConfig(ForgeConfigSpec.Builder builder) {
			crops = builder.comment("List of in-game IDs of additional crops").define("crops", new ArrayList<String>());
		}
	}
}
