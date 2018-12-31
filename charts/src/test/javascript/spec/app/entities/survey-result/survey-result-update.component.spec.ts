/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ChartsTestModule } from '../../../test.module';
import { SurveyResultUpdateComponent } from 'app/entities/survey-result/survey-result-update.component';
import { SurveyResultService } from 'app/entities/survey-result/survey-result.service';
import { SurveyResult } from 'app/shared/model/survey-result.model';

describe('Component Tests', () => {
    describe('SurveyResult Management Update Component', () => {
        let comp: SurveyResultUpdateComponent;
        let fixture: ComponentFixture<SurveyResultUpdateComponent>;
        let service: SurveyResultService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ChartsTestModule],
                declarations: [SurveyResultUpdateComponent]
            })
                .overrideTemplate(SurveyResultUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SurveyResultUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SurveyResultService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new SurveyResult('123');
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.surveyResult = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new SurveyResult();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.surveyResult = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
