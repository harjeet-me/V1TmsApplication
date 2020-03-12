import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IFileSystem } from 'app/shared/model/file-system.model';

type EntityResponseType = HttpResponse<IFileSystem>;
type EntityArrayResponseType = HttpResponse<IFileSystem[]>;

@Injectable({ providedIn: 'root' })
export class FileSystemService {
  public resourceUrl = SERVER_API_URL + 'api/file-systems';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/file-systems';

  constructor(protected http: HttpClient) {}

  create(fileSystem: IFileSystem): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fileSystem);
    return this.http
      .post<IFileSystem>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(fileSystem: IFileSystem): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fileSystem);
    return this.http
      .put<IFileSystem>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFileSystem>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFileSystem[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFileSystem[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(fileSystem: IFileSystem): IFileSystem {
    const copy: IFileSystem = Object.assign({}, fileSystem, {
      createdOn: fileSystem.createdOn && fileSystem.createdOn.isValid() ? fileSystem.createdOn.toJSON() : undefined,
      updatedOn: fileSystem.updatedOn && fileSystem.updatedOn.isValid() ? fileSystem.updatedOn.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdOn = res.body.createdOn ? moment(res.body.createdOn) : undefined;
      res.body.updatedOn = res.body.updatedOn ? moment(res.body.updatedOn) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((fileSystem: IFileSystem) => {
        fileSystem.createdOn = fileSystem.createdOn ? moment(fileSystem.createdOn) : undefined;
        fileSystem.updatedOn = fileSystem.updatedOn ? moment(fileSystem.updatedOn) : undefined;
      });
    }
    return res;
  }
}
