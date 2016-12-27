package com.rmkane.barcode;

import java.awt.image.BufferedImage;
import java.util.Map;

import com.google.zxing.EncodeHintType;

public interface BarcodeReader {
	static final String CHARSET_UTF8 = "UTF-8";
	static final String CHARSET_ISO = "ISO-8859-1";

	String getCharset();
	Map<EncodeHintType, Enum<?>> getHints();
	int getWidth();
	int getHeight();

	void createQRCode(String data, String path) throws Exception;

	String readQRCode(String path) throws Exception;
	String readQRCode(BufferedImage image) throws Exception;
}
