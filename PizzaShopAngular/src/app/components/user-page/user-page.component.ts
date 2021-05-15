import {Component, OnInit} from '@angular/core';
import {User} from '../models/User';
import {UserActionsService} from '../../logic/store/actions/user/user-actions.service';
import {select, Store} from '@ngrx/store';
import {Observable} from 'rxjs';
import {selectPrincipal, selectUserAvatar} from '../../logic/store/selectors/UserSelect';
import {ThemeObjectService} from '../../logic/theme-object/theme-object.service';
import {UserService} from '../../logic/services/userDao/user.service';
import {Avatar} from '../models/Avatar';
import {Purchase} from "../models/Purchase";


@Component({
  selector: 'app-user-page',
  templateUrl: './user-page.component.html',
  styleUrls: ['./user-page.component.css']
})
export class UserPageComponent implements OnInit {
  userFormData: FormData = new FormData();
  isCartOpen = false;
  isUpdateInfoOpen = false;
  isPurchasesOpen = false;
  isAvatarOpen: boolean;
  image: File;
  avatar: Observable<Avatar> = this.store$.pipe(select(selectUserAvatar));
  avatarUrl = '/api/avatar/image/';
  user: User;

  constructor(private userService: UserService,
              private userActionsService: UserActionsService,
              private store$: Store,
              private themeObjectService: ThemeObjectService) {
  }

  ngOnInit(): void {
    this.store$.pipe(select(selectPrincipal))
      .subscribe(data => this.user = data);
    this.userActionsService.getPrincipal(this.userService.getUserName());
    this.userActionsService.getPurchasesByUser(this.themeObjectService.data.value.userId);
    this.userActionsService.getUserAvatar(this.themeObjectService.data.value.userId);
  }

  onBasket(): void {
    this.isCartOpen = !this.isCartOpen;
    this.isUpdateInfoOpen = false;
    this.isPurchasesOpen = false;
  }

  onUserUpdate(): void {
    this.isUpdateInfoOpen = !this.isUpdateInfoOpen;
    this.isCartOpen = false;
    this.isPurchasesOpen = false;
  }

  onPurchase(): void {
    this.isPurchasesOpen = !this.isPurchasesOpen;
    this.isCartOpen = false;
    this.isUpdateInfoOpen = false;
  }

  onOpenAvatarChange(): void {
    this.isAvatarOpen = !this.isAvatarOpen;
  }

  uploadFile($event: Event): void {
     this.image = ($event.target as HTMLInputElement).files[0];
  }

  onSaveAvatar(id: number): void{
    this.userActionsService.saveAvatarUser(id, this.userFormData,
      this.userFormData.append('file', this.image));
    this.resetDataForm();
  }
  resetDataForm(): void{
    this.userFormData.delete('file');
    this.isAvatarOpen = false;
  }
}
