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
        Payment payment1 = new Payment();

       paymentMapper.insertPayment(payment1);
    }

    //クレジット情報を削除
    public void deletePaymentByUserId(Payment payment) {
    	Payment pay = new Payment();
    	Users user = new Users();
    	
    	paymentMapper.deletePaymentByUserId(user.getUsersId());
    }

    //update
    public void updatePayment(Integer userId) {
    	Payment payment = new Payment();
    	
    	paymentMapper.updatePayment(payment);
    }
}
