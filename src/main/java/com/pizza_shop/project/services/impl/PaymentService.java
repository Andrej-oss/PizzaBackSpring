package com.pizza_shop.project.services.impl;

import com.pizza_shop.project.dao.CartDao;
import com.pizza_shop.project.dao.PurchaseDao;
import com.pizza_shop.project.dao.UserDao;
import com.pizza_shop.project.dto.PaymentIntentDto;
import com.pizza_shop.project.entity.Cart;
import com.pizza_shop.project.entity.Purchase;
import com.pizza_shop.project.entity.User;
import com.pizza_shop.project.services.IPaymentService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class PaymentService implements IPaymentService {

    @Value("${stripe.key.secret}")
    String secret;
    @Autowired
    private UserDao userDao;
    @Autowired
    private PurchaseDao purchaseDao;
    @Autowired
    private CartDao cartDao;

    @Override
    public PaymentIntent paymentIntent(PaymentIntentDto paymentIntentDto) throws StripeException {
        Stripe.apiKey = secret;
        final ArrayList<String> paymentMethodsTypes = new ArrayList<>();
        paymentMethodsTypes.add("card");
        final HashMap<String, Object> params = new HashMap<>();
        params.put("currency", paymentIntentDto.getCurrency());
        params.put("amount", paymentIntentDto.getAmount()*10);
        params.put("description", paymentIntentDto.getDescription());
        params.put("payment_method_types", paymentMethodsTypes);
        return PaymentIntent.create(params);
    }

    @Override
    public PaymentIntent confirm(String id, int userId, Purchase purchase) throws StripeException{
        Stripe.apiKey = secret;
        final PaymentIntent paymentIntent = PaymentIntent.retrieve(id);
        final User user = userDao.getOne(userId);
        final HashMap<String, Object> params = new HashMap<>();
        params.put("payment_method", "pm_card_visa");
        paymentIntent.confirm(params);
        purchase.setUser(user);
        purchaseDao.save(purchase);
        return paymentIntent;
    }

    @Override
    public PaymentIntent cancel(String id) throws StripeException{
        Stripe.apiKey = secret;
        final PaymentIntent paymentIntent = PaymentIntent.retrieve(id);
        paymentIntent.cancel();
        return paymentIntent;
    }

    @Override
    public PaymentIntent confirmAllCart(String id, List<Cart> carts, int userId) throws StripeException{
        Stripe.apiKey = secret;
        final PaymentIntent paymentIntent = PaymentIntent.retrieve(id);
        final User user = userDao.getOne(userId);
        final HashMap<String, Object> params = new HashMap<>();
        for (Cart cart : carts) {
            Purchase purchase = new Purchase();
            purchase.setPizzaId(cart.getPizzaId());
            purchase.setPrice(cart.getPrice());
            purchase.setDescription(cart.getDescription());
            purchase.setName(cart.getSize());
            purchase.setUser(user);
            purchase.setAmount(cart.getAmount());
            purchaseDao.save(purchase);
            cartDao.delete(cart);
        }
        params.put("payment_method", "pm_card_visa");
        paymentIntent.confirm(params);
        return paymentIntent;
    }
}
