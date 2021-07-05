import {environment} from '../../../environments/environment';



const APi =  environment.ApiV1;
const avatarURL = `${APi}` + 'avatar';
const cartURL = `${APi}` + 'cart';
const commentURL = `${APi}` + 'comment/';
const dessertURL = `${APi}` + 'dessert';
const drinkURL = `${APi}` + 'drink';
const ingredientURL = `${APi}` + 'ingredient';
const paymentURL = `${APi}` + 'stripe/';
const pizzaURL = `${APi}` + 'pizza';
const promotionURL = `${APi}` + 'promotion/';
const purchaseURL = `${APi}` + 'purchase/';
const ratingURL = `${APi}` + 'rating/';
const sizeURL = `${APi}` + 'size';
const snackURL = `${APi}` + 'snack';
const userURL = `${APi}` + 'user';
const voiceURL = `${APi}` + 'voice/';
const pizzaImage = `${APi}` + 'pizza/image/';
const ingredientImage = `${APi}` + 'ingredient/image/';
const dessertImage = `${APi}` + 'dessert/';
const drinkImage = `${APi}` + 'drink/';
const snackImage = `${APi}` + 'snack/';
const avatarImage = `${APi}` + 'avatar/image/';
const pizzaSizeImage = `${APi}size/image/`;
const localStorageKey = 'PizzaShopCart';

export const APiURL = {
    avatarURL,
    cartURL,
    commentURL,
    dessertURL,
    drinkURL,
    ingredientURL,
    paymentURL,
    pizzaURL,
    promotionURL,
    purchaseURL,
    ratingURL,
    sizeURL,
    snackURL,
    userURL,
    voiceURL,
    pizzaImage,
    dessertImage,
    drinkImage,
    snackImage,
    avatarImage,
    pizzaSizeImage,
    localStorageKey,
    ingredientImage
};
