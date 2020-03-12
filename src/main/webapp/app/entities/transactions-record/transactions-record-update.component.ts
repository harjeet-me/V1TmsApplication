import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ITransactionsRecord, TransactionsRecord } from 'app/shared/model/transactions-record.model';
import { TransactionsRecordService } from './transactions-record.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer/customer.service';
import { IAccounts } from 'app/shared/model/accounts.model';
import { AccountsService } from 'app/entities/accounts/accounts.service';
import { IInvoice } from 'app/shared/model/invoice.model';
import { InvoiceService } from 'app/entities/invoice/invoice.service';

type SelectableEntity = ICustomer | IAccounts | IInvoice;

@Component({
  selector: 'jhi-transactions-record-update',
  templateUrl: './transactions-record-update.component.html'
})
export class TransactionsRecordUpdateComponent implements OnInit {
  isSaving = false;
  customers: ICustomer[] = [];
  accounts: IAccounts[] = [];
  invoices: IInvoice[] = [];
  txCreatedDateDp: any;
  txCompletedDateDp: any;

  editForm = this.fb.group({
    id: [],
    txType: [],
    description: [],
    txAmmount: [],
    txRefNo: [],
    txCreatedBy: [],
    txCreatedDate: [],
    txCompletedBy: [],
    txCompletedDate: [],
    status: [],
    txDoc: [],
    txDocContentType: [],
    currency: [],
    remarks: [],
    createdOn: [],
    createdBy: [],
    updatedOn: [],
    updatedBy: [],
    customer: [],
    account: [],
    invoice: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected transactionsRecordService: TransactionsRecordService,
    protected customerService: CustomerService,
    protected accountsService: AccountsService,
    protected invoiceService: InvoiceService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transactionsRecord }) => {
      if (!transactionsRecord.id) {
        const today = moment().startOf('day');
        transactionsRecord.createdOn = today;
        transactionsRecord.updatedOn = today;
      }

      this.updateForm(transactionsRecord);

      this.customerService.query().subscribe((res: HttpResponse<ICustomer[]>) => (this.customers = res.body || []));

      this.accountsService.query().subscribe((res: HttpResponse<IAccounts[]>) => (this.accounts = res.body || []));

      this.invoiceService.query().subscribe((res: HttpResponse<IInvoice[]>) => (this.invoices = res.body || []));
    });
  }

  updateForm(transactionsRecord: ITransactionsRecord): void {
    this.editForm.patchValue({
      id: transactionsRecord.id,
      txType: transactionsRecord.txType,
      description: transactionsRecord.description,
      txAmmount: transactionsRecord.txAmmount,
      txRefNo: transactionsRecord.txRefNo,
      txCreatedBy: transactionsRecord.txCreatedBy,
      txCreatedDate: transactionsRecord.txCreatedDate,
      txCompletedBy: transactionsRecord.txCompletedBy,
      txCompletedDate: transactionsRecord.txCompletedDate,
      status: transactionsRecord.status,
      txDoc: transactionsRecord.txDoc,
      txDocContentType: transactionsRecord.txDocContentType,
      currency: transactionsRecord.currency,
      remarks: transactionsRecord.remarks,
      createdOn: transactionsRecord.createdOn ? transactionsRecord.createdOn.format(DATE_TIME_FORMAT) : null,
      createdBy: transactionsRecord.createdBy,
      updatedOn: transactionsRecord.updatedOn ? transactionsRecord.updatedOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: transactionsRecord.updatedBy,
      customer: transactionsRecord.customer,
      account: transactionsRecord.account,
      invoice: transactionsRecord.invoice
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
    const transactionsRecord = this.createFromForm();
    if (transactionsRecord.id !== undefined) {
      this.subscribeToSaveResponse(this.transactionsRecordService.update(transactionsRecord));
    } else {
      this.subscribeToSaveResponse(this.transactionsRecordService.create(transactionsRecord));
    }
  }

  private createFromForm(): ITransactionsRecord {
    return {
      ...new TransactionsRecord(),
      id: this.editForm.get(['id'])!.value,
      txType: this.editForm.get(['txType'])!.value,
      description: this.editForm.get(['description'])!.value,
      txAmmount: this.editForm.get(['txAmmount'])!.value,
      txRefNo: this.editForm.get(['txRefNo'])!.value,
      txCreatedBy: this.editForm.get(['txCreatedBy'])!.value,
      txCreatedDate: this.editForm.get(['txCreatedDate'])!.value,
      txCompletedBy: this.editForm.get(['txCompletedBy'])!.value,
      txCompletedDate: this.editForm.get(['txCompletedDate'])!.value,
      status: this.editForm.get(['status'])!.value,
      txDocContentType: this.editForm.get(['txDocContentType'])!.value,
      txDoc: this.editForm.get(['txDoc'])!.value,
      currency: this.editForm.get(['currency'])!.value,
      remarks: this.editForm.get(['remarks'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? moment(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      createdBy: this.editForm.get(['createdBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? moment(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      customer: this.editForm.get(['customer'])!.value,
      account: this.editForm.get(['account'])!.value,
      invoice: this.editForm.get(['invoice'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransactionsRecord>>): void {
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
