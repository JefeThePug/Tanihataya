package com.example.group.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.group.entity.Items;
import com.example.group.form.ItemForm;
import com.example.group.repository.ItemsMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {
	private final ItemsMapper itemMapper;

	public List<Items> findAllByCategory(Integer category) {
		return itemMapper.findAllByCategory(category);
	}

	public List<Items> findPurchasesByUserId(Integer userId) {
		return itemMapper.findPurchasesByUserId(userId);
	}

	public List<Items> findSalesByUserId(Integer userId) {
		return itemMapper.findSalesByUserId(userId);
	}
	
	public Items findById(int itemId) {
        return itemMapper.findById(itemId); // 1件返す
    }

	public void insert(ItemForm form) {
		Items item = new Items();
		item.setUserId(form.getUserId());
		item.setName(form.getName());
		item.setDetail(form.getDetail());
		item.setPrice(form.getPrice());
		item.setSaleStatus(true);
		item.setBuyUser(null);
		// images
		item.setCreatedAt(LocalDateTime.now());
		item.setUpdatedAt(LocalDateTime.now());
		itemMapper.insertItem(item);
	}

	public void update(ItemForm item) {
		itemMapper.update(item);
	}

	public void markForDelete(Integer itemId) {
		itemMapper.markForDelete(itemId);
	}
}
