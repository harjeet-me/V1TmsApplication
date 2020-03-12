import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICompanyProfile } from 'app/shared/model/company-profile.model';
import { CompanyProfileService } from './company-profile.service';

@Component({
  templateUrl: './company-profile-delete-dialog.component.html'
})
export class CompanyProfileDeleteDialogComponent {
  companyProfile?: ICompanyProfile;

  constructor(
    protected companyProfileService: CompanyProfileService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.companyProfileService.delete(id).subscribe(() => {
      this.eventManager.broadcast('companyProfileListModification');
      this.activeModal.close();
    });
  }
}
