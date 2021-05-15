import { Routes } from '@angular/router';
import {FormIngredientPostingComponent} from '../../components/forms/form-ingredient-posting/form-ingredient-posting.component';
import {FormPizzaPostingComponent} from '../../components/forms/form-pizza-posting/form-pizza-posting.component';
import {FormUserAuthenticationComponent} from '../../components/forms/form-user-authentication/form-user-authentication.component';
import {FormUserRegistrationComponent} from '../../components/forms/form-user-registration/form-user-registration.component';
import {AdminPageComponent} from '../../components/admin-page/admin-page.component';
import {UserPageComponent} from '../../components/user-page/user-page.component';
import {AdminGuardService} from '../guard-url/admin-guard.service/admin-guard.service';
import {UserGuardService} from '../guard-url/user-guard.service/user-guard.service';
import {PizzaPageComponent} from '../../components/pizza/pizza-page/pizza-page.component';
import {PizzaCartPageComponent} from '../../components/pizza/pizza-cart-page/pizza-cart-page.component';
import {DrinkPageComponent} from '../../components/drink/drink-page/drink-page.component';
import {SnackPageComponent} from '../../components/snack/snack-page/snack-page.component';
import {DessertPageComponent} from '../../components/dessert/dessert-page/dessert-page.component';
import {HomePageComponent} from '../../components/home-page/home-page.component';
import {PasswordLoginReminderComponent} from '../../components/forms/password-login-reminder/password-login-reminder.component';

export const routes: Routes = [
  {path: '', component: HomePageComponent},
  {path: 'remind', component: PasswordLoginReminderComponent},
  {path: 'pizza', component: PizzaPageComponent},
  {path: 'ingredient_post', component: FormIngredientPostingComponent},
  {path: 'pizza_post', component: FormPizzaPostingComponent},
  {path: 'authenticate', component: FormUserAuthenticationComponent},
  {path: 'registration', component: FormUserRegistrationComponent},
  {path: 'admin', component: AdminPageComponent, canActivate: [AdminGuardService]},
  {path: 'user_page', component: UserPageComponent, canActivate: [UserGuardService]},
  {path: 'cart', component: PizzaCartPageComponent, canActivate: [UserGuardService]},
  {path: 'drinks', component: DrinkPageComponent},
  {path: 'snacks', component: SnackPageComponent},
  {path: 'desserts', component: DessertPageComponent},
];

