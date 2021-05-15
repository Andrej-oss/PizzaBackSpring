import {Component, Input, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {ThemeObjectService} from '../../../logic/theme-object/theme-object.service';
import {Comment} from '../../models/Comment';
import {SnackBarComponent} from '../../snack-bar/snack-bar-login/snack-bar.component';
import {MatSnackBar} from '@angular/material/snack-bar';
import {CommentService} from '../../../logic/services/commentDao/comment.service';
import {PizzaActionService} from '../../../logic/store/actions/pizza/pizza-action.service';

@Component({
  selector: 'app-form-comment',
  templateUrl: './form-comment.component.html',
  styleUrls: ['./form-comment.component.css']
})
export class FormCommentComponent implements OnInit {
  @Input()
  pizzaId;
  @Input()
  commentUser: Comment;
  pizzaComment: Comment;
  comment: FormGroup;
  tittle: FormControl;
  body: FormControl = new FormControl('', [Validators.required]);
  constructor(private pizzaService: PizzaActionService,
              private commentService: CommentService,
              private snackBar: MatSnackBar,
              public themeObjectService: ThemeObjectService) { }

  ngOnInit(): void {
    this.comment = new FormGroup({
      tittle: this.tittle = new FormControl(this.commentUser ? this.commentUser.tittle : '', [Validators.required]),
      body: this.body = new FormControl(this.commentUser ? this.commentUser.body : '', [Validators.required])
    });
  }

  onSave(comment: FormGroup): void{
    this.pizzaComment = {
      tittle: comment.controls.tittle.value,
      body: comment.controls.body.value,
    };
    this.pizzaService.postComment(this.pizzaId, this.themeObjectService.data.value.userId, this.pizzaComment);
    this.themeObjectService.data.value.message = `thank you for your comment`;
    this.snackBar.openFromComponent(SnackBarComponent, {
      duration: 2000,
    });
  }

  onUpdate(comment: FormGroup): void{
    this.pizzaComment = {
      author: this.commentUser.author,
      tittle: comment.controls.tittle.value,
      body: comment.controls.body.value,
      pizzaId: this.pizzaId,
      voice: this.commentUser.voice,
      userId: this.themeObjectService.data.value.userId,
    };
    this.commentService.editComment(this.commentUser.id, this.pizzaComment)
      .subscribe(data => console.log(data));
    this.pizzaService.updateComment(this.commentUser.id, this.pizzaComment);
    this.themeObjectService.data.value.message = `your comment was updated`;
    this.snackBar.openFromComponent(SnackBarComponent, {
      duration: 2000,
    });
  }
}
