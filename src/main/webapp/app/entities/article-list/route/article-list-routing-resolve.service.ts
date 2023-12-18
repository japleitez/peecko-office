import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IArticleList } from '../article-list.model';
import { ArticleListService } from '../service/article-list.service';

export const articleListResolve = (route: ActivatedRouteSnapshot): Observable<null | IArticleList> => {
  const id = route.params['id'];
  if (id) {
    return inject(ArticleListService)
      .find(id)
      .pipe(
        mergeMap((articleList: HttpResponse<IArticleList>) => {
          if (articleList.body) {
            return of(articleList.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default articleListResolve;
