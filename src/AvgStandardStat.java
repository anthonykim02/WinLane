// class that stores the averages of each standard statistic

public class AvgStandardStat {
	
	// stats: k/d/a, cs/min, winrate, damage/min, damage%, kill participation, cs difference @20, enemy jungle cs
	double kda;
	double csmin;
	double wl;
	double dmin;
	double dmgP;
	double kp;
	double csd20;
	double enemyjg;
	
	public AvgStandardStat(double kda, double csmin, double wl, double  dmin,
			double dmgP, double kp, double csd20, double enemyjg) {
		this.kda = kda;
		this.csmin = csmin;
		this.wl = wl;
		this.dmin = dmin;
		this.dmgP = dmgP;
		this.kp = kp;
		this.csd20 = csd20;
		this.enemyjg = enemyjg;
	}
}
