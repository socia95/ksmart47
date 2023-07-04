package ksmart.mybatis.service;

import ksmart.mybatis.dto.Goods;
import ksmart.mybatis.dto.Member;
import ksmart.mybatis.mapper.GoodsMapper;
import ksmart.mybatis.mapper.MemberMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class GoodsService {

    private final GoodsMapper goodsMapper;
    private final MemberMapper memberMapper;
    
    public List<Member> getSellerList(){
    	List<Member> sellerList = goodsMapper.getSellerList();
    	log.info("sellerList : {} ", sellerList);
    	
    	return sellerList;
    }
    
    
    /**
     * 상품목록조회
     * @return goodsList
     */
    public List<Goods> getGoodsList(String searchKey, String searchValue) {

        Map<String, Object> paramMap = null;

        if(searchValue != null) {
            switch (searchKey) {
                case "goodsCode" -> {
                    searchKey = "g.g_code";
                }
                case "goodsName" -> {
                    searchKey = "g.g_name";
                }
                case "sellerId" -> {
                    searchKey = "g.g_seller_id";
                }
                case "sellerName" -> {
                    searchKey = "m.m_name";
                }
                case "sellerEmail" -> {
                    searchKey = "m.m_email";
                }
            }
            paramMap = new HashMap<String, Object>();
            paramMap.put("searchKey", searchKey);
            paramMap.put("searchValue", searchValue);
        }

        List<Goods> goodsList = goodsMapper.getGoodsList(paramMap);

        //log.info("goodsList : {}", goodsList);

        return goodsList;
    }

    /**
     * 판매자정보 조회
     * @return List<Member>
     */
    public List<Member> getSellerInfoList() {

        List<Member> sellerInfoList = memberMapper.getMemberInfoByLevel(2);
        											

        return sellerInfoList;
    }

	/**
	 * 상품 정보 삭제
	 * @param goodsInfo
	 */
	public void removeGoods(Goods goodsCode) {
			
			goodsMapper.removeOrderByGCode(goodsCode);
			goodsMapper.removeGoodsByCode(goodsCode);
		}

    /**
     * 상품 등록
     * @param goods
     */
    public void addGoods(Goods goods) {
        // goods객체에 현재 상품코드가 없다.
        //log.info("insert 전 goods : {}", goods);
        goodsMapper.addGoods(goods);
        // goods객체에 현재 상품코드가 있다.
        //log.info("insert 후 goods : {}", goods);

        // 상품 상세 테이블 insert (외래키 설정)
        // String goodsCode = goods.getGoodsCode();
    }

    /**
     * 상품코드별 조회
     * @param goodsCode
     * @return
     */
    public Goods getGoodsByCode(String goodsCode) {

        Goods goodsInfo = goodsMapper.getGoodsByCode(goodsCode);

        return goodsInfo;
    }

    public void modifyGoods(Goods goods) {
    	goodsMapper.modifyGoods(goods);
    }
    
}
