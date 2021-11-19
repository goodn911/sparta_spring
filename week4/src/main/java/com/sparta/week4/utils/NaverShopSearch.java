package com.sparta.week4.utils;

import com.sparta.week4.models.ItemDto;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class NaverShopSearch {
    public String search(String query) {
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Naver-Client-Id", "oZnbYwQjSMwElkyrlYYD");
        headers.add("X-Naver-Client-Secret", "OnawWPqCo4");
        String body = "";

        HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
        ResponseEntity<String> responseEntity = rest.exchange("https://openapi.naver.com/v1/search/shop.json?query="+query, HttpMethod.GET, requestEntity, String.class);
        HttpStatus httpStatus = responseEntity.getStatusCode();
        int status = httpStatus.value();
        String response = responseEntity.getBody();
        System.out.println("Response status: " + status);
        System.out.println(response);

        return response;
    }
    public List<ItemDto> fromJSONtoItems(String result){
        JSONObject rjson = new JSONObject(result);  //문자열을 json으로 변경

        JSONArray items = rjson.getJSONArray("items"); //rson중 items부분이   array로 되어있어서 풀기위해 다른 jsonarray클래스가 필요함

        List<ItemDto> itemDtoList = new ArrayList<>();
        for (int i=0; i<items.length(); i++) {
            JSONObject itemJson = (JSONObject) items.get(i);
            ItemDto itemDto = new ItemDto(itemJson);
            itemDtoList.add(itemDto);

        }
        return itemDtoList;
    }

//    public static void main(String[] args) {
//        NaverShopSearch naverShopSearch = new NaverShopSearch();
//        String result= naverShopSearch.search("아이맥");                 //처음 만들때만 필요 POST로 컨트롤러에서 받은값을 fromJSONtoItems 에 input할예정
//
//    }
}