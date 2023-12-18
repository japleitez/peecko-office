import { IArticleList } from 'app/entities/article-list/article-list.model';

export interface IArticleItem {
  id: number;
  previous?: string | null;
  code?: string | null;
  next?: string | null;
  articleList?: IArticleList | null;
}

export type NewArticleItem = Omit<IArticleItem, 'id'> & { id: null };
