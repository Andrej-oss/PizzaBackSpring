import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import { Observable } from 'rxjs';
import {UserService} from '../../services/userDao/user.service';


@Injectable()
export class TokenInterceptor implements HttpInterceptor{

  constructor(private userService: UserService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (this.userService.isAuthenticated()){
      req = req.clone({
        setHeaders: {
          Authorization: this.userService.getToken()
        }
      });
    }
    return next.handle(req);
  }
}
