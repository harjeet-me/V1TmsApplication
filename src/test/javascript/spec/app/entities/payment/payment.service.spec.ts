import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { PaymentService } from 'app/entities/payment/payment.service';
import { IPayment, Payment } from 'app/shared/model/payment.model';
import { InvoiceStatus } from 'app/shared/model/enumerations/invoice-status.model';

describe('Service Tests', () => {
  describe('Payment Service', () => {
    let injector: TestBed;
    let service: PaymentService;
    let httpMock: HttpTestingController;
    let elemDefault: IPayment;
    let expectedResult: IPayment | IPayment[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PaymentService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Payment(
        0,
        'AAAAAAA',
        0,
        currentDate,
        'AAAAAAA',
        InvoiceStatus.DRAFT,
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
            invoicePaidDate: currentDate.format(DATE_FORMAT),
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

      it('should create a Payment', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            invoicePaidDate: currentDate.format(DATE_FORMAT),
            createdOn: currentDate.format(DATE_TIME_FORMAT),
            updatedOn: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            invoicePaidDate: currentDate,
            createdOn: currentDate,
            updatedOn: currentDate
          },
          returnedFromService
        );

        service.create(new Payment()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Payment', () => {
        const returnedFromService = Object.assign(
          {
            invoiceNo: 'BBBBBB',
            paymentAmt: 1,
            invoicePaidDate: currentDate.format(DATE_FORMAT),
            payRefNo: 'BBBBBB',
            status: 'BBBBBB',
            createdOn: currentDate.format(DATE_TIME_FORMAT),
            createdBy: 'BBBBBB',
            updatedOn: currentDate.format(DATE_TIME_FORMAT),
            updatedBy: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            invoicePaidDate: currentDate,
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

      it('should return a list of Payment', () => {
        const returnedFromService = Object.assign(
          {
            invoiceNo: 'BBBBBB',
            paymentAmt: 1,
            invoicePaidDate: currentDate.format(DATE_FORMAT),
            payRefNo: 'BBBBBB',
            status: 'BBBBBB',
            createdOn: currentDate.format(DATE_TIME_FORMAT),
            createdBy: 'BBBBBB',
            updatedOn: currentDate.format(DATE_TIME_FORMAT),
            updatedBy: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            invoicePaidDate: currentDate,
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

      it('should delete a Payment', () => {
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
