import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IApsUser } from 'app/entities/aps-user/aps-user.model';
import { ApsUserService } from 'app/entities/aps-user/service/aps-user.service';
import { ArticleListService } from '../service/article-list.service';
import { IArticleList } from '../article-list.model';
import { ArticleListFormService } from './article-list-form.service';

import { ArticleListUpdateComponent } from './article-list-update.component';

describe('ArticleList Management Update Component', () => {
  let comp: ArticleListUpdateComponent;
  let fixture: ComponentFixture<ArticleListUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let articleListFormService: ArticleListFormService;
  let articleListService: ArticleListService;
  let apsUserService: ApsUserService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), ArticleListUpdateComponent],
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
      .overrideTemplate(ArticleListUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ArticleListUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    articleListFormService = TestBed.inject(ArticleListFormService);
    articleListService = TestBed.inject(ArticleListService);
    apsUserService = TestBed.inject(ApsUserService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call ApsUser query and add missing value', () => {
      const articleList: IArticleList = { id: 456 };
      const apsUser: IApsUser = { id: 13492 };
      articleList.apsUser = apsUser;

      const apsUserCollection: IApsUser[] = [{ id: 13388 }];
      jest.spyOn(apsUserService, 'query').mockReturnValue(of(new HttpResponse({ body: apsUserCollection })));
      const additionalApsUsers = [apsUser];
      const expectedCollection: IApsUser[] = [...additionalApsUsers, ...apsUserCollection];
      jest.spyOn(apsUserService, 'addApsUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ articleList });
      comp.ngOnInit();

      expect(apsUserService.query).toHaveBeenCalled();
      expect(apsUserService.addApsUserToCollectionIfMissing).toHaveBeenCalledWith(
        apsUserCollection,
        ...additionalApsUsers.map(expect.objectContaining),
      );
      expect(comp.apsUsersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const articleList: IArticleList = { id: 456 };
      const apsUser: IApsUser = { id: 14974 };
      articleList.apsUser = apsUser;

      activatedRoute.data = of({ articleList });
      comp.ngOnInit();

      expect(comp.apsUsersSharedCollection).toContain(apsUser);
      expect(comp.articleList).toEqual(articleList);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IArticleList>>();
      const articleList = { id: 123 };
      jest.spyOn(articleListFormService, 'getArticleList').mockReturnValue(articleList);
      jest.spyOn(articleListService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ articleList });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: articleList }));
      saveSubject.complete();

      // THEN
      expect(articleListFormService.getArticleList).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(articleListService.update).toHaveBeenCalledWith(expect.objectContaining(articleList));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IArticleList>>();
      const articleList = { id: 123 };
      jest.spyOn(articleListFormService, 'getArticleList').mockReturnValue({ id: null });
      jest.spyOn(articleListService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ articleList: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: articleList }));
      saveSubject.complete();

      // THEN
      expect(articleListFormService.getArticleList).toHaveBeenCalled();
      expect(articleListService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IArticleList>>();
      const articleList = { id: 123 };
      jest.spyOn(articleListService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ articleList });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(articleListService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareApsUser', () => {
      it('Should forward to apsUserService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(apsUserService, 'compareApsUser');
        comp.compareApsUser(entity, entity2);
        expect(apsUserService.compareApsUser).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
