import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IArticleList } from 'app/entities/article-list/article-list.model';
import { ArticleListService } from 'app/entities/article-list/service/article-list.service';
import { IArticleItem } from '../article-item.model';
import { ArticleItemService } from '../service/article-item.service';
import { ArticleItemFormService, ArticleItemFormGroup } from './article-item-form.service';

@Component({
  standalone: true,
  selector: 'jhi-article-item-update',
  templateUrl: './article-item-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ArticleItemUpdateComponent implements OnInit {
  isSaving = false;
  articleItem: IArticleItem | null = null;

  articleListsSharedCollection: IArticleList[] = [];

  editForm: ArticleItemFormGroup = this.articleItemFormService.createArticleItemFormGroup();

  constructor(
    protected articleItemService: ArticleItemService,
    protected articleItemFormService: ArticleItemFormService,
    protected articleListService: ArticleListService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareArticleList = (o1: IArticleList | null, o2: IArticleList | null): boolean => this.articleListService.compareArticleList(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ articleItem }) => {
      this.articleItem = articleItem;
      if (articleItem) {
        this.updateForm(articleItem);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const articleItem = this.articleItemFormService.getArticleItem(this.editForm);
    if (articleItem.id !== null) {
      this.subscribeToSaveResponse(this.articleItemService.update(articleItem));
    } else {
      this.subscribeToSaveResponse(this.articleItemService.create(articleItem));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IArticleItem>>): void {
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

  protected updateForm(articleItem: IArticleItem): void {
    this.articleItem = articleItem;
    this.articleItemFormService.resetForm(this.editForm, articleItem);

    this.articleListsSharedCollection = this.articleListService.addArticleListToCollectionIfMissing<IArticleList>(
      this.articleListsSharedCollection,
      articleItem.articleList,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.articleListService
      .query()
      .pipe(map((res: HttpResponse<IArticleList[]>) => res.body ?? []))
      .pipe(
        map((articleLists: IArticleList[]) =>
          this.articleListService.addArticleListToCollectionIfMissing<IArticleList>(articleLists, this.articleItem?.articleList),
        ),
      )
      .subscribe((articleLists: IArticleList[]) => (this.articleListsSharedCollection = articleLists));
  }
}
