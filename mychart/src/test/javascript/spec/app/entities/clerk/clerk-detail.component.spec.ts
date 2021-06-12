import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MychartTestModule } from '../../../test.module';
import { ClerkDetailComponent } from 'app/entities/clerk/clerk-detail.component';
import { Clerk } from 'app/shared/model/clerk.model';

describe('Component Tests', () => {
  describe('Clerk Management Detail Component', () => {
    let comp: ClerkDetailComponent;
    let fixture: ComponentFixture<ClerkDetailComponent>;
    const route = ({ data: of({ clerk: new Clerk('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MychartTestModule],
        declarations: [ClerkDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ClerkDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ClerkDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load clerk on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.clerk).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
