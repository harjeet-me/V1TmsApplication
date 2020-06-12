import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ICompanyProfile, CompanyProfile } from 'app/shared/model/company-profile.model';
import { CompanyProfileService } from './company-profile.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-company-profile-update',
  templateUrl: './company-profile-update.component.html'
})
export class CompanyProfileUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    active: [],
    company: [],
    address: [],
    streetAddress: [],
    city: [],
    stateProvince: [],
    country: [],
    postalCode: [],
    email: [],
    website: [],
    phoneNumber: [],
    dot: [],
    mc: [],
    companyLogo: [],
    companyLogoContentType: [],
    profileStatus: [],
    preffredCurrency: [],
    createdDate: [],
    createdBy: [],
    lastModifiedDate: [],
    lastModifiedBy: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected companyProfileService: CompanyProfileService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ companyProfile }) => {
      if (!companyProfile.id) {
        const today = moment().startOf('day');
        companyProfile.createdDate = today;
        companyProfile.lastModifiedDate = today;
      }

      this.updateForm(companyProfile);
    });
  }

  updateForm(companyProfile: ICompanyProfile): void {
    this.editForm.patchValue({
      id: companyProfile.id,
      active: companyProfile.active,
      company: companyProfile.company,
      address: companyProfile.address,
      streetAddress: companyProfile.streetAddress,
      city: companyProfile.city,
      stateProvince: companyProfile.stateProvince,
      country: companyProfile.country,
      postalCode: companyProfile.postalCode,
      email: companyProfile.email,
      website: companyProfile.website,
      phoneNumber: companyProfile.phoneNumber,
      dot: companyProfile.dot,
      mc: companyProfile.mc,
      companyLogo: companyProfile.companyLogo,
      companyLogoContentType: companyProfile.companyLogoContentType,
      profileStatus: companyProfile.profileStatus,
      preffredCurrency: companyProfile.preffredCurrency,
      createdDate: companyProfile.createdDate ? companyProfile.createdDate.format(DATE_TIME_FORMAT) : null,
      createdBy: companyProfile.createdBy,
      lastModifiedDate: companyProfile.lastModifiedDate ? companyProfile.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: companyProfile.lastModifiedBy
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

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const companyProfile = this.createFromForm();
    if (companyProfile.id !== undefined) {
      this.subscribeToSaveResponse(this.companyProfileService.update(companyProfile));
    } else {
      this.subscribeToSaveResponse(this.companyProfileService.create(companyProfile));
    }
  }

  private createFromForm(): ICompanyProfile {
    return {
      ...new CompanyProfile(),
      id: this.editForm.get(['id'])!.value,
      active: this.editForm.get(['active'])!.value,
      company: this.editForm.get(['company'])!.value,
      address: this.editForm.get(['address'])!.value,
      streetAddress: this.editForm.get(['streetAddress'])!.value,
      city: this.editForm.get(['city'])!.value,
      stateProvince: this.editForm.get(['stateProvince'])!.value,
      country: this.editForm.get(['country'])!.value,
      postalCode: this.editForm.get(['postalCode'])!.value,
      email: this.editForm.get(['email'])!.value,
      website: this.editForm.get(['website'])!.value,
      phoneNumber: this.editForm.get(['phoneNumber'])!.value,
      dot: this.editForm.get(['dot'])!.value,
      mc: this.editForm.get(['mc'])!.value,
      companyLogoContentType: this.editForm.get(['companyLogoContentType'])!.value,
      companyLogo: this.editForm.get(['companyLogo'])!.value,
      profileStatus: this.editForm.get(['profileStatus'])!.value,
      preffredCurrency: this.editForm.get(['preffredCurrency'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? moment(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      createdBy: this.editForm.get(['createdBy'])!.value,
      lastModifiedDate: this.editForm.get(['lastModifiedDate'])!.value
        ? moment(this.editForm.get(['lastModifiedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompanyProfile>>): void {
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
