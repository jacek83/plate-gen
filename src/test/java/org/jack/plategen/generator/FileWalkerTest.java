package org.jack.plategen.generator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FileWalkerTest {

	private final File TEST_DIR = new File("filewalker-test");

	@Before
	public void init() throws IOException {
		TEST_DIR.mkdir();

		File fwTestSubdir = new File("filewalker-test/subdir");
		fwTestSubdir.mkdir();

		createTestFile("filewalker-test/subdir/imageA.png");
		createTestFile("filewalker-test/subdir/test.txt");

		createTestFile("filewalker-test/image1.png");
		createTestFile("filewalker-test/image2.jpeg");
		createTestFile("filewalker-test/image3.jpg");

		createTestFile("filewalker-test/img.doc");
		createTestFile("filewalker-test/imageD.cpp");
		createTestFile("filewalker-test/clazz.java");
		createTestFile("filewalker-test/image4.jpg");

//		File imgA = new File("filewalker-test/imageD.jpg");
//		FileOutputStream out = new FileOutputStream(imgA);
////		properties.store(out, null);
//		out.close();
	}

	private void createTestFile(String name) throws IOException {
		File imgA = new File(name);
		FileOutputStream out = new FileOutputStream(imgA);
		out.close();
	}

	@Test
	public void test() {

		FileWalker fw = new FileWalker();
		Set<File> result = fw.walk("filewalker-test");

		Set<String> fileNames = result.stream().map(f -> f.getName()).collect(Collectors.toSet());

		Assert.assertTrue(fileNames.contains("imageA.png"));
		Assert.assertTrue(fileNames.contains("image1.png"));
		Assert.assertTrue(fileNames.contains("image2.jpeg"));
		Assert.assertTrue(fileNames.contains("image3.jpg"));
		Assert.assertTrue(fileNames.contains("image4.jpg"));
	}

	public void deleteRecursive(File path) {
		File[] allFiles = path.listFiles();
		for (File file : allFiles) {
			if (file.isDirectory()) {
				deleteRecursive(file);
			}
			file.delete();
		}
		path.delete();
	}

	@After
	public void afterTest() {
		deleteRecursive(TEST_DIR);
	}

}
