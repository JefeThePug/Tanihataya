package com.example.group.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import com.example.group.entity.Items;
import com.example.group.form.ItemForm;
import com.example.group.repository.ItemsMapper;

@ExtendWith(MockitoExtension.class)
@DisplayName("Item Service Unit Test")
class ItemServiceTest {
	@Mock
	private ItemsMapper itemMapper;

	@InjectMocks
	private ItemService itemService;

	private ItemForm form;
	private Items item;
	List<MultipartFile> fullFiles;
	List<MultipartFile> oneFile;
	private List<Items> items;

	@BeforeEach
	void setUp() {
		// Make a Lists of 5 files / 5つのファイルのリストを作成
		fullFiles = new ArrayList<>();
		oneFile = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			MultipartFile emptyFile = mock(MultipartFile.class);
			lenient().when(emptyFile.isEmpty()).thenReturn(true);
			MultipartFile fullFile = mock(MultipartFile.class);
			lenient().when(fullFile.isEmpty()).thenReturn(false);
			lenient().when(fullFile.getOriginalFilename()).thenReturn("image" + i + ".png");
			fullFiles.add(fullFile);

			if (i == 0) {
				oneFile.add(fullFile);
			} else {
				oneFile.add(emptyFile);
			}
		}
		// Setup ItemForm with example values / ItemFormにサンプル値を設定
		form = new ItemForm();
		form.setItemId(1);
		form.setUserId(2);
		form.setName("Coffee Mug");
		form.setCategory("3");
		form.setDetail("Ceramic mug.");
		form.setPrice(1000);
		form.setSaleStatus(true);
		form.setBuyUser(null);
		form.setExistingImages(new String[] { "a.jpg", "b.jpg" });
		form.setDeleteImages(new Boolean[] { false, false });
		form.setImages(oneFile);
		// Setup Items entity / Itemsにサンプル値を設定
		item = new Items();
		item.setItemId(1);
		item.setUsersId(42);
		item.setName("Test Item");
		item.setCategory(2);
		item.setDetail("This is a test.");
		item.setPrice(123);
		item.setSaleStatus(true);
		item.setBuyUser(null);
		item.setImagePaths("a.jpg,b.jpg");
		item.setCreatedAt(LocalDateTime.now());
		item.setUpdatedAt(LocalDateTime.now());
		item.setPurchaseAt(null);
		//Make List of (one) Items / （1つの）Itemsのリストを作成
		items = List.of(item);
	}

	// @Nested は、テストを整理のために内部クラスに格納していても、@Test が実行されるようにするために使用
	@Nested
	@DisplayName("Entities to Form Conversion Tests")
	class EntitiesToFormTests {
		@Test
		void testEntitiesToForm() {
			// Given 前提

			// When 操作
			final List<ItemForm> result = itemService.entitiesToForm(items);
			// Then 期待結果
			assertEquals(1, result.size());
			ItemForm formResult = result.get(0);
			assertEquals("Test Item", formResult.getName());
			assertArrayEquals(new String[] { "a.jpg", "b.jpg" }, formResult.getExistingImages());
		}
	}

	@Nested
	@DisplayName("findById Tests")
	class FindByIdTests {
		@Test
		void testFindById() {
			// Given 前提
			// 		itemMapper に対して、findById が 1 の ID で呼ばれたときにモックの item を返すよう指示
			when(itemMapper.findById(1)).thenReturn(item);
			// When 操作
			Items result = itemService.findById(1);
			// Then 期待結果
			assertEquals(1, result.getItemId());
			//		findById が一度だけ呼ばれ、引数が 1 であったことを確認
			verify(itemMapper).findById(1);
		}
	}

	@Nested
	@DisplayName("Insert Method Tests")
	class InsertTests {
		@Test
		void testInsert() throws IOException {
			// Given 前提
			form.setImages(fullFiles);
			// When 操作
			// 		Create an ArgumentCaptor to capture the Items object passed to the mapper
			// 		マッパーに渡される Items オブジェクトを取得するための ArgumentCaptor を作成
			ArgumentCaptor<Items> captor = ArgumentCaptor.forClass(Items.class);
			itemService.insert(form);
			// Then 期待結果
			//		Capture the actual Items object passed to the mapper during verification
			// 		検証時にマッパーに渡された実際の Items オブジェクトを取得
			verify(itemMapper).insert(captor.capture());
			//		Get the captured Items object for inspection in assertions
			//		取得した Items オブジェクトを取り出し、アサーションで確認する
			Items inserted = captor.getValue();
			//		Check that form values were transferred correctly
			//		フォームの値がエンティティに正しく転送されているか確認
			assertEquals("Coffee Mug", inserted.getName(), "Name should match form");
			assertEquals(2, inserted.getUsersId(), "UserId should match form");
			assertEquals(3, inserted.getCategory(), "Category should match form");
			//		Check that all 5 mocked files were included in the image paths
			//		5つのモックファイルすべてが画像パスに含まれていることを確認
			for (int i = 0; i < fullFiles.size(); i++) {
				String filename = fullFiles.get(i).getOriginalFilename();
				assertTrue(inserted.getImagePaths().contains(filename),
						"Image paths should contain " + filename);
			}
			//		Check initial sale status, buy user, and purchase date
			//		挿入されたアイテムの初期の販売状況、購入者、購入日時を確認
			assertTrue(inserted.isSaleStatus(), "New item should be marked as for sale");
			assertNull(inserted.getBuyUser(), "New item should have no buyer yet");
			assertNull(inserted.getPurchaseAt(), "New item should have no purchase date");
		}

		@Test
		void testInsertWithOneFile() throws IOException {
			// Given 前提
			form.setImages(oneFile);
			// When 操作
			ArgumentCaptor<Items> captor = ArgumentCaptor.forClass(Items.class);
			itemService.insert(form);
			// Then 期待結果
			verify(itemMapper).insert(captor.capture());
			Items inserted = captor.getValue();
			//		Image paths should have only one image path
			String paths = inserted.getImagePaths();
			assertEquals(1, paths.split(",").length, "There should be exactly one image path");
			assertFalse(paths.contains(","), "There should be no comma when only one image");
			assertTrue(paths.contains("image0.png"), "The path should include the uploaded file");
			//		Check initial sale status, buy user, and purchase date
			assertTrue(inserted.isSaleStatus(), "New item should be marked as for sale");
			assertNull(inserted.getBuyUser(), "New item should have no buyer yet");
			assertNull(inserted.getPurchaseAt(), "New item should have no purchase date");
		}
	}

	/* The `Update` method is not unit tested because it interacts with the file system.
	 *  >`Update` メソッドはファイルシステムに関わるため、単体テストは行っていません。
	 * Testing it fully would require either:
	 *  > 完全にテストするには以下のいずれかが必要です：
	 *   - An integration test with temporary files / 一時ファイルを使用した統合テスト
	 *   - Heavy mocking of java.nio.file.Files / java.nio.file.Files の大規模なモック
	 * Unit tests for ItemService focus on methods that contain business logic without external side effects.
	 *  > ItemService の単体テストは、外部副作用のないビジネスロジックを含むメソッドに焦点を当てています。
	 */

	@Nested
	@DisplayName("Complete Purchase Tests")
	class CompletePurchaseTests {
		@Test
		void testCompletePurchaseSuccess() {
			// Given 前提
			Items existing = new Items();
			existing.setItemId(10);
			existing.setSaleStatus(true);
			when(itemMapper.findById(10)).thenReturn(existing);
			// When 操作
			ArgumentCaptor<LocalDateTime> dateCaptor = ArgumentCaptor.forClass(LocalDateTime.class);
			itemService.completePurchase(10, 42);
			// Then 期待結果
			verify(itemMapper).updatePurchaseInfo(eq(10), eq(42), dateCaptor.capture());
			assertNotNull(dateCaptor.getValue(), "Purchase date should not be null");
			//		And check item object was updated correctly
			assertFalse(existing.isSaleStatus(), "Item should no longer be for sale");
			assertEquals(42, existing.getBuyUser(), "Buy user should be set");
			assertNotNull(existing.getPurchaseAt(), "Purchase date should be set");
		}

		@Test
		void testCompletePurchaseItemNotFound() {
			// Given 前提
			when(itemMapper.findById(99)).thenReturn(null);

			// When 操作
			// Then 期待結果
			IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
					() -> itemService.completePurchase(99, 1));
			assertEquals("Item not found: 99", ex.getMessage());
		}

		@Test
		void testCompletePurchaseAlreadySold() {
			// Given 前提
			Items existing = new Items();
			existing.setItemId(5);
			existing.setSaleStatus(false);
			when(itemMapper.findById(5)).thenReturn(existing);

			// When 操作
			// Then 期待結果
			IllegalStateException ex = assertThrows(IllegalStateException.class,
					() -> itemService.completePurchase(5, 1));
			assertEquals("This item is already sold.", ex.getMessage());
		}
	}
}
