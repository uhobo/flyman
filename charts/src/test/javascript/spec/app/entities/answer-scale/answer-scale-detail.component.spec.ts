/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ChartsTestModule } from '../../../test.module';
import { AnswerScaleDetailComponent } from 'app/entities/answer-scale/answer-scale-detail.component';
import { AnswerScale } from 'app/shared/model/answer-scale.model';

describe('Component Tests', () => {
    describe('AnswerScale Management Detail Component', () => {
        let comp: AnswerScaleDetailComponent;
        let fixture: ComponentFixture<AnswerScaleDetailComponent>;
        const route = ({ data: of({ answerScale: new AnswerScale('123') }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ChartsTestModule],
                declarations: [AnswerScaleDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AnswerScaleDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AnswerScaleDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.answerScale).toEqual(jasmine.objectContaining({ id: '123' }));
            });
        });
    });
});
