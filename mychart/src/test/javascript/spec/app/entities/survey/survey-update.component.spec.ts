import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MychartTestModule } from '../../../test.module';
import { SurveyUpdateComponent } from 'app/entities/survey/survey-update.component';
import { SurveyService } from 'app/entities/survey/survey.service';
import { Survey } from 'app/shared/model/survey.model';

describe('Component Tests', () => {
  describe('Survey Management Update Component', () => {
    let comp: SurveyUpdateComponent;
    let fixture: ComponentFixture<SurveyUpdateComponent>;
    let service: SurveyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MychartTestModule],
        declarations: [SurveyUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SurveyUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SurveyUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SurveyService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Survey('123');
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
        const entity = new Survey();
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
