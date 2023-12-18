import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IArticleList } from 'app/entities/article-list/article-list.model';
import { ArticleListService } from 'app/entities/article-list/service/article-list.service';
import { ArticleItemService } from '../service/article-item.service';
import { IArticleItem } from '../article-item.model';
import { ArticleItemFormService } from './article-item-form.service';

import { ArticleItemUpdateComponent } from './article-item-update.component';

describe('ArticleItem Management Update Component', () => {
  let comp: ArticleItemUpdateComponent;
  let fixture: ComponentFixture<ArticleItemUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let articleItemFormService: ArticleItemFormService;
  let articleItemService: ArticleItemService;
  let articleListService: ArticleListService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), ArticleItemUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ArticleItemUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ArticleItemUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    articleItemFormService = TestBed.inject(ArticleItemFormService);
    articleItemService = TestBed.inject(ArticleItemService);
    articleListService = TestBed.inject(ArticleListService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call ArticleList query and add missing value', () => {
      const articleItem: IArticleItem = { id: 456 };
      const articleList: IArticleList = { id: 14921 };
      articleItem.articleList = articleList;

      const articleListCollection: IArticleList[] = [{ id: 3033 }];
      jest.spyOn(articleListService, 'query').mockReturnValue(of(new HttpResponse({ body: articleListCollection })));
      const additionalArticleLists = [articleList];
      const expectedCollection: IArticleList[] = [...additionalArticleLists, ...articleListCollection];
      jest.spyOn(articleListService, 'addArticleListToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ articleItem });
      comp.ngOnInit();

      expect(articleListService.query).toHaveBeenCalled();
      expect(articleListService.addArticleListToCollectionIfMissing).toHaveBeenCalledWith(
        articleListCollection,
        ...additionalArticleLists.map(expect.objectContaining),
      );
      expect(comp.articleListsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const articleItem: IArticleItem = { id: 456 };
      const articleList: IArticleList = { id: 19124 };
      articleItem.articleList = articleList;

      activatedRoute.data = of({ articleItem });
      comp.ngOnInit();

      expect(comp.articleListsSharedCollection).toContain(articleList);
      expect(comp.articleItem).toEqual(articleItem);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IArticleItem>>();
      const articleItem = { id: 123 };
      jest.spyOn(articleItemFormService, 'getArticleItem').mockReturnValue(articleItem);
      jest.spyOn(articleItemService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ articleItem });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: articleItem }));
      saveSubject.complete();

      // THEN
      expect(articleItemFormService.getArticleItem).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(articleItemService.update).toHaveBeenCalledWith(expect.objectContaining(articleItem));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IArticleItem>>();
      const articleItem = { id: 123 };
      jest.spyOn(articleItemFormService, 'getArticleItem').mockReturnValue({ id: null });
      jest.spyOn(articleItemService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ articleItem: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: articleItem }));
      saveSubject.complete();

      // THEN
      expect(articleItemFormService.getArticleItem).toHaveBeenCalled();
      expect(articleItemService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IArticleItem>>();
      const articleItem = { id: 123 };
      jest.spyOn(articleItemService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ articleItem });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(articleItemService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareArticleList', () => {
      it('Should forward to articleListService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(articleListService, 'compareArticleList');
        comp.compareArticleList(entity, entity2);
        expect(articleListService.compareArticleList).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
