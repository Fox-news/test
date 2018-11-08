package ethos.model.players.skills;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ethos.Server;
import ethos.event.CycleEvent;
import ethos.event.CycleEventContainer;
import ethos.event.CycleEventHandler;
import ethos.model.content.achievement.AchievementType;
import ethos.model.content.achievement.Achievements;
import ethos.model.content.achievement_diary.falador.FaladorDiaryEntry;
import ethos.model.content.achievement_diary.kandarin.KandarinDiaryEntry;
import ethos.model.content.achievement_diary.karamja.KaramjaDiaryEntry;
import ethos.model.content.achievement_diary.wilderness.WildernessDiaryEntry;
import ethos.model.content.dailytasks.DailyTasks;
import ethos.model.content.dailytasks.DailyTasks.PossibleTasks;
import ethos.model.players.Boundary;
import ethos.model.players.Player;
import ethos.model.players.PlayerHandler;
import ethos.util.Misc;

public class Fishing2 {
	
	/*
	 * Player Object
	 */
	private Player player;
	
	/*
	 * Randomizer
	 */
	private Random random = new Random();//used for random fish, rewards, chance of continuous fishing
	
	/*
	 * Holds possible fish you can get from the specific fishing spot you are interacting with
	 */
	List <Integer> attemptCatch = new ArrayList <Integer>();
	
	/*
	 * Holds if the player is currently fishing or not
	 */
	public boolean isFishing = false;//if player is currently fishing or not
	
	/*
	 * POSSIBLE REWARDS
	 */
	
	private static final int EASY_CLUE = 13648;
	private static final int MEDIUM_CLUE = 13649;
	private static final int HARD_CLUE = 13650;
	private static final int ANGLER_HAT = 13258;
	private static final int ANGLER_TOP = 13259;
	private static final int ANGLER_WADERS = 13648;
	private static final int ANDLER_BOOTS = 13648;
	private static final int PET_HERON = 13320;
	
	/*
	 * FISH ID'S
	 */
	private static final int RAW_SHRIMP = 317;
	private static final int RAW_HERRING = 345;
	private static final int RAW_ANCHOVIE = 321;
	private static final int RAW_SARDINE = 327;
	private static final int RAW_TROUT = 335;
	private static final int RAW_PIKE = 349;
	private static final int RAW_SALMON = 331;
	private static final int RAW_TUNA = 359;
	private static final int RAW_LOBSTER = 377;
	private static final int RAW_SWORDFISH = 371;
	private static final int RAW_KARAMBWAN = 3142;
	private static final int RAW_MONKFISH = 7944;
	private static final int RAW_SHARK = 383;
	private static final int RAW_ANGLER = 13439;
	private static final int DARK_CRAB = 11934;
	
	/*
	 * FISHING METHODS -the fishing tool item id's
	 */
	private static final int SMALL_NET = 303;
	private static final int FLY_FISHING = 309;
	private static final int HARPOON = 311;
	private static final int CAGE = 301;
	private static final int BIG_NET = 305;
	private static final int KARAMBWAN_VESSEL = 3157;
	private static final int FISHING_ROD = 307;
	
	/*
	 * BAIT TYPES
	 */
	private static final int NO_BAIT = 0;
	private static final int FISHING_BAIT = 313;
	private static final int FEATHERS = 314;
	private static final int SANDWORM = 13431;//
	private static final int DARK_FISHING_BAIT = 9999;//change to dark fishing bait id
	
	
	public Fishing2(Player p) {//constructor sets player objects
		this.player = p;
	}
		
	
	public enum FISHING{//all fishing data
		
		SHRIMP(RAW_SHRIMP,SMALL_NET,NO_BAIT,1,100,3913,621),
		HERRING(RAW_HERRING,FISHING_ROD,FISHING_BAIT,10,1000,3913,622),
		SARDINE(RAW_SARDINE,FISHING_ROD,FISHING_BAIT,10,1000,3913,622),
		ANCHOVIE(RAW_ANCHOVIE,SMALL_NET,NO_BAIT,15,1500,3913,621),
		TROUT(RAW_TROUT,FLY_FISHING,FEATHERS,20,2000,3417,623),
		PIKE(RAW_PIKE,FISHING_ROD,FISHING_BAIT,25,2500,3417,622),
		SALMON(RAW_SALMON,FLY_FISHING, FEATHERS, 30,3000,3417,623),
		TUNA(RAW_TUNA,HARPOON,NO_BAIT,35,3500,3657,618),
		LOBSTER(RAW_LOBSTER,CAGE,NO_BAIT,40,4000,3657,619),
		SWORDFISH(RAW_SWORDFISH,HARPOON,NO_BAIT,45,4500,3657,618),
		MONKFISH(RAW_MONKFISH,BIG_NET,NO_BAIT,62,6200,1520,620),
		KARAMBWAN(RAW_KARAMBWAN,KARAMBWAN_VESSEL,NO_BAIT,62,6200,4712,620),
		SHARK(RAW_SHARK,HARPOON, NO_BAIT,76,7600,1520,618),
		ANGLER(RAW_ANGLER,FISHING_ROD,SANDWORM,82,8200,6825,622),
		DARKCRAB(DARK_CRAB,CAGE,DARK_FISHING_BAIT,85,8500,635,619);
		
		 int fishId;//the type of fish you get
		 int fishingType;//the equipment needed to fish3
		 int bait;//what type of bait used to fish
		 int levelRequired;//level needed to fish
		 int xpGained;//how much experience you get per fish
		 int fishingSpotId;//the fishing spot object id
		 int animationId;//animation performed
		
		FISHING(int fishId, int fishingType, int bait, int levelRequired, int xpGained, int fishingSpotId, int animationId){
			this.fishId = fishId;
			this.fishingType = fishingType;
			this.bait = bait;
			this.levelRequired = levelRequired;
			this.xpGained = xpGained;
			this.fishingSpotId = fishingSpotId;
			this.animationId = animationId;
		}
		
	}
	
	
	
	public enum REWARDS{//all random rewards you can possibly get while fishing
		EASYCLUE(EASY_CLUE,240,0),
		MEDIUMCLUE(MEDIUM_CLUE,120,1),
		HARDCLUE(HARD_CLUE,60,2),
		ANGLERHAT(ANGLER_HAT,30,3),
		ANGLERTOP(ANGLER_TOP,30,4),
		ANGLERWADERS(ANGLER_WADERS,30,5),
		ANGLERBOOTS(ANDLER_BOOTS,30,6),
		HERON(PET_HERON,5,7);
		
		int itemId;
		int chance;
		int index;
		
		REWARDS(int itemId, int chance, int index){
			this.itemId = itemId;
			this.chance = chance;
			this.index = index;
		}
	}
	
	
	public void startFishing(int fishingSpotId, int fishingType) {//starts fishing
		attemptCatch.clear();//resets possible catches from spot
		for(FISHING fishing: FISHING.values()) {
			if(fishingSpotId == fishing.fishingSpotId && player.playerLevel[player.playerFishing] >= fishing.levelRequired && fishing.fishingType == fishingType) {
				if(!player.getItems().playerHasItem(fishing.fishingType)) {
					player.sendMessage("You need a "+Server.itemHandler.getItemList(fishing.fishingType).itemName+" to fish here.");
					return;
				}
				if(!player.getItems().playerHasItem(fishing.bait) && fishing.bait != 0) {
					player.sendMessage("You need a "+Server.itemHandler.getItemList(fishing.bait).itemName+" to fish here.");
					return;
				}
				if(player.getItems().freeSlots() == 0) {
					player.sendMessage("You have no more space in your inventory.");
					return;
				}
				player.startAnimation(fishing.animationId);
				attemptCatch.add(fishing.fishId);
			}
		}//end of loop
		if(isFishing == true) {
			return;
		}
		//if you pass all the conditions you begin to fish
		isFishing = true;
		player.sendMessage("You begin fishing.");
		player.stopPlayerSkill = true;
		CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer container) {
				if(attemptCatch.isEmpty() == true) {//makes it so players cant get null fish
					container.stop();//stops actual event
					stop();//resets player
					return;
				}
				if(random.nextInt(100)>65+(player.playerLevel[player.playerFishing]/4)) {//chance of continuing to fish
					container.stop();
					stop();
					return;
				}
				if(player.getItems().freeSlots() == 0) {//if player continues to fish without stopping this will stop event when inv is full
					player.sendMessage("You have no more space in your inventory.");
					container.stop();
					stop();
					return;
				}
				if (!player.stopPlayerSkill) {//if player walks away or stops fishing this stops thge container
					container.stop();
					stop();
				}
				catchFish(fishingSpotId);//after you pass conditions you catch the fish
		}
				@Override
				public void stop() {//resets fishing at the of the event
					resetFishing();
					return;
				}
			}, 5 + random.nextInt(10));//time between caught fish (randomizes intervals)
	}
	
	
public void catchFish(int fishingSpotId) {//when you catch the fish it is handled here
	if(isFishing == false) {
		return;
	}
	if(player.getItems().freeSlots() == 0) {//Will stop playing from fishing with full inventory
		player.sendMessage("You have no more space in your inventory.");
		return;
	}
	for(FISHING fishing: FISHING.values()) {
		if(attemptCatch.stream().findFirst().get().equals(fishing.fishId)) {
			if(!player.getItems().playerHasItem(fishing.bait) && fishing.bait != 0) {
				attemptCatch.clear();
				player.sendMessage("You have ran out of "+Server.itemHandler.getItemList(fishing.bait).itemName+"(s).");
				return;
			}
	getAchievmentProgression();
	player.startAnimation(fishing.animationId);
	player.sendMessage("You caught a "+Server.itemHandler.getItemList(fishing.fishId).itemName);
	player.getPA().addSkillXP(fishing.xpGained, player.playerFishing, true);
	player.getItems().deleteItem(fishing.bait, 1);
	player.getItems().addItem(fishing.fishId, 1);
	attemptRandomReward();
		} 
		if(fishing.fishId == attemptCatch.get(random.nextInt(attemptCatch.size())) && extraFish() == true && player.getItems().freeSlots() != 0) {
			player.sendMessage("You caught a "+Server.itemHandler.getItemList(fishing.fishId).itemName);
			player.getItems().deleteItem(fishing.bait, 1);
			player.getPA().addSkillXP(fishing.xpGained, player.playerFishing, true);
			player.getItems().addItem(fishing.fishId, 1);
			return;
		}
	}
}

private boolean extraFish() {//chance of getting an extra fish
	return random.nextInt(100)<30+(player.playerLevel[player.playerFishing]/4);
}

public void attemptRandomReward() {//random chance of getting clue scroll
	for(REWARDS rewards: REWARDS.values()) {
		if(random.nextInt(REWARDS.values().length) == rewards.index && random.nextInt(5000) < rewards.chance ) {
			player.sendMessage("You recieved a "+Server.itemHandler.getItemList(rewards.itemId).itemName);
			player.getItems().addItemUnderAnyCircumstance(rewards.itemId, 1);
		}
	}
}


public void getAchievmentProgression() {
	Achievements.increase(player, AchievementType.FISH, 1);//adds to achievment
	switch (player.playerSkillProp[10][1]) {
	case 389:
		if (Boundary.isIn(player, Boundary.FALADOR_BOUNDARY)) {
			player.getDiaryManager().getFaladorDiary().progress(FaladorDiaryEntry.FISH_MANTA);
		}
		break;
	case 371:
		if (Boundary.isIn(player, Boundary.CATHERBY_BOUNDARY)) {
			player.getDiaryManager().getKandarinDiary().progress(KandarinDiaryEntry.FISH_SWORD);
		}
		break;
		
	case 377:
		if (Boundary.isIn(player, Boundary.KARAMJA_BOUNDARY)) {
			player.getDiaryManager().getKaramjaDiary().progress(KaramjaDiaryEntry.FISH_LOBSTER_KAR);
		}
		break;
		
	case 3142:
		if (Boundary.isIn(player, Boundary.RESOURCE_AREA_BOUNDARY)) {
			player.getDiaryManager().getWildernessDiary().progress(WildernessDiaryEntry.KARAMBWAN);
		}
		break;
	}
	switch (player.playerSkillProp[10][7]) {
	case 389:
		if (Boundary.isIn(player, Boundary.FALADOR_BOUNDARY)) {
			player.getDiaryManager().getFaladorDiary().progress(FaladorDiaryEntry.FISH_MANTA);
		}
		break;
	}
	
	switch (player.playerSkillProp[10][4]) {
	case 383:
		DailyTasks.increase(player, PossibleTasks.SHARKS);
		break;
	case 389:
		if (Boundary.isIn(player, Boundary.FALADOR_BOUNDARY)) {
			player.getDiaryManager().getFaladorDiary().progress(FaladorDiaryEntry.FISH_MANTA);
		}
		break;
		
	case 377:
		if (Boundary.isIn(player, Boundary.KARAMJA_BOUNDARY)) {
			player.getDiaryManager().getKaramjaDiary().progress(KaramjaDiaryEntry.FISH_LOBSTER_KAR);
		}
		break;
		
	case 3142:
		if (Boundary.isIn(player, Boundary.RESOURCE_AREA_BOUNDARY)) {
			player.getDiaryManager().getWildernessDiary().progress(WildernessDiaryEntry.KARAMBWAN);
		}
		break;
	}
}

private void resetFishing() {//when anything interrupts player from fishing this resets player
	isFishing = false;
	player.startAnimation(65535);
	player.getPA().removeAllWindows();
	player.playerSkilling[10] = false;
}	
}//end of class
