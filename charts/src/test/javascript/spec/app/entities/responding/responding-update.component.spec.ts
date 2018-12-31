/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ChartsTestModule } from '../../../test.module';
import { RespondingUpdateComponent } from 'app/entities/responding/responding-update.component';
import { RespondingService } from 'app/entities/responding/responding.service';
import { Responding } from 'app/shared/model/responding.model';

describe('Component Tests', () => {
    describe('Responding Management Update Component', () => {
        let comp: RespondingUpdateComponent;
        let fixture: ComponentFixture<RespondingUpdateComponent>;
        let service: RespondingService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ChartsTestModule],
                declarations: [RespondingUpdateComponent]
            })
                .overrideTemplate(RespondingUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RespondingUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RespondingService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Responding('123');
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.responding = entity;
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
                    const entity = new Responding();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.responding = entity;
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
