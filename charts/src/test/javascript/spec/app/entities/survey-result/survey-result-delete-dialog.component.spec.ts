/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ChartsTestModule } from '../../../test.module';
import { SurveyResultDeleteDialogComponent } from 'app/entities/survey-result/survey-result-delete-dialog.component';
import { SurveyResultService } from 'app/entities/survey-result/survey-result.service';

describe('Component Tests', () => {
    describe('SurveyResult Management Delete Component', () => {
        let comp: SurveyResultDeleteDialogComponent;
        let fixture: ComponentFixture<SurveyResultDeleteDialogComponent>;
        let service: SurveyResultService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ChartsTestModule],
                declarations: [SurveyResultDeleteDialogComponent]
            })
                .overrideTemplate(SurveyResultDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SurveyResultDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SurveyResultService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete('123');
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith('123');
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
