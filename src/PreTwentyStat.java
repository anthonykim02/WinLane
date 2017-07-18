import java.util.LinkedList;

public class PreTwentyStat {
	
	LinkedList<Integer> solokill;
	LinkedList<Integer> solodeath;
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
	
	public void add(int skVal, int sdVal, int topkVal, int midkVal, int botkVal, int topgVal, int midgVal, int botgVal, 
			long fgVal, boolean ftVal, long dragVal, boolean fdVal, long riftVal, boolean frVal) {
		solokill.add(skVal);
		solodeath.add(sdVal);
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
		
		double totalSK = 0;
		double totalSD = 0;
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
			totalTopK += topkill.get(i);
			totalMidK += midkill.get(i);
			totalBotK += botkill.get(i);
			totalTopG += topgank.get(i);
			totalMidG += midgank.get(i);
			totalBotG += botgank.get(i);
			totalFirstG += firstgank.get(i);
			totalDrag += drag.get(i);
			totalRift += rift.get(i);
			
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
		double avgTopK = totalTopK / numGames;
		double avgMidK = totalMidK / numGames;
		double avgBotK = totalBotK / numGames;
		double avgTopG = totalTopG / numGames;
		double avgMidG = totalMidG / numGames;
		double avgBotG = totalBotG / numGames;
		double avgFirstG = totalFirstG / numGames;
		double avgFirstT = totalFirstT / numGames;
		double avgDrag = totalDrag / numGames;
		double avgFirstD = totalFirstD / numGames;
		double avgRift = totalRift / numGames;
		double avgFirstR = totalFirstR / numGames;
		
		
		apts = new AvgPreTwentyStat(avgSK, avgSD, avgTopK, avgMidK, avgBotK, avgTopG, 
				avgMidG, avgBotG, avgFirstG, avgFirstT, avgDrag, avgFirstD, avgRift, avgFirstR);
		
		return apts;
	}
	
	public void printLast() {
		System.out.println("SoloKills: " + solokill.getLast());
		System.out.println("SoloDeaths: " + solodeath.getLast());
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