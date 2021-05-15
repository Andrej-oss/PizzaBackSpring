import {Component, Input, OnInit} from '@angular/core';
import {User} from '../models/User';
import {Subscription} from 'rxjs';
import {UserService} from "../../logic/services/userDao/user.service";

@Component({
  selector: 'app-user-card',
  templateUrl: './user-card.component.html',
  styleUrls: ['./user-card.component.css']
})
export class UserCardComponent implements OnInit {
  @Input()
  user: User;

  constructor(private userService: UserService) {
  }

  ngOnInit(): void {
  }

  onDelete(id: number): Subscription {
   return  this.userService.deleteUser(id).subscribe(data => console.log(data));
  }
}
