import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { FontAwesomeModule } from "@fortawesome/angular-fontawesome";
import { MenuComponent } from './menu/menu.component';
import { SearchBarComponent } from './search-bar/search-bar.component';
import { ImageGalleryComponent } from './image-gallery/image-gallery.component';
import { AppRoutingModule } from './/app-routing.module';
import { TagListComponent } from './tag-list/tag-list.component';

@NgModule({
  declarations: [
    AppComponent,
    MenuComponent,
    SearchBarComponent,
    ImageGalleryComponent,
    TagListComponent,
  ],
  imports: [
    BrowserModule,
    FontAwesomeModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
