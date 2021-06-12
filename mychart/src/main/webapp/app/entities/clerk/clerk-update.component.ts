import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IClerk, Clerk } from 'app/shared/model/clerk.model';
import { ClerkService } from './clerk.service';

@Component({
  selector: 'jhi-clerk-update',
  templateUrl: './clerk-update.component.html'
})
export class ClerkUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    firstName: [null, [Validators.required]],
    lastName: []
  });

  constructor(protected clerkService: ClerkService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ clerk }) => {
      this.updateForm(clerk);
    });
  }

  updateForm(clerk: IClerk): void {
    this.editForm.patchValue({
      id: clerk.id,
      firstName: clerk.firstName,
      lastName: clerk.lastName
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const clerk = this.createFromForm();
    if (clerk.id !== undefined) {
      this.subscribeToSaveResponse(this.clerkService.update(clerk));
    } else {
      this.subscribeToSaveResponse(this.clerkService.create(clerk));
    }
  }

  private createFromForm(): IClerk {
    return {
      ...new Clerk(),
      id: this.editForm.get(['id'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IClerk>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
