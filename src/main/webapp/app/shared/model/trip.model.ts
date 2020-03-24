import { Moment } from 'moment';
import { ILocation } from 'app/shared/model/location.model';
import { IInvoice } from 'app/shared/model/invoice.model';
import { IContainer } from 'app/shared/model/container.model';
import { ICustomer } from 'app/shared/model/customer.model';
import { IDriver } from 'app/shared/model/driver.model';
import { IEquipment } from 'app/shared/model/equipment.model';
import { ICarrier } from 'app/shared/model/carrier.model';
import { TripType } from 'app/shared/model/enumerations/trip-type.model';
import { StatusEnum } from 'app/shared/model/enumerations/status-enum.model';
import { COVEREDBY } from 'app/shared/model/enumerations/coveredby.model';
import { LoadType } from 'app/shared/model/enumerations/load-type.model';
import { SizeEnum } from 'app/shared/model/enumerations/size-enum.model';

export interface ITrip {
  id?: number;
  customTripNumber?: string;
  description?: string;
  tripType?: TripType;
  shipmentNumber?: string;
  bol?: string;
  pickup?: Moment;
  drop?: Moment;
  currentLocation?: string;
  status?: StatusEnum;
  detention?: number;
  chasisInTime?: Moment;
  orderDocumentContentType?: string;
  orderDocument?: any;
  podContentType?: string;
  pod?: any;
  hazmat?: boolean;
  refrigerated?: boolean;
  liftgate?: boolean;
  recievedBy?: string;
  coveredBy?: COVEREDBY;
  loadType?: LoadType;
  containerSize?: SizeEnum;
  numbersOfContainer?: number;
  comments?: string;
  autoGenerateInvoice?: boolean;
  createdOn?: Moment;
  createdBy?: string;
  updatedOn?: Moment;
  updatedBy?: string;
  pickupLocation?: ILocation;
  dropLocation?: ILocation;
  invoices?: IInvoice[];
  containers?: IContainer[];
  customer?: ICustomer;
  driver?: IDriver;
  equipment?: IEquipment;
  carrier?: ICarrier;
}

export class Trip implements ITrip {
  constructor(
    public id?: number,
    public customTripNumber?: string,
    public description?: string,
    public tripType?: TripType,
    public shipmentNumber?: string,
    public bol?: string,
    public pickup?: Moment,
    public drop?: Moment,
    public currentLocation?: string,
    public status?: StatusEnum,
    public detention?: number,
    public chasisInTime?: Moment,
    public orderDocumentContentType?: string,
    public orderDocument?: any,
    public podContentType?: string,
    public pod?: any,
    public hazmat?: boolean,
    public refrigerated?: boolean,
    public liftgate?: boolean,
    public recievedBy?: string,
    public coveredBy?: COVEREDBY,
    public loadType?: LoadType,
    public containerSize?: SizeEnum,
    public numbersOfContainer?: number,
    public comments?: string,
    public autoGenerateInvoice?: boolean,
    public createdOn?: Moment,
    public createdBy?: string,
    public updatedOn?: Moment,
    public updatedBy?: string,
    public pickupLocation?: ILocation,
    public dropLocation?: ILocation,
    public invoices?: IInvoice[],
    public containers?: IContainer[],
    public customer?: ICustomer,
    public driver?: IDriver,
    public equipment?: IEquipment,
    public carrier?: ICarrier
  ) {
    this.hazmat = this.hazmat || false;
    this.refrigerated = this.refrigerated || false;
    this.liftgate = this.liftgate || false;
    this.autoGenerateInvoice = this.autoGenerateInvoice || false;
  }
}
