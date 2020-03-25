import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInvoiceReport } from 'app/shared/model/invoice-report.model';
import { InvoiceReportService } from './invoice-report.service';

@Component({
  templateUrl: './invoice-report-delete-dialog.component.html'
})
export class InvoiceReportDeleteDialogComponent {
  invoiceReport?: IInvoiceReport;

  constructor(
    protected invoiceReportService: InvoiceReportService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.invoiceReportService.delete(id).subscribe(() => {
      this.eventManager.broadcast('invoiceReportListModification');
      this.activeModal.close();
    });
  }
}
