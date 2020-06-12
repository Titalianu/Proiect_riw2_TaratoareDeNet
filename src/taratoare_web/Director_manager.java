package taratoare_web;

import java.io.File;

public class Director_manager {
	
	public static void createDir(String Link) {
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