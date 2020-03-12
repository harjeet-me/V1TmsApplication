import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IEquipment } from 'app/shared/model/equipment.model';

type EntityResponseType = HttpResponse<IEquipment>;
type EntityArrayResponseType = HttpResponse<IEquipment[]>;

@Injectable({ providedIn: 'root' })
export class EquipmentService {
  public resourceUrl = SERVER_API_URL + 'api/equipment';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/equipment';

  constructor(protected http: HttpClient) {}

  create(equipment: IEquipment): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(equipment);
    return this.http
      .post<IEquipment>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(equipment: IEquipment): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(equipment);
    return this.http
      .put<IEquipment>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEquipment>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEquipment[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEquipment[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(equipment: IEquipment): IEquipment {
    const copy: IEquipment = Object.assign({}, equipment, {
      licensePlateExpiration:
        equipment.licensePlateExpiration && equipment.licensePlateExpiration.isValid()
          ? equipment.licensePlateExpiration.format(DATE_FORMAT)
          : undefined,
      inspectionStickerExpiration:
        equipment.inspectionStickerExpiration && equipment.inspectionStickerExpiration.isValid()
          ? equipment.inspectionStickerExpiration.format(DATE_FORMAT)
          : undefined,
      createdOn: equipment.createdOn && equipment.createdOn.isValid() ? equipment.createdOn.toJSON() : undefined,
      updatedOn: equipment.updatedOn && equipment.updatedOn.isValid() ? equipment.updatedOn.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.licensePlateExpiration = res.body.licensePlateExpiration ? moment(res.body.licensePlateExpiration) : undefined;
      res.body.inspectionStickerExpiration = res.body.inspectionStickerExpiration
        ? moment(res.body.inspectionStickerExpiration)
        : undefined;
      res.body.createdOn = res.body.createdOn ? moment(res.body.createdOn) : undefined;
      res.body.updatedOn = res.body.updatedOn ? moment(res.body.updatedOn) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((equipment: IEquipment) => {
        equipment.licensePlateExpiration = equipment.licensePlateExpiration ? moment(equipment.licensePlateExpiration) : undefined;
        equipment.inspectionStickerExpiration = equipment.inspectionStickerExpiration
          ? moment(equipment.inspectionStickerExpiration)
          : undefined;
        equipment.createdOn = equipment.createdOn ? moment(equipment.createdOn) : undefined;
        equipment.updatedOn = equipment.updatedOn ? moment(equipment.updatedOn) : undefined;
      });
    }
    return res;
  }
}
