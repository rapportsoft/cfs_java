package com.cwms.controller;

import java.math.BigDecimal;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.cwms.helper.HelperMethods;
import com.cwms.repository.RecieptPrintRepository;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.lowagie.text.DocumentException;

@CrossOrigin("*")
@RequestMapping("/receiptprint")
@RestController
public class RecieptPrintController {
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	private RecieptPrintRepository recieptprintRepo;
	
	
	@Autowired
	private HelperMethods helperMethods;
	
	@PostMapping("/advreceipt/{cid}/{bid}/{transId}/{docType}")
	public ResponseEntity<?> generateInvoicePdf(@PathVariable("cid") String cid, @PathVariable("bid") String bid ,@PathVariable("transId") String transId ,@PathVariable("docType") String docType ) throws DocumentException{
		
		Map<String, String> company = new HashMap<>();
		
		Object companyDtl = recieptprintRepo.getCompanyAddressDetails(cid, bid);
		
		boolean key1 =true;
		
		
		
		if(companyDtl!=null) {
        	Object[] fields  = (Object[]) companyDtl;
        	
        	
        	company.put("cname",(String) fields[0]);
        	company.put("cadd1",(String) fields[13]);
        	company.put("cadd2",(String) fields[14]);
        	company.put("cadd3",(String) fields[15]);
        	company.put("gst",(String) fields[10]);
        	company.put("cin",(String) fields[11]);
        	company.put("pan",(String) fields[12]);
        	company.put("state",(String) fields[6]);
        	company.put("email",(String) fields[18]);
        	
        	company.put("baddr1",(String) fields[2]);
        	company.put("baddr2",(String) fields[3]);
        	company.put("baddr3",(String) fields[4]);
        	company.put("branchPin",(String) fields[8]);
        	company.put("companyPin",(String) fields[17]);
        	
        	
        	

		}
		
		System.out.println(companyDtl.toString());
//		String branchAddress = company.get("baddr1")+" "+company.get("baddr2")+" "+company.get("baddr3");
		
		Context context = new Context();
		context.setVariable("logo", helperMethods.getImageByPath("logo"));
//		context.setVariable("QR", helperMethods.getImageByPath("QR"));
		
		context.setVariable("cname", company.get("cname"));
		context.setVariable("cadd1", company.get("cadd1"));
		context.setVariable("cadd2", company.get("cadd2"));
		context.setVariable("cadd3", company.get("cadd3"));
		context.setVariable("gst", company.get("gst"));
		context.setVariable("cin", company.get("cin"));
		context.setVariable("pan", company.get("pan"));
		context.setVariable("state", company.get("state"));
		context.setVariable("email", company.get("email"));
//		context.setVariable("branchAddress", branchAddress);
		context.setVariable("branchPin", company.get("branchPin"));
		context.setVariable("companyPin", company.get("companyPin"));
		
		
		List<Object[]> invDtl = recieptprintRepo.getRecieptInvDetails(cid, bid, transId);
		List<Map<String,String>> invlist = new ArrayList<>();
		BigDecimal sumInv = BigDecimal.ZERO;
		BigDecimal sum1Rec = BigDecimal.ZERO;
		if(!invDtl.isEmpty()) {
			
			for (Object[] row : invDtl) {
				 Map<String, String> rowMap = new LinkedHashMap<>();
				 
				 String invoiceNo = (String) row[0];
				 String invoiceDate = (String) row[1];
				 String invoiceType = (String) row[5];
				 
				 
				 
				 
				 BigDecimal invoiceAmt1 = (BigDecimal) row[2];
				 System.out.println("inv"+invoiceAmt1);
				 sumInv=sumInv.add(invoiceAmt1);
				 System.out.println("suminv"+sumInv);
				 String invoiceAmt =invoiceAmt1.toString();
				 BigDecimal receiptAmt1 = (BigDecimal) row[3];
				 System.out.println("receiptAmt1"+receiptAmt1);
				 
				 sum1Rec=sum1Rec.add(receiptAmt1);
				 System.out.println("receiptAmt1sum"+sum1Rec);
				 String receiptAmt =receiptAmt1.toString();
				 
				
				 
				 rowMap.put("invoiceNo",invoiceNo );
				 rowMap.put("invoiceDate",invoiceDate );
				 rowMap.put("invoiceType",invoiceType );
				 rowMap.put("invoiceAmt",invoiceAmt );
				 rowMap.put("receiptAmt",receiptAmt );
				 
				 
				 invlist.add(rowMap);
				  
			}
			
		}
		
		String sumInv1 = sumInv.setScale(2, BigDecimal.ROUND_DOWN).toString(); 
		String sumRec = sum1Rec.setScale(2, BigDecimal.ROUND_DOWN).toString();
		context.setVariable("sumInv1", sumInv1);
		context.setVariable("sumRec", sumRec);
		context.setVariable("invlist", invlist);
			
			
			
			
			
			String receiptNo ="";
			String  receiptDate="";
			String  PayingParty="";
			String createdBy ="";
			
			List<Map<String,String>> paymentlist = new ArrayList<>();
			BigDecimal paysum = BigDecimal.ZERO;
			
			
			
		if(docType.startsWith("AJ")) {
			List<Object[]> recptdtl = recieptprintRepo.getRecieptDetailsAdj(cid, bid, transId);
			
			if(!recptdtl.isEmpty()) {
				for (Object[] row : recptdtl) {
					 Map<String, String> rowMap = new LinkedHashMap<>();
					 
					 
					 if(receiptNo == null || receiptNo.isEmpty()){
						 receiptNo =   (String)row[1];
					 }
					 if(receiptDate == null || receiptDate.isEmpty()){
						 receiptDate =   (String)row[2];
					 }
					 if(PayingParty == null || PayingParty.isEmpty()){
						 PayingParty =   (String)row[8];
					 }
					 
					 if(createdBy == null || createdBy.isEmpty()){
						 createdBy =   (String)row[6];
					 }
					   
					 String bankName = (String)row [23] ;
					 String paymentmode = (String)row [16];
					 String checkNoOrRtgsNo = (String)row [17];
					 String checkDateOrRtgsDate = (String)row [18];
					 
					 
					 
					 BigDecimal amount1 = (BigDecimal)row[19];
					 String amount = amount1.setScale(2, BigDecimal.ROUND_DOWN).toString(); 
					 paysum =paysum.add(amount1);
					 
					 
					 
					 BigDecimal tdsAmt = (BigDecimal)row[22];
					 String tdsAmount = tdsAmt.setScale(2, BigDecimal.ROUND_DOWN).toString();
					 
					 if(tdsAmt.compareTo(BigDecimal.ZERO) > 0) {
						 context.setVariable("tdsAmount", tdsAmount);
					 }
					 
					 
					 rowMap.put("bankName", (bankName == null || bankName.isEmpty()) ? "" : bankName);
					 rowMap.put("paymentmode", paymentmode);
					 rowMap.put("checkNoOrRtgsNo", checkNoOrRtgsNo);
					 rowMap.put("checkDateOrRtgsDate", checkDateOrRtgsDate);
					 rowMap.put("amount", amount);
					 rowMap.put("tdsAmount", tdsAmount);
					 
					 paymentlist.add(rowMap);
					 
					 
					 
					 
				}
			}
		}
		else {
			
			if(docType.startsWith("AD")) {
				key1 = false;
			}
			List<Object[]> recptdtl = recieptprintRepo.getRecieptDetails(cid, bid, transId);
			
			if(!recptdtl.isEmpty()) {
				for (Object[] row : recptdtl) {
					 Map<String, String> rowMap = new LinkedHashMap<>();
					 
					 
					 if(receiptNo == null || receiptNo.isEmpty()){
						 receiptNo =   (String)row[1];
					 }
					 if(receiptDate == null || receiptDate.isEmpty()){
						 receiptDate =   (String)row[2];
					 }
					 if(PayingParty == null || PayingParty.isEmpty()){
						 PayingParty =   (String)row[8];
					 }
					 
					 if(createdBy == null || createdBy.isEmpty()){
						 createdBy =   (String)row[6];
					 }
					   
					 String bankName = (String)row [23] ;
					 String paymentmode = (String)row [16];
					 String checkNoOrRtgsNo = (String)row [17];
					 String checkDateOrRtgsDate = (String)row [18];
					 
					 BigDecimal amount1 = (BigDecimal)row[19];
					 String amount = amount1.setScale(2, BigDecimal.ROUND_DOWN).toString(); 
					 paysum =paysum.add(amount1);
					 
					 BigDecimal tdsAmt = (BigDecimal)row[22];
					 

					 String tdsAmount = tdsAmt.setScale(2, BigDecimal.ROUND_DOWN).toString();
					 
					 
					 
					 if(tdsAmt.compareTo(BigDecimal.ZERO) > 0) {
						 context.setVariable("tdsAmount", tdsAmount);
					 }
					 
					 rowMap.put("bankName", (bankName == null || bankName.isEmpty()) ? "" : bankName);
					 rowMap.put("paymentmode", paymentmode);
					 rowMap.put("checkNoOrRtgsNo", checkNoOrRtgsNo);
					 rowMap.put("checkDateOrRtgsDate", checkDateOrRtgsDate);
					 rowMap.put("amount", amount);
					 rowMap.put("tdsAmount", tdsAmount);
					 
					 paymentlist.add(rowMap);
					 
					 
					 
					 
				}
			}
			}
				
		
		
		
		
		
		
		
		
		String payTotal = paysum.setScale(2, BigDecimal.ROUND_DOWN).toString(); 
		 String amountInWords = convertAmountToWords(paysum);
//		 String AmountInWords = "₹ " + amountInWords;
		
		
		
		
		context.setVariable("paymentlist", paymentlist);
		context.setVariable("key1", key1);
		context.setVariable("receiptNo", receiptNo);
		context.setVariable("receiptDate", receiptDate);
		context.setVariable("PayingParty", PayingParty);
		context.setVariable("payTotal", payTotal);
		context.setVariable("AmountInWords", amountInWords);
		context.setVariable("createdBy", createdBy);
		
		
		
		
	
		
		String htmlContent = templateEngine.process("Receipt_Report.html", context);
		//
				ITextRenderer renderer = new ITextRenderer();
				renderer.setDocumentFromString(htmlContent);
				renderer.layout();

				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				renderer.createPDF(outputStream);

				byte[] pdfBytes = outputStream.toByteArray();
				String base64Pdf = Base64.getEncoder().encodeToString(pdfBytes);

				return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).body(base64Pdf);
	}
	
	
	private static String convertAmountToWords(BigDecimal amount) {
		String[] units = { "", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine" };
		String[] teens = { "", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen",
				"Eighteen", "Nineteen" };
		String[] tens = { "", "Ten", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety" };

		String words = "";

		int crore = 0;
		int lakh = 0;
		int thousand = 0;
		int remainder = 0;
		if (amount != null) {
			crore = amount.intValue() / 10000000;
			lakh = (amount.intValue() / 100000) % 100;
			thousand = (amount.intValue() / 1000) % 100;
			remainder = amount.intValue() % 1000;
		}

//		int crore = amount.intValue() / 10000000;
//		int lakh = (amount.intValue() / 100000) % 100;
//		int thousand = (amount.intValue() / 1000) % 100;
//		int remainder = amount.intValue() % 1000;

		if (crore > 0) {
			words += convertThreeDigitNumber(crore) + " Crore ";
		}

		if (lakh > 0) {
			words += convertThreeDigitNumber(lakh) + " Lakh ";
		}

		if (thousand > 0) {
			words += convertThreeDigitNumber(thousand) + " Thousand ";
		}

		if (remainder > 0) {
			words += convertThreeDigitNumber(remainder);
		}

		return words.isEmpty() ? "Zero" : words + " only";
	}

	// Convert a three-digit number to words
	private static String convertThreeDigitNumber(int number) {
		String[] units = { "", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine" };
		String[] teens = { "", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen",
				"Eighteen", "Nineteen" };
		String[] tens = { "", "Ten", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety" };

		int unitDigit = number % 10;
		int tensDigit = (number % 100) / 10;
		int hundredsDigit = number / 100;

		String words = "";

		if (hundredsDigit > 0) {
			words += units[hundredsDigit] + " Hundred ";
		}

		if (tensDigit > 1) {
			words += tens[tensDigit] + " ";
			words += units[unitDigit];
		} else if (tensDigit == 1) {
			words += teens[unitDigit];
		} else {
			words += units[unitDigit];
		}

		return words;
	}


}
