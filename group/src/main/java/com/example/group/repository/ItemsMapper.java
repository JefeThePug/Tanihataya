package com.example.group.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ItemsMapper {

	//カテゴリーごとのアイテム一覧表示
	List<Items>findAllByCategory();
	
	//特定ユーザーの購入履歴一覧表示
	List<Items>findPurchasesByUserId();

	//アイテムIDでアイテムを1件取得
	void findById(Item item);

	//アイテムを追加
	void insert(Item item);

	//アイテムを更新
	void update(Item item);

	//アイテムに削除フラグを付与
	void markForDelete(Item items);
	
	
	
}
