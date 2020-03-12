import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { ILocation } from 'app/shared/model/location.model';

type EntityResponseType = HttpResponse<ILocation>;
type EntityArrayResponseType = HttpResponse<ILocation[]>;

@Injectable({ providedIn: 'root' })
export class LocationService {
  public resourceUrl = SERVER_API_URL + 'api/locations';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/locations';

  constructor(protected http: HttpClient) {}

  create(location: ILocation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(location);
    return this.http
      .post<ILocation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(location: ILocation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(location);
    return this.http
      .put<ILocation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ILocation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILocation[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILocation[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(location: ILocation): ILocation {
    const copy: ILocation = Object.assign({}, location, {
      createdOn: location.createdOn && location.createdOn.isValid() ? location.createdOn.toJSON() : undefined,
      updatedOn: location.updatedOn && location.updatedOn.isValid() ? location.updatedOn.toJSON() : undefined
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
      res.body.forEach((location: ILocation) => {
        location.createdOn = location.createdOn ? moment(location.createdOn) : undefined;
        location.updatedOn = location.updatedOn ? moment(location.updatedOn) : undefined;
      });
    }
    return res;
  }
}
