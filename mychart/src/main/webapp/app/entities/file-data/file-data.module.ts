import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { FileUploadModule } from 'primeng/components/fileupload/fileupload';
import { TableModule } from 'primeng/table'
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {RadioButtonModule} from 'primeng/radiobutton';
import {CheckboxModule} from 'primeng/checkbox';
import {DropdownModule} from 'primeng/dropdown';
import {ChartModule} from 'primeng/chart';
import {SidebarModule} from 'primeng/sidebar';
import {TreeModule} from 'primeng/tree';
import {DynamicDialogModule} from 'primeng/dynamicdialog';

import {
  FileDataComponent,
  FileDataDetailComponent,
  FileDataUpdateComponent,
  FileDataChartComponent,
  FileDataDeletePopupComponent,
  FileDataDeleteDialogComponent,
  fileDataRoute,
  fileDataPopupRoute
} from './';
import { FileDataDynamicDialogComponent } from './file-data-dynamic-dialog.component';
import { MychartSharedModule } from 'app/shared/shared.module';
import { LanguageHelper } from 'app/core/language/language.helper';
import { InfoDataDialogComponent } from './info-data-dialog.component';

const ENTITY_STATES = [...fileDataRoute, ...fileDataPopupRoute];

@NgModule({
  imports: [MychartSharedModule, FileUploadModule, TableModule, TreeModule, RadioButtonModule, DynamicDialogModule, CheckboxModule, DropdownModule, ChartModule, SidebarModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    FileDataComponent,
    FileDataDetailComponent,
    FileDataUpdateComponent,
    FileDataChartComponent,
    FileDataDeleteDialogComponent,
    FileDataDeletePopupComponent,
    FileDataDynamicDialogComponent,
    InfoDataDialogComponent
  ],
  entryComponents: [FileDataComponent, FileDataUpdateComponent, FileDataChartComponent,FileDataDeleteDialogComponent, FileDataDeletePopupComponent, FileDataDynamicDialogComponent, InfoDataDialogComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MychartFileDataModule {
  // constructor(private languageService: JhiLanguageService, private languageHelper: LanguageHelper) {
  //   this.languageHelper.language.subscribe((languageKey: string) => {
  //     if (languageKey !== undefined) {
  //       this.languageService.changeLanguage(languageKey);
  //     }
  //   });
  // }
}
