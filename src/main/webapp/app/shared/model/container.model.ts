import { Moment } from 'moment';
import { ITrip } from 'app/shared/model/trip.model';

export interface IContainer {
  id?: number;
  number?: string;
  description?: string;
  size?: number;
  createdDate?: Moment;
  createdBy?: string;
  lastModifiedDate?: Moment;
  lastModifiedBy?: string;
  trip?: ITrip;
}

export class Container implements IContainer {
  constructor(
    public id?: number,
    public number?: string,
    public description?: string,
    public size?: number,
    public createdDate?: Moment,
    public createdBy?: string,
    public lastModifiedDate?: Moment,
    public lastModifiedBy?: string,
    public trip?: ITrip
  ) {}
}
