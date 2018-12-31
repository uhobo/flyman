/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ChartsTestModule } from '../../../test.module';
import { SurveyResultComponent } from 'app/entities/survey-result/survey-result.component';
import { SurveyResultService } from 'app/entities/survey-result/survey-result.service';
import { SurveyResult } from 'app/shared/model/survey-result.model';

describe('Component Tests', () => {
    describe('SurveyResult Management Component', () => {
        let comp: SurveyResultComponent;
        let fixture: ComponentFixture<SurveyResultComponent>;
        let service: SurveyResultService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ChartsTestModule],
                declarations: [SurveyResultComponent],
                providers: []
            })
                .overrideTemplate(SurveyResultComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SurveyResultComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SurveyResultService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new SurveyResult('123')],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.surveyResults[0]).toEqual(jasmine.objectContaining({ id: '123' }));
        });
    });
});
