import { Component, OnInit, ViewChild } from '@angular/core';
import { TagApiService } from "../../services/tag-api/tag-api.service";
import { TagType } from "../../entities/tag-type.enum";
import { ActivatedRoute, Router } from "@angular/router";
import { UrlParserService } from "../../views/search/url-parser.service";
import { environment } from "../../../environments/environment";
import { ImageOrder } from "../../views/search/image-order.enum";
import { Tag } from "../../entities/tag";
import { WhispererInputComponent } from "../whisperer-input/whisperer-input.component";
import { InputParser } from "./input-parser";

@Component({
  selector: 'app-search-bar',
  templateUrl: './search-bar.component.html',
  styleUrls: ['./search-bar.component.less']
})
export class SearchBarComponent implements OnInit {

  USED_TYPES = environment.whispererUsedTags;
  ORDER = ImageOrder;

  @ViewChild(WhispererInputComponent) whispererInput: WhispererInputComponent;

  idToName = {};
  nameToId = {};
  whisperTags = [];
  urlParser: UrlParserService;
  defaultValue = '';
  order = ImageOrder.NEWEST;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    public tagApi: TagApiService ) {
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
    const filters = this.urlParser.getFilters();


    let value = '';
    if (tagIds !== null && Object.keys(this.idToName).length !== 0) {
      for (const id of tagIds) {
        value += this.idToName[id] + ' ';
      }
    }

    if (filters !== null) {
      for (const filter of filters) {
        value += filter + ' ';
      }
    }

    this.defaultValue = value;
    this.order = this.urlParser.getOrder();
  }

  onSubmit() {
    const parser = new InputParser(this.whispererInput.getValues(), this.nameToId);
    this.urlParser.navigate(1, parser.getTags(), this.order, parser.getFilters());
  }

}
