import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { NewChartsSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [NewChartsSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [NewChartsSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NewChartsSharedModule {
  static forRoot() {
    return {
      ngModule: NewChartsSharedModule
    };
  }
}
