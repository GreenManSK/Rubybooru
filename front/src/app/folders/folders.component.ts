import { Component, OnInit } from '@angular/core';
import { faAngleDoubleLeft, faAngleDoubleRight, faFolder, faQuestion, faEllipsisH } from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: 'app-folders',
  templateUrl: './folders.component.html',
  styleUrls: ['./folders.component.less']
})
export class FoldersComponent implements OnInit {
  faSquare = faQuestion;
  faAngleDoubleLeft = faAngleDoubleLeft;
  faAngleDoubleRight = faAngleDoubleRight;
  faEllipsisH = faEllipsisH;
  faFolder = faFolder;
  tagTypes = [
    'copyright',
    'circle',
    'character',
    'meta',
    'medium',
    'studio',
    'style',
    'source',
    'faults',
    'general',
    'general',
    'general',
    'general',
    'general'
  ];
  constructor() { }

  ngOnInit() {
  }

  arrayOne( n: number ): any[] {
    return Array(n);
  }

}
