package com.cwms.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.Party;
import com.cwms.entities.Port;

public interface PartyRepository extends JpaRepository<Party, String> {
	
	
	@Query(value = "SELECT p.party_id, p.party_name " +
            "FROM party p " +
            "WHERE p.company_id = :cid " +
            "AND p.branch_id = :bid " +
            "AND p.status != 'D' " +
            "AND (" +
            "   (:type = 'partyId') " +
            "   OR (:type = 'cha' AND p.cha = 'Y') " +
            "   OR (:type = 'impExp' AND (p.exp = 'Y' OR p.imp = 'Y')) " +
            "   OR (:type = 'forwarderId' AND p.frw = 'Y') " +
            "   OR (:type = 'shippingAgent' AND p.agt = 'Y') " +
            "   OR (:type = 'shippingLine' AND p.lin = 'Y') " +
            ") " +
            "AND (:val IS NULL OR :val = '' OR p.party_name LIKE CONCAT(:val, '%'))", 
            nativeQuery = true)
List<Object[]> getPartyByTypeValueTariff(
    @Param("cid") String cid,
    @Param("bid") String bid,
    @Param("val") String val,
    @Param("type") String type
);
	
	
	@Query(value = "SELECT p.party_id, p.party_name " +
            "FROM party p " +
            "WHERE p.company_id = :cid " +
            "AND p.branch_id = :bid " +
            "AND p.status != 'D' " +
            "AND (" +
            "   (:type = 'cha' AND p.cha = 'Y') " +
            "   OR (:type = 'on' AND p.agt = 'Y') " +
            "   OR (:type = 'exp' AND p.exp = 'Y') " +
            "   OR (:type = 'sa' AND p.agt = 'Y') " +
            "   OR (:type = 'sl' AND p.lin = 'Y') " +
            ") " +
            "AND (:val IS NULL OR :val = '' OR p.party_name LIKE CONCAT(:val, '%'))", 
            nativeQuery = true)
List<Object[]> getPartyByTypeValue(
    @Param("cid") String cid,
    @Param("bid") String bid,
    @Param("val") String val,
    @Param("type") String type
);

	
	
	@Query(value = "select p from Party p where p.companyId=:cid and p.branchId=:bid and p.customerCode=:pid and p.status != 'D' and p.lin='Y'")
	Party getDataByCustomerCode1(@Param("cid") String cid, @Param("bid") String bid, @Param("pid") String pid);
	
	@Query(value = "select p from Party p where p.companyId=:cid and p.branchId=:bid and p.customerCode=:pid and p.status != 'D' and p.agt='Y'")
	Party getDataByCustomerCode2(@Param("cid") String cid, @Param("bid") String bid, @Param("pid") String pid);

	@Query(value = "select COUNT(p)>0 from Party p where p.companyId=:cid and p.branchId=:bid and p.panNo=:pan and p.status !='D'")
	Boolean isExistPanNo(@Param("cid") String cid, @Param("bid") String bid, @Param("pan") String pan);
	
	 @Query("SELECT NEW com.cwms.entities.Port(i.portCode, i.portName) " +
		        "FROM Port i " +
		        "WHERE " +		       
		        "i.companyId = :companyId " +
		        "AND i.branchId = :branchId AND i.jobOrderFormat != '' " +			        		       
		        "ORDER BY i.portName DESC")
		List<Port> getPortsForGateIn(
		    @Param("companyId") String companyId,
		    @Param("branchId") String branchId  
		   );
	
	@Query(value="select p.party_Id,p.party_Name from Party p  "
			+ "where p.company_Id=:cid and p.branch_Id=:bid and p.status != 'D' "
			+ "and p.exp = 'Y' and (:val is null OR :val = '' OR p.party_Name like CONCAT (:val,'%'))",nativeQuery = true)
	List<Object[]> getDataForExp(@Param("cid") String cid, @Param("bid") String bid, @Param("val") String val);

	@Query(value = "select COUNT(p)>0 from Party p where p.companyId=:cid and p.branchId=:bid and p.panNo=:pan and p.partyId != :party and p.status !='D'")
	Boolean isExistPanNo1(@Param("cid") String cid, @Param("bid") String bid, @Param("pan") String pan,
			@Param("party") String party);

	@Query(value = "select COUNT(p)>0 from Party p where p.companyId=:cid and p.branchId=:bid and p.phoneNo=:pan and p.status !='D'")
	Boolean isExistPhoneNo(@Param("cid") String cid, @Param("bid") String bid, @Param("pan") String pan);

	@Query(value = "select COUNT(p)>0 from Party p where p.companyId=:cid and p.branchId=:bid and p.phoneNo=:pan and p.partyId != :party and p.status !='D'")
	Boolean isExistPhoneNo1(@Param("cid") String cid, @Param("bid") String bid, @Param("pan") String pan,
			@Param("party") String party);

	@Query(value = "select p.party_id, p.party_name, " + "GROUP_CONCAT(a.gst_no SEPARATOR ', '), "
			+ "p.iec_code, p.customer_code, p.status "
			+ "from Party p LEFT JOIN PartyAddress a ON p.company_id=a.company_id and p.branch_id=a.branch_id and p.party_id=a.party_id "
			+ "where p.company_id=:cid and p.branch_id=:bid and p.status != 'D' "
			+ "and (:name is null OR :name = '' OR p.party_name LIKE CONCAT('%', :name ,'%')) "
			+ "GROUP BY p.party_id, p.party_name, p.iec_code, p.customer_code, p.status ORDER BY p.party_id desc ", nativeQuery = true)
	public List<Object[]> search(@Param("cid") String cid, @Param("bid") String bid, @Param("name") String name);

	@Query(value = "select p from Party p where p.companyId=:cid and p.branchId=:bid and p.partyId=:pid and p.status != 'D'")
	Party getDataById(@Param("cid") String cid, @Param("bid") String bid, @Param("pid") String pid);
	
	@Query(value = "select p from Party p where p.companyId=:cid and p.branchId=:bid and p.customerCode=:pid and p.status != 'D'")
	Party getDataByCustomerCode(@Param("cid") String cid, @Param("bid") String bid, @Param("pid") String pid);

	@Query(value = "select p.partyId,p.partyName from Party p where p.companyId=:cid and p.branchId=:bid and p.status != 'D'")
	List<Object[]> getAll(@Param("cid") String cid, @Param("bid") String bid);

	@Query(value = "select p.partyId,p.partyName from Party p where p.companyId=:cid and p.branchId=:bid and p.lin = 'Y' and p.status != 'D'")
	List<Object[]> getLiner(@Param("cid") String cid, @Param("bid") String bid);

	@Query(value = "select p.partyId,p.partyName,p.customerCode from Party p where p.companyId=:cid and p.branchId=:bid and p.lin = 'Y' "
			+ "and p.status != 'D' and (:val is null OR :val = '' OR p.partyName LIKE CONCAT (:val,'%'))")
	List<Object[]> getLiner1(@Param("cid") String cid, @Param("bid") String bid, @Param("val") String val);
	
	@Query(value = "select p.partyId,p.partyName,p.customerCode from Party p where p.companyId=:cid and p.branchId=:bid and p.lin = 'Y' "
			+ "and p.status != 'D' and (:val is null OR :val = '' OR p.customerCode =:val)")
	Object[] getLiner2(@Param("cid") String cid, @Param("bid") String bid, @Param("val") String val);

	@Query(value = "select p.partyId,p.partyName from Party p where p.companyId=:cid and p.branchId=:bid and p.agt = 'Y' and p.status != 'D'")
	List<Object[]> getAgent(@Param("cid") String cid, @Param("bid") String bid);

	@Query(value = "select p.partyId,p.partyName,p.customerCode from Party p where p.companyId=:cid and p.branchId=:bid and p.agt = 'Y' "
			+ "and p.status != 'D' and (:val is null OR :val = '' OR p.partyName LIKE CONCAT (:val,'%'))")
	List<Object[]> getAgent1(@Param("cid") String cid, @Param("bid") String bid, @Param("val") String val);
	
	@Query(value = "select p.partyId,p.partyName,p.customerCode from Party p where p.companyId=:cid and p.branchId=:bid and p.agt = 'Y' "
			+ "and p.status != 'D' and (:val is null OR :val = '' OR p.customerCode =:val)")
	Object[] getAgent2(@Param("cid") String cid, @Param("bid") String bid, @Param("val") String val);
	
	@Query(value = "select p.partyId,p.partyName,p.customerCode from Party p where p.companyId=:cid and p.branchId=:bid and p.imp = 'Y' "
			+ "and p.status != 'D' and (:val is null OR :val = '' OR p.partyName LIKE CONCAT (:val,'%'))")
	List<Object[]> getImp(@Param("cid") String cid, @Param("bid") String bid, @Param("val") String val);
	
	
	
	
	@Query(value = "select p.partyId,p.partyName from Party p where p.companyId=:cid and p.branchId=:bid and p.trns = 'Y' and p.status != 'D' ")
	List<Object[]> getTransporter(@Param("cid") String cid, @Param("bid") String bid);
	
	@Query(value = "select p.partyId,p.partyName,p.customerCode from Party p where p.companyId=:cid and p.branchId=:bid and p.cha = 'Y' "
			+ "and p.status != 'D' and (:val is null OR :val = '' OR p.partyName LIKE CONCAT (:val,'%'))")
	List<Object[]> getCHA(@Param("cid") String cid, @Param("bid") String bid, @Param("val") String val);
	
	@Query(value = "select p.partyId,p.partyName,p.customerCode from Party p where p.companyId=:cid and p.branchId=:bid and p.cha = 'Y' "
			+ "and p.status != 'D' and ((:val is null OR :val = '' OR p.partyName LIKE CONCAT (:val,'%')) OR "
			+ "(:val is null OR :val = '' OR p.customerCode LIKE CONCAT (:val,'%'))) ")
	List<Object[]> getCHA1(@Param("cid") String cid, @Param("bid") String bid, @Param("val") String val);
	
	@Query(value = "select p.partyId,p.partyName,p.customerCode from Party p where p.companyId=:cid and p.branchId=:bid "
			+ "and p.status != 'D' and (:val is null OR :val = '' OR p.partyName LIKE CONCAT (:val,'%'))")
	List<Object[]> getAll(@Param("cid") String cid, @Param("bid") String bid, @Param("val") String val);
	
	
	@Query(value="select p.partyId,p.partyName,a.address1,a.address2,a.address3,a.srNo from Party p LEFT OUTER JOIN PartyAddress a ON p.companyId=a.companyId and "
			+ "p.branchId=a.branchId and p.partyId=a.partyId where p.companyId=:cid and p.branchId=:bid and p.status != 'D' "
			+ "and p.imp = 'Y' and (:val is null OR :val = '' OR p.partyName like CONCAT (:val,'%'))")
	List<Object[]> getDataForImp(@Param("cid") String cid, @Param("bid") String bid, @Param("val") String val);
	
	
	@Query(value="select p.partyId,p.partyName from Party p  "
			+ "where p.companyId=:cid and p.branchId=:bid and p.status != 'D' "
			+ "and p.imp = 'Y' and (:val is null OR :val = '' OR p.partyName like CONCAT (:val,'%'))")
	List<Object[]> getDataForImp1(@Param("cid") String cid, @Param("bid") String bid, @Param("val") String val);
	
	@Query(value="select p.address1,p.address2,p.address3 from PartyAddress p  "
			+ "where p.companyId=:cid and p.branchId=:bid and p.status != 'D' "
			+ "and p.partyId =:val")
	List<Object[]> getImpAddress(@Param("cid") String cid, @Param("bid") String bid, @Param("val") String val);
	
	
	@Query(value = "SELECT NEW com.cwms.entities.Party(p.partyId, p.partyName, p.customerCode) " +
            "FROM Party p " +
            "WHERE p.companyId = :companyId AND p.branchId = :branchId " +
            "AND p.partyId IN (:code1, :code2)")
List<Party> getCustomerCodes(@Param("companyId") String companyId, 
                          @Param("branchId") String branchId, 
                          @Param("code1") String code1, 
                          @Param("code2") String code2);

	
	
//@Query(value="SELECT p.party_Id, p.party_Name, a.address_1, a.address_2, a.address_3,a.sr_No,a.gst_No,a.State,p.iec_Code " +
//        "FROM Party p " +
//        "LEFT JOIN PartyAddress a ON p.company_Id = a.company_Id AND p.branch_Id = a.branch_Id AND p.party_Id = a.party_Id " +
//        "WHERE p.company_Id = :cid AND p.branch_Id = :bid AND p.status != 'D' " +
//        "AND p.EXP = 'Y' " +
//        "AND (:val IS NULL OR :val = '' OR p.party_Name LIKE CONCAT(:val, '%'))", 
//   nativeQuery = true)
//List<Object[]> getPartyByType(@Param("cid") String cid, @Param("bid") String bid, @Param("val") String val);

@Query(value="SELECT p.party_Id, p.party_Name, a.address_1, a.address_2, a.address_3,a.sr_No,a.gst_No,a.State,p.iec_Code " +
        "FROM Party p " +
        "LEFT JOIN PartyAddress a ON p.company_Id = a.company_Id AND p.branch_Id = a.branch_Id AND p.party_Id = a.party_Id " +
        "WHERE p.company_Id = :cid AND p.branch_Id = :bid AND p.status != 'D' " +
        "AND p.EXP = 'Y' " +
        "AND p.party_Name LIKE CONCAT(:val, '%')", 
   nativeQuery = true)
List<Map<String, Object>>  getPartyByType(@Param("cid") String cid, @Param("bid") String bid, @Param("val") String val);


@Query(value="select p.partyId,p.partyName,p.partyType,p.customerCode from Party p where p.companyId=:cid and p.branchId=:bid and p.status != 'D' "
		+ "and p.cha = 'Y' and (:val is null OR :val = '' OR p.partyName like CONCAT (:val,'%'))")
List<Object[]> getDataForCha(@Param("cid") String cid, @Param("bid") String bid, @Param("val") String val);


@Query(value="select p.partyId,p.partyName,p.partyType,p.customerCode from Party p where p.companyId=:cid and p.branchId=:bid and p.status != 'D' "
		+ "and p.agt = 'Y' and (:val is null OR :val = '' OR p.partyName like CONCAT (:val,'%'))")
List<Object[]> getDataForOnAccount(@Param("cid") String cid, @Param("bid") String bid, @Param("val") String val);

@Query(value = "select p.partyName from Party p where p.companyId=:cid and p.branchId=:bid and p.partyId=:pid and p.status != 'D'")
String getPartyNameById(@Param("cid") String cid, @Param("bid") String bid, @Param("pid") String pid);

@Query(value = "select p.tdsPercentage from Party p where p.companyId=:cid and p.branchId=:bid and p.partyId=:pid and p.status != 'D'")
String getTdsById(@Param("cid") String cid, @Param("bid") String bid, @Param("pid") String pid);


@Query(value = "select p.partyId,p.partyName,a.address1,a.address2,a.address3,a.srNo,a.gstNo,a.state from Party p LEFT OUTER JOIN PartyAddress a ON p.companyId=a.companyId and "
		+ "p.branchId=a.branchId and p.partyId=a.partyId  where p.companyId=:cid and p.branchId=:bid and p.frw = 'Y' "
		+ "and p.status != 'D' and (:val is null OR :val = '' OR p.partyName LIKE CONCAT (:val,'%'))")
List<Object[]> getFwdWithAdd(@Param("cid") String cid, @Param("bid") String bid, @Param("val") String val);



@Query(value = "select p.partyId,p.partyName,a.address1,a.address2,a.address3,a.srNo,a.gstNo from Party p LEFT OUTER JOIN PartyAddress a ON p.companyId=a.companyId and "
		+ "p.branchId=a.branchId and p.partyId=a.partyId where p.companyId=:cid and p.branchId=:bid and p.cha = 'Y' "
		+ "and p.status != 'D' and (:val is null OR :val = '' OR p.partyName LIKE CONCAT (:val,'%'))")
List<Object[]> getCHAWithAdd(@Param("cid") String cid, @Param("bid") String bid, @Param("val") String val);


@Query(value = "select p.partyId,p.partyName,a.address1,a.address2,a.address3,a.srNo,a.gstNo,a.state from Party p LEFT OUTER JOIN PartyAddress a ON p.companyId=a.companyId and "
		+ "p.branchId=a.branchId and p.partyId=a.partyId  where p.companyId=:cid and p.branchId=:bid "
		+ "and p.status != 'D' and (:val is null OR :val = '' OR p.partyName LIKE CONCAT (:val,'%'))")
List<Object[]> getAllWithAdd(@Param("cid") String cid, @Param("bid") String bid, @Param("val") String val);

}
