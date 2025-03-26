# Change Log

All notable changes to the "harvest-with-ease" Minecraft mod will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Crystal Nest Semantic Versioning](https://crystalnest.it/#/versioning).

## [Unreleased]

- Nothing new.

## [v9.4.0] - 2025/03/dd

- Port to 1.21.5.

## [v9.4.0] - 2025/02/02

- Port to 1.21.4.

## [v9.4.0] - 2024/12/06

- Ported to 1.21.3.
- Implemented [#52](https://github.com/Crystal-Nest/harvest-with-ease/issues/52), food exhaustion when harvesting.
- Now, by default, 0.005 food exhaustion is caused when right-click harvesting (the same as regular block breaking).
- A new configuration option, `exhaustion multiplier`, has been added to multiply the default food exhaustion, ranging from 0 (no food exhaustion) to 400 (1 hunger point every 2 crops harvested).
- Configuration comments changed and options have been divided in sections. **Be careful when updating: old configurations might be overwritten!**
- Slightly improved performance when multi-harvesting.
- Added new API utility method `HarvestUtils#hasEnoughHunger(Player)` to check whether a player has enough hunger and saturation to right-click harvest.
- Changed `HarvestUtils#isBlacklisted(BlockState)` to `HarvestUtils#isAllowed(BlockState)`, with the latter equal to the negation of the former.
- Dropped support for 1.18.2.

## [v9.3.0] - 2024/11/12

- Fix compatibility with Pam's HarvestCraft mods.
- Fix compatibility with Croptopia.
- Reverted generalization of logic to handle crops that do not yield their seeds (e.g. the Pitcher crop), which looked good on paper, but was akin to begging for mod incompatibility.
- Removed `use seeds from inventory` configuration option. Now it always happens, but for the Pitcher crop only.

## [v9.2.1] - 2024/09/30

- Fix support for 1.21.1.

## [v9.2.0] - 2024/09/21

- Fixed [#50](https://github.com/Crystal-Nest/harvest-with-ease/issues/50), Mystical Agriculture plants break when harvesting.
- Implemented [#49](https://github.com/Crystal-Nest/harvest-with-ease/issues/49), Harvested crops drop together in front of the player.

## [v9.1.1] - 2024/09/15

- Fixed [#48](https://github.com/Crystal-Nest/harvest-with-ease/issues/48), Farmer's Delight Tomatoes are broken fully when harvested.
- Fixed a minor bug introduced with [v9.1.0], Pitcher crop yielding double drops.
- Fixed a minor bug introduced with [v9.1.0], tall crops yielding wrong amount of drops.

## [v9.1.0] - 2024/09/07

- Implemented [#47](https://github.com/Crystal-Nest/harvest-with-ease/issues/47), use seeds from the inventory to replant.

## [v9.0.2] - 2024/07/19

- Fixed [#46](https://github.com/Crystal-Nest/harvest-with-ease/issues/46), crop not harvested when using tools.

## [v9.0.1] - 2024/07/11

- Ported to 1.21
- Officially dropped support for Forge.
- Added new configuration option `tiers` to specify the tier order (and thus levels) since Minecraft stripped tiers from their levels.
- Added new API method to retrieve a `TieredItem` tier level.

## [v9.0.1] - 2024/06/27

- Fix [#44](https://github.com/Crystal-Nest/harvest-with-ease/issues/44), config value resets itself each start.
- Fix `HarvestCheckEvent` by actually stopping further computations when harvesting is prevented.
- Minor changes about API annotations and abstractions.

## [v9.0.0] - 2024/06/26

- Stable version release.
- Fix declared dependencies.
- Fix interaction with TorchFlower.
- Update to our newest standards.

<details>
  <summary>Legacy</summary>

### [1.20.4-9.0.0.3-beta] - 2024/05/30

- Fix Forge build and artifact.
- Fix and update build.gradle.
- Add blacklist config option ([#43](https://github.com/Crystal-Nest/harvest-with-ease/issues/43)) and blacklist tag ([#31](https://github.com/Crystal-Nest/harvest-with-ease/issues/31)).

### [1.20.2-9.0.0.3-beta] - 2024/05/30

- Fix Forge build and artifact.
- Fix and update build.gradle.
- Add blacklist config option ([#43](https://github.com/Crystal-Nest/harvest-with-ease/issues/43)) and blacklist tag ([#31](https://github.com/Crystal-Nest/harvest-with-ease/issues/31)).

### [1.19.4-9.0.0.3-beta] - 2024/05/30

- Fix Forge build and artifact.
- Fix and update build.gradle.
- Add blacklist config option ([#43](https://github.com/Crystal-Nest/harvest-with-ease/issues/43)) and blacklist tag ([#31](https://github.com/Crystal-Nest/harvest-with-ease/issues/31)).

### [1.19.2-9.0.0.3-beta] - 2024/05/30

- Fix Forge build and artifact.
- Fix and update build.gradle.
- Add blacklist config option ([#43](https://github.com/Crystal-Nest/harvest-with-ease/issues/43)) and blacklist tag ([#31](https://github.com/Crystal-Nest/harvest-with-ease/issues/31)).

### [1.18.2-9.0.0.3-beta] - 2024/05/30

- Fix Forge build and artifact.
- Fix and update build.gradle.
- Add blacklist config option ([#43](https://github.com/Crystal-Nest/harvest-with-ease/issues/43)) and blacklist tag ([#31](https://github.com/Crystal-Nest/harvest-with-ease/issues/31)).

### [1.20.2-9.0.0.2-beta] - 2024/05/28

- Test on dependencies.

### [1.20.4-9.0.0.1-beta] - 2024/05/18

- Changed publisher plugin.
- Fixed declared related dependencies.
- Updated Cobweb API requirement to v0.0.3.0-beta.

### [1.20.4-9.0.0.0-beta] - 2024/05/15

- Changed mod id from `harvestwithease` to `harvest_with_ease`.
- Changed file configuration name.
- Removed `play sound` configuration option.
- Rewrite to comply with Cobweb Mod Template.
- Added Cobweb API as a required dependency.
- Removed API for handling tiers as it has been moved to Cobweb API.

### [1.20.4-8.0.1.1] - 2024/02/09

**(NeoForge only)**

- Fixed [#39](https://github.com/Crystal-Nest/harvest-with-ease/issues/39), incompatibility with NeoForge latest versions.
  This version is also compatible with NeoForge 20.4.70-beta and below.

### [1.20.4-8.0.1.0] - 2024/01/13

- Fixed [#37](https://github.com/crystal-nest/harvest-with-ease/issues/37), make exp orbs drop when right-click harvest.

### [1.20.2-8.0.1.0] - 2024/01/13

- Fixed [#37](https://github.com/crystal-nest/harvest-with-ease/issues/37), make exp orbs drop when right-click harvest.

### [1.20.1-8.0.1.0] - 2024/01/13

- Fixed [#37](https://github.com/crystal-nest/harvest-with-ease/issues/37), make exp orbs drop when right-click harvest.

### [1.19.4-8.0.1.0] - 2024/01/13

- Fixed [#37](https://github.com/crystal-nest/harvest-with-ease/issues/37), make exp orbs drop when right-click harvest.

### [1.19.2-8.0.1.0] - 2024/01/13

- Fixed [#37](https://github.com/crystal-nest/harvest-with-ease/issues/37), make exp orbs drop when right-click harvest.

### [1.18.2-8.0.1.0] - 2024/01/13

- Fixed [#37](https://github.com/crystal-nest/harvest-with-ease/issues/37), make exp orbs drop when right-click harvest.

### [1.20.4-8.0.0.2] - 2023/12/22

- Ported to 1.20.4.

### [1.20.2-8.0.0.2] - 2023/12/04

- Fixed [#33](https://github.com/crystal-nest/harvest-with-ease/issues/33), config option being reset.

### [1.20.1-8.0.0.2] - 2023/12/04

- Fixed [#33](https://github.com/crystal-nest/harvest-with-ease/issues/33), config option being reset.

### [1.19.4-8.0.0.2] - 2023/12/04

- Fixed [#33](https://github.com/crystal-nest/harvest-with-ease/issues/33), config option being reset.

### [1.19.2-8.0.0.2] - 2023/12/04

- Fixed [#33](https://github.com/crystal-nest/harvest-with-ease/issues/33), config option being reset.

### [1.18.2-8.0.0.2] - 2023/12/04

- Fixed [#33](https://github.com/crystal-nest/harvest-with-ease/issues/33), config option being reset.

### [1.20.2-8.0.0.1] - 2023/12/01

- Fixed [#32](https://github.com/crystal-nest/harvest-with-ease/issues/32).
- Fixed server-side compatibility issue.

### [1.20.1-8.0.0.1] - 2023/12/01

- Fixed [#32](https://github.com/crystal-nest/harvest-with-ease/issues/32).
- Fixed server-side compatibility issue.

### [1.19.4-8.0.0.1] - 2023/12/01

- Fixed [#32](https://github.com/crystal-nest/harvest-with-ease/issues/32).
- Fixed server-side compatibility issue.

### [1.19.2-8.0.0.1] - 2023/12/01

- Fixed [#32](https://github.com/crystal-nest/harvest-with-ease/issues/32).
- Fixed server-side compatibility issue.

### [1.18.2-8.0.0.1] - 2023/12/01

- Fixed [#32](https://github.com/crystal-nest/harvest-with-ease/issues/32).
- Fixed server-side compatibility issue.

### [1.20.2-8.0.0.0] - 2023/11/24

- Fixed [#28](https://github.com/crystal-nest/harvest-with-ease/issues/28), Farmer's Delight tomatoes not giving xp when harvested.
- Implemented [#30](https://github.com/crystal-nest/harvest-with-ease/issues/30), multi-harvest.
- Fixed interaction with Pitcher Crop.

### [1.20.1-8.0.0.0] - 2023/11/24

- Fixed [#28](https://github.com/crystal-nest/harvest-with-ease/issues/28), Farmer's Delight tomatoes not giving xp when harvested.
- Implemented [#30](https://github.com/crystal-nest/harvest-with-ease/issues/30), multi-harvest.
- Fixed interaction with Pitcher Crop.

### [1.19.4-8.0.0.0] - 2023/11/24

- Fixed [#28](https://github.com/crystal-nest/harvest-with-ease/issues/28), Farmer's Delight tomatoes not giving xp when harvested.
- Implemented [#30](https://github.com/crystal-nest/harvest-with-ease/issues/30), multi-harvest.

### [1.19.2-8.0.0.0] - 2023/11/24

- Fixed [#28](https://github.com/crystal-nest/harvest-with-ease/issues/28), Farmer's Delight tomatoes not giving xp when harvested.
- Implemented [#30](https://github.com/crystal-nest/harvest-with-ease/issues/30), multi-harvest.

### [1.18.2-8.0.0.0] - 2023/11/24

- Fixed [#28](https://github.com/crystal-nest/harvest-with-ease/issues/28), Farmer's Delight tomatoes not giving xp when harvested.
- Implemented [#30](https://github.com/crystal-nest/harvest-with-ease/issues/30), multi-harvest.

### [1.20.2-7.1.0.0] - 2023/10/31

- Implemented [#27](https://github.com/crystal-nest/harvest-with-ease/issues/27).

### [1.20.1-7.1.0.0] - 2023/10/31

- Implemented [#27](https://github.com/crystal-nest/harvest-with-ease/issues/27).

### [1.19.4-6.1.0.0] - 2023/10/31

- Implemented [#27](https://github.com/crystal-nest/harvest-with-ease/issues/27).

### [1.19.2-6.1.0.0] - 2023/10/31

- Implemented [#27](https://github.com/crystal-nest/harvest-with-ease/issues/27).

### [1.18.2-6.1.0.0] - 2023/10/31

- Implemented [#27](https://github.com/crystal-nest/harvest-with-ease/issues/27).

### [1.20.2-7.0.0.2] - 2023/10/19

- Ported to 1.20.2.

### [1.20.1-7.0.0.2] - 2023/07/05

- Fix [#24](https://github.com/crystal-nest/harvest-with-ease/issues/24)

### [1.20-7.0.0.2] - 2023/07/05

- Fix [#24](https://github.com/crystal-nest/harvest-with-ease/issues/24)

### [1.19.4-6.0.1.4] - 2023/07/05

- Fix [#24](https://github.com/crystal-nest/harvest-with-ease/issues/24)

### [1.19.2-6.0.1.4-final] - 2023/07/05

- Fix [#24](https://github.com/crystal-nest/harvest-with-ease/issues/24)
- I don't know why I kept the "final", but whatever.

### [1.20.1-7.0.0.1] - 2023/06/17

- Fix [#23](https://github.com/crystal-nest/harvest-with-ease/issues/23).

### [1.20-7.0.0.1] - 2023/06/17

- Fix [#23](https://github.com/crystal-nest/harvest-with-ease/issues/23).

### [1.19.4-6.0.1.3] - 2023/06/17

- Fix [#23](https://github.com/crystal-nest/harvest-with-ease/issues/23).

### [1.19.2-6.0.1.3-final] - 2023/06/17

- Fix [#23](https://github.com/crystal-nest/harvest-with-ease/issues/23).
- Sigh... I should just stop using "final" at this point...

### [1.18.2-6.0.1.3] - 2023/06/17

- Fix [#23](https://github.com/crystal-nest/harvest-with-ease/issues/23).

### [1.20.1-7.0.0.0] - 2023/06/13

- Ported to 1.20.1.

### [1.20-7.0.0.0] - 2023/06/10

- Ported to 1.20.

### [1.19.4-6.0.1.2] - 2023/04/12

- Fix `require hoe` configuration option always preventing harvest when set to true.

### [1.19.3-6.0.1.2] - 2023/04/12

- Fix `require hoe` configuration option always preventing harvest when set to true.

### [1.19.2-6.0.1.2-final] - 2023/04/12

- Fix `require hoe` configuration option always preventing harvest when set to true.
- I know it's the fourth "final" version for this Minecraft version...

### [1.18.2-6.0.1.2] - 2023/04/12

- Fix `require hoe` configuration option always preventing harvest when set to true.

### [1.19.4-6.0.1.1] - 2023/04/02

- Fix [#21](https://github.com/crystal-nest/harvest-with-ease/issues/21).

### [1.19.3-6.0.1.1] - 2023/04/02

- Fix [#21](https://github.com/crystal-nest/harvest-with-ease/issues/21).

### [1.19.2-6.0.1.1-final] - 2023/04/02

- Fix [#21](https://github.com/crystal-nest/harvest-with-ease/issues/21).
- I know it's the third "final" version for this Minecraft version, but I **had** to fix a couple more bugs!

### [1.18.2-6.0.1.1] - 2023/04/02

- Fix [#21](https://github.com/crystal-nest/harvest-with-ease/issues/21).

### [1.19.4-6.0.1.0] - 2023/03/30

- Fix [#20](https://github.com/crystal-nest/harvest-with-ease/issues/20).
- Improved API.

### [1.19.3-6.0.1.0] - 2023/03/30

- Fix [#20](https://github.com/crystal-nest/harvest-with-ease/issues/20).
- Improved API.

### [1.19.2-6.0.1.0-final] - 2023/03/30

- Fix [#20](https://github.com/crystal-nest/harvest-with-ease/issues/20).
- Improved API.

### [1.18.2-6.0.1.0] - 2023/03/30

- Fix [#20](https://github.com/crystal-nest/harvest-with-ease/issues/20).
- Improved API.

### [1.19.4-6.0.0.0] - 2023/03/17

- Ported to 1.19.4
- Added API for better compatibility and integration with other mods.
- Changed in-game mod picture.
- Fixed a bug that would prevent crops extending `CocoaBlock` or `NetherWartBlock` to be correctly recognized as right-click harvestable crops.
- Improved compatibility with other mods.

### [1.19.3-6.0.0.0] - 2023/03/17

- Added API for better compatibility and integration with other mods.
- Changed in-game mod picture.
- Fixed a bug that would prevent crops extending `CocoaBlock` or `NetherWartBlock` to be correctly recognized as right-click harvestable crops.
- Improved compatibility with other mods.

### [1.19.2-6.0.0.0-final] - 2023/03/17

- Added API for better compatibility and integration with other mods.
- Changed in-game mod picture.
- Fixed a bug that would prevent crops extending `CocoaBlock` or `NetherWartBlock` to be correctly recognized as right-click harvestable crops.
- Improved compatibility with other mods.

### [1.18.2-6.0.0.0] - 2023/03/17

- Added API for better compatibility and integration with other mods.
- Changed in-game mod picture.
- Fixed a bug that would prevent crops extending `CocoaBlock` or `NetherWartBlock` to be correctly recognized as right-click harvestable crops.
- Improved compatibility with other mods.

### [1.19.3-5.0.0.0] - 2023/01/01

- Ported to 1.19.3.

### [1.19.2-4.0.0.3] - 2022/12/03

- Fixed Forge Config API version compatibility.

### [1.18.2-4.0.0.2] - 2022/12/03

- Fixed Forge Config API version compatibility.

### [1.19.2-4.0.0.2] - 2022/09/06

- Fixed Fabric crash ([#9](https://github.com/crystal-nest/harvest-with-ease/issues/9)).

### [1.19.2-4.0.0.1] - 2022/09/05

- Fixed configuration being ignored sometimes.

### [1.19.1-4.0.0.1-final] - 2022/09/05

- Fixed configuration being ignored sometimes.
- This is the FINAL version, 1.19.1 won't receive further updates.

### [1.19-4.0.0.1-final] - 2022/09/05

- Fixed configuration being ignored sometimes.
- This is the FINAL version, 1.19 won't receive further updates.

### [1.18.2-4.0.0.1] - 2022/09/05

- Fixed configuration being ignored sometimes.

### [1.19.2-4.0.0.0] - 2022/08/17

- Updated to Minecraft 1.19.2.

### [1.19.1-4.0.0.0] - 2022/08/17

- Updated version to comply with Forge Semantic Versioning.

### [1.19-4.0.0.0] - 2022/08/17

- Updated version to comply with Forge Semantic Versioning.

### [1.18.2-4.0.0.0] - 2022/08/17

- Updated version to comply with Forge Semantic Versioning.

### [1.19.1-3.0.0.0] - 2022/07/29

- Updated project and repository structure.
- Updated to Minecraft 1.19.1.

### [1.19-3.0.0.0] - 2022/07/29

- Updated project and repository structure.
- Updated version to comply with Forge Semantic Versioning.

### [1.18.2-3.0.0.0] - 2022/07/29

- Updated project and repository structure.
- Updated version to comply with Forge Semantic Versioning.

### [1.19-2.0.0.1] - 2022/06/25

- Fixed a bug that would ignore required tool for crops.

### [1.18.2-2.0.0.1] - 2022/06/25

- Fixed a bug that would ignore required tool for crops.

### [1.19-2.0.0.0] - 2022/06/08

- Updated to Minecraft 1.19.

### [1.18.2-2.0.0.0] - 2022/06/08

- Updated version to comply with Forge Semantic Versioning.

### [1.18.2-1.0.0.1] - 2022/06/04

- Changed standard naming: moved "forge" and "fabric" notation to the end of the version to avoid crashes with Forge.

### [1.18.2-1.0.0.0] - 2022/06/02

- Merged Fabric version and Forge version into a single project.
- Changed semantic versioning system.

### [1.4.0] - 2022/04/21

- Added configuration option to set whether to play a sound when harvesting a crop. Defaults to true.
- Added some logs.
- Upgraded to Forge 40.1.0.

### [1.3.1] - 2022/04/16

- Fixed a bug that damaged hoes upon use in creative when "damage on harvest" was set to true.
- Fixed a bug that prevented hoes from breaking even when their damages was <= 0.
- Fixed a bug where Off Hand holding a hoe had priority over Main Hand.

### [1.3.0] - 2022/04/15

- Added a new configuration option: "exp on harvest".  
  This new option allows to define a custom experience value to grant the player upon harvesting.  
  It must be an integer value and will be effective only if greater than 0.
  Note that this value is in experience points and not experience levels.

### [1.2.0] - 2022/04/14

- Added a new configuration option: "damage on harvest".  
  This new option allows to define a custom damage value to apply on the hoe used to right-click harvest.  
  It must be an integer value and will be effective only if greater than 0 and require hoe is set to true.
- Added Javadoc to the code.

### [1.1.4] - 2022/04/13

- Fixed a bug for which it was impossible to place a block against a mature crop.

### [1.1.3] - 2022/04/13

- Fixed a bug which made it possible to harvest crops in spectator.
- Fixed a possible error that would make the game crash when the "age" property of a crop was not an IntegerProperty.
- Updated project image.
- Improved and fixed changelog.
- Updated [README].

### [1.1.2] - 2022/04/12

- Fixed a bug for which MainHand item was used to harvest even when OffHand item would swing.
- Updated [README].

### [1.1.1] - 2022/04/10

- Fixed project updates.
- Changed license.

### [1.1.0] - 2022/04/01

- Added config option to set whether holding a hoe (either hands) is required to right-click harvest.
- Updated [README].

### [1.0.2] - 2022/04/01

- Event handling by this mod now has a higher priority to prevent other mods from overriding its behavior (for example with Croptopia this mod event handling was overridden, leading to a minor bug).
- Now 1 seed is removed from the drops given upon harvesting to simulate the action of replanting.
- Harvesting a crop with an item enchanted with fortune now works.
- Added logo.
- Configured project for GitHub and version control.

### [1.0.1] - 2022/03/31

- Drops now pop from the face the crop was clicked on to avoid annoying behaviors with leaf crops.

### [1.0.0] - 2022/03/31

- Right-clicking on a crop allows to harvest it without eradicating it.
- A config file is provided to add eventual modded crops that don't work out of the box with this mod.

</details>

[Unreleased]: https://github.com/crystal-nest/harvest-with-ease
[README]: https://github.com/crystal-nest/harvest-with-ease#readme

[v9.4.0]: https://github.com/crystal-nest/harvest-with-ease/releases?q=9.4.0
[v9.3.0]: https://github.com/crystal-nest/harvest-with-ease/releases?q=9.3.0
[v9.2.1]: https://github.com/crystal-nest/harvest-with-ease/releases?q=9.2.1
[v9.2.0]: https://github.com/crystal-nest/harvest-with-ease/releases?q=9.2.0
[v9.1.1]: https://github.com/crystal-nest/harvest-with-ease/releases?q=9.1.1
[v9.1.0]: https://github.com/crystal-nest/harvest-with-ease/releases?q=9.1.0
[v9.0.2]: https://github.com/crystal-nest/harvest-with-ease/releases?q=9.0.2
[v9.0.1]: https://github.com/crystal-nest/harvest-with-ease/releases?q=9.0.1
[v9.0.0]: https://github.com/crystal-nest/harvest-with-ease/releases?q=9.0.0

[1.20.4-9.0.0.3-beta]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.20.4-9.0.0.3-beta
[1.20.4-9.0.0.1-beta]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.20.4-9.0.0.1-beta
[1.20.4-9.0.0.0-beta]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.20.4-9.0.0.0-beta
[1.20.4-8.0.1.1]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.20.4-8.0.1.1
[1.20.4-8.0.1.0]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.20.4-8.0.1.0
[1.20.4-8.0.0.2]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.20.4-8.0.0.2

[1.20.2-9.0.0.3-beta]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.20.2-9.0.0.3-beta
[1.20.2-9.0.0.2-beta]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.20.2-9.0.0.2-beta
[1.20.2-8.0.1.0]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.20.2-8.0.1.0
[1.20.2-8.0.0.2]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.20.2-8.0.0.2
[1.20.2-8.0.0.1]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.20.2-8.0.0.1
[1.20.2-8.0.0.0]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.20.2-8.0.0.0
[1.20.2-7.1.0.0]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.20.2-7.1.0.0
[1.20.2-7.0.0.2]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.20.2-7.0.0.2

[1.20.1-8.0.1.0]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.20.1-8.0.1.0
[1.20.1-8.0.0.2]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.20.1-8.0.0.2
[1.20.1-8.0.0.1]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.20.1-8.0.0.1
[1.20.1-8.0.0.0]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.20.1-8.0.0.0
[1.20.1-7.1.0.0]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.20.1-7.1.0.0
[1.20.1-7.0.0.2]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.20.1-7.0.0.2
[1.20.1-7.0.0.1]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.20.1-7.0.0.1
[1.20.1-7.0.0.0]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.20.1-7.0.0.0

[1.20-7.0.0.2]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.20-7.0.0.2
[1.20-7.0.0.1]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.20-7.0.0.1
[1.20-7.0.0.0]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.20-7.0.0.0

[1.19.4-9.0.0.3-beta]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.19.4-9.0.0.3-beta
[1.19.4-8.0.1.0]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.19.4-8.0.1.0
[1.19.4-8.0.0.2]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.19.4-8.0.0.2
[1.19.4-8.0.0.1]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.19.4-8.0.0.1
[1.19.4-8.0.0.0]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.19.4-8.0.0.0
[1.19.4-6.1.0.0]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.19.4-6.1.0.0
[1.19.4-6.0.1.4]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.19.4-6.0.1.4
[1.19.4-6.0.1.3]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.19.4-6.0.1.3
[1.19.4-6.0.1.2]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.19.4-6.0.1.2
[1.19.4-6.0.1.1]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.19.4-6.0.1.1
[1.19.4-6.0.1.0]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.19.4-6.0.1.0
[1.19.4-6.0.0.0]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.19.4-6.0.0.0

[1.19.3-6.0.1.2]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.19.3-6.0.1.2
[1.19.3-6.0.1.1]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.19.3-6.0.1.1
[1.19.3-6.0.1.0]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.19.3-6.0.1.0
[1.19.3-6.0.0.0]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.19.3-6.0.0.0
[1.19.3-5.0.0.1]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.19.3-5.0.0.1
[1.19.3-5.0.0.0]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.19.3-5.0.0.0

[1.19.2-9.0.0.3-beta]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.19.2-9.0.0.3-beta
[1.19.2-8.0.1.0]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.19.2-8.0.1.0
[1.19.2-8.0.0.2]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.19.2-8.0.0.2
[1.19.2-8.0.0.1]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.19.2-8.0.0.1
[1.19.2-8.0.0.0]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.19.2-8.0.0.0
[1.19.2-6.1.0.0]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.19.2-6.1.0.0
[1.19.2-6.0.1.4-final]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.19.2-6.0.1.4-final
[1.19.2-6.0.1.3-final]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.19.2-6.0.1.3-final
[1.19.2-6.0.1.2-final]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.19.2-6.0.1.2-final
[1.19.2-6.0.1.1-final]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.19.2-6.0.1.1-final
[1.19.2-6.0.1.0-final]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.19.2-6.0.1.0-final
[1.19.2-6.0.0.0-final]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.19.2-6.0.0.0-final
[1.19.2-4.0.0.4]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.19.2-4.0.0.4
[1.19.2-4.0.0.3]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.19.2-4.0.0.3
[1.19.2-4.0.0.2]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.19.2-4.0.0.2
[1.19.2-4.0.0.1]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.19.2-4.0.0.1
[1.19.2-4.0.0.0]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.19.2-4.0.0.0

[1.19.1-4.0.0.1-final]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.19.1-4.0.0.1-final
[1.19.1-4.0.0.0]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.19.1-4.0.0.0
[1.19.1-3.0.0.0]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.19.1-3.0.0.0

[1.19-4.0.0.1-final]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.19-4.0.0.1-final
[1.19-4.0.0.0]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.19-4.0.0.0
[1.19-3.0.0.0]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.19-3.0.0.0
[1.19-2.0.0.1]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.19-2.0.0.1
[1.19-2.0.0.0]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.19-2.0.0.0

[1.18.2-9.0.0.3-beta]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.18.2-9.0.0.3-beta
[1.18.2-8.0.1.0]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.18.2-8.0.1.0
[1.18.2-8.0.0.2]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.18.2-8.0.0.2
[1.18.2-8.0.0.1]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.18.2-8.0.0.1
[1.18.2-8.0.0.0]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.18.2-8.0.0.0
[1.18.2-6.1.0.0]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.18.2-6.1.0.0
[1.18.2-6.0.1.3]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.18.2-6.0.1.3
[1.18.2-6.0.1.2]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.18.2-6.0.1.2
[1.18.2-6.0.1.1]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.18.2-6.0.1.1
[1.18.2-6.0.1.0]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.18.2-6.0.1.0
[1.18.2-6.0.0.0]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.18.2-6.0.0.0
[1.18.2-4.0.0.3]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.18.2-4.0.0.3
[1.18.2-4.0.0.2]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.18.2-4.0.0.2
[1.18.2-4.0.0.1]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.18.2-4.0.0.1
[1.18.2-4.0.0.0]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.18.2-4.0.0.0
[1.18.2-3.0.0.0]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.18.2-3.0.0.0
[1.18.2-2.0.0.1]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.18.2-2.0.0.1
[1.18.2-2.0.0.0]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.18.2-2.0.0.0
[1.18.2-1.0.0.1]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.18.2-1.0.0.1
[1.18.2-1.0.0.0]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.18.2-1.0.0.0

[1.4.0]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.4.0
[1.3.1]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.3.1
[1.3.0]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.3.0
[1.2.0]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.2.0
[1.1.4]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.1.4
[1.1.3]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.1.3
[1.1.2]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.1.2
[1.1.1]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.1.1
[1.1.0]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.1.0
[1.0.2]: https://github.com/crystal-nest/harvest-with-ease/releases/tag/v1.0.2
[1.0.1]: https://www.curseforge.com/minecraft/mc-mods/harvest-with-ease/files/3725580
[1.0.0]: https://www.curseforge.com/minecraft/mc-mods/harvest-with-ease/files/3725566
