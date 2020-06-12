import { Moment } from 'moment';
import { IInsurance } from 'app/shared/model/insurance.model';
import { ITrip } from 'app/shared/model/trip.model';
import { EquipmentEnum } from 'app/shared/model/enumerations/equipment-enum.model';
import { ToggleStatus } from 'app/shared/model/enumerations/toggle-status.model';

export interface IEquipment {
  id?: number;
  enumber?: string;
  type?: EquipmentEnum;
  ownershiptype?: string;
  status?: ToggleStatus;
  vin?: string;
  make?: string;
  model?: string;
  description?: string;
  year?: string;
  yearPurchased?: string;
  licensePlateNumber?: string;
  licensePlateExpiration?: Moment;
  inspectionStickerExpiration?: Moment;
  createdDate?: Moment;
  createdBy?: string;
  lastModifiedDate?: Moment;
  lastModifiedBy?: string;
  insurance?: IInsurance;
  trips?: ITrip[];
}

export class Equipment implements IEquipment {
  constructor(
    public id?: number,
    public enumber?: string,
    public type?: EquipmentEnum,
    public ownershiptype?: string,
    public status?: ToggleStatus,
    public vin?: string,
    public make?: string,
    public model?: string,
    public description?: string,
    public year?: string,
    public yearPurchased?: string,
    public licensePlateNumber?: string,
    public licensePlateExpiration?: Moment,
    public inspectionStickerExpiration?: Moment,
    public createdDate?: Moment,
    public createdBy?: string,
    public lastModifiedDate?: Moment,
    public lastModifiedBy?: string,
    public insurance?: IInsurance,
    public trips?: ITrip[]
  ) {}
}
