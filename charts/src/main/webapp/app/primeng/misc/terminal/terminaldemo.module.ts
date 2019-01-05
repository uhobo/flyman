import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {CommonModule} from '@angular/common';
import { ChartsSharedModule } from '../../../shared';
import {GrowlModule} from 'primeng/primeng';
import {TerminalModule} from 'primeng/primeng';
import {WizardModule} from 'primeng-extensions/components/wizard/wizard.js';

import {
    TerminalDemoComponent,
    terminalDemoRoute
} from './';

const primeng_STATES = [
    terminalDemoRoute];

@NgModule({
    imports: [
        ChartsSharedModule,
        CommonModule,
        BrowserAnimationsModule,
        GrowlModule,
        TerminalModule,
        WizardModule,
        RouterModule.forRoot(primeng_STATES, { useHash: true })
    ],
    declarations: [
        TerminalDemoComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ChartsTerminalDemoModule {}
