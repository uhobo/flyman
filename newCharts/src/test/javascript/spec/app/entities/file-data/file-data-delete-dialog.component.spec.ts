/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { NewChartsTestModule } from '../../../test.module';
import { FileDataDeleteDialogComponent } from 'app/entities/file-data/file-data-delete-dialog.component';
import { FileDataService } from 'app/entities/file-data/file-data.service';

describe('Component Tests', () => {
  describe('FileData Management Delete Component', () => {
    let comp: FileDataDeleteDialogComponent;
    let fixture: ComponentFixture<FileDataDeleteDialogComponent>;
    let service: FileDataService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NewChartsTestModule],
        declarations: [FileDataDeleteDialogComponent]
      })
        .overrideTemplate(FileDataDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FileDataDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FileDataService);
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
