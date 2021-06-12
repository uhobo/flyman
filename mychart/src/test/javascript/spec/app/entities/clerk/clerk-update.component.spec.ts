import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MychartTestModule } from '../../../test.module';
import { ClerkUpdateComponent } from 'app/entities/clerk/clerk-update.component';
import { ClerkService } from 'app/entities/clerk/clerk.service';
import { Clerk } from 'app/shared/model/clerk.model';

describe('Component Tests', () => {
  describe('Clerk Management Update Component', () => {
    let comp: ClerkUpdateComponent;
    let fixture: ComponentFixture<ClerkUpdateComponent>;
    let service: ClerkService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MychartTestModule],
        declarations: [ClerkUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ClerkUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ClerkUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ClerkService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Clerk('123');
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
        const entity = new Clerk();
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
