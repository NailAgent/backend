package com.nailagent.backend.domain.payment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class PaymentPageController {

    @Value("${toss.client-key}")
    private String clientKey;

    @GetMapping("/payment")
    public String paymentPage(
            @RequestParam String orderId,
            @RequestParam Integer amount,
            @RequestParam String orderName,
            @RequestParam String customerName,
            Model model
    ) {
        model.addAttribute("clientKey", clientKey);
        model.addAttribute("orderId", orderId);
        model.addAttribute("amount", amount);
        model.addAttribute("orderName", orderName);
        model.addAttribute("customerName", customerName);
        return "payment";
    }

    @GetMapping("/payment/success")
    public String paymentSuccess(
            @RequestParam String paymentKey,
            @RequestParam String orderId,
            @RequestParam Integer amount,
            Model model
    ) {
        model.addAttribute("paymentKey", paymentKey);
        model.addAttribute("orderId", orderId);
        model.addAttribute("amount", amount);
        return "payment-success";
    }

    @GetMapping("/payment/fail")
    public String paymentFail(
            @RequestParam String code,
            @RequestParam String message,
            Model model
    ) {
        model.addAttribute("errorCode", code);
        model.addAttribute("errorMessage", message);
        return "payment-fail";
    }
}
