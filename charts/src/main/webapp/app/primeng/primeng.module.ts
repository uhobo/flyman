
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { ChartsButtonDemoModule } from './buttons/button/buttondemo.module';
import { ChartsSplitbuttonDemoModule } from './buttons/splitbutton/splitbuttondemo.module';

import { ChartsDialogDemoModule } from './overlay/dialog/dialogdemo.module';
import { ChartsConfirmDialogDemoModule } from './overlay/confirmdialog/confirmdialogdemo.module';
import { ChartsLightboxDemoModule } from './overlay/lightbox/lightboxdemo.module';
import { ChartsTooltipDemoModule } from './overlay/tooltip/tooltipdemo.module';
import { ChartsOverlayPanelDemoModule } from './overlay/overlaypanel/overlaypaneldemo.module';
import { ChartsSideBarDemoModule } from './overlay/sidebar/sidebardemo.module';

import { ChartsKeyFilterDemoModule } from './inputs/keyfilter/keyfilterdemo.module';
import { ChartsInputTextDemoModule } from './inputs/inputtext/inputtextdemo.module';
import { ChartsInputTextAreaDemoModule } from './inputs/inputtextarea/inputtextareademo.module';
import { ChartsInputGroupDemoModule } from './inputs/inputgroup/inputgroupdemo.module';
import { ChartsCalendarDemoModule } from './inputs/calendar/calendardemo.module';
import { ChartsCheckboxDemoModule } from './inputs/checkbox/checkboxdemo.module';
import { ChartsChipsDemoModule } from './inputs/chips/chipsdemo.module';
import { ChartsColorPickerDemoModule } from './inputs/colorpicker/colorpickerdemo.module';
import { ChartsInputMaskDemoModule } from './inputs/inputmask/inputmaskdemo.module';
import { ChartsInputSwitchDemoModule } from './inputs/inputswitch/inputswitchdemo.module';
import { ChartsPasswordIndicatorDemoModule } from './inputs/passwordindicator/passwordindicatordemo.module';
import { ChartsAutoCompleteDemoModule } from './inputs/autocomplete/autocompletedemo.module';
import { ChartsSliderDemoModule } from './inputs/slider/sliderdemo.module';
import { ChartsSpinnerDemoModule } from './inputs/spinner/spinnerdemo.module';
import { ChartsRatingDemoModule } from './inputs/rating/ratingdemo.module';
import { ChartsSelectDemoModule } from './inputs/select/selectdemo.module';
import { ChartsSelectButtonDemoModule } from './inputs/selectbutton/selectbuttondemo.module';
import { ChartsListboxDemoModule } from './inputs/listbox/listboxdemo.module';
import { ChartsRadioButtonDemoModule } from './inputs/radiobutton/radiobuttondemo.module';
import { ChartsToggleButtonDemoModule } from './inputs/togglebutton/togglebuttondemo.module';
import { ChartsEditorDemoModule } from './inputs/editor/editordemo.module';

import { ChartsMessagesDemoModule } from './messages/messages/messagesdemo.module';
import { ChartsToastDemoModule } from './messages/toast/toastdemo.module';
import { ChartsGalleriaDemoModule } from './multimedia/galleria/galleriademo.module';

import { ChartsFileUploadDemoModule } from './fileupload/fileupload/fileuploaddemo.module';

import { ChartsAccordionDemoModule } from './panel/accordion/accordiondemo.module';
import { ChartsPanelDemoModule } from './panel/panel/paneldemo.module';
import { ChartsTabViewDemoModule } from './panel/tabview/tabviewdemo.module';
import { ChartsFieldsetDemoModule } from './panel/fieldset/fieldsetdemo.module';
import { ChartsToolbarDemoModule } from './panel/toolbar/toolbardemo.module';
import { ChartsScrollPanelDemoModule } from './panel/scrollpanel/scrollpaneldemo.module';
import { ChartsCardDemoModule } from './panel/card/carddemo.module';
import { ChartsFlexGridDemoModule } from './panel/flexgrid/flexgriddemo.module';

import { ChartsTableDemoModule } from './data/table/tabledemo.module';
import { ChartsVirtualScrollerDemoModule } from './data/virtualscroller/virtualscrollerdemo.module';
import { ChartsPickListDemoModule } from './data/picklist/picklistdemo.module';
import { ChartsOrderListDemoModule } from './data/orderlist/orderlistdemo.module';
import { ChartsFullCalendarDemoModule } from './data/fullcalendar/fullcalendardemo.module';
import { ChartsTreeDemoModule } from './data/tree/treedemo.module';
import { ChartsTreeTableDemoModule } from './data/treetable/treetabledemo.module';
import { ChartsPaginatorDemoModule } from './data/paginator/paginatordemo.module';
import { ChartsGmapDemoModule } from './data/gmap/gmapdemo.module';
import { ChartsOrgChartDemoModule } from './data/orgchart/orgchartdemo.module';
import { ChartsCarouselDemoModule } from './data/carousel/carouseldemo.module';
import { ChartsDataViewDemoModule } from './data/dataview/dataviewdemo.module';

import { ChartsBarchartDemoModule } from './charts/barchart/barchartdemo.module';
import { ChartsDoughnutchartDemoModule } from './charts/doughnutchart/doughnutchartdemo.module';
import { ChartsLinechartDemoModule } from './charts/linechart/linechartdemo.module';
import { ChartsPiechartDemoModule } from './charts/piechart/piechartdemo.module';
import { ChartsPolarareachartDemoModule } from './charts/polarareachart/polarareachartdemo.module';
import { ChartsRadarchartDemoModule } from './charts/radarchart/radarchartdemo.module';

import { ChartsDragDropDemoModule } from './dragdrop/dragdrop/dragdropdemo.module';

import { ChartsMenuDemoModule } from './menu/menu/menudemo.module';
import { ChartsContextMenuDemoModule } from './menu/contextmenu/contextmenudemo.module';
import { ChartsPanelMenuDemoModule } from './menu/panelmenu/panelmenudemo.module';
import { ChartsStepsDemoModule } from './menu/steps/stepsdemo.module';
import { ChartsTieredMenuDemoModule } from './menu/tieredmenu/tieredmenudemo.module';
import { ChartsBreadcrumbDemoModule } from './menu/breadcrumb/breadcrumbdemo.module';
import { ChartsMegaMenuDemoModule } from './menu/megamenu/megamenudemo.module';
import { ChartsMenuBarDemoModule } from './menu/menubar/menubardemo.module';
import { ChartsSlideMenuDemoModule } from './menu/slidemenu/slidemenudemo.module';
import { ChartsTabMenuDemoModule } from './menu/tabmenu/tabmenudemo.module';

import { ChartsBlockUIDemoModule } from './misc/blockui/blockuidemo.module';
import { ChartsCaptchaDemoModule } from './misc/captcha/captchademo.module';
import { ChartsDeferDemoModule } from './misc/defer/deferdemo.module';
import { ChartsInplaceDemoModule } from './misc/inplace/inplacedemo.module';
import { ChartsProgressBarDemoModule } from './misc/progressbar/progressbardemo.module';
import { ChartsRTLDemoModule } from './misc/rtl/rtldemo.module';
import { ChartsTerminalDemoModule } from './misc/terminal/terminaldemo.module';
import { ChartsValidationDemoModule } from './misc/validation/validationdemo.module';
import { ChartsProgressSpinnerDemoModule } from './misc/progressspinner/progressspinnerdemo.module';

@NgModule({
    imports: [

        ChartsMenuDemoModule,
        ChartsContextMenuDemoModule,
        ChartsPanelMenuDemoModule,
        ChartsStepsDemoModule,
        ChartsTieredMenuDemoModule,
        ChartsBreadcrumbDemoModule,
        ChartsMegaMenuDemoModule,
        ChartsMenuBarDemoModule,
        ChartsSlideMenuDemoModule,
        ChartsTabMenuDemoModule,

        ChartsBlockUIDemoModule,
        ChartsCaptchaDemoModule,
        ChartsDeferDemoModule,
        ChartsInplaceDemoModule,
        ChartsProgressBarDemoModule,
        ChartsInputMaskDemoModule,
        ChartsRTLDemoModule,
        ChartsTerminalDemoModule,
        ChartsValidationDemoModule,

        ChartsButtonDemoModule,
        ChartsSplitbuttonDemoModule,

        ChartsInputTextDemoModule,
        ChartsInputTextAreaDemoModule,
        ChartsInputGroupDemoModule,
        ChartsCalendarDemoModule,
        ChartsChipsDemoModule,
        ChartsInputMaskDemoModule,
        ChartsInputSwitchDemoModule,
        ChartsPasswordIndicatorDemoModule,
        ChartsAutoCompleteDemoModule,
        ChartsSliderDemoModule,
        ChartsSpinnerDemoModule,
        ChartsRatingDemoModule,
        ChartsSelectDemoModule,
        ChartsSelectButtonDemoModule,
        ChartsListboxDemoModule,
        ChartsRadioButtonDemoModule,
        ChartsToggleButtonDemoModule,
        ChartsEditorDemoModule,
        ChartsColorPickerDemoModule,
        ChartsCheckboxDemoModule,
        ChartsKeyFilterDemoModule,

        ChartsMessagesDemoModule,
        ChartsToastDemoModule,
        ChartsGalleriaDemoModule,

        ChartsFileUploadDemoModule,

        ChartsAccordionDemoModule,
        ChartsPanelDemoModule,
        ChartsTabViewDemoModule,
        ChartsFieldsetDemoModule,
        ChartsToolbarDemoModule,
        ChartsScrollPanelDemoModule,
        ChartsCardDemoModule,
        ChartsFlexGridDemoModule,

        ChartsBarchartDemoModule,
        ChartsDoughnutchartDemoModule,
        ChartsLinechartDemoModule,
        ChartsPiechartDemoModule,
        ChartsPolarareachartDemoModule,
        ChartsRadarchartDemoModule,

        ChartsDragDropDemoModule,

        ChartsDialogDemoModule,
        ChartsConfirmDialogDemoModule,
        ChartsLightboxDemoModule,
        ChartsTooltipDemoModule,
        ChartsOverlayPanelDemoModule,
        ChartsSideBarDemoModule,

        ChartsTableDemoModule,
        ChartsDataViewDemoModule,
        ChartsVirtualScrollerDemoModule,
        ChartsFullCalendarDemoModule,
        ChartsOrderListDemoModule,
        ChartsPickListDemoModule,
        ChartsTreeDemoModule,
        ChartsTreeTableDemoModule,
        ChartsPaginatorDemoModule,
        ChartsOrgChartDemoModule,
        ChartsGmapDemoModule,
        ChartsCarouselDemoModule,
        ChartsProgressSpinnerDemoModule

    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ChartsprimengModule {}
