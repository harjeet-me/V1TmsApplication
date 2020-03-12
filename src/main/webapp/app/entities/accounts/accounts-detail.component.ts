import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAccounts } from 'app/shared/model/accounts.model';

@Component({
  selector: 'jhi-accounts-detail',
  templateUrl: './accounts-detail.component.html'
})
export class AccountsDetailComponent implements OnInit {
  accounts: IAccounts | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ accounts }) => (this.accounts = accounts));
  }

  previousState(): void {
    window.history.back();
  }
}
