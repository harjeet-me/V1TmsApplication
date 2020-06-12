import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IInvoiceHistory, InvoiceHistory } from 'app/shared/model/invoice-history.model';
import { InvoiceHistoryService } from './invoice-history.service';
import { IInvoice } from 'app/shared/model/invoice.model';
import { InvoiceService } from 'app/entities/invoice/invoice.service';

type SelectableEntity = IInvoiceHistory | IInvoice;

@Component({
  selector: 'jhi-invoice-history-update',
  templateUrl: './invoice-history-update.component.html'
})
export class InvoiceHistoryUpdateComponent implements OnInit {
  isSaving = false;
  previous: IInvoiceHistory[] = [];
  nexts: IInvoiceHistory[] = [];
  invoices: IInvoice[] = [];

  editForm = this.fb.group({
    id: [],
    status: [],
    comment: [],
    createdDate: [],
    createdBy: [],
    lastModifiedDate: [],
    lastModifiedBy: [],
    previous: [],
    next: [],
    invoice: []
  });

  constructor(
    protected invoiceHistoryService: InvoiceHistoryService,
    protected invoiceService: InvoiceService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ invoiceHistory }) => {
      if (!invoiceHistory.id) {
        const today = moment().startOf('day');
        invoiceHistory.createdDate = today;
        invoiceHistory.lastModifiedDate = today;
      }

      this.updateForm(invoiceHistory);

      this.invoiceHistoryService
        .query({ filter: 'invoicehistory-is-null' })
        .pipe(
          map((res: HttpResponse<IInvoiceHistory[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IInvoiceHistory[]) => {
          if (!invoiceHistory.previous || !invoiceHistory.previous.id) {
            this.previous = resBody;
          } else {
            this.invoiceHistoryService
              .find(invoiceHistory.previous.id)
              .pipe(
                map((subRes: HttpResponse<IInvoiceHistory>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IInvoiceHistory[]) => (this.previous = concatRes));
          }
        });

      this.invoiceHistoryService
        .query({ filter: 'invoicehistory-is-null' })
        .pipe(
          map((res: HttpResponse<IInvoiceHistory[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IInvoiceHistory[]) => {
          if (!invoiceHistory.next || !invoiceHistory.next.id) {
            this.nexts = resBody;
          } else {
            this.invoiceHistoryService
              .find(invoiceHistory.next.id)
              .pipe(
                map((subRes: HttpResponse<IInvoiceHistory>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IInvoiceHistory[]) => (this.nexts = concatRes));
          }
        });

      this.invoiceService.query().subscribe((res: HttpResponse<IInvoice[]>) => (this.invoices = res.body || []));
    });
  }

  updateForm(invoiceHistory: IInvoiceHistory): void {
    this.editForm.patchValue({
      id: invoiceHistory.id,
      status: invoiceHistory.status,
      comment: invoiceHistory.comment,
      createdDate: invoiceHistory.createdDate ? invoiceHistory.createdDate.format(DATE_TIME_FORMAT) : null,
      createdBy: invoiceHistory.createdBy,
      lastModifiedDate: invoiceHistory.lastModifiedDate ? invoiceHistory.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: invoiceHistory.lastModifiedBy,
      previous: invoiceHistory.previous,
      next: invoiceHistory.next,
      invoice: invoiceHistory.invoice
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const invoiceHistory = this.createFromForm();
    if (invoiceHistory.id !== undefined) {
      this.subscribeToSaveResponse(this.invoiceHistoryService.update(invoiceHistory));
    } else {
      this.subscribeToSaveResponse(this.invoiceHistoryService.create(invoiceHistory));
    }
  }

  private createFromForm(): IInvoiceHistory {
    return {
      ...new InvoiceHistory(),
      id: this.editForm.get(['id'])!.value,
      status: this.editForm.get(['status'])!.value,
      comment: this.editForm.get(['comment'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? moment(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      createdBy: this.editForm.get(['createdBy'])!.value,
      lastModifiedDate: this.editForm.get(['lastModifiedDate'])!.value
        ? moment(this.editForm.get(['lastModifiedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      previous: this.editForm.get(['previous'])!.value,
      next: this.editForm.get(['next'])!.value,
      invoice: this.editForm.get(['invoice'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInvoiceHistory>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
