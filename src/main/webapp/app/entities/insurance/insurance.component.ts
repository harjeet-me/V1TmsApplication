import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IInsurance } from 'app/shared/model/insurance.model';
import { InsuranceService } from './insurance.service';
import { InsuranceDeleteDialogComponent } from './insurance-delete-dialog.component';

@Component({
  selector: 'jhi-insurance',
  templateUrl: './insurance.component.html'
})
export class InsuranceComponent implements OnInit, OnDestroy {
  insurances?: IInsurance[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected insuranceService: InsuranceService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected activatedRoute: ActivatedRoute
  ) {
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.queryParams['search']
        ? this.activatedRoute.snapshot.queryParams['search']
        : '';
  }

  loadAll(): void {
    if (this.currentSearch) {
      this.insuranceService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IInsurance[]>) => (this.insurances = res.body || []));
      return;
    }

    this.insuranceService.query().subscribe((res: HttpResponse<IInsurance[]>) => (this.insurances = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInInsurances();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IInsurance): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInInsurances(): void {
    this.eventSubscriber = this.eventManager.subscribe('insuranceListModification', () => this.loadAll());
  }

  delete(insurance: IInsurance): void {
    const modalRef = this.modalService.open(InsuranceDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.insurance = insurance;
  }
}
