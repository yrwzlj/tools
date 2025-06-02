package com.yrw_.retry.jackson;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestSourceOrderId {

        @JsonProperty("sourceOrderId")
        public Long sourceOrderId;
        @JsonProperty("sourceOrderType")
        public SourceOrderType sourceOrderType;
}