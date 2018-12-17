import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { FontAwesomeModule } from "@fortawesome/angular-fontawesome";
import { MenuComponent } from './menu/menu.component';
import { SearchBarComponent } from './search-bar/search-bar.component';
import { ImageGalleryComponent } from './image-gallery/image-gallery.component';
import { AppRoutingModule } from './/app-routing.module';
import { TagListComponent } from './tag-list/tag-list.component';
import { PaginationComponent } from './pagination/pagination.component';
import { ImageComponent } from './image/image/image.component';
import { ImagePanelComponent } from './image/image-panel/image-panel.component';
import { SearchImagesComponent } from './search/search-images/search-images.component';
import { SearchTagsComponent } from './search/search-tags/search-tags.component';
import { WhispererInputComponent } from './whisperer-input/whisperer-input.component';

@NgModule({
  declarations: [
    AppComponent,
    MenuComponent,
    SearchBarComponent,
    ImageGalleryComponent,
    TagListComponent,
    PaginationComponent,
    ImageComponent,
    ImagePanelComponent,
    SearchImagesComponent,
    SearchTagsComponent,
    WhispererInputComponent,
  ],
  imports: [
    BrowserModule,
    FontAwesomeModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
