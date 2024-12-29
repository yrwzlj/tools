package com.yrw_.retry.transaction;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

@Component
@Data
public class TestTransaction {

    private int i1 = 1;

    private int i2 = 2;

    @Transactional
    public void doSomething() {
        i1 = 2;
        throw new RuntimeException();
    }

}
