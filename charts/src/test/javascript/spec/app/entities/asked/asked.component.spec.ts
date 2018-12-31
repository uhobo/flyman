/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ChartsTestModule } from '../../../test.module';
import { AskedComponent } from 'app/entities/asked/asked.component';
import { AskedService } from 'app/entities/asked/asked.service';
import { Asked } from 'app/shared/model/asked.model';

describe('Component Tests', () => {
    describe('Asked Management Component', () => {
        let comp: AskedComponent;
        let fixture: ComponentFixture<AskedComponent>;
        let service: AskedService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ChartsTestModule],
                declarations: [AskedComponent],
                providers: []
            })
                .overrideTemplate(AskedComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AskedComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AskedService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Asked('123')],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.askeds[0]).toEqual(jasmine.objectContaining({ id: '123' }));
        });
    });
});
