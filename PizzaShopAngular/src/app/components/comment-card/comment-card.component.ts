import {Component, Input, OnInit} from '@angular/core';
import {Comment} from '../models/Comment';
import {ThemeObjectService} from '../../logic/theme-object/theme-object.service';
import {Voice} from '../models/Voice';
import {SnackBarComponent} from '../snack-bar/snack-bar-login/snack-bar.component';
import {MatSnackBar} from '@angular/material/snack-bar';
import {CommentService} from '../../logic/services/commentDao/comment.service';
import {VoiceService} from '../../logic/services/voiceDao/voice.service';
import {PizzaActionService} from '../../logic/store/actions/pizza/pizza-action.service';
import {Observable} from 'rxjs';
import {Avatar} from '../models/Avatar';
import {select, Store} from '@ngrx/store';
import {selectAllAvatars} from '../../logic/store/selectors/UserSelect';

@Component({
  selector: 'app-comment-card',
  templateUrl: './comment-card.component.html',
  styleUrls: ['./comment-card.component.css']
})
export class CommentCardComponent implements OnInit {
  @Input()
  comment: Comment;
  @Input()
  pizzaId;
  avatars: Observable<Avatar[]> = this.store$.pipe(select(selectAllAvatars));
  isEditComment: boolean;
  voice: Voice;
  isLiked: boolean;
  voiceSum: number;
  voiceId: number;
  avatarUrl = '/api/avatar/image/';

  constructor(private pizzaService: PizzaActionService,
              private commentService: CommentService,
              private voiceService: VoiceService,
              private snackBar: MatSnackBar,
              private store$: Store,
              public themeObjectService: ThemeObjectService) {
  }

  ngOnInit(): void {
    this.voiceSummary();
    this.checkIsUserVoted();
  }

  onDeleteComment(id: number): void {
    debugger;
    this.commentService.deleteComment(id).subscribe(data => console.log(data));
    this.pizzaService.deleteCommentPizza(id);
    this.themeObjectService.data.value.message = `your comment was deleted`;
    this.snackBar.openFromComponent(SnackBarComponent, {
      duration: 2000,
    });
  }

  onEdit(id: number): void {
    this.isEditComment = !this.isEditComment;
  }

  onLikeComment(id: number): void {
    this.voice = {
      voice: 1,
      userId: this.themeObjectService.data.value.userId
    };
    this.voiceService.saveVoice(id, this.voice).subscribe(data => {
      console.log(data);
    });
    this.isLiked = true;
    this.voiceSum += 1;
    this.themeObjectService.data.value.message = `you liked comment from  ${this.comment.author}`;
    this.snackBar.openFromComponent(SnackBarComponent, {
      duration: 2000,
    });
  }

  voiceSummary(): void {

      this.voiceSum = this.comment.voice.reduce((previousValue, currentValue) => previousValue + currentValue.voice, 0);
  }

  checkIsUserVoted(): void {
      const user = this.comment.voice.find(value => value.userId === this.themeObjectService.data.value.userId);
      console.log(user);
      user && user.userId !== 0 ? this.isLiked = true : this.isLiked = false;
  }

  onDeleteVoice(id: number): void {
    debugger;
    const voice = this.comment.voice.find(value => {
      return value.userId === this.themeObjectService.data.value.userId;
    });
    if (voice) {
      this.voiceService.deleteVoiceComment(voice.id).subscribe(data => console.log(data));
      this.voiceSum -= 1;
      this.isLiked = false;
      this.themeObjectService.data.value.message = `you deleted your like comment from  ${this.comment.author}`;
      this.snackBar.openFromComponent(SnackBarComponent, {
        duration: 2000,
      });
    }
  }
}
