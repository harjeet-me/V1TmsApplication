import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInvoiceHistory } from 'app/shared/model/invoice-history.model';
import { InvoiceHistoryService } from './invoice-history.service';

@Component({
  templateUrl: './invoice-history-delete-dialog.component.html'
})
export class InvoiceHistoryDeleteDialogComponent {
  invoiceHistory?: IInvoiceHistory;

  constructor(
    protected invoiceHistoryService: InvoiceHistoryService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.invoiceHistoryService.delete(id).subscribe(() => {
      this.eventManager.broadcast('invoiceHistoryListModification');
      this.activeModal.close();
    });
  }
}
