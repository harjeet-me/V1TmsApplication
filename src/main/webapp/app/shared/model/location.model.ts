import { Moment } from 'moment';
import { ITrip } from 'app/shared/model/trip.model';
import { IContainer } from 'app/shared/model/container.model';
import { CountryEnum } from 'app/shared/model/enumerations/country-enum.model';

export interface ILocation {
  id?: number;
  address?: string;
  streetAddress?: string;
  city?: string;
  stateProvince?: string;
  country?: CountryEnum;
  postalCode?: string;
  latitude?: number;
  longitude?: number;
  createdDate?: Moment;
  createdBy?: string;
  lastModifiedDate?: Moment;
  lastModifiedBy?: string;
  trippicks?: ITrip[];
  tripdrops?: ITrip[];
  contpicks?: IContainer[];
  contdrops?: IContainer[];
}

export class Location implements ILocation {
  constructor(
    public id?: number,
    public address?: string,
    public streetAddress?: string,
    public city?: string,
    public stateProvince?: string,
    public country?: CountryEnum,
    public postalCode?: string,
    public latitude?: number,
    public longitude?: number,
    public createdDate?: Moment,
    public createdBy?: string,
    public lastModifiedDate?: Moment,
    public lastModifiedBy?: string,
    public trippicks?: ITrip[],
    public tripdrops?: ITrip[],
    public contpicks?: IContainer[],
    public contdrops?: IContainer[]
  ) {}
}
