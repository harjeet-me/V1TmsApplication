import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TmsV1ApplicationTestModule } from '../../../test.module';
import { TransactionsRecordUpdateComponent } from 'app/entities/transactions-record/transactions-record-update.component';
import { TransactionsRecordService } from 'app/entities/transactions-record/transactions-record.service';
import { TransactionsRecord } from 'app/shared/model/transactions-record.model';

describe('Component Tests', () => {
  describe('TransactionsRecord Management Update Component', () => {
    let comp: TransactionsRecordUpdateComponent;
    let fixture: ComponentFixture<TransactionsRecordUpdateComponent>;
    let service: TransactionsRecordService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TmsV1ApplicationTestModule],
        declarations: [TransactionsRecordUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TransactionsRecordUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TransactionsRecordUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TransactionsRecordService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TransactionsRecord(123);
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
        const entity = new TransactionsRecord();
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
