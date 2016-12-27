package com.rmkane.barcode;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class QRCodeReaderTest {
	private static String qrCodeData = "Hello World!";
	private static String filePath = "output/QR-Code.png";

	private static BarcodeReader qrReader;

	@BeforeClass
	public static void setup() {
		qrReader = new QRCodeReader(200, 200);
	}

	@Test
	public void testEncode() {
		try {
			qrReader.createQRCode(qrCodeData, filePath);
			System.out.println("QR Code image created successfully!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testDecode() {
		try {
			System.out.println("Data read from QR Code: " + qrReader.readQRCode(filePath));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
