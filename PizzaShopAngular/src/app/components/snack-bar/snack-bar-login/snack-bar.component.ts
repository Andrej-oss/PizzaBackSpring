import { Component, OnInit } from '@angular/core';
import {ThemeObjectService} from '../../../logic/theme-object/theme-object.service';

@Component({
  selector: 'app-snack-bar',
  templateUrl: './snack-bar.component.html',
  styleUrls: ['./snack-bar.component.css']
})
export class SnackBarComponent implements OnInit {

  constructor(public themeObjectService: ThemeObjectService) { }

  ngOnInit(): void {
  }

}
