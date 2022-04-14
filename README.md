# Harvest with ease
### Harvest your crops with ease by simply right clicking on them, without having to tediously destroy and replant them all the time!

## Features
- Right-click to harvest any crop, works with both Vanilla and modded out of the box
- Works on nether warts and cocoa beans too
- Correctly consumes 1 crop seed to simulate replanting
- Right-click while holding an item with fortune to increase drops
- Customizable: a config file is provided with an option to set whether holding a hoe (either hands) is required to right-click harvest and how much damage the hoe should receive upon use, if any
- Integrable: as stated, should work with any crop, Vanilla and modded; Furthermore if it doesn't work with some crop, such crop ID can be added in the config file to make it compatible

## Downloads
This is the FORGE version, the FABRIC version is available [here](https://www.curseforge.com/minecraft/mc-mods/harvest-with-ease-fabric).  
Downloads are available [here](https://www.curseforge.com/minecraft/mc-mods/harvest-with-ease/files).

## Issues
To open an issue visit the [issues tab](https://github.com/Nyphet/harvest-with-ease/issues).

## Technical details
Your modded crops will work with this mod out of the box only if they extend the CropBlock class (which generally is how new crops should do).  
If for whatever reason you can't extend that class in your mod you can add its in-game ID to the config file, however note that this will work only if your crops have an IntegerProperty whose name is set to be "age" and represents the age values your crops can have, from 0 to a max value.  
Tutorial on how this mod was developed is available [here](https://www.twitch.tv/collections/9gBoBVnX4RZ38A).

## License and right of use
Feel free to use this mod for any modpack or video, just be sure to give credit and possibly link [here](https://github.com/Nyphet/harvest-with-ease#readme).  
This project is published under the [GNU General Public License v3.0](https://github.com/Nyphet/harvest-with-ease/blob/master/LICENSE).
