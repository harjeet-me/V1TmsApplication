import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IInvoiceReport, InvoiceReport } from 'app/shared/model/invoice-report.model';
import { InvoiceReportService } from './invoice-report.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IInvoice } from 'app/shared/model/invoice.model';
import { InvoiceService } from 'app/entities/invoice/invoice.service';

@Component({
  selector: 'jhi-invoice-report-update',
  templateUrl: './invoice-report-update.component.html'
})
export class InvoiceReportUpdateComponent implements OnInit {
  isSaving = false;
  invoices: IInvoice[] = [];
  fromDateDp: any;
  toDateDp: any;

  editForm = this.fb.group({
    id: [],
    fromDate: [],
    toDate: [],
    remarks: [],
    invoiceReport: [],
    invoiceReportContentType: [],
    createdOn: [],
    createdBy: [],
    updatedOn: [],
    updatedBy: [],
    invoices: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected invoiceReportService: InvoiceReportService,
    protected invoiceService: InvoiceService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ invoiceReport }) => {
      if (!invoiceReport.id) {
        const today = moment().startOf('day');
        invoiceReport.createdOn = today;
        invoiceReport.updatedOn = today;
      }

      this.updateForm(invoiceReport);

      this.invoiceService.query().subscribe((res: HttpResponse<IInvoice[]>) => (this.invoices = res.body || []));
    });
  }

  updateForm(invoiceReport: IInvoiceReport): void {
    this.editForm.patchValue({
      id: invoiceReport.id,
      fromDate: invoiceReport.fromDate,
      toDate: invoiceReport.toDate,
      remarks: invoiceReport.remarks,
      invoiceReport: invoiceReport.invoiceReport,
      invoiceReportContentType: invoiceReport.invoiceReportContentType,
      createdOn: invoiceReport.createdOn ? invoiceReport.createdOn.format(DATE_TIME_FORMAT) : null,
      createdBy: invoiceReport.createdBy,
      updatedOn: invoiceReport.updatedOn ? invoiceReport.updatedOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: invoiceReport.updatedBy,
      invoices: invoiceReport.invoices
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('tmsV1ApplicationApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const invoiceReport = this.createFromForm();
    if (invoiceReport.id !== undefined) {
      this.subscribeToSaveResponse(this.invoiceReportService.update(invoiceReport));
    } else {
      this.subscribeToSaveResponse(this.invoiceReportService.create(invoiceReport));
    }
  }

  private createFromForm(): IInvoiceReport {
    return {
      ...new InvoiceReport(),
      id: this.editForm.get(['id'])!.value,
      fromDate: this.editForm.get(['fromDate'])!.value,
      toDate: this.editForm.get(['toDate'])!.value,
      remarks: this.editForm.get(['remarks'])!.value,
      invoiceReportContentType: this.editForm.get(['invoiceReportContentType'])!.value,
      invoiceReport: this.editForm.get(['invoiceReport'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? moment(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      createdBy: this.editForm.get(['createdBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? moment(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      invoices: this.editForm.get(['invoices'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInvoiceReport>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IInvoice): any {
    return item.id;
  }

  getSelected(selectedVals: IInvoice[], option: IInvoice): IInvoice {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
