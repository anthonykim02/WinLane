import java.util.Map;
import java.util.HashMap;

public class Match {
	
	Player player;
	Player[] allPlayers;
	String[] spell1;
	String[] spell2;
	
	// constructor; p stands for on player's team, e stands for on enemy team
	public Match(Player[] allPlayers, String[] spell1, String[] spell2) {
		this.allPlayers = new Player[10];
		this.spell1 = new String[10];
		this.spell2 = new String[10];
		
		for (int i = 0; i < allPlayers.length; i++) {
			Player p = allPlayers[i];
			if (p.isPlayer) {
				player = p;
			} 
			
			// sort allPlayers so you can get players by ID
			this.allPlayers[p.pId - 1] = p;
			this.spell1[p.pId - 1] = spell1[i];
			this.spell2[p.pId - 1] = spell2[i];
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
	
	
	// return true if player is in the desired role
	// if no role is specified then return true
	public boolean playerRightRole(String role) {
		if (player.role.equals(role) || role.equals("")) {
			return true;
		} else {
			return false;
		}
	}
	
	
	// return true if there are no repeat roles (if this is false don't account stats for the match)
	public boolean validRoles() {
		
		boolean top1 = false, top2 = false, jg1 = false, jg2 = false, mid1 = false, mid2 = false, 
				bot1 = false, bot2 = false, supp1 = false, supp2 = false, bottom1 = false, bottom2 = false;
		
		
		int numJg1 = 0;
		int numJg2 = 0;
		
		// get index of junglers
		int team1jg1 = -1;
		int team1jg2 = -1;
		int team2jg1 = -1;
		int team2jg2 = -1;
		
		int numBottom1 = 0;
		int numBottom2 = 0;
		
		// get index of bottoms
		int team1b1 = -1;
		int team1b2 = -1;
		int team2b1 = -1;
		int team2b2 = -1;
		
		
		// step 1: find number of jg and bottom as well as their indices
		for (int i = 0; i < allPlayers.length; i++) {
			Player p = allPlayers[i];
			if (p.role.equals("jungle") && p.team == 100) {
				// get indices of two junglers
				if (team1jg1 == -1 && team1jg2 == -1) {
					team1jg1 = i;
				} else if (team1jg1 != -1 && team1jg2 == -1) {
					team1jg2 = i;
				}
				numJg1++;
			} else if (p.role.equals("jungle") && p.team == 200) {
				// get indices of two junglers
				if (team2jg1 == -1 && team2jg2 == -1) {
					team2jg1 = i;
				} else if (team2jg1 != -1 && team2jg2 == -1) {
					team2jg2 = i;
				}
				numJg2++;
			} else if (p.role.equals("bottom") && p.team == 100) {
				// get indices of two bottoms
				if (team1b1 == -1 && team1b2 == -1) {
					team1b1 = i;
				} else if (team1b1 != -1 && team1b2 == -1) {
					team1b2 = i;
				}
				numBottom1++;
			} else if (p.role.equals("bottom") && p.team == 200) {
				// get indices of two bottoms
				if (team2b1 == -1 && team2b2 == -1) {
					team2b1 = i;
				} else if (team2b1 != -1 && team2b2 == -1) {
					team2b2 = i;
				}
				numBottom2++;
			}
		}
		
		// step 2: determine which roles are missing
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
		
		// step 3: based on which roles are missing, try to find the role of the extra player
		if (numJg1 == 2 && numBottom1 != 2) {
			Player j1 = allPlayers[team1jg1];
			String s11 = spell1[team1jg1];
			String s12 = spell2[team1jg1];
			
			Player j2 = allPlayers[team1jg2];
			String s21 = spell1[team1jg2];
			String s22 = spell2[team1jg2];
			
			
			if (!top1) {
				// first jg has smite and other jg does not
				if ((s11.equals("Smite") || s12.equals("Smite")) && (!s21.equals("Smite") && !s22.equals("Smite"))) {
					j2.role = "top";
					top1 = true;
				// second jg has smite and first does not
				} else if ((s21.equals("Smite") || s22.equals("Smite")) && (!s11.equals("Smite") && !s12.equals("Smite"))) {
					j1.role = "top";
					top1 = true;
				}
			} else if (!mid1) {
				// first jg has smite and other jg does not
				if ((s11.equals("Smite") || s12.equals("Smite")) && (!s21.equals("Smite") && !s22.equals("Smite"))) {
					j2.role = "mid";
					mid1 = true;
				// second jg has smite and first does not
				} else if ((s21.equals("Smite") || s22.equals("Smite")) && (!s11.equals("Smite") && !s12.equals("Smite"))) {
					j1.role = "mid";
					mid1 = true;
				}
				
			} else if (!bot1) {
				// first jg has smite and other jg does not
				if ((s11.equals("Smite") || s12.equals("Smite")) && (!s21.equals("Smite") && !s22.equals("Smite"))) {
					j2.role = "bot";
					bot1 = true;
				// second jg has smite and first does not
				} else if ((s21.equals("Smite") || s22.equals("Smite")) && (!s11.equals("Smite") && !s12.equals("Smite"))) {
					j1.role = "bot";
					bot1 = true;
				}
				
			} else if (!supp1) {
				// first jg has smite and other jg does not
				if ((s11.equals("Smite") || s12.equals("Smite")) && (!s21.equals("Smite") && !s22.equals("Smite"))) {
					j2.role = "supp";
					supp1 = true;
				// second jg has smite and first does not
				} else if ((s21.equals("Smite") || s22.equals("Smite")) && (!s11.equals("Smite") && !s12.equals("Smite"))) {
					j1.role = "supp";
					supp1 = true;
				}
				
			}
		} 
		
		if (numJg2 == 2 && numBottom2 != 2) {
			Player j1 = allPlayers[team2jg1];
			String s11 = spell1[team2jg1];
			String s12 = spell2[team2jg1];
			
			Player j2 = allPlayers[team2jg2];
			String s21 = spell1[team2jg2];
			String s22 = spell2[team2jg2];
			
			
			if (!top2) {
				// first jg has smite and other jg does not
				if ((s11.equals("Smite") || s12.equals("Smite")) && (!s21.equals("Smite") && !s22.equals("Smite"))) {
					j2.role = "top";
					top2 = true;
				// second jg has smite and first does not
				} else if ((s21.equals("Smite") || s22.equals("Smite")) && (!s11.equals("Smite") && !s12.equals("Smite"))) {
					j1.role = "top";
					top2 = true;
				}
			} else if (!mid2) {
				// first jg has smite and other jg does not
				if ((s11.equals("Smite") || s12.equals("Smite")) && (!s21.equals("Smite") && !s22.equals("Smite"))) {
					j2.role = "mid";
					mid2 = true;
				// second jg has smite and first does not
				} else if ((s21.equals("Smite") || s22.equals("Smite")) && (!s11.equals("Smite") && !s12.equals("Smite"))) {
					j1.role = "mid";
					mid2 = true;
				}
				
			} else if (!bot2) {
				// first jg has smite and other jg does not
				if ((s11.equals("Smite") || s12.equals("Smite")) && (!s21.equals("Smite") && !s22.equals("Smite"))) {
					j2.role = "bot";
					bot2 = true;
				// second jg has smite and first does not
				} else if ((s21.equals("Smite") || s22.equals("Smite")) && (!s11.equals("Smite") && !s12.equals("Smite"))) {
					j1.role = "bot";
					bot2 = true;
				}
				
			} else if (!supp2) {
				// first jg has smite and other jg does not
				if ((s11.equals("Smite") || s12.equals("Smite")) && (!s21.equals("Smite") && !s22.equals("Smite"))) {
					j2.role = "supp";
					supp2 = true;
				// second jg has smite and first does not
				} else if ((s21.equals("Smite") || s22.equals("Smite")) && (!s11.equals("Smite") && !s12.equals("Smite"))) {
					j1.role = "supp";
					supp2 = true;
				}
				
			}
		}
		
		if (numBottom1 == 2 && numJg1 != 2) {
			Player j1 = allPlayers[team1b1];
			String s11 = spell1[team1b1];
			String s12 = spell2[team1b1];
			
			Player j2 = allPlayers[team1b2];
			String s21 = spell1[team1b2];
			String s22 = spell2[team1b2];
			
			if ((s11.equals("Heal") || s12.equals("Heal")) && (!s21.equals("Heal") && !s22.equals("Heal"))) {
				j1.role = "bot";
				j2.role = "supp";
				bot1 = true;
				supp1 = true;
			} else if ((s21.equals("Heal") || s22.equals("Heal")) && (!s11.equals("Heal") && !s12.equals("Heal"))) {
				j1.role = "supp";
				j2.role = "bot";
				bot1 = true;
				supp1 = true;
			}	
		}
		
		if (numBottom2 == 2 && numJg2 !=2) {
			Player j1 = allPlayers[team2b1];
			String s11 = spell1[team2b1];
			String s12 = spell2[team2b1];
			
			Player j2 = allPlayers[team2b2];
			String s21 = spell1[team2b2];
			String s22 = spell2[team2b2];
			
			if ((s11.equals("Heal") || s12.equals("Heal")) && (!s21.equals("Heal") && !s22.equals("Heal"))) {
				j1.role = "bot";
				j2.role = "supp";
				bot2 = true;
				supp2 = true;
			} else if ((s21.equals("Heal") || s22.equals("Heal")) && (!s11.equals("Heal") && !s12.equals("Heal"))) {
				j1.role = "supp";
				j2.role = "bot";
				bot2 = true;
				supp2 = true;
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
