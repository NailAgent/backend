package com.nailagent.backend.domain.payment.controller;

import com.nailagent.backend.domain.payment.service.PaymentService;
import com.nailagent.backend.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PaymentPageController {

    @Value("${toss.client-key}")
    private String clientKey;

    private final PaymentService paymentService;

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
        try {
            paymentService.confirmPayment(paymentKey, orderId, amount);
        } catch (CustomException e) {
            log.error("Toss confirm 실패: paymentKey={}, orderId={}, error={}", paymentKey, orderId, e.getMessage());
            model.addAttribute("errorCode", e.getErrorCode().getCode());
            model.addAttribute("errorMessage", e.getErrorCode().getMessage());
            return "payment-fail";
        }
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
