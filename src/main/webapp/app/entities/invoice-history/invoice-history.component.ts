import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IInvoiceHistory } from 'app/shared/model/invoice-history.model';
import { InvoiceHistoryService } from './invoice-history.service';
import { InvoiceHistoryDeleteDialogComponent } from './invoice-history-delete-dialog.component';

@Component({
  selector: 'jhi-invoice-history',
  templateUrl: './invoice-history.component.html'
})
export class InvoiceHistoryComponent implements OnInit, OnDestroy {
  invoiceHistories?: IInvoiceHistory[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected invoiceHistoryService: InvoiceHistoryService,
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
      this.invoiceHistoryService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IInvoiceHistory[]>) => (this.invoiceHistories = res.body || []));
      return;
    }

    this.invoiceHistoryService.query().subscribe((res: HttpResponse<IInvoiceHistory[]>) => (this.invoiceHistories = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInInvoiceHistories();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IInvoiceHistory): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInInvoiceHistories(): void {
    this.eventSubscriber = this.eventManager.subscribe('invoiceHistoryListModification', () => this.loadAll());
  }

  delete(invoiceHistory: IInvoiceHistory): void {
    const modalRef = this.modalService.open(InvoiceHistoryDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.invoiceHistory = invoiceHistory;
  }
}
