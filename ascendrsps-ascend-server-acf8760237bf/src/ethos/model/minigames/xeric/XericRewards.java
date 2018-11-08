/* Distributes loot after completion of Trials of Xeric.
 * Author @Patrity
 */
package ethos.model.minigames.xeric;

import ethos.model.players.Player;
import ethos.model.players.PlayerHandler;
import ethos.util.Misc;
import ethos.model.items.ItemAssistant;

public class XericRewards {

	private static Player player;

	public XericRewards(Player player) {
		XericRewards.player = player;
	}

	public static void giveReward(int dmg) {
		int roll = Misc.random(100);

		if (dmg > 9999) {
			if (roll >= 93) {
				rareDrop();
			} else {
				commonDrop();
			}
		}

		if (dmg >= 8000 && dmg <= 9999) {
			if (roll >= 95) {
				rareDrop();
			} else {
				commonDrop();
			}
		}

		if (dmg >= 5000 && dmg <= 7999) {
			if (roll >= 97) {
				rareDrop();
			}
		}

		if (dmg <= 4999) {
			if (roll >= 99) {
				rareDrop();
			} else {
				commonDrop();
			}
		}
	}

	public static final int rareDropItem[] = { 
			200,//guam
			204,//tarromin
			206,//harralander
			208,//ranarr
			210,//irit
			212,//avantoe
			214,//kwuarm
			216,//cadantine
			220,//torstol
			2486,//lantadyme
			3050,//toadflax
			3052,//snapdragon
			
};
	public static final int commonDropItem[] = {
			200,//guam
			204,//tarromin
			206,//harralander
			208,//ranarr
			210,//irit
			212,//avantoe
			214,//kwuarm
			216,//cadantine
			220,//torstol
			2486,//lantadyme
			3050,//toadflax
			3052,//snapdragon
			454,//coal
			441,//iron ore
			
			
};

	public static void rareDrop() {
		int rareitem = 0;
		rareitem = Misc.random(rareDropItem.length);
		rareitem -= 1; // subtract one due to array being zero set
		if (rareitem < 0) {
			rareitem = Misc.random(rareDropItem.length);
		}
		player.getItems().addItemUnderAnyCircumstance(rareitem, 1);
	}

	public static void commonDrop() {
		int commonitem = 0;
		commonitem = Misc.random(rareDropItem.length);
		commonitem -= 1; // subtract one due to array being zero set
		if (commonitem < 0) {
			commonitem = Misc.random(rareDropItem.length);
		}
		 player.getItems().addItemUnderAnyCircumstance(commonitem, (100 + Misc.random(80)));
	}
}