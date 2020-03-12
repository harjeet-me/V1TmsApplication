import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TmsV1ApplicationTestModule } from '../../../test.module';
import { AccountHistoryDetailComponent } from 'app/entities/account-history/account-history-detail.component';
import { AccountHistory } from 'app/shared/model/account-history.model';

describe('Component Tests', () => {
  describe('AccountHistory Management Detail Component', () => {
    let comp: AccountHistoryDetailComponent;
    let fixture: ComponentFixture<AccountHistoryDetailComponent>;
    const route = ({ data: of({ accountHistory: new AccountHistory(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TmsV1ApplicationTestModule],
        declarations: [AccountHistoryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AccountHistoryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AccountHistoryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load accountHistory on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.accountHistory).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
