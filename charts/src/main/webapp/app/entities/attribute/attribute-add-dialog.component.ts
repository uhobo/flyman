import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { IAttribute } from 'app/shared/model/attribute.model';



@Component({
    selector: 'jhi-attribute-add-dialog',
    templateUrl: './attribute-add-dialog.component.html'
})
export class AttributeAddDialogComponent {

    unSelectedAttributes : IAttribute[] = [];
    selectedAttributes : IAttribute[];

    constructor( public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {
        
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save(){     
        this.activeModal.dismiss(true);
    }

    trackAttributeById(index: number, item: IAttribute) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
    
}

