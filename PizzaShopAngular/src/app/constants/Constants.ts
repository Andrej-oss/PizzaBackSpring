import {SizePizza} from '../components/models/SizePizza';

export const sizes = [
  {id: 'Small', value: {size: SizePizza.SMALL, diameter: 25}},
  {id: 'Medium', value: {size: SizePizza.MEDIUM, diameter: 30}},
  {id: 'Large', value: {size: SizePizza.LARGE, diameter: 35}},
];
export const options = [
  {name: 'purchase amount descent', value: 'amount, desc'},
  {name: 'purchase amount ascent', value: 'amount, asc'},
  {name: 'purchase date descent', value: 'date, desc'},
  {name: 'purchase date ascent', value: 'date, asc'},
  {name: 'purchase price descent', value: 'price, desc'},
  {name: 'purchase price ascent', value: 'price, asc'},
  {name: 'purchase name descent', value: 'name, desc'},
  {name: 'purchase name ascent', value: 'name, asc'},
];
export const pizzaOptions = [
  {name: 'pizza orders descent', value: 'ordersCount, desc'},
  {name: 'pizza orders ascent', value: 'ordersCount, asc'},
  {name: 'pizza new descent', value: 'newPizza, desc'},
  {name: 'pizza new  ascent', value: 'newPizza, asc'},
  {name: 'pizza price descent', value: 'price, desc'},
  {name: 'pizza price ascent', value: 'price, asc'},
  {name: 'pizza name descent', value: 'name, desc'},
  {name: 'pizza name ascent', value: 'name, asc'},
];
