import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ChartsSharedModule } from 'app/shared';
import {
    AskedComponent,
    AskedDetailComponent,
    AskedUpdateComponent,
    AskedDeletePopupComponent,
    AskedDeleteDialogComponent,
    askedRoute,
    askedPopupRoute
} from './';

const ENTITY_STATES = [...askedRoute, ...askedPopupRoute];

@NgModule({
    imports: [ChartsSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [AskedComponent, AskedDetailComponent, AskedUpdateComponent, AskedDeleteDialogComponent, AskedDeletePopupComponent],
    entryComponents: [AskedComponent, AskedUpdateComponent, AskedDeleteDialogComponent, AskedDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ChartsAskedModule {}
