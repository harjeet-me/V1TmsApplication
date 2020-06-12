import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { CarrierService } from 'app/entities/carrier/carrier.service';
import { ICarrier, Carrier } from 'app/shared/model/carrier.model';
import { Designation } from 'app/shared/model/enumerations/designation.model';
import { PreffredContactType } from 'app/shared/model/enumerations/preffred-contact-type.model';
import { CountryEnum } from 'app/shared/model/enumerations/country-enum.model';
import { ToggleStatus } from 'app/shared/model/enumerations/toggle-status.model';
import { CURRENCY } from 'app/shared/model/enumerations/currency.model';

describe('Service Tests', () => {
  describe('Carrier Service', () => {
    let injector: TestBed;
    let service: CarrierService;
    let httpMock: HttpTestingController;
    let elemDefault: ICarrier;
    let expectedResult: ICarrier | ICarrier[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(CarrierService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Carrier(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        Designation.MANAGER,
        'AAAAAAA',
        0,
        0,
        PreffredContactType.PHONE,
        'AAAAAAA',
        'AAAAAAA',
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        CountryEnum.USA,
        'AAAAAAA',
        'AAAAAAA',
        0,
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        ToggleStatus.ACTIVE,
        CURRENCY.USD,
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
            customerSince: currentDate.format(DATE_FORMAT),
            timeZone: currentDate.format(DATE_TIME_FORMAT),
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

      it('should create a Carrier', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            customerSince: currentDate.format(DATE_FORMAT),
            timeZone: currentDate.format(DATE_TIME_FORMAT),
            createdDate: currentDate.format(DATE_TIME_FORMAT),
            lastModifiedDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            customerSince: currentDate,
            timeZone: currentDate,
            createdDate: currentDate,
            lastModifiedDate: currentDate
          },
          returnedFromService
        );

        service.create(new Carrier()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Carrier', () => {
        const returnedFromService = Object.assign(
          {
            company: 'BBBBBB',
            firstName: 'BBBBBB',
            lastName: 'BBBBBB',
            contactDesignation: 'BBBBBB',
            email: 'BBBBBB',
            phoneNumber: 1,
            phoneNumberExtention: 1,
            preffredContactType: 'BBBBBB',
            website: 'BBBBBB',
            alternateContactPerson: 'BBBBBB',
            alternateContactNumber: 1,
            alternatePhoneNumberExtention: 1,
            alternateContactEmail: 'BBBBBB',
            preferredContactTime: 'BBBBBB',
            fax: 1,
            address: 'BBBBBB',
            streetAddress: 'BBBBBB',
            city: 'BBBBBB',
            stateProvince: 'BBBBBB',
            country: 'BBBBBB',
            postalCode: 'BBBBBB',
            dot: 'BBBBBB',
            mc: 1,
            taxId: 'BBBBBB',
            companyLogo: 'BBBBBB',
            customerSince: currentDate.format(DATE_FORMAT),
            notes: 'BBBBBB',
            contract: 'BBBBBB',
            status: 'BBBBBB',
            preffredCurrency: 'BBBBBB',
            payterms: 'BBBBBB',
            timeZone: currentDate.format(DATE_TIME_FORMAT),
            createdDate: currentDate.format(DATE_TIME_FORMAT),
            createdBy: 'BBBBBB',
            lastModifiedDate: currentDate.format(DATE_TIME_FORMAT),
            lastModifiedBy: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            customerSince: currentDate,
            timeZone: currentDate,
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

      it('should return a list of Carrier', () => {
        const returnedFromService = Object.assign(
          {
            company: 'BBBBBB',
            firstName: 'BBBBBB',
            lastName: 'BBBBBB',
            contactDesignation: 'BBBBBB',
            email: 'BBBBBB',
            phoneNumber: 1,
            phoneNumberExtention: 1,
            preffredContactType: 'BBBBBB',
            website: 'BBBBBB',
            alternateContactPerson: 'BBBBBB',
            alternateContactNumber: 1,
            alternatePhoneNumberExtention: 1,
            alternateContactEmail: 'BBBBBB',
            preferredContactTime: 'BBBBBB',
            fax: 1,
            address: 'BBBBBB',
            streetAddress: 'BBBBBB',
            city: 'BBBBBB',
            stateProvince: 'BBBBBB',
            country: 'BBBBBB',
            postalCode: 'BBBBBB',
            dot: 'BBBBBB',
            mc: 1,
            taxId: 'BBBBBB',
            companyLogo: 'BBBBBB',
            customerSince: currentDate.format(DATE_FORMAT),
            notes: 'BBBBBB',
            contract: 'BBBBBB',
            status: 'BBBBBB',
            preffredCurrency: 'BBBBBB',
            payterms: 'BBBBBB',
            timeZone: currentDate.format(DATE_TIME_FORMAT),
            createdDate: currentDate.format(DATE_TIME_FORMAT),
            createdBy: 'BBBBBB',
            lastModifiedDate: currentDate.format(DATE_TIME_FORMAT),
            lastModifiedBy: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            customerSince: currentDate,
            timeZone: currentDate,
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

      it('should delete a Carrier', () => {
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
