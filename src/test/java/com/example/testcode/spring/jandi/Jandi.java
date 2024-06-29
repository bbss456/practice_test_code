package com.example.testcode.spring.jandi;

import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Jandi {

    @Test
    void jandiTest() {
        // JSON 데이터
        // JSON 문자열
        String json = "{"
                + "\"body\" : \"잔디 웹훅 테스트\","
                + "\"connectColor\" : \"#FAC11B\","
                + "\"connectInfo\" : [{"
                + "\"title\" : \"[여수공항] edge PING 연결장애\","
                + "\"description\" : \"여수공항 ip '10.127.1.123' 연결 장애 \""
                + "}]"
                + "}";

        try {
            // HttpClient 생성
            HttpClient client = HttpClient.newHttpClient();

            // HttpRequest 생성
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("")) // URI 수정 필요
                    .header("Accept", "application/vnd.tosslab.jandi-v2+json")
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            // 요청 보내고 응답 받기
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // 응답 출력
            System.out.println("Response code: " + response.statusCode());
            System.out.println("Response body: " + response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
