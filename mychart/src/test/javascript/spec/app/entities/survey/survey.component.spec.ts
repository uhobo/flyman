import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MychartTestModule } from '../../../test.module';
import { SurveyComponent } from 'app/entities/survey/survey.component';
import { SurveyService } from 'app/entities/survey/survey.service';
import { Survey } from 'app/shared/model/survey.model';

describe('Component Tests', () => {
  describe('Survey Management Component', () => {
    let comp: SurveyComponent;
    let fixture: ComponentFixture<SurveyComponent>;
    let service: SurveyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MychartTestModule],
        declarations: [SurveyComponent],
        providers: []
      })
        .overrideTemplate(SurveyComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SurveyComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SurveyService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Survey('123')],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.surveys && comp.surveys[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });
  });
});
