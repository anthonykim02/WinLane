// class that stores the averages of each standard statistic

public class AvgStandardStat {
	
	// stats: k/d/a, cs/min, winrate, damage/min, damage%, kill participation, cs difference @20, enemy jungle cs
	double kda;
	double csmin;
	double wl;
	double dmin;
	double dmgP;
	double dmgT;
	double xpd;
	double kp;
	double csd20;
	double enemyjg;
	
	public AvgStandardStat(double kda, double csmin, double wl, double  dmin,
			double dmgP, double dmgT, double xpd, double kp, double csd20, double enemyjg) {
		this.kda = kda;
		this.csmin = csmin;
		this.wl = wl;
		this.dmin = dmin;
		this.dmgP = dmgP;
		this.dmgT = dmgT;
		this.xpd = xpd;
		this.kp = kp;
		this.csd20 = csd20;
		this.enemyjg = enemyjg;
	}
	
	public void print() {
		System.out.println("Avg KDA: " + kda);
		System.out.println("Avg CS/Min: " + csmin);
		System.out.println("Winrate: " + wl);
		System.out.println("Avg DMG/Min: " + dmin);
		System.out.println("Avg DMG%: " + dmgP);
		System.out.println("Avg DMG Taken @20: " + dmgT);
		System.out.println("Avg EXP Diff @20: " + xpd);
		System.out.println("Avg KP: " + kp);
		System.out.println("Avg CS Diff @20: " + csd20);
		System.out.println("Avg Enemy JG CS: " + enemyjg);
	}
}
