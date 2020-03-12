import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TmsV1ApplicationTestModule } from '../../../test.module';
import { CompanyProfileComponent } from 'app/entities/company-profile/company-profile.component';
import { CompanyProfileService } from 'app/entities/company-profile/company-profile.service';
import { CompanyProfile } from 'app/shared/model/company-profile.model';

describe('Component Tests', () => {
  describe('CompanyProfile Management Component', () => {
    let comp: CompanyProfileComponent;
    let fixture: ComponentFixture<CompanyProfileComponent>;
    let service: CompanyProfileService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TmsV1ApplicationTestModule],
        declarations: [CompanyProfileComponent]
      })
        .overrideTemplate(CompanyProfileComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CompanyProfileComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CompanyProfileService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CompanyProfile(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.companyProfiles && comp.companyProfiles[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
