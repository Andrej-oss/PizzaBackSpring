package com.pizza_shop.project.services.impl;

import com.pizza_shop.project.dao.*;
import com.pizza_shop.project.dto.PaymentIntentDto;
import com.pizza_shop.project.entity.*;
import com.pizza_shop.project.services.IPaymentService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.joda.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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
    @Autowired
    private PizzaDao pizzaDao;
    @Autowired
    private DrinkDao drinkDao;
    @Autowired
    private SnackDao snackDao;
    @Autowired
    private DessertDao dessertDao;

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
        Pizza pizza = null;
        Drink drink = null;
        Snack snack = null;
        Dessert dessert = null;
        final User user = userDao.getOne(userId);
        if (purchase.getPizzaId() != 0){
            pizza = pizzaDao.getOne(purchase.getPizzaId());
        }
        else if (purchase.getDrinkId() != 0 ){
            drink = drinkDao.getOne(purchase.getDrinkId());
        }
        else if (purchase.getSnackId() != 0){
            snack = snackDao.getOne(purchase.getSnackId());
        }
        else if (purchase.getDessertId() != 0){
            dessert = dessertDao.getOne(purchase.getDessertId());
        }
        final HashMap<String, Object> params = new HashMap<>();
        if (user != null  && paymentIntent != null) {
            params.put("payment_method", "pm_card_visa");
            paymentIntent.confirm(params);
            purchase.setUser(user);
            if (pizza != null){
                pizza.setOrdersCount((pizza.getOrdersCount() | 0)+ 1);
            }
            else if (drink != null){
                drink.setOrdersCount((drink.getOrdersCount() | 0) + 1);
            }
            else if (snack != null){
                snack.setOrdersCount((snack.getOrdersCount() | 0) + 1);
            }
            else if (dessert != null){
                dessert.setOrdersCount((dessert.getOrdersCount() | 0) + 1);
            }
            final Instant now = Instant.now();
            purchase.setAmount((purchase.getAmount() | 0)+ 1);
            purchase.setDate(now.getMillis());
            purchaseDao.save(purchase);
            return paymentIntent;
        }
        return paymentIntent.cancel();
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
            Pizza pizza = null;
            Drink drink = null;
            Snack snack = null;
            Dessert dessert = null;
            if (cart.getPizzaId() != 0){
                pizza = pizzaDao.getOne(cart.getPizzaId());
            }
            else if (cart.getDrinkId() != 0 ){
                drink = drinkDao.getOne(cart.getDrinkId());
            }
            else if (cart.getSnackId() != 0){
                snack = snackDao.getOne(cart.getSnackId());
            }
            else if (cart.getDessertId() != 0){
                dessert = dessertDao.getOne(cart.getDessertId());
            }
            if (pizza != null){
                purchase.setPizzaId(cart.getPizzaId());
                pizza.setOrdersCount(pizza.getOrdersCount() + cart.getAmount());
            }
            else if (drink != null){
                drink.setOrdersCount(drink.getOrdersCount() + cart.getAmount());
                purchase.setDrinkId(cart.getDrinkId());
            }
            else if (snack != null){
                snack.setOrdersCount(snack.getOrdersCount() + cart.getAmount());
                purchase.setSnackId(cart.getSnackId());
            }
            else if (dessert != null){
                dessert.setOrdersCount(dessert.getOrdersCount() + cart.getAmount());
                purchase.setDessertId(cart.getDessertId());
            }
            purchase.setPrice(cart.getPrice());
            purchase.setDescription(cart.getDescription());
            purchase.setName(cart.getSize());
            purchase.setUser(user);
            purchase.setDate(System.currentTimeMillis());
            purchase.setAmount(cart.getAmount());
            purchaseDao.save(purchase);
            cartDao.delete(cart);
        }
        params.put("payment_method", "pm_card_visa");
        paymentIntent.confirm(params);
        return paymentIntent;
    }
}
