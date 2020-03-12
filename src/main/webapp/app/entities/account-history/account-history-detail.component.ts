import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAccountHistory } from 'app/shared/model/account-history.model';

@Component({
  selector: 'jhi-account-history-detail',
  templateUrl: './account-history-detail.component.html'
})
export class AccountHistoryDetailComponent implements OnInit {
  accountHistory: IAccountHistory | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ accountHistory }) => (this.accountHistory = accountHistory));
  }

  previousState(): void {
    window.history.back();
  }
}
