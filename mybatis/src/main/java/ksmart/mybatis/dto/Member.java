package ksmart.mybatis.dto;

import java.util.List;

public class Member {
	
	private String memberId;
	private String memberPw;
	private String memberName;
	private int memberLevel;
	private String memberLevelName;
	private String memberEmail;
	private String memberAddr;
	private String memberRegDate;
	// 판매자일 경우 판매자 상품 목록 (1:n)
	private List<Goods> goodsList;
	
	
	public List<Goods> getGoodsList() {
		return goodsList;
	}
	public void setGoodsList(List<Goods> goodsList) {
		this.goodsList = goodsList;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getMemberPw() {
		return memberPw;
	}
	public void setMemberPw(String memberPw) {
		this.memberPw = memberPw;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public int getMemberLevel() {
		return memberLevel;
	}
	public void setMemberLevel(int memberLevel) {
		this.memberLevel = memberLevel;
	}
	public String getMemberEmail() {
		return memberEmail;
	}
	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}
	public String getMemberAddr() {
		return memberAddr;
	}
	public void setMemberAddr(String memberAddr) {
		this.memberAddr = memberAddr;
	}
	public String getMemberRegDate() {
		return memberRegDate;
	}
	public void setMemberRegDate(String memberRedDate) {
		this.memberRegDate = memberRedDate;
	}
	public String getMemberLevelName() {
		return memberLevelName;
	}
	public void setMemberLevelName(String memberLevelName) {
		this.memberLevelName = memberLevelName;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Member [goodsList=");
		builder.append(goodsList);
		builder.append(", memberId=");
		builder.append(memberId);
		builder.append(", memberPw=");
		builder.append(memberPw);
		builder.append(", memberName=");
		builder.append(memberName);
		builder.append(", memberLevel=");
		builder.append(memberLevel);
		builder.append(", memberLevelName=");
		builder.append(memberLevelName);
		builder.append(", memberEmail=");
		builder.append(memberEmail);
		builder.append(", memberAddr=");
		builder.append(memberAddr);
		builder.append(", memberRegDate=");
		builder.append(memberRegDate);
		builder.append(", getGoodsList()=");
		builder.append(getGoodsList());
		builder.append(", getMemberId()=");
		builder.append(getMemberId());
		builder.append(", getMemberPw()=");
		builder.append(getMemberPw());
		builder.append(", getMemberName()=");
		builder.append(getMemberName());
		builder.append(", getMemberLevel()=");
		builder.append(getMemberLevel());
		builder.append(", getMemberEmail()=");
		builder.append(getMemberEmail());
		builder.append(", getMemberAddr()=");
		builder.append(getMemberAddr());
		builder.append(", getMemberRegDate()=");
		builder.append(getMemberRegDate());
		builder.append(", getMemberLevelName()=");
		builder.append(getMemberLevelName());
		builder.append(", getClass()=");
		builder.append(getClass());
		builder.append(", hashCode()=");
		builder.append(hashCode());
		builder.append(", toString()=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}

	
}
