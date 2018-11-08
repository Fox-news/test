package ethos.model.content;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import ethos.event.CycleEvent;
import ethos.event.CycleEventContainer;
import ethos.event.CycleEventHandler;
import ethos.model.items.GameItem;
import ethos.model.items.ItemAssistant;
import ethos.model.players.Player;
import ethos.model.players.PlayerHandler;
import ethos.util.Misc;

/**
 * Revamped a simple means of receiving a random item based on chance.
 * 
 * @author Jason MacKeigan
 * @date Oct 29, 2014, 1:43:44 PM
 */
/**
 * Revamped a simple means of receiving a random item based on chance.
 * 
 * @author Jason MacKeigan
 * @date Oct 29, 2014, 1:43:44 PM
 */
public class HourlyRewardBox extends CycleEvent {

	/**
	 * The item id of the mystery box required to trigger the event
	 */
	public static final int MYSTERY_BOX = 11739;

	/**
	 * A map containing a List of {@link GameItem}'s that contain items relevant to their rarity.
	 */
	private static Map<Rarity, List<GameItem>> items = new HashMap<>();

	/**
	 * Stores an array of items into each map with the corresponding rarity to the list
	 */
	static {
		items.put(Rarity.COMMON, 
			Arrays.asList(
				new GameItem(1960, 10),
				new GameItem(1960, 11),
				new GameItem(1960, 12),
				new GameItem(1960, 13),
				new GameItem(1960, 14),
				new GameItem(1960, 15),
				new GameItem(1960, 16),
				new GameItem(1960, 17),
				new GameItem(1960, 18),
				new GameItem(1960, 19),
				new GameItem(1960, 20),
				new GameItem(1960, 21),
				new GameItem(1960, 22),
				new GameItem(1960, 23),
				new GameItem(1960, 24),
				new GameItem(1960, 25)
				)
		);
		
	items.put(Rarity.UNCOMMON,
			Arrays.asList(
					new GameItem(1960, 25),
					new GameItem(1960, 27),
					new GameItem(1960, 28),
					new GameItem(1960, 29),
					new GameItem(1960, 30),
					new GameItem(1960, 31),
					new GameItem(1960, 32),
					new GameItem(1960, 33),
					new GameItem(1960, 34),
					new GameItem(1960, 35),
					new GameItem(1960, 36),
					new GameItem(1960, 37),
					new GameItem(1960, 38),
					new GameItem(1960, 39),
					new GameItem(1960, 40),
					new GameItem(1960, 41),
					new GameItem(1960, 42),
					new GameItem(1960, 43),
					new GameItem(1960, 44),
					new GameItem(1960, 45),
					new GameItem(1960, 46),
					new GameItem(1960, 47),
					new GameItem(1960, 48),
					new GameItem(1960, 49),
					new GameItem(1960, 50)
					)
	);
		
		items.put(Rarity.RARE,
				Arrays.asList(
						new GameItem(1960, 100),
						new GameItem(13307, 50)
						));
	}

	/**
	 * The player object that will be triggering this event
	 */
	private Player player;

	/**
	 * Constructs a new myster box to handle item receiving for this player and this player alone
	 * 
	 * @param player the player
	 */
	public HourlyRewardBox(Player player) {// HourlyRewardBox Object is in player.java that triggers this constructor
		this.player = player;
	}
	
	
	public void startEvent() {//starts event. Triggered through constructor
		if (player.disconnected || Objects.isNull(player)) {
			return;
		}
		if(player == null) {
			return;
		}
		CycleEventHandler.getSingleton().addEvent(this, this, 360000);//   (event,event,time) 3600
	}

	/**
	 * open reward box
	 */
	public void open() {
		if (System.currentTimeMillis() - player.lastMysteryBox < 600 * 4) {
			return;
		}
		if (player.getItems().freeSlots() < 2) {
			player.sendMessage("You need atleast two free slots to open a hourly box.");
			return;
		}
		if (!player.getItems().playerHasItem(MYSTERY_BOX)) {
			player.sendMessage("You need a hourly box to do this.");
			return;
		}
		player.getItems().deleteItem(MYSTERY_BOX, 1);
		player.lastMysteryBox = System.currentTimeMillis();
	}

	/**
	 * Executes the event for receiving the mystery box
	 */
	@Override
	public void execute(CycleEventContainer container) {//at end of event this triggers
		if (player.disconnected || Objects.isNull(player)) {
			container.stop();
			return;
		}
		if(player == null) {
			container.stop();
			return;
		}
		//int coins = 500_000 + Misc.random(1_500_000);
		//int coinsDouble = 500_000 + Misc.random(1_500_000);
		int random = Misc.random(100);
		List<GameItem> itemList = random < 55 ? items.get(Rarity.COMMON) : random >= 55 && random <= 90 ? items.get(Rarity.UNCOMMON) : items.get(Rarity.RARE);
		GameItem item = Misc.getRandomItem(itemList);
		GameItem itemDouble = Misc.getRandomItem(itemList);
		
		if (Misc.random(200) == 2 && player.getItems().getItemCount(19730, true) == 0 && player.summonId != 19730) {
			PlayerHandler.executeGlobalMessage("[<col=CC0000>Mbox</col>] @cr20@ <col=255>" + player.playerName
					+ "</col> hit the jackpot and got a <col=CC0000>Rainbow Afro</col> !");
			player.getItems().addItemUnderAnyCircumstance(12430, 1);
		}

		if (Misc.random(10) == 0) {
			//player.getItems().addItem(995, coins + coinsDouble);
			player.getItems().addItemUnderAnyCircumstance(item.getId(), item.getAmount());
			player.getItems().addItemUnderAnyCircumstance(itemDouble.getId(), itemDouble.getAmount());
			player.sendMessage("You have played for an hour, You receive a <col=255>" + item.getAmount() + " x " + ItemAssistant.getItemName(item.getId()) + "</col>.");
		player.sendMessage("You have played for an hour receive <col=255>" + itemDouble.getAmount() + " x " + ItemAssistant.getItemName(itemDouble.getId()) + "</col>.");
			PlayerHandler.executeGlobalMessage("<img=10>" + Misc.formatPlayerName(player.playerName) + " just got very lucky and hit the double!");
			PlayerHandler.executeGlobalMessage("<img=10>" + Misc.formatPlayerName(player.playerName) + " has received <col=255>" + item.getAmount() + " x " + ItemAssistant.getItemName(item.getId())
					+ "</col> and <col=255>" + itemDouble.getAmount() + " x " + ItemAssistant.getItemName(itemDouble.getId()) + "</col> from a hourly reward box.");
		} else {
			//player.getItems().addItem(995, coins);
			player.getItems().addItemUnderAnyCircumstance(item.getId(), item.getAmount());
			player.sendMessage("You have played for an hour, You receive a  <col=255>" + item.getAmount() + " x " + ItemAssistant.getItemName(item.getId()) + "</col>.");
			//PlayerHandler.executeGlobalMessage(
			//		"<img=10>" + Misc.formatPlayerName(player.playerName) + " has received <col=255>" + item.getAmount() + " x " + ItemAssistant.getItemName(item.getId()) + "</col> from a hourly reward box.");
		}
		container.stop();
		startEvent();
	}

	/**
	 * Represents the rarity of a certain list of items
	 */
	enum Rarity {
		UNCOMMON, COMMON, RARE
	}

}
