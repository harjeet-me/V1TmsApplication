import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { ICompanyProfile } from 'app/shared/model/company-profile.model';

type EntityResponseType = HttpResponse<ICompanyProfile>;
type EntityArrayResponseType = HttpResponse<ICompanyProfile[]>;

@Injectable({ providedIn: 'root' })
export class CompanyProfileService {
  public resourceUrl = SERVER_API_URL + 'api/company-profiles';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/company-profiles';

  constructor(protected http: HttpClient) {}

  create(companyProfile: ICompanyProfile): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(companyProfile);
    return this.http
      .post<ICompanyProfile>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(companyProfile: ICompanyProfile): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(companyProfile);
    return this.http
      .put<ICompanyProfile>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICompanyProfile>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICompanyProfile[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICompanyProfile[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(companyProfile: ICompanyProfile): ICompanyProfile {
    const copy: ICompanyProfile = Object.assign({}, companyProfile, {
      createdOn: companyProfile.createdOn && companyProfile.createdOn.isValid() ? companyProfile.createdOn.toJSON() : undefined,
      updatedOn: companyProfile.updatedOn && companyProfile.updatedOn.isValid() ? companyProfile.updatedOn.toJSON() : undefined
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
      res.body.forEach((companyProfile: ICompanyProfile) => {
        companyProfile.createdOn = companyProfile.createdOn ? moment(companyProfile.createdOn) : undefined;
        companyProfile.updatedOn = companyProfile.updatedOn ? moment(companyProfile.updatedOn) : undefined;
      });
    }
    return res;
  }
}
