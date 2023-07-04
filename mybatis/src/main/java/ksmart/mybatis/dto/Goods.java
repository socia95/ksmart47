package ksmart.mybatis.dto;

import lombok.Data;

@Data
public class Goods {
	
	// 상품 정보
	private String goodsCode;
	private String goodsName;
	private int goodsPrice;
	private String goodsSellerId;
	private String goodsRegDate;
	
	// 판매자의 정보
	private Member sellerInfo;
	
	private String goodsOrderCode;
}
