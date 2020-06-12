import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IInsurance } from 'app/shared/model/insurance.model';

type EntityResponseType = HttpResponse<IInsurance>;
type EntityArrayResponseType = HttpResponse<IInsurance[]>;

@Injectable({ providedIn: 'root' })
export class InsuranceService {
  public resourceUrl = SERVER_API_URL + 'api/insurances';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/insurances';

  constructor(protected http: HttpClient) {}

  create(insurance: IInsurance): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(insurance);
    return this.http
      .post<IInsurance>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(insurance: IInsurance): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(insurance);
    return this.http
      .put<IInsurance>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IInsurance>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IInsurance[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IInsurance[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(insurance: IInsurance): IInsurance {
    const copy: IInsurance = Object.assign({}, insurance, {
      issueDate: insurance.issueDate && insurance.issueDate.isValid() ? insurance.issueDate.format(DATE_FORMAT) : undefined,
      expiryDate: insurance.expiryDate && insurance.expiryDate.isValid() ? insurance.expiryDate.format(DATE_FORMAT) : undefined,
      createdDate: insurance.createdDate && insurance.createdDate.isValid() ? insurance.createdDate.toJSON() : undefined,
      lastModifiedDate: insurance.lastModifiedDate && insurance.lastModifiedDate.isValid() ? insurance.lastModifiedDate.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.issueDate = res.body.issueDate ? moment(res.body.issueDate) : undefined;
      res.body.expiryDate = res.body.expiryDate ? moment(res.body.expiryDate) : undefined;
      res.body.createdDate = res.body.createdDate ? moment(res.body.createdDate) : undefined;
      res.body.lastModifiedDate = res.body.lastModifiedDate ? moment(res.body.lastModifiedDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((insurance: IInsurance) => {
        insurance.issueDate = insurance.issueDate ? moment(insurance.issueDate) : undefined;
        insurance.expiryDate = insurance.expiryDate ? moment(insurance.expiryDate) : undefined;
        insurance.createdDate = insurance.createdDate ? moment(insurance.createdDate) : undefined;
        insurance.lastModifiedDate = insurance.lastModifiedDate ? moment(insurance.lastModifiedDate) : undefined;
      });
    }
    return res;
  }
}
