import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { EmailService } from 'app/entities/email/email.service';
import { IEmail, Email } from 'app/shared/model/email.model';

describe('Service Tests', () => {
  describe('Email Service', () => {
    let injector: TestBed;
    let service: EmailService;
    let httpMock: HttpTestingController;
    let elemDefault: IEmail;
    let expectedResult: IEmail | IEmail[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(EmailService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Email(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        false,
        false,
        'image/png',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
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
            sentDateTime: currentDate.format(DATE_TIME_FORMAT),
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

      it('should create a Email', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            sentDateTime: currentDate.format(DATE_TIME_FORMAT),
            createdOn: currentDate.format(DATE_TIME_FORMAT),
            updatedOn: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            sentDateTime: currentDate,
            createdOn: currentDate,
            updatedOn: currentDate
          },
          returnedFromService
        );

        service.create(new Email()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Email', () => {
        const returnedFromService = Object.assign(
          {
            userto: 'BBBBBB',
            usercc: 'BBBBBB',
            userbcc: 'BBBBBB',
            subject: 'BBBBBB',
            message: 'BBBBBB',
            multipart: true,
            htmlBody: true,
            attachment: 'BBBBBB',
            attachmentName: 'BBBBBB',
            status: 'BBBBBB',
            sentDateTime: currentDate.format(DATE_TIME_FORMAT),
            createdOn: currentDate.format(DATE_TIME_FORMAT),
            createdBy: 'BBBBBB',
            updatedOn: currentDate.format(DATE_TIME_FORMAT),
            updatedBy: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            sentDateTime: currentDate,
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

      it('should return a list of Email', () => {
        const returnedFromService = Object.assign(
          {
            userto: 'BBBBBB',
            usercc: 'BBBBBB',
            userbcc: 'BBBBBB',
            subject: 'BBBBBB',
            message: 'BBBBBB',
            multipart: true,
            htmlBody: true,
            attachment: 'BBBBBB',
            attachmentName: 'BBBBBB',
            status: 'BBBBBB',
            sentDateTime: currentDate.format(DATE_TIME_FORMAT),
            createdOn: currentDate.format(DATE_TIME_FORMAT),
            createdBy: 'BBBBBB',
            updatedOn: currentDate.format(DATE_TIME_FORMAT),
            updatedBy: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            sentDateTime: currentDate,
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

      it('should delete a Email', () => {
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
