import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { FontAwesomeModule } from "@fortawesome/angular-fontawesome";
import { MenuComponent } from './components/menu/menu.component';
import { SearchBarComponent } from './components/search-bar/search-bar.component';
import { ImageGalleryComponent } from './components/image-gallery/image-gallery.component';
import { AppRoutingModule } from './services/router/app-routing.module';
import { TagListComponent } from './components/tag-list/tag-list.component';
import { PaginationComponent } from './components/pagination/pagination.component';
import { ImageComponent } from './views/image/image/image.component';
import { ImagePanelComponent } from './views/image/image-panel/image-panel.component';
import { SearchImagesComponent } from './views/search/search-images/search-images.component';
import { SearchTagsComponent } from './views/search/search-tags/search-tags.component';
import { WhispererInputComponent } from './components/whisperer-input/whisperer-input.component';

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
