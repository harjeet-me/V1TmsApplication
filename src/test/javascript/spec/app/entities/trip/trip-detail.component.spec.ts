import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { TmsV1ApplicationTestModule } from '../../../test.module';
import { TripDetailComponent } from 'app/entities/trip/trip-detail.component';
import { Trip } from 'app/shared/model/trip.model';

describe('Component Tests', () => {
  describe('Trip Management Detail Component', () => {
    let comp: TripDetailComponent;
    let fixture: ComponentFixture<TripDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ trip: new Trip(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TmsV1ApplicationTestModule],
        declarations: [TripDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TripDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TripDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load trip on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.trip).toEqual(jasmine.objectContaining({ id: 123 }));
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
