import { Moment } from 'moment';
import { IEmail } from 'app/shared/model/email.model';

export interface IFileSystem {
  id?: number;
  dataContentType?: string;
  data?: any;
  fileName?: string;
  createdOn?: Moment;
  createdBy?: string;
  updatedOn?: Moment;
  updatedBy?: string;
  email?: IEmail;
}

export class FileSystem implements IFileSystem {
  constructor(
    public id?: number,
    public dataContentType?: string,
    public data?: any,
    public fileName?: string,
    public createdOn?: Moment,
    public createdBy?: string,
    public updatedOn?: Moment,
    public updatedBy?: string,
    public email?: IEmail
  ) {}
}
