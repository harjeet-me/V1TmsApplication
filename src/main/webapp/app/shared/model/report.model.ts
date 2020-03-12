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
  createdOn?: Moment;
  createdBy?: string;
  updatedOn?: Moment;
  updatedBy?: string;
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
    public createdOn?: Moment,
    public createdBy?: string,
    public updatedOn?: Moment,
    public updatedBy?: string
  ) {}
}
