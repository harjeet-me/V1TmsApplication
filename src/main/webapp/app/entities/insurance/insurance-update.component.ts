import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IInsurance, Insurance } from 'app/shared/model/insurance.model';
import { InsuranceService } from './insurance.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-insurance-update',
  templateUrl: './insurance-update.component.html'
})
export class InsuranceUpdateComponent implements OnInit {
  isSaving = false;
  issueDateDp: any;
  expiryDateDp: any;

  editForm = this.fb.group({
    id: [],
    providerName: [],
    issueDate: [],
    expiryDate: [],
    policyDocument: [],
    policyDocumentContentType: [],
    coverageStatement: [],
    createdDate: [],
    createdBy: [],
    lastModifiedDate: [],
    lastModifiedBy: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected insuranceService: InsuranceService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ insurance }) => {
      if (!insurance.id) {
        const today = moment().startOf('day');
        insurance.createdDate = today;
        insurance.lastModifiedDate = today;
      }

      this.updateForm(insurance);
    });
  }

  updateForm(insurance: IInsurance): void {
    this.editForm.patchValue({
      id: insurance.id,
      providerName: insurance.providerName,
      issueDate: insurance.issueDate,
      expiryDate: insurance.expiryDate,
      policyDocument: insurance.policyDocument,
      policyDocumentContentType: insurance.policyDocumentContentType,
      coverageStatement: insurance.coverageStatement,
      createdDate: insurance.createdDate ? insurance.createdDate.format(DATE_TIME_FORMAT) : null,
      createdBy: insurance.createdBy,
      lastModifiedDate: insurance.lastModifiedDate ? insurance.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: insurance.lastModifiedBy
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

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const insurance = this.createFromForm();
    if (insurance.id !== undefined) {
      this.subscribeToSaveResponse(this.insuranceService.update(insurance));
    } else {
      this.subscribeToSaveResponse(this.insuranceService.create(insurance));
    }
  }

  private createFromForm(): IInsurance {
    return {
      ...new Insurance(),
      id: this.editForm.get(['id'])!.value,
      providerName: this.editForm.get(['providerName'])!.value,
      issueDate: this.editForm.get(['issueDate'])!.value,
      expiryDate: this.editForm.get(['expiryDate'])!.value,
      policyDocumentContentType: this.editForm.get(['policyDocumentContentType'])!.value,
      policyDocument: this.editForm.get(['policyDocument'])!.value,
      coverageStatement: this.editForm.get(['coverageStatement'])!.value,
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInsurance>>): void {
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
