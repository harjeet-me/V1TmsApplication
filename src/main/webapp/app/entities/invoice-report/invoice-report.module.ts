import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TmsV1ApplicationSharedModule } from 'app/shared/shared.module';
import { InvoiceReportComponent } from './invoice-report.component';
import { InvoiceReportDetailComponent } from './invoice-report-detail.component';
import { InvoiceReportUpdateComponent } from './invoice-report-update.component';
import { InvoiceReportDeleteDialogComponent } from './invoice-report-delete-dialog.component';
import { invoiceReportRoute } from './invoice-report.route';

@NgModule({
  imports: [TmsV1ApplicationSharedModule, RouterModule.forChild(invoiceReportRoute)],
  declarations: [InvoiceReportComponent, InvoiceReportDetailComponent, InvoiceReportUpdateComponent, InvoiceReportDeleteDialogComponent],
  entryComponents: [InvoiceReportDeleteDialogComponent]
})
export class TmsV1ApplicationInvoiceReportModule {}
