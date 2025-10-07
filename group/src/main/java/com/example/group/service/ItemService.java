package com.example.group.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.group.entity.Items;
import com.example.group.form.ItemForm;
import com.example.group.repository.ItemsMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {
	private final ItemsMapper itemMapper;

	/*
	 * Items オブジェクトのリストを ItemForm オブジェクトのリストに変換する
	 */
	public List<ItemForm> entitiesToForm(List<Items> items) {
		List<ItemForm> forms = new ArrayList<>();
		for (Items item : items) {
			ItemForm form = new ItemForm();
			form.setItemId(item.getItemId());
			form.setUserId(item.getUsersId());
			form.setName(item.getName());
			form.setCategory(String.valueOf(item.getCategory()));
			form.setDetail(item.getDetail());
			form.setPrice(item.getPrice());
			form.setSaleStatus(item.isSaleStatus());
			form.setBuyUser(item.getBuyUser());
			// 保存した画像パスの文字列をカンマで分割して、パスの String 配列を作る
			form.setExistingImages(item.getImagePaths().split(","));
			forms.add(form);
		}
		return forms;
	}

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
		item.setUsersId(form.getUserId());
		item.setName(form.getName());
		item.setCategory(Integer.parseInt(form.getCategory()));
		item.setDetail(form.getDetail());
		item.setPrice(form.getPrice());
		// システムが設定する値
		item.setSaleStatus(true); // 初期値は販売中
		item.setBuyUser(null); // 初期値はnull
		item.setCreatedAt(LocalDateTime.now());
		item.setUpdatedAt(LocalDateTime.now());

		List<String> imgPaths = new ArrayList<>();
		// ItemForm はユーザーがアップロードした MultipartFileのList を返す
		for (MultipartFile file : form.getImages()) {
			if (file != null && !file.isEmpty()) {
				// 各画像にランダムな接頭辞を付けて、
				// ユーザーが同じ名前のファイルをアップロードしても問題にならないようにする
				String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
				imgPaths.add(filename);
			}
		}
		item.setImagePaths(String.join(",", imgPaths));

		itemMapper.insert(item);

		// SQL の INSERT が成功した場合に、ファイルを保存する
		File uploadDir = new File("src/main/resources/static/images/user_imgs");
		if (!uploadDir.exists()) {
			uploadDir.mkdirs();
		}
		for (int i = 0; i < form.getImages().size(); i++) {
			MultipartFile file = form.getImages().get(i);
			if (file != null && !file.isEmpty()) {
				// データベースに送信したのと同じファイル名で画像を保存する
				Path path = Paths.get(uploadDir.getAbsolutePath(), imgPaths.get(i));
				try {
					file.transferTo(path);
				} catch (IOException e) {
					throw new RuntimeException("アップロードされたファイルの保存に失敗しました", e);
				}
			}
		}
	}

	public void update(ItemForm form) {
		// Formの内容をItemsエンティティにコピー
		Items item = new Items();
		item.setItemId(form.getItemId());
		item.setUsersId(form.getUserId());
		item.setName(form.getName());
		item.setCategory(Integer.parseInt(form.getCategory()));
		item.setDetail(form.getDetail());
		item.setPrice(form.getPrice());
		item.setUpdatedAt(LocalDateTime.now());

		// 保存および削除する画像ファイルとパス名を格納するための
		// List と Map を作成する
		List<String> imgPaths = new ArrayList<>();
		List<String> toDelete = new ArrayList<>();
		Map<String, MultipartFile> toSave = new LinkedHashMap<>();

		// フォームで既存の画像を削除すべきと示している場合、
		// その画像を削除対象のパスのリストに追加する
		if (form.getDeleteImages() != null) {
			for (int i = 0; i < form.getDeleteImages().length; i++) {
				if (form.getDeleteImages()[i]) {
					toDelete.add(form.getExistingImages()[i]);
				}
			}
		}

		// ユーザーがアップロードしたファイルのリストの有効な長さを取得する
		// （フォームは常に5件返すが、未アップロードのファイルは空なので、
		//  最後の非空ファイルを確認する）
		int newImageSize = 0;
		for (int i = form.getImages().size() - 1; i >= 0; i--) {
			MultipartFile file = form.getImages().get(i);
			if (file != null && !file.isEmpty()) {
				newImageSize = i + 1;
				break;
			}
		}

		// 既存の画像リストと新規アップロード画像リストのどちらが長いかを判定する
		int numImages = Math.max(newImageSize,
				(form.getExistingImages() == null) ? 0 : form.getExistingImages().length);

		// 各画像スロットごとに…
		for (int i = 0; i < numImages; i++) {
			MultipartFile file = form.getImages().get(i);

			// ユーザーが画像をアップロードしている場合、
			if (file != null && !file.isEmpty()) {
				// INSERT と同様の名前を付け、データベース用の画像パスリスト"imgPaths"にパスを保存し、
				// 保存用の画像マップ"toSave"にパスとファイルを登録する
				String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
				imgPaths.add(filename);
				toSave.put(filename, file);
			}
			// それ以外の場合、既存の画像がまだ存在して変更されていない場合は、
			else if (i < form.getDeleteImages().length && !form.getDeleteImages()[i]) {
				// 既存の画像パスをそのままデータベースに追加する
				imgPaths.add(form.getExistingImages()[i]);
			}
		}

		// パスのListをカンマで区切った1つの文字列に結合する
		item.setImagePaths(String.join(",", imgPaths));
		// データベースを更新する
		itemMapper.update(item);

		// SQL の UPDATE が成功した場合に、ファイルを保存および削除する
		File uploadDir = new File("src/main/resources/static/images/user_imgs");
		if (!uploadDir.exists()) {
			uploadDir.mkdirs();
		}

		// toDelete にあるパスのファイルをすべて削除する
		for (String oldFile : toDelete) {
			Path oldPath = Paths.get(uploadDir.getAbsolutePath(), oldFile);
			try {
				Files.deleteIfExists(oldPath);
			} catch (IOException e) {
				throw new RuntimeException("ファイルの削除に失敗しました", e);
			}
		}

		// toSaveのMapにある各ファイルを指定されたファイル名で保存する
		for (Map.Entry<String, MultipartFile> entry : toSave.entrySet()) {
			String filename = entry.getKey();
			MultipartFile file = entry.getValue();
			Path path = Paths.get(uploadDir.getAbsolutePath(), filename);
			try {
				file.transferTo(path);
			} catch (IOException e) {
				throw new RuntimeException("アップロードされたファイルの保存に失敗しました" + filename, e);
			}
		}
	}

	@Transactional
	public void completePurchase(int itemId, int buyUser) {
		// ① 対象アイテムを取得
		Items item = itemMapper.findById(itemId);
		if (item == null) {
			throw new IllegalArgumentException("Item not found: " + itemId);
		}
		if (!item.isSaleStatus()) {
			throw new IllegalStateException("This item is already sold.");
		}

		// ② 購入情報をセット
		item.setSaleStatus(false); // 販売終了
		item.setBuyUser(buyUser); // ← buyerId を buyUser に合わせる
		item.setPurchaseAt(LocalDateTime.now()); // ← purchaseAt → purchaseDate に修正
		item.setUpdatedAt(LocalDateTime.now()); // 更新日時

		// ③ DB更新
		itemMapper.updatePurchaseInfo(
				item.getItemId(),
				item.getBuyUser(),
				item.getPurchaseAt());
	}

	public void markForDelete(Integer itemId) {
		itemMapper.markForDelete(itemId);
	}
}