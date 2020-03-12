import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { ITrip } from 'app/shared/model/trip.model';

type EntityResponseType = HttpResponse<ITrip>;
type EntityArrayResponseType = HttpResponse<ITrip[]>;

@Injectable({ providedIn: 'root' })
export class TripService {
  public resourceUrl = SERVER_API_URL + 'api/trips';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/trips';

  constructor(protected http: HttpClient) {}

  create(trip: ITrip): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(trip);
    return this.http
      .post<ITrip>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(trip: ITrip): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(trip);
    return this.http
      .put<ITrip>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITrip>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITrip[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITrip[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(trip: ITrip): ITrip {
    const copy: ITrip = Object.assign({}, trip, {
      pickup: trip.pickup && trip.pickup.isValid() ? trip.pickup.format(DATE_FORMAT) : undefined,
      drop: trip.drop && trip.drop.isValid() ? trip.drop.format(DATE_FORMAT) : undefined,
      chasisInTime: trip.chasisInTime && trip.chasisInTime.isValid() ? trip.chasisInTime.toJSON() : undefined,
      createdOn: trip.createdOn && trip.createdOn.isValid() ? trip.createdOn.toJSON() : undefined,
      updatedOn: trip.updatedOn && trip.updatedOn.isValid() ? trip.updatedOn.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.pickup = res.body.pickup ? moment(res.body.pickup) : undefined;
      res.body.drop = res.body.drop ? moment(res.body.drop) : undefined;
      res.body.chasisInTime = res.body.chasisInTime ? moment(res.body.chasisInTime) : undefined;
      res.body.createdOn = res.body.createdOn ? moment(res.body.createdOn) : undefined;
      res.body.updatedOn = res.body.updatedOn ? moment(res.body.updatedOn) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((trip: ITrip) => {
        trip.pickup = trip.pickup ? moment(trip.pickup) : undefined;
        trip.drop = trip.drop ? moment(trip.drop) : undefined;
        trip.chasisInTime = trip.chasisInTime ? moment(trip.chasisInTime) : undefined;
        trip.createdOn = trip.createdOn ? moment(trip.createdOn) : undefined;
        trip.updatedOn = trip.updatedOn ? moment(trip.updatedOn) : undefined;
      });
    }
    return res;
  }
}
