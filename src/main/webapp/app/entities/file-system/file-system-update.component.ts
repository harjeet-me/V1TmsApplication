import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IFileSystem, FileSystem } from 'app/shared/model/file-system.model';
import { FileSystemService } from './file-system.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IEmail } from 'app/shared/model/email.model';
import { EmailService } from 'app/entities/email/email.service';

@Component({
  selector: 'jhi-file-system-update',
  templateUrl: './file-system-update.component.html'
})
export class FileSystemUpdateComponent implements OnInit {
  isSaving = false;
  emails: IEmail[] = [];

  editForm = this.fb.group({
    id: [],
    data: [],
    dataContentType: [],
    fileName: [],
    createdOn: [],
    createdBy: [],
    updatedOn: [],
    updatedBy: [],
    email: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected fileSystemService: FileSystemService,
    protected emailService: EmailService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fileSystem }) => {
      if (!fileSystem.id) {
        const today = moment().startOf('day');
        fileSystem.createdOn = today;
        fileSystem.updatedOn = today;
      }

      this.updateForm(fileSystem);

      this.emailService.query().subscribe((res: HttpResponse<IEmail[]>) => (this.emails = res.body || []));
    });
  }

  updateForm(fileSystem: IFileSystem): void {
    this.editForm.patchValue({
      id: fileSystem.id,
      data: fileSystem.data,
      dataContentType: fileSystem.dataContentType,
      fileName: fileSystem.fileName,
      createdOn: fileSystem.createdOn ? fileSystem.createdOn.format(DATE_TIME_FORMAT) : null,
      createdBy: fileSystem.createdBy,
      updatedOn: fileSystem.updatedOn ? fileSystem.updatedOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: fileSystem.updatedBy,
      email: fileSystem.email
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
    const fileSystem = this.createFromForm();
    if (fileSystem.id !== undefined) {
      this.subscribeToSaveResponse(this.fileSystemService.update(fileSystem));
    } else {
      this.subscribeToSaveResponse(this.fileSystemService.create(fileSystem));
    }
  }

  private createFromForm(): IFileSystem {
    return {
      ...new FileSystem(),
      id: this.editForm.get(['id'])!.value,
      dataContentType: this.editForm.get(['dataContentType'])!.value,
      data: this.editForm.get(['data'])!.value,
      fileName: this.editForm.get(['fileName'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? moment(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      createdBy: this.editForm.get(['createdBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? moment(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      email: this.editForm.get(['email'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFileSystem>>): void {
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

  trackById(index: number, item: IEmail): any {
    return item.id;
  }
}
