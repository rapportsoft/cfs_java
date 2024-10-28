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
		@Query("SELECT I.inBondingDate, I.bondingNo, I.bondingDate, I.bondingNo, I.bondValidityDate, I.boeNo, I.boeDate, I.igmNo, "
				+ "I.grossWeight, I.inbondGrossWt, I.cifValue, I.cargoDuty " + "FROM Cfinbondcrg I "
				+ "WHERE I.companyId = :companyId AND I.branchId = :branchId "
				+ "AND Date(I.inBondingDate) BETWEEN :startDate AND :endDate")
		List<Object[]> findinbondDataLiveBondInbond(@Param("companyId") String companyId,
				@Param("branchId") String branchId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

	
		
//		EXBOND LIVEBOND
@Query("SELECT I.bondingNo, I.bondingDate, I.exBondBeNo, I.exBondBeDate, I.igmNo, I.exBondingDate, I.exBondedGw, "
+ "I.exBondedCif, I.exBondedCargoDuty " + "FROM CfExBondCrg I "
+ "WHERE I.companyId = :companyId AND I.branchId = :branchId "
+ "AND Date(I.exBondingDate) BETWEEN :startDate AND :endDate")
List<Object[]> findinbondDataLiveBondExbond(@Param("companyId") String companyId,
@Param("branchId") String branchId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
	

@Query(value = "SELECT COUNT(*) AS count, " + "COALESCE(SUM(CIF_Value), 0) AS cifValueSum, "
		+ "COALESCE(SUM(Cargo_Duty), 0) AS cargoDutySum " + "FROM (" + "    SELECT n1.in_bonding_id, "
		+ "           MAX(n1.CIF_Value) AS CIF_Value, "
		+ "           MAX(n1.Cargo_Duty) AS Cargo_Duty " + "    FROM cfinbondcrg n1 "
		+ "    LEFT JOIN CfExBondCrg e " + "    ON n1.company_id = e.company_id "
		+ "       AND n1.branch_id = e.branch_id " + "       AND n1.noc_no = e.noc_no "
		+ "       AND n1.noc_trans_id = e.noc_trans_id " + "       AND n1.boe_no = e.boe_no "
		+ "       AND n1.in_bonding_id = e.in_bonding_id " + "    WHERE n1.company_id = :companyId "
		+ "      AND n1.branch_id = :branchId " + "      AND (" + "          CASE "
		+ "              WHEN Date(n1.ext_date3) >= :endDate THEN 1 "
		+ "              WHEN Date(n1.ext_date2) >= :endDate THEN 1 "
		+ "              WHEN Date(n1.ext_date1) >= :endDate THEN 1 "
		+ "              WHEN Date(n1.bond_validity_date) >= :endDate THEN 1 " + "              ELSE 0 "
		+ "          END) = 1 " + "      AND (n1.in_bonded_packages - n1.ex_bonded_packages) > 0 "
		+ "      AND Date(n1.in_bonding_date) < :currentDate " + "    GROUP BY n1.in_bonding_id " + "    UNION "
		+ "    SELECT n1.in_bonding_id, " + "           MAX(n1.CIF_Value) AS CIF_Value, "
		+ "           MAX(n1.Cargo_Duty) AS Cargo_Duty " + "    FROM cfinbondcrg n1 "
		+ "    LEFT JOIN CfExBondCrg e " + "    ON n1.company_id = e.company_id "
		+ "       AND n1.branch_id = e.branch_id " + "       AND n1.noc_no = e.noc_no "
		+ "       AND n1.noc_trans_id = e.noc_trans_id " + "       AND n1.boe_no = e.boe_no "
		+ "       AND n1.in_bonding_id = e.in_bonding_id " + "    WHERE n1.company_id = :companyId "
		+ "      AND n1.branch_id = :branchId " + "      AND (" + "          CASE "
		+ "              WHEN Date(n1.ext_date3) >= :endDate THEN 1 "
		+ "              WHEN Date(n1.ext_date2) >= :endDate THEN 1 "
		+ "              WHEN Date(n1.ext_date1) >= :endDate THEN 1 "
		+ "              WHEN Date(n1.bond_validity_date) >= :endDate THEN 1 " + "              ELSE 0 "
		+ "          END) = 1 " + "      AND Date(n1.in_bonding_date) < :currentDate "
		+ "      AND e.balance_bonded_packages > 0 " + "      AND Date(e.ex_bonding_date) < :currentDate "
		+ "      AND (e.noc_no, e.boe_no, e.noc_trans_id) NOT IN ("
		+ "          SELECT n.noc_no, n.boe_no, n.noc_trans_id " + "          FROM CfExBondCrg n "
		+ "          WHERE n.company_id = :companyId " + "            AND n.branch_id = :branchId "
		+ "            AND Date(n.ex_bonding_date) < :currentDate "
		+ "            AND n.balance_bonded_packages = 0" + "      ) " + "    GROUP BY n1.in_bonding_id"
		+ ") AS combined", nativeQuery = true)
List<Object[]> getLiveOpeningBalance(@Param("companyId") String companyId, @Param("branchId") String branchId,
		@Param("currentDate") Date currentDate,@Param("endDate") Date endDate);


@Query(value = "WITH SumValues AS ( " + "    SELECT ex.noc_no, "
		+ "           COALESCE(SUM(ex.ex_bonded_cif), 0) AS cifValueSum, "
		+ "           COALESCE(SUM(ex.ex_bonded_cargo_duty), 0) AS cargoDutySum " + "    FROM cfinbondcrg i "
		+ "    JOIN CfExBondCrg ex ON i.company_id = ex.company_id "
		+ "                       AND i.branch_id = ex.branch_id "
		+ "                       AND i.noc_no = ex.noc_no "
		+ "                       AND i.noc_trans_id = ex.noc_trans_id "
		+ "                       AND i.boe_no = ex.boe_no "
		+ "                       AND i.in_bonding_id = ex.in_bonding_id " + "    WHERE ex.company_id = :companyId "
		+ "      AND ex.branch_id = :branchId " + "      AND ( " + "          CASE "
		+ "              WHEN Date(i.ext_date3) >= :endDate THEN 1 "
		+ "              WHEN Date(i.ext_date2) >= :endDate THEN 1 "
		+ "              WHEN Date(i.ext_date1) >= :endDate THEN 1 "
		+ "              WHEN Date(i.bond_validity_date) >= :endDate THEN 1 " + "              ELSE 0 "
		+ "          END) = 1 "
		+ "      AND i.status != 'D' " + "      AND ex.status != 'D' " + "    GROUP BY ex.noc_no " + ") "
		+ "SELECT COALESCE(COUNT(DISTINCT n3.in_bonding_id), 0) AS count2, "
		+ "       COALESCE(SUM(n3.CIF_Value), 0) AS cifValueSum2, "
		+ "       COALESCE(SUM(n3.Cargo_Duty), 0) AS cargoDutySum2 " + "FROM cfinbondcrg n3 "
		+ "WHERE n3.company_id = :companyId " + "  AND n3.branch_id = :branchId " + "  AND ( " + "      CASE "
		+ "          WHEN Date(n3.ext_date3) >= :endDate THEN 1 "
		+ "          WHEN Date(n3.ext_date2) >= :endDate THEN 1 "
		+ "          WHEN Date(n3.ext_date1) >= :endDate THEN 1 "
		+ "          WHEN Date(n3.bond_validity_date) >= :endDate THEN 1 " + "          ELSE 0 " + "      END) = 1 "
		+ "  AND Date(n3.in_bonding_date) BETWEEN :startDate AND :endDate " + "  AND n3.status != 'D' "
		+ "UNION ALL " + "SELECT COALESCE(COUNT(DISTINCT n3.in_bonding_id), 0) AS count2, "
		+ "       COALESCE(SUM(n3.CIF_Value), 0) AS cifValueSum2, "
		+ "       COALESCE(SUM(n3.Cargo_Duty), 0) AS cargoDutySum2 " + "FROM cfinbondcrg n3 "
		+ "WHERE n3.company_id = :companyId " + "  AND n3.branch_id = :branchId " + "  AND ( " + "      CASE "
		+ "          WHEN Date(n3.ext_date3) >= :endDate THEN 1 "
		+ "          WHEN Date(n3.ext_date2) >= :endDate THEN 1 "
		+ "          WHEN Date(n3.ext_date1) >= :endDate THEN 1 "
		+ "          WHEN Date(n3.bond_validity_date) >= :endDate THEN 1 " + "          ELSE 0 " + "      END) = 1 "
		+ "  AND Date(n3.in_bonding_date) BETWEEN :aprilDate AND :lastDate " + "  AND n3.status != 'D' "
		+ "UNION ALL "
		+ "SELECT COALESCE(COUNT(DISTINCT CASE WHEN ex.balance_bonded_packages = 0 THEN ex.noc_no END), 0) AS count, "
		+ "       COALESCE(SUM(s.cifValueSum), 0) AS cifValueSum, "
		+ "       COALESCE(SUM(s.cargoDutySum), 0) AS cargoDutySum " + "FROM SumValues s "
		+ "JOIN CfExBondCrg ex ON s.noc_no = ex.noc_no " + "WHERE ex.company_id = :companyId "
		+ "  AND ex.branch_id = :branchId " + "  AND ex.balance_bonded_packages = 0 AND Date(ex.ex_bonding_date) BETWEEN :startDate AND :endDate " + "UNION ALL "
		+ "SELECT COALESCE(COUNT(DISTINCT CASE WHEN ex.balance_bonded_packages = 0 THEN ex.noc_no END), 0) AS count, "
		+ "       COALESCE(SUM(s.cifValueSum), 0) AS cifValueSum, "
		+ "       COALESCE(SUM(s.cargoDutySum), 0) AS cargoDutySum " + "FROM SumValues s "
		+ "JOIN CfExBondCrg ex ON s.noc_no = ex.noc_no " + "WHERE ex.company_id = :companyId "
		+ "  AND ex.branch_id = :branchId " + "  AND ex.balance_bonded_packages = 0 AND Date(ex.ex_bonding_date) BETWEEN :aprilDate AND :lastDate", nativeQuery = true)
List<Object[]> getLiveReceiptsAndDisposal(@Param("companyId") String companyId, @Param("branchId") String branchId,
		@Param("startDate") Date startDate, @Param("endDate") Date endDate,@Param("aprilDate") Date aprilDate, @Param("lastDate") Date lastDate);

@Query(value = "SELECT COUNT(*) AS count, COALESCE(SUM(CIF_Value), 0) AS cifValueSum, COALESCE(SUM(Cargo_Duty), 0) AS cargoDutySum FROM (SELECT n1.in_bonding_id, MAX(n1.CIF_Value) AS CIF_Value, MAX(n1.Cargo_Duty) AS Cargo_Duty FROM cfinbondcrg n1 LEFT JOIN CfExBondCrg e ON n1.company_id = e.company_id AND n1.branch_id = e.branch_id AND n1.noc_no = e.noc_no AND n1.noc_trans_id = e.noc_trans_id AND n1.boe_no = e.boe_no AND n1.in_bonding_id = e.in_bonding_id WHERE n1.company_id = :cid AND n1.branch_id = :bid AND (CASE WHEN Date(n1.ext_date3) >= :endDate THEN 1 WHEN Date(n1.ext_date2) >= :endDate THEN 1 WHEN Date(n1.ext_date1) >= :endDate THEN 1 WHEN Date(n1.bond_validity_date) >= :endDate THEN 1 ELSE 0 END) = 1 AND (n1.in_bonded_packages - n1.ex_bonded_packages) > 0 AND Date(n1.in_bonding_date) between :start AND :end GROUP BY n1.in_bonding_id "
		+ "UNION "
		+ "SELECT n1.in_bonding_id, MAX(n1.CIF_Value) AS CIF_Value, MAX(n1.Cargo_Duty) AS Cargo_Duty FROM cfinbondcrg n1 LEFT JOIN CfExBondCrg e ON n1.company_id = e.company_id AND n1.branch_id = e.branch_id AND n1.noc_no = e.noc_no AND n1.noc_trans_id = e.noc_trans_id AND n1.boe_no = e.boe_no AND n1.in_bonding_id = e.in_bonding_id WHERE n1.company_id = :cid AND n1.branch_id = :bid AND (CASE WHEN Date(n1.ext_date3) >= :endDate THEN 1 WHEN Date(n1.ext_date2) >= :endDate THEN 1 WHEN Date(n1.ext_date1) >= :endDate THEN 1 WHEN Date(n1.bond_validity_date) >= :endDate THEN 1 ELSE 0 END) = 1 AND Date(n1.in_bonding_date) between :start AND :end AND e.balance_bonded_packages > 0 AND Date(e.ex_bonding_date) between :start AND :end AND (e.noc_no, e.boe_no, e.noc_trans_id) NOT IN (SELECT n.noc_no, n.boe_no, n.noc_trans_id FROM CfExBondCrg n WHERE n.company_id = :cid AND n.branch_id = :bid AND Date(n.ex_bonding_date) between :start AND :end AND n.balance_bonded_packages = 0) GROUP BY n1.in_bonding_id) AS combined "
		+ "UNION ALL "
		+ "SELECT COUNT(*) AS count, COALESCE(SUM(CIF_Value), 0) AS cifValueSum, COALESCE(SUM(Cargo_Duty), 0) AS cargoDutySum FROM (SELECT n1.in_bonding_id, MAX(n1.CIF_Value) AS CIF_Value, MAX(n1.Cargo_Duty) AS Cargo_Duty FROM cfinbondcrg n1 LEFT JOIN CfExBondCrg e ON n1.company_id = e.company_id AND n1.branch_id = e.branch_id AND n1.noc_no = e.noc_no AND n1.noc_trans_id = e.noc_trans_id AND n1.boe_no = e.boe_no AND n1.in_bonding_id = e.in_bonding_id WHERE n1.company_id = :cid AND n1.branch_id = :bid AND (CASE WHEN Date(n1.ext_date3) >= :endDate THEN 1 WHEN Date(n1.ext_date2) >= :endDate THEN 1 WHEN Date(n1.ext_date1) >= :endDate THEN 1 WHEN Date(n1.bond_validity_date) >= :endDate THEN 1 ELSE 0 END) = 1 AND (n1.in_bonded_packages - n1.ex_bonded_packages) > 0 AND Date(n1.in_bonding_date) between :start13 AND :end13 GROUP BY n1.in_bonding_id "
		+ "UNION "
		+ "SELECT n1.in_bonding_id, MAX(n1.CIF_Value) AS CIF_Value, MAX(n1.Cargo_Duty) AS Cargo_Duty FROM cfinbondcrg n1 LEFT JOIN CfExBondCrg e ON n1.company_id = e.company_id AND n1.branch_id = e.branch_id AND n1.noc_no = e.noc_no AND n1.noc_trans_id = e.noc_trans_id AND n1.boe_no = e.boe_no AND n1.in_bonding_id = e.in_bonding_id WHERE n1.company_id = :cid AND n1.branch_id = :bid AND (CASE WHEN Date(n1.ext_date3) >= :endDate THEN 1 WHEN Date(n1.ext_date2) >= :endDate THEN 1 WHEN Date(n1.ext_date1) >= :endDate THEN 1 WHEN Date(n1.bond_validity_date) >= :endDate THEN 1 ELSE 0 END) = 1 AND Date(n1.in_bonding_date) between :start13 AND :end13 AND e.balance_bonded_packages > 0 AND Date(e.ex_bonding_date) between :start13 AND :end13 AND (e.noc_no, e.boe_no, e.noc_trans_id) NOT IN (SELECT n.noc_no, n.boe_no, n.noc_trans_id FROM CfExBondCrg n WHERE n.company_id = :cid AND n.branch_id = :bid AND Date(n.ex_bonding_date) between :start13 AND :end13 AND n.balance_bonded_packages = 0) GROUP BY n1.in_bonding_id) AS combined "
		+ "UNION ALL "
		+ "SELECT COUNT(*) AS count, COALESCE(SUM(CIF_Value), 0) AS cifValueSum, COALESCE(SUM(Cargo_Duty), 0) AS cargoDutySum FROM (SELECT n1.in_bonding_id, MAX(n1.CIF_Value) AS CIF_Value, MAX(n1.Cargo_Duty) AS Cargo_Duty FROM cfinbondcrg n1 LEFT JOIN CfExBondCrg e ON n1.company_id = e.company_id AND n1.branch_id = e.branch_id AND n1.noc_no = e.noc_no AND n1.noc_trans_id = e.noc_trans_id AND n1.boe_no = e.boe_no AND n1.in_bonding_id = e.in_bonding_id WHERE n1.company_id = :cid AND n1.branch_id = :bid AND (CASE WHEN Date(n1.ext_date3) >= :endDate THEN 1 WHEN Date(n1.ext_date2) >= :endDate THEN 1 WHEN Date(n1.ext_date1) >= :endDate THEN 1 WHEN Date(n1.bond_validity_date) >= :endDate THEN 1 ELSE 0 END) = 1 AND (n1.in_bonded_packages - n1.ex_bonded_packages) > 0 AND Date(n1.in_bonding_date) between :start36 AND :end36 GROUP BY n1.in_bonding_id "
		+ "UNION "
		+ "SELECT n1.in_bonding_id, MAX(n1.CIF_Value) AS CIF_Value, MAX(n1.Cargo_Duty) AS Cargo_Duty FROM cfinbondcrg n1 LEFT JOIN CfExBondCrg e ON n1.company_id = e.company_id AND n1.branch_id = e.branch_id AND n1.noc_no = e.noc_no AND n1.noc_trans_id = e.noc_trans_id AND n1.boe_no = e.boe_no AND n1.in_bonding_id = e.in_bonding_id WHERE n1.company_id = :cid AND n1.branch_id = :bid AND (CASE WHEN Date(n1.ext_date3) >= :endDate THEN 1 WHEN Date(n1.ext_date2) >= :endDate THEN 1 WHEN Date(n1.ext_date1) >= :endDate THEN 1 WHEN Date(n1.bond_validity_date) >= :endDate THEN 1 ELSE 0 END) = 1 AND Date(n1.in_bonding_date) between :start36 AND :end36 AND e.balance_bonded_packages > 0 AND Date(e.ex_bonding_date) between :start36 AND :end36 AND (e.noc_no, e.boe_no, e.noc_trans_id) NOT IN (SELECT n.noc_no, n.boe_no, n.noc_trans_id FROM CfExBondCrg n WHERE n.company_id = :cid AND n.branch_id = :bid AND Date(n.ex_bonding_date) between :start36 AND :end36 AND n.balance_bonded_packages = 0) GROUP BY n1.in_bonding_id) AS combined "
		+ "UNION ALL "
		+ "SELECT COUNT(*) AS count, COALESCE(SUM(CIF_Value), 0) AS cifValueSum, COALESCE(SUM(Cargo_Duty), 0) AS cargoDutySum FROM (SELECT n1.in_bonding_id, MAX(n1.CIF_Value) AS CIF_Value, MAX(n1.Cargo_Duty) AS Cargo_Duty FROM cfinbondcrg n1 LEFT JOIN CfExBondCrg e ON n1.company_id = e.company_id AND n1.branch_id = e.branch_id AND n1.noc_no = e.noc_no AND n1.noc_trans_id = e.noc_trans_id AND n1.boe_no = e.boe_no AND n1.in_bonding_id = e.in_bonding_id WHERE n1.company_id = :cid AND n1.branch_id = :bid AND (CASE WHEN Date(n1.ext_date3) >= :endDate THEN 1 WHEN Date(n1.ext_date2) >= :endDate THEN 1 WHEN Date(n1.ext_date1) >= :endDate THEN 1 WHEN Date(n1.bond_validity_date) >= :endDate THEN 1 ELSE 0 END) = 1 AND (n1.in_bonded_packages - n1.ex_bonded_packages) > 0 AND Date(n1.in_bonding_date) between :start612 AND :end612 GROUP BY n1.in_bonding_id "
		+ "UNION "
		+ "SELECT n1.in_bonding_id, MAX(n1.CIF_Value) AS CIF_Value, MAX(n1.Cargo_Duty) AS Cargo_Duty FROM cfinbondcrg n1 LEFT JOIN CfExBondCrg e ON n1.company_id = e.company_id AND n1.branch_id = e.branch_id AND n1.noc_no = e.noc_no AND n1.noc_trans_id = e.noc_trans_id AND n1.boe_no = e.boe_no AND n1.in_bonding_id = e.in_bonding_id WHERE n1.company_id = :cid AND n1.branch_id = :bid AND (CASE WHEN Date(n1.ext_date3) >= :endDate THEN 1 WHEN Date(n1.ext_date2) >= :endDate THEN 1 WHEN Date(n1.ext_date1) >= :endDate THEN 1 WHEN Date(n1.bond_validity_date) >= :endDate THEN 1 ELSE 0 END) = 1 AND Date(n1.in_bonding_date) between :start612 AND :end612 AND e.balance_bonded_packages > 0 AND Date(e.ex_bonding_date) between :start612 AND :end612 AND (e.noc_no, e.boe_no, e.noc_trans_id) NOT IN (SELECT n.noc_no, n.boe_no, n.noc_trans_id FROM CfExBondCrg n WHERE n.company_id = :cid AND n.branch_id = :bid AND Date(n.ex_bonding_date) between :start612 AND :end612 AND n.balance_bonded_packages = 0) GROUP BY n1.in_bonding_id) AS combined "
		+ "UNION ALL "
		+ "SELECT COUNT(*) AS count, COALESCE(SUM(CIF_Value), 0) AS cifValueSum, COALESCE(SUM(Cargo_Duty), 0) AS cargoDutySum FROM (SELECT n1.in_bonding_id, MAX(n1.CIF_Value) AS CIF_Value, MAX(n1.Cargo_Duty) AS Cargo_Duty FROM cfinbondcrg n1 LEFT JOIN CfExBondCrg e ON n1.company_id = e.company_id AND n1.branch_id = e.branch_id AND n1.noc_no = e.noc_no AND n1.noc_trans_id = e.noc_trans_id AND n1.boe_no = e.boe_no AND n1.in_bonding_id = e.in_bonding_id WHERE n1.company_id = :cid AND n1.branch_id = :bid AND (CASE WHEN Date(n1.ext_date3) >= :endDate THEN 1 WHEN Date(n1.ext_date2) >= :endDate THEN 1 WHEN Date(n1.ext_date1) >= :endDate THEN 1 WHEN Date(n1.bond_validity_date) >= :endDate THEN 1 ELSE 0 END) = 1 AND (n1.in_bonded_packages - n1.ex_bonded_packages) > 0 AND Date(n1.in_bonding_date) between :start1y AND :end1y GROUP BY n1.in_bonding_id "
		+ "UNION "
		+ "SELECT n1.in_bonding_id, MAX(n1.CIF_Value) AS CIF_Value, MAX(n1.Cargo_Duty) AS Cargo_Duty FROM cfinbondcrg n1 LEFT JOIN CfExBondCrg e ON n1.company_id = e.company_id AND n1.branch_id = e.branch_id AND n1.noc_no = e.noc_no AND n1.noc_trans_id = e.noc_trans_id AND n1.boe_no = e.boe_no AND n1.in_bonding_id = e.in_bonding_id WHERE n1.company_id = :cid AND n1.branch_id = :bid AND (CASE WHEN Date(n1.ext_date3) >= :endDate THEN 1 WHEN Date(n1.ext_date2) >= :endDate THEN 1 WHEN Date(n1.ext_date1) >= :endDate THEN 1 WHEN Date(n1.bond_validity_date) >= :endDate THEN 1 ELSE 0 END) = 1 AND Date(n1.in_bonding_date) between :start1y AND :end1y AND e.balance_bonded_packages > 0 AND Date(e.ex_bonding_date) between :start1y AND :end1y AND (e.noc_no, e.boe_no, e.noc_trans_id) NOT IN (SELECT n.noc_no, n.boe_no, n.noc_trans_id FROM CfExBondCrg n WHERE n.company_id = :cid AND n.branch_id = :bid AND Date(n.ex_bonding_date) between :start1y AND :end1y AND n.balance_bonded_packages = 0) GROUP BY n1.in_bonding_id) AS combined "
		+ "UNION ALL "
		+ "SELECT COUNT(*) AS count, COALESCE(SUM(CIF_Value), 0) AS cifValueSum, COALESCE(SUM(Cargo_Duty), 0) AS cargoDutySum FROM (SELECT n1.in_bonding_id, MAX(n1.CIF_Value) AS CIF_Value, MAX(n1.Cargo_Duty) AS Cargo_Duty FROM cfinbondcrg n1 LEFT JOIN CfExBondCrg e ON n1.company_id = e.company_id AND n1.branch_id = e.branch_id AND n1.noc_no = e.noc_no AND n1.noc_trans_id = e.noc_trans_id AND n1.boe_no = e.boe_no AND n1.in_bonding_id = e.in_bonding_id WHERE n1.company_id = :cid AND n1.branch_id = :bid AND (CASE WHEN Date(n1.ext_date3) >= :endDate THEN 1 WHEN Date(n1.ext_date2) >= :endDate THEN 1 WHEN Date(n1.ext_date1) >= :endDate THEN 1 WHEN Date(n1.bond_validity_date) >= :endDate THEN 1 ELSE 0 END) = 1 AND (n1.in_bonded_packages - n1.ex_bonded_packages) > 0 AND Date(n1.in_bonding_date) between :start2y AND :end2y GROUP BY n1.in_bonding_id "
		+ "UNION "
		+ "SELECT n1.in_bonding_id, MAX(n1.CIF_Value) AS CIF_Value, MAX(n1.Cargo_Duty) AS Cargo_Duty FROM cfinbondcrg n1 LEFT JOIN CfExBondCrg e ON n1.company_id = e.company_id AND n1.branch_id = e.branch_id AND n1.noc_no = e.noc_no AND n1.noc_trans_id = e.noc_trans_id AND n1.boe_no = e.boe_no AND n1.in_bonding_id = e.in_bonding_id WHERE n1.company_id = :cid AND n1.branch_id = :bid AND (CASE WHEN Date(n1.ext_date3) >= :endDate THEN 1 WHEN Date(n1.ext_date2) >= :endDate THEN 1 WHEN Date(n1.ext_date1) >= :endDate THEN 1 WHEN Date(n1.bond_validity_date) >= :endDate THEN 1 ELSE 0 END) = 1 AND Date(n1.in_bonding_date) between :start2y AND :end2y AND e.balance_bonded_packages > 0 AND Date(e.ex_bonding_date) between :start2y AND :end2y AND (e.noc_no, e.boe_no, e.noc_trans_id) NOT IN (SELECT n.noc_no, n.boe_no, n.noc_trans_id FROM CfExBondCrg n WHERE n.company_id = :cid AND n.branch_id = :bid AND Date(n.ex_bonding_date) between :start2y AND :end2y AND n.balance_bonded_packages = 0) GROUP BY n1.in_bonding_id) AS combined "
		+ "UNION ALL "
		+ "SELECT COUNT(*) AS count, COALESCE(SUM(CIF_Value), 0) AS cifValueSum, COALESCE(SUM(Cargo_Duty), 0) AS cargoDutySum FROM (SELECT n1.in_bonding_id, MAX(n1.CIF_Value) AS CIF_Value, MAX(n1.Cargo_Duty) AS Cargo_Duty FROM cfinbondcrg n1 LEFT JOIN CfExBondCrg e ON n1.company_id = e.company_id AND n1.branch_id = e.branch_id AND n1.noc_no = e.noc_no AND n1.noc_trans_id = e.noc_trans_id AND n1.boe_no = e.boe_no AND n1.in_bonding_id = e.in_bonding_id WHERE n1.company_id = :cid AND n1.branch_id = :bid AND (CASE WHEN Date(n1.ext_date3) >= :endDate THEN 1 WHEN Date(n1.ext_date2) >= :endDate THEN 1 WHEN Date(n1.ext_date1) >= :endDate THEN 1 WHEN Date(n1.bond_validity_date) >= :endDate THEN 1 ELSE 0 END) = 1 AND (n1.in_bonded_packages - n1.ex_bonded_packages) > 0 AND Date(n1.in_bonding_date) < :start3y GROUP BY n1.in_bonding_id "
		+ "UNION "
		+ "SELECT n1.in_bonding_id, MAX(n1.CIF_Value) AS CIF_Value, MAX(n1.Cargo_Duty) AS Cargo_Duty FROM cfinbondcrg n1 LEFT JOIN CfExBondCrg e ON n1.company_id = e.company_id AND n1.branch_id = e.branch_id AND n1.noc_no = e.noc_no AND n1.noc_trans_id = e.noc_trans_id AND n1.boe_no = e.boe_no AND n1.in_bonding_id = e.in_bonding_id WHERE n1.company_id = :cid AND n1.branch_id = :bid AND (CASE WHEN Date(n1.ext_date3) >= :endDate THEN 1 WHEN Date(n1.ext_date2) >= :endDate THEN 1 WHEN Date(n1.ext_date1) >= :endDate THEN 1 WHEN Date(n1.bond_validity_date) >= :endDate THEN 1 ELSE 0 END) = 1 AND Date(n1.in_bonding_date) < :start3y AND e.balance_bonded_packages > 0 AND Date(e.ex_bonding_date)< :start3y AND (e.noc_no, e.boe_no, e.noc_trans_id) NOT IN (SELECT n.noc_no, n.boe_no, n.noc_trans_id FROM CfExBondCrg n WHERE n.company_id = :cid AND n.branch_id = :bid AND Date(n.ex_bonding_date) < :start3y AND n.balance_bonded_packages = 0) GROUP BY n1.in_bonding_id) AS combined ", nativeQuery = true)
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
		+ "COALESCE(SUM(Cargo_Duty), 0) AS cargoDutySum " + "FROM (" + "    SELECT n1.in_bonding_id, "
		+ "           MAX(n1.CIF_Value) AS CIF_Value, "
		+ "           MAX(n1.Cargo_Duty) AS Cargo_Duty " + "    FROM cfinbondcrg n1 "
		+ "    LEFT JOIN CfExBondCrg e " + "    ON n1.company_id = e.company_id "
		+ "       AND n1.branch_id = e.branch_id " + "       AND n1.noc_no = e.noc_no "
		+ "       AND n1.noc_trans_id = e.noc_trans_id " + "       AND n1.boe_no = e.boe_no "
		+ "       AND n1.in_bonding_id = e.in_bonding_id " + "    WHERE n1.company_id = :companyId "
		+ "      AND n1.branch_id = :branchId " + "      AND (" + "          CASE "
		+ "              WHEN Date(n1.ext_date3) < :endDate THEN 1 "
		+ "              WHEN Date(n1.ext_date2) < :endDate THEN 1 "
		+ "              WHEN Date(n1.ext_date1) < :endDate THEN 1 "
		+ "              WHEN Date(n1.bond_validity_date) < :endDate THEN 1 " + "              ELSE 0 "
		+ "          END) = 1 " + "      AND (n1.in_bonded_packages - n1.ex_bonded_packages) > 0 "
		+ "      AND Date(n1.in_bonding_date) < :currentDate " + "    GROUP BY n1.in_bonding_id " + "    UNION "
		+ "    SELECT n1.in_bonding_id, " + "           MAX(n1.CIF_Value) AS CIF_Value, "
		+ "           MAX(n1.Cargo_Duty) AS Cargo_Duty " + "    FROM cfinbondcrg n1 "
		+ "    LEFT JOIN CfExBondCrg e " + "    ON n1.company_id = e.company_id "
		+ "       AND n1.branch_id = e.branch_id " + "       AND n1.noc_no = e.noc_no "
		+ "       AND n1.noc_trans_id = e.noc_trans_id " + "       AND n1.boe_no = e.boe_no "
		+ "       AND n1.in_bonding_id = e.in_bonding_id " + "    WHERE n1.company_id = :companyId "
		+ "      AND n1.branch_id = :branchId " + "      AND (" + "          CASE "
		+ "              WHEN Date(n1.ext_date3) < :endDate THEN 1 "
		+ "              WHEN Date(n1.ext_date2) < :endDate THEN 1 "
		+ "              WHEN Date(n1.ext_date1) < :endDate THEN 1 "
		+ "              WHEN Date(n1.bond_validity_date) < :endDate THEN 1 " + "              ELSE 0 "
		+ "          END) = 1 " + "      AND Date(n1.in_bonding_date) < :currentDate "
		+ "      AND e.balance_bonded_packages > 0 " + "      AND Date(e.ex_bonding_date) < :currentDate "
		+ "      AND (e.noc_no, e.boe_no, e.noc_trans_id) NOT IN ("
		+ "          SELECT n.noc_no, n.boe_no, n.noc_trans_id " + "          FROM CfExBondCrg n "
		+ "          WHERE n.company_id = :companyId " + "            AND n.branch_id = :branchId "
		+ "            AND Date(n.ex_bonding_date) < :currentDate "
		+ "            AND n.balance_bonded_packages = 0" + "      ) " + "    GROUP BY n1.in_bonding_id"
		+ ") AS combined", nativeQuery = true)
List<Object[]> getLiveOpeningBalance1(@Param("companyId") String companyId, @Param("branchId") String branchId,
		@Param("currentDate") Date currentDate,@Param("endDate") Date endDate);


@Query(value = "WITH SumValues AS ( " + "    SELECT ex.noc_no, "
		+ "           COALESCE(SUM(ex.ex_bonded_cif), 0) AS cifValueSum, "
		+ "           COALESCE(SUM(ex.ex_bonded_cargo_duty), 0) AS cargoDutySum " + "    FROM cfinbondcrg i "
		+ "    JOIN CfExBondCrg ex ON i.company_id = ex.company_id "
		+ "                       AND i.branch_id = ex.branch_id "
		+ "                       AND i.noc_no = ex.noc_no "
		+ "                       AND i.noc_trans_id = ex.noc_trans_id "
		+ "                       AND i.boe_no = ex.boe_no "
		+ "                       AND i.in_bonding_id = ex.in_bonding_id " + "    WHERE ex.company_id = :companyId "
		+ "      AND ex.branch_id = :branchId " + "      AND ( " + "          CASE "
		+ "              WHEN Date(i.ext_date3) < :endDate THEN 1 "
		+ "              WHEN Date(i.ext_date2) < :endDate THEN 1 "
		+ "              WHEN Date(i.ext_date1) < :endDate THEN 1 "
		+ "              WHEN Date(i.bond_validity_date) < :endDate THEN 1 " + "              ELSE 0 "
		+ "          END) = 1 "
		+ "      AND i.status != 'D' " + "      AND ex.status != 'D' " + "    GROUP BY ex.noc_no " + ") "
		+ "SELECT COALESCE(COUNT(DISTINCT n3.in_bonding_id), 0) AS count2, "
		+ "       COALESCE(SUM(n3.CIF_Value), 0) AS cifValueSum2, "
		+ "       COALESCE(SUM(n3.Cargo_Duty), 0) AS cargoDutySum2 " + "FROM cfinbondcrg n3 "
		+ "WHERE n3.company_id = :companyId " + "  AND n3.branch_id = :branchId " + "  AND ( " + "      CASE "
		+ "          WHEN Date(n3.ext_date3) < :endDate THEN 1 "
		+ "          WHEN Date(n3.ext_date2) < :endDate THEN 1 "
		+ "          WHEN Date(n3.ext_date1) < :endDate THEN 1 "
		+ "          WHEN Date(n3.bond_validity_date) < :endDate THEN 1 " + "          ELSE 0 " + "      END) = 1 "
		+ "  AND Date(n3.in_bonding_date) BETWEEN :startDate AND :endDate " + "  AND n3.status != 'D' "
		+ "UNION ALL " + "SELECT COALESCE(COUNT(DISTINCT n3.in_bonding_id), 0) AS count2, "
		+ "       COALESCE(SUM(n3.CIF_Value), 0) AS cifValueSum2, "
		+ "       COALESCE(SUM(n3.Cargo_Duty), 0) AS cargoDutySum2 " + "FROM cfinbondcrg n3 "
		+ "WHERE n3.company_id = :companyId " + "  AND n3.branch_id = :branchId " + "  AND ( " + "      CASE "
		+ "          WHEN Date(n3.ext_date3) < :endDate THEN 1 "
		+ "          WHEN Date(n3.ext_date2) < :endDate THEN 1 "
		+ "          WHEN Date(n3.ext_date1) < :endDate THEN 1 "
		+ "          WHEN Date(n3.bond_validity_date) < :endDate THEN 1 " + "          ELSE 0 " + "      END) = 1 "
		+ "  AND Date(n3.in_bonding_date) BETWEEN :aprilDate AND :lastDate " + "  AND n3.status != 'D' "
		+ "UNION ALL "
		+ "SELECT COALESCE(COUNT(DISTINCT CASE WHEN ex.balance_bonded_packages = 0 THEN ex.noc_no END), 0) AS count, "
		+ "       COALESCE(SUM(s.cifValueSum), 0) AS cifValueSum, "
		+ "       COALESCE(SUM(s.cargoDutySum), 0) AS cargoDutySum " + "FROM SumValues s "
		+ "JOIN CfExBondCrg ex ON s.noc_no = ex.noc_no " + "WHERE ex.company_id = :companyId "
		+ "  AND ex.branch_id = :branchId " + "  AND ex.balance_bonded_packages = 0 AND Date(ex.ex_bonding_date) BETWEEN :startDate AND :endDate " + "UNION ALL "
		+ "SELECT COALESCE(COUNT(DISTINCT CASE WHEN ex.balance_bonded_packages = 0 THEN ex.noc_no END), 0) AS count, "
		+ "       COALESCE(SUM(s.cifValueSum), 0) AS cifValueSum, "
		+ "       COALESCE(SUM(s.cargoDutySum), 0) AS cargoDutySum " + "FROM SumValues s "
		+ "JOIN CfExBondCrg ex ON s.noc_no = ex.noc_no " + "WHERE ex.company_id = :companyId "
		+ "  AND ex.branch_id = :branchId " + "  AND ex.balance_bonded_packages = 0 AND Date(ex.ex_bonding_date) BETWEEN :aprilDate AND :lastDate", nativeQuery = true)
List<Object[]> getLiveReceiptsAndDisposal1(@Param("companyId") String companyId, @Param("branchId") String branchId,
		@Param("startDate") Date startDate, @Param("endDate") Date endDate,@Param("aprilDate") Date aprilDate, @Param("lastDate") Date lastDate);




@Query(value = "SELECT COUNT(*) AS count, COALESCE(SUM(CIF_Value), 0) AS cifValueSum, COALESCE(SUM(Cargo_Duty), 0) AS cargoDutySum FROM (SELECT n1.in_bonding_id, MAX(n1.CIF_Value) AS CIF_Value, MAX(n1.Cargo_Duty) AS Cargo_Duty FROM cfinbondcrg n1 LEFT JOIN CfExBondCrg e ON n1.company_id = e.company_id AND n1.branch_id = e.branch_id AND n1.noc_no = e.noc_no AND n1.noc_trans_id = e.noc_trans_id AND n1.boe_no = e.boe_no AND n1.in_bonding_id = e.in_bonding_id WHERE n1.company_id = :cid AND n1.branch_id = :bid AND (CASE WHEN Date(n1.ext_date3) < :endDate THEN 1 WHEN Date(n1.ext_date2) < :endDate THEN 1 WHEN Date(n1.ext_date1) < :endDate THEN 1 WHEN Date(n1.bond_validity_date) < :endDate THEN 1 ELSE 0 END) = 1 AND (n1.in_bonded_packages - n1.ex_bonded_packages) > 0 AND Date(n1.in_bonding_date) between :start AND :end GROUP BY n1.in_bonding_id "
		+ "UNION "
		+ "SELECT n1.in_bonding_id, MAX(n1.CIF_Value) AS CIF_Value, MAX(n1.Cargo_Duty) AS Cargo_Duty FROM cfinbondcrg n1 LEFT JOIN CfExBondCrg e ON n1.company_id = e.company_id AND n1.branch_id = e.branch_id AND n1.noc_no = e.noc_no AND n1.noc_trans_id = e.noc_trans_id AND n1.boe_no = e.boe_no AND n1.in_bonding_id = e.in_bonding_id WHERE n1.company_id = :cid AND n1.branch_id = :bid AND (CASE WHEN Date(n1.ext_date3) < :endDate THEN 1 WHEN Date(n1.ext_date2) < :endDate THEN 1 WHEN Date(n1.ext_date1) < :endDate THEN 1 WHEN Date(n1.bond_validity_date) < :endDate THEN 1 ELSE 0 END) = 1 AND Date(n1.in_bonding_date) between :start AND :end AND e.balance_bonded_packages > 0 AND Date(e.ex_bonding_date) between :start AND :end AND (e.noc_no, e.boe_no, e.noc_trans_id) NOT IN (SELECT n.noc_no, n.boe_no, n.noc_trans_id FROM CfExBondCrg n WHERE n.company_id = :cid AND n.branch_id = :bid AND Date(n.ex_bonding_date) between :start AND :end AND n.balance_bonded_packages = 0) GROUP BY n1.in_bonding_id) AS combined "
		+ "UNION ALL "
		+ "SELECT COUNT(*) AS count, COALESCE(SUM(CIF_Value), 0) AS cifValueSum, COALESCE(SUM(Cargo_Duty), 0) AS cargoDutySum FROM (SELECT n1.in_bonding_id, MAX(n1.CIF_Value) AS CIF_Value, MAX(n1.Cargo_Duty) AS Cargo_Duty FROM cfinbondcrg n1 LEFT JOIN CfExBondCrg e ON n1.company_id = e.company_id AND n1.branch_id = e.branch_id AND n1.noc_no = e.noc_no AND n1.noc_trans_id = e.noc_trans_id AND n1.boe_no = e.boe_no AND n1.in_bonding_id = e.in_bonding_id WHERE n1.company_id = :cid AND n1.branch_id = :bid AND (CASE WHEN Date(n1.ext_date3) < :endDate THEN 1 WHEN Date(n1.ext_date2) < :endDate THEN 1 WHEN Date(n1.ext_date1) < :endDate THEN 1 WHEN Date(n1.bond_validity_date) < :endDate THEN 1 ELSE 0 END) = 1 AND (n1.in_bonded_packages - n1.ex_bonded_packages) > 0 AND Date(n1.in_bonding_date) between :start13 AND :end13 GROUP BY n1.in_bonding_id "
		+ "UNION "
		+ "SELECT n1.in_bonding_id, MAX(n1.CIF_Value) AS CIF_Value, MAX(n1.Cargo_Duty) AS Cargo_Duty FROM cfinbondcrg n1 LEFT JOIN CfExBondCrg e ON n1.company_id = e.company_id AND n1.branch_id = e.branch_id AND n1.noc_no = e.noc_no AND n1.noc_trans_id = e.noc_trans_id AND n1.boe_no = e.boe_no AND n1.in_bonding_id = e.in_bonding_id WHERE n1.company_id = :cid AND n1.branch_id = :bid AND (CASE WHEN Date(n1.ext_date3) < :endDate THEN 1 WHEN Date(n1.ext_date2) < :endDate THEN 1 WHEN Date(n1.ext_date1) < :endDate THEN 1 WHEN Date(n1.bond_validity_date) < :endDate THEN 1 ELSE 0 END) = 1 AND Date(n1.in_bonding_date) between :start13 AND :end13 AND e.balance_bonded_packages > 0 AND Date(e.ex_bonding_date) between :start13 AND :end13 AND (e.noc_no, e.boe_no, e.noc_trans_id) NOT IN (SELECT n.noc_no, n.boe_no, n.noc_trans_id FROM CfExBondCrg n WHERE n.company_id = :cid AND n.branch_id = :bid AND Date(n.ex_bonding_date) between :start13 AND :end13 AND n.balance_bonded_packages = 0) GROUP BY n1.in_bonding_id) AS combined "
		+ "UNION ALL "
		+ "SELECT COUNT(*) AS count, COALESCE(SUM(CIF_Value), 0) AS cifValueSum, COALESCE(SUM(Cargo_Duty), 0) AS cargoDutySum FROM (SELECT n1.in_bonding_id, MAX(n1.CIF_Value) AS CIF_Value, MAX(n1.Cargo_Duty) AS Cargo_Duty FROM cfinbondcrg n1 LEFT JOIN CfExBondCrg e ON n1.company_id = e.company_id AND n1.branch_id = e.branch_id AND n1.noc_no = e.noc_no AND n1.noc_trans_id = e.noc_trans_id AND n1.boe_no = e.boe_no AND n1.in_bonding_id = e.in_bonding_id WHERE n1.company_id = :cid AND n1.branch_id = :bid AND (CASE WHEN Date(n1.ext_date3) < :endDate THEN 1 WHEN Date(n1.ext_date2) < :endDate THEN 1 WHEN Date(n1.ext_date1) < :endDate THEN 1 WHEN Date(n1.bond_validity_date) < :endDate THEN 1 ELSE 0 END) = 1 AND (n1.in_bonded_packages - n1.ex_bonded_packages) > 0 AND Date(n1.in_bonding_date) between :start36 AND :end36 GROUP BY n1.in_bonding_id "
		+ "UNION "
		+ "SELECT n1.in_bonding_id, MAX(n1.CIF_Value) AS CIF_Value, MAX(n1.Cargo_Duty) AS Cargo_Duty FROM cfinbondcrg n1 LEFT JOIN CfExBondCrg e ON n1.company_id = e.company_id AND n1.branch_id = e.branch_id AND n1.noc_no = e.noc_no AND n1.noc_trans_id = e.noc_trans_id AND n1.boe_no = e.boe_no AND n1.in_bonding_id = e.in_bonding_id WHERE n1.company_id = :cid AND n1.branch_id = :bid AND (CASE WHEN Date(n1.ext_date3) < :endDate THEN 1 WHEN Date(n1.ext_date2) < :endDate THEN 1 WHEN Date(n1.ext_date1) < :endDate THEN 1 WHEN Date(n1.bond_validity_date) < :endDate THEN 1 ELSE 0 END) = 1 AND Date(n1.in_bonding_date) between :start36 AND :end36 AND e.balance_bonded_packages > 0 AND Date(e.ex_bonding_date) between :start36 AND :end36 AND (e.noc_no, e.boe_no, e.noc_trans_id) NOT IN (SELECT n.noc_no, n.boe_no, n.noc_trans_id FROM CfExBondCrg n WHERE n.company_id = :cid AND n.branch_id = :bid AND Date(n.ex_bonding_date) between :start36 AND :end36 AND n.balance_bonded_packages = 0) GROUP BY n1.in_bonding_id) AS combined "
		+ "UNION ALL "
		+ "SELECT COUNT(*) AS count, COALESCE(SUM(CIF_Value), 0) AS cifValueSum, COALESCE(SUM(Cargo_Duty), 0) AS cargoDutySum FROM (SELECT n1.in_bonding_id, MAX(n1.CIF_Value) AS CIF_Value, MAX(n1.Cargo_Duty) AS Cargo_Duty FROM cfinbondcrg n1 LEFT JOIN CfExBondCrg e ON n1.company_id = e.company_id AND n1.branch_id = e.branch_id AND n1.noc_no = e.noc_no AND n1.noc_trans_id = e.noc_trans_id AND n1.boe_no = e.boe_no AND n1.in_bonding_id = e.in_bonding_id WHERE n1.company_id = :cid AND n1.branch_id = :bid AND (CASE WHEN Date(n1.ext_date3) < :endDate THEN 1 WHEN Date(n1.ext_date2) < :endDate THEN 1 WHEN Date(n1.ext_date1) < :endDate THEN 1 WHEN Date(n1.bond_validity_date) < :endDate THEN 1 ELSE 0 END) = 1 AND (n1.in_bonded_packages - n1.ex_bonded_packages) > 0 AND Date(n1.in_bonding_date) between :start612 AND :end612 GROUP BY n1.in_bonding_id "
		+ "UNION "
		+ "SELECT n1.in_bonding_id, MAX(n1.CIF_Value) AS CIF_Value, MAX(n1.Cargo_Duty) AS Cargo_Duty FROM cfinbondcrg n1 LEFT JOIN CfExBondCrg e ON n1.company_id = e.company_id AND n1.branch_id = e.branch_id AND n1.noc_no = e.noc_no AND n1.noc_trans_id = e.noc_trans_id AND n1.boe_no = e.boe_no AND n1.in_bonding_id = e.in_bonding_id WHERE n1.company_id = :cid AND n1.branch_id = :bid AND (CASE WHEN Date(n1.ext_date3) < :endDate THEN 1 WHEN Date(n1.ext_date2) < :endDate THEN 1 WHEN Date(n1.ext_date1) < :endDate THEN 1 WHEN Date(n1.bond_validity_date) < :endDate THEN 1 ELSE 0 END) = 1 AND Date(n1.in_bonding_date) between :start612 AND :end612 AND e.balance_bonded_packages > 0 AND Date(e.ex_bonding_date) between :start612 AND :end612 AND (e.noc_no, e.boe_no, e.noc_trans_id) NOT IN (SELECT n.noc_no, n.boe_no, n.noc_trans_id FROM CfExBondCrg n WHERE n.company_id = :cid AND n.branch_id = :bid AND Date(n.ex_bonding_date) between :start612 AND :end612 AND n.balance_bonded_packages = 0) GROUP BY n1.in_bonding_id) AS combined "
		+ "UNION ALL "
		+ "SELECT COUNT(*) AS count, COALESCE(SUM(CIF_Value), 0) AS cifValueSum, COALESCE(SUM(Cargo_Duty), 0) AS cargoDutySum FROM (SELECT n1.in_bonding_id, MAX(n1.CIF_Value) AS CIF_Value, MAX(n1.Cargo_Duty) AS Cargo_Duty FROM cfinbondcrg n1 LEFT JOIN CfExBondCrg e ON n1.company_id = e.company_id AND n1.branch_id = e.branch_id AND n1.noc_no = e.noc_no AND n1.noc_trans_id = e.noc_trans_id AND n1.boe_no = e.boe_no AND n1.in_bonding_id = e.in_bonding_id WHERE n1.company_id = :cid AND n1.branch_id = :bid AND (CASE WHEN Date(n1.ext_date3) < :endDate THEN 1 WHEN Date(n1.ext_date2) < :endDate THEN 1 WHEN Date(n1.ext_date1) < :endDate THEN 1 WHEN Date(n1.bond_validity_date) < :endDate THEN 1 ELSE 0 END) = 1 AND (n1.in_bonded_packages - n1.ex_bonded_packages) > 0 AND Date(n1.in_bonding_date) between :start1y AND :end1y GROUP BY n1.in_bonding_id "
		+ "UNION "
		+ "SELECT n1.in_bonding_id, MAX(n1.CIF_Value) AS CIF_Value, MAX(n1.Cargo_Duty) AS Cargo_Duty FROM cfinbondcrg n1 LEFT JOIN CfExBondCrg e ON n1.company_id = e.company_id AND n1.branch_id = e.branch_id AND n1.noc_no = e.noc_no AND n1.noc_trans_id = e.noc_trans_id AND n1.boe_no = e.boe_no AND n1.in_bonding_id = e.in_bonding_id WHERE n1.company_id = :cid AND n1.branch_id = :bid AND (CASE WHEN Date(n1.ext_date3) < :endDate THEN 1 WHEN Date(n1.ext_date2) < :endDate THEN 1 WHEN Date(n1.ext_date1) < :endDate THEN 1 WHEN Date(n1.bond_validity_date) < :endDate THEN 1 ELSE 0 END) = 1 AND Date(n1.in_bonding_date) between :start1y AND :end1y AND e.balance_bonded_packages > 0 AND Date(e.ex_bonding_date) between :start1y AND :end1y AND (e.noc_no, e.boe_no, e.noc_trans_id) NOT IN (SELECT n.noc_no, n.boe_no, n.noc_trans_id FROM CfExBondCrg n WHERE n.company_id = :cid AND n.branch_id = :bid AND Date(n.ex_bonding_date) between :start1y AND :end1y AND n.balance_bonded_packages = 0) GROUP BY n1.in_bonding_id) AS combined "
		+ "UNION ALL "
		+ "SELECT COUNT(*) AS count, COALESCE(SUM(CIF_Value), 0) AS cifValueSum, COALESCE(SUM(Cargo_Duty), 0) AS cargoDutySum FROM (SELECT n1.in_bonding_id, MAX(n1.CIF_Value) AS CIF_Value, MAX(n1.Cargo_Duty) AS Cargo_Duty FROM cfinbondcrg n1 LEFT JOIN CfExBondCrg e ON n1.company_id = e.company_id AND n1.branch_id = e.branch_id AND n1.noc_no = e.noc_no AND n1.noc_trans_id = e.noc_trans_id AND n1.boe_no = e.boe_no AND n1.in_bonding_id = e.in_bonding_id WHERE n1.company_id = :cid AND n1.branch_id = :bid AND (CASE WHEN Date(n1.ext_date3) < :endDate THEN 1 WHEN Date(n1.ext_date2) < :endDate THEN 1 WHEN Date(n1.ext_date1) < :endDate THEN 1 WHEN Date(n1.bond_validity_date) < :endDate THEN 1 ELSE 0 END) = 1 AND (n1.in_bonded_packages - n1.ex_bonded_packages) > 0 AND Date(n1.in_bonding_date) between :start2y AND :end2y GROUP BY n1.in_bonding_id "
		+ "UNION "
		+ "SELECT n1.in_bonding_id, MAX(n1.CIF_Value) AS CIF_Value, MAX(n1.Cargo_Duty) AS Cargo_Duty FROM cfinbondcrg n1 LEFT JOIN CfExBondCrg e ON n1.company_id = e.company_id AND n1.branch_id = e.branch_id AND n1.noc_no = e.noc_no AND n1.noc_trans_id = e.noc_trans_id AND n1.boe_no = e.boe_no AND n1.in_bonding_id = e.in_bonding_id WHERE n1.company_id = :cid AND n1.branch_id = :bid AND (CASE WHEN Date(n1.ext_date3) < :endDate THEN 1 WHEN Date(n1.ext_date2) < :endDate THEN 1 WHEN Date(n1.ext_date1) < :endDate THEN 1 WHEN Date(n1.bond_validity_date) < :endDate THEN 1 ELSE 0 END) = 1 AND Date(n1.in_bonding_date) between :start2y AND :end2y AND e.balance_bonded_packages > 0 AND Date(e.ex_bonding_date) between :start2y AND :end2y AND (e.noc_no, e.boe_no, e.noc_trans_id) NOT IN (SELECT n.noc_no, n.boe_no, n.noc_trans_id FROM CfExBondCrg n WHERE n.company_id = :cid AND n.branch_id = :bid AND Date(n.ex_bonding_date) between :start2y AND :end2y AND n.balance_bonded_packages = 0) GROUP BY n1.in_bonding_id) AS combined "
		+ "UNION ALL "
		+ "SELECT COUNT(*) AS count, COALESCE(SUM(CIF_Value), 0) AS cifValueSum, COALESCE(SUM(Cargo_Duty), 0) AS cargoDutySum FROM (SELECT n1.in_bonding_id, MAX(n1.CIF_Value) AS CIF_Value, MAX(n1.Cargo_Duty) AS Cargo_Duty FROM cfinbondcrg n1 LEFT JOIN CfExBondCrg e ON n1.company_id = e.company_id AND n1.branch_id = e.branch_id AND n1.noc_no = e.noc_no AND n1.noc_trans_id = e.noc_trans_id AND n1.boe_no = e.boe_no AND n1.in_bonding_id = e.in_bonding_id WHERE n1.company_id = :cid AND n1.branch_id = :bid AND (CASE WHEN Date(n1.ext_date3) < :endDate THEN 1 WHEN Date(n1.ext_date2) < :endDate THEN 1 WHEN Date(n1.ext_date1) < :endDate THEN 1 WHEN Date(n1.bond_validity_date) < :endDate THEN 1 ELSE 0 END) = 1 AND (n1.in_bonded_packages - n1.ex_bonded_packages) > 0 AND Date(n1.in_bonding_date) < :start3y GROUP BY n1.in_bonding_id "
		+ "UNION "
		+ "SELECT n1.in_bonding_id, MAX(n1.CIF_Value) AS CIF_Value, MAX(n1.Cargo_Duty) AS Cargo_Duty FROM cfinbondcrg n1 LEFT JOIN CfExBondCrg e ON n1.company_id = e.company_id AND n1.branch_id = e.branch_id AND n1.noc_no = e.noc_no AND n1.noc_trans_id = e.noc_trans_id AND n1.boe_no = e.boe_no AND n1.in_bonding_id = e.in_bonding_id WHERE n1.company_id = :cid AND n1.branch_id = :bid AND (CASE WHEN Date(n1.ext_date3) < :endDate THEN 1 WHEN Date(n1.ext_date2) < :endDate THEN 1 WHEN Date(n1.ext_date1) < :endDate THEN 1 WHEN Date(n1.bond_validity_date) < :endDate THEN 1 ELSE 0 END) = 1 AND Date(n1.in_bonding_date) < :start3y AND e.balance_bonded_packages > 0 AND Date(e.ex_bonding_date)< :start3y AND (e.noc_no, e.boe_no, e.noc_trans_id) NOT IN (SELECT n.noc_no, n.boe_no, n.noc_trans_id FROM CfExBondCrg n WHERE n.company_id = :cid AND n.branch_id = :bid AND Date(n.ex_bonding_date) < :start3y AND n.balance_bonded_packages = 0) GROUP BY n1.in_bonding_id) AS combined ", nativeQuery = true)
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
}
