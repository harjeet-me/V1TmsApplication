import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TmsV1ApplicationTestModule } from '../../../test.module';
import { ProductItemComponent } from 'app/entities/product-item/product-item.component';
import { ProductItemService } from 'app/entities/product-item/product-item.service';
import { ProductItem } from 'app/shared/model/product-item.model';

describe('Component Tests', () => {
  describe('ProductItem Management Component', () => {
    let comp: ProductItemComponent;
    let fixture: ComponentFixture<ProductItemComponent>;
    let service: ProductItemService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TmsV1ApplicationTestModule],
        declarations: [ProductItemComponent]
      })
        .overrideTemplate(ProductItemComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductItemComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductItemService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ProductItem(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.productItems && comp.productItems[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
