![Harvest with ease banner](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/harvest-with-ease/banner.png "Harvest with ease banner")

---

![Minecraft](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/minecraft/minecraft.svg "Minecraft")[![1.20.2](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/minecraft/1-20-2.svg "1.20.2")](https://modrinth.com/mod/harvest-with-ease/versions?g=1.20.2)![Separator](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/separator.svg)[![1.19.4](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/minecraft/1-19-4.svg "1.19.4")](https://modrinth.com/mod/harvest-with-ease/versions?g=1.19.4)![Separator](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/separator.svg)[![1.19.2](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/minecraft/1-19-2.svg "1.19.2")](https://modrinth.com/mod/harvest-with-ease/versions?g=1.19.2)![Separator](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/separator.svg)[![1.18.2](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/minecraft/1-18-2.svg "1.18.2")](https://modrinth.com/mod/harvest-with-ease/versions?g=1.18.2)

![Loader](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/loader/loader.svg "Loader")[![NeoForge](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/loader/neoforge.svg "NeoForge")](https://modrinth.com/mod/harvest-with-ease/versions?l=neoforge)![Separator](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/separator.svg)[![Forge](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/loader/forge.svg "Forge")](https://modrinth.com/mod/harvest-with-ease/versions?l=forge)![Separator](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/separator.svg)[![Fabric](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/loader/fabric.svg "Fabric")](https://modrinth.com/mod/harvest-with-ease/versions?l=fabric)

![Overlay](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/side/client-server.svg)

![Issues](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/github/issues.svg "Issues")[![GitHub](https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/github/github.svg "GitHub")](https://github.com/crystal-nest/harvest-with-ease/issues)

---

## **Description**

Multiloader skeleton for Minecraft mods!
Built on [Jared's MultiLoaderTemplate](https://github.com/jaredlll08/MultiLoader-Template), with the addition of:

- Tasks to publish on Maven, GitHub, Modrinth, and CurseForge.
- [Cobweb](https://modrinth.com/mod/cobweb) API dependency.
- A little bit more Javadoc.
- Code style changes.

***Note: the intended use of this template is to create a repository from it, and then update each branch with the content built by our [official generator](https://crystalnest.it/generator).***

## **Setup completion**

To complete the setup:

- Change the [Support us](#support-us) section and the banner link.
- Add your project CurseForge ID in the `gradle.properties`.
- Update the changelog with proper release notes.
- Run the task `common > Tasks > vanilla gradle > decompile`
- Run the task `forge > Tasks > forgegradle runs > genIntellijRuns`

## Removing Platforms and Loaders

While this template includes support for Fabric, Forge, and NeoForge, you can easily remove support for the ones you don't need.  
This can be done by deleting the subproject folder and then removing it from the associated `include` in the `settings.gradle` file, along with deleting the related gradle properties.

The same thing applies for the different publishing platforms, Maven, GitHub, Modrinth, and CurseForge.  
To remove support for the ones you don't need just remove the section and properties regarding them.

## **License and right of use**

Feel free to use this mod template for any mod, just be sure to give credit and possibly link [here](https://github.com/crystal-nest/harvest-with-ease#readme).  
This project is published under the [GNU General Public License v3.0](https://github.com/crystal-nest/harvest-with-ease/blob/master/LICENSE).

## **Support us**

<a href="https://crystalnest.it"><img alt="Crystal Nest Website" src="https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/crystal-nest/pic512.png" width="14.286%"></a><a href="https://discord.gg/BP6EdBfAmt"><img alt="Discord" src="https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/discord/discord512.png" width="14.286%"></a><a href="https://www.patreon.com/crystalspider"><img alt="Patreon" src="https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/patreon/patreon512.png" width="14.286%"></a><a href="https://ko-fi.com/crystalspider"><img alt="Ko-fi" src="https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/kofi/kofi512.png" width="14.286%"></a><a href="https://github.com/Crystal-Nest"><img alt="Our other projects" src="https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/github/github512.png" width="14.286%"><a href="https://modrinth.com/organization/crystal-nest"><img alt="Modrinth" src="https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/modrinth/modrinth512.png" width="14.286%"></a><a href="https://www.curseforge.com/members/crystalspider/projects"><img alt="CurseForge" src="https://raw.githubusercontent.com/crystal-nest/mod-fancy-assets/main/curseforge/curseforge512.png" width="14.286%"></a>

[![Bisect Hosting](https://www.bisecthosting.com/partners/custom-banners/d559b544-474c-4109-b861-1b2e6ca6026a.webp "Bisect Hosting")](https://bisecthosting.com/crystalspider)
