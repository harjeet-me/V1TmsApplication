import { Moment } from 'moment';
import { IEmail } from 'app/shared/model/email.model';

export interface IFileSystem {
  id?: number;
  dataContentType?: string;
  data?: any;
  fileName?: string;
  createdDate?: Moment;
  createdBy?: string;
  lastModifiedDate?: Moment;
  lastModifiedBy?: string;
  email?: IEmail;
}

export class FileSystem implements IFileSystem {
  constructor(
    public id?: number,
    public dataContentType?: string,
    public data?: any,
    public fileName?: string,
    public createdDate?: Moment,
    public createdBy?: string,
    public lastModifiedDate?: Moment,
    public lastModifiedBy?: string,
    public email?: IEmail
  ) {}
}
