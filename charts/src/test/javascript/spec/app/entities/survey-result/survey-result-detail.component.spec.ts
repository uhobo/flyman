/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ChartsTestModule } from '../../../test.module';
import { SurveyResultDetailComponent } from 'app/entities/survey-result/survey-result-detail.component';
import { SurveyResult } from 'app/shared/model/survey-result.model';

describe('Component Tests', () => {
    describe('SurveyResult Management Detail Component', () => {
        let comp: SurveyResultDetailComponent;
        let fixture: ComponentFixture<SurveyResultDetailComponent>;
        const route = ({ data: of({ surveyResult: new SurveyResult('123') }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ChartsTestModule],
                declarations: [SurveyResultDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SurveyResultDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SurveyResultDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.surveyResult).toEqual(jasmine.objectContaining({ id: '123' }));
            });
        });
    });
});
