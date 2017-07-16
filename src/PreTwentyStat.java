import java.util.LinkedList;

public class PreTwentyStat {
	
	LinkedList<Integer> solokill;
	LinkedList<Long> drag;
	LinkedList<Boolean> firstDrag;
	LinkedList<Long> rift;
	LinkedList<Boolean> firstRift;
	
	public PreTwentyStat() {
		solokill = new LinkedList();
		drag = new LinkedList();
		firstDrag = new LinkedList();
		rift = new LinkedList();
		firstRift = new LinkedList();
	}
	
	public void add(int skVal, long dragVal, boolean fdVal, long riftVal, boolean frVal) {
		solokill.add(skVal);
		drag.add(dragVal);
		firstDrag.add(fdVal);
		rift.add(riftVal);
		firstRift.add(frVal);
	}
	
	public void printLast() {
		System.out.println(solokill.getLast());
		System.out.println(drag.getLast());
		System.out.println(firstDrag.getLast());
		System.out.println(rift.getLast());
		System.out.println(firstRift.getLast());
	}
}