package com.example.group.service;

import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.cdimascio.dotenv.Dotenv;

@Service
public class ApiService {
	private final String apiKey;
	private final RestTemplate restTemplate = new RestTemplate();
	private final ObjectMapper mapper = new ObjectMapper();

	public ApiService(Dotenv dotenv) {
		this.apiKey = dotenv.get("API_KEY");
	}

	public String callApi(String base64Image) throws Exception {
		// 1. APIキーを使って完全なURLを作成
		String url = "https://api.imgbb.com/1/upload?expiration=600&key=" + apiKey;

		// 2. フォームデータを作成（"image" フィールドに画像データを追加）
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("image", base64Image);

		// 3. POSTリクエストを送信
		String response = restTemplate.postForObject(url, body, String.class);

		// 4. JSONを解析して display_url を取得
		JsonNode json = mapper.readTree(response);
		return json.path("data").path("display_url").asText();
	}
}