import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MychartTestModule } from '../../../test.module';
import { ClerkComponent } from 'app/entities/clerk/clerk.component';
import { ClerkService } from 'app/entities/clerk/clerk.service';
import { Clerk } from 'app/shared/model/clerk.model';

describe('Component Tests', () => {
  describe('Clerk Management Component', () => {
    let comp: ClerkComponent;
    let fixture: ComponentFixture<ClerkComponent>;
    let service: ClerkService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MychartTestModule],
        declarations: [ClerkComponent],
        providers: []
      })
        .overrideTemplate(ClerkComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ClerkComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ClerkService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Clerk('123')],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.clerks && comp.clerks[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });
  });
});
