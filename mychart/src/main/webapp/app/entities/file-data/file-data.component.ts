import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { IFileData } from '../../shared/model/file-data.model';
//import { AccountService } from '../../core';

//import { ITEMS_PER_PAGE } from '../../shared';
import { FileDataService } from './file-data.service';
import {MenuItem, Message} from 'primeng/components/common/api';
import { AccountService } from 'app/core/auth/account.service';
import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';

@Component({
  selector: 'jhi-file-data',
  templateUrl: './file-data.component.html'
})
export class FileDataComponent implements OnInit, OnDestroy {
  currentAccount: any;
  fileData: IFileData[];
  error: any;
  success: any;
  eventSubscriber: Subscription;
  routeData: any;
  links: any;
  totalItems: any;
  itemsPerPage: any;
  page: any;
  predicate: any;
  previousPage: any;
  reverse: any;
  uploadMsgs: Message[] = [];
  uploadedFiles: any[] = [];

  constructor(
    protected fileDataService: FileDataService,
    protected parseLinks: JhiParseLinks,
    protected jhiAlertService: JhiAlertService,
    protected accountService: AccountService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager
  ) {
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.routeData = this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.previousPage = data.pagingParams.page;
      this.reverse = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
    });
  }

  loadAll():void {
    this.fileDataService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IFileData[]>) => this.paginateFileData(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  loadPage(page: number):void {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition():void {
    this.router.navigate(['/file-data'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    });
    this.loadAll();
  }

  clear():void {
    this.page = 0;
    this.router.navigate([
      '/file-data',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() :void{
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInFileData();
  }

  ngOnDestroy():void {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IFileData):string {
    return item.id;
  }

  registerChangeInFileData() :void{
    this.eventSubscriber = this.eventManager.subscribe('fileDataListModification', response => this.loadAll());
  }

  sort():string[] {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateFileData(data: IFileData[], headers: HttpHeaders):void {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.fileData = data;
  }

  protected onError(errorMessage: string) :void{
    this.jhiAlertService.error(errorMessage, null, null);
  }

  onUpload(event: any) :void{
    console.log('onUpload');
    for (const file of event.files) {
        this.uploadedFiles.push(file);
    }
    console.log('onUpload');
    const formData = new FormData();
    formData.append('file', this.uploadedFiles[0]);

    this.fileDataService.upload(formData).subscribe(
      (res: HttpResponse<IFileData>) => res.body,
      (res: HttpErrorResponse) => this.onError(res.message)
    );

    this.uploadedFiles = [];

   // this.uploadMsgs = [];
  // this.uploadMsgs.push({severity: 'info', summary: 'File Uploaded', detail: ''});
}
}
