import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IInvoiceReport } from 'app/shared/model/invoice-report.model';

@Component({
  selector: 'jhi-invoice-report-detail',
  templateUrl: './invoice-report-detail.component.html'
})
export class InvoiceReportDetailComponent implements OnInit {
  invoiceReport: IInvoiceReport | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ invoiceReport }) => (this.invoiceReport = invoiceReport));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
