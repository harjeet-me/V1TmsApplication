import { Moment } from 'moment';
import { CountryEnum } from 'app/shared/model/enumerations/country-enum.model';
import { ToggleStatus } from 'app/shared/model/enumerations/toggle-status.model';
import { CURRENCY } from 'app/shared/model/enumerations/currency.model';

export interface ICompanyProfile {
  id?: number;
  active?: boolean;
  company?: string;
  address?: string;
  streetAddress?: string;
  city?: string;
  stateProvince?: string;
  country?: CountryEnum;
  postalCode?: string;
  email?: string;
  website?: string;
  phoneNumber?: number;
  dot?: string;
  mc?: number;
  companyLogoContentType?: string;
  companyLogo?: any;
  profileStatus?: ToggleStatus;
  preffredCurrency?: CURRENCY;
  createdDate?: Moment;
  createdBy?: string;
  lastModifiedDate?: Moment;
  lastModifiedBy?: string;
}

export class CompanyProfile implements ICompanyProfile {
  constructor(
    public id?: number,
    public active?: boolean,
    public company?: string,
    public address?: string,
    public streetAddress?: string,
    public city?: string,
    public stateProvince?: string,
    public country?: CountryEnum,
    public postalCode?: string,
    public email?: string,
    public website?: string,
    public phoneNumber?: number,
    public dot?: string,
    public mc?: number,
    public companyLogoContentType?: string,
    public companyLogo?: any,
    public profileStatus?: ToggleStatus,
    public preffredCurrency?: CURRENCY,
    public createdDate?: Moment,
    public createdBy?: string,
    public lastModifiedDate?: Moment,
    public lastModifiedBy?: string
  ) {
    this.active = this.active || false;
  }
}
