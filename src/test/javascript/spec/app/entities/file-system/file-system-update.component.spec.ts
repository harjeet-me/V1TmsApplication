import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TmsV1ApplicationTestModule } from '../../../test.module';
import { FileSystemUpdateComponent } from 'app/entities/file-system/file-system-update.component';
import { FileSystemService } from 'app/entities/file-system/file-system.service';
import { FileSystem } from 'app/shared/model/file-system.model';

describe('Component Tests', () => {
  describe('FileSystem Management Update Component', () => {
    let comp: FileSystemUpdateComponent;
    let fixture: ComponentFixture<FileSystemUpdateComponent>;
    let service: FileSystemService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TmsV1ApplicationTestModule],
        declarations: [FileSystemUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FileSystemUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FileSystemUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FileSystemService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FileSystem(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new FileSystem();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
