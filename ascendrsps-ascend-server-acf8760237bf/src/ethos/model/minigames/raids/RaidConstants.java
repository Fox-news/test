package ethos.model.minigames.raids;

import java.util.ArrayList;
import java.util.List;

import ethos.model.players.Player;

public class RaidConstants {

	public static boolean lobbyActive = false;//players cannot go into other raid rooms while this is true
	
	public static List <Player> raidLobby = new ArrayList<Player>();//gets players in lobby
	
	public static int raidLobbyTimer = 6;//how many seconds til timer starts
	
	public static int timeLeft = 0;//how much time is left until raid starts

	public static List<Player> getRaidLobby() {
		return raidLobby;
	}

	
	
	
	
	
	
	
}//END OF CLASS
