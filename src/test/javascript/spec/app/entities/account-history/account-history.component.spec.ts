import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TmsV1ApplicationTestModule } from '../../../test.module';
import { AccountHistoryComponent } from 'app/entities/account-history/account-history.component';
import { AccountHistoryService } from 'app/entities/account-history/account-history.service';
import { AccountHistory } from 'app/shared/model/account-history.model';

describe('Component Tests', () => {
  describe('AccountHistory Management Component', () => {
    let comp: AccountHistoryComponent;
    let fixture: ComponentFixture<AccountHistoryComponent>;
    let service: AccountHistoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TmsV1ApplicationTestModule],
        declarations: [AccountHistoryComponent]
      })
        .overrideTemplate(AccountHistoryComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AccountHistoryComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AccountHistoryService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AccountHistory(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.accountHistories && comp.accountHistories[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
