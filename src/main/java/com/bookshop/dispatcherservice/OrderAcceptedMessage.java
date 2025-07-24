package com.bookshop.dispatcherservice;
//접수된 주문에 대한 이벤트를 나타내는 DTO
public record OrderAcceptedMessage (//주문의 아이디를 Long타입의 필드에 가지고 있는 DTO
		Long orderId
){}
