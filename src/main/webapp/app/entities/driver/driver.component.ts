import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDriver } from 'app/shared/model/driver.model';
import { DriverService } from './driver.service';
import { DriverDeleteDialogComponent } from './driver-delete-dialog.component';

@Component({
  selector: 'jhi-driver',
  templateUrl: './driver.component.html'
})
export class DriverComponent implements OnInit, OnDestroy {
  drivers?: IDriver[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected driverService: DriverService,
    protected dataUtils: JhiDataUtils,
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
      this.driverService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IDriver[]>) => (this.drivers = res.body || []));
      return;
    }

    this.driverService.query().subscribe((res: HttpResponse<IDriver[]>) => (this.drivers = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInDrivers();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDriver): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInDrivers(): void {
    this.eventSubscriber = this.eventManager.subscribe('driverListModification', () => this.loadAll());
  }

  delete(driver: IDriver): void {
    const modalRef = this.modalService.open(DriverDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.driver = driver;
  }
}
