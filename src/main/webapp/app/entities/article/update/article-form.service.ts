import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IArticle, NewArticle } from '../article.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IArticle for edit and NewArticleFormGroupInput for create.
 */
type ArticleFormGroupInput = IArticle | PartialWithRequiredKeyOf<NewArticle>;

type ArticleFormDefaults = Pick<NewArticle, 'id'>;

type ArticleFormGroupContent = {
  id: FormControl<IArticle['id'] | NewArticle['id']>;
  code: FormControl<IArticle['code']>;
  title: FormControl<IArticle['title']>;
  subtitle: FormControl<IArticle['subtitle']>;
  summary: FormControl<IArticle['summary']>;
  duration: FormControl<IArticle['duration']>;
  language: FormControl<IArticle['language']>;
  thumbnail: FormControl<IArticle['thumbnail']>;
  audioUrl: FormControl<IArticle['audioUrl']>;
  content: FormControl<IArticle['content']>;
  articleCategory: FormControl<IArticle['articleCategory']>;
  coach: FormControl<IArticle['coach']>;
};

export type ArticleFormGroup = FormGroup<ArticleFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ArticleFormService {
  createArticleFormGroup(article: ArticleFormGroupInput = { id: null }): ArticleFormGroup {
    const articleRawValue = {
      ...this.getFormDefaults(),
      ...article,
    };
    return new FormGroup<ArticleFormGroupContent>({
      id: new FormControl(
        { value: articleRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      code: new FormControl(articleRawValue.code, {
        validators: [Validators.required],
      }),
      title: new FormControl(articleRawValue.title, {
        validators: [Validators.required],
      }),
      subtitle: new FormControl(articleRawValue.subtitle),
      summary: new FormControl(articleRawValue.summary),
      duration: new FormControl(articleRawValue.duration),
      language: new FormControl(articleRawValue.language, {
        validators: [Validators.required],
      }),
      thumbnail: new FormControl(articleRawValue.thumbnail),
      audioUrl: new FormControl(articleRawValue.audioUrl),
      content: new FormControl(articleRawValue.content),
      articleCategory: new FormControl(articleRawValue.articleCategory),
      coach: new FormControl(articleRawValue.coach),
    });
  }

  getArticle(form: ArticleFormGroup): IArticle | NewArticle {
    return form.getRawValue() as IArticle | NewArticle;
  }

  resetForm(form: ArticleFormGroup, article: ArticleFormGroupInput): void {
    const articleRawValue = { ...this.getFormDefaults(), ...article };
    form.reset(
      {
        ...articleRawValue,
        id: { value: articleRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ArticleFormDefaults {
    return {
      id: null,
    };
  }
}
