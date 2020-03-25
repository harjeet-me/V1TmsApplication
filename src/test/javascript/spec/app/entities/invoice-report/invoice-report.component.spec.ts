import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TmsV1ApplicationTestModule } from '../../../test.module';
import { InvoiceReportComponent } from 'app/entities/invoice-report/invoice-report.component';
import { InvoiceReportService } from 'app/entities/invoice-report/invoice-report.service';
import { InvoiceReport } from 'app/shared/model/invoice-report.model';

describe('Component Tests', () => {
  describe('InvoiceReport Management Component', () => {
    let comp: InvoiceReportComponent;
    let fixture: ComponentFixture<InvoiceReportComponent>;
    let service: InvoiceReportService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TmsV1ApplicationTestModule],
        declarations: [InvoiceReportComponent]
      })
        .overrideTemplate(InvoiceReportComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InvoiceReportComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InvoiceReportService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new InvoiceReport(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.invoiceReports && comp.invoiceReports[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
