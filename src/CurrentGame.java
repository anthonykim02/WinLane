
public class CurrentGame {
	long id1, id2, id3, id4, id5, id6, id7, id8, id9, id10;
	int champ1, champ2, champ3, champ4, champ5, champ6, champ7, champ8, champ9, champ10;
	
	public CurrentGame() {
		id1 = 0;
		id2 = 0;
		id3 = 0;
		id4 = 0;
		id5 = 0;
		id6 = 0;
		id7 = 0;
		id8 = 0;
		id9 = 0;
		id10 = 0;
		
		champ1 = 0;
		champ2 = 0;
		champ3 = 0;
		champ4 = 0;
		champ5 = 0;
		champ6 = 0;
		champ7 = 0;
		champ8 = 0;
		champ9 = 0;
		champ10 = 0;
	}
	
	public void set(int num, long id, int champ) {
		if (num == 1) {
			id1 = id;
			champ1 = champ;
		} else if (num == 2) {
			id2 = id;
			champ2 = champ;
		} else if (num == 3) {
			id3 = id;
			champ3 = champ;
		} else if (num == 4) {
			id4 = id;
			champ4 = champ;
		} else if (num == 5) {
			id5 = id;
			champ5 = champ;
		} else if (num == 6) {
			id6 = id;
			champ6 = champ;
		} else if (num == 7) {
			id7 = id;
			champ7 = champ;
		} else if (num == 8) {
			id8 = id;
			champ8 = champ;
		} else if (num == 9) {
			id9 = id;
			champ9 = champ;
		} else if (num == 10) {
			id10 = id;
			champ10 = champ;
		}
	}
	
	public void print() {
		System.out.println("1: " + id1 + ", " + champ1);
		System.out.println("2: " + id2 + ", " + champ2);
		System.out.println("3: " + id3 + ", " + champ3);
		System.out.println("4: " + id4 + ", " + champ4);
		System.out.println("5: " + id5 + ", " + champ5);
		System.out.println("6: " + id6 + ", " + champ6);
		System.out.println("7: " + id7 + ", " + champ7);
		System.out.println("8: " + id8 + ", " + champ8);
		System.out.println("9: " + id9 + ", " + champ9);
		System.out.println("10: " + id10 + ", " + champ10);
	}
}
