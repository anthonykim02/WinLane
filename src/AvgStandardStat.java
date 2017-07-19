// class that stores the averages of each standard statistic

public class AvgStandardStat {
	
	// stats: k/d/a, cs/min, winrate, damage/min, damage%, kill participation, cs difference @20, enemy jungle cs
	double kda;
	double csmin;
	double wl;
	double dmin;
	double dmgP;
	double kp;
	double enemyjg;
	
	public AvgStandardStat(double kda, double csmin, double wl, double  dmin,
			double dmgP, double kp, double enemyjg) {
		this.kda = kda;
		this.csmin = csmin;
		this.wl = wl;
		this.dmin = dmin;
		this.dmgP = dmgP;
		this.kp = kp;
		this.enemyjg = enemyjg;
	}
	
	public void print() {
		System.out.println("Avg KDA: " + kda);
		System.out.println("Avg CS/Min: " + csmin);
		System.out.println("Winrate: " + wl);
		System.out.println("Avg DMG/Min: " + dmin);
		System.out.println("Avg DMG%: " + dmgP);
		System.out.println("Avg KP: " + kp);
		System.out.println("Avg Enemy JG CS: " + enemyjg);
	}
}
