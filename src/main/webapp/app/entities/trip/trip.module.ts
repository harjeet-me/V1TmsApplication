import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TmsV1ApplicationSharedModule } from 'app/shared/shared.module';
import { TripComponent } from './trip.component';
import { TripDetailComponent } from './trip-detail.component';
import { TripUpdateComponent } from './trip-update.component';
import { TripDeleteDialogComponent } from './trip-delete-dialog.component';
import { tripRoute } from './trip.route';
import { TripWizardComponent } from './trip-wizard/trip-wizard.component';

@NgModule({
  imports: [TmsV1ApplicationSharedModule, RouterModule.forChild(tripRoute)],
  declarations: [TripComponent, TripDetailComponent, TripUpdateComponent, TripDeleteDialogComponent, TripWizardComponent],
  entryComponents: [TripDeleteDialogComponent]
})
export class TmsV1ApplicationTripModule {}
