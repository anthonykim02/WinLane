import java.util.Map;
import java.util.HashMap;

// class that checks whether a point is within a rectangle
public class locationCalculator {
	
	// x and y for top left, top right, bot left, and bot right
	int xtl, ytl, xtr, ytr, xbl, ybl, xbr, ybr;
	double area;
	
	// constructor, requires rectangle's 
	public locationCalculator(int xtl, int ytl, int xtr, int ytr, int xbl, int ybl, int xbr, int ybr) {
		this.xtl = xtl;
		this.ytl = ytl;
		this.xtr = xtr;
		this.ytr = ytr;
		this.xbl = xbl;
		this.ybl = ybl;
		this.xbr = xbr;
		this.ybr = ybr;
		
		// calculate area of the rectangle
		this.area = distance(xtl, ytl, xtr, ytr) * distance(xtl, ytl, xbl, ybl);
	}
	
	// compares x and y to see if point is in rectangle (only for vertical or horizontal rectangles)
	public boolean basicIsInside(int x, int y) {
		int minX = xtl;
		int maxX = xtr;
		int minY = ybl;
		int maxY = ytl;
		
		if (x >= minX && x <= maxX) {
			if (y >= minY && y <= maxY) {
				return true;
			}
		}
		return false;
	}
	
	// helper distance function for complex calculation
	public double distance(int x1, int y1, int x2, int y2) {
		double part1 = (x1 - x2) * (x1 - x2);
		double part2 = (y1 - y2) * (y1 - y2);
		
		return Math.sqrt(part1 + part2);
	}
	
	// helper function to calculate side lengths of rectangle
	// need each edge of rectangle as well as edge between a point and each of the rectangle's points
	public Map<String, Double> sides(int x, int y) {
		
		// lengths between rectangle points
		double tltr = distance(xtl, ytl, xtr, ytr);
		double trbr = distance(xtr, ytr, xbr, ybr);
		double brbl = distance(xbr, ybr, xbl, ybl);
		double bltl = distance(xbl, ybl, xtl, ytl);
		
		// point to rectangle point
		double ptl = distance(xtl, ytl, x, y);
		double ptr = distance(xtr, ytr, x, y);
		double pbr = distance(xbr, ybr, x, y);
		double pbl = distance(xbl, ybl, x, y);
		
		Map<String, Double> lengths = new HashMap<String, Double>();
		lengths.put("tltr", tltr);
		lengths.put("trbr", trbr);
		lengths.put("brbl", brbl);
		lengths.put("bltl", bltl);
		lengths.put("ptl", ptl);
		lengths.put("ptr", ptr);
		lengths.put("pbr", pbr);
		lengths.put("pbl", pbl);
		
		return lengths;
	}
	
	// helper function for herons formula (find area with 3 side lengths)
	public double herons(double a, double b, double c) {
		double s = (a + b + c) / 2;
		double exp = s * (s - a) * (s - b) * (s - c);
		return Math.sqrt(exp);
	}
	
	// checks if point is in rectangle for any rectangle orientation
	public boolean complexIsInside(int x, int y) {
		
		Map<String, Double> lengths = sides(x, y);
		
		// triangles made with each edge of rectangle and the point
		// for rectangle ABCD and point P, need areas for triangles APD, DPC, CPB, PBA
		double tltr = lengths.get("tltr");
		double trbr = lengths.get("trbr");
		double brbl = lengths.get("brbl");
		double bltl = lengths.get("bltl");
		double ptl = lengths.get("ptl");
		double ptr = lengths.get("ptr");
		double pbr = lengths.get("pbr");
		double pbl = lengths.get("pbl");
		
		double apd = herons(bltl, pbl, ptl);
		double dpc = herons(brbl, pbr, pbl);
		double cpb = herons(trbr, ptr, pbr);
		double pba = herons(tltr, ptl, ptr);
		
		double triangleArea = apd + dpc + cpb + pba;
		
		if (triangleArea > area) {
			return false;
		} else {
			return true;
		}
	}
	
	
	
	
	
}
