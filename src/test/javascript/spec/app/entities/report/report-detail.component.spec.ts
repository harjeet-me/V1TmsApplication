import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { TmsV1ApplicationTestModule } from '../../../test.module';
import { ReportDetailComponent } from 'app/entities/report/report-detail.component';
import { Report } from 'app/shared/model/report.model';

describe('Component Tests', () => {
  describe('Report Management Detail Component', () => {
    let comp: ReportDetailComponent;
    let fixture: ComponentFixture<ReportDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ report: new Report(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TmsV1ApplicationTestModule],
        declarations: [ReportDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ReportDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ReportDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load report on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.report).toEqual(jasmine.objectContaining({ id: 123 }));
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
