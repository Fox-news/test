package ethos.model.minigames.xeric;

import ethos.Server;
import ethos.event.CycleEvent;
import ethos.event.CycleEventContainer;
import ethos.event.CycleEventHandler;
import ethos.model.content.achievement.AchievementType;
import ethos.model.content.achievement.Achievements;
import ethos.model.content.achievement_diary.karamja.KaramjaDiaryEntry;
import ethos.model.items.ItemAssistant;
import ethos.model.npcs.NPCHandler;
import ethos.model.players.Boundary;
import ethos.model.players.Player;
import ethos.model.players.PlayerHandler;
import ethos.util.Misc;
import ethos.model.minigames.xeric.XericRewards;

/**
 * 
 * @author Jason http://www.rune-server.org/members/jason
 * @date Oct 17, 2013
 */
public class Xeric {

	private Player player;
	private int killsRemaining;

	public Xeric(Player player) {
		this.player = player;
	}

	public void spawn() {
		final int[][] type = XericWave.LEVEL;
		if (player.xericWaveId >= type.length ) {
			stop();
			return;
		}
		CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer event) {
				if (player == null) {
					event.stop();
					return;
				}
				if (player.xericWaveId >= type.length) {
					stop();
					event.stop();
					return;
				}
				if (player.waveId != 0 && player.waveId < type.length)
					player.sendMessage("You are now on wave " + (player.waveId + 1) + " of " + type.length + ".", 255);
				setKillsRemaining(type[player.waveId].length);
				for (int i = 0; i < getKillsRemaining(); i++) {
					int npcType = type[player.waveId][i];
					int index = Misc.random(XericWave.SPAWN_DATA.length - 1);
					int x = XericWave.SPAWN_DATA[index][0];
					int y = XericWave.SPAWN_DATA[index][1];
					Server.npcHandler.spawnNpc(player, npcType, x, y, player.getIndex() * 4, 1,	XericWave.getHp(npcType), XericWave.getMax(npcType), XericWave.getAtk(npcType), XericWave.getDef(npcType), true, false);
				}
				event.stop();
			}

			@Override
			public void stop() {

			}
		}, 16);
	}

	public void leaveGame() {
		killAllSpawns();
		player.sendMessage("You have left the Trials of Xeric.");
		player.getPA().movePlayer(3104, 3500, 0);
		player.xericWaveId = 0;
	}

	public void create(int type) {
		player.getPA().removeAllWindows();
		player.getPA().movePlayer((Misc.random(3) + 2715), (Misc.random(3) + 5470), player.getIndex() * 4);
		player.sendMessage("Welcome to the Trials of Xeric. Your first wave will start soon.", 255);
		player.xericWaveId = 0;
		spawn();
	}

	public void stop() {
		int dmg = 8000;
		XericRewards.giveReward(dmg);
		player.getPA().movePlayer(3104, 3500, 0);
		player.getDH().sendStatement("Congratulations for finishing the Trials of Xeric!");
		player.xericWaveId = 0;
		player.nextChat = 0;
		player.setRunEnergy(100);
		killAllSpawns();
	}

	public void handleDeath() {
		player.getPA().movePlayer(2438, 5168, 0);
		player.getDH().sendStatement("Unfortunately you died on wave " + player.waveId + ". Better luck next time.");
		player.nextChat = 0;
		player.xericWaveId = 0;
		killAllSpawns();
	}

	public void killAllSpawns() {
		for (int i = 0; i < NPCHandler.npcs.length; i++) {
			if (NPCHandler.npcs[i] != null) {
				if (NPCHandler.isSpawnedBy(player, NPCHandler.npcs[i])) {
					NPCHandler.npcs[i] = null;

				}
			}
		}
	}

	private static final int[] REWARD_ITEMS = { 6571, 6528, 11128, 6523, 6524, 6525, 6526, 6527, 6568, 6523, 6524, 6525,
			6526, 6527, 6568 };

	public static final int FIRE_CAPE = 6570;

	public static final int TOKKUL = 6529;

	public void reward() {
		Achievements.increase(player, AchievementType.FIGHT_CAVES_ROUNDS, 1);
		switch (player.waveType) {
		case 1:
			player.getItems().addItemUnderAnyCircumstance(FIRE_CAPE, 1);
			// player.getItems().addItem(FIRE_CAPE, 1);
			break;
		case 2:
			player.getItems().addItemUnderAnyCircumstance(FIRE_CAPE, 1);
			// player.getItems().addItem(FIRE_CAPE, 1);
			break;
		case 3:
			player.getDiaryManager().getKaramjaDiary().progress(KaramjaDiaryEntry.COMPLETE_FIGHT_CAVES);
			int item = REWARD_ITEMS[Misc.random(REWARD_ITEMS.length - 1)];
			player.getItems().addItemUnderAnyCircumstance(FIRE_CAPE, 2);
			// player.getItems().addItem(FIRE_CAPE, 1);
			// player.getItems().addItem(item, 1);
			player.getItems().addItemUnderAnyCircumstance(item, 1);
			PlayerHandler.executeGlobalMessage(player.playerName + " has completed 63 waves of jad and received "
					+ ItemAssistant.getItemName(item) + ".");
			break;
		}
		// player.getItems().addItem(TOKKUL, (10000 * player.waveType) +
		// Misc.random(5000));
		player.getItems().addItemUnderAnyCircumstance(TOKKUL, (10000 * player.waveType) + Misc.random(5000));
	}

	public int getKillsRemaining() {
		return killsRemaining;
	}

	public void setKillsRemaining(int remaining) {
		this.killsRemaining = remaining;
	}

}
