/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ChartsTestModule } from '../../../test.module';
import { AskedUpdateComponent } from 'app/entities/asked/asked-update.component';
import { AskedService } from 'app/entities/asked/asked.service';
import { Asked } from 'app/shared/model/asked.model';

describe('Component Tests', () => {
    describe('Asked Management Update Component', () => {
        let comp: AskedUpdateComponent;
        let fixture: ComponentFixture<AskedUpdateComponent>;
        let service: AskedService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ChartsTestModule],
                declarations: [AskedUpdateComponent]
            })
                .overrideTemplate(AskedUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AskedUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AskedService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Asked('123');
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.asked = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Asked();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.asked = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
