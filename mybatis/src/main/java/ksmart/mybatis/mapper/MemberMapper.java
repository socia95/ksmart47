   package ksmart.mybatis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import ksmart.mybatis.dto.Member;
import ksmart.mybatis.dto.MemberLevel;


@Mapper
public interface MemberMapper {

	
		// 로그인 이력 조회
	public List<Map<String,Object>> getLoginHistoryList(Map<String, Object> paramMap);
	
		// 로그인 이력 테이블의 전체 행의 갯수
	public int getLoginCnt();
	
		// 회원 등급별 회원 목록 조회
	public List<Member> getMemberInfoByLevel(int memberLevel);
	
		// 회원 정보 삭제
	public int removeMemberById(String memberId);
		// 로그인 이력 삭제
	public int removeLoginHistoryById(String memberId);
		// 구매자별 구매이력 삭제
	public int removeOrderById(String memberId);
	
	
		// 회원 수정
	public Member getMemberInfoById(String memberId);
		// ??
	public int modifyMember(Member member);
	
		// 회원 등급 조회
	public List<MemberLevel> getMemberLevelList();
		// 회원 목록 조회
	public List<Member> getMemberList(Map<String, Object> paramMap);

		// 아이디 중복체크
	public boolean idCheck(String memberId);
		// 회원 가입
	public int addMember(Member member);
	
}
