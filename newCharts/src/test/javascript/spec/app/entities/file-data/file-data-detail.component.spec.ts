/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NewChartsTestModule } from '../../../test.module';
import { FileDataDetailComponent } from 'app/entities/file-data/file-data-detail.component';
import { FileData } from 'app/shared/model/file-data.model';

describe('Component Tests', () => {
  describe('FileData Management Detail Component', () => {
    let comp: FileDataDetailComponent;
    let fixture: ComponentFixture<FileDataDetailComponent>;
    const route = ({ data: of({ fileData: new FileData('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NewChartsTestModule],
        declarations: [FileDataDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FileDataDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FileDataDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.fileData).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
