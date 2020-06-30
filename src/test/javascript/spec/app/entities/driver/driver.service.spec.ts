import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { DriverService } from 'app/entities/driver/driver.service';
import { IDriver, Driver } from 'app/shared/model/driver.model';
import { ToggleStatus } from 'app/shared/model/enumerations/toggle-status.model';

describe('Service Tests', () => {
  describe('Driver Service', () => {
    let injector: TestBed;
    let service: DriverService;
    let httpMock: HttpTestingController;
    let elemDefault: IDriver;
    let expectedResult: IDriver | IDriver[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(DriverService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Driver(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        'AAAAAAA',
        currentDate,
        currentDate,
        currentDate,
        'image/png',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        ToggleStatus.ACTIVE,
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
            dob: currentDate.format(DATE_FORMAT),
            companyJoinedOn: currentDate.format(DATE_FORMAT),
            companyLeftOn: currentDate.format(DATE_FORMAT),
            createdDate: currentDate.format(DATE_TIME_FORMAT),
            lastModifiedDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Driver', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dob: currentDate.format(DATE_FORMAT),
            companyJoinedOn: currentDate.format(DATE_FORMAT),
            companyLeftOn: currentDate.format(DATE_FORMAT),
            createdDate: currentDate.format(DATE_TIME_FORMAT),
            lastModifiedDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dob: currentDate,
            companyJoinedOn: currentDate,
            companyLeftOn: currentDate,
            createdDate: currentDate,
            lastModifiedDate: currentDate
          },
          returnedFromService
        );

        service.create(new Driver()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Driver', () => {
        const returnedFromService = Object.assign(
          {
            company: 'BBBBBB',
            firstName: 'BBBBBB',
            lastName: 'BBBBBB',
            email: 'BBBBBB',
            phoneNumber: 1,
            licenceNumber: 'BBBBBB',
            dob: currentDate.format(DATE_FORMAT),
            companyJoinedOn: currentDate.format(DATE_FORMAT),
            companyLeftOn: currentDate.format(DATE_FORMAT),
            image: 'BBBBBB',
            licenceImage: 'BBBBBB',
            remarks: 'BBBBBB',
            contractDoc: 'BBBBBB',
            status: 'BBBBBB',
            createdDate: currentDate.format(DATE_TIME_FORMAT),
            createdBy: 'BBBBBB',
            lastModifiedDate: currentDate.format(DATE_TIME_FORMAT),
            lastModifiedBy: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dob: currentDate,
            companyJoinedOn: currentDate,
            companyLeftOn: currentDate,
            createdDate: currentDate,
            lastModifiedDate: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Driver', () => {
        const returnedFromService = Object.assign(
          {
            company: 'BBBBBB',
            firstName: 'BBBBBB',
            lastName: 'BBBBBB',
            email: 'BBBBBB',
            phoneNumber: 1,
            licenceNumber: 'BBBBBB',
            dob: currentDate.format(DATE_FORMAT),
            companyJoinedOn: currentDate.format(DATE_FORMAT),
            companyLeftOn: currentDate.format(DATE_FORMAT),
            image: 'BBBBBB',
            licenceImage: 'BBBBBB',
            remarks: 'BBBBBB',
            contractDoc: 'BBBBBB',
            status: 'BBBBBB',
            createdDate: currentDate.format(DATE_TIME_FORMAT),
            createdBy: 'BBBBBB',
            lastModifiedDate: currentDate.format(DATE_TIME_FORMAT),
            lastModifiedBy: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dob: currentDate,
            companyJoinedOn: currentDate,
            companyLeftOn: currentDate,
            createdDate: currentDate,
            lastModifiedDate: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Driver', () => {
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
