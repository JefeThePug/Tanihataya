package com.example.group.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.group.Entity.Items;

@Mapper
public interface ItemsMapper {

	//カテゴリーごとのアイテム一覧表示
	List<Items>findAllByCategory();
	
	//特定ユーザーの購入履歴一覧表示
	List<Items>findPurchasesByUserId();
	
	//特定ユーザーのすべての販売履歴
	List<Items>findSalesByUserId();

	//アイテムIDでアイテムを1件取得
	void findById(Items items);

	//アイテムを追加
	void insert(Items items);

	//アイテムを更新
	void update(Items items);

	//アイテムに削除フラグを付与
	void markForDelete(Items item_id);
	
}
