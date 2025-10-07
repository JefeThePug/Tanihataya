package com.example.group.form;

import java.util.List;

import jakarta.validation.constraints.Pattern;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemForm {

	private Integer itemId;

	private Integer userId;

	private String name;

	@Pattern(regexp = "\\d", message = "カテゴリーは指定された値のいずれかで入力してください")
	private String category;

	private String detail;

	private Integer price;

	private boolean saleStatus;

	private Integer buyUser;

	// 表示用にデータベースに既に保存されている画像
	private String[] existingImages;
	
	// 更新時にユーザーが既存の画像を削除または置き換えた場合に、
	// 削除対象としてマークするフラグ
	private Boolean[] deleteImages;

	// フォームにユーザーがアップロードした画像ファイル
	private List<MultipartFile> images;

}
