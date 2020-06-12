import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ICarrier, Carrier } from 'app/shared/model/carrier.model';
import { CarrierService } from './carrier.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IInsurance } from 'app/shared/model/insurance.model';
import { InsuranceService } from 'app/entities/insurance/insurance.service';

@Component({
  selector: 'jhi-carrier-update',
  templateUrl: './carrier-update.component.html'
})
export class CarrierUpdateComponent implements OnInit {
  isSaving = false;
  operinsurances: IInsurance[] = [];
  customerSinceDp: any;

  editForm = this.fb.group({
    id: [],
    company: [],
    firstName: [],
    lastName: [],
    contactDesignation: [],
    email: [],
    phoneNumber: [],
    phoneNumberExtention: [],
    preffredContactType: [],
    website: [],
    alternateContactPerson: [],
    alternateContactNumber: [],
    alternatePhoneNumberExtention: [],
    alternateContactEmail: [],
    preferredContactTime: [],
    fax: [],
    address: [],
    streetAddress: [],
    city: [],
    stateProvince: [],
    country: [],
    postalCode: [],
    dot: [],
    mc: [],
    taxId: [],
    companyLogo: [],
    companyLogoContentType: [],
    customerSince: [],
    notes: [],
    contract: [],
    contractContentType: [],
    status: [],
    preffredCurrency: [],
    payterms: [],
    timeZone: [],
    createdDate: [],
    createdBy: [],
    lastModifiedDate: [],
    lastModifiedBy: [],
    operInsurance: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected carrierService: CarrierService,
    protected insuranceService: InsuranceService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ carrier }) => {
      if (!carrier.id) {
        const today = moment().startOf('day');
        carrier.timeZone = today;
        carrier.createdDate = today;
        carrier.lastModifiedDate = today;
      }

      this.updateForm(carrier);

      this.insuranceService
        .query({ filter: 'carrier-is-null' })
        .pipe(
          map((res: HttpResponse<IInsurance[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IInsurance[]) => {
          if (!carrier.operInsurance || !carrier.operInsurance.id) {
            this.operinsurances = resBody;
          } else {
            this.insuranceService
              .find(carrier.operInsurance.id)
              .pipe(
                map((subRes: HttpResponse<IInsurance>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IInsurance[]) => (this.operinsurances = concatRes));
          }
        });
    });
  }

  updateForm(carrier: ICarrier): void {
    this.editForm.patchValue({
      id: carrier.id,
      company: carrier.company,
      firstName: carrier.firstName,
      lastName: carrier.lastName,
      contactDesignation: carrier.contactDesignation,
      email: carrier.email,
      phoneNumber: carrier.phoneNumber,
      phoneNumberExtention: carrier.phoneNumberExtention,
      preffredContactType: carrier.preffredContactType,
      website: carrier.website,
      alternateContactPerson: carrier.alternateContactPerson,
      alternateContactNumber: carrier.alternateContactNumber,
      alternatePhoneNumberExtention: carrier.alternatePhoneNumberExtention,
      alternateContactEmail: carrier.alternateContactEmail,
      preferredContactTime: carrier.preferredContactTime,
      fax: carrier.fax,
      address: carrier.address,
      streetAddress: carrier.streetAddress,
      city: carrier.city,
      stateProvince: carrier.stateProvince,
      country: carrier.country,
      postalCode: carrier.postalCode,
      dot: carrier.dot,
      mc: carrier.mc,
      taxId: carrier.taxId,
      companyLogo: carrier.companyLogo,
      companyLogoContentType: carrier.companyLogoContentType,
      customerSince: carrier.customerSince,
      notes: carrier.notes,
      contract: carrier.contract,
      contractContentType: carrier.contractContentType,
      status: carrier.status,
      preffredCurrency: carrier.preffredCurrency,
      payterms: carrier.payterms,
      timeZone: carrier.timeZone ? carrier.timeZone.format(DATE_TIME_FORMAT) : null,
      createdDate: carrier.createdDate ? carrier.createdDate.format(DATE_TIME_FORMAT) : null,
      createdBy: carrier.createdBy,
      lastModifiedDate: carrier.lastModifiedDate ? carrier.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: carrier.lastModifiedBy,
      operInsurance: carrier.operInsurance
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('tmsV1ApplicationApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const carrier = this.createFromForm();
    if (carrier.id !== undefined) {
      this.subscribeToSaveResponse(this.carrierService.update(carrier));
    } else {
      this.subscribeToSaveResponse(this.carrierService.create(carrier));
    }
  }

  private createFromForm(): ICarrier {
    return {
      ...new Carrier(),
      id: this.editForm.get(['id'])!.value,
      company: this.editForm.get(['company'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      contactDesignation: this.editForm.get(['contactDesignation'])!.value,
      email: this.editForm.get(['email'])!.value,
      phoneNumber: this.editForm.get(['phoneNumber'])!.value,
      phoneNumberExtention: this.editForm.get(['phoneNumberExtention'])!.value,
      preffredContactType: this.editForm.get(['preffredContactType'])!.value,
      website: this.editForm.get(['website'])!.value,
      alternateContactPerson: this.editForm.get(['alternateContactPerson'])!.value,
      alternateContactNumber: this.editForm.get(['alternateContactNumber'])!.value,
      alternatePhoneNumberExtention: this.editForm.get(['alternatePhoneNumberExtention'])!.value,
      alternateContactEmail: this.editForm.get(['alternateContactEmail'])!.value,
      preferredContactTime: this.editForm.get(['preferredContactTime'])!.value,
      fax: this.editForm.get(['fax'])!.value,
      address: this.editForm.get(['address'])!.value,
      streetAddress: this.editForm.get(['streetAddress'])!.value,
      city: this.editForm.get(['city'])!.value,
      stateProvince: this.editForm.get(['stateProvince'])!.value,
      country: this.editForm.get(['country'])!.value,
      postalCode: this.editForm.get(['postalCode'])!.value,
      dot: this.editForm.get(['dot'])!.value,
      mc: this.editForm.get(['mc'])!.value,
      taxId: this.editForm.get(['taxId'])!.value,
      companyLogoContentType: this.editForm.get(['companyLogoContentType'])!.value,
      companyLogo: this.editForm.get(['companyLogo'])!.value,
      customerSince: this.editForm.get(['customerSince'])!.value,
      notes: this.editForm.get(['notes'])!.value,
      contractContentType: this.editForm.get(['contractContentType'])!.value,
      contract: this.editForm.get(['contract'])!.value,
      status: this.editForm.get(['status'])!.value,
      preffredCurrency: this.editForm.get(['preffredCurrency'])!.value,
      payterms: this.editForm.get(['payterms'])!.value,
      timeZone: this.editForm.get(['timeZone'])!.value ? moment(this.editForm.get(['timeZone'])!.value, DATE_TIME_FORMAT) : undefined,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? moment(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      createdBy: this.editForm.get(['createdBy'])!.value,
      lastModifiedDate: this.editForm.get(['lastModifiedDate'])!.value
        ? moment(this.editForm.get(['lastModifiedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      operInsurance: this.editForm.get(['operInsurance'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICarrier>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IInsurance): any {
    return item.id;
  }
}
