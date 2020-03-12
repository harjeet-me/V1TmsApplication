import { Moment } from 'moment';
import { ITrip } from 'app/shared/model/trip.model';
import { ToggleStatus } from 'app/shared/model/enumerations/toggle-status.model';

export interface IDriver {
  id?: number;
  company?: string;
  firstName?: string;
  lastName?: string;
  email?: string;
  phoneNumber?: number;
  licenceNumber?: number;
  dob?: Moment;
  companyJoinedOn?: Moment;
  companyLeftOn?: Moment;
  imageContentType?: string;
  image?: any;
  licenceImageContentType?: string;
  licenceImage?: any;
  remarks?: string;
  contractDocContentType?: string;
  contractDoc?: any;
  status?: ToggleStatus;
  createdOn?: Moment;
  createdBy?: string;
  updatedOn?: Moment;
  updatedBy?: string;
  trips?: ITrip[];
}

export class Driver implements IDriver {
  constructor(
    public id?: number,
    public company?: string,
    public firstName?: string,
    public lastName?: string,
    public email?: string,
    public phoneNumber?: number,
    public licenceNumber?: number,
    public dob?: Moment,
    public companyJoinedOn?: Moment,
    public companyLeftOn?: Moment,
    public imageContentType?: string,
    public image?: any,
    public licenceImageContentType?: string,
    public licenceImage?: any,
    public remarks?: string,
    public contractDocContentType?: string,
    public contractDoc?: any,
    public status?: ToggleStatus,
    public createdOn?: Moment,
    public createdBy?: string,
    public updatedOn?: Moment,
    public updatedBy?: string,
    public trips?: ITrip[]
  ) {}
}
