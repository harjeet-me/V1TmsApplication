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
    createdOn: [],
    createdBy: [],
    updatedOn: [],
    updatedBy: [],
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
        equipment.createdOn = today;
        equipment.updatedOn = today;
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
      createdOn: equipment.createdOn ? equipment.createdOn.format(DATE_TIME_FORMAT) : null,
      createdBy: equipment.createdBy,
      updatedOn: equipment.updatedOn ? equipment.updatedOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: equipment.updatedBy,
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
      createdOn: this.editForm.get(['createdOn'])!.value ? moment(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      createdBy: this.editForm.get(['createdBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? moment(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
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
