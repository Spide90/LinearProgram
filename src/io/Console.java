package io;

import java.io.File;

public class Console {

	public static void main(String[] args) {
		File file = new File("testFile.lp");
		new LPReader(file);
	}

}
