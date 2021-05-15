import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {Observable, of} from 'rxjs';
import {UserService} from '../../services/userDao/user.service';

@Injectable({
  providedIn: 'root'
})
export class UserGuardService implements CanActivate{

  constructor(private userService: UserService,
              private router: Router) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree>
    | Promise<boolean | UrlTree> | boolean | UrlTree {
    if (this.userService.isUser()){
      return of(true);
    }
    else {
      this.router.navigate(['authenticate'], {
        queryParams: {
          accessDenied: true
        }
      }).then(data => console.log(data));
    }
    return of(false);
  }
}
