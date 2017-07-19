import java.util.*;

public class StandardStat {
	// stats: k/d/a, cs/min, winrate, damage/min, damage%, kill participation, cs difference @20
	
	LinkedList<Integer> kills;
	LinkedList<Integer> deaths;
	LinkedList<Integer> assists;
	LinkedList<Double> csmin;
	LinkedList<Boolean> winOrLoss;
	LinkedList<Double> dmin;
	LinkedList<Double> dmgP;
	LinkedList<Double> kp;
	LinkedList<Integer> enemyjg;
	
//	double dminDiff;
	
	public StandardStat() {
		kills = new LinkedList();
		deaths = new LinkedList();
		assists = new LinkedList();
		csmin = new LinkedList();
		winOrLoss = new LinkedList();
		dmin = new LinkedList();
		dmgP = new LinkedList();
		kp = new LinkedList();
		enemyjg = new LinkedList();
	}
	
	public void add(int killVal, int deathVal, int assistVal, double csminVal, boolean wlVal, 
			double dminVal, double dmgPval, double kpVal, int enemyjgVal) {
		kills.add(killVal);
		deaths.add(deathVal);
		assists.add(assistVal);
		csmin.add(csminVal);
		winOrLoss.add(wlVal);
		dmin.add(dminVal);
		dmgP.add(dmgPval);
		kp.add(kpVal);
		enemyjg.add(enemyjgVal);
	}

	public AvgStandardStat findAverage() {
		AvgStandardStat ass;
		
		int numGames = kills.size();
		
		double totalKills = 0;
		double totalDeaths = 0;
		double totalAssists = 0;
		double totalCSmin = 0;
		double totalWins = 0;
		double totalDPM = 0;
		double totalDP = 0;
		double totalKP = 0;
		double totalJgCS = 0;
		
		// get stat totals
		for (int i = 0; i < numGames; i++) {
			totalKills += kills.get(i);
			totalDeaths += deaths.get(i);
			totalAssists += assists.get(i);
			totalCSmin += csmin.get(i);
			totalDPM += dmin.get(i);
			totalDP += dmgP.get(i);
			totalKP += kp.get(i);
			totalJgCS += enemyjg.get(i);
			
			if (winOrLoss.get(i)) {
				totalWins++;
			}
		}
		
		// average out
		double avgKda = (totalKills + totalAssists) / totalDeaths;
		double avgCSmin = totalCSmin / numGames;
		double avgWins = totalWins / numGames;
		double avgDPM = totalDPM / numGames;
		double avgDP = totalDP / numGames;
		double avgKP = totalKP / numGames;
		double avgJgCS = totalJgCS / numGames;
		
		ass = new AvgStandardStat(avgKda, avgCSmin, avgWins, avgDPM, avgDP, avgKP, avgJgCS);
		
		return ass;
	}
	
	// use this as a test function, parameter is order it appears on op.gg
	public void printGameStandard(int gameNum) {
		if (gameNum > kills.size() || gameNum <= 0) {
			System.out.println("Game Num is either too small or large");
		} else {
			System.out.println("Kills: " + kills.get(gameNum - 1));
			System.out.println("Deaths: " + deaths.get(gameNum - 1));
			System.out.println("Assists: " + assists.get(gameNum - 1));
			System.out.println("CS/min: " + csmin.get(gameNum - 1));
			System.out.println("Win Or Loss: " + winOrLoss.get(gameNum - 1));
			System.out.println("DMG/min: " + dmin.get(gameNum - 1));
			System.out.println("DMG%: " + dmgP.get(gameNum - 1));
			System.out.println("KP: " + kp.get(gameNum - 1));
			System.out.println("EnemyJG CS: " + enemyjg.get(gameNum - 1));
		}
	}
	
	public void printLast() {
		System.out.println("Kills: " + kills.getLast());
		System.out.println("Deaths: " + deaths.getLast());
		System.out.println("Assists: " + assists.getLast());
		System.out.println("CS/min: " + csmin.getLast());
		System.out.println("Win Or Loss: " + winOrLoss.getLast());
		System.out.println("DMG/min: " + dmin.getLast());
		System.out.println("DMG%: " + dmgP.getLast());
		System.out.println("KP: " + kp.getLast());
		System.out.println("EnemyJG CS: " + enemyjg.getLast());
	}
}
