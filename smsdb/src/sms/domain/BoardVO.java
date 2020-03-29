package sms.domain;

public class BoardVO {
	private int msgnum;
	private int brdnum;
	private String content;
	
	public int getMsgnum() {
		return msgnum;
	}
	public void setMsgnum(int msgnum) {
		this.msgnum = msgnum;
	}
	public int getBrdnum() {
		return brdnum;
	}
	public void setBrdnum(int brdnum) {
		this.brdnum = brdnum;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
