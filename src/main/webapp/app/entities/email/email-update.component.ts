import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IEmail, Email } from 'app/shared/model/email.model';
import { EmailService } from './email.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-email-update',
  templateUrl: './email-update.component.html'
})
export class EmailUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    userto: [],
    usercc: [],
    userbcc: [],
    subject: [],
    message: [],
    multipart: [],
    htmlBody: [],
    attachment: [],
    attachmentContentType: [],
    attachmentName: [],
    status: [],
    sentDateTime: [],
    createdOn: [],
    createdBy: [],
    updatedOn: [],
    updatedBy: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected emailService: EmailService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ email }) => {
      if (!email.id) {
        const today = moment().startOf('day');
        email.sentDateTime = today;
        email.createdOn = today;
        email.updatedOn = today;
      }

      this.updateForm(email);
    });
  }

  updateForm(email: IEmail): void {
    this.editForm.patchValue({
      id: email.id,
      userto: email.userto,
      usercc: email.usercc,
      userbcc: email.userbcc,
      subject: email.subject,
      message: email.message,
      multipart: email.multipart,
      htmlBody: email.htmlBody,
      attachment: email.attachment,
      attachmentContentType: email.attachmentContentType,
      attachmentName: email.attachmentName,
      status: email.status,
      sentDateTime: email.sentDateTime ? email.sentDateTime.format(DATE_TIME_FORMAT) : null,
      createdOn: email.createdOn ? email.createdOn.format(DATE_TIME_FORMAT) : null,
      createdBy: email.createdBy,
      updatedOn: email.updatedOn ? email.updatedOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: email.updatedBy
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
    const email = this.createFromForm();
    if (email.id !== undefined) {
      this.subscribeToSaveResponse(this.emailService.update(email));
    } else {
      this.subscribeToSaveResponse(this.emailService.create(email));
    }
  }

  private createFromForm(): IEmail {
    return {
      ...new Email(),
      id: this.editForm.get(['id'])!.value,
      userto: this.editForm.get(['userto'])!.value,
      usercc: this.editForm.get(['usercc'])!.value,
      userbcc: this.editForm.get(['userbcc'])!.value,
      subject: this.editForm.get(['subject'])!.value,
      message: this.editForm.get(['message'])!.value,
      multipart: this.editForm.get(['multipart'])!.value,
      htmlBody: this.editForm.get(['htmlBody'])!.value,
      attachmentContentType: this.editForm.get(['attachmentContentType'])!.value,
      attachment: this.editForm.get(['attachment'])!.value,
      attachmentName: this.editForm.get(['attachmentName'])!.value,
      status: this.editForm.get(['status'])!.value,
      sentDateTime: this.editForm.get(['sentDateTime'])!.value
        ? moment(this.editForm.get(['sentDateTime'])!.value, DATE_TIME_FORMAT)
        : undefined,
      createdOn: this.editForm.get(['createdOn'])!.value ? moment(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      createdBy: this.editForm.get(['createdBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? moment(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmail>>): void {
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
