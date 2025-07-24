package com.bookshop.dispatcherservice;
//발송된 주문 이벤트를 나타내는 DTO
public record OrderDispatchedMessage (
		Long orderId
){}
