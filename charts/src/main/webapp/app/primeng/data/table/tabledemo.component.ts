import { Component, OnInit } from '@angular/core';
import { JhiLanguageService } from 'ng-jhipster';
import {Message, SelectItem, MenuItem} from 'primeng/components/common/api';
import {LazyLoadEvent} from 'primeng/components/common/api';
import {BrowserService} from './service/browser.service';
import Browser from './service/browser';
import MyBrowser from './service/mybrowser';

@Component({
    selector: 'jhi-table',
    templateUrl: './tabledemo.component.html',
    styles: [`
        .new-version {
            background-color: #1CA979 !important;
            color: #ffffff !important;
        }
        .ui-grid-row div {
            padding: 4px 10px
        }

        .ui-grid-row div label {
            font-weight: bold;
        }

        /* Column Priorities */
        @media only all {
            th.ui-p-6,
            td.ui-p-6,
            th.ui-p-5,
            td.ui-p-5,
            th.ui-p-4,
            td.ui-p-4,
            th.ui-p-3,
            td.ui-p-3,
            th.ui-p-2,
            td.ui-p-2,
            th.ui-p-1,
            td.ui-p-1 {
                display: none;
            }
        }

        /* Show priority 1 at 320px (20em x 16px) */
        @media screen and (min-width: 20em) {
            th.ui-p-1,
            td.ui-p-1 {
                display: table-cell;
            }
        }

        /* Show priority 2 at 480px (30em x 16px) */
        @media screen and (min-width: 30em) {
            th.ui-p-2,
            td.ui-p-2 {
                display: table-cell;
            }
        }

        /* Show priority 3 at 640px (40em x 16px) */
        @media screen and (min-width: 40em) {
            th.ui-p-3,
            td.ui-p-3 {
                display: table-cell;
            }
        }

        /* Show priority 4 at 800px (50em x 16px) */
        @media screen and (min-width: 50em) {
            th.ui-p-4,
            td.ui-p-4 {
                display: table-cell;
            }
        }

        /* Show priority 5 at 960px (60em x 16px) */
        @media screen and (min-width: 60em) {
            th.ui-p-5,
            td.ui-p-5 {
                display: table-cell;
            }
        }

        /* Show priority 6 at 1,120px (70em x 16px) */
        @media screen and (min-width: 70em) {
            th.ui-p-6,
            td.ui-p-6 {
                display: table-cell;
            }
        }
    `]})
export class TableDemoComponent implements OnInit {
    msgs: Message[] = [];

    activeIndex = 0;

    browser: Browser = new MyBrowser();

    basicBrowsers: Browser[];

    browsers: Browser[];

    selectedBrowser: Browser;

    selectedBrowsers: Browser[];

    displayDialog: boolean;

    stacked: boolean;

    newBrowser: boolean;

    totalRecords = 100;

    engines: SelectItem[];

    grades: SelectItem[];

    expandedRows: any[];

    cols: any[];

    columnOptions: SelectItem[];

    tableItems: MenuItem[];

    selectedColumns: any[];

    constructor(private browserService: BrowserService) { }

    ngOnInit() {
        this.browserService.getBrowsers().subscribe((browsers: any) => this.browsers = browsers.data);
        this.browserService.getBrowsers().subscribe((browsers: any) => this.basicBrowsers = browsers.data.slice(0, 10));
        this.cols = [
            {field: 'engine', header: 'Engine'},
            {field: 'browser', header: 'Browser'},
            {field: 'platform', header: 'Platform'},
            {field: 'grade', header: 'Grade'}
        ];
        this.columnOptions = [];
        for (const col of this.cols) {
            this.columnOptions.push({label: col.header, value: col});
        }

        this.engines = [];
        this.engines.push({label: 'All engines', value: null});
        this.engines.push({label: 'Trident', value: 'Trident'});
        this.engines.push({label: 'Gecko', value: 'Gecko'});
        this.engines.push({label: 'Webkit', value: 'Webkit'});

        this.grades = [];
        this.grades.push({label: 'A', value: 'A'});
        this.grades.push({label: 'B', value: 'B'});
        this.grades.push({label: 'C', value: 'C'});

        this.tableItems = [
            { label: 'View', icon: 'pi pi-search', command: event => this.selectBrowser(this.selectedBrowser) },
            { label: 'Delete', icon: 'pi pi-times', command: event => this.delete() }
        ];
        this.selectedColumns = this.cols;

    }

    onRowClick(event: any) {
        this.msgs = [];
        this.msgs.push({severity: 'info', summary: 'Browser clicked', detail: event.data.engine + ' - ' + event.data.browser});
    }

    onRowDblClick(event: any) {
        this.msgs = [];
        this.msgs.push({severity: 'info', summary: 'Browser double clicked', detail: event.data.engine + ' - ' + event.data.browser});
    }

    onRowSelect(event: any) {
        this.msgs = [];
        this.msgs.push({severity: 'info', summary: 'Type of selection:', detail: event.type});
        this.msgs.push({severity: 'info', summary: 'Browser Selected', detail: event.data.engine + ' - ' + event.data.browser});
    }

    onRowUnselect(event: any) {
        this.msgs = [];
        this.msgs.push({severity: 'info', summary: 'Type of selection:', detail: event.type});
        this.msgs.push({severity: 'info', summary: 'Browser Unselected', detail: event.data.engine + ' - ' + event.data.browser});
    }

    onHeaderCheckboxToggle(event: any) {
        this.msgs = [];
        this.msgs.push({severity: 'info', summary: 'Header checkbox toggled:', detail: event.checked});
    }

    onContextMenuSelect(event: any) {
        this.msgs = [];
        this.msgs.push({severity: 'info', summary: 'Selected data', detail: event.data.engine + ' - ' + event.data.browser});
    }

    onColResize(event: any) {
        this.msgs = [];
        this.msgs.push({severity: 'info', summary: 'Resized column header' + event.element,
            detail: 'Change of column width' +  event.delta + 'px'});
    }

    onColReorder(event: any) {
        this.msgs = [];
        this.msgs.push({severity: 'info', summary: 'Index of dragged column', detail: event.dragIndex});
        this.msgs.push({severity: 'info', summary: 'Index of dropped column', detail: event.dropIndex});
        this.msgs.push({severity: 'info', summary: 'Columns array after reorder', detail: event.columns});
    }

    onEditInit(event: any) {
        this.msgs = [];
        this.msgs.push({severity: 'info', summary: 'Column is ', detail: event.column});
        this.msgs.push({severity: 'info', summary: 'Row data', detail: event.data.engine + ' - ' + event.data.browser});
    }

    onEdit(event: any) {
        this.msgs = [];
        this.msgs.push({severity: 'info', summary: 'Row index', detail: event.index});
        this.msgs.push({severity: 'info', summary: 'Column is ', detail: event.column});
        this.msgs.push({severity: 'info', summary: 'Row data', detail: event.data.engine + ' - ' + event.data.browser});
    }
    onEditComplete(event: any) {
        this.msgs = [];
        this.msgs.push({severity: 'info', summary: 'Row index', detail: event.index});
        this.msgs.push({severity: 'info', summary: 'Column is ', detail: event.column});
        this.msgs.push({severity: 'info', summary: 'Row data', detail: event.data.engine + ' - ' + event.data.browser});
    }

    onEditCancel(event: any) {
        this.msgs = [];
        this.msgs.push({severity: 'info', summary: 'Row index', detail: event.index});
        this.msgs.push({severity: 'info', summary: 'Column is ', detail: event.column});
        this.msgs.push({severity: 'info', summary: 'Row data', detail: event.data.engine + ' - ' + event.data.browser});
    }

    onPage(event: any) {
        this.msgs = [];
        this.msgs.push({severity: 'info', summary: 'Index of first record:', detail: event.first});
        this.msgs.push({severity: 'info', summary: 'Number of rows: ', detail: event.rows});
    }

    onSort(event: any) {
        this.msgs = [];
    }

    onFilter(event: any) {
        this.msgs = [];
        this.msgs.push({severity: 'info', summary: 'Filter object(field,value and matchmode):', detail: event.filters});
    }

    onRowExpand(event: any) {
        this.msgs = [];
        this.msgs.push({severity: 'info', summary: 'Expanded row:', detail: event.data});
    }
    onRowCollapse(event: any) {
        this.msgs = [];
        this.msgs.push({severity: 'info', summary: 'Collapsed row:', detail: event.data});
    }

    onRowGroupExpand(event: any) {
        this.msgs = [];
        this.msgs.push({severity: 'info', summary: 'Row group expanded:', detail: event.group});
    }

    onRowGroupCollapse(event: any) {
        this.msgs = [];
        this.msgs.push({severity: 'info', summary: 'Row group collapsed:', detail: event.group});
    }

    loadBrowsersLazy(event: LazyLoadEvent) {
        // event.first = First row offset
        // event.rows = Number of rows per page
        // event.sortField = Field name to sort with
        // event.sortOrder = Sort order as number, 1 for asc and -1 for dec
        // filters: FilterMetadata object having field as key and filter value, filter matchMode as value

        this.browserService.getBrowsers().subscribe((browsers: any) =>
            this.browsers = browsers.data.slice(event.first, (event.first + event.rows)));
    }

    addBrowser() {
        this.newBrowser = true;
        this.browser = new MyBrowser();
        this.displayDialog = true;
    }

    save() {
        const browsers = [...this.browsers];
        if (this.newBrowser) {
            browsers.push(this.browser);
        } else {
            browsers[this.findSelectedBrowserIndex()] = this.browser;
        }
        this.browsers = browsers;
        this.browser = null;
        this.displayDialog = false;
    }

    delete() {
        const index = this.findSelectedBrowserIndex();
        this.browsers = this.browsers.filter( (val, i) => i !== index);
        this.browser = null;
        this.displayDialog = false;
    }

    onRowSelectCRUD(event: any) {
        this.newBrowser = false;
        this.browser = Object.assign({}, event.data);
        this.displayDialog = true;
    }

    findSelectedBrowserIndex(): number {
        return this.browsers.indexOf(this.selectedBrowser);
    }

    selectBrowser(browser: Browser) {
        this.msgs = [];
        this.msgs.push({severity: 'info', summary: 'Browser selected', detail: 'Browser: ' + browser.browser});
    }

    toggle() {
        this.stacked = !this.stacked;
    }

    onChangeStep(label: string) {
        this.msgs.length = 0;
        this.msgs.push({severity: 'info', summary: label});
    }

}
