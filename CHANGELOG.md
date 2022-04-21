# Change Log

All notable changes to the "harvest-with-ease" Minecraft mod will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]
- Nothing new

## [1.4.0] - 2022/04/21
- Added configuration option to set whether to play a sound when harvesting a crop. Defaults to true.
- Added some logs.
- Upgrated to Forge 40.1.0.

## [1.3.1] - 2022/04/16
- Fixed a bug that damaged hoes upon use in creative when "damage on harvest" was set to true.
- Fixed a bug that prevented hoes from breaking even when their damages was <= 0.
- Fixed a bug where Off Hand holding a hoe had priority over Main Hand.

## [1.3.0] - 2022/04/15
- Added a new configuration option: "exp on harvest".  
This new option allows to define a custom experience value to grant the player upon harvesting.  
It must be an integer value and will be effective only if greater than 0.
Note that this value is in experience points and not experience levels.

## [1.2.0] - 2022/04/14
- Added a new configuration option: "damage on harvest".  
This new option allows to define a custom damage value to apply on the hoe used to right-click harvest.  
It must be an integer value and will be effective only if greater than 0 and require hoe is set to true.
- Added Javadoc to the code.

## [1.1.4] - 2022/04/13
- Fixed a bug for which it was impossible to place a block against a mature crop.

## [1.1.3] - 2022/04/13
- Fixed a bug which made it possible to harvest crops in spectator.
- Fixed a possible error that would make the game crash when the "age" property of a crop was not an IntegerProperty.
- Updated project image.
- Improved and fixed changelog.
- Updated [README].

## [1.1.2] - 2022/04/12
- Fixed a bug for which MainHand item was used to harvest even when OffHand item would swing.
- Updated [README].

## [1.1.1] - 2022/04/10
- Fixed project updates.
- Changed license.

## [1.1.0] - 2022/04/01
- Added config option to set whether holding a hoe (either hands) is required to right-click harvest.
- Updated [README].

## [1.0.2] - 2022/04/01
- Event handling by this mod now has an higher priority to prevent other mods from overriding its behavior (for example with Croptopia this mod event handling was overrided, leading to a minor bug).
- Now 1 seed is removed from the drops given upon harvesting to simulate the action of replanting.
- Harvesting a crop with an item enchanted with fortune now works.
- Added logo.
- Configured project for GitHub and version control.

## [1.0.1] - 2022/03/31
- Drops now pop from the face the crop was clicked on to avoid annoying behaviors with leaf crops.

## [1.0.0] - 2022/03/31
- Right clicking on a crop allows to harvest it without eradicating it.
- A config file is provided to add eventual modded crops that don't work out of the box with this mod.

[Unreleased]: https://github.com/Nyphet/harvest-with-ease
[README]: https://github.com/Nyphet/harvest-with-ease#readme
[1.4.0]: https://github.com/Nyphet/harvest-with-ease/releases/tag/v1.4.0
[1.3.1]: https://github.com/Nyphet/harvest-with-ease/releases/tag/v1.3.1
[1.3.0]: https://github.com/Nyphet/harvest-with-ease/releases/tag/v1.3.0
[1.2.0]: https://github.com/Nyphet/harvest-with-ease/releases/tag/v1.2.0
[1.1.4]: https://github.com/Nyphet/harvest-with-ease/releases/tag/v1.1.4
[1.1.3]: https://github.com/Nyphet/harvest-with-ease/releases/tag/v1.1.3
[1.1.2]: https://github.com/Nyphet/harvest-with-ease/releases/tag/v1.1.2
[1.1.1]: https://github.com/Nyphet/harvest-with-ease/releases/tag/v1.1.1
[1.1.0]: https://github.com/Nyphet/harvest-with-ease/releases/tag/v1.1.0
[1.0.2]: https://github.com/Nyphet/harvest-with-ease/releases/tag/v1.0.2
[1.0.1]: https://www.curseforge.com/minecraft/mc-mods/harvest-with-ease/files/3725580
[1.0.0]: https://www.curseforge.com/minecraft/mc-mods/harvest-with-ease/files/3725566
