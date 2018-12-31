import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ChartsSharedModule } from 'app/shared';
import {
    AttributeComponent,
    AttributeDetailComponent,
    AttributeUpdateComponent,
    AttributeDeletePopupComponent,
    AttributeDeleteDialogComponent,
    attributeRoute,
    attributePopupRoute
} from './';

const ENTITY_STATES = [...attributeRoute, ...attributePopupRoute];

@NgModule({
    imports: [ChartsSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AttributeComponent,
        AttributeDetailComponent,
        AttributeUpdateComponent,
        AttributeDeleteDialogComponent,
        AttributeDeletePopupComponent
    ],
    entryComponents: [AttributeComponent, AttributeUpdateComponent, AttributeDeleteDialogComponent, AttributeDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ChartsAttributeModule {}
