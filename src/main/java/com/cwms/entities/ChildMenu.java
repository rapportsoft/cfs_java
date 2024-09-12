package com.cwms.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "CMenu")
@IdClass(ChildMenuId.class)
public class ChildMenu {
	@Id
	@Column(length = 6,nullable = false)
	private String Company_Id;
	@Id
	@Column(length = 6,nullable = false)
	private String Branch_Id;
	@Id
	@Column(length = 5)
	private String Child_Menu_Id;
	
	@Column(name = "Process_id",length = 6)
	private String processId;
	@Column(length = 100,nullable = false)
	private String Child_Menu_Name;
    @Column(length = 30)
	private String cicons;
	@Column(length = 50)
    private String child_page_links;
	@Column(length = 6)
	private String pprocess_Id;

	@Column(length = 1)
	private String tabStatus = "N";
	

	public ChildMenu() {
		super();
		// TODO Auto-generated constructor stub
	}



	public String getCompany_Id() {
		return Company_Id;
	}



	public void setCompany_Id(String company_Id) {
		Company_Id = company_Id;
	}



	public String getBranch_Id() {
		return Branch_Id;
	}



	public void setBranch_Id(String branch_Id) {
		Branch_Id = branch_Id;
	}



	public String getTabStatus() {
		return tabStatus;
	}



	public void setTabStatus(String tabStatus) {
		this.tabStatus = tabStatus;
	}



	public ChildMenu(String child_Menu_Id, String processId, String child_Menu_Name, String cicons,
			String child_page_links, String pprocess_Id) {
		super();
		Child_Menu_Id = child_Menu_Id;
		this.processId = processId;
		Child_Menu_Name = child_Menu_Name;
		this.cicons = cicons;
		this.child_page_links = child_page_links;
		this.pprocess_Id = pprocess_Id;
		
	}



	public String getChild_Menu_Id() {
		return Child_Menu_Id;
	}



	public void setChild_Menu_Id(String child_Menu_Id) {
		Child_Menu_Id = child_Menu_Id;
	}



	public String getProcessId() {
		return processId;
	}



	public void setProcessId(String processId) {
		this.processId = processId;
	}



	public String getChild_Menu_Name() {
		return Child_Menu_Name;
	}



	public void setChild_Menu_Name(String child_Menu_Name) {
		Child_Menu_Name = child_Menu_Name;
	}



	public String getCicons() {
		return cicons;
	}



	public void setCicons(String cicons) {
		this.cicons = cicons;
	}



	public String getChild_page_links() {
		return child_page_links;
	}



	public void setChild_page_links(String child_page_links) {
		this.child_page_links = child_page_links;
	}



	public String getPprocess_Id() {
		return pprocess_Id;
	}



	public void setPprocess_Id(String pprocess_Id) {
		this.pprocess_Id = pprocess_Id;
	}








	public ChildMenu(String company_Id, String branch_Id, String child_Menu_Id, String processId,
			String child_Menu_Name) {
		super();
		Company_Id = company_Id;
		Branch_Id = branch_Id;
		Child_Menu_Id = child_Menu_Id;
		this.processId = processId;
		Child_Menu_Name = child_Menu_Name;
	}

	
	
	
}
