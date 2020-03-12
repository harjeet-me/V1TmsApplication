import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFileSystem } from 'app/shared/model/file-system.model';
import { FileSystemService } from './file-system.service';

@Component({
  templateUrl: './file-system-delete-dialog.component.html'
})
export class FileSystemDeleteDialogComponent {
  fileSystem?: IFileSystem;

  constructor(
    protected fileSystemService: FileSystemService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.fileSystemService.delete(id).subscribe(() => {
      this.eventManager.broadcast('fileSystemListModification');
      this.activeModal.close();
    });
  }
}
