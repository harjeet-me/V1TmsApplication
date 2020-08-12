import { Moment } from 'moment';
import { ILocation } from 'app/shared/model/location.model';
import { ITrip } from 'app/shared/model/trip.model';
import { TripType } from 'app/shared/model/enumerations/trip-type.model';
import { SizeEnum } from 'app/shared/model/enumerations/size-enum.model';

export interface IContainer {
  id?: number;
  number?: string;
  tripType?: TripType;
  pickup?: Moment;
  drop?: Moment;
  containerSize?: SizeEnum;
  createdDate?: Moment;
  createdBy?: string;
  lastModifiedDate?: Moment;
  lastModifiedBy?: string;
  pickupLocation?: ILocation;
  dropLocation?: ILocation;
  trip?: ITrip;
}

export class Container implements IContainer {
  constructor(
    public id?: number,
    public number?: string,
    public tripType?: TripType,
    public pickup?: Moment,
    public drop?: Moment,
    public containerSize?: SizeEnum,
    public createdDate?: Moment,
    public createdBy?: string,
    public lastModifiedDate?: Moment,
    public lastModifiedBy?: string,
    public pickupLocation?: ILocation,
    public dropLocation?: ILocation,
    public trip?: ITrip
  ) {}
}
