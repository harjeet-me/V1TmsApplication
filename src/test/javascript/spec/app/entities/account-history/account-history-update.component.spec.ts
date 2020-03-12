import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TmsV1ApplicationTestModule } from '../../../test.module';
import { AccountHistoryUpdateComponent } from 'app/entities/account-history/account-history-update.component';
import { AccountHistoryService } from 'app/entities/account-history/account-history.service';
import { AccountHistory } from 'app/shared/model/account-history.model';

describe('Component Tests', () => {
  describe('AccountHistory Management Update Component', () => {
    let comp: AccountHistoryUpdateComponent;
    let fixture: ComponentFixture<AccountHistoryUpdateComponent>;
    let service: AccountHistoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TmsV1ApplicationTestModule],
        declarations: [AccountHistoryUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AccountHistoryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AccountHistoryUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AccountHistoryService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AccountHistory(123);
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
        const entity = new AccountHistory();
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
