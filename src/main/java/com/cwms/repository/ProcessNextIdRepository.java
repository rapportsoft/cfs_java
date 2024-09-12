package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cwms.entities.ProcessNextId;
import com.cwms.entities.ProcessNextIdPK;

import jakarta.transaction.Transactional;

@Repository
public interface ProcessNextIdRepository extends JpaRepository<ProcessNextId, ProcessNextIdPK> {

	List<ProcessNextId> findByProcessId(String processId);
	
	@Query(value = 
			"Select Next_Id from  processnextid  where Company_Id ='C00001' AND Branch_Id = 'B00001' AND  Fin_Year = '2231' AND  Process_Id = 'P05054'", nativeQuery = true)
	String findNextuncTransId();

	@Modifying
	@Transactional
	@Query(value = "UPDATE processnextid SET Next_Id = ?1 WHERE Company_Id = 'C00001' AND Branch_Id = 'B00001' AND Process_Id = 'P05054' AND fin_year='2231'", nativeQuery = true)
	void updateNextuncTransId(String nextimpTransId);
	
	
	
	
	@Query(value = 
			"Select Next_Id from  processnextid  where Company_Id ='C00001' AND Branch_Id = 'B00001' AND  Fin_Year = '2232' AND  Process_Id = 'P05055'", nativeQuery = true)
	String findNextUNCSIRNo();

	@Modifying
	@Transactional
	@Query(value = "UPDATE processnextid SET Next_Id = ?1 WHERE Company_Id = 'C00001' AND Branch_Id = 'B00001' AND Process_Id = 'P05055' AND fin_year='2232'", nativeQuery = true)
	void updateNextUNCSIRNo(String nextSIRNo);
	
	

	@Query(value = "Select Next_Id from  processnextid  where Company_Id ='C00001' AND Branch_Id = 'B00037' AND  Fin_Year = '2006' AND  Process_Id = 'P00011'", nativeQuery = true)
	String findNextId();

	@Modifying
	@Transactional
	@Query(value = "UPDATE processnextid SET Next_Id = ?1 WHERE Company_Id = 'C00001' AND Branch_Id = 'B00037' AND Process_Id = 'P00011' AND fin_year='2006'", nativeQuery = true)
	void updateNextId(String nextId);

	@Query(value = "Select Next_Id from  processnextid  where Company_Id ='C00011' AND Branch_Id = 'B00038' AND  Fin_Year = '2006' AND  Process_Id = 'P00012'", nativeQuery = true)
	String findNextIdforHoliday();

	@Modifying
	@Transactional
	@Query(value = "UPDATE processnextid SET Next_Id = ?1 WHERE Company_Id = 'C00011' AND Branch_Id = 'B00038' AND Process_Id = 'P00012' AND fin_year='2006'", nativeQuery = true)
	void updateNextIdforHoliday(String nextholi);

	@Query(value = "Select Next_Id from  processnextid  where Company_Id ='C00090' AND Branch_Id = 'B00090' AND  Fin_Year = '2007' AND  Process_Id = 'P00009'", nativeQuery = true)
	String findNextMailId();

	@Modifying
	@Transactional
	@Query(value = "UPDATE processnextid SET Next_Id = ?1 WHERE Company_Id = 'C00090' AND Branch_Id = 'B00090' AND Process_Id = 'P00009' AND fin_year='2007'", nativeQuery = true)
	void updateNextMailId(String mailId);
	
	
	
	@Query(value = "Select Next_Id from  processnextid  where Company_Id ='C00001' AND Branch_Id = 'B00001' AND  Fin_Year = '2006' AND  Process_Id = 'P00001'", nativeQuery = true)
	String findNextJarDId();

	@Modifying
	@Transactional
	@Query(value = "UPDATE processnextid SET Next_Id = ?1 WHERE Company_Id = 'C00001' AND Branch_Id = 'B00001' AND Process_Id = 'P00001' AND fin_year='2006'", nativeQuery = true)
	void updateNextJarDId(String nextJarDId);

	@Query(value = "Select Next_Id from  processnextid  where Company_Id ='C00001' AND Branch_Id = 'B00001' AND  Fin_Year = '2006' AND  Process_Id = 'P00001'", nativeQuery = true)
	String findNextJarId();

	@Modifying
	@Transactional
	@Query(value = "UPDATE processnextid SET Next_Id = ?1 WHERE Company_Id = 'C00001' AND Branch_Id = 'B00001' AND Process_Id = 'P00001' AND fin_year='2006'", nativeQuery = true)
	void updateNextJarId(String nextJarId);
	
	@Query(value = 
			"Select Next_Id from  processnextid  where Company_Id ='C00099' AND Branch_Id = 'B00099' AND  Fin_Year = '2009' AND  Process_Id = 'P00099'", nativeQuery = true)
	String findNextReId();

	@Modifying
	@Transactional
	@Query(value = "UPDATE processnextid SET Next_Id = ?1 WHERE Company_Id = 'C00099' AND Branch_Id = 'B00099' AND Process_Id = 'P00099' AND fin_year='2009'", nativeQuery = true)
	void updateNextReId(String mailId);
	
	
	@Query(value = 
			"Select Next_Id from  processnextid  where Company_Id ='C00012' AND Branch_Id = 'B00039' AND  Fin_Year = '2007' AND  Process_Id = 'P00013'", nativeQuery = true)
	String findNextServiceId();

	@Modifying
	@Transactional
	@Query(value = "UPDATE processnextid SET Next_Id = ?1 WHERE Company_Id = 'C00012' AND Branch_Id = 'B00039' AND Process_Id = 'P00013' AND fin_year='2007'", nativeQuery = true)
	void updateNextServiceId(String nextServiceId);
	
	@Query(value = 
			"Select Next_Id from  processnextid  where Company_Id ='C00013' AND Branch_Id = 'B00040' AND  Fin_Year = '2007' AND  Process_Id = 'P00014'", nativeQuery = true)
	String findNextCFSTarrifNo();

	@Modifying
	@Transactional
	@Query(value = "UPDATE processnextid SET Next_Id = ?1 WHERE Company_Id = 'C00013' AND Branch_Id = 'B00040' AND Process_Id = 'P00014' AND fin_year='2007'", nativeQuery = true)
	void updateNextCFSTarrifNo(String nextCFSTarrifNo);
	
	@Query(value = 
			"Select Next_Id from  processnextid  where Company_Id ='C00001' AND Branch_Id = 'B00001' AND  Fin_Year = '2007' AND  Process_Id = 'P00015'", nativeQuery = true)
	String findNextSIRNo();

	@Modifying
	@Transactional
	@Query(value = "UPDATE processnextid SET Next_Id = ?1 WHERE Company_Id = 'C00001' AND Branch_Id = 'B00001' AND Process_Id = 'P00015' AND fin_year='2007'", nativeQuery = true)
	void updateNextSIRNo(String nextSIRNo);
	
	@Query(value = 
			"Select Next_Id from  processnextid  where Company_Id ='C00001' AND Branch_Id = 'B00001' AND  Fin_Year = '2007' AND  Process_Id = 'P00016'", nativeQuery = true)
	String findNextimpTransId();

	@Modifying
	@Transactional
	@Query(value = "UPDATE processnextid SET Next_Id = ?1 WHERE Company_Id = 'C00001' AND Branch_Id = 'B00001' AND Process_Id = 'P00016' AND fin_year='2007'", nativeQuery = true)
	void updateNextimpTransId(String nextimpTransId);
	
	@Query(value = 
			"Select Next_Id from  processnextid  where Company_Id ='C00001' AND Branch_Id = 'B00001' AND  Fin_Year = '2111' AND Process_Id = 'P00100' ", nativeQuery = true)
	String findNextsubimpid();

	@Modifying
	@Transactional
	@Query(value = "UPDATE processnextid SET  Next_Id = :nextimpTransId WHERE Company_Id = 'C00001' AND Branch_Id = 'B00001' AND fin_year='2111' and Process_Id = 'P00100'", nativeQuery = true)
	void updateNexsubimpid(@Param("nextimpTransId")String nextimpTransId);
	
	
	@Query(value = 
			"Select Next_Id from  processnextid  where Company_Id ='C00001' AND Branch_Id = 'B00001' AND  Fin_Year = '2123' AND  Process_Id = 'P1320'", nativeQuery = true)
	String findNextSIRExportNo();

	@Modifying
	@Transactional
	@Query(value = "UPDATE processnextid SET Next_Id = ?1 WHERE Company_Id = 'C00001' AND Branch_Id = 'B00001' AND Process_Id = 'P1320' AND fin_year='2123'", nativeQuery = true)
	void updateNextSIRExportNo(String nextSIRNo);
	
	
	@Query(value = 
			"Select Next_Id from  processnextid  where Company_Id ='C00001' AND Branch_Id = 'B00001' AND  Fin_Year = '2222' AND Process_Id = 'P00200' ", nativeQuery = true)
	String findNextsubimptransid();

	@Modifying
	@Transactional
	@Query(value = "UPDATE processnextid SET  Next_Id = :nextimpTransId WHERE Company_Id = 'C00001' AND Branch_Id = 'B00001' AND fin_year='2222' and Process_Id = 'P00200'", nativeQuery = true)
	void updateNexsubimptransid(@Param("nextimpTransId")String nextimpTransId);
	
	@Query(value = 
			"Select Next_Id from  processnextid  where Company_Id ='C00001' AND Branch_Id = 'B00001' AND  Fin_Year = '2224' AND Process_Id = 'P00208' ", nativeQuery = true)
	String findNextsubexpid();

	@Modifying
	@Transactional
	@Query(value = "UPDATE processnextid SET  Next_Id = :nextimpTransId WHERE Company_Id = 'C00001' AND Branch_Id = 'B00001' AND fin_year='2224' and Process_Id = 'P00208'", nativeQuery = true)
	void updateNexsubexpid(@Param("nextimpTransId")String nextimpTransId);
	
	@Query(value = 
			"Select Next_Id from  processnextid  where Company_Id ='C00001' AND Branch_Id = 'B00001' AND  Fin_Year = '2225' AND Process_Id = 'P00209' ", nativeQuery = true)
	String findNextsubexptransid();

	@Modifying
	@Transactional
	@Query(value = "UPDATE processnextid SET  Next_Id = :nextimpTransId WHERE Company_Id = 'C00001' AND Branch_Id = 'B00001' AND fin_year='2225' and Process_Id = 'P00209'", nativeQuery = true)
	void updateNexsubexptransid(@Param("nextimpTransId")String nextimpTransId);
	
	  @Query(value = 
				"Select Next_Id from  processnextid  where Company_Id ='C00018' AND Branch_Id = 'B00018' AND  Fin_Year = '2018' AND  Process_Id = 'P00018'", nativeQuery = true)
		String findNextPctmNo();

		@Modifying
		@Transactional
		@Query(value = "UPDATE processnextid SET Next_Id = ?1 WHERE Company_Id = 'C00018' AND Branch_Id = 'B00018' AND Process_Id = 'P00018' AND fin_year='2018'", nativeQuery = true)
		void updateNextPctmNo(String PctmNo);
		

		@Query(value = 
				"Select Next_Id from  processnextid  where Company_Id ='C00019' AND Branch_Id = 'B00019' AND  Fin_Year = '2019' AND  Process_Id = 'P00019'", nativeQuery = true)
		String findNexttpNo();

		@Modifying
		@Transactional
		@Query(value = "UPDATE processnextid SET Next_Id = ?1 WHERE Company_Id = 'C00019' AND Branch_Id = 'B00019' AND Process_Id = 'P00019' AND fin_year='2019'", nativeQuery = true)
		void updateNexttpNo(String tpNo);
		
		
		@Query(value = 
				"Select Next_Id from  processnextid  where Company_Id ='C00001' AND Branch_Id = 'B00001' AND  Fin_Year = '2023' AND Process_Id = 'P01400' ", nativeQuery = true)
		String findNextexternalUserId();

		@Modifying
		@Transactional
		@Query(value = "UPDATE processnextid SET  Next_Id = :externalUserId WHERE Company_Id = 'C00001' AND Branch_Id = 'B00001' AND fin_year='2023' and Process_Id = 'P01400'", nativeQuery = true)
		void updateNextexternalUserId(@Param("externalUserId")String externalUserId);
		
		@Query(value = 
				"Select Next_Id from  processnextid  where Company_Id ='C00099' AND Branch_Id = 'B00099' AND  Fin_Year = '2090' AND  Process_Id = 'P00099'", nativeQuery = true)
		String findDetentionId();

		@Modifying
		@Transactional
		@Query(value = "UPDATE processnextid SET Next_Id = ?1 WHERE Company_Id = 'C00099' AND Branch_Id = 'B00099' AND Process_Id = 'P00099' AND fin_year='2090'", nativeQuery = true)
		void updateNextDetentionId(String detentionId);
		
		
		@Query(value = "Select Next_Id from  processnextid  where Company_Id ='C00001' AND Branch_Id = 'B00001' AND  Fin_Year = '2023' AND  Process_Id = 'P00300'", nativeQuery = true)
		String findDoNumber();

		@Modifying
		@Transactional
		@Query(value = "UPDATE processnextid SET Next_Id = ?1 WHERE Company_Id = 'C00001' AND Branch_Id = 'B00001' AND Process_Id = 'P00300' AND fin_year='2023'", nativeQuery = true)
		void updateNextDoNumber(String detentionId);
		
		  @Query(value = 
					"Select Next_Id from  processnextid  where Company_Id ='C00001' AND Branch_Id = 'B00001' AND  Fin_Year = '2023' AND  Process_Id = 'P01313'", nativeQuery = true)
			String findNextEXPPctmNo();

			@Modifying
			@Transactional
			@Query(value = "UPDATE processnextid SET Next_Id = ?1 WHERE Company_Id = 'C00001' AND Branch_Id = 'B00001' AND Process_Id = 'P01313' AND fin_year='2023'", nativeQuery = true)
			void updateNextEXPPctmNo(String PctmNo);
			

			@Query(value = 
					"Select Next_Id from  processnextid  where Company_Id ='C00001' AND Branch_Id = 'B00001' AND  Fin_Year = '2023' AND  Process_Id = 'P01414'", nativeQuery = true)
			String findNextEXPtpNo();

			@Modifying
			@Transactional
			@Query(value = "UPDATE processnextid SET Next_Id = ?1 WHERE Company_Id = 'C00001' AND Branch_Id = 'B00001' AND Process_Id = 'P01414' AND fin_year='2023'", nativeQuery = true)
			void updateNextEXPtpNo(String tpNo);
			
			@Query(value = 
					"Select Next_Id from  processnextid  where Company_Id ='C00001' AND Branch_Id = 'B00001' AND  Fin_Year = '2111' AND  Process_Id = 'P01099'", nativeQuery = true)
			String findGateInId();

			@Modifying
			@Transactional
			@Query(value = "UPDATE processnextid SET Next_Id = ?1 WHERE Company_Id = 'C00001' AND Branch_Id = 'B00001' AND Process_Id = 'P01099' AND fin_year='2111'", nativeQuery = true)
			void updateGateInId(String gateInId);
			
			
			@Query(value = 
					"Select Next_Id from  processnextid  where Company_Id ='C00001' AND Branch_Id = 'B00001' AND  Fin_Year = '2024' AND Process_Id = 'P03030' ", nativeQuery = true)
			String findNextInvoiceNumber();

			@Modifying
			@Transactional
			@Query(value = "UPDATE processnextid SET  Next_Id = :InvoiceNumber WHERE Company_Id = 'C00001' AND Branch_Id = 'B00001' AND fin_year='2024' and Process_Id = 'P03030'", nativeQuery = true)
			void updateNextextInvoiceNumber(@Param("InvoiceNumber")String InvoiceNumber);
			
			
//			Bill Number
			@Query(value = 
					"Select Next_Id from  processnextid  where Company_Id ='C00001' AND Branch_Id = 'B00001' AND  Fin_Year = '2024' AND Process_Id = 'P04040' ", nativeQuery = true)
			String findNextBillNumber();

			@Modifying
			@Transactional
			@Query(value = "UPDATE processnextid SET  Next_Id = :InvoiceNumber WHERE Company_Id = 'C00001' AND Branch_Id = 'B00001' AND fin_year='2024' and Process_Id = 'P04040'", nativeQuery = true)
			void updateNextextBillNumber(@Param("InvoiceNumber")String InvoiceNumber);
			
			@Query(value = "Select Next_Id from  processnextid  where Company_Id ='C00001' AND Branch_Id = 'B00001' AND  Fin_Year = '2226' AND  Process_Id = 'P00210'", nativeQuery = true)
			String findNextRepresentativePartyId();

			@Modifying
			@Transactional
			@Query(value = "UPDATE processnextid SET Next_Id = ?1 WHERE Company_Id = 'C00001' AND Branch_Id = 'B00001' AND Process_Id = 'P00210' AND fin_year='2226'", nativeQuery = true)
			void updateNextRepresentativePartyId(String nextId);
			
			
//			Receipt Id
	@Query(value = "Select Next_Id from  processnextid  where Company_Id ='C00001' AND Branch_Id = 'B00001' AND  Fin_Year = '2024' AND Process_Id = 'P05050' ", nativeQuery = true)
	String findNextReceiptNumber();

	@Modifying
	@Transactional
	@Query(value = "UPDATE processnextid SET  Next_Id = :InvoiceNumber WHERE Company_Id = 'C00001' AND Branch_Id = 'B00001' AND fin_year='2024' and Process_Id = 'P05050'", nativeQuery = true)
	void updateNextextReceiptNumber(@Param("InvoiceNumber") String InvoiceNumber);
	
	@Query(value = "Select Next_Id from  processnextid  where Company_Id ='C00001' AND Branch_Id = 'B00001' AND  Fin_Year = '2024' AND  Process_Id = 'P04140'", nativeQuery = true)
	String findNextProformaId();

	@Modifying
	@Transactional
	@Query(value = "UPDATE processnextid SET Next_Id = ?1 WHERE Company_Id = 'C00001' AND Branch_Id = 'B00001' AND Process_Id = 'P04140' AND fin_year='2024'", nativeQuery = true)
	void updateNextProformaId(String nextId);
			
			
	@Query(value = "Select Next_Id from  processnextid  where Company_Id ='C00001' AND Branch_Id = 'B00001' AND  Fin_Year = '2227' AND  Process_Id = 'P10211'", nativeQuery = true)
	String findNextPCGatePassId();

	@Modifying
	@Transactional
	@Query(value = "UPDATE processnextid SET Next_Id = ?1 WHERE Company_Id = 'C00001' AND Branch_Id = 'B00001' AND Process_Id = 'P10211' AND fin_year='2227'", nativeQuery = true)
	void updateNextPCGatePassId(String nextId);
	
	
	@Query(value = "Select Next_Id from  processnextid  where Company_Id ='C00001' AND Branch_Id = 'B00001' AND  Fin_Year = '2228' AND  Process_Id = 'P10212'", nativeQuery = true)
	String findNextMOPGatePassId();

	@Modifying
	@Transactional
	@Query(value = "UPDATE processnextid SET Next_Id = ?1 WHERE Company_Id = 'C00001' AND Branch_Id = 'B00001' AND Process_Id = 'P10212' AND fin_year='2228'", nativeQuery = true)
	void updateNextMOPGatePassId(String nextId);

	@Query(value = "Select Next_Id from  processnextid  where Company_Id ='C00001' AND Branch_Id = 'B00001' AND  Fin_Year = '2229' AND  Process_Id = 'P10213'", nativeQuery = true)
	String findNextCommonGatePassId();

	@Modifying
	@Transactional
	@Query(value = "UPDATE processnextid SET Next_Id = ?1 WHERE Company_Id = 'C00001' AND Branch_Id = 'B00001' AND Process_Id = 'P10213' AND fin_year='2229'", nativeQuery = true)
	void updateNextCommonGatePassId(String nextId);
	
	
	@Query(value = "Select Next_Id from  processnextid  where Company_Id ='C00001' AND Branch_Id = 'B00001' AND  Fin_Year = '2230' AND  Process_Id = 'P10214'", nativeQuery = true)
	String findNextCombineReprentativeId();

	@Modifying
	@Transactional
	@Query(value = "UPDATE processnextid SET Next_Id = ?1 WHERE Company_Id = 'C00001' AND Branch_Id = 'B00001' AND Process_Id = 'P10214' AND fin_year='2230'", nativeQuery = true)
	void updateNextCombineReprentativeId(String nextId);
	
	@Query(value = "Select Next_Id from  processnextid  where Company_Id ='C00001' AND Branch_Id = 'B00001' AND  Fin_Year = '2024' AND  Process_Id = 'P05053'", nativeQuery = true)
	String findNextPredictableInviceId();

	@Modifying
	@Transactional
	@Query(value = "UPDATE processnextid SET Next_Id = ?1 WHERE Company_Id = 'C00001' AND Branch_Id = 'B00001' AND Process_Id = 'P05053' AND fin_year='2024'", nativeQuery = true)
	void updateNextPredictableInviceId(String nextId);
	
	
	@Query(value = "Select Next_Id from  processnextid  where Company_Id =:cid AND Branch_Id = :bid AND  Fin_Year = :fin AND  Process_Id = :pid", nativeQuery = true)
	String findNextID(@Param("cid") String cid,@Param("bid") String bid,@Param("fin") String fin,@Param("pid") String pid);

	@Modifying
	@Transactional
	@Query(value = "UPDATE processnextid SET Next_Id = :nextId WHERE Company_Id = :cid AND Branch_Id = :bid AND Process_Id = :pid AND fin_year=:fin", nativeQuery = true)
	void updateNextID(@Param("cid") String cid,@Param("bid") String bid,@Param("fin") String fin,@Param("pid") String pid,@Param("nextId") String nextId);
			
	@Query(value = "SELECT Next_Id FROM processnextid WHERE Company_Id = :companyId AND Branch_Id = :branchId AND fin_Year = :finYear AND Process_Id = :processId", nativeQuery = true)
	 String findAuditTrail(@Param("companyId") String companyId, @Param("branchId") String branchId, @Param("processId") String processId, @Param("finYear") String finYear);

			
	
	@Modifying
   @Transactional
   @Query(value = "UPDATE processnextid SET Next_Id = :nextId WHERE Company_Id = :companyId AND Branch_Id = :branchId AND fin_year = :finYear AND Process_Id = :processId", nativeQuery = true)
   void updateAuditTrail(@Param("companyId") String companyId, @Param("branchId") String branchId, @Param("processId") String processId, @Param("nextId") String nextId, @Param("finYear") String finYear);

	@Query("SELECT p.nextId FROM ProcessNextId p WHERE p.companyId = :companyId " +
            "AND p.branchId = :branchId AND p.processId = :processId")
    String findNextIdByCompositeKey(String companyId, String branchId, String processId);

    @Modifying
    @Query("UPDATE ProcessNextId p SET p.nextId = :nextId " +
            "WHERE p.companyId = :companyId AND p.branchId = :branchId " +
            "AND p.processId = :processId")
    int updateNextIdByCompositeKey(String companyId, String branchId, String processId, String nextId);
    
    
    @Query(value = "Select Next_Id from  processnextid  where Company_Id =:companyId AND Branch_Id = :branchId AND  Process_Id =:processId  AND Fin_Year=:finYear ", nativeQuery = true)
	String findNextReceiptTransId(@Param("companyId") String companyId, 
		       @Param("branchId") String branchId,@Param("processId") String processId,@Param("finYear") String finYear );
	
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE processnextid SET Next_Id = :nextDoTransId WHERE Company_Id =:companyId AND Branch_Id =:branchId AND Process_Id =:processId AND Fin_Year=:finYear ", nativeQuery = true)
	void updateNetReceiptTransId(@Param ("nextDoTransId") String nextDoTransId,@Param("companyId") String companyId, 
		       @Param("branchId") String branchId,@Param("processId") String processId,@Param("finYear") String finYear );
}
