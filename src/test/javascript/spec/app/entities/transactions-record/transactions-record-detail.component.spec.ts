import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { TmsV1ApplicationTestModule } from '../../../test.module';
import { TransactionsRecordDetailComponent } from 'app/entities/transactions-record/transactions-record-detail.component';
import { TransactionsRecord } from 'app/shared/model/transactions-record.model';

describe('Component Tests', () => {
  describe('TransactionsRecord Management Detail Component', () => {
    let comp: TransactionsRecordDetailComponent;
    let fixture: ComponentFixture<TransactionsRecordDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ transactionsRecord: new TransactionsRecord(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TmsV1ApplicationTestModule],
        declarations: [TransactionsRecordDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TransactionsRecordDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TransactionsRecordDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load transactionsRecord on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.transactionsRecord).toEqual(jasmine.objectContaining({ id: 123 }));
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
