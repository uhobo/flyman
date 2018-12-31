/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ChartsTestModule } from '../../../test.module';
import { RespondingComponent } from 'app/entities/responding/responding.component';
import { RespondingService } from 'app/entities/responding/responding.service';
import { Responding } from 'app/shared/model/responding.model';

describe('Component Tests', () => {
    describe('Responding Management Component', () => {
        let comp: RespondingComponent;
        let fixture: ComponentFixture<RespondingComponent>;
        let service: RespondingService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ChartsTestModule],
                declarations: [RespondingComponent],
                providers: []
            })
                .overrideTemplate(RespondingComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RespondingComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RespondingService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Responding('123')],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.respondings[0]).toEqual(jasmine.objectContaining({ id: '123' }));
        });
    });
});
