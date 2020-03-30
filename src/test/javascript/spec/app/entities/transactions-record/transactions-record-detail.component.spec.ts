import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TmsV1ApplicationTestModule } from '../../../test.module';
import { TransactionsRecordDetailComponent } from 'app/entities/transactions-record/transactions-record-detail.component';
import { TransactionsRecord } from 'app/shared/model/transactions-record.model';

describe('Component Tests', () => {
  describe('TransactionsRecord Management Detail Component', () => {
    let comp: TransactionsRecordDetailComponent;
    let fixture: ComponentFixture<TransactionsRecordDetailComponent>;
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
    });

    describe('OnInit', () => {
      it('Should load transactionsRecord on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.transactionsRecord).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
