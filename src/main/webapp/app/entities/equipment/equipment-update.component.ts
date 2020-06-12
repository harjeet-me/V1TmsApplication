import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IEquipment, Equipment } from 'app/shared/model/equipment.model';
import { EquipmentService } from './equipment.service';
import { IInsurance } from 'app/shared/model/insurance.model';
import { InsuranceService } from 'app/entities/insurance/insurance.service';

@Component({
  selector: 'jhi-equipment-update',
  templateUrl: './equipment-update.component.html'
})
export class EquipmentUpdateComponent implements OnInit {
  isSaving = false;
  insurances: IInsurance[] = [];
  licensePlateExpirationDp: any;
  inspectionStickerExpirationDp: any;

  editForm = this.fb.group({
    id: [],
    enumber: [],
    type: [],
    ownershiptype: [],
    status: [],
    vin: [],
    make: [],
    model: [],
    description: [],
    year: [],
    yearPurchased: [],
    licensePlateNumber: [],
    licensePlateExpiration: [],
    inspectionStickerExpiration: [],
    createdDate: [],
    createdBy: [],
    lastModifiedDate: [],
    lastModifiedBy: [],
    insurance: []
  });

  constructor(
    protected equipmentService: EquipmentService,
    protected insuranceService: InsuranceService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ equipment }) => {
      if (!equipment.id) {
        const today = moment().startOf('day');
        equipment.createdDate = today;
        equipment.lastModifiedDate = today;
      }

      this.updateForm(equipment);

      this.insuranceService
        .query({ filter: 'equipment-is-null' })
        .pipe(
          map((res: HttpResponse<IInsurance[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IInsurance[]) => {
          if (!equipment.insurance || !equipment.insurance.id) {
            this.insurances = resBody;
          } else {
            this.insuranceService
              .find(equipment.insurance.id)
              .pipe(
                map((subRes: HttpResponse<IInsurance>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IInsurance[]) => (this.insurances = concatRes));
          }
        });
    });
  }

  updateForm(equipment: IEquipment): void {
    this.editForm.patchValue({
      id: equipment.id,
      enumber: equipment.enumber,
      type: equipment.type,
      ownershiptype: equipment.ownershiptype,
      status: equipment.status,
      vin: equipment.vin,
      make: equipment.make,
      model: equipment.model,
      description: equipment.description,
      year: equipment.year,
      yearPurchased: equipment.yearPurchased,
      licensePlateNumber: equipment.licensePlateNumber,
      licensePlateExpiration: equipment.licensePlateExpiration,
      inspectionStickerExpiration: equipment.inspectionStickerExpiration,
      createdDate: equipment.createdDate ? equipment.createdDate.format(DATE_TIME_FORMAT) : null,
      createdBy: equipment.createdBy,
      lastModifiedDate: equipment.lastModifiedDate ? equipment.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: equipment.lastModifiedBy,
      insurance: equipment.insurance
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const equipment = this.createFromForm();
    if (equipment.id !== undefined) {
      this.subscribeToSaveResponse(this.equipmentService.update(equipment));
    } else {
      this.subscribeToSaveResponse(this.equipmentService.create(equipment));
    }
  }

  private createFromForm(): IEquipment {
    return {
      ...new Equipment(),
      id: this.editForm.get(['id'])!.value,
      enumber: this.editForm.get(['enumber'])!.value,
      type: this.editForm.get(['type'])!.value,
      ownershiptype: this.editForm.get(['ownershiptype'])!.value,
      status: this.editForm.get(['status'])!.value,
      vin: this.editForm.get(['vin'])!.value,
      make: this.editForm.get(['make'])!.value,
      model: this.editForm.get(['model'])!.value,
      description: this.editForm.get(['description'])!.value,
      year: this.editForm.get(['year'])!.value,
      yearPurchased: this.editForm.get(['yearPurchased'])!.value,
      licensePlateNumber: this.editForm.get(['licensePlateNumber'])!.value,
      licensePlateExpiration: this.editForm.get(['licensePlateExpiration'])!.value,
      inspectionStickerExpiration: this.editForm.get(['inspectionStickerExpiration'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? moment(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      createdBy: this.editForm.get(['createdBy'])!.value,
      lastModifiedDate: this.editForm.get(['lastModifiedDate'])!.value
        ? moment(this.editForm.get(['lastModifiedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      insurance: this.editForm.get(['insurance'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEquipment>>): void {
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
