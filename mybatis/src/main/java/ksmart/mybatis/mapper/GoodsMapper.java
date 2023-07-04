package ksmart.mybatis.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import ksmart.mybatis.dto.Goods;
import ksmart.mybatis.dto.Member;

@Mapper
public interface GoodsMapper {

		// 판매자별 상품 목록 조회
	public List<Member> getSellerList();
	
		// 상품 등록
	public int addGoods(Goods goods);
		// 상품 정보 삭제
	public int removeGoodsByCode(String goodsCode);
		// 주문 이력 삭제
	public int removeOrderHistoryByCode(String goodsCode);
	
		// 상품 목록 조회
	public List<Goods> getGoodsList(Map<String, Object> paramMap2);
	
		// 판매자별 상품삭제
	public int removeGoodsById(String sellerId);
		// 판매자가 등록한 상품코드별 주문이력 삭제
	public int removeOrderByGCode(String sellerId);
	
	public Goods getGoodsByCode(String goodsCode);
		// 상품 정보 수정
	public int modifyGoods(Goods goods);

	public int removeOrderByGCode(Goods goodsCode);
	public int removeGoodsByCode(Goods goodsCode);
	
}
