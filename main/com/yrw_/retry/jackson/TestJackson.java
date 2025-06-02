package com.yrw_.retry.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TestJackson {

    public static void main(String[] args) {

        GetOrderInfosRequestType getOrderInfosRequestType1 = new GetOrderInfosRequestType();
        String s = String.valueOf(getOrderInfosRequestType1.getCode());
        System.out.println(s);

        String string = BigDecimal.ZERO.toString();
        int i = BigDecimal.ZERO.intValue();
        System.out.println(i);
        System.out.println(string);

        Map<String,Object> reqJsonObj = new HashMap<>();
        List<Map<String, Object>> sourceOrderIds = new ArrayList<>();
        Map<String,Object> sourceOrderId = new HashMap<>();
        sourceOrderId.put("sourceOrderId", "1132567045024556");
        sourceOrderId.put("sourceOrderType", 1);
        sourceOrderIds.add(sourceOrderId);

        reqJsonObj.put("sourceOrderIds", sourceOrderIds);
        reqJsonObj.put("needSourceOrderBasicInfo", true);
        reqJsonObj.put("needChildOrderBasicInfo", true);
        reqJsonObj.put("needChildOrderVisaInfo", true);
        reqJsonObj.put("needChildOrderClientInfo", true);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonString = objectMapper.writeValueAsString(reqJsonObj);
            System.out.println(jsonString);
            GetOrderInfosRequestType getOrderInfosRequestType = objectMapper.readValue(jsonString, GetOrderInfosRequestType.class);
            System.out.println(getOrderInfosRequestType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
