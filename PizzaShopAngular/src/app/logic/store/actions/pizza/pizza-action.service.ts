import { Injectable } from '@angular/core';
import {Store} from '@ngrx/store';
import {
  CommentDeleteInState, CommentUpdateInState, DessertLoaded, DrinksLoaded,
  IngredientsLoad, PizzaCommentSaveLoad, PizzaCommentsLoad, PizzaDeleteLoaded, PizzaSaveLoaded, PizzaSizesLoaded,
  PizzasLoad, PromotionsLoaded, RatingLoad,
  SizePizzaLoad, SnacksLoaded
} from '../../actions-type/pizzaAction';
import {ThemeObjectService} from '../../../theme-object/theme-object.service';
import {Rating} from '../../../../components/models/Rating';
import {RatingService} from '../../../services/ratingDao/rating.service';
import {Comment} from '../../../../components/models/Comment';
import {CommentService} from '../../../services/commentDao/comment.service';
import {PizzaService} from '../../../services/pizzaDao/pizza.service';
import {IngredientService} from '../../../services/ingredientDao/ingredient.service';
import {SizeService} from '../../../services/sizeDao/size.service';
import {PromotionService} from '../../../services/PromotionDao/promotion.service';
import {DrinkService} from '../../../services/drinkDao/drink.service';
import {SnackService} from '../../../services/snackDao/snack.service';
import {DessertService} from '../../../services/dessertDao/dessert.service';

@Injectable({
  providedIn: 'root'
})
export class PizzaActionService {

  constructor(private pizzaService: PizzaService,
              private ingredientService: IngredientService,
              private sizeService: SizeService,
              private themeService: ThemeObjectService,
              private ratingService: RatingService,
              private promotionService: PromotionService,
              private commentService: CommentService,
              private drinkService: DrinkService,
              private snackService: SnackService,
              private dessertService: DessertService,
              private store: Store) { }
  getAllPizzas(): | {}{
    return this.pizzaService.getAllPizza()
      .subscribe(data => {
        this.store.dispatch(new PizzasLoad(data));
      });
  }
  getSortedPizzas(page: number, type: string, sort: string): |{}{
    return this.pizzaService.getSortedPizzas(page, type, sort)
      .subscribe(data => {
        this.themeService.data.value.sort = sort;
        this.themeService.data.value.firstPage = data.number + 1;
        this.themeService.data.value.lastPage = data.totalPages;
        this.themeService.data.value.type = type;
        this.store.dispatch(new PizzasLoad(data.pizzas));
      });
  }
  savePizza(formData: FormData, append: void): | {}{
    return this.pizzaService.savePizza(formData, append)
      .subscribe(data => this.store.dispatch(new PizzasLoad(data)));
  }
  updatePizza(id: number, formData: FormData, append: void): |{}{
    return this.pizzaService.upDatePizza(id, formData, append)
      .subscribe(data => this.store.dispatch(new PizzaSaveLoaded(data)));
  }
  deletePizza(id: number): | {}{
    return this.pizzaService.deletePizza(id)
      .subscribe(data => this.store.dispatch(new PizzaDeleteLoaded(data)));
  }
  saveIngredient(formData: FormData, append: void): | {}{
    return this.ingredientService.saveIngredient(formData, append)
      .subscribe(data => this.store.dispatch(new IngredientsLoad(data)));
  }
  getIngredients(): | {}{
    return this.ingredientService.getAllIngredients()
      .subscribe(data => {
        this.store.dispatch(new IngredientsLoad(data));
      });
  }
  updateAndGetIngredients(id: number, formData: FormData, append: void): | {}{
    return this.ingredientService.upDateIngredient(id, formData, append)
      .subscribe(data => this.store.dispatch(new IngredientsLoad(data)));
  }
  deleteIngredient(id: number): | {}{
    return this.ingredientService.deleteIngredient(id)
      .subscribe(data => this.store.dispatch(new IngredientsLoad(data)));
  }
  getSizePizza(id: number, name: string): | {}{
    return this.sizeService.getPizzaSize(id, name)
      .subscribe(data => {
        this.themeService.data.value.price = data.price;
        this.store.dispatch(new SizePizzaLoad(data));
      });
  }
  getSizesPizza(id: number): | {}{
    return this.sizeService.getPizzaSizes(id)
      .subscribe(data => this.store.dispatch(new PizzaSizesLoaded(data)));
  }
  upgradePizzaSize(id: number, formData: FormData, append: void): | {}{
    return this.sizeService.updatePizzaSize(id, formData, append)
      .subscribe(data => this.store.dispatch(new PizzaSizesLoaded(data)));
  }
  deletePizzaSize(id: number): | {}{
    return this.sizeService.deletePizzaSize(id)
      .subscribe(data => this.store.dispatch(new PizzaSizesLoaded(data)));
  }
  postRating(pizzaId: number, rating: Rating): | {}{
    return this.ratingService.saveRating(rating, pizzaId).subscribe(data => {
      this.store.dispatch(new RatingLoad(data));
    });
  }
  postComment(pizzaId: number, userId: number, comment: Comment): | {}{
    return this.commentService.saveComment(pizzaId, userId, comment)
      .subscribe(data => this.store.dispatch(new PizzaCommentSaveLoad(data)));
  }
  getPizzaComments(pizzaId: number): | {}{
    return this.commentService.getComments(pizzaId)
      .subscribe(data => this.store.dispatch(new PizzaCommentsLoad(data)));
  }
  deleteCommentPizza(id: number): void{
    this.store.dispatch(new CommentDeleteInState({id}));
  }
  updateComment(id: number, comment: Comment): void{
    comment.id = id;
    this.store.dispatch(new CommentUpdateInState({id, comment}));
  }
  savePromotion(promotion: FormData, append: void): | {}{
    return this.promotionService.savePromotion(promotion, append)
      .subscribe(data => this.store.dispatch(new PromotionsLoaded(data)));
  }
  getPromotions(): | {}{
    return this.promotionService.getAllPromotions()
      .subscribe(data => this.store.dispatch(new PromotionsLoaded(data)));
  }
  deletePromotion(id: number): | {}{
    return this.promotionService.deletePromotion(id)
      .subscribe(data => this.store.dispatch(new PromotionsLoaded(data)));
  }
  updatePromotion(id: number, formData: FormData): | {}{
    return this.promotionService.updatePromotion(id, formData)
      .subscribe(data => this.store.dispatch(new PromotionsLoaded(data)));
  }
  saveDrink(formData: FormData, append: void): | {}{
    return this.drinkService.saveDrink(formData, append)
      .subscribe(data => this.store.dispatch(new DrinksLoaded(data)));
  }
  getDrinks(): | {}{
    return this.drinkService.getAllDrinks()
      .subscribe(data => this.store.dispatch(new DrinksLoaded(data)));
  }
  deleteDrink(id: number): | {}{
    return this.drinkService.deleteDrink(id)
      .subscribe(data => this.store.dispatch(new DrinksLoaded(data)));
  }
  updateDrink(id: number, formData: FormData, append: void): | {}{
    return this.drinkService.updateDrink(id, formData)
      .subscribe(data => this.store.dispatch(new DrinksLoaded(data)));
  }
  saveSnack(formData: FormData, append: void): | {}{
    return this.snackService.saveSnack(formData, append)
      .subscribe(data => this.store.dispatch(new SnacksLoaded(data)));
  }
  getAllSnacks(): | {}{
    return this.snackService.getAllSnacks()
      .subscribe(data => this.store.dispatch(new SnacksLoaded(data)));
  }
  updateSnack(id: number, formData: FormData, append: void): | {}{
    return this.snackService.updateSnack(id, formData, append)
      .subscribe(data => this.store.dispatch(new SnacksLoaded(data)));
  }
  deleteSnack(id: number): | {}{
    return this.snackService.deleteSnack(id)
      .subscribe(data => this.store.dispatch(new SnacksLoaded(data)));
  }
  saveDessert(formData: FormData, append: void): | {}{
    return this.dessertService.saveDessert(formData, append)
      .subscribe(data => this.store.dispatch(new DessertLoaded(data)));
  }
  getAllDessert(): | {}{
    return this.dessertService.getAllDessert()
      .subscribe(data => this.store.dispatch(new DessertLoaded(data)));
  }
  updateDessert(id: number, formData: FormData, append: void): | {}{
    return this.dessertService.updateDessert(id, formData, append)
      .subscribe(data => this.store.dispatch(new DessertLoaded(data)));
  }
  deleteDessert(id: number): | {}{
    return this.dessertService.deleteDessert(id)
      .subscribe(data => this.store.dispatch(new DessertLoaded(data)));
  }
}

