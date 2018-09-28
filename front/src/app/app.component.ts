import { Component } from '@angular/core';
import { faSearch, faAngleDoubleLeft, faAngleDoubleRight, faQuestion } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.less']
})
export class AppComponent {
  title = 'app';
  faSearch = faSearch;
  faSquare = faQuestion;
  faAngleDoubleLeft = faAngleDoubleLeft;
  faAngleDoubleRight = faAngleDoubleRight;
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

  arrayOne( n: number ): any[] {
    return Array(n);
  }
}
