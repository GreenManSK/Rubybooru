import { Component, Input, OnInit } from '@angular/core';
import { Tag } from "../entity/tag";
import { faQuestion } from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: 'app-tag-list',
  templateUrl: './tag-list.component.html',
  styleUrls: ['./tag-list.component.less']
})
export class TagListComponent implements OnInit {


  faSquare = faQuestion;

  @Input() tags: Tag[] = [];

  ngOnInit() {
  }

}
