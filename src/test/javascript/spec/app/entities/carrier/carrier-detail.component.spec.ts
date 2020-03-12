import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { TmsV1ApplicationTestModule } from '../../../test.module';
import { CarrierDetailComponent } from 'app/entities/carrier/carrier-detail.component';
import { Carrier } from 'app/shared/model/carrier.model';

describe('Component Tests', () => {
  describe('Carrier Management Detail Component', () => {
    let comp: CarrierDetailComponent;
    let fixture: ComponentFixture<CarrierDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ carrier: new Carrier(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TmsV1ApplicationTestModule],
        declarations: [CarrierDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CarrierDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CarrierDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load carrier on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.carrier).toEqual(jasmine.objectContaining({ id: 123 }));
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
