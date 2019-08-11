import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {AppRoutingModule} from './app-routing.module';
import {ClustersComponent} from './clusters/clusters.component';
import {ClusterDetailComponent} from './cluster-detail/cluster-detail.component';
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {HttpClientModule} from "@angular/common/http";
import {NgxGraphModule} from "@swimlane/ngx-graph";
import { ClustersGraphComponent } from './clusters-graph/clusters-graph.component';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {MatChipsModule, MatSidenavModule} from "@angular/material";

@NgModule({
  declarations: [
    AppComponent,
    ClustersComponent,
    ClusterDetailComponent,
    ClustersGraphComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    NgbModule,
    HttpClientModule,
    NgxGraphModule,
    MatSidenavModule,
    MatChipsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
