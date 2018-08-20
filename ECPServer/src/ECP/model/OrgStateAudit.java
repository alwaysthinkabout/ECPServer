package ECP.model;

import java.sql.Timestamp;

public class OrgStateAudit {
	private String auditId;
	private String orgUserId;
	private Timestamp operationTime;
	private String operationType;
	private String operationReason;
	private String auditUserId;
	private String auditReason;
	private Timestamp auditTime;
	private String auditStatus;
	private String orgStateId;
	private String orgInfoId;
	private String auditResult;
	private String orgStateInfo;
	
	public OrgStateAudit(){
		auditId = "";
		orgUserId = "";
		operationTime = new Timestamp(System.currentTimeMillis());
		operationType = "修改";
		operationReason = "";
		auditUserId = "0";
		auditReason = "";
		auditTime = new Timestamp(System.currentTimeMillis());
		auditStatus = "未审核";
		orgStateId = "0";
		orgInfoId = "0";
		auditResult = "待审核";
		orgStateInfo = "{}";
	}

	public String getAuditId() {
		return auditId;
	}

	public void setAuditId(String auditId) {
		this.auditId = auditId;
	}

	public String getOrgUserId() {
		return orgUserId;
	}

	public void setOrgUserId(String orgUserId) {
		this.orgUserId = orgUserId;
	}

	public Timestamp getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(Timestamp operationTime) {
		this.operationTime = operationTime;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getOperationReason() {
		return operationReason;
	}

	public void setOperationReason(String operationReason) {
		this.operationReason = operationReason;
	}

	public String getAuditUserId() {
		return auditUserId;
	}

	public void setAuditUserId(String auditUserId) {
		this.auditUserId = auditUserId;
	}

	public String getAuditReason() {
		return auditReason;
	}

	public void setAuditReason(String auditReason) {
		this.auditReason = auditReason;
	}

	public Timestamp getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Timestamp auditTime) {
		this.auditTime = auditTime;
	}

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getOrgStateId() {
		return orgStateId;
	}

	public void setOrgStateId(String orgStateId) {
		this.orgStateId = orgStateId;
	}

	public String getOrgInfoId() {
		return orgInfoId;
	}

	public void setOrgInfoId(String orgInfoId) {
		this.orgInfoId = orgInfoId;
	}

	public String getAuditResult() {
		return auditResult;
	}

	public void setAuditResult(String auditResult) {
		this.auditResult = auditResult;
	}

	public String getOrgStateInfo() {
		return orgStateInfo;
	}

	public void setOrgStateInfo(String orgStateInfo) {
		this.orgStateInfo = orgStateInfo;
	}
}
