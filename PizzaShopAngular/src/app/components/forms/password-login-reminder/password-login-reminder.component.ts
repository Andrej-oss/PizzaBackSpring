import { Component, OnInit } from '@angular/core';
import {ThemeObjectService} from '../../../logic/theme-object/theme-object.service';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {UserService} from '../../../logic/services/userDao/user.service';
import {SnackBarComponent} from '../../snack-bar/snack-bar-login/snack-bar.component';
import {MatSnackBar} from '@angular/material/snack-bar';
import {Router} from "@angular/router";

@Component({
  selector: 'app-password-login-reminder',
  templateUrl: './password-login-reminder.component.html',
  styleUrls: ['./password-login-reminder.component.css']
})
export class PasswordLoginReminderComponent implements OnInit {
reminderForm: FormGroup;
email: FormControl;
error: string;
  constructor(public themeObjectService: ThemeObjectService,
              private snackBar: MatSnackBar,
              private router: Router,
              private userService: UserService) {
    this.reminderForm = new FormGroup({
      email: this.email = new FormControl('', [Validators.required, Validators.email])
    });
  }

  ngOnInit(): void {
  }

  onRemindPassword(authForm: FormGroup): void{
    this.reminderForm.disable();
    this.userService.passwordReminder(authForm.controls.email.value)
      .subscribe(data => {
        this.error = null;
      },
        (error => {
          console.log(error);
          if (error.status === 200){
            this.router.navigate(['/']).then(data1 => console.log(data1));
            this.themeObjectService.data.value.message = 'Sending! Check your email';
            this.snackBar.openFromComponent(SnackBarComponent, {
              duration: 2000,
            });
          }
          this.error = error.error.tittle;
          this.reminderForm.enable();
        }));
  }
}
