import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IAsked } from 'app/shared/model/asked.model';
import { AskedService } from './asked.service';
import { IAttribute } from 'app/shared/model/attribute.model';
import { AttributeService } from 'app/entities/attribute';

@Component({
    selector: 'jhi-asked-update',
    templateUrl: './asked-update.component.html'
})
export class AskedUpdateComponent implements OnInit {
    asked: IAsked;
    isSaving: boolean;

    attributes: IAttribute[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private askedService: AskedService,
        private attributeService: AttributeService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ asked }) => {
            this.asked = asked;
        });
        this.attributeService.query().subscribe(
            (res: HttpResponse<IAttribute[]>) => {
                this.attributes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.asked.id !== undefined) {
            this.subscribeToSaveResponse(this.askedService.update(this.asked));
        } else {
            this.subscribeToSaveResponse(this.askedService.create(this.asked));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAsked>>) {
        result.subscribe((res: HttpResponse<IAsked>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
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
