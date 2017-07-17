import java.util.*;

public class StandardStat {
	// stats: k/d/a, cs/min, winrate, damage/min, damage%, kill participation, cs difference @20
	
	LinkedList<Double> kda;
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
		kda = new LinkedList();
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
	
	public void add(double kdaVal, double csminVal, boolean wlVal, double dminVal, double dmgPval, 
			double dmgtVal, double xpdVal, double kpVal, double csd20Val, int enemyjgVal) {
		kda.add(kdaVal);
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
		
		int numGames = kda.size();
		
		double totalKda = 0;
		double totalCSmin = 0;
		double totalWins = 0;
		double totalDPM = 0;
		double totalDP = 0;
		// add xp and dmgt
		
		
		double totalKP = 0;
		double totalCSDiff = 0;
		double totalJgCS = 0;
		
		// get stat totals
		for (int i = 0; i < numGames; i++) {
			totalKda += kda.get(i);
			totalCSmin += csmin.get(i);
			totalDPM += dmin.get(i);
			totalDP += dmgP.get(i);
			totalKP += kp.get(i);
			totalCSDiff += csd20.get(i);
			totalJgCS += enemyjg.get(i);
			
			if (winOrLoss.get(i)) {
				totalWins++;
			}
		}
		
		// average out
		double avgKda = totalKda / numGames;
		double avgCSmin = totalCSmin / numGames;
		double avgWins = totalWins / numGames;
		double avgDPM = totalDPM / numGames;
		double avgDP = totalDP / numGames;
		double avgKP = totalKP / numGames;
		double avgCSDiff = totalCSDiff / numGames;
		double avgJgCS = totalJgCS / numGames;
		
		ass = new AvgStandardStat(avgKda, avgCSmin, avgWins, avgDPM, avgDP, avgKP, avgCSDiff, avgJgCS);
		
		return ass;
	}
	
	public void printLast() {
		System.out.println("KDA: " + kda.getLast());
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
