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

import com.example.group.Entity.Items;
import com.example.group.form.ItemForm;
import com.example.group.repository.ItemsMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {
	private final ItemsMapper itemMapper;

	public List<Items> findAllByCategory(Integer category) {
		return itemMapper.findAllByCategory(category);
	}

	public List<Items> findPurchasesByUserId(Integer userId) {
		return itemMapper.findPurchasesByUserId(userId);
	}

	public List<Items> findSalesByUserId(Integer userId) {
		return itemMapper.findSalesByUserId(userId);
	}

	public Items findById(Integer id) {
		return itemMapper.findById(id);
	}

	public void insert(ItemForm form) {
		Items item = new Items();
		item.setUserId(form.getUserId());
		item.setName(form.getName());
		item.setDetail(form.getDetail());
		item.setPrice(form.getPrice());
		item.setSaleStatus(true);
		item.setBuyUser(null);
		File uploadDir = new File("src/main/resources/static/images/user_imgs");
		if (!uploadDir.exists())
			uploadDir.mkdirs();
		List<String> savedPaths = new ArrayList<>();
		for (MultipartFile file : form.getImagesPath()) {
			if (file != null && !file.isEmpty()) {
				String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
				Path path = Paths.get(uploadDir.getAbsolutePath(), filename);
				try {
					file.transferTo(path);
				} catch (IOException e) {
					throw new RuntimeException("Failed to save uploaded file", e);
				}
				savedPaths.add(filename);
			}
		}
		item.setImagesPath(savedPaths);
		item.setCreatedAt(LocalDateTime.now());
		item.setUpdatedAt(LocalDateTime.now());
		itemMapper.insert(item);
	}

	public void update(ItemForm form) {
		Items item = itemMapper.findById(form.getItemId());
		if (item == null) {
			throw new RuntimeException("Item not found: " + form.getItemId());
		}
		item.setName(form.getName());
		item.setDetail(form.getDetail());
		item.setPrice(form.getPrice());
		item.setCategory(form.getCategory());
		item.setSaleStatus(form.isSaleStatus());
		File uploadDir = new File("src/main/resources/static/images/user_imgs");
		for (String oldFile : item.getImagesPath()) {
			Path oldPath = Paths.get(uploadDir.getAbsolutePath(), oldFile);
			try {
				Files.deleteIfExists(oldPath);
			} catch (IOException e) {
				throw new RuntimeException("Failed to save uploaded file", e);
			}
		}
		if (!uploadDir.exists())
			uploadDir.mkdirs();
		List<String> savedPaths = new ArrayList<>();
		for (MultipartFile file : form.getImagesPath()) {
			if (file != null && !file.isEmpty()) {
				String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
				Path path = Paths.get(uploadDir.getAbsolutePath(), filename);
				try {
					file.transferTo(path);
				} catch (IOException e) {
					throw new RuntimeException("Failed to save uploaded file", e);
				}
				savedPaths.add(filename);
			}
		}
		item.setImagesPath(savedPaths);
		item.setUpdatedAt(LocalDateTime.now());
		itemMapper.update(item);
	}

	public void markForDelete(Integer itemId) {
		itemMapper.markForDelete(itemId);
	}
}
