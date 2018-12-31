/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ChartsTestModule } from '../../../test.module';
import { AskedDetailComponent } from 'app/entities/asked/asked-detail.component';
import { Asked } from 'app/shared/model/asked.model';

describe('Component Tests', () => {
    describe('Asked Management Detail Component', () => {
        let comp: AskedDetailComponent;
        let fixture: ComponentFixture<AskedDetailComponent>;
        const route = ({ data: of({ asked: new Asked('123') }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ChartsTestModule],
                declarations: [AskedDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AskedDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AskedDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.asked).toEqual(jasmine.objectContaining({ id: '123' }));
            });
        });
    });
});
