import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITransactionsRecord } from 'app/shared/model/transactions-record.model';
import { TransactionsRecordService } from './transactions-record.service';

@Component({
  templateUrl: './transactions-record-delete-dialog.component.html'
})
export class TransactionsRecordDeleteDialogComponent {
  transactionsRecord?: ITransactionsRecord;

  constructor(
    protected transactionsRecordService: TransactionsRecordService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.transactionsRecordService.delete(id).subscribe(() => {
      this.eventManager.broadcast('transactionsRecordListModification');
      this.activeModal.close();
    });
  }
}
