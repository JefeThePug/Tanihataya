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

	 // --- 検索系メソッド ---
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

	 // --- データ操作系メソッド ---
	public void insert(ItemForm form) {
		Items item = new Items();
		
		// Formから受け取る値
		item.setUserId(form.getUserId());
		item.setName(form.getName());
		item.setCategory(form.getCategory());
		item.setDetail(form.getDetail());
		item.setPrice(form.getPrice());
		item.setImagePaths(form.getImagesPath()); 
				
		// システムが設定する値
		item.setSaleStatus(true); // 初期値は販売中
		item.setBuyUser(null);    // 初期値はnull
		item.setCreatedAt(LocalDateTime.now());
		item.setUpdatedAt(LocalDateTime.now());
		
		itemMapper.insertItem(item);
	}

	public void update(ItemForm form) {
        // Formの内容をItemsエンティティにコピー
        Items item = new Items();
        item.setItemId(form.getItemId());
        item.setUserId(form.getUserId());
        item.setName(form.getName());
        item.setCategory(form.getCategory());
        item.setDetail(form.getDetail());
        item.setPrice(form.getPrice());
        item.setImagePaths(form.getImagesPath());

        // 更新日時をシステム側で設定
        item.setUpdatedAt(LocalDateTime.now());
        
        // ※注意: saleStatusやbuyUserの更新が必要な場合は、別途Formに持たせるか、
        //         このServiceでDBから既存データを取得して設定する必要があります。
        
		itemMapper.update(item);
	}

	public void markForDelete(Integer itemId) {
		itemMapper.markForDelete(itemId);
	}
}
