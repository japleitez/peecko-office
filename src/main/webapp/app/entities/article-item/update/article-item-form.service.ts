import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IArticleItem, NewArticleItem } from '../article-item.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IArticleItem for edit and NewArticleItemFormGroupInput for create.
 */
type ArticleItemFormGroupInput = IArticleItem | PartialWithRequiredKeyOf<NewArticleItem>;

type ArticleItemFormDefaults = Pick<NewArticleItem, 'id'>;

type ArticleItemFormGroupContent = {
  id: FormControl<IArticleItem['id'] | NewArticleItem['id']>;
  previous: FormControl<IArticleItem['previous']>;
  code: FormControl<IArticleItem['code']>;
  next: FormControl<IArticleItem['next']>;
  articleList: FormControl<IArticleItem['articleList']>;
};

export type ArticleItemFormGroup = FormGroup<ArticleItemFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ArticleItemFormService {
  createArticleItemFormGroup(articleItem: ArticleItemFormGroupInput = { id: null }): ArticleItemFormGroup {
    const articleItemRawValue = {
      ...this.getFormDefaults(),
      ...articleItem,
    };
    return new FormGroup<ArticleItemFormGroupContent>({
      id: new FormControl(
        { value: articleItemRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      previous: new FormControl(articleItemRawValue.previous),
      code: new FormControl(articleItemRawValue.code),
      next: new FormControl(articleItemRawValue.next),
      articleList: new FormControl(articleItemRawValue.articleList),
    });
  }

  getArticleItem(form: ArticleItemFormGroup): IArticleItem | NewArticleItem {
    return form.getRawValue() as IArticleItem | NewArticleItem;
  }

  resetForm(form: ArticleItemFormGroup, articleItem: ArticleItemFormGroupInput): void {
    const articleItemRawValue = { ...this.getFormDefaults(), ...articleItem };
    form.reset(
      {
        ...articleItemRawValue,
        id: { value: articleItemRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ArticleItemFormDefaults {
    return {
      id: null,
    };
  }
}
