package com.rmkane.barcode.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

public class FileUtils {
	public static BufferedImage readImage(String path) throws FileNotFoundException, IOException {
		return ImageIO.read(new FileInputStream(path));
	}

	public static Path createPath(String filepath) {
		File file = new File(filepath);
		file.getParentFile().mkdirs();
		return Paths.get(filepath);
	}

	public static String extractFileExt(String path) {
		return path.substring(path.lastIndexOf('.') + 1);
	}
}
