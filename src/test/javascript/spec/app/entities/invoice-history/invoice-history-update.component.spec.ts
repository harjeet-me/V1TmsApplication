import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TmsV1ApplicationTestModule } from '../../../test.module';
import { InvoiceHistoryUpdateComponent } from 'app/entities/invoice-history/invoice-history-update.component';
import { InvoiceHistoryService } from 'app/entities/invoice-history/invoice-history.service';
import { InvoiceHistory } from 'app/shared/model/invoice-history.model';

describe('Component Tests', () => {
  describe('InvoiceHistory Management Update Component', () => {
    let comp: InvoiceHistoryUpdateComponent;
    let fixture: ComponentFixture<InvoiceHistoryUpdateComponent>;
    let service: InvoiceHistoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TmsV1ApplicationTestModule],
        declarations: [InvoiceHistoryUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(InvoiceHistoryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InvoiceHistoryUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InvoiceHistoryService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new InvoiceHistory(123);
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
        const entity = new InvoiceHistory();
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
