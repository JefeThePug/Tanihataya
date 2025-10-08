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
	void setup() {
		// Make a Lists of 5 files
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
		// Setup ItemForm with example values
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
		// Setup Items entity
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
		//Make List of (one) Items
		items = new ArrayList<>();
		items.add(item);
	}

	@Nested
	@DisplayName("Entities to Form Conversion Tests")
	class EntitiesToFormTests {
		@Test
		void testEntitiesToForm() {
			// Given

			// When
			final List<ItemForm> result = itemService.entitiesToForm(items);
			// Then
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
			// Given
			when(itemMapper.findById(1)).thenReturn(item);
			// When
			Items result = itemService.findById(1);
			// Then
			assertEquals(1, result.getItemId());
			verify(itemMapper).findById(1);
		}
	}

	@Nested
	@DisplayName("Insert Method Tests")
	class InsertTests {
		@Test
		void testInsert() throws IOException {
			// Given
			form.setImages(fullFiles);
			// When
			ArgumentCaptor<Items> captor = ArgumentCaptor.forClass(Items.class);
			itemService.insert(form);
			// Then
			//		Capture the Items object passed to the mapper
			verify(itemMapper).insert(captor.capture());
			Items inserted = captor.getValue();
			//		Check that form values were transferred correctly
			assertEquals("Coffee Mug", inserted.getName(), "Name should match form");
			assertEquals(2, inserted.getUsersId(), "UserId should match form");
			assertEquals(3, inserted.getCategory(), "Category should match form");
			//		Check that all 5 mocked files were included in the image paths
			for (int i = 0; i < fullFiles.size(); i++) {
				String filename = fullFiles.get(i).getOriginalFilename();
				assertTrue(inserted.getImagePaths().contains(filename),
						"Image paths should contain " + filename);
			}
			//		Check initial sale status, buy user, and purchase date
			assertTrue(inserted.isSaleStatus(), "New item should be marked as for sale");
			assertNull(inserted.getBuyUser(), "New item should have no buyer yet");
			assertNull(inserted.getPurchaseAt(), "New item should have no purchase date");
		}

		@Test
		void testInsertWithOneFile() throws IOException {
			// Given
			form.setImages(oneFile);
			// When
			ArgumentCaptor<Items> captor = ArgumentCaptor.forClass(Items.class);
			itemService.insert(form);
			// Then
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
	 * Testing it fully would require either:
	 *   - An integration test with temporary files
	 *   - Heavy mocking of java.nio.file.Files
	 * Unit tests for ItemService focus on methods that contain business logic without external side effects.
	 */

	@Nested
	@DisplayName("Complete Purchase Tests")
	class CompletePurchaseTests {
		@Test
		void testCompletePurchaseSuccess() {
			// Given
			Items existing = new Items();
			existing.setItemId(10);
			existing.setSaleStatus(true);
			when(itemMapper.findById(10)).thenReturn(existing);
			// When
			ArgumentCaptor<LocalDateTime> dateCaptor = ArgumentCaptor.forClass(LocalDateTime.class);
			itemService.completePurchase(10, 42);
			// Then
			verify(itemMapper).updatePurchaseInfo(eq(10), eq(42), dateCaptor.capture());
			assertNotNull(dateCaptor.getValue(), "Purchase date should not be null");
			//		And check item object was updated correctly
			assertFalse(existing.isSaleStatus(), "Item should no longer be for sale");
			assertEquals(42, existing.getBuyUser(), "Buy user should be set");
			assertNotNull(existing.getPurchaseAt(), "Purchase date should be set");
		}

		@Test
		void testCompletePurchaseItemNotFound() {
			// Given
			when(itemMapper.findById(99)).thenReturn(null);

			// When
			// Then
			IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
					() -> itemService.completePurchase(99, 1));
			assertEquals("Item not found: 99", ex.getMessage());
		}

		@Test
		void testCompletePurchaseAlreadySold() {
			// Given
			Items existing = new Items();
			existing.setItemId(5);
			existing.setSaleStatus(false);
			when(itemMapper.findById(5)).thenReturn(existing);

			// When
			// Then
			IllegalStateException ex = assertThrows(IllegalStateException.class,
					() -> itemService.completePurchase(5, 1));
			assertEquals("This item is already sold.", ex.getMessage());
		}
	}
}
