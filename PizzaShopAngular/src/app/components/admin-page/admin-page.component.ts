import {Component, OnInit} from '@angular/core';
import {User} from '../models/User';
import {UserActionsService} from '../../logic/store/actions/user/user-actions.service';
import {select, Store} from '@ngrx/store';
import {Observable} from 'rxjs';
import {selectPrincipal, selectUserAvatar, selectUsers} from '../../logic/store/selectors/UserSelect';
import {UserService} from '../../logic/services/userDao/user.service';
import {ThemeObjectService} from '../../logic/theme-object/theme-object.service';
import {Avatar} from '../models/Avatar';
import {Drink} from '../models/Drink';
import {PizzaActionService} from '../../logic/store/actions/pizza/pizza-action.service';
import {AllPizzasSelector, DrinksSelector} from '../../logic/store/selectors/PizzaSelector';
import {Pizza} from '../models/Pizza';

@Component({
  selector: 'app-admin-page',
  templateUrl: './admin-page.component.html',
  styleUrls: ['./admin-page.component.css']
})
export class AdminPageComponent implements OnInit {
  userFormData: FormData = new FormData();
  image: File;
  users: Observable<User[]> = this.store$.pipe(select(selectUsers));
  admin: User;
  isAdmin = this.userService.isAdmin();
  isOpenPizzaCreator = false;
  isOpenUsersAdministrating = false;
  isOpenUserUpdate = false;
  isPromotionCreator: boolean;
  step = 0;
  isOpenPizzaOptions: boolean;
  isOpenIngredientUpdate: boolean;
  isOpenIngredientCreator: boolean;
  isOpenAllPurchases: boolean;
  isOpenSnacks: boolean;
  avatar: Observable<Avatar> = this.store$.pipe(select(selectUserAvatar));
  drinks: Drink[];
  pizzas: Pizza[];
  avatarUrl = '/api/avatar/image/';
  isAvatarOpen: boolean;
  isUpdateInfoOpen: boolean;
  isPromotionsOpen: boolean;
  isDrinksOpen: boolean;
  isOpenDrinkCreator: boolean;
  isOpenSnackCreator: boolean;
  isDessertOpen: boolean;
  isOpenDessertCreator: boolean;
  constructor(private userService: UserService,
              private pizzaActionService: PizzaActionService,
              private userActionsService: UserActionsService,
              public themeObjectService: ThemeObjectService,
              private store$: Store) {
  }

  ngOnInit(): void {
    this.store$.pipe(select(AllPizzasSelector)).subscribe(data => this.pizzas = data);
    if (this.pizzas.length < 21){
      this.pizzaActionService.getAllPizzas();
    }
    this.userActionsService.getUsers();
    this.userActionsService.getPrincipal(this.userService.getUserName());
    this.store$.pipe(select(selectPrincipal)).subscribe(data => this.admin = data);
    this.store$.pipe(select(DrinksSelector)).subscribe(data => this.drinks = data);
    this.userActionsService.getUserAvatar(this.themeObjectService.data.value.userId);
  }

  onPizzaCreate(): void {
    this.isOpenPizzaCreator = !this.isOpenPizzaCreator;
  }

  setStep(index: number): void {
    this.step = index;
  }

  nextStep(): void {
    this.step++;
  }

  prevStep(): void {
    this.step--;
  }

  onUsersAdmin(): void {
    debugger;
    this.isOpenUsersAdministrating = true;
    this.isOpenPizzaOptions = false;
    this.isOpenUserUpdate = false;
    this.isOpenIngredientUpdate = false;
    this.isOpenAllPurchases = false;
    this.isPromotionsOpen = false;
    this.isDrinksOpen = false;
    this.isOpenSnacks = false;
    this.isDessertOpen = false;
  }

  onUserUpdate(): void {
    this.isOpenUserUpdate = true;
    this.isOpenUsersAdministrating = false;
    this.isOpenPizzaOptions = false;
    this.isOpenIngredientUpdate = false;
    this.isOpenAllPurchases = false;
    this.isPromotionsOpen = false;
    this.isOpenSnacks = false;
    this.isDrinksOpen = false;
    this.isDessertOpen = false;
  }

  onPizzaUpdate(): void {
    this.isOpenPizzaOptions = true;
    this.isOpenUserUpdate = false;
    this.isOpenUsersAdministrating = false;
    this.isOpenIngredientUpdate = false;
    this.isOpenAllPurchases = false;
    this.isPromotionsOpen = false;
    this.isDrinksOpen = false;
    this.isOpenSnacks = false;
    this.isDessertOpen = false;
  }

  onIngredientPage(): void {
    this.isOpenIngredientUpdate = true;
    this.isOpenUserUpdate = false;
    this.isOpenPizzaOptions = false;
    this.isOpenUsersAdministrating = false;
    this.isOpenAllPurchases = false;
    this.isPromotionsOpen = false;
    this.isDrinksOpen = false;
    this.isOpenSnacks = false;
    this.isDessertOpen = false;
  }

  onOpenIngredientCreator(): void {
    this.isOpenIngredientCreator = !this.isOpenIngredientCreator;
  }

  onAllPurchases(): void {
    this.userActionsService.getAllPurchases(0, 'amount', 'desc');
    this.isOpenAllPurchases = true;
    this.isOpenUserUpdate = false;
    this.isOpenPizzaOptions = false;
    this.isOpenUsersAdministrating = false;
    this.isPromotionsOpen = false;
    this.isOpenIngredientUpdate = false;
    this.isDrinksOpen = false;
    this.isOpenSnacks = false;
    this.isDessertOpen = false;
  }

  uploadFile($event: Event): void {
    this.image = ($event.target as HTMLInputElement).files[0];
  }

  onSaveAvatar(id: number): void {
    this.userActionsService.saveAvatarUser(id, this.userFormData,
      this.userFormData.append('file', this.image));
    this.resetDataForm();
  }

  resetDataForm(): void {
    this.userFormData.delete('file');
    this.isAvatarOpen = false;
  }

  onOpenAvatarChange(): void {
    this.isAvatarOpen = !this.isAvatarOpen;
  }

  onPromotions(): void {
    this.isPromotionsOpen = true;
    this.isOpenAllPurchases = false;
    this.isOpenUserUpdate = false;
    this.isOpenPizzaOptions = false;
    this.isOpenUsersAdministrating = false;
    this.isOpenIngredientUpdate = false;
    this.isDrinksOpen = false;
    this.isOpenSnacks = false;
    this.isDessertOpen = false;
  }

  onOpenPromoCreator(): void {
    this.isOpenIngredientCreator = !this.isOpenIngredientCreator;
  }

  onDrinks(): void {
    this.isDrinksOpen = true;
    this.isPromotionsOpen = false;
    this.isOpenAllPurchases = false;
    this.isOpenUserUpdate = false;
    this.isOpenPizzaOptions = false;
    this.isOpenUsersAdministrating = false;
    this.isOpenIngredientUpdate = false;
    this.isOpenSnacks = false;
    this.isDessertOpen = false;
  }

  onOpenDrinkCreator(): void {
    this.isOpenDrinkCreator = !this.isOpenDrinkCreator;
  }

  onSnacks(): void {
    this.isOpenSnacks = true;
    this.isDrinksOpen = false;
    this.isPromotionsOpen = false;
    this.isOpenAllPurchases = false;
    this.isOpenUserUpdate = false;
    this.isOpenPizzaOptions = false;
    this.isOpenUsersAdministrating = false;
    this.isOpenIngredientUpdate = false;
    this.isDessertOpen = false;
  }

  onOpenSnackCreator(): void{
    this.isOpenSnackCreator = !this.isOpenSnackCreator;
  }

  onDessert(): void{
    this.isPromotionsOpen = false;
    this.isOpenAllPurchases = false;
    this.isOpenUserUpdate = false;
    this.isOpenPizzaOptions = false;
    this.isOpenUsersAdministrating = false;
    this.isOpenIngredientUpdate = false;
    this.isDrinksOpen = false;
    this.isOpenSnacks = false;
    this.isDessertOpen = true;
  }

  onOpenDessertCreator(): void{
    this.isOpenDessertCreator = !this.isOpenDessertCreator;
  }
}
