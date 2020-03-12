import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAccounts } from 'app/shared/model/accounts.model';
import { AccountsService } from './accounts.service';

@Component({
  templateUrl: './accounts-delete-dialog.component.html'
})
export class AccountsDeleteDialogComponent {
  accounts?: IAccounts;

  constructor(protected accountsService: AccountsService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.accountsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('accountsListModification');
      this.activeModal.close();
    });
  }
}
