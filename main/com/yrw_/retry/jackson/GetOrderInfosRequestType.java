package com.yrw_.retry.jackson;

import lombok.Data;

import java.util.List;

@Data
public class GetOrderInfosRequestType {

        public Integer code;

        public MobileRequestHead head;

        public List<RequestSourceOrderId> sourceOrderIds;

        public Boolean needSourceOrderBasicInfo;

        public Boolean needChildOrderBasicInfo;
        public Boolean needChildOrderVisaInfo;
        public Boolean needChildOrderClientInfo;
}

