import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TmsV1ApplicationTestModule } from '../../../test.module';
import { CompanyProfileUpdateComponent } from 'app/entities/company-profile/company-profile-update.component';
import { CompanyProfileService } from 'app/entities/company-profile/company-profile.service';
import { CompanyProfile } from 'app/shared/model/company-profile.model';

describe('Component Tests', () => {
  describe('CompanyProfile Management Update Component', () => {
    let comp: CompanyProfileUpdateComponent;
    let fixture: ComponentFixture<CompanyProfileUpdateComponent>;
    let service: CompanyProfileService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TmsV1ApplicationTestModule],
        declarations: [CompanyProfileUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CompanyProfileUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CompanyProfileUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CompanyProfileService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CompanyProfile(123);
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
        const entity = new CompanyProfile();
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
