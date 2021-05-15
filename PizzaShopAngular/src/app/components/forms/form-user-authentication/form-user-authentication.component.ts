import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {forkJoin, Observable, of, pipe, Subscription} from 'rxjs';
import {ActivatedRoute, Router} from '@angular/router';
import {MatSnackBar} from '@angular/material/snack-bar';
import {SnackBarComponent} from '../../snack-bar/snack-bar-login/snack-bar.component';
import {ThemeObjectService} from '../../../logic/theme-object/theme-object.service';
import {UserActionsService} from '../../../logic/store/actions/user/user-actions.service';
import {concatMap, map, mergeMap, switchMap, tap} from 'rxjs/operators';
import {PizzaService} from '../../../logic/services/pizzaDao/pizza.service';
import {UserService} from '../../../logic/services/userDao/user.service';
import {PizzaActionService} from '../../../logic/store/actions/pizza/pizza-action.service';
import {select, Store} from '@ngrx/store';
import {DrinksSelector, SnacksSelector} from '../../../logic/store/selectors/PizzaSelector';
import {Drink} from '../../models/Drink';
import {Snack} from '../../models/Snack';
import {Dessert} from '../../models/Dessert';

@Component({
  selector: 'app-form-user-authentication',
  templateUrl: './form-user-authentication.component.html',
  styleUrls: ['./form-user-authentication.component.css']
})
export class FormUserAuthenticationComponent implements OnInit, OnDestroy {
  error: string;
  hide = true;
  sub: Subscription;
  authForm: FormGroup;
  username: FormControl = new FormControl('', Validators.required);
  password: FormControl = new FormControl('', Validators.required);
  authority: string;
  drinks: Drink[];
  snacks: Snack[];
  desserts: Dessert[];

  constructor(private userService: UserService,
              private router: Router,
              private store$: Store,
              private snackBar: MatSnackBar,
              private pizzaActionService: PizzaActionService,
              private activatedRoute: ActivatedRoute,
              private userActionsService: UserActionsService,
              public themeObjectService: ThemeObjectService, private pizzaService: PizzaService) {
    this.authForm = new FormGroup({
      username: this.username,
      password: this.password
    });
  }

  ngOnDestroy(): void {
    if (this.sub) {
      this.sub.unsubscribe();
    }
  }

  ngOnInit(): void {
    this.activatedRoute.queryParams.subscribe((params) => {
      if (params.accessDenied) {
        this.error = 'Please log in to Pizza shop first';
      }
      console.log(params);
    });
    this.formCheck();
    this.store$.pipe(select(DrinksSelector)).subscribe(data => this.drinks = data);
    this.store$.pipe(select(SnacksSelector)).subscribe(data => this.snacks = data);
    this.pizzaActionService.getDrinks();
    this.pizzaActionService.getAllSnacks();
    this.pizzaActionService.getAllDessert();
  }
  getAuthenticateUser(user: {username: string, password: string}): any{
    return new Promise(resolve => {
      return this.userService.authenticateUser(user).subscribe(data => resolve(data));
    });
  }
  getPrincipal(name: string): any{
    return new Promise(resolve => {
      return this.userService.getUserByName(name).subscribe(data => resolve(data));
    });
  }

  onAuthenticate(authForm: FormGroup): void {
    this.themeObjectService.data.value.isAuthLoad = true;
    this.authForm.disable();
    // Option1: Promises//////////////////////////
    // this.getAuthenticateUser({username: authForm.controls.username.value, password: authForm.controls.password.value})
    //   .then(user => {
    //     console.log(user);
    //     return this.getPrincipal(user.username);
    //   })
    //   .then(principal => {
    //     console.log(principal);
    //     debugger;
    //     return  this.userActionsService.getAllCart(principal.id);
    //   })
    //   .then(data => {
    //   console.log(data);
    //   this.error = null;
    //   this.themeObjectService.data.value.message = 'Login success';
    //   this.snackBar.openFromComponent(SnackBarComponent, {
    //            duration: 2000,
    //          });
    //   this.router.navigate(['/'])
    //     .then(data1 => console.log(data1));
    // }).catch(data => console.log(data));
    // Option2: RxJs/////////////////////
    // @ts-ignore
    this.userService
      .authenticateUser({username: authForm.controls.username.value, password: authForm.controls.password.value})
      .pipe(switchMap(data1 => {
          this.userActionsService.getPrincipal(data1.username);
          return this.userService.getUserByName(data1.username);
        }),
        tap(data2 =>  this.userActionsService.getAllCart(data2.id)))
      .subscribe((data) => {
          this.error = null;
          this.themeObjectService.data.value.message = 'Login success';
          this.snackBar.openFromComponent(SnackBarComponent, {
            duration: 2000,
          });
          // tslint:disable-next-line:no-shadowed-variable
          this.router.navigate(['/']).then(data => console.log(data));
        },
        (error) => {
          if (error.status === 403) {
            this.error = 'Wrong User name or password!!!!!!!!!!';
          }
          console.warn(error);
          this.authForm.enable();
        });
  }

  isAdmin(): boolean {
    return this.authority === 'ADMIN' ? true : false;
  }

  formCheck(): void {
    if (this.userService.isAuthenticated()) {
      this.authForm.disable();
    } else if (!this.userService.isAuthenticated()) {
      this.authForm.enable();
    }
  }

  onRegistration(): void {
    this.router.navigateByUrl('/registration').then(data => console.log(data));
  }

  onReminder(): void{
    this.router.navigate(['remind']).then(data => console.log(data));
  }
}
