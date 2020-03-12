import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TmsV1ApplicationSharedModule } from 'app/shared/shared.module';
import { ProductItemComponent } from './product-item.component';
import { ProductItemDetailComponent } from './product-item-detail.component';
import { ProductItemUpdateComponent } from './product-item-update.component';
import { ProductItemDeleteDialogComponent } from './product-item-delete-dialog.component';
import { productItemRoute } from './product-item.route';

@NgModule({
  imports: [TmsV1ApplicationSharedModule, RouterModule.forChild(productItemRoute)],
  declarations: [ProductItemComponent, ProductItemDetailComponent, ProductItemUpdateComponent, ProductItemDeleteDialogComponent],
  entryComponents: [ProductItemDeleteDialogComponent]
})
export class TmsV1ApplicationProductItemModule {}
