import java.util.LinkedList;

public class PreTwentyStat {
	
	LinkedList<Integer> solokill;
	LinkedList<Integer> solodeath;
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
	
	public void add(int skVal, int sdVal, int topVal, int midVal, int botVal, long fgVal, boolean ftVal, long dragVal, boolean fdVal, long riftVal, boolean frVal) {
		solokill.add(skVal);
		solodeath.add(sdVal);
		topgank.add(topVal);
		midgank.add(midVal);
		botgank.add(botVal);
		firstgank.add(fgVal);
		firstturret.add(ftVal);
		drag.add(dragVal);
		firstDrag.add(fdVal);
		rift.add(riftVal);
		firstRift.add(frVal);
	}
	
	public void printLast() {
		System.out.println("SoloKills: " + solokill.getLast());
		System.out.println("SoloDeaths: " + solodeath.getLast());
		System.out.println("Top Kills off Ganks: " + topgank.getLast());
		System.out.println("Mid Kills off Ganks: " + midgank.getLast());
		System.out.println("Bot Kills off Ganks: " + botgank.getLast());
		System.out.println("First Gank Time: " + firstgank.getLast());
		System.out.println("Got First Turret: " + firstturret.getLast());
		System.out.println("First Drag Time: " + drag.getLast());
		System.out.println("Got First Drag: " + firstDrag.getLast());
		System.out.println("Rift Time: " + rift.getLast());
		System.out.println("Got Rift: " + firstRift.getLast());
	}
}