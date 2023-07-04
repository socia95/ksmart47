package ksmart.mybatis.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import ksmart.mybatis.dto.Goods;
import ksmart.mybatis.dto.Member;
import ksmart.mybatis.service.GoodsService;
import ksmart.mybatis.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/goods")
@AllArgsConstructor
@Slf4j
public class GoodsController {
	
	
	@GetMapping("/sellerList")
	public String getSellerList(Model model, HttpSession session) {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		String memberId = (String) session.getAttribute("SID");
		
		if(memberId != null) {
			int memberLevel = (int) session.getAttribute("SLEVEL");
			if(memberLevel == 2) paramMap.put("sellerId", memberId);
		}
		
		paramMap.put("memberLevel", 2);
		
		List<Member> sellerList = goodsService.getSellerList(paramMap);
		
		model.addAttribute("title", "판매자별 상품목록 조회");
		model.addAttribute("sellerList", sellerList);
		
		return "goods/sellerList";
	}
	private final GoodsService goodsService;
	private final MemberService memberService;
	
	/* 상품 삭제 폼
	 * 
	 */
	// @param goodsCode
	// @return 상품 삭제 페이지
	@GetMapping("/removeGoods")
	public String removeGoods(@RequestParam(value="goodsCode") String goodsCode,
								@RequestParam(value="goodsName") String goodsName,
								@RequestParam(value="sellerId") String sellerId,
								@RequestParam(value="msg", required=false) String msg, Model model) {
		Goods goodsInfo = goodsService.getGoodsByCode(goodsCode);
		goodsName = goodsInfo.getGoodsSellerId();
		sellerId = goodsInfo.getGoodsSellerId();
		
		model.addAttribute("title", "상품 삭제");
		model.addAttribute("goodsCode", goodsCode);
		model.addAttribute("goodsName", goodsName);
		model.addAttribute("sellerId", sellerId);
		
		if(msg !=null) model.addAttribute("msg", msg);
		
		return "goods/removeGoods";
	}
	
	@PostMapping("/removeGoods")
	public String removeGoods(@RequestParam(value="goodsCode") String goodsCode,
								@RequestParam(value="memberPw") String memberPw,
								RedirectAttributes reAttr) {
		Goods goodsInfo = goodsService.getGoodsByCode(goodsCode);
		String sellerId = goodsInfo.getGoodsSellerId();
		
		// 회원 여부 검증
		Map<String, Object> isValidMap = memberService.isValidMember(sellerId, memberPw);
		boolean isValid = (boolean) isValidMap.get("isValid");
		
		if(isValid) {
			goodsService.removeGoods(goodsInfo);
			return "redirect:/goods/goodsList";
		}
		reAttr.addAttribute("goodsCode", goodsCode);
		reAttr.addAttribute("msg", "회원 정보 불일치");
		
		return "redirect:/goods/removeGoods";
	}
	
	@PostMapping("/addGoods")
	public String addGoods(Goods goods) {
		log.info("goods: {}", goods);
		goodsService.addGoods(goods);
		return "redirect:/goods/goodsList";
	}
	
	@GetMapping("/goodsList")
	public String getGoodsList(Model model,
			@RequestParam(value="searchKey2", required= false, defaultValue = "") String searchKey2,
			@RequestParam(value="searchValue2", required= false) String searchValue2) {
		
		log.info("searchKey2 : {}", searchKey2);
		log.info("searchValue2 : {}", searchValue2);
		
		 List <Goods> goodsList = goodsService.getGoodsList(searchKey2, searchValue2);
		 model.addAttribute("title", "상품목록");
		 model.addAttribute("goodsList",goodsList);
		
		return "goods/goodsList";
	}
	
	@GetMapping("/addGoods")
	public String addGoods(Model model) {
		List<Member> sellerInfoList = goodsService.getSellerInfoList();
		model.addAttribute("title", "상품등록");
		model.addAttribute("sellerInfoList", sellerInfoList);
		
		return "goods/addGoods";
	}
	
	@GetMapping("/modifyGoods")
	public String modifyGoods(@RequestParam(value="goodsCode") String goodsCode, Model model) {
		
		
		List<Member> sellerInfoList = goodsService.getSellerInfoList();
		Goods goodsInfo = goodsService.getGoodsByCode(goodsCode);
		
		model.addAttribute("title", "상품수정");
		model.addAttribute("sellerInfoList", sellerInfoList);
		model.addAttribute("goodsInfo", goodsInfo);
		
			
		return "goods/modifyGoods";
	}

	@PostMapping("/modifyGoods")
	public String modifyGoods(Goods goods) {

		return "redirect:/goods/goodsList";
	}
}
