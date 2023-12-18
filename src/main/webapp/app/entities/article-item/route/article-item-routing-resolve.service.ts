import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IArticleItem } from '../article-item.model';
import { ArticleItemService } from '../service/article-item.service';

export const articleItemResolve = (route: ActivatedRouteSnapshot): Observable<null | IArticleItem> => {
  const id = route.params['id'];
  if (id) {
    return inject(ArticleItemService)
      .find(id)
      .pipe(
        mergeMap((articleItem: HttpResponse<IArticleItem>) => {
          if (articleItem.body) {
            return of(articleItem.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default articleItemResolve;
