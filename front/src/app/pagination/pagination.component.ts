import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { faAngleDoubleLeft, faAngleLeft, faAngleRight } from "@fortawesome/free-solid-svg-icons";


@Component({
  selector: 'app-pagination',
  templateUrl: './pagination.component.html',
  styleUrls: ['./pagination.component.less']
})
export class PaginationComponent implements OnInit {

  public START_PAGES = 2;
  public END_PAGES = 2;

  @Input() pages: number;
  @Input() actualPage: number;
  @Input() size = 7;
  @Output() pageChange = new EventEmitter<number>();

  leftIcon = faAngleLeft;
  rightIcon = faAngleRight;
  firstIcon = faAngleDoubleLeft;

  ngOnInit() {
  }

  changePage( page: number ) {
    this.actualPage = page;
    this.pageChange.emit(page);
    return false;
  }

  pageArray(): any[] {
    let result = Array(this.size).fill(1).map(( x, i ) => i + this.actualPage - Math.floor(this.size / 2));
    result = result.filter(v => (v >= 1 && v <= this.pages));
    return result;
  }

}
