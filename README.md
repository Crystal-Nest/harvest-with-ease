# Harvest with ease
### Harvest your crops with ease by simply right clicking on them, without having to tediously destroy and replant them all the time!

## Features
- Right-click to harvest any crop, works with both Vanilla and modded out of the box!
- Works on nether warts and cocoa beans too!
- Correctly consumes 1 crop seed to simulate replanting!
- Right-click while holding an item with fortune to increase drops!
- Customizable: a config file is provided with several options, see next section for more details.
- Integrable: as stated, should work with any crop, Vanilla and modded; Furthermore if it doesn't work with some crop, such crop ID can be added in the config file to make it compatible.

## Configuration
### This mod works already out of the box, configuration is only for further tweakings
- crops: \[List\<String\>\] - list of additional in-game IDs for crops that are not supported out of the box.
- require hoe: \[Boolean\] - whether holding a hoe (either hands) is required to right-click harvest.
- damage on harvest: \[Integer\] - how much damage the hoe should receive upon use, if any (effective only if greater than 0 and "require hoe" is set to true).
- exp on harvest: \[Integer\] - how many experience points should be granted when right-click harvesting (effective only if greater than 0; note it's exp points and not exp levels).
- play sound \[Boolean\] - whether to play a sound when harvesting a crop.

## Downloads
This is BOTH the FORGE and FABRIC version, so make sure you're downloading the correct version that suits your needs.  
Downloads are available [here](https://www.curseforge.com/minecraft/mc-mods/harvest-with-ease/files).

## Issues
To open a new issue visit the [issues tab](https://github.com/Nyphet/harvest-with-ease/issues).

## Technical details
This mod is required on both server and client to work properly.  
Your modded crops will work with this mod out of the box only if they extend the CropBlock class (which generally is how new crops should do).  
If for whatever reason you can't extend that class in your mod you can add its in-game ID to the config file, however note that this will work only if your crops have an IntegerProperty (IntProperty for Fabric) whose name is set to be "age" and represents the age values your crops can have, from 0 to a max value.

## License and right of use
Feel free to use this mod for any modpack or video, just be sure to give credit and possibly link [here](https://github.com/Nyphet/harvest-with-ease#readme).  
This project is published under the [GNU General Public License v3.0](https://github.com/Nyphet/harvest-with-ease/blob/1.18.2/main/LICENSE).
