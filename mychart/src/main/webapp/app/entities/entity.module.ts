import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'file-data',
        loadChildren: () => import('./file-data/file-data.module').then(m => m.MychartFileDataModule)
      },
      {
        path: 'clerk',
        loadChildren: () => import('./clerk/clerk.module').then(m => m.MychartClerkModule)
      },
      {
        path: 'survey',
        loadChildren: () => import('./survey/survey.module').then(m => m.MychartSurveyModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class MychartEntityModule {}
