import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IFileSystem } from 'app/shared/model/file-system.model';

@Component({
  selector: 'jhi-file-system-detail',
  templateUrl: './file-system-detail.component.html'
})
export class FileSystemDetailComponent implements OnInit {
  fileSystem: IFileSystem | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fileSystem }) => (this.fileSystem = fileSystem));
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
