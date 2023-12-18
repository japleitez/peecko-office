import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../article-item.test-samples';

import { ArticleItemFormService } from './article-item-form.service';

describe('ArticleItem Form Service', () => {
  let service: ArticleItemFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ArticleItemFormService);
  });

  describe('Service methods', () => {
    describe('createArticleItemFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createArticleItemFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            previous: expect.any(Object),
            code: expect.any(Object),
            next: expect.any(Object),
            articleList: expect.any(Object),
          }),
        );
      });

      it('passing IArticleItem should create a new form with FormGroup', () => {
        const formGroup = service.createArticleItemFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            previous: expect.any(Object),
            code: expect.any(Object),
            next: expect.any(Object),
            articleList: expect.any(Object),
          }),
        );
      });
    });

    describe('getArticleItem', () => {
      it('should return NewArticleItem for default ArticleItem initial value', () => {
        const formGroup = service.createArticleItemFormGroup(sampleWithNewData);

        const articleItem = service.getArticleItem(formGroup) as any;

        expect(articleItem).toMatchObject(sampleWithNewData);
      });

      it('should return NewArticleItem for empty ArticleItem initial value', () => {
        const formGroup = service.createArticleItemFormGroup();

        const articleItem = service.getArticleItem(formGroup) as any;

        expect(articleItem).toMatchObject({});
      });

      it('should return IArticleItem', () => {
        const formGroup = service.createArticleItemFormGroup(sampleWithRequiredData);

        const articleItem = service.getArticleItem(formGroup) as any;

        expect(articleItem).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IArticleItem should not enable id FormControl', () => {
        const formGroup = service.createArticleItemFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewArticleItem should disable id FormControl', () => {
        const formGroup = service.createArticleItemFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
