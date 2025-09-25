package com.example.group.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.group.Entity.Item;
import com.example.group.repository.ItemsMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {
	private final ItemsMapper itemMapper;

	public List<Item> findAllByCategory(Integer category) {
		return itemMapper.findAllByCategory(category);
	}

	public List<Item> findPurchasesByUserId(Integer userId) {
		return itemMapper.findPurchasesByUserId(userId);
	}

	public List<Item> findSalesByUserId(Integer userId) {
		return itemMapper.findSalesByUserId(userId);
	}

	public Item findById(Integer itemId) {
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
