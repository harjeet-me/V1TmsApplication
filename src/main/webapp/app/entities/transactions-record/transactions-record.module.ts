import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TmsV1ApplicationSharedModule } from 'app/shared/shared.module';
import { TransactionsRecordComponent } from './transactions-record.component';
import { TransactionsRecordDetailComponent } from './transactions-record-detail.component';
import { TransactionsRecordUpdateComponent } from './transactions-record-update.component';
import { TransactionsRecordDeleteDialogComponent } from './transactions-record-delete-dialog.component';
import { transactionsRecordRoute } from './transactions-record.route';

@NgModule({
  imports: [TmsV1ApplicationSharedModule, RouterModule.forChild(transactionsRecordRoute)],
  declarations: [
    TransactionsRecordComponent,
    TransactionsRecordDetailComponent,
    TransactionsRecordUpdateComponent,
    TransactionsRecordDeleteDialogComponent
  ],
  entryComponents: [TransactionsRecordDeleteDialogComponent]
})
export class TmsV1ApplicationTransactionsRecordModule {}
