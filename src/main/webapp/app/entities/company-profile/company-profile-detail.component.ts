import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ICompanyProfile } from 'app/shared/model/company-profile.model';

@Component({
  selector: 'jhi-company-profile-detail',
  templateUrl: './company-profile-detail.component.html'
})
export class CompanyProfileDetailComponent implements OnInit {
  companyProfile: ICompanyProfile | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ companyProfile }) => (this.companyProfile = companyProfile));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
