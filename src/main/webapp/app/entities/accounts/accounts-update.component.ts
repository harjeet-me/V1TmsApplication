import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IAccounts, Accounts } from 'app/shared/model/accounts.model';
import { AccountsService } from './accounts.service';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer/customer.service';

@Component({
  selector: 'jhi-accounts-update',
  templateUrl: './accounts-update.component.html'
})
export class AccountsUpdateComponent implements OnInit {
  isSaving = false;
  customers: ICustomer[] = [];

  editForm = this.fb.group({
    id: [],
    balance: [],
    over30: [],
    over60: [],
    over90: [],
    createdDate: [],
    createdBy: [],
    lastModifiedDate: [],
    lastModifiedBy: [],
    customer: []
  });

  constructor(
    protected accountsService: AccountsService,
    protected customerService: CustomerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ accounts }) => {
      if (!accounts.id) {
        const today = moment().startOf('day');
        accounts.createdDate = today;
        accounts.lastModifiedDate = today;
      }

      this.updateForm(accounts);

      this.customerService
        .query({ filter: 'accounts-is-null' })
        .pipe(
          map((res: HttpResponse<ICustomer[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ICustomer[]) => {
          if (!accounts.customer || !accounts.customer.id) {
            this.customers = resBody;
          } else {
            this.customerService
              .find(accounts.customer.id)
              .pipe(
                map((subRes: HttpResponse<ICustomer>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ICustomer[]) => (this.customers = concatRes));
          }
        });
    });
  }

  updateForm(accounts: IAccounts): void {
    this.editForm.patchValue({
      id: accounts.id,
      balance: accounts.balance,
      over30: accounts.over30,
      over60: accounts.over60,
      over90: accounts.over90,
      createdDate: accounts.createdDate ? accounts.createdDate.format(DATE_TIME_FORMAT) : null,
      createdBy: accounts.createdBy,
      lastModifiedDate: accounts.lastModifiedDate ? accounts.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: accounts.lastModifiedBy,
      customer: accounts.customer
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const accounts = this.createFromForm();
    if (accounts.id !== undefined) {
      this.subscribeToSaveResponse(this.accountsService.update(accounts));
    } else {
      this.subscribeToSaveResponse(this.accountsService.create(accounts));
    }
  }

  private createFromForm(): IAccounts {
    return {
      ...new Accounts(),
      id: this.editForm.get(['id'])!.value,
      balance: this.editForm.get(['balance'])!.value,
      over30: this.editForm.get(['over30'])!.value,
      over60: this.editForm.get(['over60'])!.value,
      over90: this.editForm.get(['over90'])!.value,
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAccounts>>): void {
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
