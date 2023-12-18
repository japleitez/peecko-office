import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IArticleList, NewArticleList } from '../article-list.model';

export type PartialUpdateArticleList = Partial<IArticleList> & Pick<IArticleList, 'id'>;

type RestOf<T extends IArticleList | NewArticleList> = Omit<T, 'created' | 'updated'> & {
  created?: string | null;
  updated?: string | null;
};

export type RestArticleList = RestOf<IArticleList>;

export type NewRestArticleList = RestOf<NewArticleList>;

export type PartialUpdateRestArticleList = RestOf<PartialUpdateArticleList>;

export type EntityResponseType = HttpResponse<IArticleList>;
export type EntityArrayResponseType = HttpResponse<IArticleList[]>;

@Injectable({ providedIn: 'root' })
export class ArticleListService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/article-lists');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(articleList: NewArticleList): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(articleList);
    return this.http
      .post<RestArticleList>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(articleList: IArticleList): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(articleList);
    return this.http
      .put<RestArticleList>(`${this.resourceUrl}/${this.getArticleListIdentifier(articleList)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(articleList: PartialUpdateArticleList): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(articleList);
    return this.http
      .patch<RestArticleList>(`${this.resourceUrl}/${this.getArticleListIdentifier(articleList)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestArticleList>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestArticleList[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getArticleListIdentifier(articleList: Pick<IArticleList, 'id'>): number {
    return articleList.id;
  }

  compareArticleList(o1: Pick<IArticleList, 'id'> | null, o2: Pick<IArticleList, 'id'> | null): boolean {
    return o1 && o2 ? this.getArticleListIdentifier(o1) === this.getArticleListIdentifier(o2) : o1 === o2;
  }

  addArticleListToCollectionIfMissing<Type extends Pick<IArticleList, 'id'>>(
    articleListCollection: Type[],
    ...articleListsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const articleLists: Type[] = articleListsToCheck.filter(isPresent);
    if (articleLists.length > 0) {
      const articleListCollectionIdentifiers = articleListCollection.map(
        articleListItem => this.getArticleListIdentifier(articleListItem)!,
      );
      const articleListsToAdd = articleLists.filter(articleListItem => {
        const articleListIdentifier = this.getArticleListIdentifier(articleListItem);
        if (articleListCollectionIdentifiers.includes(articleListIdentifier)) {
          return false;
        }
        articleListCollectionIdentifiers.push(articleListIdentifier);
        return true;
      });
      return [...articleListsToAdd, ...articleListCollection];
    }
    return articleListCollection;
  }

  protected convertDateFromClient<T extends IArticleList | NewArticleList | PartialUpdateArticleList>(articleList: T): RestOf<T> {
    return {
      ...articleList,
      created: articleList.created?.toJSON() ?? null,
      updated: articleList.updated?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restArticleList: RestArticleList): IArticleList {
    return {
      ...restArticleList,
      created: restArticleList.created ? dayjs(restArticleList.created) : undefined,
      updated: restArticleList.updated ? dayjs(restArticleList.updated) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestArticleList>): HttpResponse<IArticleList> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestArticleList[]>): HttpResponse<IArticleList[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
