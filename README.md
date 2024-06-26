![Harvest with ease banner](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/harvest-with-ease/banner.gif)

---
![Minecraft](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/minecraft/minecraft.svg)[![1.20.4](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/minecraft/1-20-4.svg)](https://modrinth.com/mod/harvest-with-ease/versions?g=1.20.4)![Separator](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/separator.svg)[![1.20.2](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/minecraft/1-20-2.svg)](https://modrinth.com/mod/harvest-with-ease/versions?g=1.20.2)![Separator](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/separator.svg)[![1.19.4](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/minecraft/1-19-4.svg)](https://modrinth.com/mod/harvest-with-ease/versions?g=1.19.4)![Separator](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/separator.svg)[![1.19.2](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/minecraft/1-19-2.svg)](https://modrinth.com/mod/harvest-with-ease/versions?g=1.19.2)![Separator](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/separator.svg)[![1.18.2](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/minecraft/1-18-2.svg)](https://modrinth.com/mod/harvest-with-ease/versions?g=1.18.2)

![Loader](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/loader/loader.svg)[![Forge](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/loader/forge.svg)](https://modrinth.com/mod/harvest-with-ease/versions?l=forge)![Separator](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/separator.svg)[![Fabric](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/loader/fabric.svg)](https://modrinth.com/mod/harvest-with-ease/versions?l=fabric)

![Overlay](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/side/server.svg)

![Issues](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/github/issues.svg)[![GitHub](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/github/github.svg)](https://github.com/crystal-nest/harvest-with-ease/issues)

---

## **Description**

Harvesting crops is such a pain, breaking them all to get the drops and then having to replant each one.  
With this mod you can just right-click on your crops to harvest and replant them in one go!  
It's also compatible with **almost any** modded crops, to ease your mind of one more thing!

*Although it's stated that the mod is required server-side only, when a client that does not have the mod connects to a server that does and tries to harvest a crop while holding a block, it causes block flashing.*  
*The only way to prevent this glitch is to set `require hoe` to `true`.*

## **Features**

- Right-click to harvest any crop, works with both Vanilla and modded out of the box!  
  ![Harvesting wheat](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/harvest-with-ease/wheat.gif)
- Works on nether warts and cocoa beans too!  
  ![Harvesting cocoa](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/harvest-with-ease/cocoa.gif) ![Harvesting nether wart](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/harvest-with-ease/wart.gif)
- Correctly consumes 1 crop seed to simulate replanting!
- Right-click while holding an item with fortune to increase drops!
- **Highly configurable!** See next section for more details.

## **Configuration**

- **`require hoe`**: whether holding a hoe (either hands) is required to right-click harvest, defaults to `false`.
- **`damage on harvest`**: how much damage the hoe should receive upon use, effective only if `> 0` and **`require hoe`** is enabled.
- **`exp on harvest`**: how many experience points should be granted when right-click harvesting or break-harvesting, effective only if `> 0` (note it's exp _points_ and not exp _levels_).
- **`multi-harvest starting tier`**:  
  Tool tier starting from which it is possible to harvest multiple crops at once.  
  All tiers that cannot multi-harvest will have a 1x1 square area of effect (a single crop).  
  If **`starting harvest area size`** is set to `single` and **`area increment step`** to `none` multi-harvest will be effectively disabled, regardless of this config option value.  
  When set to `none` multi-harvest will be enabled without a tool too. Note that **`require hoe`** takes precedence.
- **`starting harvest area size`**:  
  Starting multi-harvest area size (square side length).  
  The area is always a square centered on the right-clicked crop.  
  Setting this to `single` and **`area increment step`** to `none` will effectively disable multi-harvest.
- **`area increment step`**:  
  Increment step for the harvest area size with higher tool tiers.  
  Setting this to `none` and **`starting harvest area size`** to `single` will effectively disable multi-harvest.
- **`crops`**: list of additional in-game IDs for crops that are not supported out of the box, defaults to an empty list.  
  This last config option is just a safety measure, so far no crop needs it.
- **`blacklist`**: list in-game IDs for crops that under no condition can be right-click harvested.

## **Dependencies**

| Mod                                                                     |         Loader         | Requirement |
|:------------------------------------------------------------------------|:----------------------:|:-----------:|
| [Cobweb](https://modrinth.com/mod/forge-config-api-port)                |          All           |  Required   |
| [Forge Config API Port](https://modrinth.com/mod/forge-config-api-port) | Fabric; Forge ≥ 1.20.2 |  Required   |

## **License and right of use**

Feel free to use this mod for any modpack or video, just be sure to give credit and possibly link [here](https://github.com/crystal-nest/harvest-with-ease#readme).  
This project is published under the [GNU General Public License v3.0](https://github.com/crystal-nest/harvest-with-ease/blob/master/LICENSE).

## **For developers**

Your modded crops will work with this mod out of the box only if they extend the CropBlock class, which is how it should be.  
If you can't extend that class, the **`crops`** config option is there for you. Just add the in-game ID of your crop(s) to make it work, however note that this will work only if your crops have an Integer Property whose name
is set to be `"age"` and represents the age values your crops can have, from `0` to a `max` value.

Since v6.0.0.0, an API is available to better integrate your mod with this one, if needed.  
Since v9.0.0, it's possible to blacklist crops via datapack.  
To learn how to use the provided API and the datapack feature, follow the [Wiki](https://github.com/crystal-nest/harvest-with-ease/wiki).

## **Support us**

<a href="https://crystalnest.it"><img alt="Crystal Nest Website" src="https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/crystal-nest/pic512.png" width="14.286%"></a><a href="https://discord.gg/BP6EdBfAmt"><img alt="Discord" src="https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/discord/discord512.png" width="14.286%"></a><a href="https://www.patreon.com/crystalspider"><img alt="Patreon" src="https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/patreon/patreon512.png" width="14.286%"></a><a href="https://ko-fi.com/crystalspider"><img alt="Ko-fi" src="https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/kofi/kofi512.png" width="14.286%"></a><a href="https://github.com/Crystal-Nest"><img alt="Our other projects" src="https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/github/github512.png" width="14.286%"><a href="https://modrinth.com/organization/crystal-nest"><img alt="Modrinth" src="https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/modrinth/modrinth512.png" width="14.286%"></a><a href="https://www.curseforge.com/members/crystalspider/projects"><img alt="CurseForge" src="https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/curseforge/curseforge512.png" width="14.286%"></a>

[![Bisect Hosting](https://www.bisecthosting.com/partners/custom-banners/d559b544-474c-4109-b861-1b2e6ca6026a.webp "Bisect Hosting")](https://bisecthosting.com/crystalspider)
