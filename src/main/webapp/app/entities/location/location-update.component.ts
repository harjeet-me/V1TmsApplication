import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ILocation, Location } from 'app/shared/model/location.model';
import { LocationService } from './location.service';

@Component({
  selector: 'jhi-location-update',
  templateUrl: './location-update.component.html'
})
export class LocationUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    address: [],
    streetAddress: [],
    city: [],
    stateProvince: [],
    country: [],
    postalCode: [],
    latitude: [],
    longitude: [],
    createdOn: [],
    createdBy: [],
    updatedOn: [],
    updatedBy: []
  });

  constructor(protected locationService: LocationService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ location }) => {
      if (!location.id) {
        const today = moment().startOf('day');
        location.createdOn = today;
        location.updatedOn = today;
      }

      this.updateForm(location);
    });
  }

  updateForm(location: ILocation): void {
    this.editForm.patchValue({
      id: location.id,
      address: location.address,
      streetAddress: location.streetAddress,
      city: location.city,
      stateProvince: location.stateProvince,
      country: location.country,
      postalCode: location.postalCode,
      latitude: location.latitude,
      longitude: location.longitude,
      createdOn: location.createdOn ? location.createdOn.format(DATE_TIME_FORMAT) : null,
      createdBy: location.createdBy,
      updatedOn: location.updatedOn ? location.updatedOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: location.updatedBy
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const location = this.createFromForm();
    if (location.id !== undefined) {
      this.subscribeToSaveResponse(this.locationService.update(location));
    } else {
      this.subscribeToSaveResponse(this.locationService.create(location));
    }
  }

  private createFromForm(): ILocation {
    return {
      ...new Location(),
      id: this.editForm.get(['id'])!.value,
      address: this.editForm.get(['address'])!.value,
      streetAddress: this.editForm.get(['streetAddress'])!.value,
      city: this.editForm.get(['city'])!.value,
      stateProvince: this.editForm.get(['stateProvince'])!.value,
      country: this.editForm.get(['country'])!.value,
      postalCode: this.editForm.get(['postalCode'])!.value,
      latitude: this.editForm.get(['latitude'])!.value,
      longitude: this.editForm.get(['longitude'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? moment(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      createdBy: this.editForm.get(['createdBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? moment(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILocation>>): void {
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
}
