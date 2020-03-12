import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TmsV1ApplicationTestModule } from '../../../test.module';
import { InvoiceItemDetailComponent } from 'app/entities/invoice-item/invoice-item-detail.component';
import { InvoiceItem } from 'app/shared/model/invoice-item.model';

describe('Component Tests', () => {
  describe('InvoiceItem Management Detail Component', () => {
    let comp: InvoiceItemDetailComponent;
    let fixture: ComponentFixture<InvoiceItemDetailComponent>;
    const route = ({ data: of({ invoiceItem: new InvoiceItem(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TmsV1ApplicationTestModule],
        declarations: [InvoiceItemDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(InvoiceItemDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InvoiceItemDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load invoiceItem on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.invoiceItem).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
