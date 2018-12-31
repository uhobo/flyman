/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ChartsTestModule } from '../../../test.module';
import { AskedDeleteDialogComponent } from 'app/entities/asked/asked-delete-dialog.component';
import { AskedService } from 'app/entities/asked/asked.service';

describe('Component Tests', () => {
    describe('Asked Management Delete Component', () => {
        let comp: AskedDeleteDialogComponent;
        let fixture: ComponentFixture<AskedDeleteDialogComponent>;
        let service: AskedService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ChartsTestModule],
                declarations: [AskedDeleteDialogComponent]
            })
                .overrideTemplate(AskedDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AskedDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AskedService);
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
