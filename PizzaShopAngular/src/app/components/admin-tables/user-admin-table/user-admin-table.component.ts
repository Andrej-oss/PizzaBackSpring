import { Component, OnInit } from '@angular/core';
import {select, Store} from '@ngrx/store';
import {Observable} from 'rxjs';
import {User} from '../../models/User';
import {selectAllAvatars, selectUserAvatar, selectUsers} from '../../../logic/store/selectors/UserSelect';
import {ThemeObjectService} from '../../../logic/theme-object/theme-object.service';
import {UserActionsService} from '../../../logic/store/actions/user/user-actions.service';
import {Avatar} from '../../models/Avatar';

@Component({
  selector: 'app-user-admin-table',
  templateUrl: './user-admin-table.component.html',
  styleUrls: ['./user-admin-table.component.css']
})
export class UserAdminTableComponent implements OnInit {
  users$: Observable<User[]> = this.store$.pipe(select(selectUsers));
  user: User;
  avatars: Observable<Avatar[]> = this.store$.pipe(select(selectAllAvatars));
  avatar: Observable<Avatar> = this.store$.pipe(select(selectUserAvatar));
  avatarUrl = '/api/avatar/image/';
  displayedColumns: string[] = ['position', 'image', 'username', 'name', 'lastName', 'email', 'city', 'address',
    'postCode', 'phone', 'role', 'option'];
  black = 'background-color: black';
  white = 'background-color: white';
  blackColor = 'color: white';
  whiteColor = 'color: black';
  isOpenCommentTable: boolean;
  isOpenPurchasesTable: boolean;
  constructor(private store$: Store,
              private userActionsService: UserActionsService,
              public themeObjectService: ThemeObjectService) { }

  ngOnInit(): void {
  }

  onDelete(id: any): void{
    this.userActionsService.deleteUser(id);
  }
  getRole(role: string): string{
    return role.substring(role.split('').findIndex(value => value === '_') + 1, role.length);
  }

  getComments(name: string): void{
    this.userActionsService.getUsersComments(name);
    this.isOpenCommentTable = !this.isOpenCommentTable;
    // this.isOpenPurchasesTable = false;
  }

  getPurchases(id: number): void{
    this.userActionsService.getPurchasesByUser(id);
    this.isOpenPurchasesTable = !this.isOpenPurchasesTable;
    // this.isOpenCommentTable = false;
  }
}
