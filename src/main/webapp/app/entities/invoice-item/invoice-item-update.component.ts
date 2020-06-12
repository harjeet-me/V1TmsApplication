import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IInvoiceItem, InvoiceItem } from 'app/shared/model/invoice-item.model';
import { InvoiceItemService } from './invoice-item.service';
import { IInvoice } from 'app/shared/model/invoice.model';
import { InvoiceService } from 'app/entities/invoice/invoice.service';

@Component({
  selector: 'jhi-invoice-item-update',
  templateUrl: './invoice-item-update.component.html'
})
export class InvoiceItemUpdateComponent implements OnInit {
  isSaving = false;
  invoices: IInvoice[] = [];

  editForm = this.fb.group({
    id: [],
    itemName: [],
    description: [],
    qty: [],
    price: [],
    discount: [],
    total: [],
    createdDate: [],
    createdBy: [],
    lastModifiedDate: [],
    lastModifiedBy: [],
    invoice: []
  });

  constructor(
    protected invoiceItemService: InvoiceItemService,
    protected invoiceService: InvoiceService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ invoiceItem }) => {
      if (!invoiceItem.id) {
        const today = moment().startOf('day');
        invoiceItem.createdDate = today;
        invoiceItem.lastModifiedDate = today;
      }

      this.updateForm(invoiceItem);

      this.invoiceService.query().subscribe((res: HttpResponse<IInvoice[]>) => (this.invoices = res.body || []));
    });
  }

  updateForm(invoiceItem: IInvoiceItem): void {
    this.editForm.patchValue({
      id: invoiceItem.id,
      itemName: invoiceItem.itemName,
      description: invoiceItem.description,
      qty: invoiceItem.qty,
      price: invoiceItem.price,
      discount: invoiceItem.discount,
      total: invoiceItem.total,
      createdDate: invoiceItem.createdDate ? invoiceItem.createdDate.format(DATE_TIME_FORMAT) : null,
      createdBy: invoiceItem.createdBy,
      lastModifiedDate: invoiceItem.lastModifiedDate ? invoiceItem.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: invoiceItem.lastModifiedBy,
      invoice: invoiceItem.invoice
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const invoiceItem = this.createFromForm();
    if (invoiceItem.id !== undefined) {
      this.subscribeToSaveResponse(this.invoiceItemService.update(invoiceItem));
    } else {
      this.subscribeToSaveResponse(this.invoiceItemService.create(invoiceItem));
    }
  }

  private createFromForm(): IInvoiceItem {
    return {
      ...new InvoiceItem(),
      id: this.editForm.get(['id'])!.value,
      itemName: this.editForm.get(['itemName'])!.value,
      description: this.editForm.get(['description'])!.value,
      qty: this.editForm.get(['qty'])!.value,
      price: this.editForm.get(['price'])!.value,
      discount: this.editForm.get(['discount'])!.value,
      total: this.editForm.get(['total'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? moment(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      createdBy: this.editForm.get(['createdBy'])!.value,
      lastModifiedDate: this.editForm.get(['lastModifiedDate'])!.value
        ? moment(this.editForm.get(['lastModifiedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      invoice: this.editForm.get(['invoice'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInvoiceItem>>): void {
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
}
