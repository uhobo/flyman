
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { NewChartsButtonDemoModule } from './buttons/button/buttondemo.module';
import { NewChartsSplitbuttonDemoModule } from './buttons/splitbutton/splitbuttondemo.module';

import { NewChartsDialogDemoModule } from './overlay/dialog/dialogdemo.module';
import { NewChartsConfirmDialogDemoModule } from './overlay/confirmdialog/confirmdialogdemo.module';
import { NewChartsLightboxDemoModule } from './overlay/lightbox/lightboxdemo.module';
import { NewChartsTooltipDemoModule } from './overlay/tooltip/tooltipdemo.module';
import { NewChartsOverlayPanelDemoModule } from './overlay/overlaypanel/overlaypaneldemo.module';
import { NewChartsSideBarDemoModule } from './overlay/sidebar/sidebardemo.module';

import { NewChartsKeyFilterDemoModule } from './inputs/keyfilter/keyfilterdemo.module';
import { NewChartsInputTextDemoModule } from './inputs/inputtext/inputtextdemo.module';
import { NewChartsInputTextAreaDemoModule } from './inputs/inputtextarea/inputtextareademo.module';
import { NewChartsInputGroupDemoModule } from './inputs/inputgroup/inputgroupdemo.module';
import { NewChartsCalendarDemoModule } from './inputs/calendar/calendardemo.module';
import { NewChartsCheckboxDemoModule } from './inputs/checkbox/checkboxdemo.module';
import { NewChartsChipsDemoModule } from './inputs/chips/chipsdemo.module';
import { NewChartsColorPickerDemoModule } from './inputs/colorpicker/colorpickerdemo.module';
import { NewChartsInputMaskDemoModule } from './inputs/inputmask/inputmaskdemo.module';
import { NewChartsInputSwitchDemoModule } from './inputs/inputswitch/inputswitchdemo.module';
import { NewChartsPasswordIndicatorDemoModule } from './inputs/passwordindicator/passwordindicatordemo.module';
import { NewChartsAutoCompleteDemoModule } from './inputs/autocomplete/autocompletedemo.module';
import { NewChartsSliderDemoModule } from './inputs/slider/sliderdemo.module';
import { NewChartsSpinnerDemoModule } from './inputs/spinner/spinnerdemo.module';
import { NewChartsRatingDemoModule } from './inputs/rating/ratingdemo.module';
import { NewChartsSelectDemoModule } from './inputs/select/selectdemo.module';
import { NewChartsSelectButtonDemoModule } from './inputs/selectbutton/selectbuttondemo.module';
import { NewChartsListboxDemoModule } from './inputs/listbox/listboxdemo.module';
import { NewChartsRadioButtonDemoModule } from './inputs/radiobutton/radiobuttondemo.module';
import { NewChartsToggleButtonDemoModule } from './inputs/togglebutton/togglebuttondemo.module';
import { NewChartsEditorDemoModule } from './inputs/editor/editordemo.module';

import { NewChartsMessagesDemoModule } from './messages/messages/messagesdemo.module';
import { NewChartsToastDemoModule } from './messages/toast/toastdemo.module';
import { NewChartsGalleriaDemoModule } from './multimedia/galleria/galleriademo.module';

import { NewChartsFileUploadDemoModule } from './fileupload/fileupload/fileuploaddemo.module';

import { NewChartsAccordionDemoModule } from './panel/accordion/accordiondemo.module';
import { NewChartsPanelDemoModule } from './panel/panel/paneldemo.module';
import { NewChartsTabViewDemoModule } from './panel/tabview/tabviewdemo.module';
import { NewChartsFieldsetDemoModule } from './panel/fieldset/fieldsetdemo.module';
import { NewChartsToolbarDemoModule } from './panel/toolbar/toolbardemo.module';
import { NewChartsScrollPanelDemoModule } from './panel/scrollpanel/scrollpaneldemo.module';
import { NewChartsCardDemoModule } from './panel/card/carddemo.module';
import { NewChartsFlexGridDemoModule } from './panel/flexgrid/flexgriddemo.module';

import { NewChartsTableDemoModule } from './data/table/tabledemo.module';
import { NewChartsVirtualScrollerDemoModule } from './data/virtualscroller/virtualscrollerdemo.module';
import { NewChartsPickListDemoModule } from './data/picklist/picklistdemo.module';
import { NewChartsOrderListDemoModule } from './data/orderlist/orderlistdemo.module';
import { NewChartsFullCalendarDemoModule } from './data/fullcalendar/fullcalendardemo.module';
import { NewChartsTreeDemoModule } from './data/tree/treedemo.module';
import { NewChartsTreeTableDemoModule } from './data/treetable/treetabledemo.module';
import { NewChartsPaginatorDemoModule } from './data/paginator/paginatordemo.module';
import { NewChartsGmapDemoModule } from './data/gmap/gmapdemo.module';
import { NewChartsOrgChartDemoModule } from './data/orgchart/orgchartdemo.module';
import { NewChartsCarouselDemoModule } from './data/carousel/carouseldemo.module';
import { NewChartsDataViewDemoModule } from './data/dataview/dataviewdemo.module';

import { NewChartsBarchartDemoModule } from './charts/barchart/barchartdemo.module';
import { NewChartsDoughnutchartDemoModule } from './charts/doughnutchart/doughnutchartdemo.module';
import { NewChartsLinechartDemoModule } from './charts/linechart/linechartdemo.module';
import { NewChartsPiechartDemoModule } from './charts/piechart/piechartdemo.module';
import { NewChartsPolarareachartDemoModule } from './charts/polarareachart/polarareachartdemo.module';
import { NewChartsRadarchartDemoModule } from './charts/radarchart/radarchartdemo.module';

import { NewChartsDragDropDemoModule } from './dragdrop/dragdrop/dragdropdemo.module';

import { NewChartsMenuDemoModule } from './menu/menu/menudemo.module';
import { NewChartsContextMenuDemoModule } from './menu/contextmenu/contextmenudemo.module';
import { NewChartsPanelMenuDemoModule } from './menu/panelmenu/panelmenudemo.module';
import { NewChartsStepsDemoModule } from './menu/steps/stepsdemo.module';
import { NewChartsTieredMenuDemoModule } from './menu/tieredmenu/tieredmenudemo.module';
import { NewChartsBreadcrumbDemoModule } from './menu/breadcrumb/breadcrumbdemo.module';
import { NewChartsMegaMenuDemoModule } from './menu/megamenu/megamenudemo.module';
import { NewChartsMenuBarDemoModule } from './menu/menubar/menubardemo.module';
import { NewChartsSlideMenuDemoModule } from './menu/slidemenu/slidemenudemo.module';
import { NewChartsTabMenuDemoModule } from './menu/tabmenu/tabmenudemo.module';

import { NewChartsBlockUIDemoModule } from './misc/blockui/blockuidemo.module';
import { NewChartsCaptchaDemoModule } from './misc/captcha/captchademo.module';
import { NewChartsDeferDemoModule } from './misc/defer/deferdemo.module';
import { NewChartsInplaceDemoModule } from './misc/inplace/inplacedemo.module';
import { NewChartsProgressBarDemoModule } from './misc/progressbar/progressbardemo.module';
import { NewChartsRTLDemoModule } from './misc/rtl/rtldemo.module';
import { NewChartsTerminalDemoModule } from './misc/terminal/terminaldemo.module';
import { NewChartsValidationDemoModule } from './misc/validation/validationdemo.module';
import { NewChartsProgressSpinnerDemoModule } from './misc/progressspinner/progressspinnerdemo.module';

@NgModule({
    imports: [

        NewChartsMenuDemoModule,
        NewChartsContextMenuDemoModule,
        NewChartsPanelMenuDemoModule,
        NewChartsStepsDemoModule,
        NewChartsTieredMenuDemoModule,
        NewChartsBreadcrumbDemoModule,
        NewChartsMegaMenuDemoModule,
        NewChartsMenuBarDemoModule,
        NewChartsSlideMenuDemoModule,
        NewChartsTabMenuDemoModule,

        NewChartsBlockUIDemoModule,
        NewChartsCaptchaDemoModule,
        NewChartsDeferDemoModule,
        NewChartsInplaceDemoModule,
        NewChartsProgressBarDemoModule,
        NewChartsInputMaskDemoModule,
        NewChartsRTLDemoModule,
        NewChartsTerminalDemoModule,
        NewChartsValidationDemoModule,

        NewChartsButtonDemoModule,
        NewChartsSplitbuttonDemoModule,

        NewChartsInputTextDemoModule,
        NewChartsInputTextAreaDemoModule,
        NewChartsInputGroupDemoModule,
        NewChartsCalendarDemoModule,
        NewChartsChipsDemoModule,
        NewChartsInputMaskDemoModule,
        NewChartsInputSwitchDemoModule,
        NewChartsPasswordIndicatorDemoModule,
        NewChartsAutoCompleteDemoModule,
        NewChartsSliderDemoModule,
        NewChartsSpinnerDemoModule,
        NewChartsRatingDemoModule,
        NewChartsSelectDemoModule,
        NewChartsSelectButtonDemoModule,
        NewChartsListboxDemoModule,
        NewChartsRadioButtonDemoModule,
        NewChartsToggleButtonDemoModule,
        NewChartsEditorDemoModule,
        NewChartsColorPickerDemoModule,
        NewChartsCheckboxDemoModule,
        NewChartsKeyFilterDemoModule,

        NewChartsMessagesDemoModule,
        NewChartsToastDemoModule,
        NewChartsGalleriaDemoModule,

        NewChartsFileUploadDemoModule,

        NewChartsAccordionDemoModule,
        NewChartsPanelDemoModule,
        NewChartsTabViewDemoModule,
        NewChartsFieldsetDemoModule,
        NewChartsToolbarDemoModule,
        NewChartsScrollPanelDemoModule,
        NewChartsCardDemoModule,
        NewChartsFlexGridDemoModule,

        NewChartsBarchartDemoModule,
        NewChartsDoughnutchartDemoModule,
        NewChartsLinechartDemoModule,
        NewChartsPiechartDemoModule,
        NewChartsPolarareachartDemoModule,
        NewChartsRadarchartDemoModule,

        NewChartsDragDropDemoModule,

        NewChartsDialogDemoModule,
        NewChartsConfirmDialogDemoModule,
        NewChartsLightboxDemoModule,
        NewChartsTooltipDemoModule,
        NewChartsOverlayPanelDemoModule,
        NewChartsSideBarDemoModule,

        NewChartsTableDemoModule,
        NewChartsDataViewDemoModule,
        NewChartsVirtualScrollerDemoModule,
        NewChartsFullCalendarDemoModule,
        NewChartsOrderListDemoModule,
        NewChartsPickListDemoModule,
        NewChartsTreeDemoModule,
        NewChartsTreeTableDemoModule,
        NewChartsPaginatorDemoModule,
        NewChartsOrgChartDemoModule,
        NewChartsGmapDemoModule,
        NewChartsCarouselDemoModule,
        NewChartsProgressSpinnerDemoModule

    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NewChartsprimengModule {}
