import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { TmsV1ApplicationTestModule } from '../../../test.module';
import { InvoiceDetailComponent } from 'app/entities/invoice/invoice-detail.component';
import { Invoice } from 'app/shared/model/invoice.model';

describe('Component Tests', () => {
  describe('Invoice Management Detail Component', () => {
    let comp: InvoiceDetailComponent;
    let fixture: ComponentFixture<InvoiceDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ invoice: new Invoice(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TmsV1ApplicationTestModule],
        declarations: [InvoiceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(InvoiceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InvoiceDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load invoice on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.invoice).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
