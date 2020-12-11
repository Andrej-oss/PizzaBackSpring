package com.pizza_shop.project.controllers;

import com.pizza_shop.project.dto.PaymentIntentDto;
import com.pizza_shop.project.entity.Purchase;
import com.pizza_shop.project.services.IPaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class PaymentController {
    private IPaymentService paymentService;

    @Autowired
    public PaymentController(IPaymentService paymentService) {
        this.paymentService = paymentService;
    }
    @PostMapping("/stripe/payment")
    public ResponseEntity<String> payment(@RequestBody PaymentIntentDto paymentIntentDto){
        String payment = null;
        try {
            final PaymentIntent paymentIntent = paymentService.paymentIntent(paymentIntentDto);
            payment = paymentIntent.toJson();
            log.info("Handling /post in payment method with payment" + paymentIntentDto);
        } catch (StripeException e) {
            log.info("Handling stripe exception in payment method " + e.getMessage());
        }
        return new ResponseEntity<String>(payment, HttpStatus.OK);
    }
    @PostMapping("/stripe/confirm/{id}/{userId}")
    public ResponseEntity<String> confirm(@PathVariable String id,
                                          @PathVariable int userId,
                                          @RequestBody Purchase purchase) throws StripeException{
        final PaymentIntent paymentIntent = paymentService.confirm(id, userId, purchase);
        log.info("Handling /post confirm by id " + id);
        final String payment = paymentIntent.toJson();
        return new ResponseEntity<String>(payment, HttpStatus.OK);
    }
    @PostMapping("/stripe/cancel/{id}")
    public ResponseEntity<String> cancelPayment(@PathVariable String id) throws StripeException{
        final PaymentIntent paymentIntent = paymentService.cancel(id);
        log.info("Handling /post cancel payment by id " + id);
        final String payment = paymentIntent.toJson();
        return new ResponseEntity<String>(payment, HttpStatus.OK);
    }
}
