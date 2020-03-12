import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IDriver, Driver } from 'app/shared/model/driver.model';
import { DriverService } from './driver.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-driver-update',
  templateUrl: './driver-update.component.html'
})
export class DriverUpdateComponent implements OnInit {
  isSaving = false;
  dobDp: any;
  companyJoinedOnDp: any;
  companyLeftOnDp: any;

  editForm = this.fb.group({
    id: [],
    company: [],
    firstName: [],
    lastName: [],
    email: [],
    phoneNumber: [],
    licenceNumber: [],
    dob: [],
    companyJoinedOn: [],
    companyLeftOn: [],
    image: [],
    imageContentType: [],
    licenceImage: [],
    licenceImageContentType: [],
    remarks: [],
    contractDoc: [],
    contractDocContentType: [],
    status: [],
    createdOn: [],
    createdBy: [],
    updatedOn: [],
    updatedBy: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected driverService: DriverService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ driver }) => {
      if (!driver.id) {
        const today = moment().startOf('day');
        driver.createdOn = today;
        driver.updatedOn = today;
      }

      this.updateForm(driver);
    });
  }

  updateForm(driver: IDriver): void {
    this.editForm.patchValue({
      id: driver.id,
      company: driver.company,
      firstName: driver.firstName,
      lastName: driver.lastName,
      email: driver.email,
      phoneNumber: driver.phoneNumber,
      licenceNumber: driver.licenceNumber,
      dob: driver.dob,
      companyJoinedOn: driver.companyJoinedOn,
      companyLeftOn: driver.companyLeftOn,
      image: driver.image,
      imageContentType: driver.imageContentType,
      licenceImage: driver.licenceImage,
      licenceImageContentType: driver.licenceImageContentType,
      remarks: driver.remarks,
      contractDoc: driver.contractDoc,
      contractDocContentType: driver.contractDocContentType,
      status: driver.status,
      createdOn: driver.createdOn ? driver.createdOn.format(DATE_TIME_FORMAT) : null,
      createdBy: driver.createdBy,
      updatedOn: driver.updatedOn ? driver.updatedOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: driver.updatedBy
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
    const driver = this.createFromForm();
    if (driver.id !== undefined) {
      this.subscribeToSaveResponse(this.driverService.update(driver));
    } else {
      this.subscribeToSaveResponse(this.driverService.create(driver));
    }
  }

  private createFromForm(): IDriver {
    return {
      ...new Driver(),
      id: this.editForm.get(['id'])!.value,
      company: this.editForm.get(['company'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      email: this.editForm.get(['email'])!.value,
      phoneNumber: this.editForm.get(['phoneNumber'])!.value,
      licenceNumber: this.editForm.get(['licenceNumber'])!.value,
      dob: this.editForm.get(['dob'])!.value,
      companyJoinedOn: this.editForm.get(['companyJoinedOn'])!.value,
      companyLeftOn: this.editForm.get(['companyLeftOn'])!.value,
      imageContentType: this.editForm.get(['imageContentType'])!.value,
      image: this.editForm.get(['image'])!.value,
      licenceImageContentType: this.editForm.get(['licenceImageContentType'])!.value,
      licenceImage: this.editForm.get(['licenceImage'])!.value,
      remarks: this.editForm.get(['remarks'])!.value,
      contractDocContentType: this.editForm.get(['contractDocContentType'])!.value,
      contractDoc: this.editForm.get(['contractDoc'])!.value,
      status: this.editForm.get(['status'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? moment(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      createdBy: this.editForm.get(['createdBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? moment(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDriver>>): void {
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
