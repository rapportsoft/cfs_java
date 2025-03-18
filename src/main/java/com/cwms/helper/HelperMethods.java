package com.cwms.helper;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Date;
import java.util.TimeZone;

import javax.imageio.ImageIO;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.StampingProperties;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.signatures.BouncyCastleDigest;
import com.itextpdf.signatures.DigestAlgorithms;
import com.itextpdf.signatures.IExternalDigest;
import com.itextpdf.signatures.IExternalSignature;
import com.itextpdf.signatures.PdfSignatureAppearance;
import com.itextpdf.signatures.PdfSigner;
import com.itextpdf.signatures.PrivateKeySignature;

@Component
public class HelperMethods {
		
	    @Value("${pfxFilePath}")
	    private String PFX_FILE_PATH;
		
		@Value("${pfxPassword}")
	    private String PFX_PASSWORD;
		
		@Value("${appLogoPath}")
	    private String logoPath;
	    
	    
	@Autowired
	private TemplateEngine templateEngine;
	
	
	
	public String getImageByPath(String type) {
	    String dataURL = "";
	    String fileName = "";
	    
	    if("logo".equals(type))
	    {
	    	fileName = "logo.png";
	    }else if("QR".equals(type))
	    {
	    	fileName = "sampleQR.jpeg";
	    }else
	    {
	    	fileName = "s";
	    }
	    String path = logoPath + fileName;
	    try {
	       
	        Path filePath = Paths.get(path);
	       

	        // Check if the file exists
	        if (Files.exists(filePath)) {
	            byte[] imageBytes = Files.readAllBytes(filePath);

	            // Encode the image bytes to base64
	            String base64Image = Base64.getEncoder().encodeToString(imageBytes);

	            // Construct a data URL for the image
	            dataURL = "data:image/jpeg;base64," + base64Image;
	        }
	    } catch (IOException e) {
	        // Log the error or handle it as needed
	        System.err.println("Error reading image file: " + e.getMessage());
	    }
	    return dataURL;
	}

	
	
	 public String generateAuctionInvoiceAndSignPdf(Context context) throws Exception {
	        // Step 1: Generate PDF from HTML
	        ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
	        String htmlContent = templateEngine.process("AuctionInvoicePrint.html", context);

	        ITextRenderer renderer = new ITextRenderer();
	        renderer.setDocumentFromString(htmlContent);
	        renderer.layout();
	        renderer.createPDF(pdfOutputStream);

	        byte[] pdfBytes = pdfOutputStream.toByteArray();

	        // Step 2: Sign the PDF
	        byte[] signedPdfBytes = signPdf(pdfBytes);

	        // Step 3: Convert to Base64
	        return Base64.getEncoder().encodeToString(signedPdfBytes);
	    }

	
	
	
	
	public String getFinancialYear() {
		LocalDate currentDate = LocalDate.now();
		int currentYear = currentDate.getYear();
		int month = currentDate.getMonthValue();

		// If the date is before April 1st, return the previous year
		if (month < 4) {
			return String.valueOf(currentYear - 1);
		} else { // If the date is April 1st or later, return the current year
			return String.valueOf(currentYear);
		}
	}

	 public String generateUPIQRCodeBase64(String upiId, String upiAccount, String amount, String remarks) {
	        try {
	            String qrData = "upi://pay?pa=" + upiId + "&pn=" + upiAccount + "&am=" + amount + "&cu=INR&tn=" + remarks;
	            
	            int width = 200;
	            int height = 200;
	            BitMatrix bitMatrix = new MultiFormatWriter().encode(qrData, BarcodeFormat.QR_CODE, width, height);
	            BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

	            ByteArrayOutputStream baos = new ByteArrayOutputStream();
	            ImageIO.write(qrImage, "png", baos);
	            byte[] imageBytes = baos.toByteArray();
	            
	            return "data:image/png;base64," + Base64.getEncoder().encodeToString(imageBytes);
	        } catch (WriterException | java.io.IOException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
//	 get Digitally signed PDF
	 
	 public String generateAndSignPdf(Context context) throws Exception {
	        // Step 1: Generate PDF from HTML
	        ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
	        String htmlContent = templateEngine.process("importInvoicePrint.html", context);

	        ITextRenderer renderer = new ITextRenderer();
	        renderer.setDocumentFromString(htmlContent);
	        renderer.layout();
	        renderer.createPDF(pdfOutputStream);

	        byte[] pdfBytes = pdfOutputStream.toByteArray();

	        // Step 2: Sign the PDF
	        byte[] signedPdfBytes = signPdf(pdfBytes);

	        // Step 3: Convert to Base64
	        return Base64.getEncoder().encodeToString(signedPdfBytes);
	    }

	 private String getISTDateTime() {
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
		    sdf.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata")); // ✅ Set to IST
		    return sdf.format(new Date());
		}

	 
	 public byte[] signPdf(byte[] pdfBytes) throws Exception {
	        // 🔹 Register Bouncy Castle provider
	        Security.addProvider(new BouncyCastleProvider());

	        // Load the keystore from .pfx file
	        KeyStore keyStore = KeyStore.getInstance("PKCS12");
	        try (FileInputStream fis = new FileInputStream(PFX_FILE_PATH)) {
	            keyStore.load(fis, PFX_PASSWORD.toCharArray());
	        }

	        // Get the private key and certificate
	        String alias = keyStore.aliases().nextElement();
	        PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, PFX_PASSWORD.toCharArray());
	        Certificate[] certChain = keyStore.getCertificateChain(alias);
	        X509Certificate cert = (X509Certificate) certChain[0];

	        // ✅ Extract signer's name from certificate
	        String signerName = getCommonNameFromCertificate(cert);

	        // Create PdfSigner
	        ByteArrayOutputStream signedPdfOutputStream = new ByteArrayOutputStream();
	        PdfReader reader = new PdfReader(new ByteArrayInputStream(pdfBytes));
	        PdfDocument pdfDoc = new PdfDocument(reader);
	        int lastPage = pdfDoc.getNumberOfPages(); // ✅ Get last page number
	        pdfDoc.close(); // Close after getting the page count

	        PdfSigner signer = new PdfSigner(new PdfReader(new ByteArrayInputStream(pdfBytes)), signedPdfOutputStream, new StampingProperties());

	        String istDateTime = getISTDateTime();
	        // ✅ Define signature appearance (bottom-right corner of last page)
//	        PdfSignatureAppearance appearance = signer.getSignatureAppearance()
//	                .setReason("I have reviewed this document")
//	                .setLocation("Pune")
//	                .setPageNumber(lastPage) // ✅ Place on last page
//	                .setPageRect(new Rectangle(450, 50, 120, 50)) // ✅ Adjust as needed
//	                .setLayer2Text("✔ Signature Valid\n\nSigned by: " + signerName + 
//	                               "\nDate: " + istDateTime + 
//	                               "\nLocation: Pune") // ✅ Text inside signature box
//	                .setRenderingMode(PdfSignatureAppearance.RenderingMode.NAME_AND_DESCRIPTION); // ✅ Show tick mark and text
//
//	        signer.setFieldName("sig");
	        
	        PdfSignatureAppearance appearance = signer.getSignatureAppearance()
	                .setReason("I have reviewed this document")
	                .setLocation("Pune")
	                .setPageNumber(lastPage) // ✅ Place on last page
	                .setPageRect(new Rectangle(400, 50, 180, 80)) // ✅ Move left for better alignment
	                .setLayer2Text("✔ Signature Valid\n\nDigitaly signed by " + signerName + 
	                               "\nDate: " + istDateTime + 
	                               "\nLocation: Pune") // ✅ Align left, remove extra spaces
	                .setRenderingMode(PdfSignatureAppearance.RenderingMode.DESCRIPTION); // ✅ No image required

	        // ✅ Use a regular font (not bold)
	        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
	        appearance.setLayer2Font(font);

	        // ✅ Manually draw a thin, light border
	        PdfDocument pdfDocument = signer.getDocument();
	        PdfPage page = pdfDocument.getPage(lastPage);
	        PdfCanvas canvas = new PdfCanvas(page);

	        Rectangle rect = appearance.getPageRect(); 
	        canvas.setLineWidth(0.5f)  // ✅ Thin border
	              .setStrokeColor(new DeviceRgb(150, 150, 150)) // ✅ Light gray border
	              .rectangle(rect.getLeft(), rect.getBottom(), rect.getWidth(), rect.getHeight())
	              .stroke();

	        signer.setFieldName("sig");


	        // Apply the signature
	        IExternalSignature pks = new PrivateKeySignature(privateKey, DigestAlgorithms.SHA256, "BC"); // BouncyCastle provider
	        IExternalDigest digest = new BouncyCastleDigest();
	        signer.signDetached(digest, pks, certChain, null, null, null, 0, PdfSigner.CryptoStandard.CMS);

	        return signedPdfOutputStream.toByteArray();
	    }

	    // 🔹 Helper method to extract Common Name (CN) from the certificate
	    private static String getCommonNameFromCertificate(X509Certificate cert) {
	        String subjectDN = cert.getSubjectX500Principal().getName();
	        for (String part : subjectDN.split(",")) {
	            if (part.trim().startsWith("CN=")) {
	                return part.trim().substring(3); // Remove "CN="
	            }
	        }
	        return "Unknown";
	    }

	 
	 
	 
}
