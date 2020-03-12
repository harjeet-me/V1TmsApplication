import { Moment } from 'moment';
import { IInsurance } from 'app/shared/model/insurance.model';
import { ITrip } from 'app/shared/model/trip.model';
import { Designation } from 'app/shared/model/enumerations/designation.model';
import { PreffredContactType } from 'app/shared/model/enumerations/preffred-contact-type.model';
import { CountryEnum } from 'app/shared/model/enumerations/country-enum.model';
import { ToggleStatus } from 'app/shared/model/enumerations/toggle-status.model';
import { CURRENCY } from 'app/shared/model/enumerations/currency.model';

export interface ICarrier {
  id?: number;
  company?: string;
  firstName?: string;
  lastName?: string;
  contactDesignation?: Designation;
  email?: string;
  phoneNumber?: number;
  phoneNumberExtention?: number;
  preffredContactType?: PreffredContactType;
  website?: string;
  alternateContactPerson?: string;
  alternateContactNumber?: number;
  alternatePhoneNumberExtention?: number;
  alternateContactEmail?: string;
  preferredContactTime?: string;
  fax?: number;
  address?: string;
  streetAddress?: string;
  city?: string;
  stateProvince?: string;
  country?: CountryEnum;
  postalCode?: string;
  dot?: string;
  mc?: number;
  taxId?: string;
  companyLogoContentType?: string;
  companyLogo?: any;
  customerSince?: Moment;
  notes?: string;
  contractContentType?: string;
  contract?: any;
  status?: ToggleStatus;
  preffredCurrency?: CURRENCY;
  payterms?: string;
  timeZone?: Moment;
  createdOn?: Moment;
  createdBy?: string;
  updatedOn?: Moment;
  updatedBy?: string;
  operInsurance?: IInsurance;
  loadOrders?: ITrip[];
}

export class Carrier implements ICarrier {
  constructor(
    public id?: number,
    public company?: string,
    public firstName?: string,
    public lastName?: string,
    public contactDesignation?: Designation,
    public email?: string,
    public phoneNumber?: number,
    public phoneNumberExtention?: number,
    public preffredContactType?: PreffredContactType,
    public website?: string,
    public alternateContactPerson?: string,
    public alternateContactNumber?: number,
    public alternatePhoneNumberExtention?: number,
    public alternateContactEmail?: string,
    public preferredContactTime?: string,
    public fax?: number,
    public address?: string,
    public streetAddress?: string,
    public city?: string,
    public stateProvince?: string,
    public country?: CountryEnum,
    public postalCode?: string,
    public dot?: string,
    public mc?: number,
    public taxId?: string,
    public companyLogoContentType?: string,
    public companyLogo?: any,
    public customerSince?: Moment,
    public notes?: string,
    public contractContentType?: string,
    public contract?: any,
    public status?: ToggleStatus,
    public preffredCurrency?: CURRENCY,
    public payterms?: string,
    public timeZone?: Moment,
    public createdOn?: Moment,
    public createdBy?: string,
    public updatedOn?: Moment,
    public updatedBy?: string,
    public operInsurance?: IInsurance,
    public loadOrders?: ITrip[]
  ) {}
}
