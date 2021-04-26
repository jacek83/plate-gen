package org.jack.plategen.generator;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class FileWalker {

	public Set<File> walk(String rootPath) {
		File rootDir = new File(rootPath);
		Set<File> imgFiles = new HashSet<>();

		if (rootDir.listFiles() == null) {
			return imgFiles;
		}
		File[] files = rootDir.listFiles();

		for (File f : files) {
			if (f.isDirectory()) {
				imgFiles.addAll(walk(f.getAbsolutePath()));
			} else if (isImageFile(f.getName())) {
				imgFiles.add(f);
			}
		}
		return imgFiles;
	}

	private boolean isImageFile(String fileName) {
		return fileName.endsWith(".jpeg") || fileName.endsWith(".jpg") || fileName.endsWith(".png");
	}

}
