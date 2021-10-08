package com.deng.order.bean;

import com.dora.common.db.bean.IDEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.math.BigDecimal;


@Data
public class OrderReturns extends IDEntity {

    private Long id;
	
	/**
	 * 退货编号 供客户查询
	*/
	private String returnsNo;
	
	/**
	 * 订单编号
	*/
	private Long orderId;
	
	/**
	 * 物流单号
	*/
	private String expressNo;
	
	/**
	 * 收货人姓名
	*/
	private String consigneeRealname;
	
	/**
	 * 联系电话
	*/
	private String consigneeTelphone;
	
	/**
	 * 备用联系电话
	*/
	private String consigneeTelphone2;
	
	/**
	 * 收货地址
	*/
	private String consigneeAddress;
	
	/**
	 * 邮政编码
	*/
	private String consigneeZip;
	
	/**
	 * 物流方式
	*/
	private String logisticsType;
	
	/**
	 * 物流发货运费
	*/
	private BigDecimal logisticsFee;
	
	/**
	 * 物流状态
	*/
	private Integer orderLogisticsStatus;
	
	/**
	 * 物流结算状态
	*/
	private Integer logisticsSettlementStatus;
	
	/**
	 * 物流最后状态描述
	*/
	private String logisticsResultLast;
	
	/**
	 * 物流描述
	*/
	private String logisticsResult;
	
	/**
	 * 发货时间
	*/
	private Integer logisticsCreateTime;
	
	/**
	 * 物流更新时间
	*/
	private Integer logisticsUpdateTime;
	
	/**
	 * 物流结算时间
	*/
	private Integer logisticsSettlementTime;
	
	/**
	 * 0全部退单 1部分退单
	*/
	private Byte returnsType;
	
	/**
	 * PUPAWAY:退货入库;REDELIVERY:重新发货;RECLAIM-REDELIVERY:不要求归还并重新发货; REFUND:退款; COMPENSATION:不退货并赔偿
	*/
	private String handlingWay;
	
	/**
	 * 退款金额
	*/
	private BigDecimal returnsAmount;
	
	/**
	 * 退货申请时间
	*/
	private Integer returnSubmitTime;
	
	/**
	 * 退货处理时间
	*/
	private Integer handlingTime;
	
	/**
	 * 退货原因
	*/
	private String remark;
	
	
}