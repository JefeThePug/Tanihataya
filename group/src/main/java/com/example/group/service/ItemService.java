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

	List<Item> findAllByCategory(Integer category) {
		return itemMapper.findAllByCategory(category);
	}

	List<Item> findPurchasesByUserId(Integer userId) {
		return itemMapper.findPurchasesByUserId(userId);
	}

	List<Item> findSalesByUserId(Integer userId) {
		return itemMapper.findSalesByUserId(userId);
	}

	Item findById(Integer itemId) {
		return itemMapper.findById(itemId);
	}

	void insert(Item item) {
		itemMapper.insert(item);
	}

	void update(Item item) {
		itemMapper.update(item);
	}

	void markForDelete(Integer itemId) {
		itemMapper.markForDelete(itemId);
	}
}
