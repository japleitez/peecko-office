import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../article-list.test-samples';

import { ArticleListFormService } from './article-list-form.service';

describe('ArticleList Form Service', () => {
  let service: ArticleListFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ArticleListFormService);
  });

  describe('Service methods', () => {
    describe('createArticleListFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createArticleListFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            counter: expect.any(Object),
            created: expect.any(Object),
            updated: expect.any(Object),
            apsUser: expect.any(Object),
          }),
        );
      });

      it('passing IArticleList should create a new form with FormGroup', () => {
        const formGroup = service.createArticleListFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            counter: expect.any(Object),
            created: expect.any(Object),
            updated: expect.any(Object),
            apsUser: expect.any(Object),
          }),
        );
      });
    });

    describe('getArticleList', () => {
      it('should return NewArticleList for default ArticleList initial value', () => {
        const formGroup = service.createArticleListFormGroup(sampleWithNewData);

        const articleList = service.getArticleList(formGroup) as any;

        expect(articleList).toMatchObject(sampleWithNewData);
      });

      it('should return NewArticleList for empty ArticleList initial value', () => {
        const formGroup = service.createArticleListFormGroup();

        const articleList = service.getArticleList(formGroup) as any;

        expect(articleList).toMatchObject({});
      });

      it('should return IArticleList', () => {
        const formGroup = service.createArticleListFormGroup(sampleWithRequiredData);

        const articleList = service.getArticleList(formGroup) as any;

        expect(articleList).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IArticleList should not enable id FormControl', () => {
        const formGroup = service.createArticleListFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewArticleList should disable id FormControl', () => {
        const formGroup = service.createArticleListFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
