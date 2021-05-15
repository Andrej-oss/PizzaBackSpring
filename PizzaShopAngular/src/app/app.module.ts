import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MaterialModules} from './logic/moduls/MaterialModules';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { FormUserRegistrationComponent } from './components/forms/form-user-registration/form-user-registration.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import { FormUserAuthenticationComponent } from './components/forms/form-user-authentication/form-user-authentication.component';
import {RouterModule} from '@angular/router';
import {TokenInterceptor} from './logic/shared/classes/tokenInterceptor';
import { FormIngredientPostingComponent } from './components/forms/form-ingredient-posting/form-ingredient-posting.component';
import {routes} from './logic/moduls/RouterModule';
import '@angular/compiler';
import {FormPizzaPostingComponent} from './components/forms/form-pizza-posting/form-pizza-posting.component';
import { HeaderComponent } from './components/header/header.component';
import { NavBarComponent } from './components/nav-bar/nav-bar.component';
import { SnackBarComponent } from './components/snack-bar/snack-bar-login/snack-bar.component';
import { AdminPageComponent } from './components/admin-page/admin-page.component';
import { SnackBarRegistrationComponent } from './components/snack-bar/snack-bar-registration/snack-bar-registration.component';
import { FormSizePizzaPostComponent } from './components/forms/form-size-pizza-post/form-size-pizza-post.component';
import { UserCardComponent } from './components/user-card/user-card.component';
import { UserPageComponent } from './components/user-page/user-page.component';
import { StoreModule } from '@ngrx/store';
import { metaReducers, reducers} from './logic/store/redusers';
import { StoreDevtoolsModule } from '@ngrx/store-devtools';
import {environment} from '../environments/environment';
import { PizzaPageComponent } from './components/pizza/pizza-page/pizza-page.component';
import { PizzaCardComponent } from './components/pizza/pizza-card/pizza-card.component';
import {PizzaChooseSheetComponent} from './components/pizza/pizza-choose-sheet/pizza-choose-sheet.component';
import { IngredientCardComponent } from './components/pizza/ingredient-card/ingredient-card.component';
import {NgxStripeModule} from 'ngx-stripe';
import { PaymentComponent } from './components/payment-components-stripe/payment/payment.component';
import { PaymentModalComponent } from './components/payment-components-stripe/payment-modal/payment-modal.component';
import {ToastrModule} from 'ngx-toastr';
import { PizzaCartCardComponent } from './components/pizza/pizza-cart-card/pizza-cart-card.component';
import { PizzaCardCartItemComponent } from './components/pizza/pizza-card-cart-item/pizza-card-cart-item.component';
import { PizzaCartPageComponent } from './components/pizza/pizza-cart-page/pizza-cart-page.component';
import { StarRatingVoteComponent } from './components/star-rating-vote/star-rating-vote.component';
import { StarRatingComponent } from './components/star-rating/star-rating.component';
import { FormCommentComponent } from './components/forms/form-comment/form-comment.component';
import { CommentCardComponent } from './components/comment-card/comment-card.component';
import { PurchaseComponent } from './components/purchase/purchase/purchase.component';
import { PurchasesItemComponent } from './components/purchase/purchases-item/purchases-item.component';
import { PizzaAdminTableComponent } from './components/admin-tables/pizza-admin-table/pizza-admin-table.component';
import { IngredientPipe } from './pipes/ingredient/ingredient.pipe';
import { IngredientAdminPageComponent } from './components/admin-tables/ingredient-admin-page/ingredient-admin-page.component';
import { PizzaSizeAdminTableComponent } from './components/admin-tables/pizza-size-admin-table/pizza-size-admin-table.component';
import { UserAdminTableComponent } from './components/admin-tables/user-admin-table/user-admin-table.component';
import { CommentUserAdminTableComponent } from './components/admin-tables/comment-user-admin-table/comment-user-admin-table.component';
import { PaginationComponent } from './components/pagination/pagination.component';
import { UserAvatarPipe } from './pipes/user/user-avatar.pipe';
import { FormPromotionComponent } from './components/forms/form-promotion/form-promotion.component';
import { PromotionsTableComponent } from './components/admin-tables/promotions-table/promotions-table.component';
import { FormDrinkComponent } from './components/forms/form-drink/form-drink.component';
import { DrinksTableComponent } from './components/admin-tables/drinks-table/drinks-table.component';
import { DrinkPageComponent } from './components/drink/drink-page/drink-page.component';
import { DrinkCardComponent } from './components/drink/drink-card/drink-card.component';
import { DrinkChooseSheetComponent } from './components/drink/drink-choose-sheet/drink-choose-sheet.component';
import { VolumeDrinkPipe } from './pipes/drink/volume-drink.pipe';
import { FormSnackComponent } from './components/forms/form-snack/form-snack.component';
import { SnacksAdminTableComponent } from './components/admin-tables/snacks-admin-table/snacks-admin-table.component';
import { SnackPageComponent } from './components/snack/snack-page/snack-page.component';
import { SnackCardComponent } from './components/snack/snack-card/snack-card.component';
import { SnackChooseSheetComponent } from './components/snack/snack-choose-sheet/snack-choose-sheet.component';
import { DessertTableComponent } from './components/admin-tables/dessert-table/dessert-table.component';
import { FormDessertComponent } from './components/forms/form-dessert/form-dessert.component';
import { DessertCardComponent } from './components/dessert/dessert-card/dessert-card.component';
import {DessertPageComponent} from './components/dessert/dessert-page/dessert-page.component';
import { DessertChooseSheetComponent } from './components/dessert/dessert-choose-sheet/dessert-choose-sheet.component';
import { HomePageComponent } from './components/home-page/home-page.component';
import { FooterComponent } from './components/footer/footer.component';
import { PasswordLoginReminderComponent } from './components/forms/password-login-reminder/password-login-reminder.component';



@NgModule({
  declarations: [
    AppComponent,
    FormUserRegistrationComponent,
    FormUserAuthenticationComponent,
    FormPizzaPostingComponent,
    FormIngredientPostingComponent,
    HeaderComponent,
    NavBarComponent,
    SnackBarComponent,
    AdminPageComponent,
    SnackBarRegistrationComponent,
    FormSizePizzaPostComponent,
    UserCardComponent,
    UserPageComponent,
    PizzaPageComponent,
    PizzaCardComponent,
    PizzaChooseSheetComponent,
    IngredientCardComponent,
    PaymentComponent,
    PaymentModalComponent,
    PizzaCartCardComponent,
    PizzaCardCartItemComponent,
    PizzaCartPageComponent,
    StarRatingVoteComponent,
    StarRatingComponent,
    FormCommentComponent,
    CommentCardComponent,
    PurchaseComponent,
    PurchasesItemComponent,
    PizzaAdminTableComponent,
    IngredientPipe,
    IngredientAdminPageComponent,
    PizzaSizeAdminTableComponent,
    UserAdminTableComponent,
    CommentUserAdminTableComponent,
    PaginationComponent,
    UserAvatarPipe,
    FormPromotionComponent,
    PromotionsTableComponent,
    FormDrinkComponent,
    DrinksTableComponent,
    DrinkPageComponent,
    DrinkCardComponent,
    DrinkChooseSheetComponent,
    VolumeDrinkPipe,
    FormSnackComponent,
    SnacksAdminTableComponent,
    SnackPageComponent,
    SnackCardComponent,
    SnackChooseSheetComponent,
    DessertTableComponent,
    FormDessertComponent,
    DessertCardComponent,
    DessertPageComponent,
    DessertChooseSheetComponent,
    HomePageComponent,
    FooterComponent,
    PasswordLoginReminderComponent,
  ],
    imports: [
        BrowserModule,
        NgbModule,
        BrowserAnimationsModule,
        MaterialModules,
        ReactiveFormsModule,
        HttpClientModule,
        ToastrModule.forRoot(),
        NgxStripeModule.forRoot('pk_test_51Hv6uTGmmCaqYLZpH2DedxpEXPGQFNx7eM4i' +
            'aTxkjowLq94xpVY5ORolZnmokcoiwQ51IAMCWsGC0B6cl6c7EbUg00Aefbn2rC'),
        RouterModule.forRoot(routes, { relativeLinkResolution: 'legacy' }),
        StoreModule.forRoot(reducers, {
            metaReducers,
            runtimeChecks: {
                strictStateImmutability: true,
                strictActionImmutability: true
            }
        }),
        StoreDevtoolsModule.instrument({maxAge: 25, logOnly: environment.production}),
        FormsModule,
    ],
  providers: [{
    provide: HTTP_INTERCEPTORS,
    multi: true,
    useClass: TokenInterceptor
  }],
  bootstrap: [AppComponent]
})
export class AppModule { }
