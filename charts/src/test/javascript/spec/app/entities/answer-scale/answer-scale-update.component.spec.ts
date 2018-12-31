/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ChartsTestModule } from '../../../test.module';
import { AnswerScaleUpdateComponent } from 'app/entities/answer-scale/answer-scale-update.component';
import { AnswerScaleService } from 'app/entities/answer-scale/answer-scale.service';
import { AnswerScale } from 'app/shared/model/answer-scale.model';

describe('Component Tests', () => {
    describe('AnswerScale Management Update Component', () => {
        let comp: AnswerScaleUpdateComponent;
        let fixture: ComponentFixture<AnswerScaleUpdateComponent>;
        let service: AnswerScaleService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ChartsTestModule],
                declarations: [AnswerScaleUpdateComponent]
            })
                .overrideTemplate(AnswerScaleUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AnswerScaleUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AnswerScaleService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new AnswerScale('123');
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.answerScale = entity;
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
                    const entity = new AnswerScale();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.answerScale = entity;
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
