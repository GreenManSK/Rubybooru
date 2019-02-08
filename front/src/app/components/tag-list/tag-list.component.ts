import { Component, Input, OnInit } from '@angular/core';
import { Tag } from "../../entities/tag";
import { faQuestion } from "@fortawesome/free-solid-svg-icons";
import { TagType } from "../../entities/tag-type.enum";
import { ActivatedRoute, Router } from "@angular/router";
import { UrlParserService } from "../../views/search/url-parser.service";
import { t } from "../../../../node_modules/@angular/core/src/render3/index";

@Component({
  selector: 'app-tag-list',
  templateUrl: './tag-list.component.html',
  styleUrls: ['./tag-list.component.less']
})
export class TagListComponent implements OnInit {

  static typesSort = [TagType.COPYRIGHT, TagType.CHARACTER, TagType.CIRCLE, TagType.ARTIST, TagType.STUDIO, TagType.GENERAL, TagType.META,
    TagType.MEDIUM, TagType.STYLE, TagType.SOURCE, TagType.FAULTS
  ];

  faSquare = faQuestion;
  public urlParser: UrlParserService;

  @Input() tags: Tag[] = [];

  static sortTags( tags: Tag[] ): Tag[] {
    return tags.sort(( a: any, b: any ) => {
      if (a.type === b.type) {
        return b.count - a.count;
      }
      return TagListComponent.typesSort.indexOf(a.type.toLocaleLowerCase())
      < TagListComponent.typesSort.indexOf(b.type.toLocaleLowerCase()) ? -1 : 1;
    });
  }

  constructor(
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.urlParser = new UrlParserService(router, route);
  }


  ngOnInit() {

  }

  sortTags( tags: Tag[] ): Tag[] {
    return TagListComponent.sortTags(tags);
  }

}
