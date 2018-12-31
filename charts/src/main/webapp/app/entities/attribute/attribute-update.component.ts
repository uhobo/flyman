import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IAttribute, Attribute } from 'app/shared/model/attribute.model';
import { AttributeService } from './attribute.service';
import { AttributeType, IAttributeType } from 'app/shared/model/attributeType.model';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { AttributeValue } from 'app/shared/model/attributeValue.model';

@Component({
    selector: 'jhi-attribute-update',
    templateUrl: './attribute-update.component.html'
})
export class AttributeUpdateComponent implements OnInit {
    attribute: IAttribute;
    isSaving: boolean;
    attributeTypes: AttributeType[];
    inputValue : any;
    valuesList: any[] = [];
    //integerValues: number[];
    //stringValues : string[];
    constructor(private attributeService: AttributeService, private activatedRoute: ActivatedRoute, private jhiAlertService: JhiAlertService) {}

    ngOnInit() {
        this.isSaving = false;
        this.loadAttributeTypes();
        this.activatedRoute.data.subscribe(({ attribute }) => {
            this.attribute = attribute;
            if(this.attribute  !== undefined && this.attribute .id !== undefined){
                for(let val of this.attribute.valuesList){
                    this.valuesList.push(val.value);
                }
            }else{
                this.attribute = new Attribute();
                this.attribute.isClosedValueList = false;
            }
            
            
        });
    }

    previousState() {
        window.history.back();
    }

    loadAttributeTypes(){
        this.attributeService.queryAttributeTypes().subscribe(
            (res: HttpResponse<IAttribute[]>) => {
                this.attributeTypes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
    save() {
        this.isSaving = true;
        this.attribute.valuesList = [];
        for(let value of this.valuesList){
            let attributeValue = new AttributeValue();
            attributeValue.className = this.attribute.attributeType.className;
            attributeValue.value = value;
            this.attribute.valuesList.push(attributeValue);
        }


        if (this.attribute.id !== undefined) {
            this.subscribeToSaveResponse(this.attributeService.update(this.attribute));
        } else {
            this.subscribeToSaveResponse(this.attributeService.create(this.attribute));
        }
    }
    

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAttribute>>) {
        result.subscribe((res: HttpResponse<IAttribute>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError(res));
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError(res: HttpErrorResponse) {
        console.log(res);
        this.isSaving = false;
    }

    trackAttributeById(index: number, item: IAttributeType) {
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
    
    isCheckboxEnabled(): boolean{
        if(this.attribute.attributeType === null || this.attribute.attributeType === undefined ||  !this.attribute.attributeType.enableList){
            return false;
        }
        return true;
    }

    isNumberType() : boolean{
        if(this.attribute === null || this.attribute.attributeType === undefined ||  this.attribute.attributeType === null){
            return false;
        }

        if(this.attribute.attributeType.className === "java.lang.Integer" ){
            return true;
        }
        return false;
    }

    addValueToList(){
        // if(this.isNumberType()){
        //     this.integerValues.push(this.inputValue);
        // }else{
        //     this.stringValues.push(this.inputValue);
        // }
        if(this.valuesList.indexOf(this.inputValue) > -1){
               return; 
        } 
        this.valuesList.push(this.inputValue);
        this.inputValue = null;
    }

    clearValues(){
        this.valuesList = [];
    }
}
