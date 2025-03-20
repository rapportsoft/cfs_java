package com.cwms.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.GeneralGateIn;

public interface GeneralReportsRepo extends JpaRepository<GeneralGateIn, String> {

	@Query(value = 
	        "SELECT " +
	        "    DATE_FORMAT(a.Receiving_Date,'%d %b %y') AS ReceivingDate, " +
	        "    a.Importer_Name, " +
	        "    b.Party_Name, " +
	        "    a.BOE_No, " +
	        "    DATE_FORMAT(a.BOE_Date,'%d %b %y') AS BOE_Date, " +
	        "    rc.Commodity_Description, " +
	        "    rc.Container_No, " +
	        "    a.No_Of_20FT, " +
	        "    a.No_Of_40FT, " +
	        "    rc.Gate_In_Packages, " +
	        "    rc.gate_in_weight, " +
	        "    rc.reciving_packages, " +
	        "    rc.reciving_weight, " +
	        "    rc.Type_Of_Package, " +
	        "    rc.Vehicle_No, " +
	        "    a.transporter_name, " +
	        "    a.handling_equip1, " +
	        "    a.handling_equip2, " +
	        "    a.handling_equip3, " +
	        "    a.Owner1, " +
	        "    a.Owner2, " +
	        "    a.Owner3, " +
	        "    rc.Job_No, " +
	        "    a.Remark, " +
	        "    a.Deposit_No " +
	        "FROM generalreceivingcrg a " +
	        "LEFT OUTER JOIN party b " +
	        "    ON a.Company_Id = b.Company_Id " +
	        "    AND a.branch_id = b.branch_id " +
	        "    AND a.CHA = b.Party_Id " +
	        "LEFT OUTER JOIN generalreceivinggateindtl rc " +
	        "    ON a.company_id = rc.company_id " +
	        "    AND a.branch_id = rc.branch_id " +
	        "    AND a.Receiving_Id = rc.Receiving_Id " +
	        "    AND a.deposit_no = rc.deposit_no " +
	        "WHERE a.Company_Id = :companyId " +
	        "    AND a.Branch_Id = :branchId " +
	        "    AND a.Status <> 'D' " +
	        "    AND a.Receiving_Date BETWEEN :startDate AND :endDate " +
	        "AND (:imp IS NULL OR :imp = '' OR a.Importer_Name = :imp) " +
	        "AND (:cha IS NULL OR :cha = '' OR a.cha = :cha) " +
	        "ORDER BY a.Receiving_Date ASC", nativeQuery = true)
	List<Object[]> findReceivingDetails(
	    @Param("companyId") String companyId,
	    @Param("branchId") String branchId,
	    @Param("startDate") Date startDate,
	    @Param("endDate") Date endDate,
	    @Param("imp") String imp,
	    @Param("cha") String cha
	);
	
	

	
	
	
	@Query(value = "SELECT DATE_FORMAT(a.Delivery_Date,'%d %b %y') AS DeliveryDate, " + 
            "a.Importer_Name, b.Party_Name, a.BOE_No, " + 
            "DATE_FORMAT(a.BOE_Date,'%d %b %y') AS BOEDate, " + 
            "dd.Commodity_Description, dd.Container_No, " + 
            "a.No_Of_20FT, a.No_Of_40FT, dd.delivered_packages, dd.delivered_weight, " + 
            "IFNULL(dd.gate_pass_packages,'0'), IFNULL(dd.gate_pass_weight,'0'), dd.type_of_package,gd.yard_location,gd.yard_block,gd.block_cell_no, " + 
            "g.Vehicle_No, a.Transporter_name, a.Handling_Equip, " + 
            "a.Handling_Equip1, a.Handling_Equip2, " + 
            "a.Owner, a.Owner1, a.Owner2, dd.Job_No, a.Comments, " + 
            "IFNULL(a.Remaining_Packages,'0'), " + 
            "IFNULL(a.Delivered_Packages,'0'), " + 
            "IFNULL(a.balanced_packages,'0'), " + 
            "a.Delivery_GW, a.Comments, a.Deposit_No, dd.delivery_id " + 
            "FROM generaldeliverycrg a " + 
            "LEFT OUTER JOIN generaldeliverydetails dd " + 
            "ON a.Company_Id = dd.Company_Id " + 
            "AND a.branch_Id = dd.branch_Id " + 
            "AND a.Delivery_Id = dd.Delivery_Id " + 
            "AND a.receiving_id = dd.receiving_id " + 
            "AND a.deposit_no = dd.deposit_no " + 
            "AND a.boe_no = dd.boe_no " + 
            "LEFT OUTER JOIN party b " + 
            "ON a.Company_Id = b.Company_Id " + 
            "AND a.branch_id = b.branch_id " + 
            "AND a.CHA = b.Party_Id " + 
            "LEFT OUTER JOIN generalcargogatepass g " + 
            "ON a.Company_Id = g.Company_Id " + 
            "AND a.branch_id = g.branch_id " +  
            "AND a.Delivery_Id = g.Delivery_Id " + 
            "AND a.BOE_No = g.BOE_No " + 
            "AND a.receiving_id = g.receiving_id " + 
            "AND a.deposit_no = g.deposit_no " + 
            "AND g.commodity_id = dd.commodity_id " + 
            "LEFT OUTER JOIN generaldeliverygrid gd " + 
            "ON a.Company_Id = gd.Company_Id " + 
            "AND a.branch_id = gd.branch_id " +  
            "AND a.Delivery_Id = gd.Delivery_Id " + 
            "AND a.receiving_id = gd.receiving_id " +  
            "AND a.boe_no = gd.boe_no " +  
            "WHERE a.Company_Id = :companyId " + 
            "AND a.Branch_Id = :branchId " + 
            "AND a.Status = 'A' " +  
            "AND a.Delivery_Date BETWEEN :startDate AND :endDate " +  
            "AND (:imp IS NULL OR :imp = '' OR a.Importer_Name = :imp) " +
	        "AND (:cha IS NULL OR :cha = '' OR a.cha = :cha) " +
	        "GROUP BY dd.commodity_id " +
            "ORDER BY a.Delivery_Date ASC", 
    nativeQuery = true)
List<Object[]> getGeneralDeliveryReport(
 @Param("companyId") String companyId,
 @Param("branchId") String branchId,
 @Param("startDate") Date startDate,
 @Param("endDate") Date endDate,
 @Param("imp") String imp,
 @Param("cha") String cha
);

@Query(value = "" +
        "SELECT c.deposit_no, c.boe_no,  DATE_FORMAT(c.boe_date, '%d-%m-%Y %H:%i'), c.importer_name, p.party_name AS chanme, " +
        "gd.act_commodity_id, gd.commodity_description, gd.gate_in_packages, gd.gate_in_weight, " +
        "gd.reciving_packages, gd.reciving_weight, gd.delivered_packages, gd.delivered_weight, " +
        "gp.gate_pass_packages, gp.gate_pass_weight, " +
        "COALESCE(gd.reciving_packages, 0) - COALESCE(gd.delivered_packages, 0) AS balance_receiving_packages, " +
        "COALESCE(gd.delivered_packages, 0) - COALESCE(gp.gate_pass_packages, 0) AS gatepass_balance, " +
        "d.yard_location, d.yard_block, d.block_cell_no, gd.job_no, gd.job_trans_id,gd.commodity_id " +
        "FROM generalreceivingcrg c " +
        "LEFT OUTER JOIN generalreceivinggateindtl gd ON c.company_id = gd.company_id " +
        "AND c.branch_id = gd.branch_id " +
        "AND c.deposit_no = gd.deposit_no " +
        "AND c.receiving_id = gd.receiving_id " +
        "AND c.job_no = gd.job_no " +
        "AND c.job_trans_id = gd.job_trans_id " +
        "LEFT OUTER JOIN generalreceivinggrid d ON gd.company_id = d.company_id " +
        "AND gd.branch_id = d.branch_id " +
        "AND gd.receiving_id = d.receiving_id " +
        "LEFT OUTER JOIN party p ON c.company_id = p.company_id " +
        "AND c.branch_id = p.branch_id " +
        "AND c.cha = p.party_id " +
        "LEFT OUTER JOIN generalcargogatepass gp ON c.company_id = gp.company_id " +
        "AND c.branch_id = gp.branch_id " +
        "AND c.deposit_no = gp.deposit_no " +
        "AND c.receiving_id = gp.receiving_id " +
        "WHERE c.company_id = :companyId " +
        "AND c.branch_id = :branchId " +
        "AND c.receiving_date <= :endDate " +
        "AND (:imp IS NULL OR :imp = '' OR c.Importer_Name = :imp) " +
        "AND (:cha IS NULL OR :cha = '' OR c.cha = :cha) " +
        "GROUP BY gd.commodity_id ", nativeQuery = true)
List<Object[]> getReceivingDetails(@Param("companyId") String companyId, 
                                   @Param("branchId") String branchId, 
                                   @Param("endDate") Date endDate,
                                   @Param("imp") String imp,
                                   @Param("cha") String cha);
}
