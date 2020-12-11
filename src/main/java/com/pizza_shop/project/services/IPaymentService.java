package com.pizza_shop.project.services;

import com.pizza_shop.project.dto.PaymentIntentDto;
import com.pizza_shop.project.entity.Purchase;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

public interface IPaymentService {
    PaymentIntent paymentIntent(PaymentIntentDto paymentIntentDto) throws StripeException;
    PaymentIntent confirm(String id, int userId, Purchase purchase) throws StripeException;
    PaymentIntent cancel(String id) throws StripeException;
}
