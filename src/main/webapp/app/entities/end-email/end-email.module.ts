import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ConnectorSharedModule } from 'app/shared';
import {
  EndEmailComponent,
  EndEmailDetailComponent,
  EndEmailUpdateComponent,
  EndEmailDeletePopupComponent,
  EndEmailDeleteDialogComponent,
  endEmailRoute,
  endEmailPopupRoute
} from './';

const ENTITY_STATES = [...endEmailRoute, ...endEmailPopupRoute];

@NgModule({
  imports: [ConnectorSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    EndEmailComponent,
    EndEmailDetailComponent,
    EndEmailUpdateComponent,
    EndEmailDeleteDialogComponent,
    EndEmailDeletePopupComponent
  ],
  entryComponents: [EndEmailComponent, EndEmailUpdateComponent, EndEmailDeleteDialogComponent, EndEmailDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ConnectorEndEmailModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
