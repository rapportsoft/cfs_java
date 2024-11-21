package com.cwms.repository;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.Cfinbondcrg;
import com.cwms.entities.Cfinbondcrg;

public interface InbondCFRepositary extends JpaRepository<Cfinbondcrg, String> {

	// changes chaName to bondingNo in 3rd possition
		@Query("SELECT I.inBondingDate, I.bondingNo, I.bondingDate, I.cha, I.bondValidityDate, I.boeNo, I.boeDate, I.igmNo, "
				+ "I.grossWeight, I.inbondGrossWt, I.cifValue, I.cargoDuty " + "FROM Cfinbondcrg I "
				+ "WHERE I.companyId = :companyId AND I.branchId = :branchId "
				+ "AND Date(I.inBondingDate) BETWEEN :startDate AND :endDate")
		List<Object[]> findinbondDataLiveBondInbond(@Param("companyId") String companyId,
				@Param("branchId") String branchId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

        //EXBOND LIVEBOND
        @Query("SELECT I.bondingNo, I.bondingDate, I.exBondBeNo, I.exBondBeDate, I.igmNo, I.exBondingDate, I.exBondedGw, "
        + "I.exBondedCif, I.exBondedCargoDuty " + "FROM CfExBondCrg I "
        + "WHERE I.companyId = :companyId AND I.branchId = :branchId "
        + "AND Date(I.exBondingDate) BETWEEN :startDate AND :endDate")
        List<Object[]> findinbondDataLiveBondExbond(@Param("companyId") String companyId,
        @Param("branchId") String branchId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);


        
@Query(value = "SELECT COUNT(*) AS count, " 
        + "COALESCE(SUM(CIF_Value), 0) AS cifValueSum, "
		+ "COALESCE(SUM(Cargo_Duty), 0) AS cargoDutySum " 
        + "FROM "
        + "("
        + "    SELECT n1.in_bonding_id, "
		+ "           MAX(n1.CIF_Value) AS CIF_Value, "
		+ "           MAX(n1.Cargo_Duty) AS Cargo_Duty "
		+ "    FROM cfinbondcrg n1 "
		+ "    LEFT JOIN CfExBondCrg e " 
		+ "    ON n1.company_id = e.company_id "
		+ "       AND n1.branch_id = e.branch_id "
		+ "       AND n1.noc_no = e.noc_no "
		+ "       AND n1.noc_trans_id = e.noc_trans_id "
		+ "       AND n1.boe_no = e.boe_no "
		+ "       AND n1.in_bonding_id = e.in_bonding_id " 
		+ "    WHERE n1.company_id = :companyId "
		+ "      AND n1.branch_id = :branchId " 
		+ "      AND n1.section_49 = :section49 " 
		+ "      AND ("
		+ "          CASE "
		+ "              WHEN Date(n1.Extension_Date3) >= :endDate THEN 1 "
		+ "              WHEN Date(n1.Extension_Date2) >= :endDate THEN 1 "
		+ "              WHEN Date(n1.Extension_Date1) >= :endDate THEN 1 "
		+ "              WHEN Date(n1.bond_validity_date) >= :endDate THEN 1 " 
		+ "              ELSE 0 "
		+ "          END) = 1 " 
		+ "      AND (n1.in_bonded_packages - n1.ex_bonded_packages) > 0 "
		+ "      AND Date(n1.in_bonding_date) < :currentDate " 
		+ "    GROUP BY n1.in_bonding_id " 
		+ "    UNION "
		+ "    SELECT n1.in_bonding_id, "
		+ "           MAX(n1.CIF_Value) AS CIF_Value, "
		+ "           MAX(n1.Cargo_Duty) AS Cargo_Duty "
		+ "    FROM cfinbondcrg n1 "
		+ "    LEFT JOIN CfExBondCrg e " 
		+ "    ON n1.company_id = e.company_id "
		+ "       AND n1.branch_id = e.branch_id " 
		+ "       AND n1.noc_no = e.noc_no "
		+ "       AND n1.noc_trans_id = e.noc_trans_id " 
		+ "       AND n1.boe_no = e.boe_no "
		+ "       AND n1.in_bonding_id = e.in_bonding_id " 
		+ "    WHERE n1.company_id = :companyId "
		+ "      AND n1.branch_id = :branchId " 
		+ "      AND n1.section_49 = :section49 " 
		+ "      AND (" 
		+ "          CASE "
		+ "              WHEN Date(n1.Extension_Date3) >= :endDate THEN 1 "
		+ "              WHEN Date(n1.Extension_Date2) >= :endDate THEN 1 "
		+ "              WHEN Date(n1.Extension_Date1) >= :endDate THEN 1 "
		+ "              WHEN Date(n1.bond_validity_date) >= :endDate THEN 1 " 
		+ "              ELSE 0 "
		+ "          END) = 1 "
		+ "      AND Date(n1.in_bonding_date) < :currentDate "
		+ "      AND e.balanced_packages > 0 " 
		+ "      AND Date(e.ex_bonding_date) < :currentDate "
		+ "      AND (e.noc_no, e.boe_no, e.noc_trans_id) NOT IN ("
		+ "          SELECT n.noc_no, n.boe_no, n.noc_trans_id " 
		+ "          FROM CfExBondCrg n "
		+ "          WHERE n.company_id = :companyId " 
		+ "            AND n.branch_id = :branchId "
		+ "            AND Date(n.ex_bonding_date) < :currentDate "
		+ "            AND n.balanced_packages = 0" 
		+ "      ) " 
		+ "    GROUP BY n1.in_bonding_id"
		+ ") "
		+ "AS combined", nativeQuery = true)
List<Object[]> getLiveOpeningBalance(@Param("companyId") String companyId,
		@Param("branchId") String branchId,
		@Param("currentDate") Date currentDate,
		@Param("endDate") Date endDate,
		@Param("section49") String section49);


@Query(value = "WITH SumValues AS ( " 
		+ "    SELECT ex.noc_no, "
		+ "           COALESCE(SUM(ex.ex_bonded_cif), 0) AS cifValueSum, "
		+ "           COALESCE(SUM(ex.ex_bonded_cargo_duty), 0) AS cargoDutySum " 
		+ "    FROM cfinbondcrg i "
		+ "    JOIN CfExBondCrg ex ON i.company_id = ex.company_id "
		+ "                       AND i.branch_id = ex.branch_id "
		+ "                       AND i.noc_no = ex.noc_no "
		+ "                       AND i.noc_trans_id = ex.noc_trans_id "
		+ "                       AND i.boe_no = ex.boe_no "
		+ "                       AND i.in_bonding_id = ex.in_bonding_id " 
		+ "    WHERE ex.company_id = :companyId "
		+ "      AND ex.branch_id = :branchId " 
		+ "      AND ( "
		+ "          CASE "
		+ "              WHEN Date(i.Extension_Date3) >= :endDate THEN 1 "
		+ "              WHEN Date(i.Extension_Date2) >= :endDate THEN 1 "
		+ "              WHEN Date(i.Extension_Date1) >= :endDate THEN 1 "
		+ "              WHEN Date(i.bond_validity_date) >= :endDate THEN 1 " 
		+ "              ELSE 0 "
		+ "          END) = 1 "
		+ "      AND i.status != 'D' " 
		+ "      AND i.section_49 = :section49 " 
		+ "      AND ex.status != 'D' " 
		+ "    GROUP BY ex.noc_no " 
		+ ") "
		+ "SELECT COALESCE(COUNT(DISTINCT n3.in_bonding_id), 0) AS count2, "
		+ "       COALESCE(SUM(n3.CIF_Value), 0) AS cifValueSum2, "
		+ "       COALESCE(SUM(n3.Cargo_Duty), 0) AS cargoDutySum2 " 
		+ "FROM cfinbondcrg n3 "
		+ "WHERE n3.company_id = :companyId " 
		+ "  AND n3.branch_id = :branchId " 
		+ "      AND n3.section_49 = :section49 " 
		+ "  AND ( "
		+ "      CASE "
		+ "          WHEN Date(n3.Extension_Date3) >= :endDate THEN 1 "
		+ "          WHEN Date(n3.Extension_Date2) >= :endDate THEN 1 "
		+ "          WHEN Date(n3.Extension_Date1) >= :endDate THEN 1 "
		+ "          WHEN Date(n3.bond_validity_date) >= :endDate THEN 1 "
		+ "          ELSE 0 "
		+ "      END) = 1 "
		+ "  AND Date(n3.in_bonding_date) BETWEEN :startDate AND :endDate "
		+ "  AND n3.status != 'D' "
		+ "UNION ALL "
		+ "SELECT COALESCE(COUNT(DISTINCT n3.in_bonding_id), 0) AS count2, "
		+ "       COALESCE(SUM(n3.CIF_Value), 0) AS cifValueSum2, "
		+ "       COALESCE(SUM(n3.Cargo_Duty), 0) AS cargoDutySum2 " 
		+ "FROM cfinbondcrg n3 "
		+ "WHERE n3.company_id = :companyId " 
		+ "  AND n3.branch_id = :branchId " 
		+ "      AND n3.section_49 = :section49 " 
		+ "  AND ( "
		+ "      CASE "
		+ "          WHEN Date(n3.Extension_Date3) >= :endDate THEN 1 "
		+ "          WHEN Date(n3.Extension_Date2) >= :endDate THEN 1 "
		+ "          WHEN Date(n3.Extension_Date1) >= :endDate THEN 1 "
		+ "          WHEN Date(n3.bond_validity_date) >= :endDate THEN 1 "
		+ "          ELSE 0 "
		+ "      END) = 1 "
		+ "  AND Date(n3.in_bonding_date) BETWEEN :aprilDate AND :lastDate "
		+ "  AND n3.status != 'D' "
		+ "UNION ALL "
		+ "SELECT COALESCE(COUNT(DISTINCT CASE WHEN ex.balanced_packages = 0 THEN ex.noc_no END), 0) AS count, "
		+ "       COALESCE(SUM(s.cifValueSum), 0) AS cifValueSum, "
		+ "       COALESCE(SUM(s.cargoDutySum), 0) AS cargoDutySum "
		+ "FROM SumValues s "
		+ "JOIN CfExBondCrg ex ON s.noc_no = ex.noc_no "
		+ "WHERE ex.company_id = :companyId "
		+ "  AND ex.branch_id = :branchId "
		+ "  AND ex.balanced_packages = 0 AND Date(ex.ex_bonding_date) BETWEEN :startDate AND :endDate "
		+ "UNION ALL "
		+ "SELECT COALESCE(COUNT(DISTINCT CASE WHEN ex.balanced_packages = 0 THEN ex.noc_no END), 0) AS count, "
		+ "       COALESCE(SUM(s.cifValueSum), 0) AS cifValueSum, "
		+ "       COALESCE(SUM(s.cargoDutySum), 0) AS cargoDutySum "
		+ "FROM SumValues s "
		+ "JOIN CfExBondCrg ex ON s.noc_no = ex.noc_no " 
		+ "WHERE ex.company_id = :companyId "
		+ "  AND ex.branch_id = :branchId " 
//		+ "  AND n3.section_49 = :section49 " 
		+ "  AND ex.balanced_packages = 0 AND Date(ex.ex_bonding_date) BETWEEN :aprilDate AND :lastDate", nativeQuery = true)
List<Object[]> getLiveReceiptsAndDisposal(@Param("companyId") String companyId, 
		@Param("branchId") String branchId,
		@Param("startDate") Date startDate,
		@Param("endDate") Date endDate,
		@Param("aprilDate") Date aprilDate,
		@Param("lastDate") Date lastDate,
		@Param("section49") String section49);


@Query(value = "SELECT COUNT(*) AS count, COALESCE(SUM(CIF_Value), 0) AS cifValueSum, COALESCE(SUM(Cargo_Duty), 0) AS cargoDutySum FROM (SELECT n1.in_bonding_id, MAX(n1.CIF_Value) AS CIF_Value, MAX(n1.Cargo_Duty) AS Cargo_Duty FROM cfinbondcrg n1 LEFT JOIN CfExBondCrg e ON n1.company_id = e.company_id AND n1.branch_id = e.branch_id AND n1.noc_no = e.noc_no AND n1.noc_trans_id = e.noc_trans_id AND n1.boe_no = e.boe_no AND n1.in_bonding_id = e.in_bonding_id WHERE n1.company_id = :cid AND n1.branch_id = :bid AND (CASE WHEN Date(n1.Extension_Date3) >= :endDate THEN 1 WHEN Date(n1.Extension_Date2) >= :endDate THEN 1 WHEN Date(n1.Extension_Date1) >= :endDate THEN 1 WHEN Date(n1.bond_validity_date) >= :endDate THEN 1 ELSE 0 END) = 1 AND (n1.in_bonded_packages - n1.ex_bonded_packages) > 0 AND Date(n1.in_bonding_date) between :start AND :end GROUP BY n1.in_bonding_id "
		+ "UNION "
		+ "SELECT n1.in_bonding_id, MAX(n1.CIF_Value) AS CIF_Value, MAX(n1.Cargo_Duty) AS Cargo_Duty FROM cfinbondcrg n1 LEFT JOIN CfExBondCrg e ON n1.company_id = e.company_id AND n1.branch_id = e.branch_id AND n1.noc_no = e.noc_no AND n1.noc_trans_id = e.noc_trans_id AND n1.boe_no = e.boe_no AND n1.in_bonding_id = e.in_bonding_id WHERE n1.company_id = :cid AND n1.branch_id = :bid AND (CASE WHEN Date(n1.Extension_Date3) >= :endDate THEN 1 WHEN Date(n1.Extension_Date2) >= :endDate THEN 1 WHEN Date(n1.Extension_Date1) >= :endDate THEN 1 WHEN Date(n1.bond_validity_date) >= :endDate THEN 1 ELSE 0 END) = 1 AND Date(n1.in_bonding_date) between :start AND :end AND e.balanced_packages > 0 AND Date(e.ex_bonding_date) between :start AND :end AND (e.noc_no, e.boe_no, e.noc_trans_id) NOT IN (SELECT n.noc_no, n.boe_no, n.noc_trans_id FROM CfExBondCrg n WHERE n.company_id = :cid AND n.branch_id = :bid AND Date(n.ex_bonding_date) between :start AND :end AND n.balanced_packages = 0) GROUP BY n1.in_bonding_id) AS combined "
		+ "UNION ALL "
		+ "SELECT COUNT(*) AS count, COALESCE(SUM(CIF_Value), 0) AS cifValueSum, COALESCE(SUM(Cargo_Duty), 0) AS cargoDutySum FROM (SELECT n1.in_bonding_id, MAX(n1.CIF_Value) AS CIF_Value, MAX(n1.Cargo_Duty) AS Cargo_Duty FROM cfinbondcrg n1 LEFT JOIN CfExBondCrg e ON n1.company_id = e.company_id AND n1.branch_id = e.branch_id AND n1.noc_no = e.noc_no AND n1.noc_trans_id = e.noc_trans_id AND n1.boe_no = e.boe_no AND n1.in_bonding_id = e.in_bonding_id WHERE n1.company_id = :cid AND n1.branch_id = :bid AND (CASE WHEN Date(n1.Extension_Date3) >= :endDate THEN 1 WHEN Date(n1.Extension_Date2) >= :endDate THEN 1 WHEN Date(n1.Extension_Date1) >= :endDate THEN 1 WHEN Date(n1.bond_validity_date) >= :endDate THEN 1 ELSE 0 END) = 1 AND (n1.in_bonded_packages - n1.ex_bonded_packages) > 0 AND Date(n1.in_bonding_date) between :start13 AND :end13 GROUP BY n1.in_bonding_id "
		+ "UNION "
		+ "SELECT n1.in_bonding_id, MAX(n1.CIF_Value) AS CIF_Value, MAX(n1.Cargo_Duty) AS Cargo_Duty FROM cfinbondcrg n1 LEFT JOIN CfExBondCrg e ON n1.company_id = e.company_id AND n1.branch_id = e.branch_id AND n1.noc_no = e.noc_no AND n1.noc_trans_id = e.noc_trans_id AND n1.boe_no = e.boe_no AND n1.in_bonding_id = e.in_bonding_id WHERE n1.company_id = :cid AND n1.branch_id = :bid AND (CASE WHEN Date(n1.Extension_Date3) >= :endDate THEN 1 WHEN Date(n1.Extension_Date2) >= :endDate THEN 1 WHEN Date(n1.Extension_Date1) >= :endDate THEN 1 WHEN Date(n1.bond_validity_date) >= :endDate THEN 1 ELSE 0 END) = 1 AND Date(n1.in_bonding_date) between :start13 AND :end13 AND e.balanced_packages > 0 AND Date(e.ex_bonding_date) between :start13 AND :end13 AND (e.noc_no, e.boe_no, e.noc_trans_id) NOT IN (SELECT n.noc_no, n.boe_no, n.noc_trans_id FROM CfExBondCrg n WHERE n.company_id = :cid AND n.branch_id = :bid AND Date(n.ex_bonding_date) between :start13 AND :end13 AND n.balanced_packages = 0) GROUP BY n1.in_bonding_id) AS combined "
		+ "UNION ALL "
		+ "SELECT COUNT(*) AS count, COALESCE(SUM(CIF_Value), 0) AS cifValueSum, COALESCE(SUM(Cargo_Duty), 0) AS cargoDutySum FROM (SELECT n1.in_bonding_id, MAX(n1.CIF_Value) AS CIF_Value, MAX(n1.Cargo_Duty) AS Cargo_Duty FROM cfinbondcrg n1 LEFT JOIN CfExBondCrg e ON n1.company_id = e.company_id AND n1.branch_id = e.branch_id AND n1.noc_no = e.noc_no AND n1.noc_trans_id = e.noc_trans_id AND n1.boe_no = e.boe_no AND n1.in_bonding_id = e.in_bonding_id WHERE n1.company_id = :cid AND n1.branch_id = :bid AND (CASE WHEN Date(n1.Extension_Date3) >= :endDate THEN 1 WHEN Date(n1.Extension_Date2) >= :endDate THEN 1 WHEN Date(n1.Extension_Date1) >= :endDate THEN 1 WHEN Date(n1.bond_validity_date) >= :endDate THEN 1 ELSE 0 END) = 1 AND (n1.in_bonded_packages - n1.ex_bonded_packages) > 0 AND Date(n1.in_bonding_date) between :start36 AND :end36 GROUP BY n1.in_bonding_id "
		+ "UNION "
		+ "SELECT n1.in_bonding_id, MAX(n1.CIF_Value) AS CIF_Value, MAX(n1.Cargo_Duty) AS Cargo_Duty FROM cfinbondcrg n1 LEFT JOIN CfExBondCrg e ON n1.company_id = e.company_id AND n1.branch_id = e.branch_id AND n1.noc_no = e.noc_no AND n1.noc_trans_id = e.noc_trans_id AND n1.boe_no = e.boe_no AND n1.in_bonding_id = e.in_bonding_id WHERE n1.company_id = :cid AND n1.branch_id = :bid AND (CASE WHEN Date(n1.Extension_Date3) >= :endDate THEN 1 WHEN Date(n1.Extension_Date2) >= :endDate THEN 1 WHEN Date(n1.Extension_Date1) >= :endDate THEN 1 WHEN Date(n1.bond_validity_date) >= :endDate THEN 1 ELSE 0 END) = 1 AND Date(n1.in_bonding_date) between :start36 AND :end36 AND e.balanced_packages > 0 AND Date(e.ex_bonding_date) between :start36 AND :end36 AND (e.noc_no, e.boe_no, e.noc_trans_id) NOT IN (SELECT n.noc_no, n.boe_no, n.noc_trans_id FROM CfExBondCrg n WHERE n.company_id = :cid AND n.branch_id = :bid AND Date(n.ex_bonding_date) between :start36 AND :end36 AND n.balanced_packages = 0) GROUP BY n1.in_bonding_id) AS combined "
		+ "UNION ALL "
		+ "SELECT COUNT(*) AS count, COALESCE(SUM(CIF_Value), 0) AS cifValueSum, COALESCE(SUM(Cargo_Duty), 0) AS cargoDutySum FROM (SELECT n1.in_bonding_id, MAX(n1.CIF_Value) AS CIF_Value, MAX(n1.Cargo_Duty) AS Cargo_Duty FROM cfinbondcrg n1 LEFT JOIN CfExBondCrg e ON n1.company_id = e.company_id AND n1.branch_id = e.branch_id AND n1.noc_no = e.noc_no AND n1.noc_trans_id = e.noc_trans_id AND n1.boe_no = e.boe_no AND n1.in_bonding_id = e.in_bonding_id WHERE n1.company_id = :cid AND n1.branch_id = :bid AND (CASE WHEN Date(n1.Extension_Date3) >= :endDate THEN 1 WHEN Date(n1.Extension_Date2) >= :endDate THEN 1 WHEN Date(n1.Extension_Date1) >= :endDate THEN 1 WHEN Date(n1.bond_validity_date) >= :endDate THEN 1 ELSE 0 END) = 1 AND (n1.in_bonded_packages - n1.ex_bonded_packages) > 0 AND Date(n1.in_bonding_date) between :start612 AND :end612 GROUP BY n1.in_bonding_id "
		+ "UNION "
		+ "SELECT n1.in_bonding_id, MAX(n1.CIF_Value) AS CIF_Value, MAX(n1.Cargo_Duty) AS Cargo_Duty FROM cfinbondcrg n1 LEFT JOIN CfExBondCrg e ON n1.company_id = e.company_id AND n1.branch_id = e.branch_id AND n1.noc_no = e.noc_no AND n1.noc_trans_id = e.noc_trans_id AND n1.boe_no = e.boe_no AND n1.in_bonding_id = e.in_bonding_id WHERE n1.company_id = :cid AND n1.branch_id = :bid AND (CASE WHEN Date(n1.Extension_Date3) >= :endDate THEN 1 WHEN Date(n1.Extension_Date2) >= :endDate THEN 1 WHEN Date(n1.Extension_Date1) >= :endDate THEN 1 WHEN Date(n1.bond_validity_date) >= :endDate THEN 1 ELSE 0 END) = 1 AND Date(n1.in_bonding_date) between :start612 AND :end612 AND e.balanced_packages > 0 AND Date(e.ex_bonding_date) between :start612 AND :end612 AND (e.noc_no, e.boe_no, e.noc_trans_id) NOT IN (SELECT n.noc_no, n.boe_no, n.noc_trans_id FROM CfExBondCrg n WHERE n.company_id = :cid AND n.branch_id = :bid AND Date(n.ex_bonding_date) between :start612 AND :end612 AND n.balanced_packages = 0) GROUP BY n1.in_bonding_id) AS combined "
		+ "UNION ALL "
		+ "SELECT COUNT(*) AS count, COALESCE(SUM(CIF_Value), 0) AS cifValueSum, COALESCE(SUM(Cargo_Duty), 0) AS cargoDutySum FROM (SELECT n1.in_bonding_id, MAX(n1.CIF_Value) AS CIF_Value, MAX(n1.Cargo_Duty) AS Cargo_Duty FROM cfinbondcrg n1 LEFT JOIN CfExBondCrg e ON n1.company_id = e.company_id AND n1.branch_id = e.branch_id AND n1.noc_no = e.noc_no AND n1.noc_trans_id = e.noc_trans_id AND n1.boe_no = e.boe_no AND n1.in_bonding_id = e.in_bonding_id WHERE n1.company_id = :cid AND n1.branch_id = :bid AND (CASE WHEN Date(n1.Extension_Date3) >= :endDate THEN 1 WHEN Date(n1.Extension_Date2) >= :endDate THEN 1 WHEN Date(n1.Extension_Date1) >= :endDate THEN 1 WHEN Date(n1.bond_validity_date) >= :endDate THEN 1 ELSE 0 END) = 1 AND (n1.in_bonded_packages - n1.ex_bonded_packages) > 0 AND Date(n1.in_bonding_date) between :start1y AND :end1y GROUP BY n1.in_bonding_id "
		+ "UNION "
		+ "SELECT n1.in_bonding_id, MAX(n1.CIF_Value) AS CIF_Value, MAX(n1.Cargo_Duty) AS Cargo_Duty FROM cfinbondcrg n1 LEFT JOIN CfExBondCrg e ON n1.company_id = e.company_id AND n1.branch_id = e.branch_id AND n1.noc_no = e.noc_no AND n1.noc_trans_id = e.noc_trans_id AND n1.boe_no = e.boe_no AND n1.in_bonding_id = e.in_bonding_id WHERE n1.company_id = :cid AND n1.branch_id = :bid AND (CASE WHEN Date(n1.Extension_Date3) >= :endDate THEN 1 WHEN Date(n1.Extension_Date2) >= :endDate THEN 1 WHEN Date(n1.Extension_Date1) >= :endDate THEN 1 WHEN Date(n1.bond_validity_date) >= :endDate THEN 1 ELSE 0 END) = 1 AND Date(n1.in_bonding_date) between :start1y AND :end1y AND e.balanced_packages > 0 AND Date(e.ex_bonding_date) between :start1y AND :end1y AND (e.noc_no, e.boe_no, e.noc_trans_id) NOT IN (SELECT n.noc_no, n.boe_no, n.noc_trans_id FROM CfExBondCrg n WHERE n.company_id = :cid AND n.branch_id = :bid AND Date(n.ex_bonding_date) between :start1y AND :end1y AND n.balanced_packages = 0) GROUP BY n1.in_bonding_id) AS combined "
		+ "UNION ALL "
		+ "SELECT COUNT(*) AS count, COALESCE(SUM(CIF_Value), 0) AS cifValueSum, COALESCE(SUM(Cargo_Duty), 0) AS cargoDutySum FROM (SELECT n1.in_bonding_id, MAX(n1.CIF_Value) AS CIF_Value, MAX(n1.Cargo_Duty) AS Cargo_Duty FROM cfinbondcrg n1 LEFT JOIN CfExBondCrg e ON n1.company_id = e.company_id AND n1.branch_id = e.branch_id AND n1.noc_no = e.noc_no AND n1.noc_trans_id = e.noc_trans_id AND n1.boe_no = e.boe_no AND n1.in_bonding_id = e.in_bonding_id WHERE n1.company_id = :cid AND n1.branch_id = :bid AND (CASE WHEN Date(n1.Extension_Date3) >= :endDate THEN 1 WHEN Date(n1.Extension_Date2) >= :endDate THEN 1 WHEN Date(n1.Extension_Date1) >= :endDate THEN 1 WHEN Date(n1.bond_validity_date) >= :endDate THEN 1 ELSE 0 END) = 1 AND (n1.in_bonded_packages - n1.ex_bonded_packages) > 0 AND Date(n1.in_bonding_date) between :start2y AND :end2y GROUP BY n1.in_bonding_id "
		+ "UNION "
		+ "SELECT n1.in_bonding_id, MAX(n1.CIF_Value) AS CIF_Value, MAX(n1.Cargo_Duty) AS Cargo_Duty FROM cfinbondcrg n1 LEFT JOIN CfExBondCrg e ON n1.company_id = e.company_id AND n1.branch_id = e.branch_id AND n1.noc_no = e.noc_no AND n1.noc_trans_id = e.noc_trans_id AND n1.boe_no = e.boe_no AND n1.in_bonding_id = e.in_bonding_id WHERE n1.company_id = :cid AND n1.branch_id = :bid AND (CASE WHEN Date(n1.Extension_Date3) >= :endDate THEN 1 WHEN Date(n1.Extension_Date2) >= :endDate THEN 1 WHEN Date(n1.Extension_Date1) >= :endDate THEN 1 WHEN Date(n1.bond_validity_date) >= :endDate THEN 1 ELSE 0 END) = 1 AND Date(n1.in_bonding_date) between :start2y AND :end2y AND e.balanced_packages > 0 AND Date(e.ex_bonding_date) between :start2y AND :end2y AND (e.noc_no, e.boe_no, e.noc_trans_id) NOT IN (SELECT n.noc_no, n.boe_no, n.noc_trans_id FROM CfExBondCrg n WHERE n.company_id = :cid AND n.branch_id = :bid AND Date(n.ex_bonding_date) between :start2y AND :end2y AND n.balanced_packages = 0) GROUP BY n1.in_bonding_id) AS combined "
		+ "UNION ALL "
		+ "SELECT COUNT(*) AS count, COALESCE(SUM(CIF_Value), 0) AS cifValueSum, COALESCE(SUM(Cargo_Duty), 0) AS cargoDutySum FROM (SELECT n1.in_bonding_id, MAX(n1.CIF_Value) AS CIF_Value, MAX(n1.Cargo_Duty) AS Cargo_Duty FROM cfinbondcrg n1 LEFT JOIN CfExBondCrg e ON n1.company_id = e.company_id AND n1.branch_id = e.branch_id AND n1.noc_no = e.noc_no AND n1.noc_trans_id = e.noc_trans_id AND n1.boe_no = e.boe_no AND n1.in_bonding_id = e.in_bonding_id WHERE n1.company_id = :cid AND n1.branch_id = :bid AND (CASE WHEN Date(n1.Extension_Date3) >= :endDate THEN 1 WHEN Date(n1.Extension_Date2) >= :endDate THEN 1 WHEN Date(n1.Extension_Date1) >= :endDate THEN 1 WHEN Date(n1.bond_validity_date) >= :endDate THEN 1 ELSE 0 END) = 1 AND (n1.in_bonded_packages - n1.ex_bonded_packages) > 0 AND Date(n1.in_bonding_date) < :start3y GROUP BY n1.in_bonding_id "
		+ "UNION "
		+ "SELECT n1.in_bonding_id, MAX(n1.CIF_Value) AS CIF_Value, MAX(n1.Cargo_Duty) AS Cargo_Duty FROM cfinbondcrg n1 LEFT JOIN CfExBondCrg e ON n1.company_id = e.company_id AND n1.branch_id = e.branch_id AND n1.noc_no = e.noc_no AND n1.noc_trans_id = e.noc_trans_id AND n1.boe_no = e.boe_no AND n1.in_bonding_id = e.in_bonding_id WHERE n1.company_id = :cid AND n1.branch_id = :bid AND (CASE WHEN Date(n1.Extension_Date3) >= :endDate THEN 1 WHEN Date(n1.Extension_Date2) >= :endDate THEN 1 WHEN Date(n1.Extension_Date1) >= :endDate THEN 1 WHEN Date(n1.bond_validity_date) >= :endDate THEN 1 ELSE 0 END) = 1 AND Date(n1.in_bonding_date) < :start3y AND e.balanced_packages > 0 AND Date(e.ex_bonding_date)< :start3y AND (e.noc_no, e.boe_no, e.noc_trans_id) NOT IN (SELECT n.noc_no, n.boe_no, n.noc_trans_id FROM CfExBondCrg n WHERE n.company_id = :cid AND n.branch_id = :bid AND Date(n.ex_bonding_date) < :start3y AND n.balanced_packages = 0) GROUP BY n1.in_bonding_id) AS combined ", nativeQuery = true)
List<Object[]> getLiveBreckUp(@Param("cid") String cid,
		@Param("bid") String bid,
		@Param("endDate") Date endDate,
		@Param("start") Date start,
		@Param("end") Date end,
		@Param("start13") Date start13,
		@Param("end13") Date end13,
		@Param("start36") Date start36,
		@Param("end36") Date end36,
		@Param("start612") Date start612,
		@Param("end612") Date end612,
		@Param("start1y") Date start1y,
		@Param("end1y") Date end1y,
		@Param("start2y") Date start2y,
		@Param("end2y") Date end2y,
		@Param("start3y") Date start3y
);


@Query(value = "SELECT COUNT(*) AS count, " + "COALESCE(SUM(CIF_Value), 0) AS cifValueSum, "
		+ "COALESCE(SUM(Cargo_Duty), 0) AS cargoDutySum "
		+ "FROM ("
		+ "    SELECT n1.in_bonding_id, "
		+ "           MAX(n1.CIF_Value) AS CIF_Value, "
		+ "           MAX(n1.Cargo_Duty) AS Cargo_Duty "
		+ "    FROM cfinbondcrg n1 "
		+ "    LEFT JOIN CfExBondCrg e "
		+ "    ON n1.company_id = e.company_id "
		+ "       AND n1.branch_id = e.branch_id "
		+ "       AND n1.noc_no = e.noc_no "
		+ "       AND n1.noc_trans_id = e.noc_trans_id " 
		+ "       AND n1.boe_no = e.boe_no "
		+ "       AND n1.in_bonding_id = e.in_bonding_id "
		+ "    WHERE n1.company_id = :companyId "
		+ "      AND n1.branch_id = :branchId "
		+ "      AND n1.section_49 = :section49 "
		+ "      AND ("
		+ "          CASE "
		+ "              WHEN Date(n1.Extension_Date3) < :endDate THEN 1 "
		+ "              WHEN Date(n1.Extension_Date2) < :endDate THEN 1 "
		+ "              WHEN Date(n1.Extension_Date1) < :endDate THEN 1 "
		+ "              WHEN Date(n1.bond_validity_date) < :endDate THEN 1 " 
		+ "              ELSE 0 "
		+ "          END) = 1 " 
		+ "      AND (n1.in_bonded_packages - n1.ex_bonded_packages) > 0 "
		+ "      AND Date(n1.in_bonding_date) < :currentDate "
		+ "    GROUP BY n1.in_bonding_id " 
		+ "    UNION "
		+ "    SELECT n1.in_bonding_id, "
		+ "           MAX(n1.CIF_Value) AS CIF_Value, "
		+ "           MAX(n1.Cargo_Duty) AS Cargo_Duty " 
		+ "    FROM cfinbondcrg n1 "
		+ "    LEFT JOIN CfExBondCrg e "
		+ "    ON n1.company_id = e.company_id "
		+ "       AND n1.branch_id = e.branch_id " 
		+ "       AND n1.noc_no = e.noc_no "
		+ "       AND n1.noc_trans_id = e.noc_trans_id " 
		+ "       AND n1.boe_no = e.boe_no "
		+ "       AND n1.in_bonding_id = e.in_bonding_id "
		+ "    WHERE n1.company_id = :companyId "
		+ "      AND n1.branch_id = :branchId " 
		+ "      AND n1.section_49 = :section49 "
		+ "      AND (" 
		+ "          CASE "
		+ "              WHEN Date(n1.Extension_Date3) < :endDate THEN 1 "
		+ "              WHEN Date(n1.Extension_Date2) < :endDate THEN 1 "
		+ "              WHEN Date(n1.Extension_Date1) < :endDate THEN 1 "
		+ "              WHEN Date(n1.bond_validity_date) < :endDate THEN 1 " + "              ELSE 0 "
		+ "          END) = 1 " 
		+ "      AND Date(n1.in_bonding_date) < :currentDate "
		+ "      AND e.balanced_packages > 0 " 
		+ "      AND Date(e.ex_bonding_date) < :currentDate "
		+ "      AND (e.noc_no, e.boe_no, e.noc_trans_id) NOT IN ("
		+ "          SELECT n.noc_no, n.boe_no, n.noc_trans_id " 
		+ "          FROM CfExBondCrg n "
		+ "          WHERE n.company_id = :companyId " 
		+ "            AND n.branch_id = :branchId "
		+ "            AND n1.section_49 = :section49 "
		+ "            AND Date(n.ex_bonding_date) < :currentDate "
		+ "            AND n.balanced_packages = 0"
		+ "      ) "
		+ "    GROUP BY n1.in_bonding_id"
		+ ") AS combined", nativeQuery = true)
List<Object[]> getLiveOpeningBalance1(@Param("companyId") String companyId, @Param("branchId") String branchId,
		@Param("currentDate") Date currentDate,
		@Param("endDate") Date endDate,
		@Param("section49") String section49);


@Query(value = "WITH SumValues AS ( "
		+ "    SELECT ex.noc_no, "
		+ "           COALESCE(SUM(ex.ex_bonded_cif), 0) AS cifValueSum, "
		+ "           COALESCE(SUM(ex.ex_bonded_cargo_duty), 0) AS cargoDutySum "
		+ "    FROM cfinbondcrg i "
		+ "    JOIN CfExBondCrg ex ON i.company_id = ex.company_id "
		+ "                       AND i.branch_id = ex.branch_id "
		+ "                       AND i.noc_no = ex.noc_no "
		+ "                       AND i.noc_trans_id = ex.noc_trans_id "
		+ "                       AND i.boe_no = ex.boe_no "
		+ "                       AND i.in_bonding_id = ex.in_bonding_id "
		+ "    WHERE ex.company_id = :companyId "
		+ "      AND ex.branch_id = :branchId "
		+ "      AND ( " 
		+ "          CASE "
		+ "              WHEN Date(i.Extension_Date3) < :endDate THEN 1 "
		+ "              WHEN Date(i.Extension_Date2) < :endDate THEN 1 "
		+ "              WHEN Date(i.Extension_Date1) < :endDate THEN 1 "
		+ "              WHEN Date(i.bond_validity_date) < :endDate THEN 1 "
		+ "              ELSE 0 "
		+ "          END) = 1 "
		+ "      AND i.status != 'D' "
		+ "      AND i.section_49 =:section49 "
		+ "      AND ex.status != 'D' "
		+ "    GROUP BY ex.noc_no " 
		+ ") "
		+ "SELECT COALESCE(COUNT(DISTINCT n3.in_bonding_id), 0) AS count2, "
		+ "       COALESCE(SUM(n3.CIF_Value), 0) AS cifValueSum2, "
		+ "       COALESCE(SUM(n3.Cargo_Duty), 0) AS cargoDutySum2 " 
		+ "FROM cfinbondcrg n3 "
		+ "WHERE n3.company_id = :companyId " 
		+ "  AND n3.branch_id = :branchId "
		+ "      AND n3.section_49 =:section49 "
		+ "  AND ( "
		+ "      CASE "
		+ "          WHEN Date(n3.Extension_Date3) < :endDate THEN 1 "
		+ "          WHEN Date(n3.Extension_Date2) < :endDate THEN 1 "
		+ "          WHEN Date(n3.Extension_Date1) < :endDate THEN 1 "
		+ "          WHEN Date(n3.bond_validity_date) < :endDate THEN 1 "
		+ "          ELSE 0 "
		+ "      END) = 1 "
		+ "  AND Date(n3.in_bonding_date) BETWEEN :startDate AND :endDate "
		+ "  AND n3.status != 'D' "
		+ "UNION ALL "
		+ "SELECT COALESCE(COUNT(DISTINCT n3.in_bonding_id), 0) AS count2, "
		+ "       COALESCE(SUM(n3.CIF_Value), 0) AS cifValueSum2, "
		+ "       COALESCE(SUM(n3.Cargo_Duty), 0) AS cargoDutySum2 " 
		+ "FROM cfinbondcrg n3 "
		+ "WHERE n3.company_id = :companyId " 
		+ "  AND n3.branch_id = :branchId "
		+ "      AND n3.section_49 =:section49 "
		+ "  AND ( "
		+ "      CASE "
		+ "          WHEN Date(n3.Extension_Date3) < :endDate THEN 1 "
		+ "          WHEN Date(n3.Extension_Date2) < :endDate THEN 1 "
		+ "          WHEN Date(n3.Extension_Date1) < :endDate THEN 1 "
		+ "          WHEN Date(n3.bond_validity_date) < :endDate THEN 1 "
		+ "          ELSE 0 "
		+ "      END) = 1 "
		+ "  AND Date(n3.in_bonding_date) BETWEEN :aprilDate AND :lastDate "
		+ "  AND n3.status != 'D' "
		+ "UNION ALL "
		+ "SELECT COALESCE(COUNT(DISTINCT CASE WHEN ex.balanced_packages = 0 THEN ex.noc_no END), 0) AS count, "
		+ "       COALESCE(SUM(s.cifValueSum), 0) AS cifValueSum, "
		+ "       COALESCE(SUM(s.cargoDutySum), 0) AS cargoDutySum "
		+ "FROM SumValues s "
		+ "JOIN CfExBondCrg ex ON s.noc_no = ex.noc_no "
		+ "WHERE ex.company_id = :companyId "
		+ "  AND ex.branch_id = :branchId " 
		+ "  AND ex.balanced_packages = 0 AND Date(ex.ex_bonding_date) BETWEEN :startDate AND :endDate "
		+ "UNION ALL "
		+ "SELECT COALESCE(COUNT(DISTINCT CASE WHEN ex.balanced_packages = 0 THEN ex.noc_no END), 0) AS count, "
		+ "       COALESCE(SUM(s.cifValueSum), 0) AS cifValueSum, "
		+ "       COALESCE(SUM(s.cargoDutySum), 0) AS cargoDutySum "
		+ "FROM SumValues s "
		+ "JOIN CfExBondCrg ex ON s.noc_no = ex.noc_no "
		+ "WHERE ex.company_id = :companyId "
		+ "  AND ex.branch_id = :branchId " 
//		+ "  AND n3.section_49 = :section49 " 
		+ "  AND ex.balanced_packages = 0 AND Date(ex.ex_bonding_date) BETWEEN :aprilDate AND :lastDate", nativeQuery = true)
List<Object[]> getLiveReceiptsAndDisposal1(@Param("companyId") String companyId, @Param("branchId") String branchId,
		@Param("startDate") Date startDate, @Param("endDate") Date endDate,@Param("aprilDate") Date aprilDate, @Param("lastDate") Date lastDate, @Param("section49") String section49);




@Query(value = "SELECT COUNT(*) AS count, COALESCE(SUM(CIF_Value), 0) AS cifValueSum, COALESCE(SUM(Cargo_Duty), 0) AS cargoDutySum FROM (SELECT n1.in_bonding_id, MAX(n1.CIF_Value) AS CIF_Value, MAX(n1.Cargo_Duty) AS Cargo_Duty FROM cfinbondcrg n1 LEFT JOIN CfExBondCrg e ON n1.company_id = e.company_id AND n1.branch_id = e.branch_id AND n1.noc_no = e.noc_no AND n1.noc_trans_id = e.noc_trans_id AND n1.boe_no = e.boe_no AND n1.in_bonding_id = e.in_bonding_id WHERE n1.company_id = :cid AND n1.branch_id = :bid AND (CASE WHEN Date(n1.Extension_Date3) < :endDate THEN 1 WHEN Date(n1.Extension_Date2) < :endDate THEN 1 WHEN Date(n1.Extension_Date1) < :endDate THEN 1 WHEN Date(n1.bond_validity_date) < :endDate THEN 1 ELSE 0 END) = 1 AND (n1.in_bonded_packages - n1.ex_bonded_packages) > 0 AND Date(n1.in_bonding_date) between :start AND :end GROUP BY n1.in_bonding_id "
		+ "UNION "
		+ "SELECT n1.in_bonding_id, MAX(n1.CIF_Value) AS CIF_Value, MAX(n1.Cargo_Duty) AS Cargo_Duty FROM cfinbondcrg n1 LEFT JOIN CfExBondCrg e ON n1.company_id = e.company_id AND n1.branch_id = e.branch_id AND n1.noc_no = e.noc_no AND n1.noc_trans_id = e.noc_trans_id AND n1.boe_no = e.boe_no AND n1.in_bonding_id = e.in_bonding_id WHERE n1.company_id = :cid AND n1.branch_id = :bid AND (CASE WHEN Date(n1.Extension_Date3) < :endDate THEN 1 WHEN Date(n1.Extension_Date2) < :endDate THEN 1 WHEN Date(n1.Extension_Date1) < :endDate THEN 1 WHEN Date(n1.bond_validity_date) < :endDate THEN 1 ELSE 0 END) = 1 AND Date(n1.in_bonding_date) between :start AND :end AND e.balanced_packages > 0 AND Date(e.ex_bonding_date) between :start AND :end AND (e.noc_no, e.boe_no, e.noc_trans_id) NOT IN (SELECT n.noc_no, n.boe_no, n.noc_trans_id FROM CfExBondCrg n WHERE n.company_id = :cid AND n.branch_id = :bid AND Date(n.ex_bonding_date) between :start AND :end AND n.balanced_packages = 0) GROUP BY n1.in_bonding_id) AS combined "
		+ "UNION ALL "
		+ "SELECT COUNT(*) AS count, COALESCE(SUM(CIF_Value), 0) AS cifValueSum, COALESCE(SUM(Cargo_Duty), 0) AS cargoDutySum FROM (SELECT n1.in_bonding_id, MAX(n1.CIF_Value) AS CIF_Value, MAX(n1.Cargo_Duty) AS Cargo_Duty FROM cfinbondcrg n1 LEFT JOIN CfExBondCrg e ON n1.company_id = e.company_id AND n1.branch_id = e.branch_id AND n1.noc_no = e.noc_no AND n1.noc_trans_id = e.noc_trans_id AND n1.boe_no = e.boe_no AND n1.in_bonding_id = e.in_bonding_id WHERE n1.company_id = :cid AND n1.branch_id = :bid AND (CASE WHEN Date(n1.Extension_Date3) < :endDate THEN 1 WHEN Date(n1.Extension_Date2) < :endDate THEN 1 WHEN Date(n1.Extension_Date1) < :endDate THEN 1 WHEN Date(n1.bond_validity_date) < :endDate THEN 1 ELSE 0 END) = 1 AND (n1.in_bonded_packages - n1.ex_bonded_packages) > 0 AND Date(n1.in_bonding_date) between :start13 AND :end13 GROUP BY n1.in_bonding_id "
		+ "UNION "
		+ "SELECT n1.in_bonding_id, MAX(n1.CIF_Value) AS CIF_Value, MAX(n1.Cargo_Duty) AS Cargo_Duty FROM cfinbondcrg n1 LEFT JOIN CfExBondCrg e ON n1.company_id = e.company_id AND n1.branch_id = e.branch_id AND n1.noc_no = e.noc_no AND n1.noc_trans_id = e.noc_trans_id AND n1.boe_no = e.boe_no AND n1.in_bonding_id = e.in_bonding_id WHERE n1.company_id = :cid AND n1.branch_id = :bid AND (CASE WHEN Date(n1.Extension_Date3) < :endDate THEN 1 WHEN Date(n1.Extension_Date2) < :endDate THEN 1 WHEN Date(n1.Extension_Date1) < :endDate THEN 1 WHEN Date(n1.bond_validity_date) < :endDate THEN 1 ELSE 0 END) = 1 AND Date(n1.in_bonding_date) between :start13 AND :end13 AND e.balanced_packages > 0 AND Date(e.ex_bonding_date) between :start13 AND :end13 AND (e.noc_no, e.boe_no, e.noc_trans_id) NOT IN (SELECT n.noc_no, n.boe_no, n.noc_trans_id FROM CfExBondCrg n WHERE n.company_id = :cid AND n.branch_id = :bid AND Date(n.ex_bonding_date) between :start13 AND :end13 AND n.balanced_packages = 0) GROUP BY n1.in_bonding_id) AS combined "
		+ "UNION ALL "
		+ "SELECT COUNT(*) AS count, COALESCE(SUM(CIF_Value), 0) AS cifValueSum, COALESCE(SUM(Cargo_Duty), 0) AS cargoDutySum FROM (SELECT n1.in_bonding_id, MAX(n1.CIF_Value) AS CIF_Value, MAX(n1.Cargo_Duty) AS Cargo_Duty FROM cfinbondcrg n1 LEFT JOIN CfExBondCrg e ON n1.company_id = e.company_id AND n1.branch_id = e.branch_id AND n1.noc_no = e.noc_no AND n1.noc_trans_id = e.noc_trans_id AND n1.boe_no = e.boe_no AND n1.in_bonding_id = e.in_bonding_id WHERE n1.company_id = :cid AND n1.branch_id = :bid AND (CASE WHEN Date(n1.Extension_Date3) < :endDate THEN 1 WHEN Date(n1.Extension_Date2) < :endDate THEN 1 WHEN Date(n1.Extension_Date1) < :endDate THEN 1 WHEN Date(n1.bond_validity_date) < :endDate THEN 1 ELSE 0 END) = 1 AND (n1.in_bonded_packages - n1.ex_bonded_packages) > 0 AND Date(n1.in_bonding_date) between :start36 AND :end36 GROUP BY n1.in_bonding_id "
		+ "UNION "
		+ "SELECT n1.in_bonding_id, MAX(n1.CIF_Value) AS CIF_Value, MAX(n1.Cargo_Duty) AS Cargo_Duty FROM cfinbondcrg n1 LEFT JOIN CfExBondCrg e ON n1.company_id = e.company_id AND n1.branch_id = e.branch_id AND n1.noc_no = e.noc_no AND n1.noc_trans_id = e.noc_trans_id AND n1.boe_no = e.boe_no AND n1.in_bonding_id = e.in_bonding_id WHERE n1.company_id = :cid AND n1.branch_id = :bid AND (CASE WHEN Date(n1.Extension_Date3) < :endDate THEN 1 WHEN Date(n1.Extension_Date2) < :endDate THEN 1 WHEN Date(n1.Extension_Date1) < :endDate THEN 1 WHEN Date(n1.bond_validity_date) < :endDate THEN 1 ELSE 0 END) = 1 AND Date(n1.in_bonding_date) between :start36 AND :end36 AND e.balanced_packages > 0 AND Date(e.ex_bonding_date) between :start36 AND :end36 AND (e.noc_no, e.boe_no, e.noc_trans_id) NOT IN (SELECT n.noc_no, n.boe_no, n.noc_trans_id FROM CfExBondCrg n WHERE n.company_id = :cid AND n.branch_id = :bid AND Date(n.ex_bonding_date) between :start36 AND :end36 AND n.balanced_packages = 0) GROUP BY n1.in_bonding_id) AS combined "
		+ "UNION ALL "
		+ "SELECT COUNT(*) AS count, COALESCE(SUM(CIF_Value), 0) AS cifValueSum, COALESCE(SUM(Cargo_Duty), 0) AS cargoDutySum FROM (SELECT n1.in_bonding_id, MAX(n1.CIF_Value) AS CIF_Value, MAX(n1.Cargo_Duty) AS Cargo_Duty FROM cfinbondcrg n1 LEFT JOIN CfExBondCrg e ON n1.company_id = e.company_id AND n1.branch_id = e.branch_id AND n1.noc_no = e.noc_no AND n1.noc_trans_id = e.noc_trans_id AND n1.boe_no = e.boe_no AND n1.in_bonding_id = e.in_bonding_id WHERE n1.company_id = :cid AND n1.branch_id = :bid AND (CASE WHEN Date(n1.Extension_Date3) < :endDate THEN 1 WHEN Date(n1.Extension_Date2) < :endDate THEN 1 WHEN Date(n1.Extension_Date1) < :endDate THEN 1 WHEN Date(n1.bond_validity_date) < :endDate THEN 1 ELSE 0 END) = 1 AND (n1.in_bonded_packages - n1.ex_bonded_packages) > 0 AND Date(n1.in_bonding_date) between :start612 AND :end612 GROUP BY n1.in_bonding_id "
		+ "UNION "
		+ "SELECT n1.in_bonding_id, MAX(n1.CIF_Value) AS CIF_Value, MAX(n1.Cargo_Duty) AS Cargo_Duty FROM cfinbondcrg n1 LEFT JOIN CfExBondCrg e ON n1.company_id = e.company_id AND n1.branch_id = e.branch_id AND n1.noc_no = e.noc_no AND n1.noc_trans_id = e.noc_trans_id AND n1.boe_no = e.boe_no AND n1.in_bonding_id = e.in_bonding_id WHERE n1.company_id = :cid AND n1.branch_id = :bid AND (CASE WHEN Date(n1.Extension_Date3) < :endDate THEN 1 WHEN Date(n1.Extension_Date2) < :endDate THEN 1 WHEN Date(n1.Extension_Date1) < :endDate THEN 1 WHEN Date(n1.bond_validity_date) < :endDate THEN 1 ELSE 0 END) = 1 AND Date(n1.in_bonding_date) between :start612 AND :end612 AND e.balanced_packages > 0 AND Date(e.ex_bonding_date) between :start612 AND :end612 AND (e.noc_no, e.boe_no, e.noc_trans_id) NOT IN (SELECT n.noc_no, n.boe_no, n.noc_trans_id FROM CfExBondCrg n WHERE n.company_id = :cid AND n.branch_id = :bid AND Date(n.ex_bonding_date) between :start612 AND :end612 AND n.balanced_packages = 0) GROUP BY n1.in_bonding_id) AS combined "
		+ "UNION ALL "
		+ "SELECT COUNT(*) AS count, COALESCE(SUM(CIF_Value), 0) AS cifValueSum, COALESCE(SUM(Cargo_Duty), 0) AS cargoDutySum FROM (SELECT n1.in_bonding_id, MAX(n1.CIF_Value) AS CIF_Value, MAX(n1.Cargo_Duty) AS Cargo_Duty FROM cfinbondcrg n1 LEFT JOIN CfExBondCrg e ON n1.company_id = e.company_id AND n1.branch_id = e.branch_id AND n1.noc_no = e.noc_no AND n1.noc_trans_id = e.noc_trans_id AND n1.boe_no = e.boe_no AND n1.in_bonding_id = e.in_bonding_id WHERE n1.company_id = :cid AND n1.branch_id = :bid AND (CASE WHEN Date(n1.Extension_Date3) < :endDate THEN 1 WHEN Date(n1.Extension_Date2) < :endDate THEN 1 WHEN Date(n1.Extension_Date1) < :endDate THEN 1 WHEN Date(n1.bond_validity_date) < :endDate THEN 1 ELSE 0 END) = 1 AND (n1.in_bonded_packages - n1.ex_bonded_packages) > 0 AND Date(n1.in_bonding_date) between :start1y AND :end1y GROUP BY n1.in_bonding_id "
		+ "UNION "
		+ "SELECT n1.in_bonding_id, MAX(n1.CIF_Value) AS CIF_Value, MAX(n1.Cargo_Duty) AS Cargo_Duty FROM cfinbondcrg n1 LEFT JOIN CfExBondCrg e ON n1.company_id = e.company_id AND n1.branch_id = e.branch_id AND n1.noc_no = e.noc_no AND n1.noc_trans_id = e.noc_trans_id AND n1.boe_no = e.boe_no AND n1.in_bonding_id = e.in_bonding_id WHERE n1.company_id = :cid AND n1.branch_id = :bid AND (CASE WHEN Date(n1.Extension_Date3) < :endDate THEN 1 WHEN Date(n1.Extension_Date2) < :endDate THEN 1 WHEN Date(n1.Extension_Date1) < :endDate THEN 1 WHEN Date(n1.bond_validity_date) < :endDate THEN 1 ELSE 0 END) = 1 AND Date(n1.in_bonding_date) between :start1y AND :end1y AND e.balanced_packages > 0 AND Date(e.ex_bonding_date) between :start1y AND :end1y AND (e.noc_no, e.boe_no, e.noc_trans_id) NOT IN (SELECT n.noc_no, n.boe_no, n.noc_trans_id FROM CfExBondCrg n WHERE n.company_id = :cid AND n.branch_id = :bid AND Date(n.ex_bonding_date) between :start1y AND :end1y AND n.balanced_packages = 0) GROUP BY n1.in_bonding_id) AS combined "
		+ "UNION ALL "
		+ "SELECT COUNT(*) AS count, COALESCE(SUM(CIF_Value), 0) AS cifValueSum, COALESCE(SUM(Cargo_Duty), 0) AS cargoDutySum FROM (SELECT n1.in_bonding_id, MAX(n1.CIF_Value) AS CIF_Value, MAX(n1.Cargo_Duty) AS Cargo_Duty FROM cfinbondcrg n1 LEFT JOIN CfExBondCrg e ON n1.company_id = e.company_id AND n1.branch_id = e.branch_id AND n1.noc_no = e.noc_no AND n1.noc_trans_id = e.noc_trans_id AND n1.boe_no = e.boe_no AND n1.in_bonding_id = e.in_bonding_id WHERE n1.company_id = :cid AND n1.branch_id = :bid AND (CASE WHEN Date(n1.Extension_Date3) < :endDate THEN 1 WHEN Date(n1.Extension_Date2) < :endDate THEN 1 WHEN Date(n1.Extension_Date1) < :endDate THEN 1 WHEN Date(n1.bond_validity_date) < :endDate THEN 1 ELSE 0 END) = 1 AND (n1.in_bonded_packages - n1.ex_bonded_packages) > 0 AND Date(n1.in_bonding_date) between :start2y AND :end2y GROUP BY n1.in_bonding_id "
		+ "UNION "
		+ "SELECT n1.in_bonding_id, MAX(n1.CIF_Value) AS CIF_Value, MAX(n1.Cargo_Duty) AS Cargo_Duty FROM cfinbondcrg n1 LEFT JOIN CfExBondCrg e ON n1.company_id = e.company_id AND n1.branch_id = e.branch_id AND n1.noc_no = e.noc_no AND n1.noc_trans_id = e.noc_trans_id AND n1.boe_no = e.boe_no AND n1.in_bonding_id = e.in_bonding_id WHERE n1.company_id = :cid AND n1.branch_id = :bid AND (CASE WHEN Date(n1.Extension_Date3) < :endDate THEN 1 WHEN Date(n1.Extension_Date2) < :endDate THEN 1 WHEN Date(n1.Extension_Date1) < :endDate THEN 1 WHEN Date(n1.bond_validity_date) < :endDate THEN 1 ELSE 0 END) = 1 AND Date(n1.in_bonding_date) between :start2y AND :end2y AND e.balanced_packages > 0 AND Date(e.ex_bonding_date) between :start2y AND :end2y AND (e.noc_no, e.boe_no, e.noc_trans_id) NOT IN (SELECT n.noc_no, n.boe_no, n.noc_trans_id FROM CfExBondCrg n WHERE n.company_id = :cid AND n.branch_id = :bid AND Date(n.ex_bonding_date) between :start2y AND :end2y AND n.balanced_packages = 0) GROUP BY n1.in_bonding_id) AS combined "
		+ "UNION ALL "
		+ "SELECT COUNT(*) AS count, COALESCE(SUM(CIF_Value), 0) AS cifValueSum, COALESCE(SUM(Cargo_Duty), 0) AS cargoDutySum FROM (SELECT n1.in_bonding_id, MAX(n1.CIF_Value) AS CIF_Value, MAX(n1.Cargo_Duty) AS Cargo_Duty FROM cfinbondcrg n1 LEFT JOIN CfExBondCrg e ON n1.company_id = e.company_id AND n1.branch_id = e.branch_id AND n1.noc_no = e.noc_no AND n1.noc_trans_id = e.noc_trans_id AND n1.boe_no = e.boe_no AND n1.in_bonding_id = e.in_bonding_id WHERE n1.company_id = :cid AND n1.branch_id = :bid AND (CASE WHEN Date(n1.Extension_Date3) < :endDate THEN 1 WHEN Date(n1.Extension_Date2) < :endDate THEN 1 WHEN Date(n1.Extension_Date1) < :endDate THEN 1 WHEN Date(n1.bond_validity_date) < :endDate THEN 1 ELSE 0 END) = 1 AND (n1.in_bonded_packages - n1.ex_bonded_packages) > 0 AND Date(n1.in_bonding_date) < :start3y GROUP BY n1.in_bonding_id "
		+ "UNION "
		+ "SELECT n1.in_bonding_id, MAX(n1.CIF_Value) AS CIF_Value, MAX(n1.Cargo_Duty) AS Cargo_Duty FROM cfinbondcrg n1 LEFT JOIN CfExBondCrg e ON n1.company_id = e.company_id AND n1.branch_id = e.branch_id AND n1.noc_no = e.noc_no AND n1.noc_trans_id = e.noc_trans_id AND n1.boe_no = e.boe_no AND n1.in_bonding_id = e.in_bonding_id WHERE n1.company_id = :cid AND n1.branch_id = :bid AND (CASE WHEN Date(n1.Extension_Date3) < :endDate THEN 1 WHEN Date(n1.Extension_Date2) < :endDate THEN 1 WHEN Date(n1.Extension_Date1) < :endDate THEN 1 WHEN Date(n1.bond_validity_date) < :endDate THEN 1 ELSE 0 END) = 1 AND Date(n1.in_bonding_date) < :start3y AND e.balanced_packages > 0 AND Date(e.ex_bonding_date)< :start3y AND (e.noc_no, e.boe_no, e.noc_trans_id) NOT IN (SELECT n.noc_no, n.boe_no, n.noc_trans_id FROM CfExBondCrg n WHERE n.company_id = :cid AND n.branch_id = :bid AND Date(n.ex_bonding_date) < :start3y AND n.balanced_packages = 0) GROUP BY n1.in_bonding_id) AS combined ", nativeQuery = true)
List<Object[]> getLiveBreckUp1(@Param("cid") String cid,
		@Param("bid") String bid,
		@Param("endDate") Date endDate,
		@Param("start") Date start,
		@Param("end") Date end,
		@Param("start13") Date start13,
		@Param("end13") Date end13,
		@Param("start36") Date start36,
		@Param("end36") Date end36,
		@Param("start612") Date start612,
		@Param("end612") Date end612,
		@Param("start1y") Date start1y,
		@Param("end1y") Date end1y,
		@Param("start2y") Date start2y,
		@Param("end2y") Date end2y,
		@Param("start3y") Date start3y
);














































@Query(value = "SELECT n1.boe_no, DATE_FORMAT(n1.BOE_Date,'%d.%m.%Y'), n1.Source_Port, n1.bonding_no, "
		+ "DATE_FORMAT(n1.bonding_date,'%d.%m.%Y'), n2.commodity_description, n2.In_Bonded_Packages, n2.type_of_package, DATE_FORMAT(n1.in_bonding_date,'%d.%m.%Y'), "
		+ "n2.noc_packages, n2.inbond_gross_wt, n2.inbond_cif_value, n2.inbond_cargo_duty, 'NA', 'NA', n1.otl_no, "
		+ "n2.In_Bonded_Packages, (n2.breakage + n2.damaged_qty), n2.shortage_packages, DATE_FORMAT(n1.bond_validity_date,'%d.%m.%Y'), "
		+ "DATE_FORMAT(n1.Extension_Date1,'%d.%m.%Y'), n1.section_64, 'NA', e.ex_bond_be_no, DATE_FORMAT(e.ex_bond_be_date,'%d.%m.%Y'), e1.ex_bonded_packages, "
		+ "e1.ex_bonded_cif, e1.ex_bonded_cargo_duty, e.Balanced_Packages, e.Balance_CIF, e.Balance_Cargo_Duty, 'NA',e.ex_bond_type "
		+ "FROM cfinbondcrg n1 "
		+ "LEFT JOIN cfinbondcrg_dtl n2 ON n1.company_id=n2.company_id AND n1.branch_id=n2.branch_id AND n1.noc_no=n2.noc_no "
		+ "AND n1.noc_trans_id=n2.noc_trans_id AND n1.boe_no=n2.boe_no AND n1.in_bonding_id=n2.in_bonding_id "
		+ "LEFT JOIN cfexbondcrg e ON n2.company_id=e.company_id AND n2.branch_id=e.branch_id AND n2.noc_no=e.noc_no "
		+ "AND n2.noc_trans_id=e.noc_trans_id AND n2.boe_no=e.boe_no AND n2.in_bonding_id=e.in_bonding_id "
		+ "LEFT JOIN cfexbondcrgdtl e1 ON e.company_id=e1.company_id AND e.branch_id=e1.branch_id AND e.noc_no=e1.noc_no "
		+ "AND e.noc_trans_id=e1.noc_trans_id AND e.boe_no=e1.boe_no AND e.ex_bonding_id=e1.ex_bonding_id "
		+ "WHERE n1.company_id = :cid AND n1.branch_id = :bid AND (n1.ex_bonded_packages = 0 OR (n1.ex_bonded_packages != 0 && (n1.in_bonded_packages - n1.ex_bonded_packages) > 0)) "
		+ "AND ( CASE WHEN Date(n1.Extension_Date3) >= :endDate THEN 1 WHEN Date(n1.Extension_Date2) >= :endDate THEN 1 "
		+ "WHEN Date(n1.Extension_Date1) >= :endDate THEN 1 WHEN Date(n1.bond_validity_date)>= :endDate THEN 1 ELSE 0 END) = 1 "
		+ "AND Date(n1.in_bonding_date) <= :startDate AND n1.status != 'D' AND n2.status != 'D' "
		+ "AND (n1.boe_no,n1.in_bonding_id) "
		+ "NOT IN (select a.boe_no,a.in_bonding_id from cfinbondcrg a "
		+ "LEFT JOIN cfexbondcrg b ON a.company_id = b.company_id "
		+ "and a.branch_id = b.branch_id and a.noc_no=b.noc_no and a.noc_trans_id = b.noc_trans_id and a.boe_no = b.boe_no "
		+ "and a.in_bonding_id = b.in_bonding_id where a.company_id= :cid and a.branch_id= :bid and Date(b.ex_bonding_date) "
		+ "between :startDate and :endDate and b.Balanced_Packages = 0 and a.status != 'D' and b.status != 'D') "
		+ "GROUP BY n1.boe_no, n1.BOE_Date, n1.Source_Port, n1.bonding_no, n1.bonding_date, n2.cfbond_detail_id,n1.in_bonding_id, "
		
		+ "n2.In_Bonded_Packages, n2.type_of_package, n1.in_bonding_date,n2.noc_packages, n2.inbond_gross_wt, n2.inbond_cif_value, n2.inbond_cargo_duty, "
		+ "n1.otl_no, n2.In_Bonded_Packages, n2.breakage, n2.damaged_qty, n2.shortage_packages, n1.bond_validity_date, n1.Extension_Date1, "
		+ "n1.section_64, e.ex_bond_be_no, e.ex_bond_be_date, e1.ex_bonded_packages, e1.ex_bonded_cif, e1.ex_bonded_cargo_duty, "
		+ "e.Balanced_Packages, e.Balance_CIF, e.Balance_Cargo_Duty,e.ex_bond_type " 
		+ "UNION "
		+ "SELECT n1.boe_no, DATE_FORMAT(n1.BOE_Date,'%d.%m.%Y'), n1.Source_Port, n1.bonding_no, "
		+ "DATE_FORMAT(n1.bonding_date,'%d.%m.%Y'), n2.commodity_description, n2.In_Bonded_Packages, n2.type_of_package, DATE_FORMAT(n1.in_bonding_date,'%d.%m.%Y'), "
		+ "n2.noc_packages, n2.inbond_gross_wt, n2.inbond_cif_value, n2.inbond_cargo_duty, 'NA', 'NA', n1.otl_no, "
		+ "n2.In_Bonded_Packages, (n2.breakage + n2.damaged_qty), n2.shortage_packages, DATE_FORMAT(n1.bond_validity_date,'%d.%m.%Y'), "
		+ "DATE_FORMAT(n1.Extension_Date1,'%d.%m.%Y'), n1.section_64, 'NA', e.ex_bond_be_no, DATE_FORMAT(e.ex_bond_be_date,'%d.%m.%Y'), e1.ex_bonded_packages, "
		+ "e1.ex_bonded_cif, e1.ex_bonded_cargo_duty, e.Balanced_Packages, e.Balance_CIF, e.Balance_Cargo_Duty, 'NA',e.ex_bond_type "
		+ "FROM cfinbondcrg n1 "
		+ "LEFT JOIN cfinbondcrg_dtl n2 ON n1.company_id=n2.company_id AND n1.branch_id=n2.branch_id AND n1.noc_no=n2.noc_no "
		+ "AND n1.noc_trans_id=n2.noc_trans_id AND n1.boe_no=n2.boe_no AND n1.in_bonding_id=n2.in_bonding_id "
		+ "LEFT JOIN cfexbondcrg e ON n2.company_id=e.company_id AND n2.branch_id=e.branch_id AND n2.noc_no=e.noc_no "
		+ "AND n2.noc_trans_id=e.noc_trans_id AND n2.boe_no=e.boe_no AND n2.in_bonding_id=e.in_bonding_id "
		+ "LEFT JOIN cfexbondcrgdtl e1 ON e.company_id=e1.company_id AND e.branch_id=e1.branch_id AND e.noc_no=e1.noc_no "
		+ "AND e.noc_trans_id=e1.noc_trans_id AND e.boe_no=e1.boe_no AND e.ex_bonding_id=e1.ex_bonding_id "
		+ "WHERE n1.company_id = :cid AND n1.branch_id = :bid AND (e.Balanced_Packages > 0 AND Date(n1.in_bonding_date) <= :startDate AND Date(e.ex_bonding_date)<=:startDate) "
		+ "AND ( CASE WHEN Date(n1.Extension_Date3) >= :endDate THEN 1 WHEN Date(n1.Extension_Date2) >= :endDate THEN 1 "
		+ "WHEN Date(n1.Extension_Date1) >= :endDate THEN 1 WHEN Date(n1.bond_validity_date)>= :endDate THEN 1 ELSE 0 END) = 1 "
		+ "AND (n1.boe_no,n1.in_bonding_id) "
		+ "NOT IN (select a.boe_no,a.in_bonding_id from cfinbondcrg a "
		+ "LEFT JOIN cfexbondcrg b ON a.company_id = b.company_id "
		+ "and a.branch_id = b.branch_id and a.noc_no=b.noc_no and a.noc_trans_id = b.noc_trans_id and a.boe_no = b.boe_no "
		+ "and a.in_bonding_id = b.in_bonding_id where a.company_id= :cid and a.branch_id= :bid and Date(b.ex_bonding_date) "
		+ "between :startDate and :endDate and b.Balanced_Packages = 0 and a.status != 'D' and b.status != 'D') "
		+ "AND e.status != 'D' AND n2.status != 'D' " 
		+ "UNION "
		+ "SELECT n1.boe_no, DATE_FORMAT(n1.BOE_Date,'%d.%m.%Y'), n1.Source_Port, n1.bonding_no, "
		+ "DATE_FORMAT(n1.bonding_date,'%d.%m.%Y'), n2.commodity_description, n2.In_Bonded_Packages, n2.type_of_package, DATE_FORMAT(n1.in_bonding_date,'%d.%m.%Y'), "
		+ "n2.noc_packages, n2.inbond_gross_wt, n2.inbond_cif_value, n2.inbond_cargo_duty, 'NA', 'NA', n1.otl_no, "
		+ "n2.In_Bonded_Packages, (n2.breakage + n2.damaged_qty), n2.shortage_packages, DATE_FORMAT(n1.bond_validity_date,'%d.%m.%Y'), "
		+ "DATE_FORMAT(n1.Extension_Date1,'%d.%m.%Y'), n1.section_64, 'NA', e.ex_bond_be_no, DATE_FORMAT(e.ex_bond_be_date,'%d.%m.%Y'), e1.ex_bonded_packages, "
		+ "e1.ex_bonded_cif, e1.ex_bonded_cargo_duty, e.Balanced_Packages, e.Balance_CIF, e.Balance_Cargo_Duty, 'NA',e.ex_bond_type "
		+ "FROM cfinbondcrg n1 "
		+ "LEFT JOIN cfinbondcrg_dtl n2 ON n1.company_id = n2.company_id AND n1.branch_id = n2.branch_id AND n1.noc_no = n2.noc_no "
		+ "AND n1.noc_trans_id = n2.noc_trans_id AND n1.boe_no = n2.boe_no AND n1.in_bonding_id = n2.in_bonding_id "
		+ "LEFT JOIN cfexbondcrg e ON n2.company_id = e.company_id AND n2.branch_id = e.branch_id AND n2.noc_no = e.noc_no "
		+ "AND n2.noc_trans_id = e.noc_trans_id AND n2.boe_no = e.boe_no AND n2.in_bonding_id = e.in_bonding_id "
		+ "LEFT JOIN cfexbondcrgdtl e1 ON e.company_id = e1.company_id AND e.branch_id = e1.branch_id AND e.noc_no = e1.noc_no "
		+ "AND e.noc_trans_id = e1.noc_trans_id AND e.boe_no = e1.boe_no AND e.ex_bonding_id = e1.ex_bonding_id "
		+ "WHERE n1.company_id = :cid AND n1.branch_id = :bid "
		+ "AND ((e.company_id IS NOT NULL AND Date(e.ex_bonding_date) BETWEEN :startDate AND :endDate AND e.status != 'D') "
		+ "OR (Date(n1.in_bonding_date) BETWEEN :startDate AND :endDate)) "
		+ "AND ( CASE WHEN Date(n1.Extension_Date3) >= :endDate THEN 1 WHEN Date(n1.Extension_Date2) >= :endDate THEN 1 "
		+ "WHEN Date(n1.Extension_Date1) >= :endDate THEN 1 WHEN Date(n1.bond_validity_date)>= :endDate THEN 1 ELSE 0 END) = 1 "
		+ "AND n2.status != 'D' AND n1.status != 'D'", nativeQuery = true)
List<Object[]> getCustomQueryResults(@Param("cid") String cid, @Param("bid") String bid,
		@Param("startDate") Date start, @Param("endDate") Date end);



@Query(value = "SELECT i.boe_no, DATE_FORMAT(i.boe_date,'%d.%m.%Y'), i.bonding_no, "
		+ "DATE_FORMAT(i.bonding_date,'%d.%m.%Y'), i.importer_name, p.party_name, "
		+ "i1.commodity_description, i1.In_Bonded_Packages, i1.inbond_cif_value, "
		+ "i1.inbond_cargo_duty, DATE_FORMAT(i.bond_validity_date,'%d.%m.%Y'), "
		+ "DATE_FORMAT(i.Extension_Date1,'%d.%m.%Y') " 
		+ "FROM cfinbondcrg i "
		+ "LEFT JOIN cfinbondcrg_dtl i1 ON i.company_id = i1.company_id "
		+ "AND i.branch_id = i1.branch_id AND i.noc_no = i1.noc_no "
		+ "AND i.noc_trans_id = i1.noc_trans_id AND i.boe_no = i1.boe_no "
		+ "AND i.in_bonding_id = i1.in_bonding_id "
		+ "LEFT JOIN party p ON i.company_id = p.company_id and i.branch_id=p.branch_id and i.cha = p.party_id "
		+ "WHERE i.company_id = :cid AND i.branch_id = :bid "
		+ "AND (i.ex_bonded_packages = 0 OR (i.ex_bonded_packages != 0 "
		+ "AND (i.in_bonded_packages - i.ex_bonded_packages) > 0)) "
		+ "AND (CASE WHEN DATE(i.Extension_Date3) <= :endDate THEN 1 " 
		+ "WHEN DATE(i.Extension_Date2) <= :endDate THEN 1 "
		+ "WHEN DATE(i.Extension_Date1) <= :endDate THEN 1 "
		+ "WHEN DATE(i.bond_validity_date) <= :endDate THEN 1 ELSE 0 END) = 1 "
		+ "AND DATE(i.in_bonding_date) <= :endDate AND i.status != 'D' AND i1.status != 'D' " + "UNION "
		+ "SELECT i.boe_no, DATE_FORMAT(i.boe_date,'%d.%m.%Y'), i.bonding_no, "
		+ "DATE_FORMAT(i.bonding_date,'%d.%m.%Y'), i.importer_name, p9.party_name, "
		+ "i1.commodity_description, i1.In_Bonded_Packages, i1.inbond_cif_value, "
		+ "i1.inbond_cargo_duty, DATE_FORMAT(i.bond_validity_date,'%d.%m.%Y'), "
		+ "DATE_FORMAT(i.Extension_Date1,'%d.%m.%Y') "
		+ "FROM cfinbondcrg i "
		+ "LEFT JOIN cfinbondcrg_dtl i1 ON i.company_id = i1.company_id "
		+ "AND i.branch_id = i1.branch_id AND i.noc_no = i1.noc_no "
		+ "AND i.noc_trans_id = i1.noc_trans_id AND i.boe_no = i1.boe_no "
		+ "AND i.in_bonding_id = i1.in_bonding_id " 
		+ "LEFT JOIN cfexbondcrg e ON i1.company_id = e.company_id "
		+ "AND i1.branch_id = e.branch_id AND i1.noc_no = e.noc_no "
		+ "AND i1.noc_trans_id = e.noc_trans_id AND i1.boe_no = e.boe_no "
		+ "AND i1.in_bonding_id = e.in_bonding_id " 
		+ "LEFT JOIN party p9 ON i.company_id = p9.company_id and i.branch_id=p9.branch_id and i.cha = p9.party_id "
		+ "WHERE i.company_id = :cid AND i.branch_id = :bid "
		+ "AND (CASE WHEN DATE(i.Extension_Date3) <= :endDate THEN 1 " 
		+ "WHEN DATE(i.Extension_Date2) <= :endDate THEN 1 "
		+ "WHEN DATE(i.Extension_Date1) <= :endDate THEN 1 "
		+ "WHEN DATE(i.bond_validity_date) <= :endDate THEN 1 ELSE 0 END) = 1 "
		+ "AND e.Balanced_Packages > 0 AND DATE(i.in_bonding_date) <= :endDate "
		+ "AND DATE(e.ex_bonding_date) <= :endDate AND i.status != 'D' "
		+ "AND i1.status != 'D' AND e.status != 'D'", nativeQuery = true)
List<Object[]> getCustomQueryResults1(@Param("cid") String cid, @Param("bid") String bid,
		@Param("endDate") Date end);



@Query(value = "SELECT i.boe_no, DATE_FORMAT(i.boe_date,'%d.%m.%Y'), i.bonding_no, "
		+ "DATE_FORMAT(i.bonding_date,'%d.%m.%Y'), i.importer_name, p.party_name, "
		+ "i1.commodity_description, i1.In_Bonded_Packages, i1.inbond_cif_value, "
		+ "i1.inbond_cargo_duty, DATE_FORMAT(i.bond_validity_date,'%d.%m.%Y'), "
		+ "DATE_FORMAT(i.Extension_Date1,'%d.%m.%Y') " 
		+ "FROM cfinbondcrg i "
		+ "LEFT JOIN cfinbondcrg_dtl i1 ON i.company_id = i1.company_id "
		+ "AND i.branch_id = i1.branch_id AND i.noc_no = i1.noc_no "
		+ "AND i.noc_trans_id = i1.noc_trans_id AND i.boe_no = i1.boe_no "
		+ "AND i.in_bonding_id = i1.in_bonding_id " 
		+ "LEFT JOIN party p ON i.company_id = p.company_id and i.branch_id=p.branch_id and i.cha = p.party_id "
		+ "WHERE i.company_id = :cid AND i.branch_id = :bid "
		+ "AND (i.ex_bonded_packages = 0 OR (i.ex_bonded_packages != 0 "
		+ "AND (i.in_bonded_packages - i.ex_bonded_packages) > 0)) "
		+ "AND (CASE WHEN DATE(i.Extension_Date3) between :startDate and :endDate THEN 1 "
		+ "WHEN DATE(i.Extension_Date2) between :startDate and :endDate THEN 1 "
		+ "WHEN DATE(i.Extension_Date1) between :startDate and :endDate THEN 1 "
		+ "WHEN DATE(i.bond_validity_date) between :startDate and :endDate THEN 1 ELSE 0 END) = 1 "
		+ "AND DATE(i.in_bonding_date) <= :endDate AND i.status != 'D' AND i1.status != 'D' " + "UNION "
		+ "SELECT i.boe_no, DATE_FORMAT(i.boe_date,'%d.%m.%Y'), i.bonding_no, "
		+ "DATE_FORMAT(i.bonding_date,'%d.%m.%Y'), i.importer_name, p1.party_name, "
		+ "i1.commodity_description, i1.In_Bonded_Packages, i1.inbond_cif_value, "
		+ "i1.inbond_cargo_duty, DATE_FORMAT(i.bond_validity_date,'%d.%m.%Y'), "
		+ "DATE_FORMAT(i.Extension_Date1,'%d.%m.%Y') " 
		+ "FROM cfinbondcrg i "
		+ "LEFT JOIN cfinbondcrg_dtl i1 ON i.company_id = i1.company_id "
		+ "AND i.branch_id = i1.branch_id AND i.noc_no = i1.noc_no "
		+ "AND i.noc_trans_id = i1.noc_trans_id AND i.boe_no = i1.boe_no "
		+ "AND i.in_bonding_id = i1.in_bonding_id " 
		+ "LEFT JOIN cfexbondcrg e ON i1.company_id = e.company_id "
		+ "AND i1.branch_id = e.branch_id AND i1.noc_no = e.noc_no "
		+ "AND i1.noc_trans_id = e.noc_trans_id AND i1.boe_no = e.boe_no "
		+ "AND i1.in_bonding_id = e.in_bonding_id " 
		+ "LEFT JOIN party p1 ON i.company_id = p1.company_id and i.branch_id=p1.branch_id and i.cha = p1.party_id "
		+ "WHERE i.company_id = :cid AND i.branch_id = :bid "
		+ "AND (CASE WHEN DATE(i.Extension_Date3) between :startDate and :endDate THEN 1 "
		+ "WHEN DATE(i.Extension_Date2) between :startDate and :endDate THEN 1 "
		+ "WHEN DATE(i.Extension_Date1) between :startDate and :endDate THEN 1 "
		+ "WHEN DATE(i.bond_validity_date) <= :endDate THEN 1 ELSE 0 END) = 1 "
		+ "AND e.Balanced_Packages > 0 AND DATE(i.in_bonding_date) <= :endDate "
		+ "AND DATE(e.ex_bonding_date) <= :endDate AND i.status != 'D' "
		+ "AND i1.status != 'D' AND e.status != 'D'", nativeQuery = true)
List<Object[]> getCustomQueryResults2(@Param("cid") String cid, @Param("bid") String bid,
		@Param("startDate") Date start, @Param("endDate") Date end);


}
