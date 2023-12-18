import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IArticleItem, NewArticleItem } from '../article-item.model';

export type PartialUpdateArticleItem = Partial<IArticleItem> & Pick<IArticleItem, 'id'>;

export type EntityResponseType = HttpResponse<IArticleItem>;
export type EntityArrayResponseType = HttpResponse<IArticleItem[]>;

@Injectable({ providedIn: 'root' })
export class ArticleItemService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/article-items');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(articleItem: NewArticleItem): Observable<EntityResponseType> {
    return this.http.post<IArticleItem>(this.resourceUrl, articleItem, { observe: 'response' });
  }

  update(articleItem: IArticleItem): Observable<EntityResponseType> {
    return this.http.put<IArticleItem>(`${this.resourceUrl}/${this.getArticleItemIdentifier(articleItem)}`, articleItem, {
      observe: 'response',
    });
  }

  partialUpdate(articleItem: PartialUpdateArticleItem): Observable<EntityResponseType> {
    return this.http.patch<IArticleItem>(`${this.resourceUrl}/${this.getArticleItemIdentifier(articleItem)}`, articleItem, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IArticleItem>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IArticleItem[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getArticleItemIdentifier(articleItem: Pick<IArticleItem, 'id'>): number {
    return articleItem.id;
  }

  compareArticleItem(o1: Pick<IArticleItem, 'id'> | null, o2: Pick<IArticleItem, 'id'> | null): boolean {
    return o1 && o2 ? this.getArticleItemIdentifier(o1) === this.getArticleItemIdentifier(o2) : o1 === o2;
  }

  addArticleItemToCollectionIfMissing<Type extends Pick<IArticleItem, 'id'>>(
    articleItemCollection: Type[],
    ...articleItemsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const articleItems: Type[] = articleItemsToCheck.filter(isPresent);
    if (articleItems.length > 0) {
      const articleItemCollectionIdentifiers = articleItemCollection.map(
        articleItemItem => this.getArticleItemIdentifier(articleItemItem)!,
      );
      const articleItemsToAdd = articleItems.filter(articleItemItem => {
        const articleItemIdentifier = this.getArticleItemIdentifier(articleItemItem);
        if (articleItemCollectionIdentifiers.includes(articleItemIdentifier)) {
          return false;
        }
        articleItemCollectionIdentifiers.push(articleItemIdentifier);
        return true;
      });
      return [...articleItemsToAdd, ...articleItemCollection];
    }
    return articleItemCollection;
  }
}
