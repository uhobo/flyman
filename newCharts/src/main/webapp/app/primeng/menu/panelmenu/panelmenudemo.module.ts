import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {CommonModule} from '@angular/common';
import { NewChartsSharedModule } from '../../../shared';
import {GrowlModule} from 'primeng/primeng';
import {PanelMenuModule} from 'primeng/components/panelmenu/panelmenu';
import {WizardModule} from 'primeng-extensions/components/wizard/wizard.js';

import {
    PanelMenuDemoComponent,
    panelmenuDemoRoute
} from './';

const primeng_STATES = [
    panelmenuDemoRoute
];

@NgModule({
    imports: [
        NewChartsSharedModule,
        CommonModule,
        BrowserAnimationsModule,
        PanelMenuModule,
        GrowlModule,
        WizardModule,
        RouterModule.forRoot(primeng_STATES, { useHash: true })
    ],
    declarations: [
        PanelMenuDemoComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NewChartsPanelMenuDemoModule {}
