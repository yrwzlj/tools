package model;

import java.util.List;

/**
 * @Author: rw.yang
 * @DateTime: 2024/12/29
 **/
public class GetOrderInfosRequestType {
    public MobileRequestHead head;

    public List<RequestSourceOrderId> sourceOrderIds;

    public Boolean needSourceOrderBasicInfo;

    public Boolean needChildOrderBasicInfo;
    public Boolean needChildOrderVisaInfo;
    public Boolean needChildOrderClientInfo;

    public class RequestSourceOrderId {
        public Long sourceOrderId;
        public Integer sourceOrderType;
    }

    public class MobileRequestHead {
    }
}

