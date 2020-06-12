import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IPayment, Payment } from 'app/shared/model/payment.model';
import { PaymentService } from './payment.service';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer/customer.service';

@Component({
  selector: 'jhi-payment-update',
  templateUrl: './payment-update.component.html'
})
export class PaymentUpdateComponent implements OnInit {
  isSaving = false;
  customers: ICustomer[] = [];
  payDateDp: any;

  editForm = this.fb.group({
    id: [],
    invoiceNo: [],
    payDate: [],
    payRefNo: [],
    mode: [],
    ammount: [],
    unusedAmmount: [],
    createdDate: [],
    createdBy: [],
    lastModifiedDate: [],
    lastModifiedBy: [],
    customer: []
  });

  constructor(
    protected paymentService: PaymentService,
    protected customerService: CustomerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ payment }) => {
      if (!payment.id) {
        const today = moment().startOf('day');
        payment.createdDate = today;
        payment.lastModifiedDate = today;
      }

      this.updateForm(payment);

      this.customerService.query().subscribe((res: HttpResponse<ICustomer[]>) => (this.customers = res.body || []));
    });
  }

  updateForm(payment: IPayment): void {
    this.editForm.patchValue({
      id: payment.id,
      invoiceNo: payment.invoiceNo,
      payDate: payment.payDate,
      payRefNo: payment.payRefNo,
      mode: payment.mode,
      ammount: payment.ammount,
      unusedAmmount: payment.unusedAmmount,
      createdDate: payment.createdDate ? payment.createdDate.format(DATE_TIME_FORMAT) : null,
      createdBy: payment.createdBy,
      lastModifiedDate: payment.lastModifiedDate ? payment.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: payment.lastModifiedBy,
      customer: payment.customer
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const payment = this.createFromForm();
    if (payment.id !== undefined) {
      this.subscribeToSaveResponse(this.paymentService.update(payment));
    } else {
      this.subscribeToSaveResponse(this.paymentService.create(payment));
    }
  }

  private createFromForm(): IPayment {
    return {
      ...new Payment(),
      id: this.editForm.get(['id'])!.value,
      invoiceNo: this.editForm.get(['invoiceNo'])!.value,
      payDate: this.editForm.get(['payDate'])!.value,
      payRefNo: this.editForm.get(['payRefNo'])!.value,
      mode: this.editForm.get(['mode'])!.value,
      ammount: this.editForm.get(['ammount'])!.value,
      unusedAmmount: this.editForm.get(['unusedAmmount'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? moment(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      createdBy: this.editForm.get(['createdBy'])!.value,
      lastModifiedDate: this.editForm.get(['lastModifiedDate'])!.value
        ? moment(this.editForm.get(['lastModifiedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      customer: this.editForm.get(['customer'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPayment>>): void {
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

  trackById(index: number, item: ICustomer): any {
    return item.id;
  }
}
