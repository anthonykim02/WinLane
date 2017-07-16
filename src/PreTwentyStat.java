import java.util.LinkedList;

public class PreTwentyStat {
	
	LinkedList<Integer> solokill;
	LinkedList<Integer> solodeath;
	LinkedList<Integer> topgank;
	LinkedList<Integer> midgank;
	LinkedList<Integer> botgank;
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
		drag = new LinkedList();
		firstDrag = new LinkedList();
		rift = new LinkedList();
		firstRift = new LinkedList();
	}
	
	public void add(int skVal, int sdVal, int topVal, int midVal, int botVal, long dragVal, boolean fdVal, long riftVal, boolean frVal) {
		solokill.add(skVal);
		solodeath.add(sdVal);
		topgank.add(topVal);
		midgank.add(midVal);
		botgank.add(botVal);
		drag.add(dragVal);
		firstDrag.add(fdVal);
		rift.add(riftVal);
		firstRift.add(frVal);
	}
	
	public void printLast() {
		System.out.println(solokill.getLast());
		System.out.println(solodeath.getLast());
		System.out.println(topgank.getLast());
		System.out.println(midgank.getLast());
		System.out.println(botgank.getLast());
		System.out.println(drag.getLast());
		System.out.println(firstDrag.getLast());
		System.out.println(rift.getLast());
		System.out.println(firstRift.getLast());
	}
}