import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NewChartsSharedModule } from '../../../shared';
import { ChartModule } from 'primeng/primeng';

import {
    PiechartDemoComponent,
    piechartDemoRoute
} from '../../charts/piechart';

const primeng_STATES = [
    piechartDemoRoute
];

@NgModule({
    imports: [
        NewChartsSharedModule,
        ChartModule,
        RouterModule.forRoot(primeng_STATES, { useHash: true })
    ],
    declarations: [
        PiechartDemoComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NewChartsPiechartDemoModule {}
