import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IInvoiceItem } from 'app/shared/model/invoice-item.model';
import { InvoiceItemService } from './invoice-item.service';
import { InvoiceItemDeleteDialogComponent } from './invoice-item-delete-dialog.component';

@Component({
  selector: 'jhi-invoice-item',
  templateUrl: './invoice-item.component.html'
})
export class InvoiceItemComponent implements OnInit, OnDestroy {
  invoiceItems?: IInvoiceItem[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected invoiceItemService: InvoiceItemService,
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
      this.invoiceItemService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IInvoiceItem[]>) => (this.invoiceItems = res.body || []));
      return;
    }

    this.invoiceItemService.query().subscribe((res: HttpResponse<IInvoiceItem[]>) => (this.invoiceItems = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInInvoiceItems();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IInvoiceItem): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInInvoiceItems(): void {
    this.eventSubscriber = this.eventManager.subscribe('invoiceItemListModification', () => this.loadAll());
  }

  delete(invoiceItem: IInvoiceItem): void {
    const modalRef = this.modalService.open(InvoiceItemDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.invoiceItem = invoiceItem;
  }
}
