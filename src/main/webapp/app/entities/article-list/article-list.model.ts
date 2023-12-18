import dayjs from 'dayjs/esm';
import { IArticleItem } from 'app/entities/article-item/article-item.model';
import { IApsUser } from 'app/entities/aps-user/aps-user.model';

export interface IArticleList {
  id: number;
  name?: string | null;
  counter?: number | null;
  created?: dayjs.Dayjs | null;
  updated?: dayjs.Dayjs | null;
  articleItems?: IArticleItem[] | null;
  apsUser?: IApsUser | null;
}

export type NewArticleList = Omit<IArticleList, 'id'> & { id: null };
