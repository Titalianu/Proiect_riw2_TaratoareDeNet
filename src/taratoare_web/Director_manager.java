package taratoare_web;

import java.io.File;
import java.util.ArrayList;

public class Director_manager {
	
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