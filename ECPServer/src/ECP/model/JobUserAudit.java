package ECP.model;

import java.sql.Timestamp;

public class JobUserAudit {
	private String auditId;
	private String applyUserId;
	private String opeType;
	private Timestamp opeTime;
	private String auditUserId;
	private Timestamp auditTime;
	private String auditStatus;
	private String auditResult;
	private String userInfo;
	private String content;
	private String auditReason;
	
	public JobUserAudit(){
		auditId = "";
		applyUserId = "";
		opeType = "";
		opeTime = new Timestamp(System.currentTimeMillis());
		auditUserId = "";
		auditTime = new Timestamp(System.currentTimeMillis());
		auditStatus = "未审核";
		auditResult = "待审核";
		userInfo = "{}";
		content = "";
		auditReason = "";
	}

	public String getAuditReason() {
		return auditReason;
	}

	public void setAuditReason(String auditReason) {
		this.auditReason = auditReason;
	}

	public String getAuditId() {
		return auditId;
	}

	public void setAuditId(String auditId) {
		this.auditId = auditId;
	}

	public String getApplyUserId() {
		return applyUserId;
	}

	public void setApplyUserId(String applyUserId) {
		this.applyUserId = applyUserId;
	}

	public String getOpeType() {
		return opeType;
	}

	public void setOpeType(String opeType) {
		this.opeType = opeType;
	}

	public Timestamp getOpeTime() {
		return opeTime;
	}

	public void setOpeTime(Timestamp opeTime) {
		this.opeTime = opeTime;
	}

	public String getAuditUserId() {
		return auditUserId;
	}

	public void setAuditUserId(String auditUserId) {
		this.auditUserId = auditUserId;
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

	public String getAuditResult() {
		return auditResult;
	}

	public void setAuditResult(String auditResult) {
		this.auditResult = auditResult;
	}

	public String getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(String userInfo) {
		this.userInfo = userInfo;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
