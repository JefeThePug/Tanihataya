package com.example.group.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.group.entity.Payment;
import com.example.group.entity.Users;
import com.example.group.repository.PaymentMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PurchaseService {
	
    private final PaymentMapper paymentMapper;
    
    //クレジットカード登録
    public void insertPayment(Payment payment) {
       paymentMapper.insertPayment(payment);
    }

    //クレジット情報を削除
    public void deletePaymentByUserId(Payment payment) {
    	Users user = new Users();
    	paymentMapper.deletePaymentByUserId(user.getUsersId());
    }
    
    //クレジット情報の検索
    public Payment findById(Integer userId) {
    	Payment payment =paymentMapper.findById(userId);
		return payment;
    }

    //update
    public void updatePayment(Payment payment) {
    	paymentMapper.updatePayment(payment);
    }
}
