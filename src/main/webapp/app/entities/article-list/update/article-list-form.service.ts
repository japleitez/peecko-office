import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IArticleList, NewArticleList } from '../article-list.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IArticleList for edit and NewArticleListFormGroupInput for create.
 */
type ArticleListFormGroupInput = IArticleList | PartialWithRequiredKeyOf<NewArticleList>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IArticleList | NewArticleList> = Omit<T, 'created' | 'updated'> & {
  created?: string | null;
  updated?: string | null;
};

type ArticleListFormRawValue = FormValueOf<IArticleList>;

type NewArticleListFormRawValue = FormValueOf<NewArticleList>;

type ArticleListFormDefaults = Pick<NewArticleList, 'id' | 'created' | 'updated'>;

type ArticleListFormGroupContent = {
  id: FormControl<ArticleListFormRawValue['id'] | NewArticleList['id']>;
  name: FormControl<ArticleListFormRawValue['name']>;
  counter: FormControl<ArticleListFormRawValue['counter']>;
  created: FormControl<ArticleListFormRawValue['created']>;
  updated: FormControl<ArticleListFormRawValue['updated']>;
  apsUser: FormControl<ArticleListFormRawValue['apsUser']>;
};

export type ArticleListFormGroup = FormGroup<ArticleListFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ArticleListFormService {
  createArticleListFormGroup(articleList: ArticleListFormGroupInput = { id: null }): ArticleListFormGroup {
    const articleListRawValue = this.convertArticleListToArticleListRawValue({
      ...this.getFormDefaults(),
      ...articleList,
    });
    return new FormGroup<ArticleListFormGroupContent>({
      id: new FormControl(
        { value: articleListRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      name: new FormControl(articleListRawValue.name, {
        validators: [Validators.required],
      }),
      counter: new FormControl(articleListRawValue.counter, {
        validators: [Validators.required],
      }),
      created: new FormControl(articleListRawValue.created, {
        validators: [Validators.required],
      }),
      updated: new FormControl(articleListRawValue.updated, {
        validators: [Validators.required],
      }),
      apsUser: new FormControl(articleListRawValue.apsUser),
    });
  }

  getArticleList(form: ArticleListFormGroup): IArticleList | NewArticleList {
    return this.convertArticleListRawValueToArticleList(form.getRawValue() as ArticleListFormRawValue | NewArticleListFormRawValue);
  }

  resetForm(form: ArticleListFormGroup, articleList: ArticleListFormGroupInput): void {
    const articleListRawValue = this.convertArticleListToArticleListRawValue({ ...this.getFormDefaults(), ...articleList });
    form.reset(
      {
        ...articleListRawValue,
        id: { value: articleListRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ArticleListFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      created: currentTime,
      updated: currentTime,
    };
  }

  private convertArticleListRawValueToArticleList(
    rawArticleList: ArticleListFormRawValue | NewArticleListFormRawValue,
  ): IArticleList | NewArticleList {
    return {
      ...rawArticleList,
      created: dayjs(rawArticleList.created, DATE_TIME_FORMAT),
      updated: dayjs(rawArticleList.updated, DATE_TIME_FORMAT),
    };
  }

  private convertArticleListToArticleListRawValue(
    articleList: IArticleList | (Partial<NewArticleList> & ArticleListFormDefaults),
  ): ArticleListFormRawValue | PartialWithRequiredKeyOf<NewArticleListFormRawValue> {
    return {
      ...articleList,
      created: articleList.created ? articleList.created.format(DATE_TIME_FORMAT) : undefined,
      updated: articleList.updated ? articleList.updated.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
