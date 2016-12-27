package com.rmkane.barcode;

import static com.google.zxing.BarcodeFormat.QR_CODE;
import static com.rmkane.barcode.utils.FileUtils.createPath;
import static com.rmkane.barcode.utils.FileUtils.extractFileExt;
import static com.rmkane.barcode.utils.FileUtils.readImage;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QRCodeReader implements BarcodeReader {
	public static final Map<EncodeHintType, Enum<?>> DEFAULT_HINTS;

	static {
		DEFAULT_HINTS = new HashMap<EncodeHintType, Enum<?>>();
		DEFAULT_HINTS.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
	}

	private String charset;
	private Map<EncodeHintType, Enum<?>> hints;
	private int width;
	private int height;

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public Map<EncodeHintType, Enum<?>> getHints() {
		return hints;
	}

	public void setHints(Map<EncodeHintType, Enum<?>> hints) {
		this.hints = hints;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public QRCodeReader(String charset, Map<EncodeHintType, Enum<?>> hints, int width, int height) {
		this.charset = charset;
		this.hints = hints;
		this.width = width;
		this.height = height;
	}

	public QRCodeReader(int width, int height) {
		this(CHARSET_ISO, DEFAULT_HINTS, width, height);
	}

	public void createQRCode(String data, String path) throws WriterException, IOException {
		MatrixToImageWriter.writeToPath(dataToMatrix(data, getCharset(), getHints(), getWidth(), getHeight()), extractFileExt(path), createPath(path));
	}

	public String readQRCode(String path) throws FileNotFoundException, IOException, NotFoundException {
		return readQRCode(readImage(path));
	}

	public String readQRCode(BufferedImage image) throws FileNotFoundException, IOException, NotFoundException {
		return imageToResult(image, getHints()).getText();
	}

	private static BitMatrix dataToMatrix(String data, String charset, Map<EncodeHintType, Enum<?>> hints, int width, int height) throws UnsupportedEncodingException, WriterException {
		return new MultiFormatWriter().encode(new String(data.getBytes(charset), charset), QR_CODE, width, height, hints);
	}

	@SuppressWarnings("unchecked")
	private static Result imageToResult(BufferedImage image, @SuppressWarnings("rawtypes") Map hintMap) throws NotFoundException {
		return new MultiFormatReader().decode(imageToBitmap(image), hintMap);
	}

	private static BinaryBitmap imageToBitmap(BufferedImage image) throws NotFoundException {
		return new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));
	}
}
