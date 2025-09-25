package com.example.group.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {
	private final ItemMapper itemMapper;

	public List<Items> findAllByCategory(Integer category) {
		return itemMapper.findAllByCategory(category);
	}

	public List<Items> findPurchasesByUserId(Integer userId) {
		return itemMapper.findPurchasesByUserId(userId);
	}

	public List<Items> findSalesByUserId(Integer userId) {
		return itemMapper.findSalesByUserId(userId);
	}

	public Items findById(Integer itemId) {
		return itemMapper.findById(itemId);
	}

	public void insert(ItemForm item) {
		itemMapper.insert(item);
	}

	public void update(ItemForm item) {
		itemMapper.update(item);
	}

	public void markForDelete(Integer itemId) {
		itemMapper.markForDelete(itemId);
	}
}
