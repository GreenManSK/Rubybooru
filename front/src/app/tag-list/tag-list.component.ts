import { Component, Input, OnInit } from '@angular/core';
import { Tag } from "../entity/tag";
import { faQuestion } from "@fortawesome/free-solid-svg-icons";
import { TagType } from "../entity/tag-type.enum";
import { ActivatedRoute, Router } from "@angular/router";
import { UrlParserService } from "../search/url-parser.service";

@Component({
  selector: 'app-tag-list',
  templateUrl: './tag-list.component.html',
  styleUrls: ['./tag-list.component.less']
})
export class TagListComponent implements OnInit {

  faSquare = faQuestion;
  typesSort = [TagType.COPYRIGHT, TagType.CHARACTER, TagType.CIRCLE, TagType.ARTIST, TagType.STUDIO, TagType.GENERAL, TagType.META,
    TagType.MEDIUM, TagType.STYLE, TagType.SOURCE, TagType.FAULTS
  ];
  public urlParser: UrlParserService;

  @Input() tags: Tag[] = [];

  constructor(
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.urlParser = new UrlParserService(router, route);
  }


  ngOnInit() {

  }

  sortTags( tags: Tag[] ): Tag[] {
    return tags.sort(( a: any, b: any ) => {
      if (a.type === b.type) {
        return b.count - a.count;
      }
      return this.typesSort.indexOf(a.type.toLocaleLowerCase()) < this.typesSort.indexOf(b.type.toLocaleLowerCase()) ? -1 : 1;
    });
  }

}
