import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAccountHistory } from 'app/shared/model/account-history.model';
import { AccountHistoryService } from './account-history.service';

@Component({
  templateUrl: './account-history-delete-dialog.component.html'
})
export class AccountHistoryDeleteDialogComponent {
  accountHistory?: IAccountHistory;

  constructor(
    protected accountHistoryService: AccountHistoryService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.accountHistoryService.delete(id).subscribe(() => {
      this.eventManager.broadcast('accountHistoryListModification');
      this.activeModal.close();
    });
  }
}
