import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEmail } from 'app/shared/model/email.model';
import { EmailService } from './email.service';

@Component({
  templateUrl: './email-delete-dialog.component.html'
})
export class EmailDeleteDialogComponent {
  email?: IEmail;

  constructor(protected emailService: EmailService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.emailService.delete(id).subscribe(() => {
      this.eventManager.broadcast('emailListModification');
      this.activeModal.close();
    });
  }
}
