import { Component, OnInit } from '@angular/core';
import { faAngleDoubleLeft, faAngleDoubleRight, faQuestion, faFolder } from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: 'app-gallery',
  templateUrl: './gallery.component.html',
  styleUrls: ['./gallery.component.less']
})
export class GalleryComponent implements OnInit {
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
  constructor() { }

  ngOnInit() {
  }

  arrayOne( n: number ): any[] {
    return Array(n);
  }

}
