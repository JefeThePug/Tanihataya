-- こちらを使用して、共有データベースにアクセスできない場合は、
-- ご自身のデータベースにテスト用データを挿入してください。
-- また、application.properties 内の情報も必ず更新してください。

-- Users
INSERT INTO public.users VALUES (3, 'SELLER_A', 'seller_a@tanihataya.com', '$2a$10$4XZ.QUuh7Li0NJpJ9/eKLeZyvjDLMJVkLFnWM3U/jDlpYIS8/XKJu', '2134545', '大阪市港区なんちゃら1-9-1', '08015648987', true, '2025-10-03 10:42:32.674146', '2025-10-03 10:42:32.674146');
INSERT INTO public.users VALUES (4, 'SELLER_B', 'seller_b@tanihataya.com', '$2a$10$ap8COg1ibXsqkjzEKK6jEuGUoq2F4Xc/UUPBm9yGsNnmTYZH5Hoz2', '1245125', '東京都新宿区西新宿２丁目８−１５', '09045679845', true, '2025-10-03 10:44:09.628499', '2025-10-03 10:44:09.628499');
INSERT INTO public.users VALUES (5, 'テスト', 'aaba@example.com', '$2a$10$WuPyipLw8h4ihaOLj9JiZ.kLD/rlZIuGfNHQYsF/7hWLDPaV5OYRi', '', '', '', true, '2025-10-03 10:45:09.953169', '2025-10-03 10:45:09.953169');
INSERT INTO public.users VALUES (6, 'SELLER_C', 'seller_c@tanihataya.com', '$2a$10$WoGl0bYU.R6VlfeSLaFuMO7RA2nLly8EQfm1Mh5RR2VBWJzLdlBX6', '3154684', '沖縄県那覇市久茂地３丁目１２−７', '08065496548', true, '2025-10-03 10:45:17.863468', '2025-10-03 10:45:17.863468');
INSERT INTO public.users VALUES (7, 'テストｄ', 'ddd@tes', '$2a$10$gEiWEs4x/DwS9ltDQItlg./7j7/DJL.7ohvEQHlGSDC7HXkoZOrGi', '', '', '', true, '2025-10-03 10:56:06.713411', '2025-10-03 10:56:06.713411');
INSERT INTO public.users VALUES (8, 'テストｄ', 'yamadda@example.com', '$2a$10$1TO7pnSvzQTbdO1s7zOdRuahfkGiikacgltwtbg7DqGRQf2CRlVLO', '', '', '', true, '2025-10-03 10:58:55.993159', '2025-10-03 10:58:55.993159');
INSERT INTO public.users VALUES (9, 'テスト太郎', 'testtarou@admin.com', '$2a$10$NpIyyi93UxwRoqvbR2qlL.tS2fJr99IG0gIFy/0KkjW3y10XbSDcO', '', '', '', true, '2025-10-08 14:41:33.794625', '2025-10-08 14:41:33.794625');
INSERT INTO public.users VALUES (1, 'admin', 'admin@admin.com', '$2a$10$PQh7iwd5s0d4dMNvZmg.Q.p3SO6mpYvvkzwhwly8qJlxPkyuHrKYy', '1234567', '大阪府', '08012345678', true, '2025-09-30 10:34:17.576075', '2025-10-10 15:18:36.988363');
INSERT INTO public.users VALUES (10, '登録花子', 'aaab@example.com', '$2a$10$JS7mbXNejeKMYxwpc50Cbup5zuyxGz6Wj5bFeEMQBpwiXY4YWxMFm', '1234567', '東京都', '', true, '2025-10-09 09:18:48.244767', '2025-10-09 09:19:10.258371');
INSERT INTO public.users VALUES (11, '登録太郎', 'ccc@admin.com', '$2a$10$YhRGGPGMrsgRvzG5gBwru.lDPLes2aKmjENlVZuRK8amaIfoCPZuS', '1234567', '東京都渋谷区2-2', '08012345678', true, '2025-10-10 09:21:37.871669', '2025-10-10 09:23:58.13148');
INSERT INTO public.users VALUES (2, '登録花子', 'aaa@example.com', '$2a$10$kWqKDZs3vKxuaylWk//96eqcDkDLoocOZ41AUuKZO/Jq1kDPHzY9W', '1234567', '東京都渋谷区2-2', '', false, '2025-10-03 10:22:12.079927', '2025-10-10 09:40:09.345636');

-- Items
INSERT INTO public.items VALUES (6, 6, 'ラジコンカー', 2, 'リモコン付き、動作確認済み。タイヤに小さな傷あり。', 1800, true, NULL, 'car1.jpg,car2.jpg,car3.jpg', '2025-10-03 00:00:00', '2025-10-10 09:49:47.911464', NULL);
INSERT INTO public.items VALUES (24, 6, '自転車用ライト', 8, 'USB充電式。ジャンク品。ライトが点灯しません。', 20, true, NULL, 'lite1.jpg,lite2.jpg,lite3.jpg', '2025-10-03 00:00:00', '2025-10-10 09:58:04.753108', NULL);
INSERT INTO public.items VALUES (21, 6, '英和辞典', 7, '中古、書き込みなし。', 700, true, NULL, 'dict1.jpg,dict2.jpg', '2025-10-03 00:00:00', '2025-10-10 10:01:14.905634', NULL);
INSERT INTO public.items VALUES (13, 3, '猫用キャリーバッグ', 5, '2回使用、美品。', 2500, true, NULL, 'carry1.jpg,carry2.jpg,carry3.jpg', '2025-10-03 00:00:00', '2025-10-10 09:49:17.331363', NULL);
INSERT INTO public.items VALUES (8, 4, 'ノートパソコン Lenovo', 3, 'Core i5、メモリ8GB。中古、バッテリー持ちはやや短め。', 25000, false, 1, 'computer1.png', '2025-10-03 00:00:00', '2025-10-10 15:18:36.268103', '2025-10-10 15:18:35.673413');
INSERT INTO public.items VALUES (19, 3, '小説「架空の街」', 7, '文庫本、ページに日焼けあり。', 400, true, NULL, 'novel1.jpg,novel2.jpg', '2025-10-03 00:00:00', '2025-10-07 13:11:39.28605', NULL);
INSERT INTO public.items VALUES (27, 1, 'ドッグ リード', 5, '中古品。多少の傷や汚れあり。', 750, true, NULL, '177ee55c-3699-421a-ab24-c3937935f546_lead1.jpg,6e173240-d542-418c-8a6b-48de6dd6f13c_lead3.jpg,0099fe54-701f-40c0-9439-1e7660774ddb_lead2.jpg', '2025-10-06 10:40:54.813909', '2025-10-08 09:21:33.577876', NULL);
INSERT INTO public.items VALUES (10, 3, 'サッカーボール 5号', 4, '使用回数少なめ。軽い汚れあり。', 1000, true, NULL, 'soccer1.jpg', '2025-10-03 00:00:00', '2025-10-03 00:00:00', NULL);
INSERT INTO public.items VALUES (25, 1, 'サーモス 水筒 500ml', 8, 'ステンレス製、真空断熱タイプ。2〜3回使用のみで美品。専用ケース付き。', 1800, true, NULL, 'thermos1.jpg,thermos2.jpg', '2025-10-03 00:00:00', '2025-10-08 09:21:43.802445', NULL);
INSERT INTO public.items VALUES (2, 4, 'レディーススカート', 1, '花柄、サイズM。ウエスト部分に少し使用感あり。', 1200, false, 10, 'skirt1.jpg,skirt2.jpg,skirt3.jpg', '2025-10-03 00:00:00', '2025-10-09 09:19:10.133384', '2025-10-09 09:19:11.817126');
INSERT INTO public.items VALUES (5, 4, 'ぬいぐるみ（クマ）', 2, '高さ40cm。耳に少し汚れあり。', 500, true, NULL, 'bear1.jpg,bear2.jpg,bear3.jpg,bear4.jpg', '2025-10-03 00:00:00', '2025-10-08 15:12:54.495402', NULL);
INSERT INTO public.items VALUES (23, 4, 'キッチンタイマー', 8, '動作確認済み。小さな傷あり。', 300, true, NULL, 'timer1.jpg', '2025-10-03 00:00:00', '2025-10-09 15:28:47.275636', NULL);
INSERT INTO public.items VALUES (9, 6, 'ワイヤレスイヤホン', 3, '未使用、充電ケース付き。', 4500, true, NULL, 'earbuds1.jpg,earbuds2.jpg', '2025-10-03 00:00:00', '2025-10-08 11:10:52.445001', NULL);
INSERT INTO public.items VALUES (7, 3, 'iPhone 12 (64GB)', 3, 'SIMフリー、本体のみ。画面に小さな傷あり。', 32000, true, NULL, 'phone1.jpg,phone2.jpg,phone3.jpg,phone4.jpg,phone5.jpg', '2025-10-03 00:00:00', '2025-10-08 11:15:33.764863', NULL);
INSERT INTO public.items VALUES (20, 4, '漫画セット（1〜5巻）', 7, '全巻揃い、カバーにスレあり。', 1800, true, NULL, 'manga1.jpg', '2025-10-03 00:00:00', '2025-10-10 10:00:04.506297', NULL);
INSERT INTO public.items VALUES (14, 4, '2回使用、美品。', 5, '直径60cm、洗濯済み。使用感あり。', 1200, true, NULL, 'bed1.jpg,bed2.jpg', '2025-10-03 00:00:00', '2025-10-10 10:03:31.029915', NULL);
INSERT INTO public.items VALUES (18, 6, 'メイクブラシセット', 6, '5本入り。数回使用、洗浄済み。', 600, true, NULL, 'brush1.jpg,brush2.jpg,brush3.jpg', '2025-10-03 00:00:00', '2025-10-07 15:14:36.498057', NULL);
INSERT INTO public.items VALUES (1, 3, 'メンズジャケット', 1, 'サイズL、黒色。数回使用のみでほぼ新品。', 3500, true, NULL, 'jacket1.jpg,jacket2.jpg', '2025-10-03 00:00:00', '2025-10-08 14:54:14.33453', NULL);
INSERT INTO public.items VALUES (29, 1, 'Eclipseツアー2022 バンドTシャツ', 1, '2022.9.11,12に大阪、東京のライヴ会場で販売したスウェーデンのバンドEclipseのツアーTシャツです。
生地はユナイテッドアスレの5.6 オンス　5001-01です。Sサイズです。
返品、交換は受け付けることができません。
ご了承ください。
肩幅41cm 身幅49cm着丈61cm', 4000, false, 11, 'd3b15da8-073a-4943-a521-747c965f973e_tshirt1.jpg,14b5f95d-00da-476c-86e5-0a31fa2035bd_tshirt2.jpg', '2025-10-07 13:26:36.198868', '2025-10-10 09:23:19.103521', '2025-10-10 09:23:18.576547');
INSERT INTO public.items VALUES (4, 3, 'レゴブロック 300ピース', 2, '箱なし、中古。ピースはすべて揃っています。', 2000, false, 11, 'lego1.jpg,lego2.jpg', '2025-10-03 00:00:00', '2025-10-10 09:23:57.948853', '2025-10-10 09:23:57.431475');
INSERT INTO public.items VALUES (3, 6, 'キッズTシャツセット', 1, '100cmサイズ、3枚セット。色あせあり。', 800, true, NULL, 'kids1.jpg', '2025-10-03 00:00:00', '2025-10-08 14:15:02.434656', NULL);
INSERT INTO public.items VALUES (16, 3, 'ヘアドライヤー', 6, '中古、動作良好。箱なし。', 1500, false, 9, 'dryer1.jpg,dryer2.jpg,dryer3.jpg,dryer4.jpg,dryer5.jpg', '2025-10-03 00:00:00', '2025-10-08 14:42:10.477853', '2025-10-08 14:42:11.873492');
INSERT INTO public.items VALUES (17, 4, 'フェイスマスク 7枚セット', 6, '新品・未開封。保湿タイプ。', 900, false, 2, 'mask1.jpg', '2025-10-03 00:00:00', '2025-10-10 09:40:09.251878', '2025-10-10 09:40:08.871745');
INSERT INTO public.items VALUES (12, 6, 'ヨガマット', 4, '厚さ10mm、青色。端に擦れ跡あり。', 900, true, NULL, 'yoga1.jpg,yoga2.jpg,yoga3.jpg,yoga4.jpg', '2025-10-03 00:00:00', '2025-10-09 14:26:27.979572', NULL);
INSERT INTO public.items VALUES (22, 3, '折りたたみ傘', 8, '赤色、未使用。ケース付き。', 500, true, NULL, 'umbrella1.jpg,umbrella2.jpg,umbrella3.jpg,umbrella4.jpg,umbrella5.jpg', '2025-10-03 00:00:00', '2025-10-08 14:52:44.80358', NULL);
INSERT INTO public.items VALUES (11, 4, 'テニスラケット', 4, '中古。グリップテープは交換したほうが良いです。', 2200, true, NULL, 'tennis1.jpg,tennis2.jpg,tennis3.jpg', '2025-10-03 00:00:00', '2025-10-10 09:37:33.171612', NULL);
INSERT INTO public.items VALUES (15, 6, '小動物用ケージ', 5, 'ハムスター用。水飲みボトル付き。', 1800, true, NULL, 'cage1.jpg', '2025-10-03 00:00:00', '2025-10-10 09:40:57.757383', NULL);

-- Payments
INSERT INTO public.payments VALUES (12, 2, '41111111111112222', 'hanako', '123', '2025-10-08');
INSERT INTO public.payments VALUES (13, 9, '4111111111111111', 'tarou', '123', '2025-10-08');
INSERT INTO public.payments VALUES (14, 11, '411111111111', 'admin', '123', '2025-10-10');
INSERT INTO public.payments VALUES (11, 1, '4123415551234567', 'admin', '555', '2015-12-31');
