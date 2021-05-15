import {Pizza} from './Pizza';
import {Ingredient} from './Ingredient';
import {Size} from './Size';
import {Rating} from './Rating';
import {Comment} from './Comment';
import {Promotion} from './Promotion';
import {Drink} from './Drink';
import {Snack} from './Snack';
import {Dessert} from "./Dessert";

export interface DefaultPizzaState {
  pizzas: Pizza[];
  ingredients: Ingredient[];
  size: Size;
  rating: Rating;
  comments: Comment[];
  sizes: Size[];
  promotions: Promotion[];
  drinks: Drink[];
  snacks: Snack[];
  desserts: Dessert[];
}

export const InitialPizzaState: DefaultPizzaState = {
  pizzas: [],
  ingredients: [],
  size: null,
  rating: null,
  comments: [],
  sizes: [],
  promotions: [],
  drinks: [],
  snacks: [],
  desserts: [],
};

