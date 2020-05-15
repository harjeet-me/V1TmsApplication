import { InvoiceItemService } from './../invoice-item/invoice-item.service';
import { InvoiceItem } from './../../shared/model/invoice-item.model';
import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IInvoice, Invoice } from 'app/shared/model/invoice.model';
import { InvoiceService } from './invoice.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IEmail } from 'app/shared/model/email.model';
import { EmailService } from 'app/entities/email/email.service';
import { ITrip } from 'app/shared/model/trip.model';
import { TripService } from 'app/entities/trip/trip.service';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer/customer.service';
import { IProductItem } from 'app/shared/model/product-item.model';
import { ProductItemService } from 'app/entities/product-item/product-item.service';

type SelectableEntity = IEmail | ITrip | ICustomer;

@Component({
  selector: 'jhi-invoice-update',
  templateUrl: './invoice-update.component.html'
})
export class InvoiceUpdateComponent implements OnInit {
  indexSize = 1;
  grantTotal = 0;
  advanceTotal = 0;
  // @ViewChild('content', {static: false}) content: ElementRef;
  newDynamic: any = new InvoiceItem();
  dynamicArray: Array<InvoiceItem> = [];
  j = 1;
  chargeList: any = [];
  isSaving = false;
  notifications: IEmail[] = [];
  trips: ITrip[] = [];
  customers: ICustomer[] = [];
  invoiceDateDp: any;
  invoicePaidDateDp: any;
  invoiceDueDateDp: any;
  invoiceTotal = 5000;
  productitems: IProductItem[] = [];
  customerName = '';
  selectedCustomerName = '';
  selectedCustomer: any;
  maxId: any;
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
    createdOn: [],
    createdBy: [],
    updatedOn: [],
    updatedBy: [],
    notification: [],
    trip: [],
    customer: [],
    invoiceItems: [],
    charges: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected invoiceService: InvoiceService,
    protected emailService: EmailService,
    protected tripService: TripService,
    protected customerService: CustomerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    protected productItemService: ProductItemService,
    private invoiceItemService: InvoiceItemService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ invoice }) => {
      if (!invoice.id) {
        const today = moment().startOf('day');
        invoice.createdOn = today;
        invoice.updatedOn = today;
      }
      this.editForm.markAsDirty();

      this.grantTotal = 0;

      this.newDynamic = { id: null, itemName: this.dynamicArray.length + 1, description: 'SFO TO DEL', price: 20, qty: 1, total: 0 };
      // this.newDynamic = {index:this.indexSize,title1: "SFO TO DEL", title2: "20",title3:"40"};
      this.dynamicArray.push(this.newDynamic);

      // this.invoiceService.findMaxId().subscribe(data => (this.maxId = data?.body?.id));

      this.updateForm(invoice);

      if (invoice.invoiceItems !== undefined) {
        this.dynamicArray = invoice.invoiceItems;
      } else {
        this.newDynamic = { id: null, itemName: 'Load Move ', description: 'Load Move ', price: 207, qty: 1, total: 0 };
        this.dynamicArray.push(this.newDynamic);
      }
      this.calculateTotal();
      this.emailService
        .query({ filter: 'invoice-is-null' })
        .pipe(
          map((res: HttpResponse<IEmail[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IEmail[]) => {
          if (!invoice.notification || !invoice.notification.id) {
            this.notifications = resBody;
          } else {
            this.emailService
              .find(invoice.notification.id)
              .pipe(
                map((subRes: HttpResponse<IEmail>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IEmail[]) => (this.notifications = concatRes));
          }
        });

      this.tripService.query().subscribe((res: HttpResponse<ITrip[]>) => (this.trips = res.body || []));

      this.customerService.query().subscribe((res: HttpResponse<ICustomer[]>) => (this.customers = res.body || []));
      this.productItemService.query().subscribe((res: HttpResponse<IProductItem[]>) => (this.productitems = res.body || []));
      this.customerName = invoice?.customer?.company;
      this.selectedCustomer = invoice?.customer;
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
      createdOn: invoice.createdOn ? invoice.createdOn.format(DATE_TIME_FORMAT) : null,
      createdBy: invoice.createdBy,
      updatedOn: invoice.updatedOn ? invoice.updatedOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: invoice.updatedBy,
      notification: invoice.notification,
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
        new JhiEventWithContent<AlertError>('jiotmsApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    if (this.editForm.invalid) {
      return;
    }
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
      invoiceTotal: this.grantTotal,
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
      createdOn: this.editForm.get(['createdOn'])!.value ? moment(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      createdBy: this.editForm.get(['createdBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? moment(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      notification: this.editForm.get(['notification'])!.value,
      trip: this.editForm.get(['trip'])!.value,
      customer: this.selectedCustomer,

      invoiceItems: this.dynamicArray
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

  addRow(index: number): any {
    this.indexSize = index + 1;
    // this.newDynamic = { id : 2 , itemName: this.dynamicArray.length + 1, description: 'SFO TO DEL', price: '20', total: '40' };

    this.dynamicArray.push({ itemName: '', description: '', price: 0, qty: 1, total: 0 });
    this.calculateTotal();
    return true;
  }

  deleteRow(index: number): any {
    if (this.dynamicArray.length === 1) {
      this.calculateTotal();
      return false;
    } else {
      const id = this.dynamicArray[index].id;
      if (id !== undefined) {
        this.invoiceItemService.delete(id).subscribe(() => {});
      }

      this.dynamicArray.splice(index, 1);
      this.calculateTotal();
      return true;
    }
  }
  calculateTotal(): any {
    this.grantTotal = 0;
    let tt: any;
    for (let _i = 0; _i < this.dynamicArray.length; _i++) {
      tt = this.dynamicArray[_i];
      if (tt !== null || tt !== undefined) {
        tt.invoice = null;
        this.dynamicArray[_i].total = Number(this.dynamicArray[_i].price) * Number(this.dynamicArray[_i].qty);
        this.grantTotal = this.grantTotal + Number(tt?.total);
      }
    }
  }

  onSelectChange(lineItemValue: string, index: number): any {
    let myItem: any;
    if (lineItemValue !== null) {
      myItem = this.productitems.find(item => item.itemName === lineItemValue);
      this.dynamicArray[index] = {
        itemName: myItem.itemName,
        description: myItem.description,
        price: myItem.price,
        qty: 1,
        total: myItem.price
      };
    }
    this.calculateTotal();
  }
  onCustomerChange(): any {
    let myItem: any;
    if (this.customerName !== null) {
      myItem = this.customers.find(item => item.company === this.customerName);
      this.selectedCustomer = myItem;
      this.selectedCustomerName =
        myItem.address + ' ' + myItem.streetAddress + ' \n ' + myItem.city + ' ' + myItem.stateProvince + ' ' + myItem.postalCode;

      this.editForm?.get('customerInfo')?.setValue(this.selectedCustomerName);
    }
  }
}
