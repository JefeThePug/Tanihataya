package com.example.group.repository;

import org.apache.ibatis.annotations.Mapper;

import com.example.group.entity.Payment;

@Mapper
public interface PyamentMapper {
	/**
     * クレジット情報の登録
     */
    void insertPayment(Payment payment); 

    /**
     * ユーザーIDに基づいてクレジット情報を削除（アカウント削除時などに使用）
     */
    void deletePaymentByUserId(Integer userId); 

    /**
     * クレジット情報の更新
     */
    void updatePayment(Payment payment); 	
}
