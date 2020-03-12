import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IAccountHistory, AccountHistory } from 'app/shared/model/account-history.model';
import { AccountHistoryService } from './account-history.service';

@Component({
  selector: 'jhi-account-history-update',
  templateUrl: './account-history-update.component.html'
})
export class AccountHistoryUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    enityName: [],
    entityLink: [],
    action: [],
    createdOn: [],
    createdBy: [],
    updatedOn: [],
    updatedBy: []
  });

  constructor(protected accountHistoryService: AccountHistoryService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ accountHistory }) => {
      if (!accountHistory.id) {
        const today = moment().startOf('day');
        accountHistory.createdOn = today;
        accountHistory.updatedOn = today;
      }

      this.updateForm(accountHistory);
    });
  }

  updateForm(accountHistory: IAccountHistory): void {
    this.editForm.patchValue({
      id: accountHistory.id,
      enityName: accountHistory.enityName,
      entityLink: accountHistory.entityLink,
      action: accountHistory.action,
      createdOn: accountHistory.createdOn ? accountHistory.createdOn.format(DATE_TIME_FORMAT) : null,
      createdBy: accountHistory.createdBy,
      updatedOn: accountHistory.updatedOn ? accountHistory.updatedOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: accountHistory.updatedBy
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const accountHistory = this.createFromForm();
    if (accountHistory.id !== undefined) {
      this.subscribeToSaveResponse(this.accountHistoryService.update(accountHistory));
    } else {
      this.subscribeToSaveResponse(this.accountHistoryService.create(accountHistory));
    }
  }

  private createFromForm(): IAccountHistory {
    return {
      ...new AccountHistory(),
      id: this.editForm.get(['id'])!.value,
      enityName: this.editForm.get(['enityName'])!.value,
      entityLink: this.editForm.get(['entityLink'])!.value,
      action: this.editForm.get(['action'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? moment(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      createdBy: this.editForm.get(['createdBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? moment(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAccountHistory>>): void {
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
