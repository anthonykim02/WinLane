

public class AvgPreTwentyStat {
	double sk;
	double sd;
	double topk;
	double midk;
	double botk;
	double topg;
	double midg;
	double botg;
	double firstg;
	double firstt;
	double drag;
	double firstd;
	double rift;
	double firstr;
	
	public AvgPreTwentyStat(double sk, double sd, double topk, double midk, double botk, double topg, double midg, 
			double botg, double firstg, double firstt, double drag, double firstd, double rift, double firstr) {
		this.sk = sk;
		this.sd = sd;
		this.topk = topk;
		this.midk = midk;
		this.botk = botk; 
		this.topg = topg;
		this.midg = midg;
		this.botg = botg;
		this.firstg = firstg;
		this.firstt = firstt;
		this.drag = drag;
		this.firstd = firstd;
		this.rift = rift;
		this.firstr = firstr;
	}
	
	public void print() {
		System.out.println("Avg Solo Kills: " + sk);
		System.out.println("Avg Solo Deaths: " + sd);
		System.out.println("Avg Kills Contributed off Top Ganks: " + topk);
		System.out.println("Avg Kills Contributed off Mid Ganks: " + midk);
		System.out.println("Avg Kills Contributed off Bot Ganks: " + botk);
		System.out.println("Avg Top Ganks: " + topg);
		System.out.println("Avg Mid Ganks: " + midg);
		System.out.println("Avg Bot Ganks: " + botg);
		System.out.println("Avg First Gank Time: " + firstg);
		System.out.println("First Turret of Lane %: " + firstt);
		System.out.println("Avg First Drag Time: " + drag);
		System.out.println("First Drag %: " + firstd);
		System.out.println("Avg First Rift Time: " + rift);
		System.out.println("First Rift %: " + firstr);
		
	}
}
