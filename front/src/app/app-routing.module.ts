import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ImageGalleryComponent } from "./image-gallery/image-gallery.component";
import { ImageComponent } from "./image/image/image.component";
import { TagListComponent } from "./tag-list/tag-list.component";
import { ImagePanelComponent } from "./image/image-panel/image-panel.component";

const routes: Routes = [
  {
    path: '',
    children: [
      {path: '', component: ImageGalleryComponent, outlet: 'main'},
      {path: '', component: TagListComponent, outlet: 'sidebar'}
    ]
  },
  {
    path: 'image/:id',
    children: [
      {path: '', component: ImageComponent, outlet: 'main'},
      {path: '', component: ImagePanelComponent, outlet: 'sidebar'}
    ]
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
