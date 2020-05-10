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

@Component({
  selector: 'jhi-payment-update',
  templateUrl: './payment-update.component.html'
})
export class PaymentUpdateComponent implements OnInit {
  isSaving = false;
  invoicePaidDateDp: any;

  editForm = this.fb.group({
    id: [],
    invoiceNo: [],
    paymentAmt: [],
    invoicePaidDate: [],
    payRefNo: [],
    status: [],
    createdOn: [],
    createdBy: [],
    updatedOn: [],
    updatedBy: []
  });

  constructor(protected paymentService: PaymentService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ payment }) => {
      if (!payment.id) {
        const today = moment().startOf('day');
        payment.createdOn = today;
        payment.updatedOn = today;
      }

      this.updateForm(payment);
    });
  }

  updateForm(payment: IPayment): void {
    this.editForm.patchValue({
      id: payment.id,
      invoiceNo: payment.invoiceNo,
      paymentAmt: payment.paymentAmt,
      invoicePaidDate: payment.invoicePaidDate,
      payRefNo: payment.payRefNo,
      status: payment.status,
      createdOn: payment.createdOn ? payment.createdOn.format(DATE_TIME_FORMAT) : null,
      createdBy: payment.createdBy,
      updatedOn: payment.updatedOn ? payment.updatedOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: payment.updatedBy
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
      paymentAmt: this.editForm.get(['paymentAmt'])!.value,
      invoicePaidDate: this.editForm.get(['invoicePaidDate'])!.value,
      payRefNo: this.editForm.get(['payRefNo'])!.value,
      status: this.editForm.get(['status'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? moment(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      createdBy: this.editForm.get(['createdBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? moment(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value
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
}
