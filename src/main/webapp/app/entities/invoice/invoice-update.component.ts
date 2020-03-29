import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IInvoice, Invoice } from 'app/shared/model/invoice.model';
import { InvoiceService } from './invoice.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { ITrip } from 'app/shared/model/trip.model';
import { TripService } from 'app/entities/trip/trip.service';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer/customer.service';

type SelectableEntity = ITrip | ICustomer;

@Component({
  selector: 'jhi-invoice-update',
  templateUrl: './invoice-update.component.html'
})
export class InvoiceUpdateComponent implements OnInit {
  isSaving = false;
  trips: ITrip[] = [];
  customers: ICustomer[] = [];
  invoiceDateDp: any;
  invoicePaidDateDp: any;
  invoiceDueDateDp: any;

  editForm = this.fb.group({
    id: [],
    orderNo: [],
    invoiceNo: [],
    taxRate: [],
    taxType: [],
    currency: [],
    invoiceTaxTotal: [],
    invoiceSubTotal: [],
    invoiceTotal: [],
    invoiceDate: [],
    invoicePaidDate: [],
    refOption1: [],
    refValue1: [],
    refOption2: [],
    refValue2: [],
    refOption3: [],
    refValue3: [],
    payRefNo: [],
    invoiceDueDate: [],
    status: [],
    invoicePdf: [],
    invoicePdfContentType: [],
    remarks: [],
    customerInfo: [],
    payterm: [],
    balance: [],
    advance: [],
    discount: [],
    createdOn: [],
    createdBy: [],
    updatedOn: [],
    updatedBy: [],
    trip: [],
    customer: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected invoiceService: InvoiceService,
    protected tripService: TripService,
    protected customerService: CustomerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ invoice }) => {
      if (!invoice.id) {
        const today = moment().startOf('day');
        invoice.createdOn = today;
        invoice.updatedOn = today;
      }

      this.updateForm(invoice);

      this.tripService.query().subscribe((res: HttpResponse<ITrip[]>) => (this.trips = res.body || []));

      this.customerService.query().subscribe((res: HttpResponse<ICustomer[]>) => (this.customers = res.body || []));
    });
  }

  updateForm(invoice: IInvoice): void {
    this.editForm.patchValue({
      id: invoice.id,
      orderNo: invoice.orderNo,
      invoiceNo: invoice.invoiceNo,
      taxRate: invoice.taxRate,
      taxType: invoice.taxType,
      currency: invoice.currency,
      invoiceTaxTotal: invoice.invoiceTaxTotal,
      invoiceSubTotal: invoice.invoiceSubTotal,
      invoiceTotal: invoice.invoiceTotal,
      invoiceDate: invoice.invoiceDate,
      invoicePaidDate: invoice.invoicePaidDate,
      refOption1: invoice.refOption1,
      refValue1: invoice.refValue1,
      refOption2: invoice.refOption2,
      refValue2: invoice.refValue2,
      refOption3: invoice.refOption3,
      refValue3: invoice.refValue3,
      payRefNo: invoice.payRefNo,
      invoiceDueDate: invoice.invoiceDueDate,
      status: invoice.status,
      invoicePdf: invoice.invoicePdf,
      invoicePdfContentType: invoice.invoicePdfContentType,
      remarks: invoice.remarks,
      customerInfo: invoice.customerInfo,
      payterm: invoice.payterm,
      balance: invoice.balance,
      advance: invoice.advance,
      discount: invoice.discount,
      createdOn: invoice.createdOn ? invoice.createdOn.format(DATE_TIME_FORMAT) : null,
      createdBy: invoice.createdBy,
      updatedOn: invoice.updatedOn ? invoice.updatedOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: invoice.updatedBy,
      trip: invoice.trip,
      customer: invoice.customer
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
    const invoice = this.createFromForm();
    if (invoice.id !== undefined) {
      this.subscribeToSaveResponse(this.invoiceService.update(invoice));
    } else {
      this.subscribeToSaveResponse(this.invoiceService.create(invoice));
    }
  }

  private createFromForm(): IInvoice {
    return {
      ...new Invoice(),
      id: this.editForm.get(['id'])!.value,
      orderNo: this.editForm.get(['orderNo'])!.value,
      invoiceNo: this.editForm.get(['invoiceNo'])!.value,
      taxRate: this.editForm.get(['taxRate'])!.value,
      taxType: this.editForm.get(['taxType'])!.value,
      currency: this.editForm.get(['currency'])!.value,
      invoiceTaxTotal: this.editForm.get(['invoiceTaxTotal'])!.value,
      invoiceSubTotal: this.editForm.get(['invoiceSubTotal'])!.value,
      invoiceTotal: this.editForm.get(['invoiceTotal'])!.value,
      invoiceDate: this.editForm.get(['invoiceDate'])!.value,
      invoicePaidDate: this.editForm.get(['invoicePaidDate'])!.value,
      refOption1: this.editForm.get(['refOption1'])!.value,
      refValue1: this.editForm.get(['refValue1'])!.value,
      refOption2: this.editForm.get(['refOption2'])!.value,
      refValue2: this.editForm.get(['refValue2'])!.value,
      refOption3: this.editForm.get(['refOption3'])!.value,
      refValue3: this.editForm.get(['refValue3'])!.value,
      payRefNo: this.editForm.get(['payRefNo'])!.value,
      invoiceDueDate: this.editForm.get(['invoiceDueDate'])!.value,
      status: this.editForm.get(['status'])!.value,
      invoicePdfContentType: this.editForm.get(['invoicePdfContentType'])!.value,
      invoicePdf: this.editForm.get(['invoicePdf'])!.value,
      remarks: this.editForm.get(['remarks'])!.value,
      customerInfo: this.editForm.get(['customerInfo'])!.value,
      payterm: this.editForm.get(['payterm'])!.value,
      balance: this.editForm.get(['balance'])!.value,
      advance: this.editForm.get(['advance'])!.value,
      discount: this.editForm.get(['discount'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? moment(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      createdBy: this.editForm.get(['createdBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? moment(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      trip: this.editForm.get(['trip'])!.value,
      customer: this.editForm.get(['customer'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInvoice>>): void {
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
