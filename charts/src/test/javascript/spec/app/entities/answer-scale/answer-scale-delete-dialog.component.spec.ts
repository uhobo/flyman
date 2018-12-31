/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ChartsTestModule } from '../../../test.module';
import { AnswerScaleDeleteDialogComponent } from 'app/entities/answer-scale/answer-scale-delete-dialog.component';
import { AnswerScaleService } from 'app/entities/answer-scale/answer-scale.service';

describe('Component Tests', () => {
    describe('AnswerScale Management Delete Component', () => {
        let comp: AnswerScaleDeleteDialogComponent;
        let fixture: ComponentFixture<AnswerScaleDeleteDialogComponent>;
        let service: AnswerScaleService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ChartsTestModule],
                declarations: [AnswerScaleDeleteDialogComponent]
            })
                .overrideTemplate(AnswerScaleDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AnswerScaleDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AnswerScaleService);
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
