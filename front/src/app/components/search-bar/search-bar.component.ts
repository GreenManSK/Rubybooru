import { Component, OnInit } from '@angular/core';
import { TagApiService } from "../../services/tag-api/tag-api.service";
import { TagType } from "../../entities/tag-type.enum";
import { ActivatedRoute, Router } from "@angular/router";
import { UrlParserService } from "../../views/search/url-parser.service";

@Component({
  selector: 'app-search-bar',
  templateUrl: './search-bar.component.html',
  styleUrls: ['./search-bar.component.less']
})
export class SearchBarComponent implements OnInit {

  USED_TYPES = [TagType.COPYRIGHT, TagType.CHARACTER, TagType.ARTIST, TagType.GENERAL];

  idToName = {};
  nameToId = {};
  whisperTags = [];
  urlParser: UrlParserService;
  defaultValue = '';

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    public tagApi: TagApiService ) {
    // TODO
    // ability to redirect on submit
    this.urlParser = new UrlParserService(router, route);
    this.route.params.subscribe(() => this.onParamChange());
    this.route.queryParams.subscribe(() => this.onParamChange());
  }

  ngOnInit() {
    this.tagApi.getTags().subscribe(tags => {
      tags.forEach(t => {
        this.idToName[t.id] = t.name;
        this.nameToId[t.name] = t.id;
      });
      this.whisperTags = tags.filter(t => this.USED_TYPES.indexOf(TagType[t.type]) !== -1);
      this.onParamChange();
    });
  }

  onParamChange() {
    const tagIds = this.urlParser.getTags();
    if (tagIds === null || Object.keys(this.idToName).length === 0) {
      return;
    }
    let value = '';
    for (const id of tagIds) {
      value += this.idToName[id] + ' ';
    }
    this.defaultValue = value;
  }


}
