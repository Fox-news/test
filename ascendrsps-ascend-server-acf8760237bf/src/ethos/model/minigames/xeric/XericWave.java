package ethos.model.minigames.xeric;

import ethos.util.Misc;

public class XericWave {
 //spawn locations for monsters
	public static int[][] SPAWN_DATA = { { 2715, 5481 }, { 2703, 5468 }, { 2710, 5458 }, { 2721, 5457 }, { 2729, 5470 } };
//Npc ids
	public static final int RUNT = 7547, BEAST = 7548, RANGER = 7559, MAGE = 7560, SHAMAN = 7573, LIZARD = 7597,
			AIR_CRAB = 7576, FIRE_CRAB = 7577, EARTH_CRAB = 7578, WATER_CRAB = 7579, ICE_FIEND = 7586, VESPINE = 7538,
			VANGUARD = 7529,

			// BOSSES
			VESPULA = 7531, TEKTON = 7543, MUTTADILE = 7563, VASA = 7566, ICE_DEMON = 7585;

		//get the npcs hp
	public static int getHp(int npc) {
		switch (npc) {
		case RUNT:
			return 95;
		case BEAST:
			return 165;
		case RANGER:
		case MAGE:
			return 220;
		case SHAMAN:
			return 750;
		case LIZARD:
			return 60;
		case AIR_CRAB:
		case FIRE_CRAB:
		case EARTH_CRAB:
		case WATER_CRAB:
			return 120;
		case ICE_FIEND:
			return 150;
		case VESPINE:
			return 280;
		case VANGUARD:
			return 180;

		case ICE_DEMON:
			return 1500;
		case VESPULA:
			return 1800;
		case MUTTADILE:
			return 2250;
		case VASA:
			return 2000;
		case TEKTON:
			return 2500;
		}
		return 50 + Misc.random(50);
	}
	//get the npcs max hit
	public static int getMax(int npc) {
		switch (npc) {
		case RUNT:
			return 8;
		case BEAST:
			return 12;
		case RANGER:
			return 18;
		case MAGE:
			return 22;
		case SHAMAN:
			return 25;
		case LIZARD:
			return 5;
		case AIR_CRAB:
		case FIRE_CRAB:
		case EARTH_CRAB:
		case WATER_CRAB:
			return 15;
		case ICE_FIEND:
			return 18;
		case VESPINE:
			return 22;
		case VANGUARD:
			return 20;

		case VESPULA:
			return 60;
		case TEKTON:
			return 70;
		case MUTTADILE:
			return 50;
		case VASA:
			return 60;
		case ICE_DEMON:
			return 45;
		}
		return 5 + Misc.random(5);
	}
// get the npcs attack lvl?
	public static int getAtk(int npc) {
		switch (npc) {
		case RUNT:
			return 10;
		case BEAST:
			return 25;
		case RANGER:
			return 0;
		case MAGE:
			return 0;
		case SHAMAN:
			return 300;
		case LIZARD:
			return 10;
		case AIR_CRAB:
		case FIRE_CRAB:
		case EARTH_CRAB:
		case WATER_CRAB:
			return 70;
		case ICE_FIEND:
			return 75;
		case VESPINE:
			return 125;
		case VANGUARD:
			return 100;

		case VESPULA:
			return 360;
		case TEKTON:
			return 500;
		case MUTTADILE:
			return 350;
		case VASA:
			return 350;
		case ICE_DEMON:
			return 400;
		}
		return 50 + Misc.random(50);
	}
//get the npcs def lvl
	public static int getDef(int npc) {
		switch (npc) {
		case RUNT:
			return 40;
		case BEAST:
			return 60;
		case RANGER:
			return 80;
		case MAGE:
			return 70;
		case SHAMAN:
			return 250;
		case LIZARD:
			return 30;
		case AIR_CRAB:
		case FIRE_CRAB:
		case EARTH_CRAB:
		case WATER_CRAB:
			return 80;
		case ICE_FIEND:
			return 40;
		case VESPINE:
			return 180;
		case VANGUARD:
			return 120;

		case VESPULA:
			return 220;
		case TEKTON:
			return 320;
		case MUTTADILE:
			return 350;
		case VASA:
			return 300;
		case ICE_DEMON:
			return 280;
		}
		return 50 + Misc.random(50);
	} 
//sets the npcs to the level (wave) ie. line 1 = wave 1, line 2 = wave 2 etc etc
	public static final int[][] LEVEL = {
			{ RUNT, RUNT, RUNT },
			{ RUNT, RUNT, RUNT, BEAST, BEAST },
			{ BEAST, BEAST, RANGER, MAGE },
			{ RANGER, RANGER, MAGE, MAGE },
			{ RUNT, RUNT, RUNT, RUNT, MUTTADILE },

			{ RUNT, RUNT, RUNT },
			{ RUNT, RUNT, RUNT, BEAST, BEAST },
			{ BEAST, BEAST, ICE_FIEND, ICE_FIEND },
			{ ICE_FIEND, ICE_FIEND, ICE_FIEND, ICE_FIEND },
			{ RUNT, ICE_FIEND, ICE_FIEND, ICE_DEMON },

			{ RUNT, RUNT, RUNT },
			{ RUNT, RUNT, RUNT, BEAST, BEAST},
			{ BEAST, BEAST, BEAST},
			{ RUNT, RUNT, RUNT, BEAST, BEAST},
			{ SHAMAN, SHAMAN, SHAMAN },

			{ VANGUARD, VANGUARD, VANGUARD },
			{ VANGUARD, VANGUARD, VANGUARD, VESPINE },
			{ VESPINE, VESPINE, VESPINE },
			{ VANGUARD, VANGUARD, VESPINE, VESPINE },
			{ VESPINE, VESPINE, VESPULA },

			{ AIR_CRAB, WATER_CRAB, FIRE_CRAB, EARTH_CRAB },
			{ AIR_CRAB, WATER_CRAB, FIRE_CRAB, EARTH_CRAB, VANGUARD, VANGUARD },
			{ AIR_CRAB, WATER_CRAB, FIRE_CRAB, EARTH_CRAB, VESPINE, VESPINE },
			{ VESPINE, TEKTON, VESPINE },

			{ TEKTON, VESPULA, SHAMAN }

	};

}
