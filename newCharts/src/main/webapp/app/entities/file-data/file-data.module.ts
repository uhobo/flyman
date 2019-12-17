import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from '../../core';
import { FileUploadModule } from 'primeng/components/fileupload/fileupload';
import { TableModule } from 'primeng/table'
import {BrowserModule} from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NewChartsSharedModule } from '../../shared';
import {RadioButtonModule} from 'primeng/radiobutton';
import {CheckboxModule} from 'primeng/checkbox';
import {DropdownModule} from 'primeng/dropdown';
import {ChartModule} from 'primeng/chart';
import {SidebarModule} from 'primeng/sidebar';
import {TreeModule} from 'primeng/tree';
import {TreeNode} from 'primeng/api';

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

const ENTITY_STATES = [...fileDataRoute, ...fileDataPopupRoute];

@NgModule({
  imports: [NewChartsSharedModule, FileUploadModule, TableModule, TreeModule, RadioButtonModule, CheckboxModule, DropdownModule, ChartModule, SidebarModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    FileDataComponent,
    FileDataDetailComponent,
    FileDataUpdateComponent,
    FileDataChartComponent,
    FileDataDeleteDialogComponent,
    FileDataDeletePopupComponent
  ],
  entryComponents: [FileDataComponent, FileDataUpdateComponent, FileDataChartComponent,FileDataDeleteDialogComponent, FileDataDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NewChartsFileDataModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
