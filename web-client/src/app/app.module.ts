import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {WipComponent} from './header/wip/wip.component';
import {FormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";
import {TitleComponent} from './header/title/title.component';
import {TabsComponent} from './header/tabs/tabs.component';
import {HomeComponent} from './home/home.component';
import {AppRoutingModule} from "./app.routes";
import {BlogComponent} from './blog/blog.component';
import {StubBlogService} from "./blog/blog.service";
import {BlogListComponent} from './blog/blog-list/blog-list.component';
import { BlogListItemComponent } from './blog/blog-list/blog-list-item/blog-list-item.component';
import { BlogEntryViewComponent } from './blog/blog-entry-view/blog-entry-view.component';
import {BlogEntryBlankComponent} from "./blog/blog-entry-view/blog-entry-blank.component";
import {BlogEntryResolverComponent} from "./blog/blog-entry-view/blog-entry-resolver.component";
import {BlogEntryResolver} from "./blog/resolvers";

@NgModule({
  declarations: [
    AppComponent,
    WipComponent,
    TitleComponent,
    TabsComponent,
    HomeComponent,
    BlogComponent,
    BlogListComponent,
    BlogListItemComponent,
    BlogEntryViewComponent,
    BlogEntryBlankComponent,
    BlogEntryResolverComponent
  ],
  imports: [
    AppRoutingModule,
    BrowserModule,
    FormsModule,
    HttpModule,
  ],
  providers: [
    {provide: "BlogService", useClass: StubBlogService},
    BlogEntryResolver
  ],
  bootstrap: [AppComponent]
})
export class AppModule {

}
