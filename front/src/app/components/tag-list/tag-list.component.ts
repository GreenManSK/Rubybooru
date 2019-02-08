import { Component, Input, OnInit } from '@angular/core';
import { Tag } from "../../entities/tag";
import { faQuestion } from "@fortawesome/free-solid-svg-icons";
import { TagType } from "../../entities/tag-type.enum";
import { ActivatedRoute, Router } from "@angular/router";
import { UrlParserService } from "../../views/search/url-parser.service";
import { t } from "../../../../node_modules/@angular/core/src/render3/index";
import { TagService } from "../../services/tag/tag.service";

@Component({
  selector: 'app-tag-list',
  templateUrl: './tag-list.component.html',
  styleUrls: ['./tag-list.component.less']
})
export class TagListComponent implements OnInit {

  faSquare = faQuestion;
  public urlParser: UrlParserService;

  @Input() tags: Tag[] = [];

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private tagService: TagService
  ) {
    this.urlParser = new UrlParserService(router, route);
  }


  ngOnInit() {

  }

  sortTags( tags: Tag[] ): Tag[] {
    return this.tagService.sortTags(tags);
  }

}
