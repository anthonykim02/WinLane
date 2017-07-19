import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.util.Map;
import java.util.HashMap;

import org.json.*;

public class Main {
	
	// link to the riot api; should be used for every get request
	static String apiurl = "https://na1.api.riotgames.com/lol/";
	
	// key to use the api, may need to reuse (DO NOT use this for final product)
	static String apiKey = "RGAPI-6d39696c-ee69-48be-84e9-0a5a42ec3b42";
	
	
	
	// ***LANE COORDINATES FOR EACH RECTANGLE***
	// xtl, ytl, xtr, ytr, xbl, ybl, xbr, ybr (constants)
	
	// blue side top (two for extended lane)
	static locationCalculator blueTopMain = new locationCalculator(0, 14881, 3500, 14881, 0, 9400, 3500, 9400);
	static locationCalculator blueTopExtend = new locationCalculator(0, 14881, 1800, 14881, 0, 5900, 1800, 5900);
	
	// red side top (two)
	static locationCalculator redTopMain = new locationCalculator(0, 14881, 5200, 14881, 0, 11100, 5200, 11100);
	static locationCalculator redTopExtend = new locationCalculator(0, 14881, 8600, 14881, 0, 13100, 8600, 13100);
	
	// mid
	static locationCalculator mid = new locationCalculator(9200, 11400, 11400, 9200, 3400, 5600, 5600, 3400);
	
	// blue side bot (two)
	static locationCalculator blueBotMain = new locationCalculator(9650, 3850, 14820, 3850, 9650, 0, 14820, 0);
	static locationCalculator blueBotExtend = new locationCalculator(5900, 1800, 14820, 1800, 5900, 0, 14820, 0);
	
	// red side bot (two)
	static locationCalculator redBotMain = new locationCalculator(11300, 5600, 14820, 5600, 11300, 0, 14820, 0);
	static locationCalculator redBotExtend = new locationCalculator(13000, 9000, 14820, 9000, 13000, 0, 14820, 0);
	
	
	
	
	// *********************************************************************************************
	// MARK: MAKE HTML GET REQUESTS
	// *********************************************************************************************
	
	
	// get request from riot api, returns json in string format
	public static String getHTML(String urlToRead) throws Exception {
	      StringBuilder result = new StringBuilder();
	      URL url = new URL(urlToRead);
	      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	      conn.setRequestMethod("GET");
	      BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	      String line;
	      while ((line = rd.readLine()) != null) {
	         result.append(line);
	      }
	      rd.close();
	      return result.toString();
	}
	
	
	
	
	// *********************************************************************************************
	// MARK: MATCH INFO THAT DOES NOT INVOLVE SPECIFIC TIMES
	// *********************************************************************************************
	
	// return json string with match details (does NOT have time)
	public static JSONObject getMatchById(BigInteger matchId) throws Exception{
		String link = apiurl 
				+ "match/v3/matches/" + matchId
				+ "?api_key=" + apiKey;
		
		String match = getHTML(link);
		System.out.println(match);
		return new JSONObject(match);
	}
	
	
	// find participant ids, team ids, roles of all participants to create player object
	// all player objects used to create and then return match object
	public static Match createPlayers(JSONObject obj, int champId) throws Exception {
		JSONArray participants = obj.getJSONArray("participants");
		
		Player[] allPlayers = new Player[10];
		int playerTeam = 0;
		
		for (int i=0; i < 10; i++) {
			
			JSONObject participant = participants.getJSONObject(i);
			int teamId = participant.getInt("teamId");
			int pId = participant.getInt("participantId");
			int championId = participant.getInt("championId");
			
			// roles: SOLO, NONE, DUO_CARRY, DUO_SUPPORT
			// lanes: TOP, JUNGLE, MIDDLE, BOTTOM
			JSONObject timeline = participant.getJSONObject("timeline");
			String role = timeline.getString("role");
			String lane = timeline.getString("lane");
			
			// single string that represents role (not split into role and lane)
			String actualRole = "";
			if (role.equals("DUO_CARRY")) {
				actualRole = "bot";
			} else if (role.equals("DUO_SUPPORT")) {
				actualRole = "supp";
			} else if (lane.equals("MIDDLE")) {
				actualRole = "mid";
			} else {
				actualRole = lane.toLowerCase();
			}
			
			// create player object and set its variables
			Player p = new Player(pId, teamId, actualRole);
			
			if (championId == champId) {
				p.isPlayer = true;
				playerTeam = teamId;
			} else {
				p.isPlayer = false;
			}
			
			allPlayers[i] = p;
		}
		
		// set boolean values for whether or not player is on the same team as main player
		for (int i = 0; i < allPlayers.length; i++) {
			Player p = allPlayers[i];
			if (p.team == playerTeam) {
				p.onPlayerTeam = true;
			} else {
				p.onPlayerTeam = false;
			}
		}
		
		// create match object using player array
		Match m = new Match(allPlayers);
		
		return m;
	}

	
	// calculate standard statistics and add them to linked lists within standardstat object
	public static void addStandard(JSONObject obj, Match m, StandardStat ss) throws Exception{
		int pId = m.getMainPlayer().pId;
		
		JSONArray participants = obj.getJSONArray("participants");
		JSONObject participant = participants.getJSONObject(pId - 1);
		JSONObject stats = participant.getJSONObject("stats");
		JSONObject timeline = participant.getJSONObject("timeline");
		
		long gameDuration = obj.getLong("gameDuration");
		int teamId = participant.getInt("teamId");
		
		// ***WIN/LOSS***
		boolean wl = stats.getBoolean("win");
		
		// ***KDA***
		int kills = stats.getInt("kills");
		int assists = stats.getInt("assists");
		int deaths = stats.getInt("deaths");
		
		// ***KILL PARTICIPATION***
		// first get total kill/dmg values for entire team (damage used for later)
		int totalKills = 0;
		long totalDamage = 0;
		for (int i = 0; i < participants.length(); i++) {
			JSONObject p = participants.getJSONObject(i);
			if (p.getInt("teamId")  == teamId) {
				JSONObject pStat = p.getJSONObject("stats");
				totalKills += pStat.getInt("kills");
				totalDamage += pStat.getInt("totalDamageDealtToChampions");
			}
		}
		double kp = (double) (kills + assists) / (double) totalKills;
		double minutes = (double) gameDuration / 60.0;
		
		
		// ***CS/MIN***
		int totalcs = stats.getInt("totalMinionsKilled") + stats.getInt("neutralMinionsKilled");
		double cspm = (double) totalcs / minutes;
		
		// ***DAMAGE/MIN***
		long dmg = stats.getLong("totalDamageDealtToChampions");
		double dpm = (double) dmg / minutes;
		
		// ***DAMAGE %***
		double dp = (double) dmg / (double) totalDamage;
		
		// ***ENEMY JG CS***
		int enemyjg = stats.getInt("neutralMinionsKilledEnemyJungle");
		
		
		// add to list of standard stats for that champion
		ss.add(kills, deaths, assists, cspm, wl, dpm, dp, kp, enemyjg);
	}
	
	// *********************************************************************************************
	// MARK: COORDINATE CHECKING HELPER FUNCTIONS
	// *********************************************************************************************
	
	public static boolean checkTop(int x, int y) {
		boolean btm = blueTopMain.basicIsInside(x, y);
		boolean bte = blueTopExtend.basicIsInside(x, y);
		boolean rtm = redTopMain.basicIsInside(x, y);
		boolean rte = redTopExtend.basicIsInside(x, y);
		
		if (btm || bte || rtm || rte) {
			return true;
		}
		
		return false;
	}
	
	public static boolean checkJungle(int x, int y) {
		return !checkTop(x, y) && !checkMid(x, y) && !checkBot(x, y);
	}
	
	public static boolean checkMid(int x, int y) {
		return mid.complexIsInside(x, y);
	}
	
	public static boolean checkBot(int x, int y) {
		boolean bbm = blueBotMain.basicIsInside(x, y);
		boolean bbe = blueBotExtend.basicIsInside(x, y);
		boolean rbm = redBotMain.basicIsInside(x, y);
		boolean rbe = redBotExtend.basicIsInside(x, y);
		
		if (bbm || bbe || rbm || rbe) {
			return true;
		}
		
		return false;
	}
	
	// *********************************************************************************************
	// MARK: MATCH INFO INVOLVING TIME
	// *********************************************************************************************
	
	public static JSONObject getTimeline(BigInteger matchId) throws Exception {
		String link = apiurl 
				+ "match/v3/timelines/by-match/" + matchId
				+ "?api_key=" + apiKey;
		String timeline = getHTML(link);
		System.out.println(timeline);
		return new JSONObject(timeline);
	}

	
	// returns array length 2 with values 1 or 0 based on if solo kill/death happened 
	// (based on killer, victim, and LOCATION of the kill)
	public static int[] soloKill(Match m, int killerId, int victimId, JSONArray assists, int locationx, int locationy) throws Exception{
		
		Player player = m.getMainPlayer();
		
		boolean correctLocation = false;
		
		// first make sure that kill occurred within player's lane
		if (player.role.equals("top")) {
			correctLocation = checkTop(locationx, locationy);
		} else if (player.role.equals("jungle")) {
			correctLocation = checkJungle(locationx, locationy);
		} else if (player.role.equals("mid")) {
			correctLocation = checkMid(locationx, locationy);
		} else {
			correctLocation = checkBot(locationx, locationy);
		}
		
		
		// num solo kills
		int wasSK = 0;
		int wasSD = 0;
		
		if (correctLocation) {
			Player companion = null;
			Player oppositePlayer = m.getOppositePlayer(player);
			Player oppositePlayer2 = null;
			
			boolean botCheck = false;
			
			// bot lane can kill either bot or support and still call it "solo kill"
			if (player.role.equals("bot") || player.role.equals("supp")) {
				companion = m.getCompanionPlayer(player);
				oppositePlayer2 = m.getCompanionPlayer(oppositePlayer);
				botCheck = true;
			}
			
			int numAssists = assists.length();
			
			
			// first check if the main player is a bot laner or not
			if (botCheck) {
				
				// victim is bot laner
				if (victimId == oppositePlayer.pId || victimId == oppositePlayer2.pId) {
					
					// killer is player 
					if (killerId == player.pId) {
						
						// either no assists or whoever got the assist is other bot laner
						if (numAssists == 0 || (numAssists == 1 && assists.getInt(0) == companion.pId)) {
							wasSK++;
						}
						
					// killer is companion
					} else if (killerId == companion.pId) {
						
						// main player got the only assist
						if (numAssists == 1 && assists.getInt(0) == player.pId) {
							wasSK++;
						}
					}
					
				} 
				
				
				// switch killer and victim id requirements for solo death
				if (victimId == player.pId) {
					
					//killer can be either of the bot laners on enemy team
					if (killerId == oppositePlayer.pId) {
						
						// either no assits or other bot laner gets the assist
						if (numAssists == 0 || (numAssists == 1 && assists.getInt(0) == oppositePlayer2.pId)) {
							wasSD++;
						} 
					} else if (killerId == oppositePlayer.pId) {
						if (numAssists == 0 || (numAssists == 1 && assists.getInt(0) == oppositePlayer.pId)) {
							wasSD++;
						} 
					}
				}
			
			// if not bot laner all you need to check is that victim was opposite laner and there were 0 assists
			} else {
				if (numAssists == 0 && killerId == player.pId) {
					if (victimId == oppositePlayer.pId) {
						wasSK++;
					}
				}
				
				// switch killer and victim id requirements for solo death
				if (numAssists == 0 && killerId == oppositePlayer.pId) {
					if (victimId == player.pId) {
						wasSD++;
					}
				}
			}
		}
		
		int[] result = new int[2];
		result[0] = wasSK;
		result[1] = wasSD;
		
		return result;
	}
	
	public static int gank(Match m, int killerId, JSONArray assists, int locationx, int locationy) throws Exception {
		
		// 1 if top, 2 if mid, 3 if bot
		int wasGank = 0;
		
		Player player = m.getMainPlayer();
		
		boolean checkLocation = false;
		
		boolean bot = checkBot(locationx, locationy);
		boolean top = checkTop(locationx, locationy);
		boolean mid = checkMid(locationx, locationy);
		
		// make sure location was a lane, not the same lane as laner
		if (player.role.equals("top")) {
			checkLocation = bot || mid; 
		} else if (player.role.equals("jungle")) {
			checkLocation = bot || top || mid;
		} else if (player.role.equals("mid")) {
			checkLocation = bot || top;
		} else {
			checkLocation = top || mid;
		}
		
		if (checkLocation) {			
			
			if (killerId == player.pId) {
				
				// set this value only if gank was valid
				if (bot) {
					wasGank = 3;
				} else if (mid) {
					wasGank = 2;
				} else if (top) {
					wasGank = 1;
				}
				return wasGank;
			} else {
				for (int i = 0; i < assists.length(); i++) {
					int a = assists.getInt(i);
					if (a == player.pId) {
						
						// set this value only if gank was valid
						if (bot) {
							wasGank = 3;
						} else if (mid) {
							wasGank = 2;
						} else if (top) {
							wasGank = 1;
						}
						return wasGank;
					}
				}
			}
		}

		return wasGank;
	}
	
	public static String whoGotFirstTower(Match m, int teamId, String lane) throws Exception{
		
		String result = "";
		
		Player player = m.getMainPlayer();
		
		// check if tower is same lane as laner
		boolean topTurret = player.role.equals("top") && lane.equals("TOP_LANE");
		boolean midTurret = player.role.equals("mid") && lane.equals("MID_LANE");
		boolean botTurret = (player.role.equals("bot") || player.role.equals("supp")) && lane.equals("BOT_LANE");
		
		if (topTurret || midTurret || botTurret) {
			
			// check which team got the first tower
			if (teamId != player.team) {
				result = "player";
			} else {
				result = "enemy";
			}
		}
		
		return result;
	}

	public static void addPreTwentyStats(JSONObject timeline, Match m, PreTwentyStat pts) throws Exception {
		
		JSONArray frames = timeline.getJSONArray("frames");
		int numFrames = frames.length();
		
		// timeline: time of latest event; index: frame #
		int index = 0;
		JSONObject frameobj = frames.getJSONObject(index);
		long timestamp = frameobj.getLong("timestamp");
		
		Player player = m.getMainPlayer();
		
		
		// STATS TO CALCULATE
		
		// solo
		int numSoloKills = 0;
		int numSoloDeaths = 0;
		
		// @20 stats
		int csDiff = 0;
		int xpDiff = 0;
		int goldDiff = 0;
		
		// ganks
		int numMidKills = 0;
		int numTopKills = 0;
		int numBotKills = 0;
		int numMidGanks = 0;
		int numTopGanks = 0;
		int numBotGanks = 0;
		long firstGankTime = 0;
		boolean ganked = false; // checks for whether or not player has made a successful gank yet
		String laneOfLastKill = ""; // this is used to help check multiple kill were multiple ganks or not
		long timeOfLastGank = 0; // time last gank occurred
		long gankTimeThreshold = 0; // based on above, add 12 seconds; before this time is reached, any additional kills are counted as part of same gank
		
		
		// jungle monsters
		long riftHerald = 0;
		boolean firstRift = false;
		long dragon = 0;
		boolean firstDragInGame = false;
		boolean gotFirstDrag = false;
		
		// tower 
		boolean firstTower = false;
		boolean opponentGotTower = false;
		
		
		// timestamp is based on milliseconds; 20 min = 1,200,000 ms (add half a minute for sake of accuracy)
		// make sure timestamp begins at ~1min 30 or so to not include early game invades (unrelated to laning)
		while (timestamp < 1230000) {
			JSONArray events = frameobj.getJSONArray("events");
			// check events for a champion kill
			for (int i = 0; i < events.length(); i++) {
				
				JSONObject event = events.getJSONObject(i);
				String type = event.getString("type");
				
				if (type.equals("CHAMPION_KILL")) {
					
					
					// kill participants (kill, assist, death)
					JSONArray assists = event.getJSONArray("assistingParticipantIds");
					
					int killerId = event.getInt("killerId");
					int victimId = event.getInt("victimId");
					
					JSONObject position = event.getJSONObject("position");
					int locationx = position.getInt("x");
					int locationy = position.getInt("y");
					
					int[] kd = soloKill(m, killerId, victimId, assists, locationx, locationy);
					numSoloKills += kd[0];
					numSoloDeaths += kd[1];
					
					int g = gank(m, killerId, assists, locationx, locationy);
					
					// if gank was yours AND successful record the time of the gank
					if (g != 0) {
						timeOfLastGank = event.getLong("timestamp");
						
						// check if this was your first successful gank
						if (!ganked) {
							firstGankTime = timeOfLastGank;
							ganked = true;
						}
						
					}
					
					// increase appropriate lane counter
					if (g == 1) {
						numTopKills++;
						
						// check if kill was separate gank based on time; also check based on lane ganked
						if (timeOfLastGank > gankTimeThreshold || !laneOfLastKill.equals("top")) {
							numTopGanks++;
						}
						
						gankTimeThreshold = timeOfLastGank + 14000; // 14000 because 14 seconds (10 sec min death timer + extra leeway for tp)
						laneOfLastKill = "top";
						
					} else if (g == 2) {
						numMidKills++;
						
						// check if kill was separate gank based on time; also check based on lane ganked
						if (timeOfLastGank > gankTimeThreshold || !laneOfLastKill.equals("mid")) {
							numMidGanks++;
						}
						
						gankTimeThreshold = timeOfLastGank + 12000; // 12000 because 12 seconds (10 sec min death timer + extra leeway for tp)
						laneOfLastKill = "mid";
						
					} else if (g == 3) {
						numBotKills++;
						
						// check if kill was separate gank based on time; also check based on lane ganked
						if (timeOfLastGank > gankTimeThreshold || !laneOfLastKill.equals("bot")) {
							numBotGanks++;
						}
						
						gankTimeThreshold = timeOfLastGank + 12000; // 12000 because 12 seconds (10 sec min death timer + extra leeway for tp)
						laneOfLastKill = "bot";
						
					}
					
						
				}
				
				// only need to check this if player is a jungler
				if (player.role.equals("jungle")) {
					if (type.equals("ELITE_MONSTER_KILL")) {
						
						String monsterType = event.getString("monsterType"); 
						int killerId = event.getInt("killerId");
						Player killer = m.getPlayer(killerId);

						// check monster type and whether the first drag has been recorded or not
						if (monsterType.equals("RIFTHERALD")) {
							if (killer.onPlayerTeam) {
								riftHerald = event.getLong("timestamp");
								firstRift = true;
							}
						} else if ((monsterType.equals("DRAGON")) && !firstDragInGame) {
							if (killer.onPlayerTeam) {
								dragon = event.getLong("timestamp");
								gotFirstDrag = true;
							}
							firstDragInGame = true;
						}
					}
				
				// only check first tower for laner
				} else {
					
					// building kill occurs, lane both opponent and player have not gotten tower yet, tower destroyed is an outer turret
					if (type.equals("BUILDING_KILL") && !opponentGotTower && !firstTower && event.getString("towerType").equals("OUTER_TURRET")) {
						
						int killerId = event.getInt("killerId");
						JSONArray assists = event.getJSONArray("assistingParticipantIds");
						int towerTeam = event.getInt("teamId");
						
						String towerKiller = whoGotFirstTower(m, towerTeam, event.getString("laneType"));
						
						if (towerKiller.equals("player")) {
							firstTower = true;
						} else if (towerKiller.equals("enemy")) {
							opponentGotTower = true;
						} 
						// if neither than not a relevant tower kill
					}
				}
				
			}
			
			
			// specific check for 20 minute mark to check for diff @20 stats (will only happen once)
			if (timestamp >= 120000) {
				JSONObject participantFrames = frameobj.getJSONObject("participantFrames");
				
				// player's stats
				JSONObject playerFrame = participantFrames.getJSONObject(Integer.toString(player.pId));
				int playerCS = playerFrame.getInt("minionsKilled");
				int playerXP = playerFrame.getInt("xp");
				int playerGold = playerFrame.getInt("totalGold");
				
				// opponent's stats
				Player opp = m.getOppositePlayer(player);
				JSONObject oppFrame = participantFrames.getJSONObject(Integer.toString(opp.pId));
				int oppCS = oppFrame.getInt("minionsKilled");
				int oppXP = oppFrame.getInt("xp");
				int oppGold = oppFrame.getInt("totalGold");
				
				csDiff = playerCS - oppCS;
				xpDiff = playerXP - oppXP;
				goldDiff = playerGold - oppGold;
			}
			
			index++;
			// some games may not go to 20 minutes
			if (index < numFrames) {
				frameobj = frames.getJSONObject(index);
				timestamp = frameobj.getLong("timestamp");
			
			// in the case where game ends before 20 minutes, just get the latest difference values
			} else {
				
				// same code as above when getting difference stats, only difference is that the assumption is the time is 15-20 min
				JSONObject participantFrames = frameobj.getJSONObject("participantFrames");
				
				// player's stats
				JSONObject playerFrame = participantFrames.getJSONObject(Integer.toString(player.pId));
				int playerCS = playerFrame.getInt("minionsKilled");
				int playerXP = playerFrame.getInt("xp");
				int playerGold = playerFrame.getInt("totalGold");
				
				// opponent's stats
				Player opp = m.getOppositePlayer(player);
				JSONObject oppFrame = participantFrames.getJSONObject(Integer.toString(opp.pId));
				int oppCS = oppFrame.getInt("minionsKilled");
				int oppXP = oppFrame.getInt("xp");
				int oppGold = oppFrame.getInt("totalGold");
				
				csDiff = playerCS - oppCS;
				xpDiff = playerXP - oppXP;
				goldDiff = playerGold - oppGold;
				
				timestamp = 1230000;
			}
			
		}
		
		pts.add(numSoloKills, numSoloDeaths, csDiff, xpDiff, goldDiff, numTopKills, numMidKills, numBotKills, numTopGanks, numMidGanks, numBotGanks, firstGankTime, firstTower, dragon, gotFirstDrag, riftHerald, firstRift);
	}
	
	// *********************************************************************************************
	// MARK: INITIAL POINT OF STAT SEARCH
	// ********************************************************************************************
	
	// use result of this function as parameters to search function
	public static void getCurrentGame() {
		
	}
	
	
	// find statistics on games as a certain champion on certain role
	public static void search(long accountId, int champNum, int season) throws Exception {
		
		String matchRequest = "https://na1.api.riotgames.com/lol/match/v3/matchlists/"
				+ "by-account/" + accountId
				+ "?queue=420"	// 420 is the queueid for solo queue
				+ "&season=" + season 
				+ "&champion=" + champNum
				+ "&api_key=" + apiKey;
		String result = getHTML(matchRequest);
		JSONObject obj = new JSONObject(result);
		JSONArray matchlist = obj.getJSONArray("matches");
		int numMatches = obj.getInt("totalGames");
		System.out.println(numMatches);
		
		// after you get the matchlist, iterate through the matches; only go deeper
		// if the role is correct
		
		StandardStat ss = new StandardStat();
		PreTwentyStat pts = new PreTwentyStat();
		
		int matchesToCheck = 20;
		
		// possibility of having less than 10 solo queue games on a champion
		if (matchesToCheck > numMatches) {
			matchesToCheck = numMatches;
		}
		
		for (int i = 0; i < matchesToCheck; i++) {
			long id = matchlist.getJSONObject(i).getLong("gameId");
			BigInteger matchId = new BigInteger(Long.toString(id));
			JSONObject matchobj = getMatchById(matchId);
			int matchDuration = matchobj.getInt("gameDuration");
			
			Match m = createPlayers(matchobj, champNum);
			
			// check for valid roles and match duration of at least 15
			if (m.validRoles() && matchDuration >= 900) {
				addStandard(matchobj, m, ss);
				
				JSONObject t = getTimeline(matchId);
				addPreTwentyStats(t, m, pts);
				
			//skip if roles are not assigned properly; make sure not to keep trying to fill 10 games if there are less matches
			} else if (numMatches != matchesToCheck) {
				matchesToCheck++;
			}
			
		}
		
//		AvgStandardStat ass = ss.findAverage();
//		ass.print();
//		
//		AvgPreTwentyStat apts = pts.findAverage();
//		apts.print();
		
		// @DAVID
		// THE NUMBER WITHIN THE PARAMETER IS THE GAME NUMBER (SEE #2 OF THE INSTRUCTIONS
		ss.printGameStandard(1);
		pts.printGamePreTwenty(1);
		
	}
	
	public static void main(String[] args) throws Exception{		
		
		/*
		 *  FOR DAVID: TESTING INSTRUCTIONS:
		 *  DON'T MIND THE CODE ABOVE, JUST STICK TO THIS PART OF THE MAIN FUNCTION
		 *  1. LOOK UP SOME GAMES (5 GAMES WOULD BE ENOUGH) ON OP.GG FOR A CERTAIN CHAMPION YOU PLAY, MAKE SURE THESE ARE SOLO QUEUE GAMES
		 *  2. FIND HOW RECENT IT IS AND USE THAT NUMBER AS THE PARAMETER OF THE TWO PRINTGAME FUNCTIONS
		 *  	FOR EX: MOST RECENT GAME IS #1, NEXT RECENT IS #2 AND SO ON..
		 *  	YOU CAN FIND THE CALLS OF THE PRINTGAME FUNCTION WITHIN THE FUNCTION TITLED "SEARCH" WHICH IS RIGHT ABOVE THE MAIN (I COMMENTED @DAVID ABOVE IT)
		 *  3. ON THE VERY BOTTOM OF THIS FUNCTION (MAIN) YOU FIND THE SEARCH FUNCTION CALL. CHANGE THE PARAMETERS BASED ON THE GAME YOU'RE LOOKING UP
		 *  4. LOOK AT RESULTS AND COMPARE IT WITH THE ACTUAL GAME RESULTS (YOU WILL HAVE TO WATCH THE GAME REPLAY TO MAKE SURE THE STATS WORK)
		 *  5. IF ANY STAT IS WRONG, OR ANYTHING IS CONFUSING, OR THE CODE CRASHES (LAST ONE IS ESPECIALLY IMPORTANT) TAKE NOT OF IT AND CONTINUE WORKING
		 *  6. POST ON THE GROUP CHAT WITH THE RESULTS AFTER ALL THE TESTING IN COMPLETE
		 *  
		 *  
		 *  THERE WILL BE MORE COMMENTS BELOW TO HELP YOU 
		 */
		
		// SOME CHAMP NUMS:
		// 39 irelia 33 rammus 113 sejuani 90 malzahar 412 thresh
		//69 cass 157 yasuo 102 shyvana 105 fizz 83 yorick 
		// 59 jarvan 45 veigar 36 mundo 18 trist 63 brand
		//64 lee sin 2 olaf 76 nidalee 412 thresh
		
		// SOME ACCOUNT IDS:
		// 218887656 anthony
		// 219253169 andrew
		// 216255780 david
		// 219224164 kevin 
		// 41236595 edar
		
		// RUN SEARCH FUNCTION
		// PARAMETERS: ACCOUNTID, CHAMP NUM, SEASON
		// SEASON SHOULD ALWAYS BE ON 9, BUT REST YOU SHOULD CHANGE BASED ON WHAT YOU'RE LOOKING FOR
		search(218887656, 39, 9);
		
		
	}
}
