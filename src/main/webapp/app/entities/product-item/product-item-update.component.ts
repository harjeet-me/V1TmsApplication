import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IProductItem, ProductItem } from 'app/shared/model/product-item.model';
import { ProductItemService } from './product-item.service';

@Component({
  selector: 'jhi-product-item-update',
  templateUrl: './product-item-update.component.html'
})
export class ProductItemUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    itemName: [],
    description: [],
    defaultQty: [],
    price: [],
    createdDate: [],
    createdBy: [],
    lastModifiedDate: [],
    lastModifiedBy: []
  });

  constructor(protected productItemService: ProductItemService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productItem }) => {
      if (!productItem.id) {
        const today = moment().startOf('day');
        productItem.createdDate = today;
        productItem.lastModifiedDate = today;
      }

      this.updateForm(productItem);
    });
  }

  updateForm(productItem: IProductItem): void {
    this.editForm.patchValue({
      id: productItem.id,
      itemName: productItem.itemName,
      description: productItem.description,
      defaultQty: productItem.defaultQty,
      price: productItem.price,
      createdDate: productItem.createdDate ? productItem.createdDate.format(DATE_TIME_FORMAT) : null,
      createdBy: productItem.createdBy,
      lastModifiedDate: productItem.lastModifiedDate ? productItem.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: productItem.lastModifiedBy
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const productItem = this.createFromForm();
    if (productItem.id !== undefined) {
      this.subscribeToSaveResponse(this.productItemService.update(productItem));
    } else {
      this.subscribeToSaveResponse(this.productItemService.create(productItem));
    }
  }

  private createFromForm(): IProductItem {
    return {
      ...new ProductItem(),
      id: this.editForm.get(['id'])!.value,
      itemName: this.editForm.get(['itemName'])!.value,
      description: this.editForm.get(['description'])!.value,
      defaultQty: this.editForm.get(['defaultQty'])!.value,
      price: this.editForm.get(['price'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? moment(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      createdBy: this.editForm.get(['createdBy'])!.value,
      lastModifiedDate: this.editForm.get(['lastModifiedDate'])!.value
        ? moment(this.editForm.get(['lastModifiedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductItem>>): void {
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
