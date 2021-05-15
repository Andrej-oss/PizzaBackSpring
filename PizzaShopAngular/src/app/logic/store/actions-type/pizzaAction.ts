import {Action} from '@ngrx/store';
import {Pizza} from '../../../components/models/Pizza';
import {Ingredient} from '../../../components/models/Ingredient';
import {Size} from '../../../components/models/Size';
import {Rating} from '../../../components/models/Rating';
import {Comment} from '../../../components/models/Comment';
import {Promotion} from '../../../components/models/Promotion';
import {Drink} from '../../../components/models/Drink';
import {Snack} from '../../../components/models/Snack';
import {Dessert} from '../../../components/models/Dessert';
import {PizzaPage} from "../../../components/models/PizzaPage";

export enum PizzaActionType {
  pizzaAllLoaded = '[PIZZA] all loaded',
  pizzaSizeLoaded = '[PIZZA] size loaded',
  pizzaIngredientsLoaded = '[INGREDIENTS] pizza loaded',
  pizzaRatingLoaded = '[RATING] pizza loaded',
  pizzaCommentSaveLoaded = '[COMMENT] pizza saved & loaded',
  pizzaCommentsLoaded = '[COMMENT] pizza by id loaded',
  pizzaCommentDeleteInState = '[Comment] deleted',
  pizzaCommentUpdateInState = '[Comment] pizza update',
  pizzaSaveAndLoaded = '[PIZZA] updated and loaded',
  pizzaDeleteAndLoaded = '[PIZZA] deleted and loaded',
  pizzaSizesLoaded = '[PIZZA] sizes loaded',
  promotionsLoaded = '[PROMOTIONS] loaded',
  drinksLoaded = '[DRINKS] loaded',
  snacksLoaded = '[SNACKS] loaded',
  dessertLoaded = '[DESSERT] loaded',
}

export class PizzasLoad implements Action {
  readonly type = PizzaActionType.pizzaAllLoaded;

  constructor(public payload: Pizza[] | PizzaPage) {
  }
}

export class IngredientsLoad implements Action {
  readonly type = PizzaActionType.pizzaIngredientsLoaded;

  constructor(public payload: Ingredient[]) {
  }
}

export class SizePizzaLoad implements Action {
  readonly type = PizzaActionType.pizzaSizeLoaded;

  constructor(public payload: Size) {
  }
}

export class RatingLoad implements Action {
  readonly type = PizzaActionType.pizzaRatingLoaded;

  constructor(public payload: Rating[]) {
  }
}

export class PizzaCommentSaveLoad implements Action {
  readonly type = PizzaActionType.pizzaCommentSaveLoaded;

  constructor(public payload: Comment[]) {
  }
}
export class PizzaCommentsLoad  implements Action{
  readonly type = PizzaActionType.pizzaCommentsLoaded;

  constructor(public payload: Comment[]) {
  }
}
export class CommentDeleteInState implements Action{
  readonly type = PizzaActionType.pizzaCommentDeleteInState;

  constructor(public payload: {id: number}) {
  }
}
export class CommentUpdateInState implements Action{
  readonly type = PizzaActionType.pizzaCommentUpdateInState;

  constructor(public payload: {id: number, comment: Comment}) {
  }
}
export class PizzaSaveLoaded implements Action{
  readonly type = PizzaActionType.pizzaSaveAndLoaded;

  constructor(public payload: Pizza[]) {
  }
}
export class PizzaDeleteLoaded implements Action{
  readonly type = PizzaActionType.pizzaDeleteAndLoaded;
  constructor(public payload: Pizza[]) {
  }
}
export class PizzaSizesLoaded implements Action{
  readonly type = PizzaActionType.pizzaSizesLoaded;
  constructor(public payload: Size[]) {
  }
}
export class PromotionsLoaded implements Action{
  readonly type = PizzaActionType.promotionsLoaded;
  constructor(public payload: Promotion[]) {
  }
}
export class DrinksLoaded implements Action{
  readonly type = PizzaActionType.drinksLoaded;
  constructor(public payload: Drink[]) {
  }
}
export class SnacksLoaded implements Action{
  readonly type = PizzaActionType.snacksLoaded;
  constructor(public payload: Snack[]) {
  }
}
export class DessertLoaded implements Action{
  readonly type = PizzaActionType.dessertLoaded;
  constructor(public payload: Dessert[]) {
  }
}
export type PizzaAction = PizzasLoad
  | IngredientsLoad
  | SizePizzaLoad
  | RatingLoad
  | PizzaCommentSaveLoad
  | PizzaCommentsLoad
  | CommentDeleteInState
  | CommentUpdateInState
  | PizzaSaveLoaded
  | PizzaDeleteLoaded
  | PizzaSizesLoaded
  | PromotionsLoaded
  | DrinksLoaded
  | SnacksLoaded
  | DessertLoaded;
