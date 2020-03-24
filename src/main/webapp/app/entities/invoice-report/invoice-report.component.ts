import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IInvoiceReport } from 'app/shared/model/invoice-report.model';
import { InvoiceReportService } from './invoice-report.service';
import { InvoiceReportDeleteDialogComponent } from './invoice-report-delete-dialog.component';

@Component({
  selector: 'jhi-invoice-report',
  templateUrl: './invoice-report.component.html'
})
export class InvoiceReportComponent implements OnInit, OnDestroy {
  invoiceReports?: IInvoiceReport[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected invoiceReportService: InvoiceReportService,
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
      this.invoiceReportService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IInvoiceReport[]>) => (this.invoiceReports = res.body || []));
      return;
    }

    this.invoiceReportService.query().subscribe((res: HttpResponse<IInvoiceReport[]>) => (this.invoiceReports = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInInvoiceReports();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IInvoiceReport): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInInvoiceReports(): void {
    this.eventSubscriber = this.eventManager.subscribe('invoiceReportListModification', () => this.loadAll());
  }

  delete(invoiceReport: IInvoiceReport): void {
    const modalRef = this.modalService.open(InvoiceReportDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.invoiceReport = invoiceReport;
  }
}
