import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ArticleListComponent } from './list/article-list.component';
import { ArticleListDetailComponent } from './detail/article-list-detail.component';
import { ArticleListUpdateComponent } from './update/article-list-update.component';
import ArticleListResolve from './route/article-list-routing-resolve.service';

const articleListRoute: Routes = [
  {
    path: '',
    component: ArticleListComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ArticleListDetailComponent,
    resolve: {
      articleList: ArticleListResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ArticleListUpdateComponent,
    resolve: {
      articleList: ArticleListResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ArticleListUpdateComponent,
    resolve: {
      articleList: ArticleListResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default articleListRoute;
