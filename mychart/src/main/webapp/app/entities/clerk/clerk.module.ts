import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MychartSharedModule } from 'app/shared/shared.module';
import { ClerkComponent } from './clerk.component';
import { ClerkDetailComponent } from './clerk-detail.component';
import { ClerkUpdateComponent } from './clerk-update.component';
import { ClerkDeleteDialogComponent } from './clerk-delete-dialog.component';
import { clerkRoute } from './clerk.route';

@NgModule({
  imports: [MychartSharedModule, RouterModule.forChild(clerkRoute)],
  declarations: [ClerkComponent, ClerkDetailComponent, ClerkUpdateComponent, ClerkDeleteDialogComponent],
  entryComponents: [ClerkDeleteDialogComponent]
})
export class MychartClerkModule {}
