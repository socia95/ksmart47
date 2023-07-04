package ksmart.mybatis.controller;


import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import groovy.util.logging.Log;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ksmart.mybatis.dto.Member;
import ksmart.mybatis.dto.MemberLevel;
import ksmart.mybatis.mapper.MemberMapper;
import ksmart.mybatis.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/member")
@AllArgsConstructor
public class MemberController {
	
		private final MemberService memberService;
		private final MemberMapper memberMapper;
		
		
		// 로그아웃
		@GetMapping("/logout")
		public String logout(HttpSession session) {
			
			session.invalidate();	// 세션에 담겨있는 데이터 초기화. 세션종료가 아님. invalidate = 무효화하다
			
			
			return "redirect:/member/logout";
			
		}
		
		// 로그인
		@PostMapping("/login")
		public String login(@RequestParam(value="memberId") String memberId,
							@RequestParam(value="memberPw") String memberPw,
							HttpServletRequest request,
							HttpServletResponse response,
							HttpSession session,
							RedirectAttributes reAttr) {
			Map<String, Object> validMap = memberService.isValidMember(memberId, memberPw);
			boolean isValid = (boolean) validMap.get("isValid");

//			request.getParameter("memberId");
//			request.getParameter("memberPw");
			if(isValid) {
				Member loginInfo = (Member) validMap.get("memberInfo");
				int memberLevel = loginInfo.getMemberLevel();
				String memberName = loginInfo.getMemberName();
				session.setAttribute("SID", memberId);
				session.setAttribute("SLEVEL", memberLevel);
				session.setAttribute("SNAME", memberName);
				return "redirect:/";
			}
				reAttr.addAttribute("msg", "일치하는 회원 정보가 없습니다.");
				
				return "redirect:/member/login";
			}
		@GetMapping("/login")
		public String login(Model model, @RequestParam(value = "msg", required = false) String msg) {
			
			model.addAttribute("title", "login");
			if(msg != null) model.addAttribute("msg", msg);
			
			return "/login/login";
		}
		
		
		// 로구인 기록 조회
		@SuppressWarnings("unchecked")
		@GetMapping("/loginHistory")
		public String loginHistory(@RequestParam(value="currentPage", required = false, defaultValue="1") int currentPage, Model model) {
			Map<String, Object> resultMap = memberService.getLoginHistory(currentPage);
			int lastPage = (int) resultMap.get("lastPage");
			int startPageNum = (int) resultMap.get("startPageNum");
			int endPageNum = (int) resultMap.get("endPageNum");
			List<Map<String, Object>> loginHistoryList = (List<Map<String, Object>>) resultMap.get("loginHistoryList");
			
			
			model.addAttribute("title", "로그인 이력 조회");
			model.addAttribute("currentPage", currentPage);
			model.addAttribute("lastPage", lastPage);
			model.addAttribute("loginHistoryList", loginHistoryList);
			model.addAttribute("startPageNum", startPageNum);
			model.addAttribute("endPageNum", endPageNum);
			
			return "login/loginHistory";
		}
		
		// 회원가입 화면
		// 1
		@GetMapping("/addMember")
		public String addMember(Model model) {
			
			
			List<MemberLevel> memberLevelList = memberService.getMemberLevelList();
			// 2
			model.addAttribute("title","회원가입");
			model.addAttribute("memberLevelList", memberLevelList);
			
			return "member/addMember";
		}
		
		// 회원 리스트
		@GetMapping("/memberList")
		public String getMemberList(Model model,
									@RequestParam(value="searchKey", required= false, defaultValue = "") String searchKey,
									@RequestParam(value="searchValue", required= false) String searchValue) {
			log.info("searchKey : {}", searchKey);
			log.info("searchValue : {}", searchValue);
			
			List<Member> MemberList = memberService.getMemberList(searchKey, searchValue);
			model.addAttribute("title", "회원목록");
			model.addAttribute("memberList", MemberList);			
			return "member/memberList";
		}
		
		// 아이디 중복체크
		@PostMapping("/idCheck")
		@ResponseBody
		public boolean idCheck(@RequestParam(value="memberId") String memberId) {
			
			log.info("id 중복체크:{}", memberId);
			boolean result = memberMapper.idCheck(memberId);
			log.info("id 중복체크 결과값 : {}", result);
			return result;
		}
		public String addMember(Member member) {
			
			log.info("회원가입시 입력정보 : {}", member);
			
			memberMapper.addMember(member);
			
			// response.sendRedirect("/member/memberList")
			// spring framework mvc 에서는 controller의 리턴값에 redirect: 키워드로 작성
			// redirect: 키워드를 작성할 경우 그 다음의 문자열은 html 파일 논리 경로가 아닌 주소를 의미
			return "redirect:/member/memberList";
		}
		
		// 사용자 요청시 쿼리 스트링 : ex) memberId=id001
		// @RequestParam(value="memberId") String memberId
		// @return
		@GetMapping("/modifyMember")
		public String modifyMember(@RequestParam(value="memberId") String memberId, Model model) {
	
			// 회원 상세 조회
			Member memberInfo = memberService.getMemberInfoById(memberId);
				
			// 회원 등급 목록 조회
			List<MemberLevel> memberLevelList = memberService.getMemberLevelList();
			
			model.addAttribute("title", "회원수정");
			model.addAttribute("memberInfo", memberInfo);
			model.addAttribute("memberLevelList", memberLevelList);
			
			
			return "member/modifyMember";
		}
		// 회원 수정 처리
		// @param Member(커맨드 객체) : 사용자 데이터 요청시 (controller layer) 데이터를 name="값"과 동일한 DTO의 멤버변수명과 동일하면 자동으로 객체생성 후 바인딩 받는 객체 
		// @return 회원리스트 화면으로 리디렉션요청
		
		@PostMapping("/modifyMember")
		public String modifyMember(Member member) {
//			커맨드 객체를 지원하지 않았다면 주석처리한 아래와 같은 코드를 다 일일히 써야함.
//			String memberId = request.getParameter("memberId");
//			String memberPw = request.getParameter("memberPw");
//			int memberLevel = integer.parseInt(request.getParameter("memberLevel"));
//			String memberName = request.getParameter("memberName");
//			String memberEmail = request.getParameter("memberEmail");
//			String memberAddr = request.getParameter("memberAddr");
//			String memberRegDate = request.getParameter("memberRegDate");
//			
//			Member member = new Member();
//			member.setMemberId(memberId);
//			member.setMemberPw(memberPw);
//			member.setMemberLevel(memberLevel);
//			member.setMemberName(memberName);
//			member.setMemberEmail(memberEmail);
//			member.setMemberAddr(memberAddr);
//			member.setMemberRegDate(memberRegDate);
		
			memberService.modifyMember(member);
			
			return "redirect:/member/memberList";
		}
		
		/* 회원탈퇴 폼
		 * 
		 */
		// @param memberId
		// @return 회원탈퇴 페이지
		@GetMapping("/removeMember")
		public String RemoveMember(@RequestParam(value="memberId") String memberId, @RequestParam(value="msg", required=false) String msg, Model model) {
			
			model.addAttribute("title", "회원탈퇴");
			model.addAttribute("memberId", memberId);
			
			if(msg !=null) model.addAttribute("msg", msg);
			return "member/removeMember";
		
		}
		
		
		@PostMapping("/removeMember")
		public String removeMember(@RequestParam(value="memberId") String memberId,
									@RequestParam(value="memberPw") String memberPw,
									RedirectAttributes reAttr) {
			// 회원 여부 검증
			Map<String, Object> isValidMap = memberService.isValidMember(memberId, memberPw);
			boolean isValid = (boolean) isValidMap.get("isValid");
			
			
			// 회원정보 일치시 회원 탈퇴
			if(isValid) {
				Member memberInfo = (Member) isValidMap.get("memberInfo");
				
				// 회원탈퇴 서비스 메소드 호출(숙제 : 
				memberService.removeMember(memberInfo);
				return "redirect:/member/memberList";
			}
			
			reAttr.addAttribute("memberId", memberId);
			reAttr.addAttribute("msg", "회원 정보 불일치");
			
			return "redirect:/member/removeMember";
		}
//		/* 회원탈퇴 폼
//		 * 
//		 */
//		// @param memberId
//		// @return 회원탈퇴 페이지
//		
//		@PostMapping("/removeMember")
//		public String removeMember(@RequestParam(value="memberId") String memberId,
//									@RequestParam(value="msg", required = false) String msg,
//									Model model) {
//			model.addAttribute("title", "회원탈퇴");
//			model.addAttribute("memberId", memberId);
//			if(msg != null) model.addAttribute("msg", msg);
//			
//			return "member/removeMember";
//			}
}



