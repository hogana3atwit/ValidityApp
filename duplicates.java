import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;

public class duplicates {

	public static void main(String[] args) throws FileNotFoundException {
		String csvFile = "~/validity/simple-app-starter/test-files/normal.csv";
		File in = new File(csvFile);
		Scanner reader = new Scanner(in);

		String curLine = "";
		String compareLine = "";
		String cvsSplitBy = ",";

		File outputFile = new File("~/validity/simple-app-starter/test-files/duplicates.csv");
		PrintWriter out = new PrintWriter(outputFile);
		int duplicates = 0;
		ArrayList<Integer> dupLocations = new ArrayList<Integer>();

		while(reader.hasNextLine()) {
			curLine = reader.nextLine();
			String[] entry = curLine.split(cvsSplitBy);
			for(int i = 1; i < entry.length; i++) {
				char[] a = new char[entry[i].length()];
				for(int j = 0; j < a.length; j++) {
					a[j] = entry[i].charAt(j);
				}
				while(reader.hasNextLine()) {
					compareLine = reader.nextLine();
					String[] compare = compareLine.split(cvsSplitBy);
					for(int k = 1; k < compare.length; k++) {
						char[] b = new char[compare[k].length()];
						for(int l = 0; l < b.length; l++) {
							b[l] = compare[k].charAt(l);

						}
						int difference = LevenshteinDistance(a, b);
						if(difference == 0) {
							dupLocations.add(i); //tracks which components of the entry are duplicates
						}
					}
				}
			}

			if(dupLocations.contains(1) && dupLocations.contains(2) && (dupLocations.contains(4)|| dupLocations.contains(5))) {
				duplicates++;
				out.write(curLine);
				out.println();
			}
					//check which positions returned duplicates and if name and email both did, increment duplicates by 1
					//write to output file
			dupLocations.clear();  //clear list of duplicate positions for next comparison
		}
		out.write("The total number of duplicates is: " + duplicates);
		out.println();

}

	public static int LevenshteinDistance(char[] a, char[] b) {
		int[][] d = new int[a.length][b.length];
		int cost = 0;
		for(int i = 0; i < d.length; i++) {
			for(int j = 0; j < d[0].length; j++) {
				d[i][j] = 0;
			}
		}


		for(int i = 1; i < a.length; i++) {
			d[i][0] = i;
		}

		for(int j = 1; j < b.length; j++) {
			d[0][j] = j;
		}

		for(int j = 1; j < b.length; j++) {
			for(int i = 1; i < a.length; i++) {
				if(a[i] == b[j]) {
					cost = 0;
				} else {
					cost = 1;
				}

				d[i][j] = Math.min(d[i-1][j] + 1, Math.min(d[i][j-1]+1, d[i-1][j-1] + cost));

			}
		}

		return d[a.length][b.length];

	}

}
