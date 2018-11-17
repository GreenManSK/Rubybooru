import { Component, Input, OnInit } from '@angular/core';
import { Tag } from "../entity/tag";
import { faQuestion } from "@fortawesome/free-solid-svg-icons";
import { TagType } from "../entity/tag-type.enum";
import { ActivatedRoute } from "@angular/router";
import { UrlParserService } from "../search/url-parser.service";
import { ImageOrder } from "../search/image-order.enum";

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

  constructor( private route: ActivatedRoute ) {
    this.urlParser = new UrlParserService(route);
  }


  ngOnInit() {

  }

  generateLink( tag: Tag ): string {
    return this.urlParser.generateUrl(1, [tag], ImageOrder.NEWEST);
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
