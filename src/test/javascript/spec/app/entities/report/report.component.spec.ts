import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TmsV1ApplicationTestModule } from '../../../test.module';
import { ReportComponent } from 'app/entities/report/report.component';
import { ReportService } from 'app/entities/report/report.service';
import { Report } from 'app/shared/model/report.model';

describe('Component Tests', () => {
  describe('Report Management Component', () => {
    let comp: ReportComponent;
    let fixture: ComponentFixture<ReportComponent>;
    let service: ReportService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TmsV1ApplicationTestModule],
        declarations: [ReportComponent]
      })
        .overrideTemplate(ReportComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ReportComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ReportService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Report(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.reports && comp.reports[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
