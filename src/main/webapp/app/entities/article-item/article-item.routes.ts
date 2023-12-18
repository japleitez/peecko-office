import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ArticleItemComponent } from './list/article-item.component';
import { ArticleItemDetailComponent } from './detail/article-item-detail.component';
import { ArticleItemUpdateComponent } from './update/article-item-update.component';
import ArticleItemResolve from './route/article-item-routing-resolve.service';

const articleItemRoute: Routes = [
  {
    path: '',
    component: ArticleItemComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ArticleItemDetailComponent,
    resolve: {
      articleItem: ArticleItemResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ArticleItemUpdateComponent,
    resolve: {
      articleItem: ArticleItemResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ArticleItemUpdateComponent,
    resolve: {
      articleItem: ArticleItemResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default articleItemRoute;
