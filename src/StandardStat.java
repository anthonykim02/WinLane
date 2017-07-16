import java.util.*;

public class StandardStat {
	// stats: k/d/a, cs/min, winrate, damage/min, damage%, kill participation, cs difference @20
	
	LinkedList<Double> kda;
	LinkedList<Double> csmin;
	LinkedList<Boolean> winOrLoss;
	LinkedList<Double> dmin;
	LinkedList<Double> dmgP;
	LinkedList<Double> kp;
	LinkedList<Double> csd20;
//	double dminDiff;
	
	public StandardStat() {
		kda = new LinkedList();
		csmin = new LinkedList();
		winOrLoss = new LinkedList();
		dmin = new LinkedList();
		dmgP = new LinkedList();
		kp = new LinkedList();
		csd20 = new LinkedList();
	}
	
	public void add(double kdaVal, double csminVal, boolean wlVal, 
			double dminVal, double dmgPval, double kpVal, double csd20Val) {
		kda.add(kdaVal);
		csmin.add(csminVal);
		winOrLoss.add(wlVal);
		dmin.add(dminVal);
		dmgP.add(dmgPval);
		kp.add(kpVal);
		csd20.add(csd20Val);
	}

	public AvgStandardStat findAverage() {
		AvgStandardStat ass;
		
		int numGames = kda.size();
		
		double totalKda = 0;
		double totalCSmin = 0;
		double totalWins = 0;
		double totalDPM = 0;
		double totalDP = 0;
		double totalKP = 0;
		double totalCSDiff = 0;
		
		// get stat totals
		for (int i = 0; i < numGames; i++) {
			totalKda += kda.get(i);
			totalCSmin += csmin.get(i);
			totalDPM += dmin.get(i);
			totalDP += dmgP.get(i);
			totalKP += kp.get(i);
			totalCSDiff += csd20.get(i);
			
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
		
		ass = new AvgStandardStat(avgKda, avgCSmin, avgWins, avgDPM, avgDP, avgKP, avgCSDiff);
		
		return ass;
	}
	
	public void printLast() {
		System.out.println(kda.getLast());
		System.out.println(csmin.getLast());
		System.out.println(winOrLoss.getLast());
		System.out.println(dmin.getLast());
		System.out.println(dmgP.getLast());
		System.out.println(kp.getLast());
		System.out.println(csd20.getLast());
	}
}
