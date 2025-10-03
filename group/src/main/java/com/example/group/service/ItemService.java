package com.example.group.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

	private List<ItemForm> entitiesToForm(List<Items> items) {
		List<ItemForm> forms = new ArrayList<>();
		for (Items item : items) {
			ItemForm form = new ItemForm();
			form.setItemId(item.getItemId());
			form.setUserId(item.getUserId());
			form.setName(item.getName());
			form.setCategory(item.getCategory());
			form.setDetail(item.getDetail());
			form.setPrice(item.getPrice());
			form.setSaleStatus(item.isSaleStatus());
			form.setBuyUser(item.getBuyUser());
			List<File> imgs = new ArrayList<>();
			File uploadDir = new File("/path/to/uploadDir");
			for (String path : item.getImagePaths().split(",")) {
				File file = new File(uploadDir, path.trim());
				if (file.exists()) {
					imgs.add(file);
				} else {
					System.out.println("File not found: " + file.getAbsolutePath());
				}
			}
			form.setExistingImages(imgs);
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
		item.setUserId(form.getUserId());
		item.setName(form.getName());
		item.setCategory(form.getCategory());
		item.setDetail(form.getDetail());
		item.setPrice(form.getPrice());

		File uploadDir = new File("src/main/resources/static/images/user_imgs");
		if (!uploadDir.exists()) {
			uploadDir.mkdirs();
		}
		List<String> imgPaths = new ArrayList<>();
		for (MultipartFile file : form.getImages()) {
			if (file != null && !file.isEmpty()) {
				String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
				Path path = Paths.get(uploadDir.getAbsolutePath(), filename);
				try {
					file.transferTo(path);
					imgPaths.add(filename);
				} catch (IOException e) {
					throw new RuntimeException("Failed to save uploaded file", e);
				}
			}
		}
		item.setImagePaths(String.join(",", imgPaths));

		// システムが設定する値
		item.setSaleStatus(true); // 初期値は販売中
		item.setBuyUser(null); // 初期値はnull
		item.setCreatedAt(LocalDateTime.now());
		item.setUpdatedAt(LocalDateTime.now());

		itemMapper.insert(item);
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

		File uploadDir = new File("src/main/resources/static/images/user_imgs");
		if (!uploadDir.exists()) {
			uploadDir.mkdirs();
		}
		List<String> imgPaths = new ArrayList<>();
		for (String oldFile : item.getImagePaths().split(",")) {
			Path oldPath = Paths.get(uploadDir.getAbsolutePath(), oldFile);
			try {
				Files.deleteIfExists(oldPath);
			} catch (IOException e) {
				throw new RuntimeException("Failed to save uploaded file", e);
			}
		}
		for (MultipartFile file : form.getImages()) {
			if (file != null && !file.isEmpty()) {
				String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
				Path path = Paths.get(uploadDir.getAbsolutePath(), filename);
				try {
					file.transferTo(path);
					imgPaths.add(filename);
				} catch (IOException e) {
					throw new RuntimeException("Failed to save uploaded file", e);
				}
			}
		}
		item.setImagePaths(String.join(",", imgPaths));

		// 更新日時をシステム側で設定
		item.setUpdatedAt(LocalDateTime.now());

		// ※注意: saleStatusやbuyUserの更新が必要な場合は、別途Formに持たせるか、
		//         このServiceでDBから既存データを取得して設定する必要があります。

		itemMapper.update(item);
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