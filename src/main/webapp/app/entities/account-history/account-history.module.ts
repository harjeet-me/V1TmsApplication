import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TmsV1ApplicationSharedModule } from 'app/shared/shared.module';
import { AccountHistoryComponent } from './account-history.component';
import { AccountHistoryDetailComponent } from './account-history-detail.component';
import { AccountHistoryUpdateComponent } from './account-history-update.component';
import { AccountHistoryDeleteDialogComponent } from './account-history-delete-dialog.component';
import { accountHistoryRoute } from './account-history.route';

@NgModule({
  imports: [TmsV1ApplicationSharedModule, RouterModule.forChild(accountHistoryRoute)],
  declarations: [
    AccountHistoryComponent,
    AccountHistoryDetailComponent,
    AccountHistoryUpdateComponent,
    AccountHistoryDeleteDialogComponent
  ],
  entryComponents: [AccountHistoryDeleteDialogComponent]
})
export class TmsV1ApplicationAccountHistoryModule {}
