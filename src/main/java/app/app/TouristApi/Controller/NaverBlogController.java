package app.app.TouristApi.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Controller
public class NaverBlogController {

    private static final String CLIENT_ID = "w0SVynZeCsheFkCZMaD3"; // 네이버 API Client ID
    private static final String CLIENT_SECRET = "m69Yy4YomV"; // 네이버 API Client Secret

    @GetMapping("/api/naver-search-blog")
    public ResponseEntity<String> searchBlog(@RequestParam String query) {
        try {
            String encodedQuery = URLEncoder.encode(query, "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/search/blog?query=" + encodedQuery;
            // 헤더 설정
            Map<String, String> requestHeaders = new HashMap<>();
            requestHeaders.put("X-Naver-Client-Id", CLIENT_ID);
            requestHeaders.put("X-Naver-Client-Secret", CLIENT_SECRET);

            // API 호출 및 응답 가져오기
            String responseBody = get(apiURL, requestHeaders);

            // 성공적으로 데이터를 가져왔으면 200 OK와 함께 응답 본문 반환
            return ResponseEntity.ok(responseBody);

        } catch (Exception e) {
            // 에러 발생 시 500 에러 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("API 요청 중 오류가 발생했습니다.");
        }
    }

    // API 호출을 위한 GET 메서드
    private String get(String apiUrl, Map<String, String> requestHeaders) throws IOException {
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return readBody(con.getInputStream());
            } else {
                return readBody(con.getErrorStream());
            }
        } finally {
            con.disconnect();
        }
    }

    // API URL에 연결
    private HttpURLConnection connect(String apiUrl) throws IOException {
        URL url = new URL(apiUrl);
        return (HttpURLConnection) url.openConnection();
    }

    // API 응답 본문 읽기
    private String readBody(InputStream body) throws IOException {
        InputStreamReader streamReader = new InputStreamReader(body);
        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();
            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }
            return responseBody.toString();
        }
    }
}
