import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ChartsSharedModule } from 'app/shared';
import { RespondingDataService } from 'app/entities/responding/respondingDataService';

import {
    RespondingComponent,
    RespondingDetailComponent,
    RespondingUpdateComponent,
    RespondingDeletePopupComponent,
    RespondingDeleteDialogComponent,
    respondingRoute,
    respondingPopupRoute
} from './';

const ENTITY_STATES = [...respondingRoute, ...respondingPopupRoute];

@NgModule({
    imports: [ChartsSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        RespondingComponent,
        RespondingDetailComponent,
        RespondingUpdateComponent,
        RespondingDeleteDialogComponent,
        RespondingDeletePopupComponent
    ],
    entryComponents: [RespondingComponent, RespondingUpdateComponent, RespondingDeleteDialogComponent, RespondingDeletePopupComponent ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
    providers: [RespondingDataService]
})
export class ChartsRespondingModule {}
