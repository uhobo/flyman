import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAsked } from 'app/shared/model/asked.model';

@Component({
    selector: 'jhi-asked-detail',
    templateUrl: './asked-detail.component.html'
})
export class AskedDetailComponent implements OnInit {
    asked: IAsked;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ asked }) => {
            this.asked = asked;
        });
    }

    previousState() {
        window.history.back();
    }
}
