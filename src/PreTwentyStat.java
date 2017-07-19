import java.util.LinkedList;

public class PreTwentyStat {
	
	LinkedList<Integer> solokill;
	LinkedList<Integer> solodeath;
	LinkedList<Integer> csdiff;
	LinkedList<Integer> xpdiff;
	LinkedList<Integer> golddiff;
	LinkedList<Integer> topkill;
	LinkedList<Integer> midkill;
	LinkedList<Integer> botkill;
	LinkedList<Integer> topgank;
	LinkedList<Integer> midgank;
	LinkedList<Integer> botgank;
	LinkedList<Long> firstgank;
	LinkedList<Boolean> firstturret;
	LinkedList<Long> drag;
	LinkedList<Boolean> firstDrag;
	LinkedList<Long> rift;
	LinkedList<Boolean> firstRift;
	
	public PreTwentyStat() {
		solokill = new LinkedList();
		solodeath = new LinkedList();
		csdiff = new LinkedList();
		xpdiff = new LinkedList();
		golddiff = new LinkedList();
		topkill = new LinkedList();
		midkill = new LinkedList();
		botkill = new LinkedList();
		topgank = new LinkedList();
		midgank = new LinkedList();
		botgank = new LinkedList();
		firstgank = new LinkedList();
		firstturret = new LinkedList();
		drag = new LinkedList();
		firstDrag = new LinkedList();
		rift = new LinkedList();
		firstRift = new LinkedList();
	}
	
	public void add(int skVal, int sdVal, int csdiffVal, int xpdiffVal, int golddiffVal, int topkVal, int midkVal, int botkVal, int topgVal, 
			int midgVal, int botgVal, long fgVal, boolean ftVal, long dragVal, boolean fdVal, long riftVal, boolean frVal) {
		solokill.add(skVal);
		solodeath.add(sdVal);
		csdiff.add(csdiffVal);
		xpdiff.add(xpdiffVal);
		golddiff.add(golddiffVal);
		topkill.add(topkVal);
		midkill.add(midkVal);
		botkill.add(botkVal);
		topgank.add(topgVal);
		midgank.add(midgVal);
		botgank.add(botgVal);
		firstgank.add(fgVal);
		firstturret.add(ftVal);
		drag.add(dragVal);
		firstDrag.add(fdVal);
		rift.add(riftVal);
		firstRift.add(frVal);
	}
	
	public AvgPreTwentyStat findAverage() {
		AvgPreTwentyStat apts;
		
		int numGames = solokill.size();
		
		// these are used so the 0's from games without getting these objectives doesn't skew the avg
		int gamesWithFirstDrag = 0;
		int gamesWithRift = 0;
		
		double totalSK = 0;
		double totalSD = 0;
		double totalCSD = 0;
		double totalXPD = 0;
		double totalGD = 0;
		double totalTopK = 0;
		double totalMidK = 0;
		double totalBotK = 0;
		double totalTopG = 0;
		double totalMidG = 0;
		double totalBotG = 0;
		double totalFirstG = 0;
		double totalFirstT = 0;
		double totalDrag = 0;
		double totalFirstD = 0;
		double totalRift = 0;
		double totalFirstR = 0;
		
		// get stat totals
		for (int i = 0; i < numGames; i++) {
			
			totalSK += solokill.get(i);
			totalSD += solodeath.get(i);
			totalCSD += csdiff.get(i);
			totalXPD += xpdiff.get(i);
			totalGD += golddiff.get(i);
			totalTopK += topkill.get(i);
			totalMidK += midkill.get(i);
			totalBotK += botkill.get(i);
			totalTopG += topgank.get(i);
			totalMidG += midgank.get(i);
			totalBotG += botgank.get(i);
			totalFirstG += firstgank.get(i);
			
			
			// rift and drag times
			long d = drag.get(i);
			long r = rift.get(i);
			
			if (d != 0) {
				gamesWithFirstDrag++;
			}
			if (r != 0) {
				gamesWithRift++;
			}
			
			totalDrag += d;
			totalRift += r;
			
			
			// percentages
			if (firstturret.get(i)) {
				totalFirstT++;
			}
			
			if (firstDrag.get(i)) {
				totalFirstD++;
			}
			
			if (firstRift.get(i)) {
				totalFirstR++;
			}
		}
		
		// average out
		double avgSK = totalSK / numGames;
		double avgSD = totalSD / numGames;
		double avgCSD = totalCSD / numGames;
		double avgXPD = totalXPD / numGames;
		double avgGD = totalGD / numGames;
		double avgTopK = totalTopK / numGames;
		double avgMidK = totalMidK / numGames;
		double avgBotK = totalBotK / numGames;
		double avgTopG = totalTopG / numGames;
		double avgMidG = totalMidG / numGames;
		double avgBotG = totalBotG / numGames;
		double avgFirstG = totalFirstG / numGames;
		double avgFirstT = totalFirstT / numGames;
		double avgDrag = totalDrag / gamesWithFirstDrag;
		double avgFirstD = totalFirstD / numGames;
		double avgRift = totalRift / gamesWithRift;
		double avgFirstR = totalFirstR / numGames;
		
		
		apts = new AvgPreTwentyStat(avgSK, avgSD, avgCSD, avgXPD, avgGD, avgTopK, avgMidK, avgBotK, avgTopG, 
				avgMidG, avgBotG, avgFirstG, avgFirstT, avgDrag, avgFirstD, avgRift, avgFirstR);
		
		return apts;
	}
	
	public void printGamePreTwenty(int gameNum) {
		if (gameNum > solokill.size() || gameNum <= 0) {
			System.out.println("Game Num is either too small or large");
		} else {
			System.out.println("SoloKills: " + solokill.get(gameNum - 1));
			System.out.println("SoloDeaths: " + solodeath.get(gameNum - 1));
			System.out.println("CS Diff: " + csdiff.get(gameNum - 1));
			System.out.println("XP Diff: " + xpdiff.get(gameNum - 1));
			System.out.println("Gold Diff: " + golddiff.get(gameNum - 1));
			System.out.println("Top Kills off Ganks: " + topkill.get(gameNum - 1));
			System.out.println("Mid Kills off Ganks: " + midkill.get(gameNum - 1));
			System.out.println("Bot Kills off Ganks: " + botkill.get(gameNum - 1));
			System.out.println("Top Ganks: " + topgank.get(gameNum - 1));
			System.out.println("Mid Ganks: " + midgank.get(gameNum - 1));
			System.out.println("Bot Ganks: " + botgank.get(gameNum - 1));
			System.out.println("First Gank Time: " + firstgank.get(gameNum - 1));
			System.out.println("Got First Turret: " + firstturret.get(gameNum - 1));
			System.out.println("First Drag Time: " + drag.get(gameNum - 1));
			System.out.println("Got First Drag: " + firstDrag.get(gameNum - 1));
			System.out.println("Rift Time: " + rift.get(gameNum - 1));
			System.out.println("Got Rift: " + firstRift.get(gameNum - 1));
		}
	}
	
	public void printLast() {
		System.out.println("SoloKills: " + solokill.getLast());
		System.out.println("SoloDeaths: " + solodeath.getLast());
		System.out.println("CS Diff: " + csdiff.getLast());
		System.out.println("XP Diff: " + xpdiff.getLast());
		System.out.println("Gold Diff: " + golddiff.getLast());
		System.out.println("Top Kills off Ganks: " + topkill.getLast());
		System.out.println("Mid Kills off Ganks: " + midkill.getLast());
		System.out.println("Bot Kills off Ganks: " + botkill.getLast());
		System.out.println("Top Ganks: " + topgank.getLast());
		System.out.println("Mid Ganks: " + midgank.getLast());
		System.out.println("Bot Ganks: " + botgank.getLast());
		System.out.println("First Gank Time: " + firstgank.getLast());
		System.out.println("Got First Turret: " + firstturret.getLast());
		System.out.println("First Drag Time: " + drag.getLast());
		System.out.println("Got First Drag: " + firstDrag.getLast());
		System.out.println("Rift Time: " + rift.getLast());
		System.out.println("Got Rift: " + firstRift.getLast());
	}
}