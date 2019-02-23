import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAttribute } from 'app/shared/model/attribute.model';
import { Principal } from 'app/core';
import { AttributeService } from './attribute.service';

@Component({
    selector: 'jhi-attribute',
    templateUrl: './attribute.component.html'
})
export class AttributeComponent implements OnInit, OnDestroy {
    attributes: IAttribute[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private attributeService: AttributeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.attributeService.query().subscribe(
            (res: HttpResponse<IAttribute[]>) => {
                this.attributes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInAttributes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IAttribute) {
        return item.id;
    }

    registerChangeInAttributes() {
        this.eventSubscriber = this.eventManager.subscribe('attributeListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
    getvalues(attribute:IAttribute):string{
        
            if(!attribute.isClosedValueList){
                return "";
            }
            let valuesStr:string = "";
            attribute.valuesList.forEach(x=> { valuesStr+= x.value + ","});
            return valuesStr.substr(0, valuesStr.length-1);
      
    }
}
