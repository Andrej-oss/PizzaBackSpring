import {Pizza} from './Pizza';

export interface PizzaPage {
  pizzas: Pizza[];
  totalElements: number;
  size: number;
  totalPages: number;
  number: number;
}
