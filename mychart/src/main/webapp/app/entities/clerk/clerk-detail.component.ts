import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IClerk } from 'app/shared/model/clerk.model';

@Component({
  selector: 'jhi-clerk-detail',
  templateUrl: './clerk-detail.component.html'
})
export class ClerkDetailComponent implements OnInit {
  clerk: IClerk | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ clerk }) => {
      this.clerk = clerk;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
