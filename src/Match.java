import java.util.Map;
import java.util.HashMap;

public class Match {
	
	Player player;
	Player[] allPlayers;
	
	// constructor; p stands for on player's team, e stands for on enemy team
	public Match(Player[] allPlayers) {
		this.allPlayers = new Player[10];
		
		for (int i = 0; i < allPlayers.length; i++) {
			Player p = allPlayers[i];
			if (p.isPlayer) {
				player = p;
			} 
			
			// sort allPlayers so you can get players by ID
			this.allPlayers[p.pId - 1] = p;
		}
	}
	
	public Player getPlayer(int id) {
		return allPlayers[id - 1];
	}
	
	public Player getMainPlayer() {
		return player;
	}
	
	public Player getOppositePlayer(Player p) {
		Player result = null;
		for (int i = 0; i < allPlayers.length; i++) {
			Player p2 = allPlayers[i];
			if (p2.team != p.team && p2.role.equals(p.role)) {
				result = p2;
				break;
			}
		}
		return result;
	}
	
	public Player getCompanionPlayer(Player p) {
		Player result = null;
		String desiredRole = "";
		
		if (p.role.equals("bot")) {
			desiredRole = "supp";
		} else if (p.role.equals("supp")) {
			desiredRole = "bot";
		} else {
			return result;
		}
		
		for (int i = 0; i < allPlayers.length; i++) {
			Player p2 = allPlayers[i];
			if (p2.team == p.team && p2.role.equals(desiredRole)) {
				result = p2;
				break;
			}
		}
		
		return result;
	}
	
	
	// return ture if there are no repeat roles (if this is false don't account stats for the match)
	public boolean validRoles() {
		
		boolean top1 = false, top2 = false, jg1 = false, jg2 = false, mid1 = false, mid2 = false, 
				bot1 = false, bot2 = false, supp1 = false, supp2 = false;
		
		for (int i = 0; i < allPlayers.length; i++) {
			Player p = allPlayers[i];
			if (p.role.equals("top")) {
				if (p.team == 100) {
					top1 = true;
				} else {
					top2 = true;
				}
			} else if (p.role.equals("jungle")) {
				if (p.team == 100) {
					jg1 = true;
				} else {
					jg2 = true;
				}
			} else if (p.role.equals("mid")) {
				if (p.team == 100) {
					mid1 = true;
				} else {
					mid2 = true;
				}
			} else if (p.role.equals("bot")) {
				if (p.team == 100) {
					bot1 = true;
				} else {
					bot2 = true;
				}
			} else if (p.role.equals("supp")) {
				if (p.team == 100) {
					supp1 = true;
				} else {
					supp2 = true;
				}
			}
		}
		
		return (top1 && top2 && jg1 && jg2 && mid1 && mid2 && bot1 && bot2 && supp1 && supp2);
	}
	
	public void printMain() {
		System.out.println("id: " + player.pId + " role: " + player.role + " team: " + player.team);
	}
	public void printAll() {
		
		for (int i = 0; i < allPlayers.length; i++) {
			Player p = allPlayers[i];
			System.out.println("id: " + p.pId + " role: " + p.role + " team: " + p.team);
		}
	}
	
	
}
