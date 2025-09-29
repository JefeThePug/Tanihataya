package com.example.group.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.group.Entity.Items;
import com.example.group.form.ItemForm;

@Mapper
public interface ItemsMapper {

	//カテゴリーごとのアイテム一覧表示
	List<Items>findAllByCategory(Integer category);
	
	//特定ユーザーの購入履歴一覧表示
	List<Items>findPurchasesByUserId(Integer userId);
	
	//特定ユーザーのすべての販売履歴
	List<Items>findSalesByUserId(Integer userId);
	
	// アイテムIDで1件取得
    List<Items> findById(Integer itemId);

	//アイテムを追加
	void insert(Items items);

	//アイテムを更新
	void update(ItemForm item);

	//アイテムに削除フラグを付与
	void markForDelete(Integer item_id);
	

}
