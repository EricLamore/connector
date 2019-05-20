import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ConnectorSharedLibsModule, ConnectorSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [ConnectorSharedLibsModule, ConnectorSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [ConnectorSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ConnectorSharedModule {
  static forRoot() {
    return {
      ngModule: ConnectorSharedModule
    };
  }
}
