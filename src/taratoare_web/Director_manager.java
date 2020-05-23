package taratoare_web;

import java.io.File;
import java.util.ArrayList;

public class Director_manager {

	static ArrayList<String> dataFile = new ArrayList<String>();

	// Get files from a url
	public static ArrayList<String> getFiles(String path) {

		ArrayList<String> dataNull = new ArrayList<String>();
		File root = new File(path);
		File[] list = root.listFiles();
		System.out.println("Entering in getFiles");
		if (list == null)
			return dataNull;
		for (File f : list) {
			if (f.isDirectory())
				getFiles(f.getAbsolutePath());
			else if (f.toString().endsWith(".html")) {
				dataFile.add(f.getAbsolutePath());
				System.out.println(f.getAbsoluteFile());
			}
		}
		System.out.print(dataFile);
		return dataFile;
	}

	// Create directory for all url's
	public static void createDir(String Link) {
		// http://riweb.tibeica.com/tests/l1_absolute/name/nam
		String[] parts = Link.split("/");
		Integer i;
		String path = parts[2];
		for (i = 3; i < parts.length; i++) {
			System.out.println(path);
			File dir = new File(path);
			boolean successful = dir.mkdir();
			if (successful) {
				// creating the directory succeeded
				System.out.println("directory was created successfully");
				if (i + 1 < parts.length)
					path += "\\" + parts[i + 1];
				// else
				// System.exit(0);
			}
		}

	}

}