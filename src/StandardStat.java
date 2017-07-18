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
	LinkedList<Double> dmgt20;
	LinkedList<Double> xpd20;
	LinkedList<Double> kp;
	LinkedList<Double> csd20;
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
		dmgt20 = new LinkedList();
		xpd20 = new LinkedList();
		kp = new LinkedList();
		csd20 = new LinkedList();
		enemyjg = new LinkedList();
	}
	
	public void add(int killVal, int deathVal, int assistVal, double csminVal, boolean wlVal, double dminVal, double dmgPval, 
			double dmgtVal, double xpdVal, double kpVal, double csd20Val, int enemyjgVal) {
		kills.add(killVal);
		deaths.add(deathVal);
		assists.add(assistVal);
		csmin.add(csminVal);
		winOrLoss.add(wlVal);
		dmin.add(dminVal);
		dmgP.add(dmgPval);
		dmgt20.add(dmgtVal);
		xpd20.add(xpdVal);
		kp.add(kpVal);
		csd20.add(csd20Val);
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
		double totalDT = 0;
		double totalXP = 0;
		double totalKP = 0;
		double totalCSDiff = 0;
		double totalJgCS = 0;
		
		// get stat totals
		for (int i = 0; i < numGames; i++) {
			totalKills += kills.get(i);
			totalDeaths += deaths.get(i);
			totalAssists += assists.get(i);
			totalCSmin += csmin.get(i);
			totalDPM += dmin.get(i);
			totalDP += dmgP.get(i);
			totalDT += dmgt20.get(i);
			totalXP += xpd20.get(i);
			totalKP += kp.get(i);
			totalCSDiff += csd20.get(i);
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
		double avgDT = totalDT / numGames;
		double avgXP = totalXP / numGames;
		double avgKP = totalKP / numGames;
		double avgCSDiff = totalCSDiff / numGames;
		double avgJgCS = totalJgCS / numGames;
		
		ass = new AvgStandardStat(avgKda, avgCSmin, avgWins, avgDPM, avgDP, avgDT, avgXP, avgKP, avgCSDiff, avgJgCS);
		
		return ass;
	}
	
	public void printLast() {
		System.out.println("Kills: " + kills.getLast());
		System.out.println("Deaths: " + deaths.getLast());
		System.out.println("Assists: " + assists.getLast());
		System.out.println("CS/min: " + csmin.getLast());
		System.out.println("Win Or Loss: " + winOrLoss.getLast());
		System.out.println("DMG/min: " + dmin.getLast());
		System.out.println("DMG%: " + dmgP.getLast());
		System.out.println("DMGtaken @20: " + dmgt20.getLast());
		System.out.println("XPdiff @20: " + xpd20.getLast());
		System.out.println("KP: " + kp.getLast());
		System.out.println("CSdiff @20: " + csd20.getLast());
		System.out.println("EnemyJG CS: " + enemyjg.getLast());
	}
}
