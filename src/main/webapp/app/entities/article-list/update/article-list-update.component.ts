import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IApsUser } from 'app/entities/aps-user/aps-user.model';
import { ApsUserService } from 'app/entities/aps-user/service/aps-user.service';
import { IArticleList } from '../article-list.model';
import { ArticleListService } from '../service/article-list.service';
import { ArticleListFormService, ArticleListFormGroup } from './article-list-form.service';

@Component({
  standalone: true,
  selector: 'jhi-article-list-update',
  templateUrl: './article-list-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ArticleListUpdateComponent implements OnInit {
  isSaving = false;
  articleList: IArticleList | null = null;

  apsUsersSharedCollection: IApsUser[] = [];

  editForm: ArticleListFormGroup = this.articleListFormService.createArticleListFormGroup();

  constructor(
    protected articleListService: ArticleListService,
    protected articleListFormService: ArticleListFormService,
    protected apsUserService: ApsUserService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareApsUser = (o1: IApsUser | null, o2: IApsUser | null): boolean => this.apsUserService.compareApsUser(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ articleList }) => {
      this.articleList = articleList;
      if (articleList) {
        this.updateForm(articleList);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const articleList = this.articleListFormService.getArticleList(this.editForm);
    if (articleList.id !== null) {
      this.subscribeToSaveResponse(this.articleListService.update(articleList));
    } else {
      this.subscribeToSaveResponse(this.articleListService.create(articleList));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IArticleList>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(articleList: IArticleList): void {
    this.articleList = articleList;
    this.articleListFormService.resetForm(this.editForm, articleList);

    this.apsUsersSharedCollection = this.apsUserService.addApsUserToCollectionIfMissing<IApsUser>(
      this.apsUsersSharedCollection,
      articleList.apsUser,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.apsUserService
      .query()
      .pipe(map((res: HttpResponse<IApsUser[]>) => res.body ?? []))
      .pipe(
        map((apsUsers: IApsUser[]) => this.apsUserService.addApsUserToCollectionIfMissing<IApsUser>(apsUsers, this.articleList?.apsUser)),
      )
      .subscribe((apsUsers: IApsUser[]) => (this.apsUsersSharedCollection = apsUsers));
  }
}
