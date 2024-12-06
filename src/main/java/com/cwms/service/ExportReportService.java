package com.cwms.service;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.cwms.entities.Branch;
import com.cwms.entities.GateIn;
import com.cwms.repository.BranchRepo;
import com.cwms.repository.ExportReportRepositary;

@Service
public class ExportReportService {
	
	@Autowired
	private TemplateEngine templateEngine;
	@Autowired
	private BranchRepo branchRepo;
	
	@Autowired
	private ExportReportRepositary reportRepo;
	
	public byte[] getExportTruckWiseGateInReport(String companyId, String branchId, String profitCenterId, String gateInId, String userId)
	{
		try
		{
			 Context context = new Context();
		        Branch branch = branchRepo.getCompleteCompanyAndBranch(companyId, branchId);
		        
			BigDecimal totalPackage = BigDecimal.ZERO;
			BigDecimal totalWeight = BigDecimal.ZERO;
			
		        List<Object[]> exportTruckWiseGateInReport = reportRepo.getExportTruckWiseGateInReport(companyId, branchId, gateInId);
			
		        
		        System.out.println("exportTruckWiseGateInReport : " + exportTruckWiseGateInReport.size());
		        Object[] firstRecord = exportTruckWiseGateInReport.get(0);
		        
		        
		        if (exportTruckWiseGateInReport != null && !exportTruckWiseGateInReport.isEmpty()) {
		            // Iterate through each record
		            for (Object[] record : exportTruckWiseGateInReport) {
		                // Ensure there are enough columns (at least 13 values: index 11 and 12 should exist)
		                if (record.length > 12) {
		                    // Access the values at index 11 (for package) and index 12 (for weight)
		                    BigDecimal valueAt11 = (record[11] instanceof BigDecimal) ? (BigDecimal) record[11] : BigDecimal.ZERO; // Package
		                    BigDecimal valueAt12 = (record[12] instanceof BigDecimal) ? (BigDecimal) record[12] : BigDecimal.ZERO; // Weight

		                    // Add the values to the respective totals
		                    totalPackage = totalPackage.add(valueAt11);
		                    totalWeight = totalWeight.add(valueAt12);
		                }
		            }
		        }
		        
		        
		        System.out.println("firstRecord : ");
		        for (Object element : firstRecord) {
		            System.out.println(element);
		        }		        
		        context.setVariable("gateInId", (String) firstRecord[0]);
		        context.setVariable("gateInDate", (String) firstRecord[1]);
		        context.setVariable("truckNo", (String) firstRecord[4]);
		        context.setVariable("onAccountOf", (String) firstRecord[19]);
		        context.setVariable("cha", (String) firstRecord[6]);
		        context.setVariable("receiptNo", (String) firstRecord[0]);
		        context.setVariable("totalPackage", totalPackage);
		        context.setVariable("totalWeight", totalWeight);
		        context.setVariable("createdBy", (String) firstRecord[14]);
		       
		        
		        
		       
		        
		        
		        
		        context.setVariable("branch", branch);
		        context.setVariable("reportData", exportTruckWiseGateInReport);
			
		        // Process the Thymeleaf template with dynamic values
		        String htmlContent = templateEngine.process("ExportTruckWiseGateIn", context);

		        // Create an ITextRenderer instance
		        ITextRenderer renderer = new ITextRenderer();

		        // Set the PDF page size and margins
		        renderer.setDocumentFromString(htmlContent);
		        renderer.layout();

		        // Generate PDF content
		        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		        renderer.createPDF(outputStream);

		        // Get the PDF bytes
		        byte[] pdfBytes = outputStream.toByteArray();
		        context.clearVariables();

		        return pdfBytes;
		    } catch (Exception e) {
		        System.out.println(e);
		        return null;
		    }
		}
	
	public byte[] getExportTruckWiseJobOrderReport(String companyId, String branchId, String profitCenterId, String gateInId, String userId)
	{
		try
		{
			 Context context = new Context();
		     Branch branch = branchRepo.getCompleteCompanyAndBranch(companyId, branchId);
		        
			BigDecimal totalPackage = BigDecimal.ZERO;
			BigDecimal totalWeight = BigDecimal.ZERO;
			
		        List<Object[]> exportTruckWiseGateInReport = reportRepo.getExportTruckWiseGateInReport(companyId, branchId, gateInId);
			
		        
		        Object[] firstRecord = exportTruckWiseGateInReport.get(0);
		        
		        
		        if (exportTruckWiseGateInReport != null && !exportTruckWiseGateInReport.isEmpty()) {
		            // Iterate through each record
		            for (Object[] record : exportTruckWiseGateInReport) {
		                // Ensure there are enough columns (at least 13 values: index 11 and 12 should exist)
		                if (record.length > 12) {
		                    // Access the values at index 11 (for package) and index 12 (for weight)
		                    BigDecimal valueAt11 = (record[11] instanceof BigDecimal) ? (BigDecimal) record[11] : BigDecimal.ZERO; // Package
		                    BigDecimal valueAt12 = (record[12] instanceof BigDecimal) ? (BigDecimal) record[12] : BigDecimal.ZERO; // Weight

		                    // Add the values to the respective totals
		                    totalPackage = totalPackage.add(valueAt11);
		                    totalWeight = totalWeight.add(valueAt12);
		                }
		            }
		        }
		        
		        
		        
		        context.setVariable("gateInId", (String) firstRecord[0]);
		        context.setVariable("gateInDate", (String) firstRecord[1]);
		        context.setVariable("jobDate", (String) firstRecord[18]);
		        context.setVariable("jobOrderId", (String) firstRecord[17]);
		        context.setVariable("onAccountOf", (String) firstRecord[19]);
		        context.setVariable("cha", (String) firstRecord[6]);
		        context.setVariable("totalPackage", totalPackage);
		        context.setVariable("totalWeight", totalWeight);
		        context.setVariable("createdBy", (String) firstRecord[14]);
		       
		        
		        
		       
		        
		        
		        
		        context.setVariable("branch", branch);
		        context.setVariable("reportData", exportTruckWiseGateInReport);
			
		        // Process the Thymeleaf template with dynamic values
		        String htmlContent = templateEngine.process("ExportTruckWisejobOrder", context);

		        // Create an ITextRenderer instance
		        ITextRenderer renderer = new ITextRenderer();

		        // Set the PDF page size and margins
		        renderer.setDocumentFromString(htmlContent);
		        renderer.layout();

		        // Generate PDF content
		        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		        renderer.createPDF(outputStream);

		        // Get the PDF bytes
		        byte[] pdfBytes = outputStream.toByteArray();
		        context.clearVariables();

		        return pdfBytes;
		    } catch (Exception e) {
		        System.out.println(e);
		        return null;
		    }
		}
	
	
	public byte[] getExportBackToTownReport(String companyId, String branchId, String profitCenterId, String backToTownTransId, String userId)
	{
		try
		{
				Context context = new Context();
		        Branch branch = branchRepo.getCompleteCompanyAndBranch(companyId, branchId);
		        
		
			
		        List<Object[]> exportTruckWiseGateInReport = reportRepo.getExportBackToTownReport(companyId, branchId, "N00004",backToTownTransId);
			
		        
		        System.out.println("exportTruckWiseGateInReport : " + exportTruckWiseGateInReport.size());
		        Object[] firstRecord = exportTruckWiseGateInReport.get(0);
		        
		        
		     
		        
		        System.out.println("firstRecord : ");
		        for (Object element : firstRecord) {
		            System.out.println(element);
		        }		        
		        context.setVariable("workOrderId", (String) firstRecord[4]);
		        context.setVariable("workOrderDate", (String) firstRecord[5]);
		        context.setVariable("truckNo", (String) firstRecord[2]);
		        context.setVariable("lineAgent", (String) firstRecord[11]);
		        context.setVariable("cha", (String) firstRecord[12]);
		       
		        
		        
		       
		        
		        
		        
		        context.setVariable("branch", branch);
		        context.setVariable("reportData", exportTruckWiseGateInReport);
			
		        // Process the Thymeleaf template with dynamic values
		        String htmlContent = templateEngine.process("ExportBackToTown", context);

		        // Create an ITextRenderer instance
		        ITextRenderer renderer = new ITextRenderer();

		        // Set the PDF page size and margins
		        renderer.setDocumentFromString(htmlContent);
		        renderer.layout();

		        // Generate PDF content
		        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		        renderer.createPDF(outputStream);

		        // Get the PDF bytes
		        byte[] pdfBytes = outputStream.toByteArray();
		        context.clearVariables();

		        return pdfBytes;
		    } catch (Exception e) {
		        System.out.println(e);
		        return null;
		    }
		}
	
	
	
	
	
	
	
	public byte[] downLoadExportPortReturnReport(String companyId, String branchId, String profitCenterId, String gateInId, String userId)
	{
		try
		{
				Context context = new Context();
		        Branch branch = branchRepo.getCompleteCompanyAndBranch(companyId, branchId);     
		
			
		        List<GateIn> exportPortReturnReportList = reportRepo.getExportPortReturnReport(companyId, branchId, "N00004",gateInId);
			
		        GateIn  exportPortReturnReport =exportPortReturnReportList.get(0);
		        System.out.println("exportTruckWiseGateInReport : " + exportPortReturnReport);
		        
		        
		        context.setVariable("branch", branch);
		        context.setVariable("reportData", exportPortReturnReport);
			
		        // Process the Thymeleaf template with dynamic values
		        String htmlContent = templateEngine.process("ExportPortReturn", context);

		        // Create an ITextRenderer instance
		        ITextRenderer renderer = new ITextRenderer();

		        // Set the PDF page size and margins
		        renderer.setDocumentFromString(htmlContent);
		        renderer.layout();

		        // Generate PDF content
		        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		        renderer.createPDF(outputStream);

		        // Get the PDF bytes
		        byte[] pdfBytes = outputStream.toByteArray();
		        context.clearVariables();

		        return pdfBytes;
		    } catch (Exception e) {
		        System.out.println(e);
		        return null;
		    }
		}
	
	
	
	
	
	
	
	
}
