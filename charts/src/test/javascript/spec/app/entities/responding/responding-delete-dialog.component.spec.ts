/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ChartsTestModule } from '../../../test.module';
import { RespondingDeleteDialogComponent } from 'app/entities/responding/responding-delete-dialog.component';
import { RespondingService } from 'app/entities/responding/responding.service';

describe('Component Tests', () => {
    describe('Responding Management Delete Component', () => {
        let comp: RespondingDeleteDialogComponent;
        let fixture: ComponentFixture<RespondingDeleteDialogComponent>;
        let service: RespondingService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ChartsTestModule],
                declarations: [RespondingDeleteDialogComponent]
            })
                .overrideTemplate(RespondingDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RespondingDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RespondingService);
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
