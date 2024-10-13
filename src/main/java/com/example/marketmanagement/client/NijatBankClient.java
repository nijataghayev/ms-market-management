package com.example.marketmanagement.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: nijataghayev
 */

@FeignClient(name = "ms-nijat-bank", url = "http://localhost:8081/payments")
public interface NijatBankClient {

    @PostMapping("/process")
    void processPayment(@RequestParam Long accountId, @RequestParam Double paidAmmount);
}
