import { Moment } from 'moment';
import { ITrip } from 'app/shared/model/trip.model';

export interface IContainer {
  id?: number;
  number?: string;
  description?: string;
  size?: number;
  createdOn?: Moment;
  createdBy?: string;
  updatedOn?: Moment;
  updatedBy?: string;
  trip?: ITrip;
}

export class Container implements IContainer {
  constructor(
    public id?: number,
    public number?: string,
    public description?: string,
    public size?: number,
    public createdOn?: Moment,
    public createdBy?: string,
    public updatedOn?: Moment,
    public updatedBy?: string,
    public trip?: ITrip
  ) {}
}
