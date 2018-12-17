import { Component, Input, OnInit, Output, ViewChild } from '@angular/core';
import { ElementRef, Renderer2 } from '@angular/core';
import { Tag } from "../entity/tag";

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
  @Input() items: Tag[];
  @Input() whisperLimit = 10;

  whisperer = [];
  focusTimer = 0;
  activeIndex = -1;

  constructor( private rd: Renderer2 ) {
  }

  ngOnInit() {
    this.items = this.items.sort(( a, b ) => a.name.localeCompare(b.name));
  }

  valueChange( value: string ): void {
    let usedItems = this.splitValues(value);
    usedItems = usedItems.map(i => i.toLocaleLowerCase());
    const prefix = usedItems[usedItems.length - 1];
    const new_whisper_items = [];
    if (prefix.length > 0) {
      for (const item of this.items) {
        if (item.name.startsWith(prefix) && usedItems.indexOf(item.name) === -1) {
          new_whisper_items.push(item);
        }
        if (new_whisper_items.length === this.whisperLimit) {
          return;
        }
      }
    }
    this.setWhispererItems(new_whisper_items);
  }

  focusIn() {
    clearTimeout(this.focusTimer);
  }

  focusOut() {
    this.focusTimer = setTimeout(() => this.whisperer = [], WhispererInputComponent.FOCUS_OUT_TIMER);
  }

  moveActive( event: Event ) {
    event.preventDefault();
    this.activeIndex++;
    this.activeIndex %= this.whisperer.length;
  }

  addIndexValue( event: Event ) {
    if (this.activeIndex === -1) {
      return true;
    }
    event.preventDefault();
    this.addValue(this.whisperer[this.activeIndex]);
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
