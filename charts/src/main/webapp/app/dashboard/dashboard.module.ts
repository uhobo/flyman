import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ChartsBarchartModule } from './barchart/barchart.module';
import { ChartsDoughnutchartModule } from './doughnutchart/doughnutchart.module';
import { ChartsLinechartModule } from './linechart/linechart.module';
import { ChartsPiechartModule } from './piechart/piechart.module';
import { ChartsPolarareachartModule } from './polarareachart/polarareachart.module';
import { ChartsRadarchartModule } from './radarchart/radarchart.module';

@NgModule({
    imports: [
        ChartsBarchartModule,
        ChartsDoughnutchartModule,
        ChartsLinechartModule,
        ChartsPiechartModule,
        ChartsPolarareachartModule,
        ChartsRadarchartModule,
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ChartsDashboardModule {}
