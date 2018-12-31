/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ChartsTestModule } from '../../../test.module';
import { AnswerScaleComponent } from 'app/entities/answer-scale/answer-scale.component';
import { AnswerScaleService } from 'app/entities/answer-scale/answer-scale.service';
import { AnswerScale } from 'app/shared/model/answer-scale.model';

describe('Component Tests', () => {
    describe('AnswerScale Management Component', () => {
        let comp: AnswerScaleComponent;
        let fixture: ComponentFixture<AnswerScaleComponent>;
        let service: AnswerScaleService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ChartsTestModule],
                declarations: [AnswerScaleComponent],
                providers: []
            })
                .overrideTemplate(AnswerScaleComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AnswerScaleComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AnswerScaleService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new AnswerScale('123')],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.answerScales[0]).toEqual(jasmine.objectContaining({ id: '123' }));
        });
    });
});
