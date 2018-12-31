/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ChartsTestModule } from '../../../test.module';
import { RespondingDetailComponent } from 'app/entities/responding/responding-detail.component';
import { Responding } from 'app/shared/model/responding.model';

describe('Component Tests', () => {
    describe('Responding Management Detail Component', () => {
        let comp: RespondingDetailComponent;
        let fixture: ComponentFixture<RespondingDetailComponent>;
        const route = ({ data: of({ responding: new Responding('123') }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ChartsTestModule],
                declarations: [RespondingDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(RespondingDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RespondingDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.responding).toEqual(jasmine.objectContaining({ id: '123' }));
            });
        });
    });
});
