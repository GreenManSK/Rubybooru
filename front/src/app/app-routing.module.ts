import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ImageComponent } from "./image/image/image.component";
import { TagListComponent } from "./tag-list/tag-list.component";
import { ImagePanelComponent } from "./image/image-panel/image-panel.component";
import { SearchImagesComponent } from "./search/search-images/search-images.component";
import { SearchTagsComponent } from "./search/search-tags/search-tags.component";

const routes: Routes = [
  {
    path: '',
    children: [
      {path: '', component: SearchImagesComponent},
      {path: '', component: TagListComponent, outlet: 'sidebar'}
    ]
  },
  {
    path: 'image/:id',
    children: [
      {path: '', component: ImageComponent},
      {path: '', component: ImagePanelComponent, outlet: 'sidebar'}
    ]
  },
  {
    path: ':page',
    children: [
      {path: '', component: SearchImagesComponent},
      {path: '', component: SearchTagsComponent, outlet: 'sidebar'}
    ]
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
