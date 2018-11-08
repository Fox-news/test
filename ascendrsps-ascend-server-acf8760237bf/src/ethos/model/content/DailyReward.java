package ethos.model.content;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import ethos.model.players.Player;

/*
 * This system gives players a reward once a day
 */

public class DailyReward {

	private Player p;// player object

	LocalDateTime now = LocalDateTime.now(); // gets servers date and time
	
	DateTimeFormatter time = DateTimeFormatter.ofPattern("H"); // gets hours
	
	YearMonth yearMonthObject = YearMonth.of(now.getYear(), now.getMonthValue());// gets current year and month
	
	YearMonth daysLastMonth = YearMonth.of(now.getYear(), now.getMonthValue()-1);// gets current year and last month
	
	int daysInMonth = yearMonthObject.lengthOfMonth(); // checks how many days are in the month
	
	int daysInLastMonth = daysLastMonth.lengthOfMonth(); // checks how many days are in the month
	
	int currentTime = Integer.parseInt(time.format(now));// gets current time of day

	int[] rewards = { 25, 50, 75, 100, 150, 200, 300, 500 };// day rewards
	
	
	
	
	

	public DailyReward(Player p) {// Constructor that sets players data
		this.p = p;
	}

	public void getDay() {
		if (now.getDayOfMonth() == p.lastDayClaimed) {//if already claimed reward today
			p.getPA().showInterface(65000);
			p.getPA().sendFrame126("Time Left: " + (24 - currentTime) + " hours left", 65023);
			return;
		} else {
			p.getPA().showInterface(65000);
			p.getPA().sendFrame126("Claim reward now", 65023);

		}
	}

	public void getReward() {
		if (now.getDayOfMonth() == p.lastDayClaimed) {//if player already claimed todays rewards
			p.getPA().closeAllWindows();
			p.sendMessage("You have already claimed todays reward. Try again tomorrow.");
			return;
		}
		
		
		
		
		else if(p.dailyRewardCombo >= 7) {//after finished 8 day challenge it resets...can add bonus for doing all 10 days here
			p.getItems().addItemUnderAnyCircumstance(1960, rewards[p.dailyRewardCombo]);
			p.dailyRewardCombo = 0;
			p.lastDayClaimed = now.getDayOfMonth();
			p.sendMessage("You have claimed all 8 days your combo has been reset");
			p.getPA().closeAllWindows();
			return;
		}
		
		//if the day is the first and the last time recieved reward was last day of last month
		else if(p.lastDayClaimed == daysInLastMonth && now.getDayOfMonth() == 1) {
			p.getItems().addItem(1960, rewards[p.dailyRewardCombo]);
			p.dailyRewardCombo += 1;
			p.lastDayClaimed = now.getDayOfMonth();
			p.sendMessage("You have now logged in for ("+p.dailyRewardCombo+")days in a row!");
			p.getPA().closeAllWindows();
			return;
		}
		
		//if the last day the player claimed the reward is the day before todays date will add onto players combo
		else if(p.lastDayClaimed == (now.getDayOfMonth()-1) && p.lastDayClaimed != daysInMonth) {
			p.getItems().addItem(1960, rewards[p.dailyRewardCombo]);
			p.dailyRewardCombo += 1;
			p.lastDayClaimed = now.getDayOfMonth();
			p.sendMessage("You have now logged in for ("+p.dailyRewardCombo+")days in a row!");
			p.getPA().closeAllWindows();
			return;
		} 
		
		//if player missed a day the combo ends
		else {
		p.dailyRewardCombo = 0;
		p.getItems().addItem(1960, rewards[0]);
		p.lastDayClaimed = now.getDayOfMonth();
		p.sendMessage("You have now logged in for ("+p.dailyRewardCombo+")days in a row!");
		p.getPA().closeAllWindows();
		}
	}

}// END OF CLASS
