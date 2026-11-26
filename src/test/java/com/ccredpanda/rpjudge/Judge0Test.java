package com.ccredpanda.rpjudge;

import com.ccredpanda.rpjudge.common.Judge0Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

// 直接写在单元测试里跑，或者写在某个 Controller 里临时触发
public class Judge0Test {

    @Test
    public void JudgeTest(){
        // 1. 创建 RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // 2. 请求头：Judge0 需要这个头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Auth-Token", "你的token");  // 自建 Judge0 一般不需要，公共 API 需要

        // 3. 请求体
        String code = "public class Main { public static void main(String[] a) { System.out.println(\"hello\"); } }";
        Map<String, Object> body = new HashMap<>();
        body.put("source_code", Base64.getEncoder().encodeToString(code.getBytes()));
        body.put("language_id", 62);        // Java 17
        body.put("stdin", Base64.getEncoder().encodeToString("".getBytes()));
        body.put("expected_output", Base64.getEncoder().encodeToString("hello".getBytes()));
        body.put("cpu_time_limit", 2.0);    // 秒
        body.put("memory_limit", 128000);   // KB

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        // 4. 发请求（wait=true = 同步等结果）
        String url = "http://localhost:2358/submissions?base64_encoded=true&wait=true";
        ResponseEntity<Judge0Response> response = restTemplate.postForEntity(
                url, request, Judge0Response.class);

        // 5. 打印整个响应
        Judge0Response r = response.getBody();
        System.out.println("状态: " + r.getStatus().getId() + " - " + r.getStatus().getDescription());
        System.out.println("耗时: " + r.getTime() + "s");
        System.out.println("内存: " + r.getMemory() + "KB");
        System.out.println("stdout(base64): " + r.getStdout());
        System.out.println("stdout(解码): " + new String(Base64.getDecoder().decode(r.getStdout())));
    }
}
