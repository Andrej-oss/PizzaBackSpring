import { Component, OnInit } from '@angular/core';
import {select, Store} from '@ngrx/store';
import {Observable} from 'rxjs';
import {selectCommentsUser} from '../../../logic/store/selectors/UserSelect';
import {Comment} from '../../models/Comment';
import {ThemeObjectService} from '../../../logic/theme-object/theme-object.service';
import {Voice} from '../../models/Voice';
import {CommentService} from '../../../logic/services/commentDao/comment.service';

@Component({
  selector: 'app-comment-user-admin-table',
  templateUrl: './comment-user-admin-table.component.html',
  styleUrls: ['./comment-user-admin-table.component.css']
})
export class CommentUserAdminTableComponent implements OnInit {
  comments$: Observable<Comment[]> = this.store$.pipe(select(selectCommentsUser));
  displayedColumns: string[] = ['position', 'body', 'tittle', 'author', 'voice', 'option'];
  black = 'background-color: black';
  white = 'background-color: white';
  blackColor = 'color: white';
  whiteColor = 'color: black';
  comment: Comment;
  isOpenCommentUpdate: boolean;
  constructor(private store$: Store,
              private commentService: CommentService,
              public themeObjectService: ThemeObjectService) { }

  ngOnInit(): void {
  }

  onDelete(id: any): void{
    this.commentService.deleteComment(id);
  }

  upDate(element: Comment): void{
    this.comment = element;
    this.isOpenCommentUpdate = !this.isOpenCommentUpdate;
  }

  getVoices(voice: Voice[]): number{
    return voice.reduce((previousValue, currentValue) => previousValue + currentValue.voice, 0);
  }
}
