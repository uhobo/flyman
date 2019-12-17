/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { NewChartsTestModule } from '../../../test.module';
import { FileDataUpdateComponent } from 'app/entities/file-data/file-data-update.component';
import { FileDataService } from 'app/entities/file-data/file-data.service';
import { FileData } from 'app/shared/model/file-data.model';

describe('Component Tests', () => {
  describe('FileData Management Update Component', () => {
    let comp: FileDataUpdateComponent;
    let fixture: ComponentFixture<FileDataUpdateComponent>;
    let service: FileDataService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NewChartsTestModule],
        declarations: [FileDataUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FileDataUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FileDataUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FileDataService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FileData('123');
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new FileData();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
