import { Moment } from 'moment';
import { ReportType } from 'app/shared/model/enumerations/report-type.model';

export interface IReport {
  id?: number;
  reportType?: ReportType;
  description?: string;
  fromDate?: Moment;
  toDate?: Moment;
  attachmentContentType?: string;
  attachment?: any;
  emailTo?: string;
  createdDate?: Moment;
  createdBy?: string;
  lastModifiedDate?: Moment;
  lastModifiedBy?: string;
}

export class Report implements IReport {
  constructor(
    public id?: number,
    public reportType?: ReportType,
    public description?: string,
    public fromDate?: Moment,
    public toDate?: Moment,
    public attachmentContentType?: string,
    public attachment?: any,
    public emailTo?: string,
    public createdDate?: Moment,
    public createdBy?: string,
    public lastModifiedDate?: Moment,
    public lastModifiedBy?: string
  ) {}
}
