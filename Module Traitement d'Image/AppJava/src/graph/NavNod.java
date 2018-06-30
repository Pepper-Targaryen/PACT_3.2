package graph;

import java.util.ArrayList;

public class NavNod {
	// coordonnees
	private int x, y;

	// Type
	private String category;

	private int id;

	private ArrayList<NavNod> succesors = null;

	private NavGraph graph;

	public NavNod(int x, int y, String category, int id, NavGraph graph) {
		this.x = x;
		this.y = y;
		this.id = id;
		this.category = category;
		this.graph = graph;
	}

	public void initialSuccesors() {
		// We need to verify 4 neighbours
		if (this.canGo()) {
			if (x > 0 && graph.getNod(x - 1, y).canGo())
				succesors.add(graph.getNod(x - 1, y));
			if (x < graph.getxMax() - 1 && graph.getNod(x + 1, y).canGo())
				succesors.add(graph.getNod(x + 1, y));
			if (y > 0 && graph.getNod(x, y - 1).canGo())
				succesors.add(graph.getNod(x, y - 1));
			if (y < graph.getyMax() - 1 && graph.getNod(x, y + 1).canGo())
				succesors.add(graph.getNod(x, y + 1));
		}

	}

	public boolean canGo() {
		if (category == "E")
			return true;

		return false;
	}
	
	public String getCategory() {
		return category;
	}
}
