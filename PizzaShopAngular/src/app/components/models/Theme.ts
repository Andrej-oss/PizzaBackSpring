export interface Theme {
  isDarkTheme: boolean;
  isAuthLoad: boolean;
  idChoosePizza: number;
  idChooseDrink: number;
  idChooseDessert: number;
  idChooseSnack: number;
  ingredients: number[];
  price: number;
  userName: string;
  userId: number;
  message: string;
  sizeCart: number;
  totalPrice: number;
  isOpenPayment: boolean;
  lastPage: number;
  firstPage: number;
  sort: string;
  type: string;
}
