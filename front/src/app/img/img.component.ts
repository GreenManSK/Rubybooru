import { Component, OnInit } from '@angular/core';
import { faAngleDoubleLeft, faAngleDoubleRight, faFolder, faQuestion } from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: 'app-img',
  templateUrl: './img.component.html',
  styleUrls: ['./img.component.less']
})
export class ImgComponent implements OnInit {
  faSquare = faQuestion;
  faAngleDoubleLeft = faAngleDoubleLeft;
  faAngleDoubleRight = faAngleDoubleRight;
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

  constructor() {
  }

  ngOnInit() {
  }

  toggleMini( e: Element ) {
    if (e.classList.contains('mini')) {
      e.classList.remove('mini');
    } else {
      e.classList.add('mini');
    }
  }

}
