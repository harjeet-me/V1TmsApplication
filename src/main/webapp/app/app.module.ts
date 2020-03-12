import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { TmsV1ApplicationSharedModule } from 'app/shared/shared.module';
import { TmsV1ApplicationCoreModule } from 'app/core/core.module';
import { TmsV1ApplicationAppRoutingModule } from './app-routing.module';
import { TmsV1ApplicationHomeModule } from './home/home.module';
import { TmsV1ApplicationEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    TmsV1ApplicationSharedModule,
    TmsV1ApplicationCoreModule,
    TmsV1ApplicationHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    TmsV1ApplicationEntityModule,
    TmsV1ApplicationAppRoutingModule
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  bootstrap: [MainComponent]
})
export class TmsV1ApplicationAppModule {}
