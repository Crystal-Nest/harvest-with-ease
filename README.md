![Harvest with ease banner](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/harvest-with-ease/banner.gif)

---
![Minecraft](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/minecraft/minecraft.svg)[![1.21.5](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/minecraft/1-21-5.svg)](https://www.patreon.com/c/crystalspider/membership)![Separator](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/separator.svg)[![1.21.4](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/minecraft/1-21-4.svg)](https://modrinth.com/mod/harvest-with-ease/versions?g=1.21.4)![Separator](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/separator.svg)[![1.21.3](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/minecraft/1-21-3.svg)](https://modrinth.com/mod/harvest-with-ease/versions?g=1.21.3)![Separator](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/separator.svg)[![1.21.1](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/minecraft/1-21-1.svg)](https://modrinth.com/mod/harvest-with-ease/versions?g=1.21.1)![Separator](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/separator.svg)[![1.21](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/minecraft/1-21.svg)](https://modrinth.com/mod/harvest-with-ease/versions?g=1.21)![Separator](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/separator.svg)[![1.20.4](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/minecraft/1-20-4.svg)](https://modrinth.com/mod/harvest-with-ease/versions?g=1.20.4)![Separator](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/separator.svg)[![1.20.2](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/minecraft/1-20-2.svg)](https://modrinth.com/mod/harvest-with-ease/versions?g=1.20.2)![Separator](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/separator.svg)[![1.20.1](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/minecraft/1-20-1.svg)](https://modrinth.com/mod/harvest-with-ease/versions?g=1.20.1)![Separator](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/separator.svg)[![1.19.4](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/minecraft/1-19-4.svg)](https://modrinth.com/mod/harvest-with-ease/versions?g=1.19.4)![Separator](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/separator.svg)[![1.19.2](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/minecraft/1-19-2.svg)](https://modrinth.com/mod/harvest-with-ease/versions?g=1.19.2)

![Loader](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/loader/loader.svg)[![NeoForge](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/loader/neoforge.svg)](https://modrinth.com/mod/harvest-with-ease/versions?l=neoforge)![Separator](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/separator.svg)[![Forge](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/loader/forge.svg)](https://modrinth.com/mod/harvest-with-ease/versions?l=forge)![Separator](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/separator.svg)[![Fabric](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/loader/fabric.svg)](https://modrinth.com/mod/harvest-with-ease/versions?l=fabric)

![Overlay](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/side/server.svg)

![Issues](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/github/issues.svg)[![GitHub](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/github/github.svg)](https://github.com/crystal-nest/harvest-with-ease/issues)

---

## 📝 Description

Tired of breaking and replanting crops over and over again?  
**Harvest With Ease** lets you **right-click** on fully grown crops to **harvest and replant them instantly** — no more tedious replanting!

It works out of the box with **Vanilla crops** and **most modded crops**, so you can relax and enjoy farming without the grind.

> 💡 *Note: While the mod is technically server-side only, a client without the mod may experience a visual glitch (block flashing) when harvesting while holding a block. To avoid this, set `require hoe` to `true` in the config.*

## ✨ Features

- ✅ **Right-click to harvest & replant** – works with **Vanilla** and most **modded crops**!  
  ![Harvesting wheat](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/harvest-with-ease/wheat.gif)
- 🍫 Supports **Nether Wart** and **Cocoa Beans** too!  
  ![Harvesting cocoa](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/harvest-with-ease/cocoa.gif) ![Harvesting nether wart](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/harvest-with-ease/wart.gif)
- 🌱 Automatically consumes 1 crop to simulate replanting.
- ✨ **Fortune support** – Use a Fortune-enchanted item for bonus drops!
- ⚙️ **Highly configurable** – Tailor it to your playstyle (see below!).
  
## ⚙️ Configuration

Customize your farming experience with settings for:

- Compatibility with modded crops.
- Gameplay balance, like requiring tools or limiting multi-harvest.
- Tweaks for multi-block harvest behavior.

📖 Check out the [dedicated Wiki page](https://github.com/Crystal-Nest/harvest-with-ease/wiki/End-user-configuration) for full details!

## 🔗 Dependencies

| Mod                                                                     |         Loader         | Requirement |
|:------------------------------------------------------------------------|:----------------------:|:-----------:|
| [Cobweb](https://modrinth.com/mod/cobweb)                               |          All           |  Required   |
| [Forge Config API Port](https://modrinth.com/mod/forge-config-api-port) | Fabric; Forge ≥ 1.20.2 |  Required   |

## 📜 License and right of use

Feel free to use this mod for any modpack or video, just be sure to give credit and possibly link [here](https://github.com/crystal-nest/harvest-with-ease#readme).  
This project is published under the [Crystal Nest Community License v1](https://github.com/crystal-nest/harvest-with-ease/blob/master/LICENSE).

## 💻 For Mod Developers

By default, any crop that extends `CropBlock` will automatically work with this mod — no extra steps needed!  
If your crops **don’t extend `CropBlock`**, you can still support them via the `crops` config option. Just make sure:

- The block has an Integer property named `"age"`  
- The property represents growth stages from `0` to a max value

Since **v9.0.0**, it's possible to blacklist crops via block tags.

📚 Full integration guides are available on the [Developer Wiki](https://github.com/Crystal-Nest/harvest-with-ease/wiki).

## ❤️ Support us

<a href="https://crystalnest.it"><img alt="Crystal Nest Website" src="https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/crystal-nest/pic512.png" width="14.286%"></a><a href="https://discord.gg/BP6EdBfAmt"><img alt="Discord" src="https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/discord/discord512.png" width="14.286%"></a><a href="https://www.patreon.com/crystalspider"><img alt="Patreon" src="https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/patreon/patreon512.png" width="14.286%"></a><a href="https://ko-fi.com/crystalspider"><img alt="Ko-fi" src="https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/kofi/kofi512.png" width="14.286%"></a><a href="https://github.com/Crystal-Nest"><img alt="Our other projects" src="https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/github/github512.png" width="14.286%"><a href="https://modrinth.com/organization/crystal-nest"><img alt="Modrinth" src="https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/modrinth/modrinth512.png" width="14.286%"></a><a href="https://www.curseforge.com/members/crystalspider/projects"><img alt="CurseForge" src="https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/curseforge/curseforge512.png" width="14.286%"></a>

[![Bisect Hosting](https://www.bisecthosting.com/partners/custom-banners/d559b544-474c-4109-b861-1b2e6ca6026a.webp "Bisect Hosting")](https://bisecthosting.com/crystalspider)
