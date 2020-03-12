import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TmsV1ApplicationTestModule } from '../../../test.module';
import { AccountsUpdateComponent } from 'app/entities/accounts/accounts-update.component';
import { AccountsService } from 'app/entities/accounts/accounts.service';
import { Accounts } from 'app/shared/model/accounts.model';

describe('Component Tests', () => {
  describe('Accounts Management Update Component', () => {
    let comp: AccountsUpdateComponent;
    let fixture: ComponentFixture<AccountsUpdateComponent>;
    let service: AccountsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TmsV1ApplicationTestModule],
        declarations: [AccountsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AccountsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AccountsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AccountsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Accounts(123);
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
        const entity = new Accounts();
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
