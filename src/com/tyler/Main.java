package com.tyler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {

	private static final String INPUT_FILE_NAME = "src/com/tyler/input.txt";
	private static final String OUTPUT_FILE_NAME = "src/com/tyler/output.txt";
	private static final double LONG_SHORT_SIDE_RATIO = 2;

	public static void main(String[] args) {
		long tStart = System.currentTimeMillis();
		solve();
		long tEnd = System.currentTimeMillis();
		System.out.println("The elapsed Seconds is " + (tEnd - tStart) / 1000.0 + " s");
	}

	public static void solve() {

		try (BufferedReader in = new BufferedReader(new FileReader(INPUT_FILE_NAME));
		     BufferedWriter out = new BufferedWriter(new FileWriter(OUTPUT_FILE_NAME))) {

			String line;
			int count = 0;

			int totalTestCases = 0;
			int testCases = 0;

			Set<Double> lights = new HashSet<>();
			List<Integer> counts = new ArrayList<>();

			while ((line = in.readLine()) != null) {
				count++;

				if (count == 1) {  // read the first line
					totalTestCases = Integer.parseInt(line);
				} else if (testCases == 0) { // read the individual test cases
					testCases = Integer.parseInt(line);

					lights = new HashSet<>();
				} else {  // read the coordinates of mosquitos, do the statics
					String[] values = line.split(" ");
					int x = Integer.parseInt(values[0]);
					int y = Integer.parseInt(values[1]);

					lights.add(calculateLightCoordFromMosquito(x, y));

					testCases--;
					if (testCases == 0) {
						counts.add(lights.size());
					}
				}
			}

			for (int i = 0; i < totalTestCases; i++) {
				out.write(String.format("#%d %d\n", i + 1, counts.get(i)));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// theory: in the 30,60,90 degrees triangle, the ratio of the sides length is 1:√3:2
	// input: the coordinate of mosquitos
	// output: the x coordinate of the light hit on the material, ignore the y coordinates since they're identical
	public static double calculateLightCoordFromMosquito(int x, int y) {

		if (x <= 0 || y <= 0 || x >= 100000 || y >= 100000 || y == 50000) {
			throw new IllegalArgumentException("Invalid input");
		}

		//case 1, reflective area, 50000 < y < 100000
		if (y > 50000) {
			return x - (y - 50000) / LONG_SHORT_SIDE_RATIO;
		}

		//case 2, permeated area,  0 < y < 50000
		return x - (50000 - y) * LONG_SHORT_SIDE_RATIO;
	}
}
