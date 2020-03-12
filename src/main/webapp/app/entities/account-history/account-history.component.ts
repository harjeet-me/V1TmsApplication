import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAccountHistory } from 'app/shared/model/account-history.model';
import { AccountHistoryService } from './account-history.service';
import { AccountHistoryDeleteDialogComponent } from './account-history-delete-dialog.component';

@Component({
  selector: 'jhi-account-history',
  templateUrl: './account-history.component.html'
})
export class AccountHistoryComponent implements OnInit, OnDestroy {
  accountHistories?: IAccountHistory[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected accountHistoryService: AccountHistoryService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected activatedRoute: ActivatedRoute
  ) {
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.queryParams['search']
        ? this.activatedRoute.snapshot.queryParams['search']
        : '';
  }

  loadAll(): void {
    if (this.currentSearch) {
      this.accountHistoryService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IAccountHistory[]>) => (this.accountHistories = res.body || []));
      return;
    }

    this.accountHistoryService.query().subscribe((res: HttpResponse<IAccountHistory[]>) => (this.accountHistories = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAccountHistories();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAccountHistory): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAccountHistories(): void {
    this.eventSubscriber = this.eventManager.subscribe('accountHistoryListModification', () => this.loadAll());
  }

  delete(accountHistory: IAccountHistory): void {
    const modalRef = this.modalService.open(AccountHistoryDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.accountHistory = accountHistory;
  }
}
