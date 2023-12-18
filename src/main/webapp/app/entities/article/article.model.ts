import { IArticleCategory } from 'app/entities/article-category/article-category.model';
import { ICoach } from 'app/entities/coach/coach.model';
import { Language } from 'app/entities/enumerations/language.model';

export interface IArticle {
  id: number;
  code?: string | null;
  title?: string | null;
  subtitle?: string | null;
  summary?: string | null;
  duration?: number | null;
  language?: keyof typeof Language | null;
  thumbnail?: string | null;
  audioUrl?: string | null;
  content?: string | null;
  articleCategory?: IArticleCategory | null;
  coach?: ICoach | null;
}

export type NewArticle = Omit<IArticle, 'id'> & { id: null };
