import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITransactionsRecord } from 'app/shared/model/transactions-record.model';

@Component({
  selector: 'jhi-transactions-record-detail',
  templateUrl: './transactions-record-detail.component.html'
})
export class TransactionsRecordDetailComponent implements OnInit {
  transactionsRecord: ITransactionsRecord | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transactionsRecord }) => (this.transactionsRecord = transactionsRecord));
  }

  previousState(): void {
    window.history.back();
  }
}
