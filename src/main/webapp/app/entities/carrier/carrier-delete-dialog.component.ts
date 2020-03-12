import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICarrier } from 'app/shared/model/carrier.model';
import { CarrierService } from './carrier.service';

@Component({
  templateUrl: './carrier-delete-dialog.component.html'
})
export class CarrierDeleteDialogComponent {
  carrier?: ICarrier;

  constructor(protected carrierService: CarrierService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.carrierService.delete(id).subscribe(() => {
      this.eventManager.broadcast('carrierListModification');
      this.activeModal.close();
    });
  }
}
