import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TmsV1ApplicationTestModule } from '../../../test.module';
import { InvoiceHistoryComponent } from 'app/entities/invoice-history/invoice-history.component';
import { InvoiceHistoryService } from 'app/entities/invoice-history/invoice-history.service';
import { InvoiceHistory } from 'app/shared/model/invoice-history.model';

describe('Component Tests', () => {
  describe('InvoiceHistory Management Component', () => {
    let comp: InvoiceHistoryComponent;
    let fixture: ComponentFixture<InvoiceHistoryComponent>;
    let service: InvoiceHistoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TmsV1ApplicationTestModule],
        declarations: [InvoiceHistoryComponent]
      })
        .overrideTemplate(InvoiceHistoryComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InvoiceHistoryComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InvoiceHistoryService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new InvoiceHistory(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.invoiceHistories && comp.invoiceHistories[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
