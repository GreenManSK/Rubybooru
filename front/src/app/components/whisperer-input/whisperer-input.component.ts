import { Component, Input, OnInit, Output, ViewChild } from '@angular/core';
import { ElementRef, Renderer2 } from '@angular/core';
import { Tag } from "../../entities/tag";
import { WhispererTag } from "./whisperer-tag";
import { TagListComponent } from "../tag-list/tag-list.component";
import { TagType } from "../../entities/tag-type.enum";
import { environment } from "../../../environments/environment";
import { TagService } from "../../services/tag/tag.service";

@Component({
  selector: 'app-whisperer-input',
  templateUrl: './whisperer-input.component.html',
  styleUrls: ['./whisperer-input.component.less']
})
export class WhispererInputComponent implements OnInit {

  static FOCUS_OUT_TIMER = 150;

  @ViewChild('input') inputElement: ElementRef;

  @Input() id = '';
  @Input() name = '';
  @Input() placeholder = '';
  @Input() whisperLimit = environment.whispererTagLimit;

  tags: WhispererTag[] = [];
  whisperer = [];
  focusTimer = 0;
  activeIndex = -1;

  constructor( private rd: Renderer2, private tagService: TagService ) {
  }

  ngOnInit() {
  }

  @Input()
  set items( items: Tag[] ) {
    this.tags = this.tagService.sortTags(items).map(i => new WhispererTag(i));
  }

  @Input()
  set defaultValue( value: String ) {
    this.inputElement.nativeElement.value = value;
  }

  valueChange( value: string ): void {
    let usedItems = this.splitValues(value);
    usedItems = usedItems.map(i => i.toLocaleLowerCase());
    const prefix = usedItems[usedItems.length - 1];
    const newWhisperItems = [];
    if (prefix.length > 0) {
      for (const item of this.tags) {
        if (item.match(prefix) && usedItems.indexOf(item.tag.name) === -1) {
          newWhisperItems.push(item.tag);
        }
        if (newWhisperItems.length === this.whisperLimit) {
          break;
        }
      }
    }
    this.setWhispererItems(newWhisperItems);
  }

  focusIn() {
    clearTimeout(this.focusTimer);
  }

  focusOut() {
    this.focusTimer = setTimeout(() => this.whisperer = [], WhispererInputComponent.FOCUS_OUT_TIMER);
  }

  moveActive( event: Event, delta: number ) {
    event.preventDefault();
    this.activeIndex += delta;
    this.activeIndex %= this.whisperer.length;
  }

  addIndexValue( event: Event ) {
    if (this.activeIndex === -1) {
      return true;
    }
    event.preventDefault();
    this.addValue(this.whisperer[this.activeIndex].name);
    return false;
  }

  addValue( item: string ) {
    const usedItems = this.splitValues(this.inputElement.nativeElement.value);
    usedItems.pop();
    usedItems.push(item);
    this.inputElement.nativeElement.value = usedItems.join(' ') + ' ';
    this.setWhispererItems([]);
    this.inputElement.nativeElement.focus();
  }

  setWhispererItems( items: string[] ) {
    let matching = this.whisperer.length === items.length;
    if (matching) {
      for (let i = 0; i < items.length; i++) {
        if (items[i] !== this.whisperer[i]) {
          matching = false;
          break;
        }
      }
    }
    if (matching) {
      return;
    }
    this.whisperer = items;
    this.activeIndex = -1;
  }

  splitValues( values: string ): string[] {
    return values.split(/ +/);
  }
}
