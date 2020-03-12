import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { TransactionsRecordService } from 'app/entities/transactions-record/transactions-record.service';
import { ITransactionsRecord, TransactionsRecord } from 'app/shared/model/transactions-record.model';
import { TransactionType } from 'app/shared/model/enumerations/transaction-type.model';
import { TxStatus } from 'app/shared/model/enumerations/tx-status.model';
import { CURRENCY } from 'app/shared/model/enumerations/currency.model';

describe('Service Tests', () => {
  describe('TransactionsRecord Service', () => {
    let injector: TestBed;
    let service: TransactionsRecordService;
    let httpMock: HttpTestingController;
    let elemDefault: ITransactionsRecord;
    let expectedResult: ITransactionsRecord | ITransactionsRecord[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TransactionsRecordService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new TransactionsRecord(
        0,
        TransactionType.CREDIT,
        'AAAAAAA',
        0,
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        currentDate,
        TxStatus.INITIATED,
        'image/png',
        'AAAAAAA',
        CURRENCY.USD,
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        currentDate,
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            txCreatedDate: currentDate.format(DATE_FORMAT),
            txCompletedDate: currentDate.format(DATE_FORMAT),
            createdOn: currentDate.format(DATE_TIME_FORMAT),
            updatedOn: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a TransactionsRecord', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            txCreatedDate: currentDate.format(DATE_FORMAT),
            txCompletedDate: currentDate.format(DATE_FORMAT),
            createdOn: currentDate.format(DATE_TIME_FORMAT),
            updatedOn: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            txCreatedDate: currentDate,
            txCompletedDate: currentDate,
            createdOn: currentDate,
            updatedOn: currentDate
          },
          returnedFromService
        );

        service.create(new TransactionsRecord()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TransactionsRecord', () => {
        const returnedFromService = Object.assign(
          {
            txType: 'BBBBBB',
            description: 'BBBBBB',
            txAmmount: 1,
            txRefNo: 'BBBBBB',
            txCreatedBy: 'BBBBBB',
            txCreatedDate: currentDate.format(DATE_FORMAT),
            txCompletedBy: 'BBBBBB',
            txCompletedDate: currentDate.format(DATE_FORMAT),
            status: 'BBBBBB',
            txDoc: 'BBBBBB',
            currency: 'BBBBBB',
            remarks: 'BBBBBB',
            createdOn: currentDate.format(DATE_TIME_FORMAT),
            createdBy: 'BBBBBB',
            updatedOn: currentDate.format(DATE_TIME_FORMAT),
            updatedBy: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            txCreatedDate: currentDate,
            txCompletedDate: currentDate,
            createdOn: currentDate,
            updatedOn: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of TransactionsRecord', () => {
        const returnedFromService = Object.assign(
          {
            txType: 'BBBBBB',
            description: 'BBBBBB',
            txAmmount: 1,
            txRefNo: 'BBBBBB',
            txCreatedBy: 'BBBBBB',
            txCreatedDate: currentDate.format(DATE_FORMAT),
            txCompletedBy: 'BBBBBB',
            txCompletedDate: currentDate.format(DATE_FORMAT),
            status: 'BBBBBB',
            txDoc: 'BBBBBB',
            currency: 'BBBBBB',
            remarks: 'BBBBBB',
            createdOn: currentDate.format(DATE_TIME_FORMAT),
            createdBy: 'BBBBBB',
            updatedOn: currentDate.format(DATE_TIME_FORMAT),
            updatedBy: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            txCreatedDate: currentDate,
            txCompletedDate: currentDate,
            createdOn: currentDate,
            updatedOn: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a TransactionsRecord', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
