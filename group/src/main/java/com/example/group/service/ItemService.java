package com.example.group.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.group.Entity.Items;
import com.example.group.repository.ItemsMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {
	private final ItemMapper itemMapper;

	public List<Items> findAllByCategory(Integer category) {
		return ItemsMapper.findAllByCategory(category);
	}

	public List<Items> findPurchasesByUserId(Integer userId) {
		return ItemsMapper.findPurchasesByUserId(userId);
	}

	public List<Items> findSalesByUserId(Integer userId) {
		return ItemsMapper.findSalesByUserId(userId);
	}

	public Item findById(Integer itemId) {
		return itemMapper.findById(itemId);
	}

	public void insert(ItemForm item) {
		ItemsMapper.insert(item);
	}

	public void update(ItemForm item) {
		ItemsMapper.update(item);
	}

	public void markForDelete(Integer itemId) {
		ItemsMapper.markForDelete(itemId);
	}
}
