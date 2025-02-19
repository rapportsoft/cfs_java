package com.cwms.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.cwms.repository.MiscellaneousInvPrintRepository;
import com.cwms.service.MiscellaneousInvPrintService;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.lowagie.text.DocumentException;

@CrossOrigin("*")
@RequestMapping("/miscellaneousInvPrint")
@RestController
public class MiscellaneousInvPrintController {
	
	@Autowired
	private  MiscellaneousInvPrintService miscellaneousInvPrintService;
	
	@Autowired
	private  MiscellaneousInvPrintRepository miscellaneousInvPrintRepository;
	
	@Autowired
	private TemplateEngine templateEngine;
	
	
	@Autowired
	private HelperMethods helperMethods;
	
	@PostMapping("/miscellaneousInvPrintpdf/{cid}/{bid}/{invoiceNo}")
	public ResponseEntity<?> generateInvoicePdf(@PathVariable("cid") String cid, @PathVariable("bid") String bid ,@PathVariable("invoiceNo") String invoiceNo
			 ) throws DocumentException{

		
		System.out.println("inside miscellaneousInvPrintpdf  controller ");
		System.out.println(cid+" "+bid);
		
//		String profitcentreId ="N00002";
		Map<String,String> invdtl = miscellaneousInvPrintService.getInvoiceDetails(cid, bid,invoiceNo);
		String billingPartyAddress = invdtl.get("billingPartyAddress1")+" "+invdtl.get("billingPartyAddress2")+" "+invdtl.get("billingPartyAddress3");
	
		
		Map<String,String> compdtl = miscellaneousInvPrintService.getCompanyAddressDetails(bid,cid);
		System.out.println(compdtl.toString());
		String branchAddress = compdtl.get("baddr1")+" "+compdtl.get("baddr2")+" "+compdtl.get("baddr3");
		
		Context context = new Context();
		
		
		context.setVariable("logo", helperMethods.getImageByPath("logo"));
		context.setVariable("QR", helperMethods.getImageByPath("QR"));
		
		context.setVariable("cname", compdtl.get("cname"));
		context.setVariable("cadd1", compdtl.get("cadd1"));
		context.setVariable("cadd2", compdtl.get("cadd2"));
		context.setVariable("cadd3", compdtl.get("cadd3"));
		context.setVariable("gst", compdtl.get("gst"));
		context.setVariable("cin", compdtl.get("cin"));
		context.setVariable("pan", compdtl.get("pan"));
		context.setVariable("state", compdtl.get("state"));
		context.setVariable("email", compdtl.get("email"));
		context.setVariable("branchAddress", branchAddress);
		context.setVariable("branchPin", compdtl.get("branchPin"));
		context.setVariable("companyPin", compdtl.get("companyPin"));
		
		
		context.setVariable("billingPartyAddress", billingPartyAddress);
//		context.setVariable("chaAddress", chaAddress);
		context.setVariable("invoiceNo",invoiceNo );
		context.setVariable("invoiceDate", invdtl.get("invoiceDate"));
//		context.setVariable("invoicevalDate", invdtl.get("invoicevalDate"));
//		context.setVariable("shippingLine", invdtl.get("shippingLine"));
//		context.setVariable("shippingAgent", invdtl.get("shippingAgent"));
		context.setVariable("BillingParty", invdtl.get("BillingParty"));
		context.setVariable("State", invdtl.get("State"));
		context.setVariable("GSTIN", invdtl.get("GSTIN"));
		context.setVariable("PAN", invdtl.get("PAN"));
//		context.setVariable("cha", invdtl.get("cha"));
//		context.setVariable("IRN", invdtl.get("IRN"));
		String isBos1 =invdtl.get("isBOS");
		context.setVariable("isBOS", (isBos1 != null) ? isBos1 : "N");
		context.setVariable("createdBy", invdtl.get("createdBy"));
		
		
		
		// payment details 
				List<Object[]> paydtl = miscellaneousInvPrintRepository.getPaymentDetails(cid, bid,invoiceNo);
				String creditFlag ="";
				List<Map<String,String>> paymap = new ArrayList<>();
				
				if(!paydtl.isEmpty()) {
					for (Object[] row : paydtl) {
						
						Map<String,String> map2= new HashMap<>();
						 	String paymode = (String)row[0];   	   
				    	    String checqNO = (String)row[1];
				    	    String checqDate = (String)row[2];
				    	    String bankDetail = (String)row[3];
				    	    
				    	    BigDecimal amnt = (BigDecimal) row[4];
//						    String amount =String.format("%.2f",amnt);
				    	    String amount = amnt.setScale(2, BigDecimal.ROUND_DOWN).toString(); 
						    
						    creditFlag = (row[6] != null) ? row[6].toString() : "N";
						    
						    map2.put("paymode",paymode);
						    map2.put("checqNO",checqNO);
						    map2.put("checqDate",checqDate);
						    map2.put("bankDetail",bankDetail);
						    map2.put("amount",amount);
						    
						    
						    paymap.add(map2);
						
					}

		    	    context.setVariable("list2",paymap);
		    	    context.setVariable("creditFlag",creditFlag);
		    	    
		    	    
		    	    
		    	    
		    	    String recievedpayment ="";
		    	    BigDecimal sum3 = BigDecimal.ZERO;
		    	    for(Map<String,String> paymap1:paymap) {
		    	    	
		        	    String am1 = paymap1.get("amount");
		    			BigDecimal am2 = new BigDecimal(am1);
		    			sum3 = sum3.add(am2);
		    	    	
		    	    }
		    	    
//		    	    recievedpayment = String.format("%.2f",sum3);
		    	    recievedpayment = sum3.setScale(2, BigDecimal.ROUND_DOWN).toString();
					
					 
					
					 
					 String amountInWords = convertAmountToWords(sum3);
					 String AmountInWords2 = "₹ " + amountInWords;
					 
					 context.setVariable("AmountInWords2", AmountInWords2);
					 context.setVariable("recievedpayment", recievedpayment);
		    	    
		    
		    	}
				
				
				Object bankdtl = miscellaneousInvPrintRepository.getBankDetails(cid, bid);
				if (bankdtl != null) {
		    	    Object[] fields = (Object[]) bankdtl;
		    	    
		    	    String bankName = (String)fields[0];   	   
		    	    String bankAccNo = (String)fields[1];
		    	    String bankifsc = (String)fields[2];
		    	    String bankAddr = (String)fields[3];
		    	    
		    	    
		    	    
		    	    context.setVariable("bankName",bankName);
		    		context.setVariable("bankAcc",bankAccNo);
		    		context.setVariable("bankIfsc",bankifsc);
		    		context.setVariable("bankAdd",bankAddr);
		    	    
		    	   
		    	}
				
				
				List<Map<String,String>> itemList = miscellaneousInvPrintService.getDetailsOfBill(cid, bid, invoiceNo);
				if(!itemList.isEmpty()) {
					
					
					List<Map<String,String>> sacwiseTotal = new ArrayList<>();
					Set<BigDecimal> distinctTaxper = new HashSet<>();
					Map<String,BigDecimal>  bysacTotal = new HashMap<>();
					Map<String,String> sacDesc = new HashMap<>();
					context.setVariable("itemList",itemList);
					System.out.println(itemList.toString());
					
					BigDecimal sum = BigDecimal.ZERO;
					for(Map<String,String> item:itemList) {
						
						 
						 BigDecimal val = new BigDecimal(item.get("amount1").trim());
						 sum = sum.add(val);
						 
						 String sacCode = item.get("sac");
						 
						 BigDecimal amount2 = new BigDecimal(item.get("amount1").trim());
						 if(bysacTotal.containsKey(sacCode)) {
							 bysacTotal.put(sacCode,bysacTotal.get(sacCode).add(amount2));
							 
							 
						 }
						 else {
							
							 bysacTotal.put(sacCode,amount2);
							 
						 }
						 
						 if(!sacDesc.containsKey(sacCode))
							 sacDesc.put(sacCode,item.get("sacDesc"));
						 
						 
						 BigDecimal taxperc2 = new BigDecimal(item.get("taxPerc").trim());
						 distinctTaxper.add(taxperc2);
						 
						 
					}
					
		List<BigDecimal> distinctTaxList  = new ArrayList<>(distinctTaxper);
					
//					BigDecimal taxRate1 = new BigDecimal("12.00");
//					BigDecimal taxRate2 = new BigDecimal("18.00");
//					
//					List<BigDecimal> distinctTaxList = Arrays.asList(taxRate1, taxRate2);
					
					List<Map<String, Double>> taxRatePairs = new ArrayList<>();
					for (BigDecimal taxRate : distinctTaxList) {
//					    Map<String, Double> taxPair = new HashMap<>();
					    Map<String, Double> taxPair = new LinkedHashMap<>();
					    // divide by 2 to get CGST and SGST

					    BigDecimal halfRate = taxRate.divide(new BigDecimal("2.00"), 2, RoundingMode.HALF_UP);
					    
					    taxPair.put("CGST", halfRate.doubleValue());
					    taxPair.put("SGST", halfRate.doubleValue());

					    taxRatePairs.add(taxPair); 
					}
					
					System.out.println(taxRatePairs.toString());
					
					Boolean Key =false;
					
					context.setVariable("taxRatePairs",taxRatePairs);
					
//					context.setVariable("Key", Key);
					
					System.out.println("taxrates"+taxRatePairs.toString());
					
					
					System.out.println("sum is "+sum);
					System.out.println("sacwiseAddition "+bysacTotal.toString()+"sac desc"+sacDesc.toString());
//					String total_Sum =String.format("%.2f",sum);
					
					String total_Sum =sum.setScale(2, BigDecimal.ROUND_DOWN).toString();
					context.setVariable("total_Sum",total_Sum);
					
					List<List<Map<String,String>>> gstlist1 = new ArrayList<>();
					for(String sac :bysacTotal.keySet()) {
						List<Map<String,String>> gstlist  = new ArrayList<>();
						Map<String, String> entry = new HashMap<>();
						entry.put("sac", sac);
						entry.put("sacDesc1", sacDesc.getOrDefault(sac, ""));
//						String sact1 =String.format("%.2f",bysacTotal.get(sac));
						
						String sact1 = bysacTotal.get(sac).setScale(2, BigDecimal.ROUND_DOWN).toString();
						
						entry.put("sact1",sact1);
						
						
						
//						sacwiseTotal.add(entry);
//						"null".equals(invdtl.get("Igst")) || "Y".equals(invdtl.get("Igst"))
						System.out.println("igst val"+invdtl.get("Igst"));
						if("Y".equals(invdtl.get("Igst")) ){
							Key=true;
							System.out.println("inside igst");
							for (Map<String,Double> taxpair :taxRatePairs) {
								
//								Map<String,String> map2 = new HashMap<>();
								
								BigDecimal totalAmt =bysacTotal.get(sac);
								
								BigDecimal taxperc = BigDecimal.valueOf(taxpair.get("CGST")).divide(BigDecimal.valueOf(100)).add( BigDecimal.valueOf(taxpair.get("SGST")).divide(BigDecimal.valueOf(100)));
								
								System.out.println("tax perc"+taxperc);
								BigDecimal taxAmount = totalAmt.multiply(taxperc); 
								taxAmount =taxAmount.setScale(2, BigDecimal.ROUND_DOWN);
								
								
														
								BigDecimal finalamt = totalAmt.add(taxAmount);
								
//								 String taxAmountI1 =String.format("%.2f",taxAmount);
								 String taxAmountI1 =taxAmount.toString();
//								 String finalamtI1 =String.format("%.2f",finalamt);
								 String finalamtI1= finalamt.setScale(2, BigDecimal.ROUND_DOWN).toString();
								 
								 String taxperc1 =String.format("%.2f",taxperc.multiply(BigDecimal.valueOf(100)));
								 
								 boolean isMatch = miscellaneousInvPrintService.sacNoAndTaxpercMatch(sac,taxperc1.trim() ,itemList );
								 
								 if(isMatch)
								 {
									 entry.put("taxAmountI1", taxAmountI1);
									 entry.put("finalamtI1", finalamtI1);
								 }
								 else {
									 
									 
									 entry.put("taxAmountI1Alt", null);
//									 entry.put("finalamtI1", null);
								 }
								 
								 
								
								 
//								 TotalCal.add(map2);
								 
			
								
							}
							
							
								
						}
						else {
							Key =false;
							System.out.println("inside Cgst");
							
								
							
								for (Map<String,Double> taxpair :taxRatePairs) {
								
								Map<String,String> map2 = new HashMap<>();
								
								BigDecimal totalAmt =bysacTotal.get(sac);
								System.out.println("totalAmt"+totalAmt);
								
								
								BigDecimal taxperc = BigDecimal.valueOf(taxpair.get("CGST")).divide(BigDecimal.valueOf(100)).add( BigDecimal.valueOf(taxpair.get("SGST")).divide(BigDecimal.valueOf(100)));
								
								
								System.out.println("tax perc"+taxperc);
								BigDecimal halftaxperc = taxperc.divide(new BigDecimal("2.00"), 2, RoundingMode.HALF_UP);
								
								
								
								String taxperc1 =String.format("%.2f",taxperc.multiply(BigDecimal.valueOf(100)));
								 
								 boolean isMatch = miscellaneousInvPrintService.sacNoAndTaxpercMatch(sac,taxperc1.trim() ,itemList );
								 
								 if(isMatch) {
									 
									 	BigDecimal taxAmount = totalAmt.multiply(halftaxperc); 
										taxAmount=taxAmount.setScale(2, BigDecimal.ROUND_DOWN);
										System.out.println("taxAmount"+taxAmount);
										
										BigDecimal totaltaxamt = taxAmount.multiply(BigDecimal.valueOf(2));
										
										System.out.println("totaltaxamt"+totaltaxamt);
									 	BigDecimal finalamt = totalAmt.add(totaltaxamt);
										System.out.println("finalamt"+finalamt);
										String finalamtI1= finalamt.setScale(2, BigDecimal.ROUND_DOWN).toString();
										System.out.println("finalamtI1"+finalamtI1);
										String taxAmountI1 =taxAmount.setScale(2, BigDecimal.ROUND_DOWN).toString();
										System.out.println("taxAmountI1"+taxAmountI1);
										
										
										entry.put("taxAmountI1", taxAmountI1);
										entry.put("finalamtI1", finalamtI1);
										
										map2.put("taxAmountI1",taxAmountI1 );
//										map2.put("finalamtI1",finalamtI1 );
//										
								 }
								 
								 else {
									 
//									 entry.put("taxAmountAlt1", "0.00");
									 map2.put("taxAmountI1","0.00" );
//									 entry.put("finalamtI1", null);
								 }
								 
								 
								 
														
								
								
//								 String taxAmountI1 =String.format("%.2f",taxAmount);
								
//								 String finalamtI1 =String.format("%.2f",finalamt);
								
								 
								 
//								 String taxperc1 =String.format("%.2f",taxperc.multiply(BigDecimal.valueOf(100)));
//								 
//								 boolean isMatch = exportinvoiceprintService.sacNoAndTaxpercMatch(sac,taxperc1.trim() ,itemList );
								 
//								 if(isMatch)
//								 {
//									 
//									 
//								 }
//								
								 
								
								 
//								 TotalCal.add(map2);
								 
			
								 gstlist.add(map2);
								 gstlist1.add(gstlist);
								 
							}
							
							
							
							
							
							
							
						}
						
						sacwiseTotal.add(entry);
						
					}
					
					context.setVariable("gstlist", gstlist1);
					
					System.out.println(sacwiseTotal.toString());
					
					String sumint = "";
					String finaltotal ="";
					BigDecimal sum1 = BigDecimal.ZERO;
					BigDecimal sum2 = BigDecimal.ZERO;
					for(Map <String,String> mapt : sacwiseTotal) {
						
						String am1 = mapt.get("taxAmountI1");
						BigDecimal am2 = new BigDecimal(am1);
						sum1 = sum1.add(am2);
						
						String am3= mapt.get("finalamtI1");
						BigDecimal am4 = new BigDecimal(am3);
						sum2= sum2.add(am4);
						
						
										
						
					}
					
//					 sumint = String.format("%.2f",sum1);
					
					 sumint= sum1.setScale(2, BigDecimal.ROUND_DOWN).toString();
//					 finaltotal = String.format("%.2f",sum2);
					 
					 finaltotal= sum2.setScale(2, BigDecimal.ROUND_DOWN).toString();
					 
					 System.out.println("sumint"+sumint+" finaltotal"+finaltotal);
					 
					 String amountInWords = convertAmountToWords(sum2);
					 String AmountInWords = "₹ " + amountInWords;
					
					
					
					context.setVariable("Key", Key);
					context.setVariable("sacdataList", sacwiseTotal);
					context.setVariable("sumint", sumint);
					context.setVariable("finaltotal", finaltotal);
					
					context.setVariable("AmountInWords", AmountInWords);
					
					
					
//					context.setVariable("TotalCAL", TotalCal);
					
//					System.out.println("total Cal "+TotalCal.toString());
					
				}
		
		
		
		
		
		
		
		
		String htmlContent = templateEngine.process("MiscellaneousinvoicePrint.html", context);
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
	
	
	
	
	
	@PostMapping("/vendorInvPrintpdf/{cid}/{bid}/{invoiceNo}")
	public ResponseEntity<?> generateVendorInvoicePdf(@PathVariable("cid") String cid, @PathVariable("bid") String bid ,@PathVariable("invoiceNo") String invoiceNo
			 ) throws DocumentException{

		
		System.out.println("inside miscellaneousInvPrintpdf  controller ");
		System.out.println(cid+" "+bid);
		
//		String profitcentreId ="N00002";
		Map<String,String> invdtl = miscellaneousInvPrintService.getInvoiceDetails1(cid, bid,invoiceNo);
		String billingPartyAddress = invdtl.get("billingPartyAddress1")+" "+invdtl.get("billingPartyAddress2")+" "+invdtl.get("billingPartyAddress3");
	
		
		Map<String,String> compdtl = miscellaneousInvPrintService.getCompanyAddressDetails(bid,cid);
		System.out.println(compdtl.toString());
		String branchAddress = compdtl.get("baddr1")+" "+compdtl.get("baddr2")+" "+compdtl.get("baddr3");
		
		Context context = new Context();
		
		context.setVariable("logo", helperMethods.getImageByPath("logo"));
		context.setVariable("QR", helperMethods.getImageByPath("QR"));
		
		
		context.setVariable("cname", compdtl.get("cname"));
		context.setVariable("cadd1", compdtl.get("cadd1"));
		context.setVariable("cadd2", compdtl.get("cadd2"));
		context.setVariable("cadd3", compdtl.get("cadd3"));
		context.setVariable("gst", compdtl.get("gst"));
		context.setVariable("cin", compdtl.get("cin"));
		context.setVariable("pan", compdtl.get("pan"));
		context.setVariable("state", compdtl.get("state"));
		context.setVariable("email", compdtl.get("email"));
		context.setVariable("branchAddress", branchAddress);
		context.setVariable("branchPin", compdtl.get("branchPin"));
		context.setVariable("companyPin", compdtl.get("companyPin"));
		
		
		context.setVariable("billingPartyAddress", billingPartyAddress);
//		context.setVariable("chaAddress", chaAddress);
		context.setVariable("invoiceNo",invoiceNo );
		context.setVariable("invoiceDate", invdtl.get("invoiceDate"));
		context.setVariable("lastInvoiceNo",invdtl.get("lastInvoiceNo") );
		context.setVariable("lastInvoiceDate", invdtl.get("lastInvoiceDate"));
//		context.setVariable("invoicevalDate", invdtl.get("invoicevalDate"));
//		context.setVariable("shippingLine", invdtl.get("shippingLine"));
//		context.setVariable("shippingAgent", invdtl.get("shippingAgent"));
		context.setVariable("BillingParty", invdtl.get("BillingParty"));
		context.setVariable("State", invdtl.get("State"));
		context.setVariable("GSTIN", invdtl.get("GSTIN"));
		context.setVariable("PAN", invdtl.get("PAN"));
//		context.setVariable("cha", invdtl.get("cha"));
//		context.setVariable("IRN", invdtl.get("IRN"));
		String isBos1 =invdtl.get("isBOS");
		context.setVariable("isBOS", (isBos1 != null) ? isBos1 : "N");
		context.setVariable("createdBy", invdtl.get("createdBy"));
		
		
		
		// payment details 
//				List<Object[]> paydtl = miscellaneousInvPrintRepository.getPaymentDetails(cid, bid,invoiceNo);
//				String creditFlag ="";
//				List<Map<String,String>> paymap = new ArrayList<>();
//				
//				if(!paydtl.isEmpty()) {
//					for (Object[] row : paydtl) {
//						
//						Map<String,String> map2= new HashMap<>();
//						 	String paymode = (String)row[0];   	   
//				    	    String checqNO = (String)row[1];
//				    	    String checqDate = (String)row[2];
//				    	    String bankDetail = (String)row[3];
//				    	    
//				    	    BigDecimal amnt = (BigDecimal) row[4];
////						    String amount =String.format("%.2f",amnt);
//				    	    String amount = amnt.setScale(2, BigDecimal.ROUND_DOWN).toString(); 
//						    
//						    creditFlag = (row[6] != null) ? row[6].toString() : "N";
//						    
//						    map2.put("paymode",paymode);
//						    map2.put("checqNO",checqNO);
//						    map2.put("checqDate",checqDate);
//						    map2.put("bankDetail",bankDetail);
//						    map2.put("amount",amount);
//						    
//						    
//						    paymap.add(map2);
//						
//					}
//
//		    	    context.setVariable("list2",paymap);
//		    	    context.setVariable("creditFlag",creditFlag);
//		    	    
//		    	    
//		    	    
//		    	    
//		    	    String recievedpayment ="";
//		    	    BigDecimal sum3 = BigDecimal.ZERO;
//		    	    for(Map<String,String> paymap1:paymap) {
//		    	    	
//		        	    String am1 = paymap1.get("amount");
//		    			BigDecimal am2 = new BigDecimal(am1);
//		    			sum3 = sum3.add(am2);
//		    	    	
//		    	    }
//		    	    
////		    	    recievedpayment = String.format("%.2f",sum3);
//		    	    recievedpayment = sum3.setScale(2, BigDecimal.ROUND_DOWN).toString();
//					
//					 
//					
//					 
//					 String amountInWords = convertAmountToWords(sum3);
//					 String AmountInWords2 = "₹ " + amountInWords;
//					 
//					 context.setVariable("AmountInWords2", AmountInWords2);
//					 context.setVariable("recievedpayment", recievedpayment);
//		    	    
//		    
//		    	}
				
				
//				Object bankdtl = miscellaneousInvPrintRepository.getBankDetails(cid, bid);
//				if (bankdtl != null) {
//		    	    Object[] fields = (Object[]) bankdtl;
		    	    
		    	    String bankName = invdtl.get("bankName");   	   
		    	    String bankAccNo = invdtl.get("accNo");
		    	    String bankifsc = invdtl.get("ifsc");
		    	    String bankAddr = invdtl.get("bankAddress");
		    	    String accountName = invdtl.get("accountName");
		    	    
		    	    context.setVariable("accountName",accountName);
		    	    context.setVariable("bankName",bankName);
		    		context.setVariable("bankAcc",bankAccNo);
		    		context.setVariable("bankIfsc",bankifsc);
		    		context.setVariable("bankAdd",bankAddr);
		    	    
		    	   
//		    	}
				
				
				List<Map<String,String>> itemList = miscellaneousInvPrintService.getDetailsOfBill1(cid, bid, invoiceNo);
				if(!itemList.isEmpty()) {
					
					
					List<Map<String,String>> sacwiseTotal = new ArrayList<>();
					Set<BigDecimal> distinctTaxper = new HashSet<>();
					Map<String,BigDecimal>  bysacTotal = new HashMap<>();
					Map<String,String> sacDesc = new HashMap<>();
					context.setVariable("itemList",itemList);
					System.out.println(itemList.toString());
					
					BigDecimal sum = BigDecimal.ZERO;
					for(Map<String,String> item:itemList) {
						
						 
						 BigDecimal val = new BigDecimal(item.get("amount1").trim());
						 sum = sum.add(val);
						 
						 String sacCode = item.get("sac");
						 
						 BigDecimal amount2 = new BigDecimal(item.get("amount1").trim());
						 if(bysacTotal.containsKey(sacCode)) {
							 bysacTotal.put(sacCode,bysacTotal.get(sacCode).add(amount2));
							 
							 
						 }
						 else {
							
							 bysacTotal.put(sacCode,amount2);
							 
						 }
						 
						 if(!sacDesc.containsKey(sacCode))
							 sacDesc.put(sacCode,item.get("sacDesc"));
						 
						 
						 BigDecimal taxperc2 = new BigDecimal(item.get("taxPerc").trim());
						 distinctTaxper.add(taxperc2);
						 
						 
					}
					
		List<BigDecimal> distinctTaxList  = new ArrayList<>(distinctTaxper);
					
//					BigDecimal taxRate1 = new BigDecimal("12.00");
//					BigDecimal taxRate2 = new BigDecimal("18.00");
//					
//					List<BigDecimal> distinctTaxList = Arrays.asList(taxRate1, taxRate2);
					
					List<Map<String, Double>> taxRatePairs = new ArrayList<>();
					for (BigDecimal taxRate : distinctTaxList) {
//					    Map<String, Double> taxPair = new HashMap<>();
					    Map<String, Double> taxPair = new LinkedHashMap<>();
					    // divide by 2 to get CGST and SGST

					    BigDecimal halfRate = taxRate.divide(new BigDecimal("2.00"), 2, RoundingMode.HALF_UP);
					    
					    taxPair.put("CGST", halfRate.doubleValue());
					    taxPair.put("SGST", halfRate.doubleValue());

					    taxRatePairs.add(taxPair); 
					}
					
					System.out.println(taxRatePairs.toString());
					
					Boolean Key =false;
					
					context.setVariable("taxRatePairs",taxRatePairs);
					
//					context.setVariable("Key", Key);
					
					System.out.println("taxrates"+taxRatePairs.toString());
					
					
					System.out.println("sum is "+sum);
					System.out.println("sacwiseAddition "+bysacTotal.toString()+"sac desc"+sacDesc.toString());
//					String total_Sum =String.format("%.2f",sum);
					
					String total_Sum =sum.setScale(2, BigDecimal.ROUND_DOWN).toString();
					context.setVariable("total_Sum",total_Sum);
					
					List<List<Map<String,String>>> gstlist1 = new ArrayList<>();
					for(String sac :bysacTotal.keySet()) {
						List<Map<String,String>> gstlist  = new ArrayList<>();
						Map<String, String> entry = new HashMap<>();
						entry.put("sac", sac);
						entry.put("sacDesc1", sacDesc.getOrDefault(sac, ""));
//						String sact1 =String.format("%.2f",bysacTotal.get(sac));
						
						String sact1 = bysacTotal.get(sac).setScale(2, BigDecimal.ROUND_DOWN).toString();
						
						entry.put("sact1",sact1);
						
						
						
//						sacwiseTotal.add(entry);
//						"null".equals(invdtl.get("Igst")) || "Y".equals(invdtl.get("Igst"))
						System.out.println("igst val"+invdtl.get("Igst"));
						if("Y".equals(invdtl.get("Igst")) ){
							Key=true;
							System.out.println("inside igst");
							for (Map<String,Double> taxpair :taxRatePairs) {
								
//								Map<String,String> map2 = new HashMap<>();
								
								BigDecimal totalAmt =bysacTotal.get(sac);
								
								BigDecimal taxperc = BigDecimal.valueOf(taxpair.get("CGST")).divide(BigDecimal.valueOf(100)).add( BigDecimal.valueOf(taxpair.get("SGST")).divide(BigDecimal.valueOf(100)));
								
								System.out.println("tax perc"+taxperc);
								BigDecimal taxAmount = totalAmt.multiply(taxperc); 
								taxAmount =taxAmount.setScale(2, BigDecimal.ROUND_DOWN);
								
								
														
								BigDecimal finalamt = totalAmt.add(taxAmount);
								
//								 String taxAmountI1 =String.format("%.2f",taxAmount);
								 String taxAmountI1 =taxAmount.toString();
//								 String finalamtI1 =String.format("%.2f",finalamt);
								 String finalamtI1= finalamt.setScale(2, BigDecimal.ROUND_DOWN).toString();
								 
								 String taxperc1 =String.format("%.2f",taxperc.multiply(BigDecimal.valueOf(100)));
								 
								 boolean isMatch = miscellaneousInvPrintService.sacNoAndTaxpercMatch(sac,taxperc1.trim() ,itemList );
								 
								 if(isMatch)
								 {
									 entry.put("taxAmountI1", taxAmountI1);
									 entry.put("finalamtI1", finalamtI1);
								 }
								 else {
									 
									 
									 entry.put("taxAmountI1Alt", null);
//									 entry.put("finalamtI1", null);
								 }
								 
								 
								
								 
//								 TotalCal.add(map2);
								 
			
								
							}
							
							
								
						}
						else {
							Key =false;
							System.out.println("inside Cgst");
							
								
							
								for (Map<String,Double> taxpair :taxRatePairs) {
								
								Map<String,String> map2 = new HashMap<>();
								
								BigDecimal totalAmt =bysacTotal.get(sac);
								System.out.println("totalAmt"+totalAmt);
								
								
								BigDecimal taxperc = BigDecimal.valueOf(taxpair.get("CGST")).divide(BigDecimal.valueOf(100)).add( BigDecimal.valueOf(taxpair.get("SGST")).divide(BigDecimal.valueOf(100)));
								
								
								System.out.println("tax perc"+taxperc);
								BigDecimal halftaxperc = taxperc.divide(new BigDecimal("2.00"), 2, RoundingMode.HALF_UP);
								
								
								
								String taxperc1 =String.format("%.2f",taxperc.multiply(BigDecimal.valueOf(100)));
								 
								 boolean isMatch = miscellaneousInvPrintService.sacNoAndTaxpercMatch(sac,taxperc1.trim() ,itemList );
								 
								 if(isMatch) {
									 
									 	BigDecimal taxAmount = totalAmt.multiply(halftaxperc); 
										taxAmount=taxAmount.setScale(2, BigDecimal.ROUND_DOWN);
										System.out.println("taxAmount"+taxAmount);
										
										BigDecimal totaltaxamt = taxAmount.multiply(BigDecimal.valueOf(2));
										
										System.out.println("totaltaxamt"+totaltaxamt);
									 	BigDecimal finalamt = totalAmt.add(totaltaxamt);
										System.out.println("finalamt"+finalamt);
										String finalamtI1= finalamt.setScale(2, BigDecimal.ROUND_DOWN).toString();
										System.out.println("finalamtI1"+finalamtI1);
										String taxAmountI1 =taxAmount.setScale(2, BigDecimal.ROUND_DOWN).toString();
										System.out.println("taxAmountI1"+taxAmountI1);
										
										
										entry.put("taxAmountI1", taxAmountI1);
										entry.put("finalamtI1", finalamtI1);
										
										map2.put("taxAmountI1",taxAmountI1 );
//										map2.put("finalamtI1",finalamtI1 );
//										
								 }
								 
								 else {
									 
//									 entry.put("taxAmountAlt1", "0.00");
									 map2.put("taxAmountI1","0.00" );
//									 entry.put("finalamtI1", null);
								 }
								 
								 
								 
														
								
								
//								 String taxAmountI1 =String.format("%.2f",taxAmount);
								
//								 String finalamtI1 =String.format("%.2f",finalamt);
								
								 
								 
//								 String taxperc1 =String.format("%.2f",taxperc.multiply(BigDecimal.valueOf(100)));
//								 
//								 boolean isMatch = exportinvoiceprintService.sacNoAndTaxpercMatch(sac,taxperc1.trim() ,itemList );
								 
//								 if(isMatch)
//								 {
//									 
//									 
//								 }
//								
								 
								
								 
//								 TotalCal.add(map2);
								 
			
								 gstlist.add(map2);
								 gstlist1.add(gstlist);
								 
							}
							
							
							
							
							
							
							
						}
						
						sacwiseTotal.add(entry);
						
					}
					
					context.setVariable("gstlist", gstlist1);
					
					System.out.println(sacwiseTotal.toString());
					
					String sumint = "";
					String finaltotal ="";
					BigDecimal sum1 = BigDecimal.ZERO;
					BigDecimal sum2 = BigDecimal.ZERO;
					for(Map <String,String> mapt : sacwiseTotal) {
						
						String am1 = mapt.get("taxAmountI1");
						BigDecimal am2 = new BigDecimal(am1);
						sum1 = sum1.add(am2);
						
						String am3= mapt.get("finalamtI1");
						BigDecimal am4 = new BigDecimal(am3);
						sum2= sum2.add(am4);
						
						
										
						
					}
					
//					 sumint = String.format("%.2f",sum1);
					
					 sumint= sum1.setScale(2, BigDecimal.ROUND_DOWN).toString();
//					 finaltotal = String.format("%.2f",sum2);
					 
					 finaltotal= sum2.setScale(2, BigDecimal.ROUND_DOWN).toString();
					 
					 System.out.println("sumint"+sumint+" finaltotal"+finaltotal);
					 
					 String amountInWords = convertAmountToWords(sum2);
					 String AmountInWords = "₹ " + amountInWords;
					
					
					
					context.setVariable("Key", Key);
					context.setVariable("sacdataList", sacwiseTotal);
					context.setVariable("sumint", sumint);
					context.setVariable("finaltotal", finaltotal);
					
					context.setVariable("AmountInWords", AmountInWords);
					
					
					
//					context.setVariable("TotalCAL", TotalCal);
					
//					System.out.println("total Cal "+TotalCal.toString());
					
				}
		
		
		
		
		
		
		
		
		String htmlContent = templateEngine.process("VendorInvoicePrint.html", context);
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
	
}


