package graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class NavGraph {
	private int xMax, yMax;

	public int getxMax() {
		return xMax;
	}

	public int getyMax() {
		return yMax;
	}

	private ArrayList<ArrayList<NavNod>> graph;

	public NavGraph() {

	}

	public void initWithFile(String fileName) throws IOException {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(new File(fileName)));
			yMax = in.readLine().length();

			if (yMax == 0)
				throw new IOException();

			int line = 1;
			while (in.readLine() != null)
				line++;
			xMax = line;
			graph = new ArrayList<ArrayList<NavNod>>();
			for (int i = 0; i < xMax; i++) {
				graph.add(new ArrayList<NavNod>());
			}

			in = new BufferedReader(new FileReader(new File(fileName)));
			// reset the BufferedReader and success of reading the file

			line = 0;
			for (String x = in.readLine(); x != null; x = in.readLine()) {

				if (yMax != x.length()) {
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					throw new IOException();
				}

				for (int i = 0; i < xMax; i++) {
					switch (x.charAt(i)) {
					case '1':
						// Empty
						graph.get(line).add(new NavNod(line, i, "E", 0, this));
						break;
					case '0':
						// Wall
						graph.get(line).add(new NavNod(line, i, "W", 0, this));
						break;
					}
				}
				line++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public int getP(NavNod N1, NavNod N2) {

		return 0;
	}

	public NavNod getNod(int x, int y) {
		return graph.get(x).get(y);
	}
}
