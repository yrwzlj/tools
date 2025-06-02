package model;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: rw.yang
 * @DateTime: 2024/11/25
 **/
public class PaymentBO {

    List<Integer> list;

    BigDecimal amount;

    public List<Integer> getList() {
        return list;
    }

    public void setList(List<Integer> list) {
        this.list = list;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
