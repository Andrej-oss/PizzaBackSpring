import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css']
})
export class FooterComponent implements OnInit {
  isLiActive: boolean;
  isGitActive: boolean;
  isWuActive: boolean;
in = '../../../assets/img/In.jpg';
inA = '../../../assets/img/InA.jpg';
git = '../../../assets/img/git.jpg';
gitA = '../../../assets/img/gitA.jpg';
w = '../../../assets/img/whats.jpg';
wA = '../../../assets/img/whatsA.jpg';
  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  changeImg(): void{
    this.isLiActive = !this.isLiActive;
  }

  changeWImage(): void{
    this.isWuActive = !this.isWuActive;
  }

  changeGitImage(): void{
    this.isGitActive = !this.isGitActive;
  }
}
