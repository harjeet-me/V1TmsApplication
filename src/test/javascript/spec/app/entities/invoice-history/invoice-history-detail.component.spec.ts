import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TmsV1ApplicationTestModule } from '../../../test.module';
import { InvoiceHistoryDetailComponent } from 'app/entities/invoice-history/invoice-history-detail.component';
import { InvoiceHistory } from 'app/shared/model/invoice-history.model';

describe('Component Tests', () => {
  describe('InvoiceHistory Management Detail Component', () => {
    let comp: InvoiceHistoryDetailComponent;
    let fixture: ComponentFixture<InvoiceHistoryDetailComponent>;
    const route = ({ data: of({ invoiceHistory: new InvoiceHistory(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TmsV1ApplicationTestModule],
        declarations: [InvoiceHistoryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(InvoiceHistoryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InvoiceHistoryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load invoiceHistory on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.invoiceHistory).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
